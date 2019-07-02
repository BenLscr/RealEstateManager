package com.openclassrooms.realestatemanager

import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class UtilsInstrumentedTest {

    @Test
    fun checkIf_InternetIsAvailable() {
        assertEquals(true, Utils.isInternetAvailable(ApplicationProvider.getApplicationContext<HomeActivity>()))
    }

}
