package com.openclassrooms.realestatemanager.propertyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.models.Address
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
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
    fun getAddresses(): LiveData<List<Address>> { return addressDataSource.getAddresses()}

    fun getAddresse(addressId: Int): LiveData<Address> { return addressDataSource.getAddress(addressId)}

    fun insertAddress(address: Address) = executor.execute { addressDataSource.insertAddress(address) }

    fun updateAddress(address: Address) = executor.execute { addressDataSource.updateAddress(address) }

    fun deleAddress(addressId: Int) = executor.execute { addressDataSource.deleteAddress(addressId) }

    //---PROPERTY---\\
    fun getProperties(): LiveData<List<Property>> { return propertyDataSource.getProperties() }

    fun getProperty(propertyId: Int): LiveData<Property> { return propertyDataSource.getProperty(propertyId)}

    fun insertProperty(property: Property) = executor.execute { propertyDataSource.insertProperty(property) }

    fun updateProperty(property: Property) = executor.execute { propertyDataSource.updateProperty(property) }

    fun deleteProperty(propertyId: Int) = executor.execute { propertyDataSource.deleteProperty(propertyId) }

}