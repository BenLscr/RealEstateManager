package com.openclassrooms.realestatemanager.converters

import com.openclassrooms.realestatemanager.database.AgentConverter
import com.openclassrooms.realestatemanager.models.Agent
import org.junit.Assert.assertEquals
import org.junit.Test

class AgentConverterTest {

    @Test
    fun fromAgent_AgentToInt() {
        assertEquals(4, AgentConverter.fromAgent(Agent.BRUCE_BANNER))
    }

    @Test
    fun toAgent_IntToAgent() {
        assertEquals(Agent.PETER_PARKER, AgentConverter.toAgent(1))
    }

}