package com.example.clothinginventoryapp.model

import java.util.Collections

// An inventory of clothing with a list of clothes
class InventorySingleton  // Creates a new list of clothes to be used as an inventory.
private constructor() {
    var inventory = mutableListOf<Clothing>()

    // MODIFIES: this
    // EFFECTS: adds a clothing item to the inventory
    fun addClothing(c: Clothing) {
        inventory += c
    }

    // REQUIRES: clothing to be removed must be in the inventory already
    // MODIFIES: this
    // EFFECTS: searches for and removes a piece of clothing from the inventory
    fun removeClothing(name: String, type: String, size: String) {
        val toRemove = findClothingIndex(name, type, size)
        if (toRemove === null) {
            // TODO
        } else {
            inventory.removeAt(toRemove)
        }
    }

    // EFFECTS: Searches the inventory for a piece of clothing
    fun findClothingIndex(name: String, type: String, size: String): Int? {
        for((index, c) in inventory.withIndex()) {
            if (name == c.name && type == c.type && size == c.size) {
                return index
            }
        }
        return null
    }

    fun reverseOrder() {
        inventory.reverse()
    }

    // Getters
    fun getClothingAt(index: Int): Clothing {
        return inventory[index]
    }

    val size: Int
        get() = inventory.size

    companion object {
        val instance = InventorySingleton()
    }
}