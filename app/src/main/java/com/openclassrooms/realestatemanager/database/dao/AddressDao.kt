package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.Address

@Dao
interface AddressDao {

    @Query("SELECT * FROM Address")
    fun getAdresses(): LiveData<List<Address>>

    @Query("SELECT * FROM Address WHERE id == id")
    fun getAddress(id: Int): LiveData<Address>

    @Insert
    fun insertAddress(address: Address)

    @Update
    fun updateAddress(address: Address)

    @Query("DELETE FROM Address WHERE id == id")
    fun deleteAddress(id: Int)

    /**@Delete("DELETE * FROM Address WHERE id == id")
    fun deleteAddress(id: Int)*/

}