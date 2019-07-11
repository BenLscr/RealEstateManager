package com.openclassrooms.realestatemanager.converters

import com.openclassrooms.realestatemanager.database.StatusConverter
import com.openclassrooms.realestatemanager.models.Status
import org.junit.Assert.assertEquals
import org.junit.Test

class StatusConverterTest {

    @Test
    fun fromStatus_StatusToInt() {
        assertEquals(1 , StatusConverter.fromStatus(Status.SOLD))
    }

    @Test
    fun toStatus_IntToStatus() {
        assertEquals(Status.SOLD, StatusConverter.toStatus(1))
    }

}