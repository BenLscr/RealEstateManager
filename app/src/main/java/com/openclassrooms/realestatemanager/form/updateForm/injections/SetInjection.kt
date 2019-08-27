package com.openclassrooms.realestatemanager.form.updateForm.injections

import android.content.Context
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndLocationOfInterestDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndPropertyPhotoDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository

class SetInjection {

    companion object {

        private fun providePropertyDataSource(context: Context): PropertyDataRepository {
            val database = AppDatabase.getInstance(context)
            return PropertyDataRepository(database.propertyDao())
        }

        private fun provideAgentDataSource(context: Context): AgentDataRepository {
            val database = AppDatabase.getInstance(context)
            return AgentDataRepository(database.agentDao())
        }

        private fun provideCompositionPropertyAndLocationOfInterestDataSource(context: Context): CompositionPropertyAndLocationOfInterestDataRepository {
            val database = AppDatabase.getInstance(context)
            return CompositionPropertyAndLocationOfInterestDataRepository(database.compositionPropertyAndLocationOfInterestDao())
        }

        private fun provideCompositionPropertyAndPropertyPhoto(context: Context): CompositionPropertyAndPropertyPhotoDataRepository {
            val database = AppDatabase.getInstance(context)
            return CompositionPropertyAndPropertyPhotoDataRepository(database.compositionPropertyAndPropertyPhotoDao())
        }

        fun provideViewModelFactory(context: Context): SetViewModelFactory {
            val dataSourceProperty = providePropertyDataSource(context)
            val dataSourceAgent = provideAgentDataSource(context)
            val dataSourceCompositionPropertyAndLocationOfInterest = provideCompositionPropertyAndLocationOfInterestDataSource(context)
            val dataSourceCompositionPropertyAndPropertyPhoto = provideCompositionPropertyAndPropertyPhoto(context)
            return SetViewModelFactory(dataSourceProperty,
                    dataSourceAgent,
                    dataSourceCompositionPropertyAndLocationOfInterest,
                    dataSourceCompositionPropertyAndPropertyPhoto)
        }

    }

}