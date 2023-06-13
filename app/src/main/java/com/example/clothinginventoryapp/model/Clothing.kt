package com.example.clothinginventoryapp.model

// A clothing item with a name, price, category, and number of sales
abstract class Clothing(var name: String, var price: Double, var size: String) {
    private val imgPath: String? = null
    abstract val type: String
}