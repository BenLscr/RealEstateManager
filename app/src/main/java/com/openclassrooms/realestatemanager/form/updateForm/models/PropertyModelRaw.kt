package com.openclassrooms.realestatemanager.form.updateForm.models

class PropertyModelRaw (
        val id: Int,
        val type: String,
        val price: String,
        val surface: String,
        val rooms: String,
        val bedrooms: String,
        val bathrooms: String,
        val description: String,
        val available: Boolean,
        val entryDate: Long,
        val saleDate: Long,
        val addressId: Int,
        val fullNameAgent: String
)