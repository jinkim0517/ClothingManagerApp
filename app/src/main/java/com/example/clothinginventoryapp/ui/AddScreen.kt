package com.example.clothinginventoryapp.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.clothinginventoryapp.model.*
import com.example.clothinginventoryapp.persistence.ClothingEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController,
              state: ClothingState,
              onEvent: (ClothingEvent) -> Unit) {
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
        mutableStateOf(ClothingCategory.UNSPECIFIED)
    }

    val snackbarState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    val catList = ClothingCategory.values()

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri -> selectedImageUri = uri}
    )

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
        modifier = Modifier
            .fillMaxSize(),
        content = { innerPadding ->
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()) {
                Spacer(modifier = Modifier.size(40.dp))

                Text(
                    text = "Add an item",
                    fontSize = 40.sp,
                    modifier = Modifier.padding(innerPadding)
                )
                Spacer(modifier = Modifier.size(15.dp))

                Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(20.dp, 10.dp)) {

                    Button(onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                        Text(text = "Upload Image")
                    }

                    Spacer(modifier = Modifier.size(30.dp))

                    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.border(2.dp, color = Color.Gray)) {
                        item {
                            AsyncImage(
                                model = selectedImageUri,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(40.dp, 0.dp)
                            )
                            onEvent(ClothingEvent.SetImage(selectedImageUri.toString()))
                        }
                    }
                }

                Spacer(modifier = Modifier.size(20.dp))

                TextField(
                    value = name,
                    label = {
                        Text(text = "Name")
                    },
                    singleLine = true,
                    onValueChange = {
                        name = it
                        onEvent(ClothingEvent.SetName(name))
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
                        onEvent(ClothingEvent.SetPrice(price.toDouble()))
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
                        onEvent(ClothingEvent.SetSize(size))
                    },
                )

                Spacer(modifier = Modifier.size(25.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Category",
                        fontSize = 20.sp,
                    )

                    Spacer(modifier = Modifier.size(30.dp))

                    Box {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }) {
                            catList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(text = it.toString())
                                    },
                                    onClick = {
                                        expanded = false
                                        category = it
                                        onEvent(ClothingEvent.SetCategory(category))
                                    }
                                )
                            }
                        }

                        TextButton(onClick = { expanded = true }) {
                            Row {
                                Text(text = "$category ", fontSize = 20.sp)
                                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.size(15.dp))

                Button(
                    onClick = {
                        if (name === "" || price === "" || size === "") {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Please complete the fields!",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        } else {
                            try {
                                onEvent(ClothingEvent.SaveClothing)
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "New clothing added!",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                navController.navigate(Screen.MainScreen.route)
                            } catch (e: NumberFormatException) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Please enter a number for the price!",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }
                    }, modifier = Modifier.padding(30.dp, 10.dp)
                ) {
                    Text(
                        text = "ADD",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(30.dp, 10.dp)
                    )
                }
            }
        }
    )
}
