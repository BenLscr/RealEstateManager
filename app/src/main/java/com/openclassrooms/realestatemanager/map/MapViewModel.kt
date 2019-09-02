package com.openclassrooms.realestatemanager.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.map.models.PropertyModelProcessed
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository

class MapViewModel(propertyDataSource: PropertyDataRepository) : ViewModel() {

    private var _properties: LiveData<List<PropertyModelProcessed>> = Transformations.map(propertyDataSource.getProperties()) { it.map { property -> buildAddressModelProcessed(property) } }
    val properties: LiveData<List<PropertyModelProcessed>> = _properties

    private fun buildAddressModelProcessed(property: Property) =
            PropertyModelProcessed(
                    propertyId = property.id,
                    addressGeocoding = property.address?.path + ", " + property.address?.city
            )

}