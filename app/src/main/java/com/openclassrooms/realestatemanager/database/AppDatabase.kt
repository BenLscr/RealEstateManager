package com.openclassrooms.realestatemanager.database

import android.content.Context
import com.openclassrooms.realestatemanager.database.dao.AddressDao
import com.openclassrooms.realestatemanager.database.dao.PropertyDao
import android.content.ContentValues
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.models.*


@Database(entities = [Property::class, Address::class] , version = 1, exportSchema = false)
@TypeConverters(DistrictConverter::class)
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

        fun getInstance(context: Context): AppDatabase {
            INSTANCE ?: synchronized(Any()) {
                INSTANCE ?: Room.databaseBuilder(context, AppDatabase::class.java, "Database.db")
                        .addCallback(prepopulatedDatabase())
                        .build()
            }
            return INSTANCE!!
        }

        private fun prepopulatedDatabase(): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    firstProperty(db)
                }
            }
        }

        private fun insert(db: SupportSQLiteDatabase) {
            db.insert("Address", OnConflictStrategy.IGNORE, contentValuesAddress)
            db.insert("Property", OnConflictStrategy.IGNORE, contentValuesProperty)
        }
        private fun firstProperty(db: SupportSQLiteDatabase) {
            contentValuesAddress.put("path", "311 Edinboro Rd")
            contentValuesAddress.putNull("complement")
            contentValuesAddress.put("district", DistrictConverter.fromDistrict(District.STATEN_ISLAND))
            contentValuesAddress.put("city", CityConverter.fromCity(City.NEW_YORK))
            contentValuesAddress.put("postalCode", "NY 10306")
            contentValuesAddress.put("country", CountryConverter.fromCountry(Country.UNITED_STATES))

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
            //contentValuesProperty.put("availableSince", )
            contentValuesProperty.putNull("saleDate")
            contentValuesProperty.put("agent", AgentConverter.fromAgent(Agent.CAROL_DENVERS))

            insert(db)
        }

    }

}