package com.openclassrooms.realestatemanager.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
(foreignKeys = [ForeignKey(entity = Location::class, parentColumns = ["id"], childColumns = ["locationId"])])
class Property(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val type: Type,
        val price: Double,
        val surface: Int,
        val rooms: Int,
        val description: String,
        val images: List<Bitmap>,
        val locationId: String,
        val locationsOfinterest: List<String>,
        val status: Status,
        val availableSince: String,
        val saleDate: String?,
        val agent: Agent)