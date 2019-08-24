package com.openclassrooms.realestatemanager.form.updateForm.models

class PropertyModelProcessed(
        val type: String,
        val price: String,
        val rooms: String,
        val bedrooms: String,
        val bathrooms: String,
        val description: String,
        val available: Boolean,
        val entryDate: String,
        val saleDate: String?,
        val addressId: Int,
        val path: String?,
        val complement: String?,
        val district: String,
        val city: String,
        val postalCode: String?,
        val country: String,
        val agent: String
)