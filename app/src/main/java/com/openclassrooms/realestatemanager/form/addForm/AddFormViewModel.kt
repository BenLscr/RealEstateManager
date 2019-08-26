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
                    district = Utils.fromStringToDistrict(district),
                    city = Utils.fromStringToCity(city),
                    postalCode = postalCode,
                    country = Utils.fromStringToCountry(country)
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
                    type = Utils.fromStringToType(type),
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
                    agentId = Utils.fromStringToAgent(fullNameAgent)
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
                val name = Utils.createNamePhoto(index)
                Utils.setInternalBitmap(formPhotoAndWording.photo, rowIdProperty.toString(), name, context)
                val propertyPhoto = PropertyPhoto(
                        name = name,
                        wording = Utils.fromStringToWording(formPhotoAndWording.wording),
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

}