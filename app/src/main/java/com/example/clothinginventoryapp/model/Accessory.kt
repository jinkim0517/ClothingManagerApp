package com.example.clothinginventoryapp.model

class Accessory(name: String, price: Double, size: String) : Clothing(name, price, size) {
    // Getters
    override val type: String
        get() = "Accessory"
}