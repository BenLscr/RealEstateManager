package com.openclassrooms.realestatemanager.propertyList.injections

import android.content.Context
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndPropertyPhotoDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Injection {

    companion object {

        private fun providePropertyDataSource(context: Context): PropertyDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyDataRepository(database.propertyDao())
        }

        private fun provideCompositionPropertyAndPropertyPhotoDataSource(context: Context): CompositionPropertyAndPropertyPhotoDataRepository {
            val database = AppDatabase.getInstance(context)
            return CompositionPropertyAndPropertyPhotoDataRepository(database.compositionPropertyAndPropertyPhotoDao())
        }

        fun provideViewModelFactory(context: Context): ViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            val dataSourceCompositionPropertyAndPropertyPhoto = provideCompositionPropertyAndPropertyPhotoDataSource(context)
            return ViewModelFactory(dataSourceProperty, dataSourceCompositionPropertyAndPropertyPhoto)
        }

    }

}