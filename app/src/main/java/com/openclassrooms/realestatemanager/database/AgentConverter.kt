package com.openclassrooms.realestatemanager.database

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.models.Agent

class AgentConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromAgent(agent: Agent): Int {
            return agent.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toAgent(int: Int): Agent {
            return Agent.values()[int]
        }
    }
}