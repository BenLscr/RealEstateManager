package com.openclassrooms.realestatemanager.form.addForm.models

import android.content.Context
import com.openclassrooms.realestatemanager.form.media.models.FormPhotoAndWording

class AddFormModelRaw(
        val listFormPhotoAndWording: MutableList<FormPhotoAndWording>,
        val path: String,
        val complement: String,
        val district: String,
        val city: String,
        val postalCode: String,
        val country: String,
        val price: String,
        val description: String,
        val type: String,
        val surface: String,
        val rooms: String,
        val bathrooms: String,
        val bedrooms: String,
        val fullNameAgent: String,
        val school: Boolean,
        val commerces: Boolean,
        val park: Boolean,
        val subways: Boolean,
        val train: Boolean,
        val available: Boolean,
        val entryDate: Long,
        val context: Context
)