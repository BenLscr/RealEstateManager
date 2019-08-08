package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.PropertyDao
import com.openclassrooms.realestatemanager.models.Property

class PropertyDataRepository(private val propertyDao: PropertyDao) {

    fun getProperties(): LiveData<List<Property>> { return propertyDao.getProperties() }

    fun getProperty(propertyId: Int): LiveData<Property> { return propertyDao.getProperty(propertyId) }

    fun insertProperty(property: Property): Long { return propertyDao.insertProperty(property) }

    fun updateProperty(property: Property) = propertyDao.updateProperty(property)

    fun deleteProperty(propertyId: Int) = propertyDao.deleteProperty(propertyId)

}