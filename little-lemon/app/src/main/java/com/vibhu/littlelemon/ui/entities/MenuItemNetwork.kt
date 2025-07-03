package com.vibhu.littlelemon.ui.entities

import kotlinx.serialization.Serializable

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val image: String,
    val category: String
) {
    fun toMenuItemRoom(
        imageLocalPath: String = "",
        imageHash: String = "",
        dataLastChecked: Long = -1
    ) = MenuItemRoom(
        id = id,
        title = title,
        price = price,
        description = description,
        imageUrl = image,
        category = category,
        imageLocalPath = imageLocalPath,
        imageHash = imageHash,
        dataLastUpdated = dataLastChecked
    )
}
