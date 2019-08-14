package com.openclassrooms.realestatemanager.propertyDetail.injections

import android.content.Context
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndLocationOfInterest
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndLocationOfInterestDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Injection {

    companion object {

        private fun providePropertyDataSource(context: Context): PropertyDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyDataRepository(database.propertyDao())
        }

        private fun provideCompositionPropertyAndLocationOfInterestDataSource(context: Context): CompositionPropertyAndLocationOfInterestDataRepository {
            val database = AppDatabase.getInstance(context)
            return CompositionPropertyAndLocationOfInterestDataRepository(database.compositionPropertyAndLocationOfInterestDao())
        }

        fun provideViewModelFactory(context: Context): ViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            val dataSourceCompositionPropertyAndLocationOfInterest = provideCompositionPropertyAndLocationOfInterestDataSource(context)
            return ViewModelFactory(dataSourceProperty, dataSourceCompositionPropertyAndLocationOfInterest)
        }

    }

}