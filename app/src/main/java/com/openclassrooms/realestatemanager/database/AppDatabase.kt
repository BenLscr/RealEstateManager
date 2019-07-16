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
                    secondProperty(db)
                    thirdProperty(db)
                    fourthProperty(db)
                    fifthProperty(db)
                    //sixthProperty(db)
                    //seventhProperty(db)
                    //eighthProperty(db)
                    //ninethProperty(db)
                    //tenthProperty(db)
                }
            }
        }

        private fun buildFakeAddress(path: String,
                                     complement: String? = null,
                                     district: Int,
                                     city: Int = CityConverter.fromCity(City.NEW_YORK),
                                     postalCode: String,
                                     country: Int = CountryConverter.fromCountry(Country.UNITED_STATES)) {
            contentValuesAddress.put(PATH, path)
            if (complement == null ) {
                contentValuesAddress.putNull(COMPLEMENT)
            } else {
                contentValuesAddress.put(COMPLEMENT, complement)
            }
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
                                      garage: Boolean? = null,
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
            if (garage == null) {
                contentValuesProperty.putNull(GARAGE)
            } else {
                contentValuesProperty.put(GARAGE, garage)
            }
            contentValuesProperty.put(DESCRIPTION, description)
            //contentValuesProperty.put("images", )
            contentValuesProperty.put(ADDRESSID, addressId)
            contentValuesProperty.put(LOCATIONS_OF_INTEREST, locationsOfInterest)
            contentValuesProperty.put(STATUS, status)
            contentValuesProperty.put(AVAILABLE_SINCE, availableSince)
            if (saleDate == null) {
            } else {
                contentValuesProperty.put(SALE_DATE, saleDate)
            }
            contentValuesProperty.put(AGENT, agent)
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
                    surface = 4000,
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

        private fun secondProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                    path = "537 Court St",
                    complement = "#4A",
                    district = DistrictConverter.fromDistrict(District.BROOKLYN),
                    postalCode = "NY 11231"
            )
            buildFakeProperty(
                    type = TypeConverter.fromType(Type.TOWNHOUSE),
                    price = "$1,500,000",
                    surface = 1325,
                    rooms = 10,
                    bedrooms = 3,
                    bathrooms = 3,
                    garage = false,
                    description = "Welcome home to 537 Court Street, a gorgeous new condominium conversion at the nexus of three great neighborhoods, Carroll Gardens, Red Hook, and Gowanus. Residence 4A is a sprawling duplex penthouse residence totaling 1,325 sqft with a private 875 sqft roof terrace. This apartment has tall ceilings and a large living/dining room with a working fireplace; ideal for real entertaining. The 3 large bedrooms and bathrooms are all north facing, with ample sunlight. 537 Court Street offers gorgeous high-end finishes, superb layouts, and private outdoor space in a trendy amenity-rich neighborhood. Kitchens have stone counters, custom cabinetry, and high-end appliances. There are beautiful hardwood floors throughout and central AC and heat. Each home is equipped with a washer/dryer and video intercom. With very low common charges and taxes, and today's low mortgage rates, owning this home may well be more affordable than renting! Located on a sunny convenient corner of Court Street, your new home is incredibly conveniently located in the midst of world-class shopping and dining including both international brands and local favorites. Your new home is 2 blocks to subways, parks, and excellent schools.",
                    addressId = 2,
                    locationsOfInterest = LocationsOfInterestConverter.fromLocationsOfInterest(listOf(LocationOfInterest.SCHOOL, LocationOfInterest.COMMERCES, LocationOfInterest.PARK, LocationOfInterest.SUBWAYS)),
                    status = StatusConverter.fromStatus(Status.AVAILABLE),
                    availableSince = DateConverter.fromDate(Date(1562752237)),
                    agent = AgentConverter.fromAgent(Agent.CLINTON_BARTON)
            )
            insertValue(db)
        }

        private fun thirdProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                    path = "432 Park Ave",
                    complement = "#65A",
                    district = DistrictConverter.fromDistrict(District.MANHATTAN),
                    postalCode = "NY 10022"
            )
            buildFakeProperty(
                    type = TypeConverter.fromType(Type.CONDO),
                    price = "$29,900,000",
                    surface = 4019,
                    rooms = 7,
                    bedrooms = 3,
                    bathrooms = 5,
                    description = "Unique opportunity to own at Rafael Violy's iconic 432 Park Avenue. Entering through a private elevator vestibule, you are welcomed into this stunning 4,019 square foot home featuring 3 bedrooms, a library, 4.5 baths and 12'6' ceilings throughout. Expansive 10'x10' windows with North, South and Eastern views fill the morning with sunrise, evenings with sunset and breathtaking skyline, park and river views no matter the time of day. A gracious foyer leads to a grand 29' x 29' corner living and dining room. The windowed eat-in kitchen includes a breakfast bar overlooking Central Park with custom white lacquer and natural oak cabinetry, Blue de Savoie marble countertops and a 96' x 51' Bianco Scintillante marble center island. Miele stainless steel appliances and Dornbracht polished chrome fixtures complete this elegant kitchen. The corner master bedroom suite features light filled open Southern and Eastern views and includes spacious closets and a separate dressing area. Two windowed master bathrooms allow for complete privacy and include radiant heated marble floors and walls of book-matched slabs of Italian Statuario marble. One of the bathrooms features a freestanding soaking tub with endless views, as well as a separate shower. The two additional bedrooms each feature an ensuite bath with marble, radiant heated floors. Every inch of this apartment has been tastefully finished to the highest quality with state-of-the-art custom audio and lighting throughout. All bedrooms have been fitted with blackout shades and fully automated (LED) lighting. Living at 432 Park Avenue is comparable to living at a 5-star hotel with the luxury of not having to share it with guests. Featuring over 30,000 square feet of amenities, one never needs to leave the building. Additional services include a private restaurant, in residence dining, a 5,000 square foot outdoor terrace for dining and events, a 75-foot indoor swimming pool, gym, spa, steam room, sauna, massage/treatment room, library, billiards room, conference room with state-of-the-art teleconferencing, screening room, concierge, 24-hour doorman and security.This residence is being sold inclusive of a 595 square foot corner, fully renovated, studio suite and a storage unit.",
                    addressId = 3,
                    locationsOfInterest = LocationsOfInterestConverter.fromLocationsOfInterest(listOf(LocationOfInterest.SCHOOL, LocationOfInterest.COMMERCES, LocationOfInterest.PARK, LocationOfInterest.SUBWAYS)),
                    status = StatusConverter.fromStatus(Status.AVAILABLE),
                    availableSince = DateConverter.fromDate(Date(1562586286)),
                    agent = AgentConverter.fromAgent(Agent.TONY_STARK)
            )
            insertValue(db)
        }

        private fun fourthProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                    path = "212 W 18th St",
                    complement = "#1",
                    district = DistrictConverter.fromDistrict(District.MANHATTAN),
                    postalCode = "NY 10011"
            )
            buildFakeProperty(
                    type = TypeConverter.fromType(Type.PENTHOUSE),
                    price = "$35,000,000",
                    surface = 5955,
                    rooms = 11,
                    bedrooms = 5,
                    bathrooms = 6,
                    description = "Introducing Penthouse One at Walker Tower, widely considered to be the best penthouse downtown. Rarely does a home so momentous become available - with 5 bedrooms, 5.5 bathrooms and 3 wood-burning fireplaces amidst 5,955 square feet of pristine interiors and additional 479 square feet of private terraces. Spanning the entirety of the top floor at Walker Tower, Penthouse One offers unrivaled luxury and 360-degree views of the Hudson River, World Trade Center and Statue of Liberty in downtown's most significant building.",
                    addressId = 4,
                    locationsOfInterest = LocationsOfInterestConverter.fromLocationsOfInterest(listOf(LocationOfInterest.SCHOOL, LocationOfInterest.COMMERCES, LocationOfInterest.PARK)),
                    status = StatusConverter.fromStatus(Status.AVAILABLE),
                    availableSince = DateConverter.fromDate(Date(1548328108)),
                    agent = AgentConverter.fromAgent(Agent.STEVE_ROGERS)
            )
            insertValue(db)
        }

        private fun fifthProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                    path = "3033 Scenic Pl",
                    district = DistrictConverter.fromDistrict(District.BRONX),
                    postalCode = "NY 10463"
            )
            buildFakeProperty(
                    type = TypeConverter.fromType(Type.PENTHOUSE),
                    price = "$35,000,000",
                    surface = 6240,
                    rooms = 9,
                    bedrooms = 4,
                    bathrooms = 3,
                    description = "When your heart skips a beat, you know you found something special.Scenic place is just that, a special enclave on the banks of Hudson, a small dead end street in a nature district. Live your life with passion in this Brick Tudor home that sits on beautiful land with majestic trees giving you room to have a playground or to put in a pool or garden.Entering the foyer through the Turret of the house, one is greeted by a living room with high hand honed beams and a fireplace overlooking partial Hudson views leads to a sunroom that opens to the patio and garden on the south side. The raised dining room provides you with those special dining experiences. A renovated eat in kitchen with granite countertops and a stone backsplash with stainless steel appliances and a leaded window that artistically sits between the Kitchen and the dining. The main floor has an artistic powder room and a room that can be a guest room or an office. Downstairs there is a den with beamed ceilings, a laundry room and storage as well as a 2 car garage. Top floor offers 3 bedrooms and 2 bathrooms. Two the bedrooms have partial Hudson Views and the master bedroom has 2 exposures and a great feeling of comfort. Paint your life with incredible moments. This is Art of living to the fullest!This one of a kind oasis is only a few blocks to the Spuyten Duyvil Metro North train station where one can be whisked to Grand Central in 25 minutes. In addition, local and Manhattan express buses.",
                    addressId = 5,
                    locationsOfInterest = LocationsOfInterestConverter.fromLocationsOfInterest(listOf(LocationOfInterest.SCHOOL, LocationOfInterest.COMMERCES, LocationOfInterest.PARK, LocationOfInterest.TRAIN)),
                    status = StatusConverter.fromStatus(Status.AVAILABLE),
                    availableSince = DateConverter.fromDate(Date(1561979308)),
                    agent = AgentConverter.fromAgent(Agent.NATASHA_ROMANOFF)
            )
            insertValue(db)
        }

    }

}