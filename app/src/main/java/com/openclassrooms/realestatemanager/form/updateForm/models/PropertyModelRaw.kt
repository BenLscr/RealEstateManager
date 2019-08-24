package com.openclassrooms.realestatemanager.form.updateForm.models

class PropertyModelRaw (
        val type: Int,
        val price: String,
        val rooms: String,
        val bedrooms: String,
        val bathrooms: String,
        val description: String,
        val available: Boolean,
        val entryDate: String,
        val saleDate: String?,
        val path: String?,
        val complement: String?,
        val district: Int?,
        val city: Int?,
        val postalCode: String?,
        val country: Int?,
        val agent: Int
)