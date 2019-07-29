package com.openclassrooms.realestatemanager.converters

import com.openclassrooms.realestatemanager.database.converter.TypeConverter
import com.openclassrooms.realestatemanager.models.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class TypeConverterTest {

    @Test
    fun fromType_TypeToInt() {
        assertEquals(4 , TypeConverter.fromType(Type.HOUSE))
    }

    @Test
    fun toType_IntToType() {
        assertEquals(Type.DUPLEX, TypeConverter.toType(3))
    }

}