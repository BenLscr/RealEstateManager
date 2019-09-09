package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.database.dao.PropertyDao
import com.openclassrooms.realestatemanager.models.Property

class PropertyDataRepository(private val propertyDao: PropertyDao) {

    fun getAllProperties(): LiveData<List<Property>> = propertyDao.getAllProperties()

    fun getProperties(query: SimpleSQLiteQuery): LiveData<List<Property>> = propertyDao.getProperties(query)

    fun getProperty(propertyId: Int): LiveData<Property> = propertyDao.getProperty(propertyId)

    fun insertProperty(property: Property): Long = propertyDao.insertProperty(property)

    fun updateProperty(property: Property) = propertyDao.updateProperty(property)

    fun customSearchPropertiesId(query: SimpleSQLiteQuery): LiveData<List<Int>> = propertyDao.customSearchPropertiesId(query)

}