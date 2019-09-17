package com.openclassrooms.realestatemanager

import android.content.Context
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class UtilsInstrumentedTest {

    private var wifiManager: WifiManager? = null
    private var telephonyManager: TelephonyManager? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        wifiManager = ApplicationProvider.getApplicationContext<Context>().getSystemService(Context.WIFI_SERVICE) as WifiManager
        telephonyManager = ApplicationProvider.getApplicationContext<Context>().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    @Test
    fun checkIf_InternetIsAvailable() {
        assertEquals(true, Utils.isInternetAvailable(getApplicationContext<HomeActivity>()))
    }

    /*@Test
    fun checkIf_InternetIsAvailable_WifiDisableDataEnable() {
        if (wifiManager!!.isWifiEnabled) {
            wifiManager?.isWifiEnabled = false
        }
        if (!telephonyManager!!.isDataEnabled) {
            telephonyManager?.isDataEnabled = true
        }
        assertEquals(true, Utils.isInternetAvailable(getApplicationContext<HomeActivity>()))
    }

    @Test
    fun checkIf_InternetIsAvailable_WifiEnableDataEnable() {
        if (wifiManager!!.isWifiEnabled) {
            wifiManager?.isWifiEnabled = false
        }
        if (!telephonyManager!!.isDataEnabled) {
            telephonyManager?.isDataEnabled = true
        }
        assertEquals(true, Utils.isInternetAvailable(getApplicationContext<HomeActivity>()))
    }

    @Test
    fun checkIf_InternetIsAvailable_WifiEnableDataDisable() {
        if (!wifiManager!!.isWifiEnabled) {
            wifiManager?.isWifiEnabled = true
        }
        if (telephonyManager!!.isDataEnabled) {
            telephonyManager?.isDataEnabled = false
        }
        assertEquals(true, Utils.isInternetAvailable(getApplicationContext<HomeActivity>()))
    }

    @Test
    fun checkIf_InternetIsAvailable_WifiDisableDataDisable() {
        if (wifiManager!!.isWifiEnabled) {
            wifiManager?.isWifiEnabled = false
        }
        if (telephonyManager!!.isDataEnabled) {
            telephonyManager?.isDataEnabled = false
        }
        assertEquals(false, Utils.isInternetAvailable(getApplicationContext<HomeActivity>()))
    }*/

}