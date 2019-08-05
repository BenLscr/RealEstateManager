package com.openclassrooms.realestatemanager.models

import androidx.room.Entity

@Entity(primaryKeys = [ "propertyId", "locationOfInterestId" ])
class CompositionPropertyAndLocationOfInterest(
        val propertyId: Int,
        val locationOfInterestId: Int
)