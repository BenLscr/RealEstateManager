package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.PropertyPhotoDao
import com.openclassrooms.realestatemanager.models.PropertyPhoto

class PropertyPhotoDataRepository(private val propertyPhotoDao: PropertyPhotoDao) {

    fun getIllustrationPropertyPhoto(propertyPhotoId: Int, isThisTheIllustration: Boolean): LiveData<PropertyPhoto> { return propertyPhotoDao.getIllustrationPropertyPhoto(propertyPhotoId, isThisTheIllustration) }

    fun insertPropertyPhoto(propertyPhoto: PropertyPhoto) = propertyPhotoDao.insertPropertyPhoto(propertyPhoto)

    fun updatePropertyPhoto(propertyPhoto: PropertyPhoto) = propertyPhotoDao.updatePropertyPhoto(propertyPhoto)

    fun deletePropertyPhoto(propertyPhotoId: Int) = propertyPhotoDao.deletePropertyPhoto(propertyPhotoId)

}