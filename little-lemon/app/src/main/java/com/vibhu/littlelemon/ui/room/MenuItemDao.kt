package com.vibhu.littlelemon.ui.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vibhu.littlelemon.ui.entities.MenuItemRoom

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM menu_item")
    fun getAll(): LiveData<List<MenuItemRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg menuItems: MenuItemRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(menuItem: MenuItemRoom)

    @Query("SELECT * FROM menu_item WHERE id==:id")
    fun getMenuItemById(id: Int): MenuItemRoom

    @Query("SELECT (SELECT COUNT(*) FROM menu_item) == 0")
    fun isEmpty(): Boolean
}