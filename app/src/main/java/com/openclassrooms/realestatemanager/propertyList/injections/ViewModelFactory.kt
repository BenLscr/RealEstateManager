package com.openclassrooms.realestatemanager.propertyList.injections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.propertyList.PropertyListViewModel
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndPropertyPhotoDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
import java.util.concurrent.Executor

class ViewModelFactory(
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val compositionPropertyAndPropertyPhotoDataRepository: CompositionPropertyAndPropertyPhotoDataRepository,
        private val executor: Executor): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyListViewModel::class.java)) {
            return PropertyListViewModel(propertyDataSource, addressDataSource, agentDataSource, compositionPropertyAndPropertyPhotoDataRepository, executor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}