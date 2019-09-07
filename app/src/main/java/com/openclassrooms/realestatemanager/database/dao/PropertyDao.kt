package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.models.Property

@Dao
interface PropertyDao {

    @Query("SELECT * FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id")
    fun getProperties(): LiveData<List<Property>>

    @Query("SELECT * FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id WHERE :id == id")
    fun getProperty(id: Int): LiveData<Property>

    @Insert
    fun insertProperty(property: Property): Long

    @Update
    fun updateProperty(property: Property)

    @RawQuery(observedEntities = [Property::class])
    fun customSearchPropertiesId(query: SimpleSQLiteQuery): LiveData<List<Int>>

}