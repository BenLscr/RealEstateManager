package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.dao.AddressDao
import com.openclassrooms.realestatemanager.models.Address

class AddressDataRepository(private val addressDao: AddressDao) {

    fun getAddresses(): LiveData<List<Address>> { return addressDao.getAddresses() }

    fun getAddress(addressId: Int): LiveData<Address> { return this.addressDao.getAddress(addressId) }

    fun insertAddress(address: Address) = addressDao.insertAddress(address)

    fun updateAddress(address: Address) = addressDao.updateAddress(address)

    fun deleteAddress(addressId: Int) = addressDao.deleteAddress(addressId)

}