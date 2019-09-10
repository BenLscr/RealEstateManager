package com.openclassrooms.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.models.Address
import com.openclassrooms.realestatemanager.models.City
import com.openclassrooms.realestatemanager.models.Country
import com.openclassrooms.realestatemanager.models.District
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
        db.addressDao().insertAddress(Address(
                "451, rue Robert Lefranc",
                null,
                District.STATEN_ISLAND,
                City.NEW_YORK,
                "76510",
                Country.UNITED_STATES))

        val address = LiveDataTestUtil.getValue(db.addressDao().getAddress(3))

        assert(address!!.path == "451, rue Robert Lefranc" && address.id == 3)
        //assertThat(address, equalTo(UneAddress))
    }*/
}