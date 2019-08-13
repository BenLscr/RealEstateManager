package com.openclassrooms.realestatemanager.propertyList.models

class PropertyModelProcessed(val propertyId: Int,
                             val path: String? = "unknown path",
                             val type: String,
                             val district: String? = "unknown district",
                             val price: String)