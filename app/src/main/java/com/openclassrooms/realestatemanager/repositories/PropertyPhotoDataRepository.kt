package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.database.dao.PropertyPhotoDao
import com.openclassrooms.realestatemanager.models.PropertyPhoto

class PropertyPhotoDataRepository(private val propertyPhotoDao: PropertyPhotoDao) {

    fun insertPropertyPhoto(propertyPhoto: PropertyPhoto): Long = propertyPhotoDao.insertPropertyPhoto(propertyPhoto)

    fun updatePropertyPhoto(propertyPhoto: PropertyPhoto): Int = propertyPhotoDao.updatePropertyPhoto(propertyPhoto)

    fun deletePropertyPhoto(propertyPhotoId: Int) = propertyPhotoDao.deletePropertyPhoto(propertyPhotoId)

}