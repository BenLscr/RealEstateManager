package com.openclassrooms.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndLocationOfInterest
import com.openclassrooms.realestatemanager.models.LocationOfInterest
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
class CompositionPropertyAndLocationOfInterestDaoTest {

    private lateinit var db: AppDatabase
    private val propertyId = 65
    private val locationOfInterestId = LocationOfInterest.PARK.ordinal
    private val composition = CompositionPropertyAndLocationOfInterest(propertyId, locationOfInterestId)

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
                context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        db.compositionPropertyAndLocationOfInterestDao().insertLocationOfInterest(composition)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCompositionFromDatabase() {
        val compositions = LiveDataTestUtil.getValue(db.compositionPropertyAndLocationOfInterestDao().getLocationsOfInterest(propertyId))

        assertTrue(compositions?.get(0)?.propertyId == composition.propertyId && compositions[0].locationOfInterestId == composition.locationOfInterestId )
    }

    @Test
    @Throws(Exception::class)
    fun insertAndDeleteCompositionFromDatabase() {
        db.compositionPropertyAndLocationOfInterestDao().deleteLocationOfInterest(propertyId, locationOfInterestId)
        val compositions = LiveDataTestUtil.getValue(db.compositionPropertyAndLocationOfInterestDao().getLocationsOfInterest(propertyId))

        assertTrue(compositions.isNullOrEmpty())
    }

}