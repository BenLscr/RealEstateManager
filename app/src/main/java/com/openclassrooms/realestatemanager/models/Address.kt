package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Address(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "address_id")
        val id: Int,
        val path: String,
        val complement: String?,
        val district: District,
        val city: City,
        val postalCode: String,
        val country: Country
)