package com.openclassrooms.realestatemanager.propertyList.models

class PropertyModelProcessed(val propertyId: Int,
                             val path: String? = "Path unknown",
                             val type: String,
                             val district: String?,
                             val price: String)