package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.database.dao.AddressDao
import com.openclassrooms.realestatemanager.models.Address

class AddressDataRepository(private val addressDao: AddressDao) {

    fun insertAddress(address: Address): Long = addressDao.insertAddress(address)

    fun updateAddress(address: Address) = addressDao.updateAddress(address)

}