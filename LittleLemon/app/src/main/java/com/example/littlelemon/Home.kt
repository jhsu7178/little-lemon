package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.PrimaryColor1
import com.example.littlelemon.ui.theme.cloud


@Composable
fun Home(navController: NavController, database: AppDatabase?) {
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

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(Color(0xFF495E57))
        ) {

            Text(
                text = "Little Lemon",
                fontSize = 32.sp,
                color = Color(0xFFF4CE14),
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)

            )

            Text(
                text = "Chicago",
                fontSize = 24.sp,
                color = Color(0xFFFFFFFF),
                modifier = Modifier.padding(start = 20.dp)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "We are a family owned mediterranean restaurant, focused on" +
                            "traditional recipes served with a modern twist.",
                    fontSize = 21.sp,
                    color = Color.White,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(end = 20.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.hero),
                    contentDescription = "hero picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(160.dp)
                        .width(160.dp)
                        .clip(RoundedCornerShape(20.dp))

                )
            }

            val searchPhrase = searchBar()
            val filterType = foodCategories()

            if (database != null) {
                val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())

                val orderMenuItems by remember {
                    mutableStateOf(false)
                }

                var menuItems = if (orderMenuItems) {
                    databaseMenuItems.sortedBy { it.title }
                } else {
                    databaseMenuItems
                }

                if (filterType.isNotEmpty()) {
                    menuItems = menuItems.filter { it.category.equals(filterType, true) }
                }

                if (searchPhrase.isNotEmpty()) {
                    menuItems = menuItems.filter { it.title.contains(searchPhrase, true) }
                }

                MenuItemsList(menuItems)
            }
            else {
                MenuItemsList(emptyList())
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MenuItemsList(foodItems: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight()
            .background(Color.White)
    ) {
        items(
            items = foodItems,
            itemContent = { menuItem ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = menuItem.title,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            menuItem.desc,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = PrimaryColor1
                        )
                        Text(
                            text = "$ %.2f".format(menuItem.price),
                            color = PrimaryColor1,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(100.dp)
                    ) {
                        GlideImage(
                            model = menuItem.image,
                            contentDescription = "Food image",
                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp)
                        )
                    }
                }

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                )
            }
        )
    }
}

@Composable
fun searchBar(): String {
    var searchPhrase by remember {
        mutableStateOf("")
    }

    TextField(
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon"
            )
        },
        value = searchPhrase,
        onValueChange = { searchPhrase = it },
        label = {
            Text(
                text = "Enter search phrase"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
    )

    return searchPhrase
}

@Composable
fun foodCategories(): String {
    val categories : List<String> = listOf("Starters", "Mains", "Dessert", "Drinks")
    var filterType by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(Color.White)
    ) {
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        Text(
            text = "ORDER FOR DELIVERY!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        LazyRow{
            itemsIndexed(categories){_,category ->
                Button(
                    onClick = {
                        filterType = setFilterType(filterType, category)
                    },

                    colors = ButtonDefaults.buttonColors(backgroundColor = cloud),
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(
                        text = category
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
        )
    }

    return filterType
}

private fun setFilterType(filterType: String, type: String) = if (filterType == type) {
    ""
} else {
    type
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    LittleLemonTheme() {
        Home(navController, null)
    }
}

