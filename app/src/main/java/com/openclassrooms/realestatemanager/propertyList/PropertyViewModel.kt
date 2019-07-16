package com.openclassrooms.realestatemanager.propertyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.models.*
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

class PropertyViewModel (
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val executor: Executor) : ViewModel() {

    private var currentProperties: LiveData<List<Property>>? = null
    private var currentAddresses: LiveData<List<Address>>? = null

    /**
     * Weird implementation (lesson)
     */
    fun init() {
        if (this.currentProperties != null && this.currentAddresses != null) {
            return
        }
        currentProperties = propertyDataSource.getProperties()
        currentAddresses = addressDataSource.getAddresses()
    }

    //---ADDRESS--\\
    //fun getAddresses(): LiveData<List<Address>> { return addressDataSource.getAddresses() }
    fun getAddresses(): LiveData<List<AddressHandled>> { return Transformations.map(addressDataSource.getAddresses()) { data -> processAddressData(data)} }

    fun getAddresses(addressId: Int): LiveData<Address> { return addressDataSource.getAddress(addressId) }

    fun insertAddress(address: Address) = executor.execute { addressDataSource.insertAddress(address) }

    fun updateAddress(address: Address) = executor.execute { addressDataSource.updateAddress(address) }

    fun deleteAddresses(addressId: Int) = executor.execute { addressDataSource.deleteAddress(addressId) }

    //---PROPERTY---\\
    //fun getProperties(): LiveData<List<Property>> { return propertyDataSource.getProperties() }
    fun getProperties(): LiveData<List<PropertyHandled>> { return Transformations.map(propertyDataSource.getProperties()) {data -> processPropertyData(data)} }

    fun getProperty(propertyId: Int): LiveData<Property> { return propertyDataSource.getProperty(propertyId) }

    fun insertProperty(property: Property) = executor.execute { propertyDataSource.insertProperty(property) }

    fun updateProperty(property: Property) = executor.execute { propertyDataSource.updateProperty(property) }

    fun deleteProperty(propertyId: Int) = executor.execute { propertyDataSource.deleteProperty(propertyId) }

    //---OTHER---\\
    private fun processAddressData(addressesStart: List<Address>): List<AddressHandled> {
        val addressesEnd = mutableListOf<AddressHandled>()
        addressesStart.forEach { address ->
            val id: Int = address.id
            val path: String = address.path
            val complement: String? = address.complement
            val district: String = when (address.district) {
                District.MANHATTAN -> "Manhattan"
                District.BROOKLYN -> "Brooklyn"
                District.STATEN_ISLAND -> "Staten Island"
                District.QUEENS -> "Queens"
                District.BRONX -> "Bronx"
            }
            val city: String = when (address.city) {
                City.NEW_YORK -> "New York"
            }
            val postalCode: String = address.postalCode
            val country: String = when (address.country) {
                Country.UNITED_STATES -> "United States"
            }

            addressesEnd.add(AddressHandled(id, path, complement, district, city, postalCode, country))
        }
        return addressesEnd.toList()
    }

    private fun processPropertyData(propertiesStart: List<Property>): List<PropertyHandled> {
        val propertiesEnd = mutableListOf<PropertyHandled>()
        propertiesStart.forEach { property ->
            val id: Int = property.id
            val type: String = when (property.type) {
                Type.PENTHOUSE -> "Penthouse"
                Type.MANSION -> "Mansion"
                Type.FLAT -> "Flat"
                Type.DUPLEX -> "Duplex"
                Type.HOUSE -> "House"
                Type.LOFT -> "Loft"
                Type.TOWNHOUSE -> "Townhouse"
                Type.CONDO -> "Condo"
            }
            val price: String = property.price
            val surface = "${property.surface} sq ft"
            val rooms: Int = property.rooms
            val bedrooms: Int = property.bedrooms
            val bathrooms: Int = property.bathrooms
            val garage: Boolean? = property.garage
            val description: String = property.description
            //val images: MutableList<Bitmap>
            val addressId: Int = property.addressId
            val locationsOfInterest: MutableList<LocationOfInterest> = property.locationsOfInterest
            val status: String = when (property.status) {
                Status.AVAILABLE -> "Available"
                Status.SOLD -> "Solde"
            }
            val availableSince: String = convertLongDateToString(property.availableSince)
            val saleDate: String? = property.saleDate
            val agent: String = when (property.agent) {
                Agent.TONY_STARK -> "Tony Stark"
                Agent.PETER_PARKER -> "Peter Parker"
                Agent.STEVE_ROGERS -> "Steve Rogers"
                Agent.NATASHA_ROMANOFF -> "Natasha Romanoff"
                Agent.BRUCE_BANNER -> "Bruce Banner"
                Agent.CLINTON_BARTON -> "Clinton Barton"
                Agent.CAROL_DENVERS -> "Carol Denvers"
                Agent.WANDA_MAXIMOFF -> "Wanda Maximoff"
            }
            propertiesEnd.add(PropertyHandled(id, type, price, surface, rooms, bedrooms, bathrooms, garage, description, addressId, locationsOfInterest, status, availableSince, saleDate, agent))
        }
        return propertiesEnd.toList()
    }

    private fun convertLongDateToString(longDate: Long): String {
        val date: Date = Date(longDate)
        val format: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(date)
    }

    /*private fun buildString(stringStart: String): String {
        val firstLetterOfString: String = stringStart.first().toString()
        val endOfString: String = stringStart.subSequence(1, stringStart.lastIndex).toString().toLowerCase()
        return firstLetterOfString + endOfString
    }*/

}