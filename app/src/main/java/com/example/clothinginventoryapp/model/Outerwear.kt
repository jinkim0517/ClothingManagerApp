package com.example.clothinginventoryapp.model

// An outerwear clothing item with a name, price, category, list of sizes and number of sales
class Outerwear(name: String, price: Double, size: String) : Clothing(name, price, size) {
    // Getters
    override val type: String
        get() = "Outerwear"
}