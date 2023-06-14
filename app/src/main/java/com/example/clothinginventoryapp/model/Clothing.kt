package com.example.clothinginventoryapp.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

// A clothing item with a name, price, category, and number of sales
@Entity
data class Clothing(
    var name: String,
    var price: Double,
    var size: String,
    var category: ClothingCategory,
    var image: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)