package com.openclassrooms.realestatemanager

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun convert_euroToDollar_isCorrect() {
        assertEquals(100, Utils.convertEuroToDollar(81))
    }

    @Test
    fun todayDate_isInACorrectFormat() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val todayDate = dateFormat.format(Date())
        assertEquals(todayDate, Utils.todayDate)
    }
}