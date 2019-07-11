package com.openclassrooms.realestatemanager.converters

import com.openclassrooms.realestatemanager.database.LocationsOfInterestConverter
import com.openclassrooms.realestatemanager.models.LocationOfInterest
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationsOfInterestConverterTest  {

    @Test
    fun fromLocationsOfInterest_LocationsOfInterestToString() {
        assertEquals("SCHOOL, PARK",
                LocationsOfInterestConverter
                        .fromLocationsOfInterest(listOf(LocationOfInterest.SCHOOL,
                                LocationOfInterest.PARK)))
    }

    @Test
    fun toLocationsOfInterest_StringToLocationsOfInterest() {
        assertEquals(listOf(LocationOfInterest.SCHOOL, LocationOfInterest.PARK),
                LocationsOfInterestConverter.toLocationsOfInterest("0,2"))
    }

}