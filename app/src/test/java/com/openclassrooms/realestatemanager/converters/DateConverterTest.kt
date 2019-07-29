package com.openclassrooms.realestatemanager.converters

import com.openclassrooms.realestatemanager.database.converter.DateConverter
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateConverterTest {

    @Test
    fun fromDate_DistrictToInt() {
        assertEquals(156748945612 , DateConverter.fromDate(Date(156748945612)))
    }

    @Test
    fun toDate_IntToDistrict() {
        assertEquals(Date(1156489789561), DateConverter.toDate(1156489789561))
    }

}