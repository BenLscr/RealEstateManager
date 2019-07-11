package com.openclassrooms.realestatemanager.converters

import com.openclassrooms.realestatemanager.database.DistrictConverter
import com.openclassrooms.realestatemanager.models.District
import org.junit.Assert.assertEquals
import org.junit.Test

class DistrictConverterTest {

    @Test
    fun fromDistrict_DistrictToInt() {
        assertEquals(2 , DistrictConverter.fromDistrict(District.MANHATTAN))
    }

    @Test
    fun toDistrict_IntToDistrict() {
        assertEquals(District.QUEENS, DistrictConverter.toDistrict(3))
    }

}