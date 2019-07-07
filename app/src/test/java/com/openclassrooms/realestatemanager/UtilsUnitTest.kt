package com.openclassrooms.realestatemanager

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class UtilsUnitTest {

    @Test
    fun convert_euroToDollar_isCorrect() {
        assertEquals(100, Utils.convertEuroToDollar(81))
    }

    @Test
    fun todayDate_isInACorrectFormat() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val todayDate = dateFormat.format(Date()) // MOCK -> DATE
        assertEquals(todayDate, Utils.todayDate)
    }
}