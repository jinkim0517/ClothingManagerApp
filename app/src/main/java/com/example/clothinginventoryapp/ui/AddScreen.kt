package com.example.clothinginventoryapp.ui

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clothinginventoryapp.model.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController) {
    var name by remember {
        mutableStateOf("")
    }

    var price by remember {
        mutableStateOf("")
    }

    var size by remember {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var category by remember {
        mutableStateOf("---")
    }

    val snackbarState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    val catList = listOf<String>("Top", "Bottom", "Accessory", "Footwear")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
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
                },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)))
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()) {

                Spacer(modifier = Modifier.size(40.dp))

                Text(text = "Add an item",
                    fontSize = 40.sp,
                    modifier = Modifier.padding(innerPadding))

                Spacer(modifier = Modifier.size(120.dp))

                TextField(
                    value = name,
                    label = {
                        Text(text = "Name")
                    },
                    singleLine = true,
                    onValueChange = {
                        name = it
                    },
                )

                Spacer(modifier = Modifier.size(25.dp))

                TextField(
                    value = price,
                    label = {
                        Text(text = "Price")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        price = it
                    },
                )

                Spacer(modifier = Modifier.size(25.dp))

                TextField(
                    value = size,
                    label = {
                        Text(text = "Size")
                    },
                    singleLine = true,
                    onValueChange = {
                        size = it
                    },
                )

                Spacer(modifier = Modifier.size(25.dp))

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Text(text = "Category",
                        fontSize = 25.sp,
                    )

                    Spacer(modifier = Modifier.size(40.dp))

                    Box {
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            catList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(text = it)
                                    },
                                    onClick = {
                                        expanded = false
                                        category = it
                                    }
                                )
                            }
                        }

                        TextButton(onClick = { expanded = true }) {
                            Row {
                                Text(text = "$category ", fontSize = 25.sp)
                                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.size(100.dp))

                Button(
                    onClick = {
                        val inventory = InventorySingleton.instance
                        if (name === "" || price === "" || size === "" || category === "---") {
                            scope.launch {
                                snackbarHostState.showSnackbar("Please complete the fields!",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short)
                            }
                        } else {
                            try {
                                val priceConv = price.toDouble()
                                inventory.addClothing(makeClothing(name, priceConv, size, category))
                                scope.launch {
                                    snackbarHostState.showSnackbar("New clothing added!",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short)
                                }
                                navController.navigate(Screen.MainScreen.route)
                            } catch (e:NumberFormatException) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Please enter a number for the price!",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short)
                                }
                            }
                        }
                    }, modifier = Modifier.padding(30.dp, 10.dp)
                ) {
                    Text(text = "ADD", fontSize = 25.sp, modifier = Modifier.padding(30.dp, 10.dp))
                }
        }
    })
}

fun makeClothing(name : String, price : Double, size : String, category : String) : Clothing {
    when (category) {
        "Top" -> {
            return Top(name, price.toDouble(), size);
        }
        "Outerwear" -> {
            return Outerwear(name, price.toDouble(), size)
        }
        "Bottom" -> {
            return Bottom(name, price.toDouble(), size)
        }
        "Footwear" -> {
            return Footwear(name, price.toDouble(), size)
        }
    }
    return Accessory(name, price.toDouble(), size)
}