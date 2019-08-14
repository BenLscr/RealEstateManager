package com.openclassrooms.realestatemanager.propertyDetail.injections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.propertyDetail.PropertyDetailViewModel
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndLocationOfInterestDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

class ViewModelFactory(
        private val propertyDataSource: PropertyDataRepository,
        private val compositionPropertyAndLocationOfInterestDataSource: CompositionPropertyAndLocationOfInterestDataRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyDetailViewModel::class.java)) {
            return PropertyDetailViewModel(propertyDataSource, compositionPropertyAndLocationOfInterestDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}