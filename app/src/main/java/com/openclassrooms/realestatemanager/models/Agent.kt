package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Agent(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "agent_id")
        val id: Int,
        val name: String,
        val firstName: String
)