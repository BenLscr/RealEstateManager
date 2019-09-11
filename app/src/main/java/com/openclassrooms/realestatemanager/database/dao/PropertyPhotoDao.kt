package com.openclassrooms.realestatemanager.database.dao

import androidx.room.*
import com.openclassrooms.realestatemanager.models.PropertyPhoto

@Dao
interface PropertyPhotoDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertPropertyPhoto(propertyPhoto: PropertyPhoto): Long

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun updatePropertyPhoto(propertyPhoto: PropertyPhoto)

    @Query("DELETE FROM PropertyPhoto WHERE :id == property_photo_id")
    fun deletePropertyPhoto(id: Int)

}