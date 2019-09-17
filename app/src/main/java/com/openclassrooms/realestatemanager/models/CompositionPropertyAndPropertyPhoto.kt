package com.openclassrooms.realestatemanager.models

import android.content.ContentValues
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = [ "propertyId", "propertyPhotoId" ],
        foreignKeys = [ForeignKey(entity = PropertyPhoto::class, parentColumns = ["property_photo_id"], childColumns = ["propertyPhotoId"])])
class CompositionPropertyAndPropertyPhoto(
        val propertyId: Int,
        val propertyPhotoId: Int
) {
    @Embedded var propertyPhoto: PropertyPhoto? = null

    companion object {
        fun fromContentValues(values: ContentValues): CompositionPropertyAndPropertyPhoto =
                CompositionPropertyAndPropertyPhoto(
                        propertyId = values.getAsInteger("propertyId"),
                        propertyPhotoId = values.getAsInteger("propertyPhotoId")
                )
    }
}