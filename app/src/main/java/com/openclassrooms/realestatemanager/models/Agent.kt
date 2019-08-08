package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Agent(
        val name: String,
        val firstName: String
) {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "agent_id")
        var id: Int = 0
}