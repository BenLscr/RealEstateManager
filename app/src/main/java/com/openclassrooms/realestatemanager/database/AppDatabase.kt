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
        private const val POSTAL_CODE = "postalCode"
        private const val COUNTRY = "country"

        private const val TYPE = "type"
        private const val PRICE = "price"
        private const val SURFACE = "surface"
        private const val ROOMS = "rooms"
        private const val BEDROOMS = "bedrooms"
        private const val BATHROOMS = "bathrooms"
        private const val GARAGE = "garage"
        private const val DESCRIPTION = "description"
        private const val ADDRESSID = "addressId"
        private const val LOCATIONS_OF_INTEREST = "locationsOfInterest"
        private const val STATUS = "status"
        private const val AVAILABLE_SINCE = "availableSince"
        private const val SALE_DATE = "saleDate"
        private const val AGENT = "agent"

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
                    postalCode = "NY 10306"
            )

            buildFakeProperty(
                    type = TypeConverter.fromType(Type.HOUSE),
                    price = "$895,000",
                    surface = 2000,
                    rooms = 8,
                    bedrooms = 4,
                    bathrooms = 2,
                    garage = true,
                    description = "One of a kind Chateau style colonial nestled in prestigious Lighthouse Hill. This 4 bedroom, 2 bath has balconies off of 3 of the bedrooms and sitting patio off of the living room. Creating charm and warmth throughout with oak floors and fireplace as focal point in the living room. New kitchen and baths, all exterior updated including pavers in yard leaving the home maintenance free. Views of harbor, walk to the Light House and golf course.",
                    addressId = 1,
                    locationsOfInterest = LocationsOfInterestConverter.fromLocationsOfInterest(listOf(LocationOfInterest.SCHOOL, LocationOfInterest.PARK)),
                    status = StatusConverter.fromStatus(Status.AVAILABLE),
                    availableSince = DateConverter.fromDate(Date(1549574288)),
                    agent = AgentConverter.fromAgent(Agent.CAROL_DENVERS)
            )

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
            contentValuesAddress.put(POSTAL_CODE, postalCode)
            contentValuesAddress.put(COUNTRY, country)
        }

        private fun buildFakeProperty(type: Int,
                                     price: String,
                                     surface: Int,
                                     rooms: Int,
                                     bedrooms: Int,
                                     bathrooms: Int,
                                     garage: Boolean,
                                     description: String,
                                     addressId: Int,
                                     locationsOfInterest: String,
                                     status: Int,
                                     availableSince: Long,
                                     saleDate: String? = null,
                                     agent: Int) {
            contentValuesProperty.put(TYPE, type)
            contentValuesProperty.put(PRICE, price)
            contentValuesProperty.put(SURFACE, surface)
            contentValuesProperty.put(ROOMS, rooms)
            contentValuesProperty.put(BEDROOMS, bedrooms)
            contentValuesProperty.put(BATHROOMS, bathrooms)
            contentValuesProperty.put(GARAGE, garage)
            contentValuesProperty.put(DESCRIPTION, description)
            //contentValuesProperty.put("images", )
            contentValuesProperty.put(ADDRESSID, addressId)
            contentValuesProperty.put(LOCATIONS_OF_INTEREST, locationsOfInterest)
            contentValuesProperty.put(STATUS, status)
            contentValuesProperty.put(AVAILABLE_SINCE, availableSince)
            contentValuesProperty.putNull(SALE_DATE)
            contentValuesProperty.put(AGENT, agent)
        }

    }

}