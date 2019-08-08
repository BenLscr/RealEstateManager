package com.openclassrooms.realestatemanager.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.form.models.FormModelRaw
import com.openclassrooms.realestatemanager.models.*
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndLocationOfInterestDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
import java.util.concurrent.Executor

class FormViewModel(
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val compositionPropertyAndLocationOfInterestDataSource: CompositionPropertyAndLocationOfInterestDataRepository,
        private val executor: Executor) : ViewModel() {

    private var _fullNameAgents: LiveData<List<String>> = Transformations.map(agentDataSource.getAgents()) { list -> list.map { agent -> agent.firstName + " " + agent.name } }
    val fullNameAgents: LiveData<List<String>> = _fullNameAgents

    fun startBuildingModelsForDatabase(formModelRaw: FormModelRaw) {
        buildAddressModel(formModelRaw)
    }

    private fun buildAddressModel(formModelRaw: FormModelRaw) {
        val address: Address
        with(formModelRaw) {
            address = Address(
                    path = path,
                    complement = returnComplementOrNull(complement),
                    district = getDistrictForDatabase(district),
                    city = getCityForDatabase(city),
                    postalCode = postalCode,
                    country = getCountryForDatabase(country)
            )
        }
        insertAddress(formModelRaw, address)
    }

    private fun insertAddress(formModelRaw: FormModelRaw, address: Address) =
            executor.execute {
                val rowIdAddress = addressDataSource.insertAddress(address)
                buildPropertyModel(formModelRaw, rowIdAddress)
            }

    private fun buildPropertyModel(formModelRaw: FormModelRaw, rowIdAddress: Long) {
        val property: Property
        with(formModelRaw) {
            property = Property(
                    type = getTypeForDatabase(type),
                    price = price,
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
        insertProperty(formModelRaw, property)
    }

    private fun insertProperty(formModelRaw: FormModelRaw, property: Property) =
            executor.execute {
                val rowIdProperty = propertyDataSource.insertProperty(property)
                buildCompositionPropertyAndLocationOfInterestDataSource(formModelRaw, rowIdProperty)
            }

    private fun buildCompositionPropertyAndLocationOfInterestDataSource(formModelRaw: FormModelRaw, rowIdProperty: Long) {
        with(formModelRaw) {
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

    //---FACTORY---\\
    private fun returnComplementOrNull(complement: String): String? {
        return if (complement.isNotEmpty()) {
            complement
        } else {
            null
        }
    }

    private fun getDistrictForDatabase(district: String): District {
        return when(district) {
            "Bronx" -> District.BRONX
            "Brooklyn" -> District.BROOKLYN
            "Manhattan" -> District.MANHATTAN
            "Queens" -> District.QUEENS
            "Staten Island" -> District.STATEN_ISLAND
            else -> return District.BRONX //TODO: Why ?
        }
    }

    private fun getCityForDatabase(city: String): City {
        return when(city) {
            "New York" -> City.NEW_YORK
            else -> City.NEW_YORK //TODO: Why ?
        }
    }

    private fun getCountryForDatabase(country: String): Country {
        return when(country) {
            "United States" -> Country.UNITED_STATES
            else -> Country.UNITED_STATES //TODO: Why ?
        }
    }
    private fun getTypeForDatabase(type: String): Type {
        return when(type) {
            "Flat" -> Type.FLAT
            "Penthouse" -> Type.PENTHOUSE
            "Mansion" -> Type.MANSION
            "Duplex" -> Type.DUPLEX
            "House" -> Type.HOUSE
            "Loft" -> Type.LOFT
            "Townhouse" -> Type.TOWNHOUSE
            "Condo" -> Type.CONDO
            else -> Type.FLAT //TODO: Why ?
        }
    }

    private fun getAgentIdForDatabase(fullNameAgent: String): Int {
        return when(fullNameAgent) {
            "Tony Stark" -> 1
            "Peter Parker" -> 2
            "Steve Rogers" -> 3
            "Natasha Romanoff" -> 4
            "Bruce Banner" -> 5
            "Clinton Barton" -> 6
            "Carol Denvers" -> 7
            "Wanda Maximoff" -> 8
            else -> 1 //TODO: Why ?
        }
    }

}