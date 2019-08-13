package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.CompositionPropertyAndPropertyPhotoDao
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndPropertyPhoto

class CompositionPropertyAndPropertyPhotoDataRepository(private val compositionPropertyAndPropertyPhotoDao: CompositionPropertyAndPropertyPhotoDao) {

    fun getIllustrationPropertyPhotos(isThisTheIllustration: Boolean): LiveData<List<CompositionPropertyAndPropertyPhoto>> { return compositionPropertyAndPropertyPhotoDao.getIllustrationPropertyPhotos(isThisTheIllustration) }

    fun getPropertyPhotos(propertyId: Int): LiveData<List<CompositionPropertyAndPropertyPhoto>> { return compositionPropertyAndPropertyPhotoDao.getPropertyPhotos(propertyId) }

    fun insertPropertyPhoto(compositionPropertyAndPropertyPhoto: CompositionPropertyAndPropertyPhoto) = compositionPropertyAndPropertyPhotoDao.insertPropertyPhoto(compositionPropertyAndPropertyPhoto)

    fun updatePropertyPhoto(compositionPropertyAndPropertyPhoto: CompositionPropertyAndPropertyPhoto) = compositionPropertyAndPropertyPhotoDao.updatePropertyPhoto(compositionPropertyAndPropertyPhoto)

    fun deletePropertyPhoto(propertyId: Int) = compositionPropertyAndPropertyPhotoDao.deletePropertyPhoto(propertyId)

}