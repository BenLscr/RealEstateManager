package com.openclassrooms.realestatemanager.propertyDetail.media.injections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.propertyDetail.media.MediaViewModel
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndPropertyPhotoDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository

class ViewModelFactory(
        private val propertyDataSource: PropertyDataRepository,
        private val compositionPropertyAndPropertyPhotoDataSource: CompositionPropertyAndPropertyPhotoDataRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MediaViewModel::class.java)) {
            return MediaViewModel(propertyDataSource, compositionPropertyAndPropertyPhotoDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}