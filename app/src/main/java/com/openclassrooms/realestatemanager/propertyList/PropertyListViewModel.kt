package com.openclassrooms.realestatemanager.propertyList

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.models.*
import com.openclassrooms.realestatemanager.propertyList.models.ModelsProcessedPropertyList
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyPhotoDataRepository
import java.text.NumberFormat
import java.util.concurrent.Executor

class PropertyListViewModel (
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val propertyPhotoDataSource: PropertyPhotoDataRepository,
        private val executor: Executor) : ViewModel() {

    private var _properties: LiveData<List<ModelsProcessedPropertyList>> = Transformations.map(propertyDataSource.getProperties()) { it.map { property -> buildUiModel(property) } }
    val properties: LiveData<List<ModelsProcessedPropertyList>> = _properties

    private fun buildUiModel(property: Property) =
            ModelsProcessedPropertyList(
                    propertyId = property.id,
                    type = getTypeIntoStringForUi(property.type),
                    district = getDistrictIntoStringForUi(property.address?.district),
                    price = getPriceIntoStringForUi(property.price)
            )

    private fun getTypeIntoStringForUi(type: Type): String {
        return when(type) {
            Type.PENTHOUSE -> "Penthouse"
            Type.MANSION -> "Mansion"
            Type.FLAT -> "Flat"
            Type.DUPLEX -> "Duplex"
            Type.HOUSE -> "House"
            Type.LOFT -> "Loft"
            Type.TOWNHOUSE -> "Townhouse"
            Type.CONDO -> "Condo"
        }
    }

    private fun getDistrictIntoStringForUi(district: District?): String? {
        return when(district) {
            District.MANHATTAN -> "Manhattan"
            District.BROOKLYN -> "Brooklyn"
            District.STATEN_ISLAND -> "Staten Island"
            District.QUEENS -> "Queens"
            District.BRONX -> "Bronx"
            else -> null
        }
    }

    private fun getPriceIntoStringForUi(price: Long): String {
        return "$" + NumberFormat.getIntegerInstance().format(price)
    }

}