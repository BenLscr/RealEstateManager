package com.openclassrooms.realestatemanager.form.updateForm.injections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.form.updateForm.GetUpdateFormViewModel
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndLocationOfInterestDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndPropertyPhotoDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository

class GetViewModelFactory(
        private val propertyDataSource: PropertyDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val compositionPropertyAndLocationOfInterestDataSource: CompositionPropertyAndLocationOfInterestDataRepository,
        private val compositionPropertyAndPropertyPhotoDataSource: CompositionPropertyAndPropertyPhotoDataRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetUpdateFormViewModel::class.java)) {
            return GetUpdateFormViewModel(
                    propertyDataSource,
                    agentDataSource,
                    compositionPropertyAndLocationOfInterestDataSource,
                    compositionPropertyAndPropertyPhotoDataSource) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel Class")
    }

}