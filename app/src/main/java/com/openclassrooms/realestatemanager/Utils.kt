package com.openclassrooms.realestatemanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.openclassrooms.realestatemanager.models.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToLong


/**
 * Created by Philippe on 21/02/2018.
 */

class Utils {

    companion object {

        private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        /**
         * Conversion de la date d'aujourd'hui en un format plus approprié
         * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
         * @return
         */
        fun todayDate(): String = dateFormat.format(Date())

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
         * @propertyId is the name of the directory
         * @name is the name of the file as to be selected
         */
        fun getInternalBitmap(propertyId: String?, name: String?, context: Context?): Bitmap {
            val folder = File(context?.filesDir, propertyId)
            val file = File(folder, name)
            return if (file.exists()) {
                BitmapFactory.decodeStream(FileInputStream(file))
            } else {
                getBitmap(R.drawable.baseline_photo_24, context)
            }
        }

        private fun getBitmap(drawableRes: Int, context: Context?): Bitmap {
            val drawable: Drawable = context?.resources!!.getDrawable(drawableRes)
            val canvas = Canvas()
            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            canvas.setBitmap(bitmap)
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            drawable.draw(canvas)
            return bitmap
        }

        /**
         * Save an image (Bitmap) in /data/user/0/com.openclassrooms.realestatemanager/files
         * @propertyId is the name of the directory
         * @name is the name of the file as to be selected
         */
        fun setInternalBitmap(photo: Bitmap?, propertyId: String, name: String, context: Context?) {
            val folder = File(context?.filesDir, propertyId)
            val file = File(folder, name)
            file.parentFile.mkdirs()
            val fos = FileOutputStream(file)
            try {
                photo?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
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

        /**
         * Delete an image (Bitmap) in /data/user/0/com.openclassrooms.realestatemanager/files
         * @propertyId is the name of the directory
         * @name is the name of the file as to be deleted
         */
        fun deleteInternalBitmap(propertyId: String, name: String?, context: Context) {
            val folder = File(context.filesDir, propertyId)
            val file = File(folder, name)
            file.delete()
        }

        /**
         * Send a notification. Depends of the SKD a channel can be required.
         */
        fun sendNotification(context: Context, somethingHappened: String?) {
            val CHANNEL_ID = "CHANNEL_ID"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = context.getString(R.string.channel_name)
                val descriptionText = context.getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("RealEstateManager")
                    .setContentText(somethingHappened)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            NotificationManagerCompat.from(context).notify(0, builder.build())
        }

        //---TO-DATABASE---\\
        /**
         * Return a string or null depends of the string in parameter.
         */
        fun returnComplementOrNull(complement: String) = if (complement.isNotEmpty()) { complement } else { null }

        /**
         * With the name of a district, a value of District type is return.
         */
        fun fromStringToDistrict(district: String) =
                when(district) {
                    "Bronx" -> District.BRONX
                    "Brooklyn" -> District.BROOKLYN
                    "Manhattan" -> District.MANHATTAN
                    "Queens" -> District.QUEENS
                    "Staten Island" -> District.STATEN_ISLAND
                    else -> District.BRONX
                }

        /**
         * With the name of a city, a value of City type is return.
         */
        fun fromStringToCity(city: String) =
                when(city) {
                    "New York" -> City.NEW_YORK
                    else -> City.NEW_YORK
                }

        /**
         * With the name of a country, a value of Country type is return.
         */
        fun fromStringToCountry(country: String) =
                when(country) {
                    "United States" -> Country.UNITED_STATES
                    else -> Country.UNITED_STATES
                }

        /**
         * With the name of a type, a value of Type type is return.
         */
        fun fromStringToType(type: String) =
                when(type) {
                    "Flat" -> Type.FLAT
                    "Penthouse" -> Type.PENTHOUSE
                    "Mansion" -> Type.MANSION
                    "Duplex" -> Type.DUPLEX
                    "House" -> Type.HOUSE
                    "Loft" -> Type.LOFT
                    "Townhouse" -> Type.TOWNHOUSE
                    "Condo" -> Type.CONDO
                    else -> Type.FLAT
                }

        /**
         * With the full name of an Agent, an integer return. This integer is the id of the Agent table.
         */
        fun fromStringToAgentId(fullNameAgent: String) =
                when(fullNameAgent) {
                    "Tony Stark" -> 1
                    "Peter Parker" -> 2
                    "Steve Rogers" -> 3
                    "Natasha Romanoff" -> 4
                    "Bruce Banner" -> 5
                    "Clinton Barton" -> 6
                    "Carol Denvers" -> 7
                    "Wanda Maximoff" -> 8
                    else -> 1
                }

        /**
         * With the name of a wording, a value of Wording type is return.
         */
        fun fromStringToWording(wording: String?) =
                when(wording) {
                    "Street View" -> Wording.STREET_VIEW
                    "Living room" -> Wording.LIVING_ROOM
                    "Hall" -> Wording.HALL
                    "Kitchen" -> Wording.KITCHEN
                    "Dining room" -> Wording.DINING_ROOM
                    "Bathroom" -> Wording.BATHROOM
                    "Balcony" -> Wording.BALCONY
                    "Bedroom" -> Wording.BEDROOM
                    "Terrace" -> Wording.TERRACE
                    "Walk in closet" -> Wording.WALK_IN_CLOSET
                    "Office" -> Wording.OFFICE
                    "Roof top" -> Wording.ROOF_TOP
                    "plan" -> Wording.PLAN
                    "Hallway" -> Wording.HALLWAY
                    "View" -> Wording.VIEW
                    "Garage" -> Wording.GARAGE
                    "Swimming pool" -> Wording.SWIMMING_POOL
                    "Fitness centre" -> Wording.FITNESS_CENTRE
                    "Spa" -> Wording.SPA
                    "Cinema" -> Wording.CINEMA
                    "Conference" -> Wording.CONFERENCE
                    "Stairs" -> Wording.STAIRS
                    "Garden" -> Wording.GARDEN
                    "Floor" -> Wording.FLOOR
                    else -> Wording.STREET_VIEW
                }

        //---TO-UI---\\

        /**
         * With a value of Type type, his string value is return.
         */
        fun fromTypeToString(type: Type) =
                when(type) {
                    Type.PENTHOUSE -> "Penthouse"
                    Type.MANSION -> "Mansion"
                    Type.FLAT -> "Flat"
                    Type.DUPLEX -> "Duplex"
                    Type.HOUSE -> "House"
                    Type.LOFT -> "Loft"
                    Type.TOWNHOUSE -> "Townhouse"
                    Type.CONDO -> "Condo"
                }

        /**
         * With a value of District type, his string value is return.
         */
        fun fromDistrictToString(district: District?) =
                when(district) {
                    District.MANHATTAN -> "Manhattan"
                    District.BROOKLYN -> "Brooklyn"
                    District.STATEN_ISLAND -> "Staten Island"
                    District.QUEENS -> "Queens"
                    District.BRONX -> "Bronx"
                    else -> "District unknown"
                }

        /**
         * With a value of City type, his string value is return.
         */
        fun fromCityToString(city: City?) =
                when(city) {
                    City.NEW_YORK -> "New York"
                    else -> "City unknown"
                }

        /**
         * With a value of Country type, his string value is return.
         */
        fun fromCountryToString(country: Country?) =
                when(country) {
                    Country.UNITED_STATES -> "United States"
                    else -> "Country unknown"
                }

        /**
         * With an id corresponding of a Agent table, a full name is return.
         */
        fun fromAgentIdToString(agentId: Int) =
                when(agentId) {
                    1 -> "Tony Stark"
                    2 -> "Peter Parker"
                    3 -> "Steve Rogers"
                    4 -> "Natasha Romanoff"
                    5 -> "Bruce Banner"
                    6 -> "Clinton Barton"
                    7 -> "Carol Denvers"
                    8 -> "Wanda Maximoff"
                    else -> "Agent unknown"
                }

        /**
         * With a price in Long type, a string is return.
         */
        fun fromPriceToString(price: Long) = "$" + NumberFormat.getIntegerInstance().format(price)

        /**
         * With a surface in Int type, a string is return.
         */
        fun fromSurfaceToString(surface: Int?) = surface.toString() + "sq ft"

        /**
         * With a firstName and a name, a full name is return.
         */
        fun fromAgentToString(firstName: String?, name: String?) = "$firstName $name"

        /**
         * With a value of Long type, a date in string type is return.
         */
        fun fromEntryDateToString(entryDate: Long) = dateFormat.format(Date(entryDate))

        /**
         * If saleDate is null return empty string else return a date in string format.
         */
        fun fromSaleDateToString(saleDate: Long?) =
                if (saleDate != null) {
                    dateFormat.format(Date(saleDate))
                } else {
                    ""
                }

        /**
         * With a value of Wording type, his string value is return.
         */
        fun fromWordingToString(wording: Wording?) =
                when(wording) {
                    Wording.STREET_VIEW -> "Street view"
                    Wording.LIVING_ROOM -> "Living room"
                    Wording.HALL -> "Hall"
                    Wording.KITCHEN -> "Kitchen"
                    Wording.DINING_ROOM -> "Dining room"
                    Wording.BATHROOM -> "Bathroom"
                    Wording.BALCONY -> "Balcony"
                    Wording.BEDROOM -> "Bedroom"
                    Wording.TERRACE -> "Terrace"
                    Wording.WALK_IN_CLOSET -> "Walk in closet"
                    Wording.OFFICE -> "Office"
                    Wording.ROOF_TOP -> "Roof top"
                    Wording.PLAN -> "plan"
                    Wording.HALLWAY -> "Hallway"
                    Wording.VIEW -> "View"
                    Wording.GARAGE -> "Garage"
                    Wording.SWIMMING_POOL -> "Swimming pool"
                    Wording.FITNESS_CENTRE -> "Fitness centre"
                    Wording.SPA -> "Spa"
                    Wording.CINEMA -> "Cinema"
                    Wording.CONFERENCE -> "Conference"
                    Wording.STAIRS -> "Stairs"
                    Wording.GARDEN -> "Garden"
                    Wording.FLOOR -> "Floor"
                    else -> "Unknown wording"
                }

        /**
         * Build a name for photo.
         */
        fun createNamePhoto(index: Int) = "$index.jpg"

    }

}
