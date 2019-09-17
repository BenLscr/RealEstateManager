package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndLocationOfInterest

@Dao
interface CompositionPropertyAndLocationOfInterestDao {

    @Query("SELECT * FROM CompositionPropertyAndLocationOfInterest WHERE :id == propertyId")
    fun getLocationsOfInterest(id: Int): LiveData<List<CompositionPropertyAndLocationOfInterest>>

    @Insert
    fun insertLocationOfInterest(compositionPropertyAndLocationOfInterest: CompositionPropertyAndLocationOfInterest): Long

    @Update
    fun updateCompositionPropertyAndLocationOfInterest(compositionPropertyAndLocationOfInterest: CompositionPropertyAndLocationOfInterest): Int

    @Query("DELETE FROM CompositionPropertyAndLocationOfInterest WHERE :propertyId == propertyId AND :locationOfInterestId == locationOfInterestId")
    fun deleteLocationOfInterest(propertyId: Int, locationOfInterestId: Int)

}