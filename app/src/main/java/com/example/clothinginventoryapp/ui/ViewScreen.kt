package com.example.clothinginventoryapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clothinginventoryapp.model.Clothing
import com.example.clothinginventoryapp.persistence.ClothingEvent
import com.example.clothinginventoryapp.persistence.SortType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewScreen(navController: NavController,
               state: ClothingState,
               onEvent: (ClothingEvent) -> Unit) {
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
                                Row(modifier = Modifier.clickable {
                                    onEvent(ClothingEvent.SortClothes(sortType))
                                },
                                verticalAlignment = CenterVertically) {
                                    RadioButton(selected = state.currentSortType == sortType,
                                        onClick = {
                                            onEvent(ClothingEvent.SortClothes(sortType))
                                        })
                                    Text(text = sortType.name)
                                }
                            }
                        }

                    }
                    items(state.clothes) { clothing ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(20.dp, 0.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "${clothing.name}, ${clothing.price}, size: ${clothing.size} ",
                                    fontSize = 20.sp
                                )
                                Text(text = clothing.category.toString(), fontSize = 12.sp)
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