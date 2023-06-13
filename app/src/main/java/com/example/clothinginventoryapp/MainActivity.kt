package com.example.clothinginventoryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.clothinginventoryapp.persistence.ClothingDatabase
import com.example.clothinginventoryapp.ui.ClothingViewModel
import com.example.clothinginventoryapp.ui.Navigation
import androidx.compose.runtime.getValue




class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ClothingDatabase::class.java,
            "clothes.db"
        ).build()
    }

    private val viewModel by viewModels<ClothingViewModel> (
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ClothingViewModel(db.dao) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            Navigation(state = state, event = viewModel::onEvent)
        }
    }
}