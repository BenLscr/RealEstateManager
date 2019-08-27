package com.openclassrooms.realestatemanager.database.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.PropertyPhoto

@Dao
interface PropertyPhotoDao {

    @Query("Select * FROM PropertyPhoto WHERE :id == property_photo_id AND :isThisTheIllustration == isThisTheIllustration")
    fun getIllustrationPropertyPhoto(id: Int, isThisTheIllustration: Boolean): LiveData<PropertyPhoto>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertPropertyPhoto(propertyPhoto: PropertyPhoto): Long

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun updatePropertyPhoto(propertyPhoto: PropertyPhoto)

    @Query("DELETE FROM PropertyPhoto WHERE :id == property_photo_id")
    fun deletePropertyPhoto(id: Int)

}