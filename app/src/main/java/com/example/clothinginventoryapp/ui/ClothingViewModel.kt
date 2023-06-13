package com.example.clothinginventoryapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clothinginventoryapp.model.Clothing
import com.example.clothinginventoryapp.model.ClothingCategory
import com.example.clothinginventoryapp.persistence.ClothingDao
import com.example.clothinginventoryapp.persistence.ClothingEvent
import com.example.clothinginventoryapp.persistence.SortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class ClothingViewModel(
    private val dao: ClothingDao
): ViewModel() {
    private val _state = MutableStateFlow(ClothingState())
    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _clothes = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.NAME -> dao.getClothesOrderedByName()
                SortType.PRICE -> dao.getClothesOrderedByPrice()
                SortType.SIZE -> dao.getClothesOrderedByName() // TODO ADD SORTING
                SortType.CATEGORY -> dao.getClothesOrderedByName()// TODO ADD SORTING
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _sortType, _clothes) {
        state, sortType, clothes ->
            state.copy(clothes = clothes, currentSortType = sortType)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ClothingState())

    fun onEvent(event: ClothingEvent) {
        when(event) {
            is ClothingEvent.DeleteClothing -> {
                viewModelScope.launch {
                    dao.deleteClothing(event.clothing)
                }
            }
            ClothingEvent.SaveClothing -> {
                val name = state.value.name
                val price = state.value.price
                val category = state.value.category
                val size = state.value.size

                val clothing = Clothing(
                    name = name,
                    price = price,
                    category = category,
                    size = size
                )
                viewModelScope.launch {
                    dao.upsertClothing(clothing)
                }
                _state.update {
                    it.copy(
                        name = "",
                        price = 0.0,
                        category = ClothingCategory.UNSPECIFIED,
                        size = ""
                    )
                }
            }
            is ClothingEvent.SetCategory -> {
                _state.update {
                    it.copy(category = event.category)
                }
            }
            is ClothingEvent.SetName -> {
                _state.update {
                    it.copy(name = event.name)
                }
            }
            is ClothingEvent.SetPrice -> {
                _state.update {
                    it.copy(price = event.price)
                }
            }
            is ClothingEvent.SetSize -> {
                _state.update {
                    it.copy(size = event.size)
                }
            }
            is ClothingEvent.SortClothes -> {
                _sortType.value = event.sortType
            }
        }
    }
}