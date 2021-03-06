package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.CompositionPropertyAndPropertyPhotoDao
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndPropertyPhoto

class CompositionPropertyAndPropertyPhotoDataRepository(private val compositionPropertyAndPropertyPhotoDao: CompositionPropertyAndPropertyPhotoDao) {

    fun getPropertyIllustration(propertyId: Int, isThisTheIllustration: Boolean): LiveData<CompositionPropertyAndPropertyPhoto> = compositionPropertyAndPropertyPhotoDao.getPropertyIllustration(propertyId, isThisTheIllustration)

    fun getPropertyPhotos(propertyId: Int): LiveData<List<CompositionPropertyAndPropertyPhoto>> = compositionPropertyAndPropertyPhotoDao.getPropertyPhotos(propertyId)

    fun insertPropertyPhoto(compositionPropertyAndPropertyPhoto: CompositionPropertyAndPropertyPhoto): Long = compositionPropertyAndPropertyPhotoDao.insertPropertyPhoto(compositionPropertyAndPropertyPhoto)

    fun updateCompositionPropertyAndPropertyPhoto(compositionPropertyAndPropertyPhoto: CompositionPropertyAndPropertyPhoto): Int = compositionPropertyAndPropertyPhotoDao.updateCompositionPropertyAndPropertyPhoto(compositionPropertyAndPropertyPhoto)

    fun deletePropertyPhoto(propertyId: Int, propertyPhotoId: Int) = compositionPropertyAndPropertyPhotoDao.deletePropertyPhoto(propertyId, propertyPhotoId)

}