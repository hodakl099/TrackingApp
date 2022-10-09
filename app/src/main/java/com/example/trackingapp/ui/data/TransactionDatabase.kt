package com.example.trackingapp.ui.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trackingapp.ui.data.converters.Converter
import com.example.trackingapp.ui.data.entity.Transaction

@Database(entities = [Transaction::class],
    version = 1,
    exportSchema = false,
    )
@TypeConverters(Converter::class)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun transactionDao() : TransactionDao

    companion object {
        @Volatile
        private var INSTANCE : TransactionDatabase? = null

        fun getDatabase(context : Context): TransactionDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDatabase::class.java,
                    "all_transactions"
                ).build()

                INSTANCE = instance

                return instance
            }
        }


    }

}