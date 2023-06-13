package com.example.clothinginventoryapp.model

// A top clothing item with a name, price, category, list of sizes and number of sales
class Top(name: String, price: Double, size: String) : Clothing(name, price, size) {
    // Getters
    override val type: String
        get() = "Top"
}