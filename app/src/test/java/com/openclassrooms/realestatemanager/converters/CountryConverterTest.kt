package com.openclassrooms.realestatemanager.converters

import com.openclassrooms.realestatemanager.database.CountryConverter
import com.openclassrooms.realestatemanager.models.Country
import org.junit.Assert.assertEquals
import org.junit.Test

class CountryConverterTest {

    @Test
    fun fromDistrict_DistrictToInt() {
        assertEquals(0 , CountryConverter.fromCountry(Country.UNITED_STATES))
    }

    @Test
    fun toDistrict_IntToDistrict() {
        assertEquals(Country.UNITED_STATES, CountryConverter.toCountry(0))
    }

}