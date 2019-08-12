package com.openclassrooms.realestatemanager.database

import android.content.Context
import android.content.ContentValues
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.database.converter.*
import com.openclassrooms.realestatemanager.database.converter.TypeConverter
import com.openclassrooms.realestatemanager.database.dao.*
import com.openclassrooms.realestatemanager.models.*
import java.util.*


@Database(entities = [Property::class,
    Address::class,
    Agent::class,
    CompositionPropertyAndLocationOfInterest::class,
    PropertyPhoto::class,
    CompositionPropertyAndPropertyPhoto::class] , version = 1, exportSchema = false)
@TypeConverters(CityConverter::class,
        CountryConverter::class,
        DateConverter::class,
        DistrictConverter::class,
        LocationOfInterestConverter::class,
        TypeConverter::class,
        WordingConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun propertyDao(): PropertyDao
    abstract fun addressDao(): AddressDao
    abstract fun agentDao(): AgentDao
    abstract fun compositionPropertyAndLocationOfInterestDao(): CompositionPropertyAndLocationOfInterestDao
    abstract fun propertyPhotoDao(): PropertyPhotoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        private val contentValuesAgent = ContentValues()
        private val contentValuesAddress = ContentValues()
        private val contentValuesProperty = ContentValues()
        private val contentValuesCompositionPropertyAndLocationOfInterest = ContentValues()
        private val contentValuesPropertyPhoto = ContentValues()
        private val contentValuesCompositionPropertyAndPropertyPhoto = ContentValues()

        private const val NAME = "name"
        private const val FIRSTNAME = "firstName"

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
        private const val DESCRIPTION = "description"
        private const val ADDRESS_ID = "addressId"
        private const val AVAILABLE = "available"
        private const val ENTRY_DATE = "entryDate"
        private const val SALE_DATE = "saleDate"
        private const val AGENT_ID = "agentId"

        private const val PROPERTY_ID = "propertyId"
        private const val LOCATION_OF_INTEREST_ID = "locationOfInterestId"

        private const val WORDING = "wording"
        private const val IS_THIS_THE_ILLUSTRATION = "isThisTheIllustration"

        private const val PROPERTY_PHOTO_ID = "propertyPhotoId"

        private var propertyPhotoId = 0

        /**fun getInstance(context: Context): AppDatabase {
            INSTANCE ?: synchronized(Any()) {
                INSTANCE ?: Room.databaseBuilder(context, AppDatabase::class.java, "Database.db")
                        .addCallback(prepopulateDatabase())
                        .build()
            }
            return INSTANCE!!
        }*/

        fun getInstance(context: Context): AppDatabase {
            INSTANCE.let {  }
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

                    addEveryAgentsIntoDatabase(db)

                    firstProperty(db)
                    secondProperty(db)
                    thirdProperty(db)
                    fourthProperty(db)
                    fifthProperty(db)
                    sixthProperty(db)
                    seventhProperty(db)
                    eighthProperty(db)
                    ninthProperty(db)
                    tenthProperty(db)

                    addFirstCompositionPropertyAndLocationOfInterestIntoDatabase(1, db)
                    addSecondCompositionPropertyAndLocationOfInterestIntoDatabase(2, db)
                    addThirdCompositionPropertyAndLocationOfInterestIntoDatabase(3, db)
                    addFourthCompositionPropertyAndLocationOfInterestIntoDatabase(4, db)
                    addFifthCompositionPropertyAndLocationOfInterestIntoDatabase(5, db)
                    addSixthCompositionPropertyAndLocationOfInterestIntoDatabase(6, db)
                    addSeventhCompositionPropertyAndLocationOfInterestIntoDatabase(7, db)
                    addEighthCompositionPropertyAndLocationOfInterestIntoDatabase(8, db)
                    addNinthCompositionPropertyAndLocationOfInterestIntoDatabase(9, db)
                    addTenthCompositionPropertyAndLocationOfInterestIntoDatabase(10, db)

                    addFirstPropertyPhotoIntoDatabase(db)
                    addSecondPropertyPhotoIntoDatabase(db)
                    addThirdPropertyPhotoIntoDatabase(db)
                    addFourthPropertyPhotoIntoDatabase(db)
                    addFifthPropertyPhotoIntoDatabase(db)
                    addSixthPropertyPhotoIntoDatabase(db)
                    addSeventhPropertyPhotoIntoDatabase(db)
                    addEighthPropertyPhotoIntoDatabase(db)
                    addNinthPropertyPhotoIntoDatabase(db)
                    addTenthPropertyPhotoIntoDatabase(db)

                    addFirstCompositionPropertyAndPropertyPhotoIntoDatabase(1, db)
                    addSecondCompositionPropertyAndPropertyPhotoIntoDatabase(2, db)
                    addThirdCompositionPropertyAndPropertyPhotoIntoDatabase(3, db)
                    addFourthCompositionPropertyAndPropertyPhotoIntoDatabase(4, db)
                    addFifthCompositionPropertyAndPropertyPhotoIntoDatabase(5, db)
                    addSixthCompositionPropertyAndPropertyPhotoIntoDatabase(6, db)
                    addSeventhCompositionPropertyAndPropertyPhotoIntoDatabase(7, db)
                    addEighthCompositionPropertyAndPropertyPhotoIntoDatabase(8, db)
                    addNinthCompositionPropertyAndPropertyPhotoIntoDatabase(9, db)
                    addTenthCompositionPropertyAndPropertyPhotoIntoDatabase(10, db)
                }
            }
        }

        private fun addEveryAgentsIntoDatabase(db: SupportSQLiteDatabase) {
            buildAgentAndInsert("Stark", "Tony", db)
            buildAgentAndInsert("Parker", "Peter", db)
            buildAgentAndInsert("Rogers", "Steve", db)
            buildAgentAndInsert("Romanoff", "Natasha", db)
            buildAgentAndInsert("Banner", "Bruce", db)
            buildAgentAndInsert("Barton", "Clinton", db)
            buildAgentAndInsert("Denvers", "Carol", db)
            buildAgentAndInsert("Maximoff", "Wanda", db)
        }

        private fun buildAgentAndInsert(name: String, firstName: String, db: SupportSQLiteDatabase) {
            contentValuesAgent.put(NAME, name)
            contentValuesAgent.put(FIRSTNAME, firstName)
            db.insert("Agent", OnConflictStrategy.IGNORE, contentValuesAgent)
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
                                      price: Long,
                                      surface: Int,
                                      rooms: Int,
                                      bedrooms: Int,
                                      bathrooms: Int,
                                      description: String,
                                      addressId: Int,
                                      available: Boolean,
                                      entryDate: Long,
                                      saleDate: Long? = null,
                                      agentId: Int) {
            contentValuesProperty.put(TYPE, type)
            contentValuesProperty.put(PRICE, price)
            contentValuesProperty.put(SURFACE, surface)
            contentValuesProperty.put(ROOMS, rooms)
            contentValuesProperty.put(BEDROOMS, bedrooms)
            contentValuesProperty.put(BATHROOMS, bathrooms)
            contentValuesProperty.put(DESCRIPTION, description)
            contentValuesProperty.put(ADDRESS_ID, addressId)
            contentValuesProperty.put(AVAILABLE, available)
            contentValuesProperty.put(ENTRY_DATE, entryDate)
            if (saleDate == null) {
                contentValuesProperty.putNull(SALE_DATE)
            } else {
                contentValuesProperty.put(SALE_DATE, saleDate)
            }
            contentValuesProperty.put(AGENT_ID, agentId)
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
                    price = 895000,
                    surface = 4000,
                    rooms = 8,
                    bedrooms = 4,
                    bathrooms = 2,
                    description = "One of a kind Chateau style colonial nestled in prestigious Lighthouse Hill. This 4 bedroom, 2 bath has balconies off of 3 of the bedrooms and sitting patio off of the living room. Creating charm and warmth throughout with oak floors and fireplace as focal point in the living room. New kitchen and baths, all exterior updated including pavers in yard leaving the home maintenance free. Views of harbor, walk to the Light House and golf course.",
                    addressId = 1,
                    available = true,
                    entryDate = DateConverter.fromDate(Date(1549574288)),
                    agentId = 7
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
                    price = 1500000,
                    surface = 1325,
                    rooms = 10,
                    bedrooms = 3,
                    bathrooms = 3,
                    description = "Welcome home to 537 Court Street, a gorgeous new condominium conversion at the nexus of three great neighborhoods, Carroll Gardens, Red Hook, and Gowanus. Residence 4A is a sprawling duplex penthouse residence totaling 1,325 sqft with a private 875 sqft roof terrace. This apartment has tall ceilings and a large living/dining room with a working fireplace; ideal for real entertaining. The 3 large bedrooms and bathrooms are all north facing, with ample sunlight. 537 Court Street offers gorgeous high-end finishes, superb layouts, and private outdoor space in a trendy amenity-rich neighborhood. Kitchens have stone counters, custom cabinetry, and high-end appliances. There are beautiful hardwood floors throughout and central AC and heat. Each home is equipped with a washer/dryer and video intercom. With very low common charges and taxes, and today's low mortgage rates, owning this home may well be more affordable than renting! Located on a sunny convenient corner of Court Street, your new home is incredibly conveniently located in the midst of world-class shopping and dining including both international brands and local favorites. Your new home is 2 blocks to subways, parks, and excellent schools.",
                    addressId = 2,
                    available = true,
                    entryDate = DateConverter.fromDate(Date(1562752237)),
                    agentId = 6
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
                    price = 29900000,
                    surface = 4019,
                    rooms = 7,
                    bedrooms = 3,
                    bathrooms = 5,
                    description = "Unique opportunity to own at Rafael Violy's iconic 432 Park Avenue. Entering through a private elevator vestibule, you are welcomed into this stunning 4,019 square foot home featuring 3 bedrooms, a library, 4.5 baths and 12'6' ceilings throughout. Expansive 10'x10' windows with North, South and Eastern views fill the morning with sunrise, evenings with sunset and breathtaking skyline, park and river views no matter the time of day. A gracious foyer leads to a grand 29' x 29' corner living and dining room. The windowed eat-in kitchen includes a breakfast bar overlooking Central Park with custom white lacquer and natural oak cabinetry, Blue de Savoie marble countertops and a 96' x 51' Bianco Scintillante marble center island. Miele stainless steel appliances and Dornbracht polished chrome fixtures complete this elegant kitchen. The corner master bedroom suite features light filled open Southern and Eastern views and includes spacious closets and a separate dressing area. Two windowed master bathrooms allow for complete privacy and include radiant heated marble floors and walls of book-matched slabs of Italian Statuario marble. One of the bathrooms features a freestanding soaking tub with endless views, as well as a separate shower. The two additional bedrooms each feature an ensuite bath with marble, radiant heated floors. Every inch of this apartment has been tastefully finished to the highest quality with state-of-the-art custom audio and lighting throughout. All bedrooms have been fitted with blackout shades and fully automated (LED) lighting. Living at 432 Park Avenue is comparable to living at a 5-star hotel with the luxury of not having to share it with guests. Featuring over 30,000 square feet of amenities, one never needs to leave the building. Additional services include a private restaurant, in residence dining, a 5,000 square foot outdoor terrace for dining and events, a 75-foot indoor swimming pool, gym, spa, steam room, sauna, massage/treatment room, library, billiards room, conference room with state-of-the-art teleconferencing, screening room, concierge, 24-hour doorman and security.This residence is being sold inclusive of a 595 square foot corner, fully renovated, studio suite and a storage unit.",
                    addressId = 3,
                    available = true,
                    entryDate = DateConverter.fromDate(Date(1562586286)),
                    agentId = 1
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
                    price = 35000000,
                    surface = 5955,
                    rooms = 11,
                    bedrooms = 5,
                    bathrooms = 6,
                    description = "Introducing Penthouse One at Walker Tower, widely considered to be the best penthouse downtown. Rarely does a home so momentous become available - with 5 bedrooms, 5.5 bathrooms and 3 wood-burning fireplaces amidst 5,955 square feet of pristine interiors and additional 479 square feet of private terraces. Spanning the entirety of the top floor at Walker Tower, Penthouse One offers unrivaled luxury and 360-degree views of the Hudson River, World Trade Center and Statue of Liberty in downtown's most significant building.",
                    addressId = 4,
                    available = true,
                    entryDate = DateConverter.fromDate(Date(1548328108)),
                    agentId = 3
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
                    price = 35000000,
                    surface = 6240,
                    rooms = 9,
                    bedrooms = 4,
                    bathrooms = 3,
                    description = "When your heart skips a beat, you know you found something special.Scenic place is just that, a special enclave on the banks of Hudson, a small dead end street in a nature district. Live your life with passion in this Brick Tudor home that sits on beautiful land with majestic trees giving you room to have a playground or to put in a pool or garden.Entering the foyer through the Turret of the house, one is greeted by a living room with high hand honed beams and a fireplace overlooking partial Hudson views leads to a sunroom that opens to the patio and garden on the south side. The raised dining room provides you with those special dining experiences. A renovated eat in kitchen with granite countertops and a stone backsplash with stainless steel appliances and a leaded window that artistically sits between the Kitchen and the dining. The main floor has an artistic powder room and a room that can be a guest room or an office. Downstairs there is a den with beamed ceilings, a laundry room and storage as well as a 2 car garage. Top floor offers 3 bedrooms and 2 bathrooms. Two the bedrooms have partial Hudson Views and the master bedroom has 2 exposures and a great feeling of comfort. Paint your life with incredible moments. This is Art of living to the fullest!This one of a kind oasis is only a few blocks to the Spuyten Duyvil Metro North train station where one can be whisked to Grand Central in 25 minutes. In addition, local and Manhattan express buses.",
                    addressId = 5,
                    available = true,
                    entryDate = DateConverter.fromDate(Date(1561979308)),
                    agentId = 4
            )
            insertValue(db)
        }

        private fun sixthProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                    path = "40 Slocum Cres",
                    district = DistrictConverter.fromDistrict(District.QUEENS),
                    postalCode = "NY 11375"
            )
            buildFakeProperty(
                    type = TypeConverter.fromType(Type.TOWNHOUSE),
                    price = 1675000,
                    surface = 1765,
                    rooms = 7,
                    bedrooms = 4,
                    bathrooms = 3,
                    description = "Desirable Atterbury Designed Pebblestone Townhouse in the Beautiful Forest Hills Gardens! The Sunny First Floor Has A Living Room With A Wood Burning Fireplace, Formal Dining Room And An Eat In Kitchen With a Door to The Backyard. The Second Floor Boasts Two Generous Bedrooms And A Full Hall Bath. The Third Floor Has Two Additional Bedrooms And A Full Hall Bath. The Basement Has A Half Bath And Separate Laundry Room. The Private Backyard Has A Patio And A Beautiful Cottage Garden.",
                    addressId = 6,
                    available = true,
                    entryDate = DateConverter.fromDate(Date(1563359033)),
                    agentId = 5
            )
            insertValue(db)
        }

        private fun seventhProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                    path = "82 Douglas Rd",
                    district = DistrictConverter.fromDistrict(District.STATEN_ISLAND),
                    postalCode = "NY 10304"
            )
            buildFakeProperty(
                    type = TypeConverter.fromType(Type.HOUSE),
                    price = 4499000,
                    surface = 12200,
                    rooms = 15,
                    bedrooms = 6,
                    bathrooms = 14,
                    description = "An architectural masterpiece that exudes a presence unlike any other home in Staten Island. A newly-constructed estate of exceptional grandeur, sophistication, and privacy. This home sits on over 1/2 acre of flat land, Located in unparalleled Emerson Hill, this phenomenal home is nothing short of majestic in concept, and magnificent in execution. Every comfort has been considered, and every detail has been meticulously crafted; this home has it ALL. This grand dwelling has been masterfully designed for living and entertaining. The seamless transition from the interior living spaces to the outdoor living spaces will leave you ensconced in the pinnacle of luxury, quality, and technology. Your imagination will be fueled by the technological marvels, and your senses thrilled by the breathtaking year-round water views andsparkling City and Bridge lights from every room and the yard. The outdoor living spaces were professionally designed and landscaped with the same thoughtful considerations as the interior. Smart technology controls the fire pit, fire balls, pool, spa, and exterior lighting - all from the palm of your hand. Huge covered exterior kitchen/bar, entertainment area with paddle fans; lavish exterior bathroom; multiple seating areas and media. The awe-inspiring 24-foot tall foyer with its broad and sweeping staircase welcomes you into the sophisticated, light-infused, Smart-Home-controlled, open floor-plan with four levels of luxurious living and entertainment spaces. The main level offers banquet-sized dining room, elevator, butler's pantry, full walk-in pantry, chef's kitchen with all high-end appliances (please refer to detail sheet), over-sized center island with seating, chic powder room,family room with soaring window wall that overlooks the magnificent rear yard, living room with multiple seating areas and custom stone fireplace. The second floor offers a master suite with two private terraces, separate sitting area with fireplace, huge walk-in closet, and bathroom with double sink and claw-foot soaking tub, and a large custom walk-in shower. Three additional bedrooms, each with their own private bath and balcony. The rearmost bedroom also has a fireplace. The top level can be used as a private guest area; has two additional bedrooms, its own family room and 3/4 bath. The finished basement is fully above grade (with views too!) and features 12 foot ceiling heights, home theater, custom fully-equipped bar, and a sophisticated gentleman's lair. Built-in garage with radiant h A trophy estate that re-imagines what luxury is all about. The pictures are nice but is by far nicer in person, its a true must see in person.",
                    addressId = 7,
                    available = false,
                    entryDate = DateConverter.fromDate(Date(1558347833)),
                    saleDate = DateConverter.fromDate(Date(1560595695)),
                    agentId = 8
            )
            insertValue(db)
        }

        private fun eighthProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                    path = "168 Amity St",
                    district = DistrictConverter.fromDistrict(District.BROOKLYN),
                    postalCode = "NY 11201"
            )
            buildFakeProperty(
                    type = TypeConverter.fromType(Type.TOWNHOUSE),
                    price = 6950000,
                    surface = 4500,
                    rooms = 7,
                    bedrooms = 5,
                    bathrooms = 3,
                    description = "This unique historic Cobble Hill 25-foot Townhouse was reimagined by the owners, both acclaimed in their artistic fields of fashion and photography, in collaboration with architect Neil Logan. The home, with its clean and simple elegance, is a serene celebration of light, space, and history. With a traditional faade on one of Cobble HIll's most beautiful blocks and southern exposures, ascending the stairs and stepping inside feels immediately transportive. A bohemian blend of traditional, modern, and industrial, 168 Amity is a space to feel inspired by. The parlor floor, with its open vestibule, delivers a stunning welcome. A south facing wall of floor-to-ceiling atelier windows flood the entire space with natural light and bring the outdoors in. A large den/office off the entry hall, step into the open plan parlor floor, an airy living space with a sweeping sense of tranquility. The floating stairs, with open risers, allows the abundant light to flow throughout the space with the lush greenery of the backyard lending an uplifting natural element. A wood-burning fireplace adds a traditional touch perfectly in keeping with the spirit of the room. Downstairs, the garden level continues the theme; steel-encased glass doors open to the large, lush south-facing 45 foot deep backyard, extending the living space to the outdoors. Featuring lush plantings and blue stone patio, the garden enjoys sunlight throughout the day. The sun-drenched kitchen, dining area and garden are equally well suited for full-fledged entertaining or quiet relaxation. The garden level also features a guest bedroom, bathroom, laundry room and large pantry. Upstairs, the first-floor master bedroom features a wood burning fireplace and large terrace overlooking the garden, an ideal setting in which to create a personalized sanctuary. The windowed bathroom, with the zen-like minimalism that defines the house, is constructed of marine-grade cedar and unique design elements and fixtures. An additional bedroom shares the bathroom but would seamlessly lend itself to creating a spacious dressing room to completing a first-floor master suite. The top floor is currently configured as a large 25 x 34-foot open artist's studio with half bath. Illuminated by a skylight topping a 12 foot ceiling, it is an enviable creative space that could easily accommodate 3 additional bedrooms and full bathroom. Sitting on a wide 25 x 100 foot lot, the house has ample basement storage and radiant heated floors throughout. With an existing 4,500 square feet of interior space and 1,500 square feet of outdoor space there are endless possibilities to create a truly personal and exceptional environment. With peaceful leafy streets, historic homes, and upscale shopping and restaurants, Cobble Hill is one of New York City's hottest Brooklyn neighborhoods. A short walk to Pier 6 Park or Brooklyn Park offers waterfront green spaces and stunning views of Manhattan . Convenient to subway transportation.",
                    addressId = 8,
                    available = true,
                    entryDate = DateConverter.fromDate(Date(1560081674)),
                    agentId = 3
            )
            insertValue(db)
        }

        private fun ninthProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                    path = "66 E 11th St",
                    district = DistrictConverter.fromDistrict(District.MANHATTAN),
                    postalCode = "NY 10003"
            )
            buildFakeProperty(
                    type = TypeConverter.fromType(Type.PENTHOUSE),
                    price = 26000000,
                    surface = 7693,
                    rooms = 8,
                    bedrooms = 4,
                    bathrooms = 7,
                    description = "Introducing the Penthouse at 66 East 11th Street ? a custom designed masterpiece, turn-key and ready for occupancy. Located on a tree-lined street in the heart of historic Greenwich Village, 66 East 11th is a boutique condominium featuring just six residences, with this one-of-a-kind 7,693 square foot triplex penthouse and colossal 2,300 square foot rooftop oasis sitting at the top. Complete with open Southern, Eastern, and Northern exposures this incredible home offers fantastic light, enormous proportions and beautiful views of both uptown landmarks and the downtown skyline. The residence features four bedrooms each with its own en-suite bathroom, 2 living areas, an open chef's kitchen with scullery, a rooftop featuring a solarium and the potential for a pool, a powder room serving each floor and full wiring, and home- automation systems. A minimal material palette of rare, durable components is used throughout the space, but treated differently depending on location and application. The result is a space which is considered, sustainable, and artful. Enter off a direct keyed elevator into expansive, open-concept living featuring a breath-taking, 2 story picturesque window. Vast living space provides a variety of lounging and dining options, accented by thoughtfully arched 9-foot windows, an Empire State view and airy beamed ceilings. Past living/dining is an entertainer's dream - a massive island sits as the kitchen centerpiece, with top-of-the-line-fully-integrated appliances, endless storage and Calacatta gold waterfall countertops to compliment it. Hidden within the custom Walnut cabinetry you will find a scullery, outfitted with all Miele appliances. The PH also comes delivered with a Miele appliance package including a 102-bottle dual zone Wine Refrigerator, a Six-burner Convection Oven with Tepan Yaki grill, Microwave, Steam Oven, Dishwashers, Integrated Combination Refrigerator/Freezer and Washer/Dryer. On the South Side of the floor a Master Suite boasts World Trade Center view and two expansive walk-in closets that allow for customization into a separate dressing room, office or media room. Expect nothing less than a grand en-suite bathroom with five windows, an impressive soaking tub encased in marble, a walk-in steam shower, heated floors and separate enclosed toilet. Top of the line finishes and stone compliment the aura of this luxurious penthouse. Dramatically set against the single pane, two-story glass window is a three-story custom crafter spiral staircase with wrought-iron railing and floating wooden planks. Taking a trip down stairs you will find the premiere 7th floor living space complete with a 6-foot long fireplace against its interior wall, made of Calcutta Manhattan marble which creates a ripple of warm light when in use, opportunities for a variety of sitting areas and magnificent art walls.",
                    addressId = 9,
                    available = true,
                    entryDate = DateConverter.fromDate(Date(1559915888)),
                    agentId = 1
            )
            insertValue(db)
        }

        private fun tenthProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                    path = "166 E 81st St",
                    district = DistrictConverter.fromDistrict(District.MANHATTAN),
                    postalCode = "NY 10028"
            )
            buildFakeProperty(
                    type = TypeConverter.fromType(Type.TOWNHOUSE),
                    price = 17900000,
                    surface = 2016,
                    rooms = 8,
                    bedrooms = 8,
                    bathrooms = 9,
                    description = "This exceptional two townhouse compound (166 East 81st connecting through the double garden with with 179 East 80th St.) represents a once-in-a-lifetime opportunity to acquire a private enclave anchored by the city's most glorious garden. The two back-to-back homes, connected by a 75-foot garden with 40-foot trees and a heated outdoor plunge pool, are located on 81st and 80th Streets in Manhattan's Upper East Side. With coveted architectural details and flexible layouts to serve any need, these fine town homes provide more than 9,000-square-feet of interior living space and in excess of 2,000-square-feet of outdoor space, perfect for a seamless indoor-outdoor lifestyle, peaceful relaxation and exquisite entertaining. The 20-foot-wide, high-stoop 81st Street home invites you into a grand room with 11 foot, 7 inch ceilings and beautiful floors, leading to the formal dining room with a wood burning fireplace, an entire wall of windows and a lovely balcony that overlooks the stunning greenery of this truly unique setting. There is a convenient butler's pantry, and a dumbwaiter conveys serving items to the oversized kitchen below, which is joined with the large, casual living room -- an indoor-outdoor oasis that opens directly to the leafy garden. An enviable master suite with an en suite bathroom and large walk-in closet is positioned on the third floor along with a master study and second dressing area. Three more bedrooms and two large bathrooms on the fourth floor have 9 foot, 6 inch ceilings, beautiful windows and views, providing plenty of room for family and guests. The finished basement adds convenience with a laundry, a full bathroom, closets and massive storage rooms. Connecting through the garden, past the paved seating area, the outdoor dining area and plunge pool, the 80th Street \"Pool House\" is itself an independent, 18-foot-wide, five-story brownstone. Move right into this dignified townhouse, or take advantage of the abundant space to craft guest quarters and rooms suited to your exact needs. Currently configured predominantly as an entertaining space, the home begins on the garden level with a casual dining room, a beautiful tiled kitchen and a ground-level guest room with direct, independent access to East 80th Street. There are also two full bathrooms on this floor. The parlor level, which can be accessed by its own grand stoop on East 80th Street, features 10 foot, 6 inch ceilings, a wood-burning fireplace and an oversized window. Chic herringbone floors guide you from an expansive living room to a dramatic red-lacquer billiard room and onto a covered outdoor loggia, looking north over the pool and gardens. The third floor hosts a beautiful guest suite with a large double bathroom and private sitting room, which could easily be an additional bedroom.",
                    addressId = 10,
                    available = true,
                    entryDate = DateConverter.fromDate(Date(1559075359)),
                    agentId = 4
            )
            insertValue(db)
        }

        private fun addFirstCompositionPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.PARK),db)
        }

        private fun addSecondCompositionPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.PARK),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SUBWAYS),db)
        }

        private fun addThirdCompositionPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.PARK),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SUBWAYS),db)
        }

        private fun addFourthCompositionPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.PARK),db)
        }

        private fun addFifthCompositionPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.PARK),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.TRAIN),db)
        }

        private fun addSixthCompositionPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.PARK),db)
        }

        private fun addSeventhCompositionPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
        }

        private fun addEighthCompositionPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SUBWAYS),db)
        }

        private fun addNinthCompositionPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SUBWAYS),db)
        }

        private fun addTenthCompositionPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SUBWAYS),db)
        }

        private fun buildCompositionPropertyAndLocationOfInterestAndInsert(propertyId: Int, locationOfInterestId: Int, db: SupportSQLiteDatabase) {
            contentValuesCompositionPropertyAndLocationOfInterest.put(PROPERTY_ID, propertyId)
            contentValuesCompositionPropertyAndLocationOfInterest.put(LOCATION_OF_INTEREST_ID, locationOfInterestId)
            db.insert("CompositionPropertyAndLocationOfInterest", OnConflictStrategy.IGNORE, contentValuesCompositionPropertyAndLocationOfInterest)
        }

        private fun addFirstPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0", WordingConverter.fromWording(Wording.STREET_VIEW), true, db)
            buildPropertyPhotoAndInsert("1", WordingConverter.fromWording(Wording.STREET_VIEW), false, db)
            buildPropertyPhotoAndInsert("2", WordingConverter.fromWording(Wording.STREET_VIEW), false, db)
            buildPropertyPhotoAndInsert("3", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("4", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("5", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("6", WordingConverter.fromWording(Wording.HALL), false, db)
            buildPropertyPhotoAndInsert("7", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("8", WordingConverter.fromWording(Wording.DINING_ROOM), false, db)
            buildPropertyPhotoAndInsert("9", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("10", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("11", WordingConverter.fromWording(Wording.BALCONY), false, db)
            buildPropertyPhotoAndInsert("12", WordingConverter.fromWording(Wording.BALCONY), false, db)
            buildPropertyPhotoAndInsert("13", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("14", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("15", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("16", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("17", WordingConverter.fromWording(Wording.BALCONY), false, db)
            buildPropertyPhotoAndInsert("18", WordingConverter.fromWording(Wording.TERRACE), false, db)
            buildPropertyPhotoAndInsert("19", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("20", WordingConverter.fromWording(Wording.WALK_IN_CLOSET), false, db)
            buildPropertyPhotoAndInsert("21", WordingConverter.fromWording(Wording.OFFICE), false, db)
            buildPropertyPhotoAndInsert("22", WordingConverter.fromWording(Wording.TERRACE), false, db)
            buildPropertyPhotoAndInsert("23", WordingConverter.fromWording(Wording.TERRACE), false, db)
            buildPropertyPhotoAndInsert("24", WordingConverter.fromWording(Wording.TERRACE), false, db)
        }

        private fun addSecondPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0", WordingConverter.fromWording(Wording.LIVING_ROOM), true, db)
            buildPropertyPhotoAndInsert("1", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("2", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("3", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("4", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("5", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("6", WordingConverter.fromWording(Wording.ROOF_TOP), false, db)
            buildPropertyPhotoAndInsert("7", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("8", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("9", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("10", WordingConverter.fromWording(Wording.PLAN), false, db)
        }

        private fun addThirdPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0", WordingConverter.fromWording(Wording.LIVING_ROOM), true, db)
            buildPropertyPhotoAndInsert("1", WordingConverter.fromWording(Wording.HALLWAY), false, db)
            buildPropertyPhotoAndInsert("2", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("3", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("4", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("5", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("6", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("7", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("8", WordingConverter.fromWording(Wording.VIEW), false, db)
            buildPropertyPhotoAndInsert("9", WordingConverter.fromWording(Wording.VIEW), false, db)
            buildPropertyPhotoAndInsert("10", WordingConverter.fromWording(Wording.GARAGE), false, db)
            buildPropertyPhotoAndInsert("11", WordingConverter.fromWording(Wording.SWIMMING_POOL), false, db)
            buildPropertyPhotoAndInsert("12", WordingConverter.fromWording(Wording.FITNESS_CENTRE), false, db)
            buildPropertyPhotoAndInsert("13", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("14", WordingConverter.fromWording(Wording.SPA), false, db)
            buildPropertyPhotoAndInsert("15", WordingConverter.fromWording(Wording.CINEMA), false, db)
            buildPropertyPhotoAndInsert("16", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("17", WordingConverter.fromWording(Wording.CONFERENCE), false, db)
            buildPropertyPhotoAndInsert("18", WordingConverter.fromWording(Wording.PLAN), false, db)
        }

        private fun addFourthPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0", WordingConverter.fromWording(Wording.LIVING_ROOM), true, db)
            buildPropertyPhotoAndInsert("1", WordingConverter.fromWording(Wording.OFFICE), false, db)
            buildPropertyPhotoAndInsert("2", WordingConverter.fromWording(Wording.BALCONY), false, db)
            buildPropertyPhotoAndInsert("3", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("4", WordingConverter.fromWording(Wording.VIEW), false, db)
            buildPropertyPhotoAndInsert("5", WordingConverter.fromWording(Wording.VIEW), false, db)
            buildPropertyPhotoAndInsert("6", WordingConverter.fromWording(Wording.PLAN), false, db)
        }

        private fun addFifthPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0", WordingConverter.fromWording(Wording.STREET_VIEW), true, db)
            buildPropertyPhotoAndInsert("1", WordingConverter.fromWording(Wording.HALL), false, db)
            buildPropertyPhotoAndInsert("2", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("3", WordingConverter.fromWording(Wording.DINING_ROOM), false, db)
            buildPropertyPhotoAndInsert("4", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("5", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("6", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("7", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("8", WordingConverter.fromWording(Wording.OFFICE), false, db)
            buildPropertyPhotoAndInsert("9", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("10", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("11", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("12", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("13", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("14", WordingConverter.fromWording(Wording.STAIRS), false, db)
            buildPropertyPhotoAndInsert("15", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("16", WordingConverter.fromWording(Wording.STREET_VIEW), false, db)
        }

        private fun addSixthPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0", WordingConverter.fromWording(Wording.STREET_VIEW), true, db)
            buildPropertyPhotoAndInsert("1", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("2", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("3", WordingConverter.fromWording(Wording.DINING_ROOM), false, db)
            buildPropertyPhotoAndInsert("4", WordingConverter.fromWording(Wording.DINING_ROOM), false, db)
            buildPropertyPhotoAndInsert("5", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("6", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("7", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("8", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("9", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("10", WordingConverter.fromWording(Wording.OFFICE), false, db)
            buildPropertyPhotoAndInsert("11", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("12", WordingConverter.fromWording(Wording.GARDEN), false, db)
            buildPropertyPhotoAndInsert("13", WordingConverter.fromWording(Wording.GARDEN), false, db)
        }

        private fun addSeventhPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0", WordingConverter.fromWording(Wording.STREET_VIEW), true, db)
            buildPropertyPhotoAndInsert("1", WordingConverter.fromWording(Wording.HALL), false, db)
            buildPropertyPhotoAndInsert("2", WordingConverter.fromWording(Wording.STAIRS), false, db)
            buildPropertyPhotoAndInsert("3", WordingConverter.fromWording(Wording.DINING_ROOM), false, db)
            buildPropertyPhotoAndInsert("4", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("5", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("6", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("7", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("8", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("9", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("10", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("11", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("12", WordingConverter.fromWording(Wording.FLOOR), false, db)
            buildPropertyPhotoAndInsert("13", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("14", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("15", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("16", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("17", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("18", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("19", WordingConverter.fromWording(Wording.BALCONY), false, db)
            buildPropertyPhotoAndInsert("20", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("21", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("22", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("23", WordingConverter.fromWording(Wording.OFFICE), false, db)
            buildPropertyPhotoAndInsert("24", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("25", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("26", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("27", WordingConverter.fromWording(Wording.TERRACE), false, db)
            buildPropertyPhotoAndInsert("28", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("29", WordingConverter.fromWording(Wording.SWIMMING_POOL), false, db)
            buildPropertyPhotoAndInsert("30", WordingConverter.fromWording(Wording.TERRACE), false, db)
            buildPropertyPhotoAndInsert("31", WordingConverter.fromWording(Wording.SWIMMING_POOL), false, db)
        }

        private fun addEighthPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0", WordingConverter.fromWording(Wording.LIVING_ROOM), true, db)
            buildPropertyPhotoAndInsert("1", WordingConverter.fromWording(Wording.GARDEN), false, db)
            buildPropertyPhotoAndInsert("2", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("3", WordingConverter.fromWording(Wording.STAIRS), false, db)
            buildPropertyPhotoAndInsert("4", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("5", WordingConverter.fromWording(Wording.BALCONY), false, db)
            buildPropertyPhotoAndInsert("6", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("7", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("8", WordingConverter.fromWording(Wording.FLOOR), false, db)
            buildPropertyPhotoAndInsert("9", WordingConverter.fromWording(Wording.FLOOR), false, db)
            buildPropertyPhotoAndInsert("10", WordingConverter.fromWording(Wording.HALL), false, db)
            buildPropertyPhotoAndInsert("11", WordingConverter.fromWording(Wording.STREET_VIEW), false, db)
        }

        private fun addNinthPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0", WordingConverter.fromWording(Wording.STAIRS), true, db)
            buildPropertyPhotoAndInsert("1", WordingConverter.fromWording(Wording.DINING_ROOM), false, db)
            buildPropertyPhotoAndInsert("2", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("3", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("4", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("5", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("6", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("7", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("8", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("9", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("10", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("11", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("12", WordingConverter.fromWording(Wording.ROOF_TOP), false, db)
            buildPropertyPhotoAndInsert("13", WordingConverter.fromWording(Wording.ROOF_TOP), false, db)
            buildPropertyPhotoAndInsert("14", WordingConverter.fromWording(Wording.ROOF_TOP), false, db)
            buildPropertyPhotoAndInsert("15", WordingConverter.fromWording(Wording.STAIRS), false, db)
        }

        private fun addTenthPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0", WordingConverter.fromWording(Wording.STREET_VIEW), true, db)
            buildPropertyPhotoAndInsert("1", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("2", WordingConverter.fromWording(Wording.DINING_ROOM), false, db)
            buildPropertyPhotoAndInsert("3", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("4", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("5", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("6", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("7", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("8", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("9", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("10", WordingConverter.fromWording(Wording.GARDEN), false, db)
            buildPropertyPhotoAndInsert("11", WordingConverter.fromWording(Wording.SWIMMING_POOL), false, db)
            buildPropertyPhotoAndInsert("12", WordingConverter.fromWording(Wording.FLOOR), false, db)
            buildPropertyPhotoAndInsert("13", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("14", WordingConverter.fromWording(Wording.BATHROOM), false, db)
        }

        private fun buildPropertyPhotoAndInsert(name: String, wording: Int, isThisTheIllustration: Boolean, db: SupportSQLiteDatabase) {
            contentValuesPropertyPhoto.put(NAME, name)
            contentValuesPropertyPhoto.put(WORDING, wording)
            contentValuesPropertyPhoto.put(IS_THIS_THE_ILLUSTRATION, isThisTheIllustration)
            db.insert("PropertyPhoto", OnConflictStrategy.IGNORE, contentValuesPropertyPhoto)
        }

        private fun addFirstCompositionPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
        }

        private fun addSecondCompositionPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addThirdCompositionPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addFourthCompositionPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addFifthCompositionPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addSixthCompositionPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addSeventhCompositionPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addEighthCompositionPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addNinthCompositionPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addTenthCompositionPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun getPropertyPhotoId(): Int {
            propertyPhotoId++
            return propertyPhotoId
        }

        private fun buildCompositionPropertyAndPropertyPhotoAndInsert(propertyId: Int, propertyPhotoId: Int, db: SupportSQLiteDatabase) {
            contentValuesCompositionPropertyAndPropertyPhoto.put(PROPERTY_ID, propertyId)
            contentValuesCompositionPropertyAndPropertyPhoto.put(PROPERTY_PHOTO_ID, propertyPhotoId)
            db.insert("CompositionPropertyAndPropertyPhoto", OnConflictStrategy.IGNORE, contentValuesCompositionPropertyAndPropertyPhoto)
        }

    }

}