package com.openclassrooms.realestatemanager.form.searchForm.injections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.form.searchForm.SearchFormViewModel
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndLocationOfInterestDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val propertyDataSource: PropertyDataRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchFormViewModel::class.java)) {
            return SearchFormViewModel(propertyDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}