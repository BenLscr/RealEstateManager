package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndPropertyPhoto

@Dao
interface CompositionPropertyAndPropertyPhotoDao {

    @Query("SELECT * FROM CompositionPropertyAndPropertyPhoto INNER JOIN PropertyPhoto ON CompositionPropertyAndPropertyPhoto.propertyPhotoId = PropertyPhoto.property_photo_id WHERE :isThisTheIllustration = PropertyPhoto.isThisTheIllustration AND :propertyId = CompositionPropertyAndPropertyPhoto.propertyId")
    fun getPropertyIllustration(propertyId: Int, isThisTheIllustration: Boolean): LiveData<CompositionPropertyAndPropertyPhoto>

    @Query("SELECT * FROM CompositionPropertyAndPropertyPhoto INNER JOIN PropertyPhoto ON CompositionPropertyAndPropertyPhoto.propertyPhotoId = PropertyPhoto.property_photo_id WHERE :propertyId = propertyId")
    fun getPropertyPhotos(propertyId: Int): LiveData<List<CompositionPropertyAndPropertyPhoto>>

    @Insert
    fun insertPropertyPhoto(compositionPropertyAndPropertyPhoto: CompositionPropertyAndPropertyPhoto): Long

    @Update
    fun updateCompositionPropertyAndPropertyPhoto(compositionPropertyAndPropertyPhoto: CompositionPropertyAndPropertyPhoto): Int

    @Query("DELETE FROM CompositionPropertyAndPropertyPhoto WHERE :propertyId = propertyId AND :propertyPhotoId = propertyPhotoId")
    fun deletePropertyPhoto(propertyId: Int, propertyPhotoId: Int)

}