package com.openclassrooms.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.models.Address
import com.openclassrooms.realestatemanager.models.City
import com.openclassrooms.realestatemanager.models.Country
import com.openclassrooms.realestatemanager.models.District
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
class AddressDaoTest {

    private lateinit var db: AppDatabase

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
                context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /*@Test
    @Throws(Exception::class)
    fun insertAndGetAddress() {
        val USER_ID = 3
        val address = Address(
                id = USER_ID,
                path = "451, rue Robert Lefranc",
                complement = null,
                district = District.STATEN_ISLAND,
                city = City.NEW_YORK,
                postalCode = "76510",
                country = Country.UNITED_STATES)
        db.addressDao().insertAddress(address)

        val getAddress: Address? = LiveDataTestUtil.getValue(db.addressDao().getAddress(USER_ID))

        assertTrue(getAddress?.path == "451, rue Robert Lefranc" && getAddress.id == USER_ID)
        //assertThat(getAddress?.path, equalTo(address.path))
    }*/

}