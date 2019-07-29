package com.openclassrooms.realestatemanager.converters

import com.openclassrooms.realestatemanager.database.converter.CityConverter
import com.openclassrooms.realestatemanager.models.City
import org.junit.Assert.assertEquals
import org.junit.Test

class CityConverterTest {

    @Test
    fun fromCity_CityToInt() {
        assertEquals(0 , CityConverter.fromCity(City.NEW_YORK))
    }

    @Test
    fun toCity_IntToCity() {
        assertEquals(City.NEW_YORK, CityConverter.toCity(0))
    }

}