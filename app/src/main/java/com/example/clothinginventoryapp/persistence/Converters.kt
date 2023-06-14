package com.example.clothinginventoryapp.persistence

import android.net.Uri
import androidx.room.TypeConverter
import com.example.clothinginventoryapp.model.ClothingCategory

class Converters {
    @TypeConverter
    fun fromString(value: String): ClothingCategory {
        when(value) {
            "TOP" -> {
                return ClothingCategory.TOP
            }
            "OUTERWEAR" -> {
                return ClothingCategory.OUTERWEAR
            }
            "BOTTOM" -> {
                return ClothingCategory.BOTTOM
            }
            "FOOTWEAR" -> {
                return ClothingCategory.FOOTWEAR
            }
            else -> {
                return ClothingCategory.ACCESSORY
            }
        }
    }
    @TypeConverter
    fun toString(category: ClothingCategory): String {
        return category.toString()
    }
}