package com.openclassrooms.realestatemanager.form.addForm

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.form.addForm.models.AddFormModelRaw
import com.openclassrooms.realestatemanager.models.*
import com.openclassrooms.realestatemanager.repositories.*
import java.util.concurrent.Executor

class AddFormViewModel(
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        agentDataSource: AgentDataRepository,
        private val compositionPropertyAndLocationOfInterestDataSource: CompositionPropertyAndLocationOfInterestDataRepository,
        private val propertyPhotoDataSource: PropertyPhotoDataRepository,
        private val compositionPropertyAndPropertyPhotoDataSource: CompositionPropertyAndPropertyPhotoDataRepository,
        private val executor: Executor) : ViewModel() {

    private var _fullNameAgents: LiveData<List<String>> = Transformations.map(agentDataSource.getAgents()) { list -> list.map { agent -> agent.firstName + " " + agent.name } }
    val fullNameAgents: LiveData<List<String>> = _fullNameAgents

    fun startBuildingModelsForDatabase(addFormModelRaw: AddFormModelRaw) = buildAddressModel(addFormModelRaw)

    private fun buildAddressModel(addFormModelRaw: AddFormModelRaw) {
        val address: Address
        with(addFormModelRaw) {
            address = Address(
                    path = path,
                    complement = returnComplementOrNull(complement),
                    district = getDistrictForDatabase(district),
                    city = getCityForDatabase(city),
                    postalCode = postalCode,
                    country = getCountryForDatabase(country)
            )
        }
        insertAddress(addFormModelRaw, address)
    }

    private fun insertAddress(addFormModelRaw: AddFormModelRaw, address: Address) =
            executor.execute {
                val rowIdAddress = addressDataSource.insertAddress(address)
                buildPropertyModel(addFormModelRaw, rowIdAddress)
            }

    private fun buildPropertyModel(addFormModelRaw: AddFormModelRaw, rowIdAddress: Long) {
        val property: Property
        with(addFormModelRaw) {
            property = Property(
                    type = getTypeForDatabase(type),
                    price = price.toLong(),
                    surface = surface.toInt(),
                    rooms = rooms.toInt(),
                    bedrooms = bedrooms.toInt(),
                    bathrooms = bathrooms.toInt(),
                    description = description,
                    addressId = rowIdAddress.toInt(),
                    available = available,
                    entryDate = entryDate,
                    saleDate = null,
                    agentId = getAgentIdForDatabase(fullNameAgent)
            )
        }
        insertProperty(addFormModelRaw, property)
    }

    private fun insertProperty(addFormModelRaw: AddFormModelRaw, property: Property) =
            executor.execute {
                val rowIdProperty = propertyDataSource.insertProperty(property)
                buildCompositionPropertyAndLocationOfInterestDataSource(addFormModelRaw, rowIdProperty)
                buildPropertyPhotoAndSavePhotosOnInternalStorage(addFormModelRaw, rowIdProperty)
            }

    private fun buildCompositionPropertyAndLocationOfInterestDataSource(addFormModelRaw: AddFormModelRaw, rowIdProperty: Long) {
        with(addFormModelRaw) {
            if (school){
                val compositionPropertyAndLocationOfInterest = CompositionPropertyAndLocationOfInterest(
                        propertyId = rowIdProperty.toInt(),
                        locationOfInterestId = LocationOfInterest.SCHOOL.ordinal
                )
                insertCompositionPropertyAndLocationOfInterest(compositionPropertyAndLocationOfInterest)
            }
            if (commerces) {
                val compositionPropertyAndLocationOfInterest = CompositionPropertyAndLocationOfInterest(
                        propertyId = rowIdProperty.toInt(),
                        locationOfInterestId = LocationOfInterest.COMMERCES.ordinal
                )
                insertCompositionPropertyAndLocationOfInterest(compositionPropertyAndLocationOfInterest)
            }
            if (park) {
                val compositionPropertyAndLocationOfInterest = CompositionPropertyAndLocationOfInterest(
                        propertyId = rowIdProperty.toInt(),
                        locationOfInterestId = LocationOfInterest.PARK.ordinal
                )
                insertCompositionPropertyAndLocationOfInterest(compositionPropertyAndLocationOfInterest)
            }
            if (subways) {
                val compositionPropertyAndLocationOfInterest = CompositionPropertyAndLocationOfInterest(
                        propertyId = rowIdProperty.toInt(),
                        locationOfInterestId = LocationOfInterest.SUBWAYS.ordinal
                )
                insertCompositionPropertyAndLocationOfInterest(compositionPropertyAndLocationOfInterest)
            }
            if (train) {
                val compositionPropertyAndLocationOfInterest = CompositionPropertyAndLocationOfInterest(
                        propertyId = rowIdProperty.toInt(),
                        locationOfInterestId = LocationOfInterest.TRAIN.ordinal
                )
                insertCompositionPropertyAndLocationOfInterest(compositionPropertyAndLocationOfInterest)
            }
        }
    }

    private fun insertCompositionPropertyAndLocationOfInterest(compositionPropertyAndLocationOfInterest: CompositionPropertyAndLocationOfInterest) =
            executor.execute {
                compositionPropertyAndLocationOfInterestDataSource.insertLocationOfInterest(compositionPropertyAndLocationOfInterest)
            }

    private fun buildPropertyPhotoAndSavePhotosOnInternalStorage(addFormModelRaw: AddFormModelRaw, rowIdProperty: Long) {
        with(addFormModelRaw) {
            listFormPhotoAndWording.forEachIndexed {index, formPhotoAndWording ->
                val name = getNamePhoto(index)
                Utils.setInternalBitmap(formPhotoAndWording.photo, path, name, context)
                val propertyPhoto = PropertyPhoto(
                        name = name,
                        wording = getWordingForDatabase(formPhotoAndWording.wording),
                        isThisTheIllustration = index == 0
                )
                insertPropertyPhoto(propertyPhoto, rowIdProperty)
            }
        }
        sendNotification(addFormModelRaw)
    }

    private fun insertPropertyPhoto(propertyPhoto: PropertyPhoto, rowIdProperty: Long) =
            executor.execute {
                val rowIdPropertyPhoto = propertyPhotoDataSource.insertPropertyPhoto(propertyPhoto)
                buildCompositionPropertyAndPropertyPhoto(rowIdProperty, rowIdPropertyPhoto)
            }


    private fun buildCompositionPropertyAndPropertyPhoto(rowIdProperty: Long, rowIdPropertyPhoto: Long) {
        val compositionPropertyAndPropertyPhoto = CompositionPropertyAndPropertyPhoto(
                rowIdProperty.toInt(),
                rowIdPropertyPhoto.toInt()
        )
        insertCompositionPropertyAndPropertyPhoto(compositionPropertyAndPropertyPhoto)
    }

    private fun insertCompositionPropertyAndPropertyPhoto(compositionPropertyAndPropertyPhoto: CompositionPropertyAndPropertyPhoto) =
            executor.execute {
                compositionPropertyAndPropertyPhotoDataSource.insertPropertyPhoto(compositionPropertyAndPropertyPhoto)
            }

    private fun sendNotification(addFormModelRaw: AddFormModelRaw) {
        //TODO : Utils
        /*val builder = NotificationCompat.Builder(addFormModelRaw.context, "channelId")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("RealEstateManager")
                .setContentText("Your property as been add.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(addFormModelRaw.context).notify(0, builder.build())*/
    }

    //---FACTORY---\\
    private fun returnComplementOrNull(complement: String) = if (complement.isNotEmpty()) { complement } else { null }

    private fun getDistrictForDatabase(district: String) =
            when(district) {
                "Bronx" -> District.BRONX
                "Brooklyn" -> District.BROOKLYN
                "Manhattan" -> District.MANHATTAN
                "Queens" -> District.QUEENS
                "Staten Island" -> District.STATEN_ISLAND
                else -> District.BRONX
            }

    private fun getCityForDatabase(city: String) =
            when(city) {
                "New York" -> City.NEW_YORK
                else -> City.NEW_YORK
            }

    private fun getCountryForDatabase(country: String) =
            when(country) {
                "United States" -> Country.UNITED_STATES
                else -> Country.UNITED_STATES
            }

    private fun getTypeForDatabase(type: String) =
            when(type) {
                "Flat" -> Type.FLAT
                "Penthouse" -> Type.PENTHOUSE
                "Mansion" -> Type.MANSION
                "Duplex" -> Type.DUPLEX
                "House" -> Type.HOUSE
                "Loft" -> Type.LOFT
                "Townhouse" -> Type.TOWNHOUSE
                "Condo" -> Type.CONDO
                else -> Type.FLAT
            }

    private fun getAgentIdForDatabase(fullNameAgent: String) =
            when(fullNameAgent) {
                "Tony Stark" -> 1
                "Peter Parker" -> 2
                "Steve Rogers" -> 3
                "Natasha Romanoff" -> 4
                "Bruce Banner" -> 5
                "Clinton Barton" -> 6
                "Carol Denvers" -> 7
                "Wanda Maximoff" -> 8
                else -> 1
        }

    private fun getWordingForDatabase(wording: String?) =
            when(wording) {
                "Street View" -> Wording.STREET_VIEW
                "Living room" -> Wording.LIVING_ROOM
                "Hall" -> Wording.HALL
                "Kitchen" -> Wording.KITCHEN
                "Dining room" -> Wording.DINING_ROOM
                "Bathroom" -> Wording.BATHROOM
                "Balcony" -> Wording.BALCONY
                "Bedroom" -> Wording.BEDROOM
                "Terrace" -> Wording.TERRACE
                "Walk in closet" -> Wording.WALK_IN_CLOSET
                "Office" -> Wording.OFFICE
                "Roof top" -> Wording.ROOF_TOP
                "plan" -> Wording.PLAN
                "Hallway" -> Wording.HALLWAY
                "View" -> Wording.VIEW
                "Garage" -> Wording.GARAGE
                "Swimming pool" -> Wording.SWIMMING_POOL
                "Fitness centre" -> Wording.FITNESS_CENTRE
                "Spa" -> Wording.SPA
                "Cinema" -> Wording.CINEMA
                "Conference" -> Wording.CONFERENCE
                "Stairs" -> Wording.STAIRS
                "Garden" -> Wording.GARDEN
                "Floor" -> Wording.FLOOR
                else -> Wording.STREET_VIEW
            }

    private fun getNamePhoto(index: Int) = "$index.png"

}