package com.openclassrooms.realestatemanager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToLong

/**
 * Created by Philippe on 21/02/2018.
 */

object Utils {

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    val todayDate: String
        get() {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return dateFormat.format(Date())
        }

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    fun convertDollarToEuro(dollars: Int): Int {
        return (dollars * 0.812).roundToLong().toInt()
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return  networkInfo != null && networkInfo.isConnected
    }

    /**
     * Convert a price of a property (Euros to Dollars)
     */
    fun convertEuroToDollar(euros: Int): Int {
        return (euros * 1.232).roundToLong().toInt()
    }

    /**
     * Return an image (Bitmap) from /data/user/0/com.openclassrooms.realestatemanager/files
     * @path is the name of the directory
     * @name is the name of the file as to be selected
     */
    fun getInternalBitmap(path: String?, name: String?, context: Context?): Bitmap {
        val folder = File(context?.filesDir, path)
        val file = File(folder, name)
        return BitmapFactory.decodeStream(FileInputStream(file))
    }

    /**
     * Save an image (Bitmap) in /data/user/0/com.openclassrooms.realestatemanager/files
     * @path is the name of the directory
     * @name is the name of the file as to be selected
     */
    fun setInternalBitmap(photo: Bitmap?, path: String, name: String, context: Context?) {
        val folder = File(context?.filesDir, path)
        val file = File(folder, name)
        file.parentFile.mkdirs()
        val fos = FileOutputStream(file)
        try {
            photo?.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
