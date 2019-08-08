package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.openclassrooms.realestatemanager.models.Agent

@Dao
interface AgentDao {

    @Query("SELECT * FROM Agent")
    fun getAgents(): LiveData<List<Agent>>

    @Query("SELECT * FROM Agent WHERE :id == agent_id")
    fun getAgent(id: Int): LiveData<Agent>

    @Insert
    fun insertAgent(agent: Agent): Long

    @Update
    fun updateAgent(agent: Agent)

    @Query("DELETE FROM Agent WHERE :id == agent_id")
    fun deleteAgent(id: Int)

}