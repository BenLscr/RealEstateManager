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

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
                context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        insertManyPropertyPhoto()
    }

    private fun insertManyPropertyPhoto() {
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
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCompositionFromDatabase() {
        val propertyId = 65
        val composition = CompositionPropertyAndPropertyPhoto(propertyId, propertyPhotoId)
        db.compositionPropertyAndPropertyPhotoDao().insertPropertyPhoto(composition)
        val compositions = LiveDataTestUtil.getValue(db.compositionPropertyAndPropertyPhotoDao().getPropertyPhotos(propertyId))

        assertTrue(compositions?.get(0)?.propertyId == composition.propertyId
                && compositions[0].propertyPhotoId == composition.propertyPhotoId
                && compositions[0].propertyPhoto?.wording == Wording.BALCONY)
    }

}