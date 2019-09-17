package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.AgentDao
import com.openclassrooms.realestatemanager.models.Agent

class AgentDataRepository(private val agentDao: AgentDao) {

    fun getAgents(): LiveData<List<Agent>> = agentDao.getAgents()

    fun insertAgent(agent: Agent): Long = agentDao.insertAgent(agent)

    fun updateAgent(agent: Agent): Int = agentDao.updateAgent(agent)

}