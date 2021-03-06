package com.openclassrooms.realestatemanager.form.addForm.injections

import android.content.Context
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.repositories.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Injection {

    companion object {

        private fun providePropertyDataSource(context: Context): PropertyDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyDataRepository(database.propertyDao())
        }

        private fun provideAddressDataSource(context: Context): AddressDataRepository {
            val database = AppDatabase.getInstance(context)
            return AddressDataRepository(database.addressDao())
        }

        private fun provideAgentDataSource(context: Context): AgentDataRepository {
            val database = AppDatabase.getInstance(context)
            return AgentDataRepository(database.agentDao())
        }

        private fun provideCompositionPropertyAndLocationOfInterestDataSource(context: Context): CompositionPropertyAndLocationOfInterestDataRepository {
            val database = AppDatabase.getInstance(context)
            return CompositionPropertyAndLocationOfInterestDataRepository(database.compositionPropertyAndLocationOfInterestDao())
        }

        private fun providePropertyPhotoDataSource(context: Context): PropertyPhotoDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyPhotoDataRepository(database.propertyPhotoDao())
        }

        private fun provideCompositionPropertyAndPropertyPhoto(context: Context): CompositionPropertyAndPropertyPhotoDataRepository {
            val database = AppDatabase.getInstance(context)
            return CompositionPropertyAndPropertyPhotoDataRepository(database.compositionPropertyAndPropertyPhotoDao())
        }

        private fun provideExecutor() : Executor {
            return Executors.newSingleThreadExecutor()
        }

        fun provideViewModelFactory(context: Context): ViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            val dataSourceAddress = provideAddressDataSource(context)
            val dataSourceAgent = provideAgentDataSource(context)
            val dataSourceCompositionPropertyAndLocationOfInterest = provideCompositionPropertyAndLocationOfInterestDataSource(context)
            val dataSourcePropertyPhoto = providePropertyPhotoDataSource(context)
            val dataSourceCompositionPropertyAndPropertyPhoto = provideCompositionPropertyAndPropertyPhoto(context)
            val executor = provideExecutor()
            return ViewModelFactory(dataSourceProperty,
                    dataSourceAddress,
                    dataSourceAgent,
                    dataSourceCompositionPropertyAndLocationOfInterest,
                    dataSourcePropertyPhoto,
                    dataSourceCompositionPropertyAndPropertyPhoto,
                    executor)
        }

    }

}