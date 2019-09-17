package com.openclassrooms.realestatemanager.database.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.models.Property

@Dao
interface PropertyDao {

    @Query("SELECT * FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id")
    fun getAllProperties(): LiveData<List<Property>>

    @Query("SELECT * FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id WHERE :id == id")
    fun getProperty(id: Int): LiveData<Property>

    @Query("SELECT * FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id WHERE :id == id")
    fun getPropertyWithCursor(id: Int): Cursor

    @Insert
    fun insertProperty(property: Property): Long

    @Update
    fun updateProperty(property: Property): Int

    @RawQuery(observedEntities = [Property::class])
    fun customSearchPropertiesId(query: SimpleSQLiteQuery): LiveData<List<Int>>

    @RawQuery(observedEntities = [Property::class])
    fun getProperties(query: SimpleSQLiteQuery): LiveData<List<Property>>

}