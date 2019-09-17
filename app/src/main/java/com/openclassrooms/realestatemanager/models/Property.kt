package com.openclassrooms.realestatemanager.models

import android.content.ContentValues
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.database.converter.TypeConverter

@Entity
(foreignKeys = [ForeignKey(entity = Address::class, parentColumns = ["address_id"], childColumns = ["addressId"]),
    ForeignKey(entity = Agent::class, parentColumns = ["agent_id"], childColumns = ["agentId"])])
class Property(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        val type: Type,
        val price: Long,
        val surface: Int,
        val rooms: Int,
        val bedrooms: Int,
        val bathrooms: Int,
        val description: String,
        val addressId: Int,
        val available: Boolean,
        val entryDate: Long,
        val saleDate: Long?,
        val agentId: Int
) {
    @Embedded var address: Address? = null
    @Embedded var agent: Agent? = null

    companion object {
        fun fromContentValues(values: ContentValues): Property =
                Property(
                        type = TypeConverter.toType(values.getAsInteger("type")),
                        price = values.getAsLong("price"),
                        surface = values.getAsInteger("surface"),
                        rooms = values.getAsInteger("rooms"),
                        bedrooms = values.getAsInteger("bedrooms"),
                        bathrooms = values.getAsInteger("bathrooms"),
                        description = values.getAsString("description"),
                        addressId = values.getAsInteger("addressId"),
                        available = values.getAsBoolean("available"),
                        entryDate = values.getAsLong("entryDate"),
                        saleDate = values.getAsLong("saleDate"),
                        agentId = values.getAsInteger("agentId")
            )
    }
}