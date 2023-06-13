package com.example.clothinginventoryapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clothinginventoryapp.persistence.ClothingEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoveScreen(navController: NavController,
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
                    text = "Remove a item",
                    fontSize = 40.sp,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    )
}