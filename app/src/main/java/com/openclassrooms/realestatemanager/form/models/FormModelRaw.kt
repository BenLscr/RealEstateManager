package com.openclassrooms.realestatemanager.form.models

import android.content.Context
import com.openclassrooms.realestatemanager.form.media.models.FormPhotoAndWording

class FormModelRaw(
        val listFormPhotoAndWording: MutableList<FormPhotoAndWording> = mutableListOf(),
        var path: String = "",
        var complement: String = "",
        var district: String = "",
        var city: String = "",
        var postalCode: String = "",
        var country: String = "",
        var price: String = "",
        var description: String = "",
        var type: String = "",
        var surface: String = "",
        var rooms: String = "",
        var bathrooms: String = "",
        var bedrooms: String = "",
        var fullNameAgent: String = "",
        var school: Boolean = false,
        var commerces: Boolean = false,
        var park: Boolean = false,
        var subways: Boolean = false,
        var train: Boolean = false,
        var available: Boolean = true,
        var entryDate: Long = 0,
        var context: Context? = null
)