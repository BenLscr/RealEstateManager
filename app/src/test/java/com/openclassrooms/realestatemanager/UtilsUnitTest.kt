package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.models.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class UtilsUnitTest {

    @Test
    fun convert_dollarToEuro_isCorrect() {
        assertEquals(81, Utils.convertDollarToEuro(100))
    }

    @Test
    fun todayDate_isInACorrectFormat() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val todayDate = dateFormat.format(Date())
        assertEquals(todayDate, Utils.todayDate())
    }

    @Test
    fun convert_euroToDollar_isCorrect() {
        assertEquals(100, Utils.convertEuroToDollar(81))
    }

    @Test
    fun convert_stringToDistrict_isCorrect() {
        assertEquals(District.MANHATTAN, Utils.fromStringToDistrict("Manhattan"))
    }

    @Test
    fun convert_stringToCity_isCorrect() {
        assertEquals(City.NEW_YORK, Utils.fromStringToCity("New York"))
    }

    @Test
    fun convert_stringToCountry_isCorrect() {
        assertEquals(Country.UNITED_STATES, Utils.fromStringToCountry("United States"))
    }

    @Test
    fun convert_stringToType_isCorrect() {
        assertEquals(Type.DUPLEX, Utils.fromStringToType("Duplex"))
    }

    @Test
    fun convert_stringToAgentId_isCorrect() {
        assertEquals(3, Utils.fromStringToAgentId("Steve Rogers"))
    }

    @Test
    fun convert_stringToWording_isCorrect() {
        assertEquals(Wording.KITCHEN, Utils.fromStringToWording("Kitchen"))
    }

    @Test
    fun convert_typeToString_isCorrect() {
        assertEquals("Flat", Utils.fromTypeToString(Type.FLAT))
    }

    @Test
    fun convert_districtToString_isCorrect() {
        assertEquals("Brooklyn", Utils.fromDistrictToString(District.BROOKLYN))
    }

    @Test
    fun convert_cityToString_isCorrect() {
        assertEquals("New York", Utils.fromCityToString(City.NEW_YORK))
    }

    @Test
    fun convert_countryToString_isCorrect() {
        assertEquals("United States", Utils.fromCountryToString(Country.UNITED_STATES))
    }

    @Test
    fun convert_agentIdToString_isCorrect() {
        assertEquals("Natasha Romanoff", Utils.fromAgentIdToString(4))
    }

    @Test
    fun convert_surfaceToString_isCorrect() {
        assertEquals("4500sq ft", Utils.fromSurfaceToString(4500))
    }

    @Test
    fun convert_agentToString_isCorrect() {
        assertEquals("Natasha Romanoff", Utils.fromAgentToString("Natasha", "Romanoff"))
    }

    @Test
    fun convert_wordingToString_isCorrect() {
        assertEquals("Cinema", Utils.fromWordingToString(Wording.CINEMA))
    }

    @Test
    fun createNamePhoto_isCorrect() {
        assertEquals("55.jpg", Utils.createNamePhoto(55))
    }

}