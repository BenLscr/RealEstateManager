package com.openclassrooms.realestatemanager.models

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.database.converter.WordingConverter

@Entity
class PropertyPhoto(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "property_photo_id")
        var id: Int = 0,
        val name: String,
        val wording: Wording,
        val isThisTheIllustration: Boolean
) {
        companion object {
                fun fromContentValues(values: ContentValues): PropertyPhoto =
                        PropertyPhoto(
                                name = values.getAsString("name"),
                                wording = WordingConverter.toWording(values.getAsInteger("wording")),
                                isThisTheIllustration = values.getAsBoolean("isThisTheIllustration")
                        )
        }
}