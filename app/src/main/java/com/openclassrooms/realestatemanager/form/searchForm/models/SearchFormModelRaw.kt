package com.openclassrooms.realestatemanager.form.searchForm.models

class SearchFormModelRaw(
        var district: String = "",
        var city: String = "",
        var postalCode: String = "",
        var country: String = "",
        var minPrice: String = "",
        var maxPrice: String = "",
        var type: String = "",
        var minSurface: String = "",
        var maxSurface: String = "",
        var rooms: String = "",
        var bathrooms: String = "",
        var bedrooms: String = "",
        var school: Boolean = false,
        var commerces: Boolean = false,
        var park: Boolean = false,
        var subways: Boolean = false,
        var train: Boolean = false,
        var availability: Boolean? = null,
        var dateLong: Long = 0
)