package com.openclassrooms.realestatemanager.converters

import com.openclassrooms.realestatemanager.database.AgentConverter
import com.openclassrooms.realestatemanager.models.Agent
import org.junit.Assert
import org.junit.Test

class AgentConverterTest {

    @Test
    fun fromAgent_AgentToInt() {
        Assert.assertEquals(4, AgentConverter.fromAgent(Agent.BRUCE_BANNER))
    }

    @Test
    fun toAgent_IntToAgent() {
        Assert.assertEquals(Agent.PETER_PARKER, AgentConverter.toAgent(1))
    }

}