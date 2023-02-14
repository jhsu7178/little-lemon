package com.example.littlelemon

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MenuNetwork(
    @SerialName("menu")
    val menu: List<MenuItemNetwork>
)

@kotlinx.serialization.Serializable
data class MenuItemNetwork(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val desc: String,

    @SerialName("price")
    val price: Double,

    @SerialName("image")
    val image: String,

    @SerialName("category")
    val category: String
) {

    fun toMenuItemRoom() = MenuItemRoom(id, title, desc, price, image, category)

}