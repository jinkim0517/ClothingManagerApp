package com.example.clothinginventoryapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.clothinginventoryapp.model.Clothing

@Database(
    entities = [Clothing::class],
    version = 1
)

abstract class ClothingDatabase: RoomDatabase() {
    abstract val dao: ClothingDao
}