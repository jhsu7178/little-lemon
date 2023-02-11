package com.example.littlelemon

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController



@Composable
fun OnboardingScreen(navController: NavController) {

    var firstName by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var lastName by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.logo),
            contentDescription = "Logo Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(150.dp)
                .padding(10.dp)
        )

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            modifier = Modifier.fillMaxWidth()
                .background(Color.Green)
                .height(100.dp)
                .padding(10.dp)

        ) {
            Text(
                text = "Let's get to know you",
                color = Color.White,
                fontSize = 32.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "Personal information"
            )
        }


        TextField(
            value = firstName,
            onValueChange = {
                firstName = it
            },
            label = { Text(text = "First Name") },
            modifier = Modifier.padding(10.dp),

        )

        TextField(
            value = lastName,
            onValueChange = {
                lastName = it
            },
            label = { Text(text = "Last Name") },
            modifier = Modifier.padding(10.dp)
        )

        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = { Text(text = "Email") },
            modifier = Modifier.padding(10.dp)
        )

        Button (
            onClick = {
                if (firstName.text.isBlank()
                    || lastName.text.isBlank()
                    || email.text.isBlank() ) {

                    Toast.makeText(context,
                    "Registration unsuccessful. Please enter all data",
                        Toast.LENGTH_LONG).show()
                } else {
                    sharedPreferences.edit()
                        .putString("firstName", firstName.text)
                        .putString("lastName", lastName.text)
                        .putString("email", email.text)
                        .putBoolean("onboardingComplete", true).apply()
                    navController.navigate(Home.route)

                }
            },

            colors = ButtonDefaults.buttonColors(Color.Yellow),
            modifier = Modifier
                .padding(10.dp)
                .height(40.dp)
                .width(200.dp)
        ) {

            Text (
                text = "Register",
                color = Color.Black

            )
        }
    }
}