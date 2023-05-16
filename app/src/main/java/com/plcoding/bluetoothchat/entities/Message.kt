package com.plcoding.bluetoothchat.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "sender") val sender: String?,
    @ColumnInfo(name = "isMe") val isMe: Boolean?


){
    constructor(content: String, sender: String,isMe:Boolean) : this(0, content, sender,isMe)
}
