package com.openclassrooms.realestatemanager.converters

import com.openclassrooms.realestatemanager.database.converter.LocationOfInterestConverter
import com.openclassrooms.realestatemanager.models.LocationOfInterest
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationOfInterestConverterTest  {

    @Test
    fun fromLocationsOfInterest_LocationsOfInterestToString() {
        assertEquals(2,
                LocationOfInterestConverter
                        .fromLocationOfInterest(LocationOfInterest.PARK))
    }

    @Test
    fun toLocationsOfInterest_StringToLocationsOfInterest() {
        assertEquals(LocationOfInterest.COMMERCES,
                LocationOfInterestConverter.toLocationOfInterest(1))
    }

}