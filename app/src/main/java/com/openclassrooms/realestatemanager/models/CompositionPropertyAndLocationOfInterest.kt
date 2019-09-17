package com.openclassrooms.realestatemanager.models

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = [ "propertyId", "locationOfInterestId" ])
class CompositionPropertyAndLocationOfInterest(
        val propertyId: Int,
        val locationOfInterestId: Int
) {
    companion object {
        fun fromContentValues(values: ContentValues): CompositionPropertyAndLocationOfInterest =
                CompositionPropertyAndLocationOfInterest(
                        propertyId = values.getAsInteger("propertyId"),
                        locationOfInterestId = values.getAsInteger("locationOfInterestId")
                )
    }
}