package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.database.converter.*
import com.openclassrooms.realestatemanager.models.City
import com.openclassrooms.realestatemanager.models.Country
import com.openclassrooms.realestatemanager.models.District
import com.openclassrooms.realestatemanager.models.Type
import com.openclassrooms.realestatemanager.provider.AppContentProvider
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class AppContentProviderTest {

    private var db: AppDatabase? = null
    private var mContentResolver: ContentResolver? = null
    private val PROPERTY_ID: Long = 11

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
                AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        mContentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }

    @Test
    fun insertAndGetProperty() {
        // BEFORE
        mContentResolver!!.insert(AppContentProvider.URI_ADDRESS, generateAddress())
        mContentResolver!!.insert(AppContentProvider.URI_AGENT, generateAgent())
        val propertyUri = mContentResolver!!.insert(AppContentProvider.URI_PROPERTY, generateProperty())
        // TEST
        val cursor = mContentResolver!!.query(ContentUris.withAppendedId(AppContentProvider.URI_PROPERTY, PROPERTY_ID), null, null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(1))
        assertThat(cursor.moveToFirst(), `is`(true))
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("description")), `is`("Fake description of a property."))
        cursor.close()
    }

    private fun generateAddress(): ContentValues {
        val values = ContentValues()
        values.put("name", "fake name")
        values.put("firstName", "fake firstName")
        return values
    }

    private fun generateAgent(): ContentValues {
        val values = ContentValues()
        values.put("path", "fake path")
        values.putNull("complement")
        values.put("district", DistrictConverter.fromDistrict(District.STATEN_ISLAND))
        values.put("city", CityConverter.fromCity(City.NEW_YORK))
        values.put("postalCode", "fake postalCode")
        values.put("country", CountryConverter.fromCountry(Country.UNITED_STATES))
        return values
    }

    private fun generateProperty(): ContentValues {
        val values = ContentValues()
        values.put("type", TypeConverter.fromType(Type.HOUSE))
        values.put("price", 1500000.toLong())
        values.put("surface", 425)
        values.put("rooms", 5)
        values.put("bedrooms", 2)
        values.put("bathrooms", 2)
        values.put("description", "Fake description of a property.")
        values.put("addressId", 1)
        values.put("available", true)
        values.put("entryDate", DateConverter.fromDate(Date(1549574288000)))
        values.putNull("saleDate")
        values.put("agentId", 1)
        return values
    }

}