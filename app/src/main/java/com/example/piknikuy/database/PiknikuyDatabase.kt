package com.example.piknikuy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.piknikuy.App
import com.example.piknikuy.model.ModelResto

@Database(entities = [ModelResto::class], version = 1, exportSchema = false)
abstract class PiknikuyDatabase : RoomDatabase() {

    abstract fun restoDao(): RestoDao

    companion object {

        @Volatile
        private var INSTANCE: PiknikuyDatabase? = null
        private lateinit var appContext: Context

        fun getDatabase(context: Context? = null) : PiknikuyDatabase {
            appContext = context?: App.context

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext,
                    PiknikuyDatabase::class.java,
                    "piknikuy_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}