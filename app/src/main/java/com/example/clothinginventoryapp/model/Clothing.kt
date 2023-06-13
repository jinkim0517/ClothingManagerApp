package com.example.clothinginventoryapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// A clothing item with a name, price, category, and number of sales
@Entity
data class Clothing(
    var name: String,
    var price: Double,
    var size: String,
    var category: ClothingCategory,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
// TODO: private val imgPath: String? = null