package com.example.clothinginventoryapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clothinginventoryapp.model.Clothing
import com.example.clothinginventoryapp.model.InventorySingleton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewScreen(navController: NavController) {
    val clothes = InventorySingleton.instance.inventory
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
                    items(inventoryToStrings(clothes)) {
                        Text(
                            modifier = Modifier.padding(vertical = 24.dp),
                            text = it,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center
                        )
                        Divider()
                    }
                }
            }
        }
    )
}

fun inventoryToStrings(clothes: MutableList<Clothing>) : Array<String> {
    var stringForm = emptyArray<String>()
    if (clothes.isEmpty()) {

    } else {
        clothes.forEach {
            stringForm += (it.name + ", $" + it.price + ", size: " + it.size)
        }
    }

    return stringForm
}