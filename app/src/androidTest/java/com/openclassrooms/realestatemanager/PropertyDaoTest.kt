package com.openclassrooms.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.models.*
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
class PropertyDaoTest {

    private lateinit var db: AppDatabase
    private val propertyId = 99
    private val address = Address(
            id = 99,
            path = "address test",
            complement = null,
            district = District.MANHATTAN,
            city = City.NEW_YORK,
            postalCode = "postalCode test",
            country = Country.UNITED_STATES)
    private val agent = Agent(
            id = 99,
            name = "name test",
            firstName = "firstName test"
    )
    private val property = Property(
            id = propertyId,
            type = Type.FLAT,
            price = 999999.toLong(),
            surface = 1234,
            rooms = 8,
            bedrooms = 5,
            bathrooms = 4,
            description = "description test of property",
            addressId = 99,
            available = true,
            entryDate = 123456789.toLong(),
            saleDate = null,
            agentId = 99
    )

    private val updatedProperty = Property(
            id = propertyId,
            type = Type.FLAT,
            price = 999999.toLong(),
            surface = 1234,
            rooms = 8,
            bedrooms = 5,
            bathrooms = 4,
            description = "description test of updatedProperty",
            addressId = 99,
            available = true,
            entryDate = 123456789.toLong(),
            saleDate = null,
            agentId = 99
    )

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
                context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        db.addressDao().insertAddress(address)
        db.agentDao().insertAgent(agent)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPropertyFromDatabase() {
        db.propertyDao().insertProperty(property)
        val propertyFromDatabase = LiveDataTestUtil.getValue(db.propertyDao().getProperty(propertyId))

        assertTrue(propertyFromDatabase?.available == property.available && propertyFromDatabase.description == property.description)
    }

    @Test
    @Throws(Exception::class)
    fun insertUpdateAndGetPropertyFromDatabase() {
        db.propertyDao().insertProperty(property)
        db.propertyDao().updateProperty(updatedProperty)
        val propertyFromDatabase = LiveDataTestUtil.getValue(db.propertyDao().getProperty(propertyId))

        assertTrue(propertyFromDatabase?.description == updatedProperty.description)
    }

}