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
    fun insertLocationOfInterest(compositionPropertyAndLocationOfInterest: CompositionPropertyAndLocationOfInterest)

    @Update
    fun updateLocationOfInterest(compositionPropertyAndLocationOfInterest: CompositionPropertyAndLocationOfInterest)

    @Query("DELETE FROM CompositionPropertyAndLocationOfInterest WHERE :id == propertyId")
    fun deleteLocationOfInterest(id: Int)

}