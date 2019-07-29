package com.openclassrooms.realestatemanager.propertyDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.models.City
import com.openclassrooms.realestatemanager.models.Country
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.propertyDetail.models.ModelsProcessedPropertyDetail
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
import java.util.concurrent.Executor

class PropertyDetailViewModel(
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val executor: Executor) : ViewModel() {

    private var _properties: LiveData<List<ModelsProcessedPropertyDetail>> = Transformations.map(propertyDataSource.getProperties()) { it.map { property -> buildUiModel(property) } }
    val properties:LiveData<List<ModelsProcessedPropertyDetail>> = _properties

    private fun buildUiModel(property: Property) =
            ModelsProcessedPropertyDetail(
                    description = property.description,
                    surface = surfaceIntToString(property.surface),
                    rooms = property.rooms.toString(),
                    bathrooms = property.bathrooms.toString(),
                    bedrooms = property.bedrooms.toString(),
                    path = property.address?.path,
                    complement = property.address?.complement,
                    city = getCityIntoStringForUi(property.address?.city),
                    postalCode = property.address?.postalCode,
                    country = getCountryIntoStringForUi(property.address?.country)
            )

    private fun surfaceIntToString(surface: Int): String {
        return surface.toString() + "sq ft"
    }

    private fun getCityIntoStringForUi(city: City?): String? {
        return when(city) {
            City.NEW_YORK -> "New York"
            else -> null
        }
    }

    private fun getCountryIntoStringForUi(country: Country?): String? {
        return when(country) {
            Country.UNITED_STATES -> "United States"
            else -> null
        }
    }

}
