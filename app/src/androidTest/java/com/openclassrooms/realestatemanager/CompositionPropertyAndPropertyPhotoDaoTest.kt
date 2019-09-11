package com.openclassrooms.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndPropertyPhoto
import com.openclassrooms.realestatemanager.models.PropertyPhoto
import com.openclassrooms.realestatemanager.models.Wording
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4ClassRunner::class)
class CompositionPropertyAndPropertyPhotoDaoTest {

    private lateinit var db: AppDatabase
    private val propertyPhotoId = 89
    private val propertyId = 65

    private val composition1 = CompositionPropertyAndPropertyPhoto(propertyId, propertyPhotoId)
    private val composition2 = CompositionPropertyAndPropertyPhoto(propertyId, 90)
    private val composition3 = CompositionPropertyAndPropertyPhoto(propertyId, 91)

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
                context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        insertManyPropertyPhotoAndComposition()
    }

    private fun insertManyPropertyPhotoAndComposition() {
        val propertyPhoto1 = PropertyPhoto(
                id = propertyPhotoId,
                name = "0.jpg",
                wording = Wording.BALCONY,
                isThisTheIllustration = true
        )
        db.propertyPhotoDao().insertPropertyPhoto(propertyPhoto1)

        val propertyPhoto2 = PropertyPhoto(
                id = 90,
                name = "1.jpg",
                wording = Wording.KITCHEN,
                isThisTheIllustration = false
        )
        db.propertyPhotoDao().insertPropertyPhoto(propertyPhoto2)

        val propertyPhoto3 = PropertyPhoto(
                id = 91,
                name = "2.jpg",
                wording = Wording.BEDROOM,
                isThisTheIllustration = false
        )
        db.propertyPhotoDao().insertPropertyPhoto(propertyPhoto3)

        db.compositionPropertyAndPropertyPhotoDao().insertPropertyPhoto(composition1)
        db.compositionPropertyAndPropertyPhotoDao().insertPropertyPhoto(composition2)
        db.compositionPropertyAndPropertyPhotoDao().insertPropertyPhoto(composition3)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCompositionFromDatabase() {
        val compositions = LiveDataTestUtil.getValue(db.compositionPropertyAndPropertyPhotoDao().getPropertyPhotos(propertyId))

        assertTrue(compositions?.size == 3
                && compositions[0].propertyId == composition1.propertyId
                && compositions[0].propertyPhotoId == composition1.propertyPhotoId
                && compositions[0].propertyPhoto?.wording == Wording.BALCONY)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetIllustrationFromDatabase() {
        val composition = LiveDataTestUtil.getValue(db.compositionPropertyAndPropertyPhotoDao().getPropertyIllustration(propertyId, true))

        assertTrue(composition!!.propertyPhoto!!.isThisTheIllustration)
    }

    @Test
    @Throws(Exception::class)
    fun deleteCompositionFromDatabase() {
        db.compositionPropertyAndPropertyPhotoDao().deletePropertyPhoto(propertyId, propertyPhotoId)
        val compositions = LiveDataTestUtil.getValue(db.compositionPropertyAndPropertyPhotoDao().getPropertyPhotos(propertyId))

        assertTrue(compositions?.size == 2)
    }

}