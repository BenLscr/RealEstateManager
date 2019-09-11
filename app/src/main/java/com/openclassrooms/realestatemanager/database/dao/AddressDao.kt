package com.openclassrooms.realestatemanager.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.openclassrooms.realestatemanager.models.Address

@Dao
interface AddressDao {

    @Insert
    fun insertAddress(address: Address): Long

    @Update
    fun updateAddress(address: Address)

}