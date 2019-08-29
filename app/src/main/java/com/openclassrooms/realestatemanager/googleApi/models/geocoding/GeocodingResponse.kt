package com.openclassrooms.realestatemanager.googleApi.models.geocoding

class GeocodingResponse(
        val results: List<Result>,
        val status: String
)