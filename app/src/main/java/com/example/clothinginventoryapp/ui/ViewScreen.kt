package com.example.clothinginventoryapp.ui

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.TypeConverter
import coil.compose.AsyncImage
import com.example.clothinginventoryapp.model.Clothing
import com.example.clothinginventoryapp.model.ClothingCategory
import com.example.clothinginventoryapp.persistence.ClothingEvent
import com.example.clothinginventoryapp.persistence.SortType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewScreen(navController: NavController,
               state: ClothingState,
               onEvent: (ClothingEvent) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var category by remember {
        mutableStateOf(state.currentSortType)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextButton(onClick = {
                        navController.navigate(Screen.MainScreen.route)
                    }, content = {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Back", modifier = Modifier.size(25.dp))
                        Text(text = "Back", fontSize = 20.sp)
                    })
                }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)))
        },
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(40.dp))

                Text(
                    text = "Your Clothes",
                    fontSize = 40.sp,
                    modifier = Modifier.padding(innerPadding)
                )

                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            SortType.values().forEach { sortType ->
                                if (sortType == SortType.NAME || sortType == SortType.PRICE) {
                                    Row(
                                        modifier = Modifier.clickable {
                                            onEvent(ClothingEvent.SortClothes(sortType))
                                        },
                                        verticalAlignment = CenterVertically
                                    ) {
                                        RadioButton(selected = state.currentSortType == sortType,
                                            onClick = {
                                                onEvent(ClothingEvent.SortClothes(sortType))
                                            })
                                        Text(text = sortType.name)
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.size(40.dp))

                            Box {
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }) {
                                    SortType.values().forEach {
                                        if (it !== SortType.NAME && it !== SortType.PRICE) {
                                            DropdownMenuItem(
                                                text = {
                                                    Text(text = it.toString())
                                                },
                                                onClick = {
                                                    expanded = false
                                                    category = it
                                                    onEvent(ClothingEvent.SortClothes(it))
                                                }
                                            )
                                        }
                                    }
                                }

                                TextButton(onClick = { expanded = true }) {
                                    Row {
                                        if (state.currentSortType == SortType.NAME || state.currentSortType == SortType.PRICE) {
                                            Text(text = "-----")
                                        } else {
                                            Text(text = "$category ")
                                        }
                                        Icon(Icons.Default.KeyboardArrowUp, contentDescription = "")
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                    }

                    items(state.clothes) { clothing ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 0.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "${clothing.name}",
                                    fontSize = 20.sp
                                )
                                Text(text = "$${clothing.price} SIZE: ${clothing.size}, ${clothing.category}, ${clothing.image}", fontSize = 12.sp)

                                // image here
                                AsyncImage(
                                    model = Uri.parse(clothing.image),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .padding(40.dp)
                                )
                            }
                            IconButton(onClick = {
                                onEvent(ClothingEvent.DeleteClothing(clothing))
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete contact"
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}