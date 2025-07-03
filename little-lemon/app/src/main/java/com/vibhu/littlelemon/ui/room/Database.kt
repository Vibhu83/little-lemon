package com.vibhu.littlelemon.ui.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vibhu.littlelemon.ui.entities.MenuItemRoom
import com.vibhu.littlelemon.ui.keys.ApplicationKeys

@Database(entities = [MenuItemRoom::class], version = 1, exportSchema = false)
abstract class LittleLemonDatabase : RoomDatabase(){
    abstract fun menuItemDao(): MenuItemDao

    companion object {
        @Volatile
        private var INSTANCE: LittleLemonDatabase? = null

        fun getInstance(context: Context): LittleLemonDatabase {
            // Double-checked locking to ensure thread safety
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LittleLemonDatabase::class.java,
                    ApplicationKeys.database_name
                )
                    .fallbackToDestructiveMigration() // optional: clear data on version mismatch
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}