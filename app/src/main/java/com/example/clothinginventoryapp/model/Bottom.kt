package com.example.clothinginventoryapp.model

// A bottom clothing item with a name, price, category, list of sizes and number of sales
class Bottom(name: String, price: Double, size: String) : Clothing(name, price, size) {
    // Getters
    override val type: String
        get() = "Bottom"
}