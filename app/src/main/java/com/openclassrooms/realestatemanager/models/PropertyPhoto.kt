package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PropertyPhoto(
        val name: String,
        val wording: Wording,
        val isThisTheIllustration: Boolean
) {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "property_photo_id")
        var id: Int = 0
}