package com.openclassrooms.realestatemanager.propertyDetail

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.models.*
import com.openclassrooms.realestatemanager.propertyDetail.models.LocationsOfInterestModelProcessed
import com.openclassrooms.realestatemanager.propertyDetail.models.PropertyModelProcessed
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndLocationOfInterestDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
import java.text.SimpleDateFormat
import java.util.*

class PropertyDetailViewModel(
        private val propertyDataSource: PropertyDataRepository,
        private val compositionPropertyAndLocationOfInterestDataSource: CompositionPropertyAndLocationOfInterestDataRepository) : ViewModel() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    fun getProperty(propertyId: Int): LiveData<PropertyModelProcessed> =
            Transformations.map(propertyDataSource.getProperty(propertyId)) { buildPropertyModelProcessed(it) }

    private fun buildPropertyModelProcessed(property: Property) =
            PropertyModelProcessed(
                    description = property.description,
                    surface = surfaceIntToStringForUi(property.surface),
                    rooms = property.rooms.toString(),
                    bathrooms = property.bathrooms.toString(),
                    bedrooms = property.bedrooms.toString(),
                    available = property.available,
                    path = property.address?.path,
                    complement = property.address?.complement,
                    city = getCityIntoStringForUi(property.address?.city),
                    postalCode = property.address?.postalCode,
                    country = getCountryIntoStringForUi(property.address?.country),
                    agentFullName = getAgentFullName(property.agent?.firstName, property.agent?.name),
                    entryDate = getEntryDateIntoStringForUi(property.entryDate),
                    saleDate = getSaleDateIntoStringForUi(property.saleDate)
            )

    private fun surfaceIntToStringForUi(surface: Int?) = surface.toString() + "sq ft"

    private fun getCityIntoStringForUi(city: City?) =
            when(city) {
                City.NEW_YORK -> "New York"
                else -> null
            }

    private fun getCountryIntoStringForUi(country: Country?) =
            when(country) {
                Country.UNITED_STATES -> "United States"
                else -> null
            }


    private fun getAgentFullName(firstName: String?, name: String?) = "$firstName $name"

    private fun getEntryDateIntoStringForUi(entryDate: Long) = dateFormat.format(Date(entryDate))

    private fun getSaleDateIntoStringForUi(saleDate: Long?) =
            if (saleDate != null) {
                dateFormat.format(Date(saleDate))
            } else {
                null
            }

    fun getLocationsOfInterest(propertyId: Int): LiveData<LocationsOfInterestModelProcessed> =
            Transformations.map(compositionPropertyAndLocationOfInterestDataSource.getLocationsOfInterest(propertyId)) { buildLocationOfInterestModelProcessed(it) }

    //---FACTORY---\\
    private fun buildLocationOfInterestModelProcessed(composition: List<CompositionPropertyAndLocationOfInterest>): LocationsOfInterestModelProcessed {
        var school = false
        var commerces= false
        var park = false
        var subways = false
        var train = false
        for(locationOfInterest in composition) {
            when(locationOfInterest.locationOfInterestId) {
                0 -> school = true
                1 -> commerces = true
                2 -> park = true
                3 -> subways = true
                4 -> train = true
            }
        }
        return LocationsOfInterestModelProcessed(
                school = school,
                commerces = commerces,
                park = park,
                subways = subways,
                train = train
        )
    }

}
