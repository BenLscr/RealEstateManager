package com.openclassrooms.realestatemanager.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
(foreignKeys = [ForeignKey(entity = Address::class, parentColumns = ["address_id"], childColumns = ["addressId"]),
    ForeignKey(entity = Agent::class, parentColumns = ["agent_id"], childColumns = ["agentId"])])
class Property(
        val type: Type,
        val price: Long,
        val surface: Int,
        val rooms: Int,
        val bedrooms: Int,
        val bathrooms: Int,
        val description: String,
        //val images: MutableList<Bitmap>,
        val addressId: Int,
        val available: Boolean,
        val entryDate: Long,
        val saleDate: Long?,
        val agentId: Int
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @Embedded var address: Address? = null
    @Embedded var agent: Agent? = null
}