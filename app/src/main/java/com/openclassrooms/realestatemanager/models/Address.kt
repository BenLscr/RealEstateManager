package com.openclassrooms.realestatemanager.models

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.database.converter.CityConverter
import com.openclassrooms.realestatemanager.database.converter.CountryConverter
import com.openclassrooms.realestatemanager.database.converter.DistrictConverter

@Entity
class Address(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "address_id")
        var id: Int = 0,
        val path: String,
        val complement: String?,
        val district: District,
        val city: City,
        val postalCode: String,
        val country: Country
) {
        companion object {
                fun fromContentValues(values: ContentValues): Address =
                        Address(
                                path = values.getAsString("path"),
                                complement = values.getAsString("complement"),
                                district = DistrictConverter.toDistrict(values.getAsInteger("district")),
                                city = CityConverter.toCity(values.getAsInteger("city")),
                                postalCode = values.getAsString("postalCode"),
                                country = CountryConverter.toCountry(values.getAsInteger("country"))
                        )
        }
}