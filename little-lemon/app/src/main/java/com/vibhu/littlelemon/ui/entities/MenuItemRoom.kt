package com.vibhu.littlelemon.ui.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_item")
data class MenuItemRoom(
    @PrimaryKey val id: Int,
    val title: String="",
    val price: Double=-1.0,
    val description: String ="",
    val imageUrl: String= "",
    val category: String ="",
    val imageLocalPath: String = "",
    val imageHash: String = "",
    val dataLastUpdated: Long = -1
){
    companion object{
        fun MenuItemRoom?.isTheSameAs(menuItemRoom: MenuItemRoom?): Boolean{
            return if(menuItemRoom == null && this == null){
                true
            }
            else if (menuItemRoom == null || this == null){
                false
            }
            else {
                this.id == menuItemRoom.id &&
                this.title == menuItemRoom.title &&
                this.price == menuItemRoom.price &&
                this.description == menuItemRoom.description &&
                this.imageUrl == menuItemRoom.imageUrl &&
                this.imageHash ==menuItemRoom.imageHash
            }
        }
        fun MenuItemRoom?.isNotTheSameAs(menuItemRoom: MenuItemRoom?): Boolean{
            return if(menuItemRoom==null && this == null){
                true
            }
            else if (menuItemRoom==null || this ==null){
                true
            } else {
                this.id != menuItemRoom.id ||
                this.title != menuItemRoom.title ||
                this.price != menuItemRoom.price ||
                this.description != menuItemRoom.description ||
                this.imageUrl != menuItemRoom.imageUrl
            }
        }
    }
}
