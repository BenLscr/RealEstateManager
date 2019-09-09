package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.AgentDao
import com.openclassrooms.realestatemanager.models.Agent

class AgentDataRepository(private val agentDao: AgentDao) {

    fun getAgents(): LiveData<List<Agent>> = agentDao.getAgents()

    fun getAgent(agentId: Int): LiveData<Agent> = agentDao.getAgent(agentId)

    fun insertAgent(agent: Agent): Long = agentDao.insertAgent(agent)

    fun updatetAgent(agent: Agent) = agentDao.updateAgent(agent)

    fun deleteAgent(agentId: Int) = agentDao.deleteAgent(agentId)

}