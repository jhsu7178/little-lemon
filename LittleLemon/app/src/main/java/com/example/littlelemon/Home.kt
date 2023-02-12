package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun Home(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.logo
                ),
                contentDescription = "Logo Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(150.dp)
                    .padding(10.dp)
                    .align(Alignment.Center)
            )

            Image(
                painter = painterResource(
                    id = R.drawable.profile
                ),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .align(Alignment.TopEnd)
                    .clickable {
                        navController.navigate(Profile.route)
                    }
            )
        }

        Text(
            text = "Home Screen"
        )

        Button(
            onClick = {
                //navController.navigate(Profile.route)
            }

        ) {
            Text(
                text = "Click"
            )
        }
    }
}