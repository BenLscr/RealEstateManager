package com.openclassrooms.realestatemanager.map.injections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.map.MapViewModel
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository

class ViewModelFactory(
        private val propertyDataSource: PropertyDataRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(propertyDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}