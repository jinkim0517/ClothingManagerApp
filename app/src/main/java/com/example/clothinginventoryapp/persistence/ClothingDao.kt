package com.example.clothinginventoryapp.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.clothinginventoryapp.model.Clothing
import com.example.clothinginventoryapp.model.ClothingCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothingDao {
    @Upsert
    suspend fun upsertClothing(clothing: Clothing)

    @Delete
    suspend fun deleteClothing(clothing: Clothing)

    @Query("SELECT * FROM clothing ORDER BY name ASC")
    fun getClothesOrderedByName(): Flow<List<Clothing>>

    @Query("SELECT * FROM clothing ORDER BY price ASC")
    fun getClothesOrderedByPrice(): Flow<List<Clothing>>

    @Query("SELECT * FROM clothing WHERE category == :cat")
    fun getClothesByCategory(cat: ClothingCategory): Flow<List<Clothing>>
}
