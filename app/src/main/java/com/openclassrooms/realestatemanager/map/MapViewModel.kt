package com.openclassrooms.realestatemanager.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.map.models.PropertyModelProcessed
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository

class MapViewModel(propertyDataSource: PropertyDataRepository) : ViewModel() {

    private var _allProperties: LiveData<List<PropertyModelProcessed>> = Transformations.map(propertyDataSource.getAllProperties()) { it.map { property -> buildAddressModelProcessed(property) } }
    val allProperties: LiveData<List<PropertyModelProcessed>> = _allProperties

    private fun buildAddressModelProcessed(property: Property) =
            PropertyModelProcessed(
                    propertyId = property.id,
                    addressGeocoding = property.address?.path + ", " + property.address?.city
            )

}