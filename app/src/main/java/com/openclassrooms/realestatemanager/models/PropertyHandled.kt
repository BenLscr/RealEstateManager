package com.openclassrooms.realestatemanager.models

class PropertyHandled(
        val id: Int,
        val type: String,
        val price: String,
        val surface: String,
        val rooms: Int,
        val bedrooms: Int,
        val bathrooms: Int,
        val garage: Boolean?,
        val description: String,
        //val images: MutableList<Bitmap>,
        val addressId: Int,
        val locationsOfInterest: MutableList<LocationOfInterest>,
        val status: String,
        val availableSince: String,
        val saleDate: String?,
        val agent: String
)