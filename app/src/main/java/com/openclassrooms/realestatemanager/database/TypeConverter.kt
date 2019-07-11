package com.openclassrooms.realestatemanager.database

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.models.Type

class TypeConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromType(type: Type): Int {
            return type.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toType(int: Int): Type {
            return Type.values()[int]
        }
    }
}