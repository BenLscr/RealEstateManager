package com.openclassrooms.realestatemanager.database

import android.content.Context
import com.openclassrooms.realestatemanager.database.dao.AddressDao
import com.openclassrooms.realestatemanager.database.dao.PropertyDao
import android.content.ContentValues
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.models.*
import java.util.*


@Database(entities = [Property::class, Address::class] , version = 1, exportSchema = false)
@TypeConverters(AgentConverter::class,
        CityConverter::class,
        CountryConverter::class,
        DateConverter::class,
        DistrictConverter::class,
        LocationsOfInterestConverter::class,
        StatusConverter::class,
        TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun propertyDao(): PropertyDao
    abstract fun addressDao(): AddressDao

    /**
     * VERSION from LESSON
     */
    /*companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, "Database.db")
                                .addCallback(prepopulateDatabase())
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }*/


    /**
     * VERSION from BLOG KOTLIN
     */
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        private val contentValuesAddress = ContentValues()
        private val contentValuesProperty = ContentValues()

        private const val PATH = "path"
        private const val COMPLEMENT = "complement"
        private const val DISTRICT = "district"
        private const val CITY = "city"
        private const val POSTALCODE = "postalCode"
        private const val COUNTRY = "country"

        /**fun getInstance(context: Context): AppDatabase {
            INSTANCE ?: synchronized(Any()) {
                INSTANCE ?: Room.databaseBuilder(context, AppDatabase::class.java, "Database.db")
                        .addCallback(prepopulateDatabase())
                        .build()
            }
            return INSTANCE!!
        }*/

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, "Database.db")
                                .addCallback(prepopulateDatabase())
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }

        private fun prepopulateDatabase(): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    firstProperty(db)
                }
            }
        }

        private fun insertValue(db: SupportSQLiteDatabase) {
            db.insert("Address", OnConflictStrategy.IGNORE, contentValuesAddress)
            db.insert("Property", OnConflictStrategy.IGNORE, contentValuesProperty)
        }

        private fun firstProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                    path = "311 Edinboro Rd",
                    district = DistrictConverter.fromDistrict(District.STATEN_ISLAND),
                    postalCode = "NY 10306")

            //buildFakeProperty(db)
            contentValuesProperty.put("type", TypeConverter.fromType(Type.HOUSE))
            contentValuesProperty.put("price", "$895,000")
            contentValuesProperty.put("surface", 2000)
            contentValuesProperty.put("rooms", 8)
            contentValuesProperty.put("bedrooms", 4)
            contentValuesProperty.put("bathrooms", 2)
            contentValuesProperty.put("garage", true)
            contentValuesProperty.put("description", "One of a kind Chateau style colonial nestled in prestigious Lighthouse Hill. This 4 bedroom, 2 bath has balconies off of 3 of the bedrooms and sitting patio off of the living room. Creating charm and warmth throughout with oak floors and fireplace as focal point in the living room. New kitchen and baths, all exterior updated including pavers in yard leaving the home maintenance free. Views of harbor, walk to the Light House and golf course.")
            //contentValuesProperty.put("images", )
            contentValuesProperty.put("addressId", 1)
            contentValuesProperty.put("locationsOfinterest", LocationsOfInterestConverter.fromLocationsOfInterest(listOf(LocationOfInterest.SCHOOL, LocationOfInterest.PARK)))
            contentValuesProperty.put("status", StatusConverter.fromStatus(Status.AVAILABLE))
            contentValuesProperty.put("availableSince", DateConverter.fromDate(Date(1549574288)))
            contentValuesProperty.putNull("saleDate")
            contentValuesProperty.put("agent", AgentConverter.fromAgent(Agent.CAROL_DENVERS))

            insertValue(db)
        }

        private fun buildFakeAddress(path: String,
                                     complement: String? = null,
                                     district: Int,
                                     city: Int = CityConverter.fromCity(City.NEW_YORK),
                                     postalCode: String,
                                     country: Int = CountryConverter.fromCountry(Country.UNITED_STATES)) {
            contentValuesAddress.put(PATH, path)
            contentValuesAddress.putNull(COMPLEMENT)
            contentValuesAddress.put(DISTRICT, district)
            contentValuesAddress.put(CITY, city)
            contentValuesAddress.put(POSTALCODE, postalCode)
            contentValuesAddress.put(COUNTRY, country)
        }

    }

}