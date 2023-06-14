package com.example.clothinginventoryapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clothinginventoryapp.persistence.ClothingEvent

@Composable
fun MainScreen(navController: NavController,
               state: ClothingState,
               onEvent: (ClothingEvent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Clothing Manager",
            fontSize = 40.sp,
            modifier = Modifier.padding(0.dp, 80.dp, 0.dp, 0.dp),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(200.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                          navController.navigate(Screen.ViewScreen.route)
                },
                modifier = Modifier
                    .padding(15.dp)
                    .width(300.dp),
            ) {
                Text(
                    text = "View Clothing",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

            Button(
                onClick = {
                    navController.navigate(Screen.AddScreen.route)
                },
                modifier = Modifier
                    .padding(15.dp)
                    .width(300.dp),
            ) {
                Text(
                    text = "Add Clothing",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

            Button(
                onClick = {
                    navController.navigate(Screen.RemoveScreen.route)
                },
                modifier = Modifier
                    .padding(15.dp)
                    .width(300.dp),
            ) {
                Text(
                    text = "Browse",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}