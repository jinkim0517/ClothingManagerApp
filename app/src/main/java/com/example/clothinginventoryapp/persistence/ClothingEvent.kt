package com.example.clothinginventoryapp.persistence

import com.example.clothinginventoryapp.model.Clothing
import com.example.clothinginventoryapp.model.ClothingCategory

sealed interface ClothingEvent {
    object SaveClothing: ClothingEvent

    data class SetName(val name: String): ClothingEvent
    data class SetPrice(val price: Double): ClothingEvent
    data class SetSize(val size: String): ClothingEvent
    data class SetCategory(val category: ClothingCategory): ClothingEvent

    data class SortClothes(val sortType: SortType): ClothingEvent

    data class DeleteClothing(val clothing: Clothing): ClothingEvent
}