package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.Property

@Dao
interface PropertyDao {

    @Query("SELECT * FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id")
    fun getProperties(): LiveData<List<Property>>

    @Query("SELECT * FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id WHERE :id == id")
    fun getProperty(id: Int): LiveData<Property>

    @Query("SELECT * FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id LIMIT 1")
    fun getFirstProperty(): LiveData<Property>

    @Insert
    fun insertProperty(property: Property)

    @Update
    fun updateProperty(property: Property)

    @Query("DELETE FROM Property WHERE :id == id")
    fun deleteProperty(id: Int)

    /**@Delete("DELETE * FROM Property WHERE :id == id")
    fun deleteProperty(id: Int)*/

}