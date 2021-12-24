package com.example.piknikuy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.piknikuy.App
import com.example.piknikuy.model.ModelHotel
import com.example.piknikuy.model.ModelResto
import com.example.piknikuy.model.ModelWisata

@Database(
    entities = [ModelResto::class, ModelHotel::class, ModelWisata::class],
    version = 1,
    exportSchema = false
)
abstract class PiknikuyDatabase : RoomDatabase() {

    abstract fun restoDao(): RestoDao
    abstract fun hotelDao(): HotelDao
    abstract fun wisataDao(): WisataDao

    companion object {

        @Volatile
        private var INSTANCE: PiknikuyDatabase? = null
        private lateinit var appContext: Context

        fun getDatabase(context: Context? = null): PiknikuyDatabase {
            appContext = context ?: App.context

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