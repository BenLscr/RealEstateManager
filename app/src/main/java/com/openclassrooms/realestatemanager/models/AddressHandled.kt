package com.openclassrooms.realestatemanager.models

class AddressHandled(
        val id: Int,
        val path: String,
        val complement: String?,
        val district: String,
        val city: String,
        val postalCode: String,
        val country: String
)