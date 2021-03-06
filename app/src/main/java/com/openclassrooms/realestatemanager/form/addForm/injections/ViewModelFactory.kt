package com.openclassrooms.realestatemanager.form.addForm.injections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.form.addForm.AddFormViewModel
import com.openclassrooms.realestatemanager.repositories.*
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

class ViewModelFactory(
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val compositionPropertyAndLocationOfInterestDataSource: CompositionPropertyAndLocationOfInterestDataRepository,
        private val propertyPhotoDataRepository: PropertyPhotoDataRepository,
        private val compositionPropertyAndPropertyPhotoDataSource: CompositionPropertyAndPropertyPhotoDataRepository,
        private val executor: Executor): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFormViewModel::class.java)) {
            return AddFormViewModel(
                    propertyDataSource,
                    addressDataSource,
                    agentDataSource,
                    compositionPropertyAndLocationOfInterestDataSource,
                    propertyPhotoDataRepository,
                    compositionPropertyAndPropertyPhotoDataSource,
                    executor) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel Class")
    }

}