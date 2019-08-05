package com.openclassrooms.realestatemanager.propertyDetail.models

class PropertyModelsProcessed(
        val description: String,
        val surface: String,
        val rooms: String,
        val bathrooms: String,
        val bedrooms: String,
        val available: Boolean,
        val path: String?,
        val complement: String?,
        val city: String?,
        val postalCode: String?,
        val country: String?,
        val agentFullName: String,
        val entryDate: String,
        val saleDate: String?
)