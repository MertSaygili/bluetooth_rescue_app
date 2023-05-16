package com.plcoding.bluetoothchat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.bluetoothchat.daos.MessageDao
import com.plcoding.bluetoothchat.entities.Message

@Database(entities = [Message::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}