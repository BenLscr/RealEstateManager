package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.Property

@Dao
interface PropertyDao {

    @Query("SELECT * FROM Property")
    fun getProperties(): LiveData<List<Property>>

    @Query("SELECT * FROM Property WHERE :id == id")
    fun getProperty(id: Int): LiveData<Property>

    @Insert
    fun insertProperty(property: Property)

    @Update
    fun updateProperty(property: Property)

    @Query("DELETE FROM Property WHERE :id == id")
    fun deleteProperty(id: Int)

    /**@Delete("DELETE * FROM Property WHERE :id == id")
    fun deleteProperty(id: Int)*/

}