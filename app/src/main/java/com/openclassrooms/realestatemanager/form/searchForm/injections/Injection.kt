package com.openclassrooms.realestatemanager.form.searchForm.injections

import android.content.Context
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndLocationOfInterestDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository

class Injection {

    companion object {

        private fun providePropertyDataSource(context: Context): PropertyDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyDataRepository(database.propertyDao())
        }

        fun provideViewModelFactory(context: Context): ViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            return ViewModelFactory(dataSourceProperty)
        }

    }

}