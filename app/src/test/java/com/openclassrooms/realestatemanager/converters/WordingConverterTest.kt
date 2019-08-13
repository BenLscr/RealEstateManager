package com.openclassrooms.realestatemanager.converters

import com.openclassrooms.realestatemanager.database.converter.WordingConverter
import com.openclassrooms.realestatemanager.models.Wording
import org.junit.Assert.assertEquals
import org.junit.Test

class WordingConverterTest {

    @Test
    fun fromWording_toInt() {
        assertEquals(7, WordingConverter.fromWording(Wording.BEDROOM))
    }

    @Test
    fun toWording_IntToWording() {
        assertEquals(Wording.ROOF_TOP, WordingConverter.toWording(11))
    }

}