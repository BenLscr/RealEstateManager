package com.openclassrooms.realestatemanager.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
(foreignKeys = [ForeignKey(entity = Address::class, parentColumns = ["id"], childColumns = ["addressId"])])
class Property(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val type: Type,
        val price: String,
        val surface: Int,
        val rooms: Int,
        val bedrooms: Int,
        val bathrooms: Int,
        val garage: Boolean,
        val description: String,
        val images: MutableList<Bitmap>,
        val addressId: Int,
        val locationsOfinterest: MutableList<LocationOfInterest>,
        val status: Status,
        val availableSince: String,
        val saleDate: String?,
        val agent: Agent
)