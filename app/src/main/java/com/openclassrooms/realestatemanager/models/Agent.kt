package com.openclassrooms.realestatemanager.models

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Agent(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "agent_id")
        var id: Int = 0,
        val name: String,
        val firstName: String
) {
        companion object {
                fun fromContentValues(values: ContentValues): Agent =
                        Agent(
                                name = values.getAsString("name"),
                                firstName = values.getAsString("firstName")
                        )
        }
}