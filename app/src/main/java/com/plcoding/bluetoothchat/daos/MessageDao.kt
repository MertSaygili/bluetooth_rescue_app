package com.plcoding.bluetoothchat.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.plcoding.bluetoothchat.entities.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAll(): List<Message>

    @Query("SELECT * FROM message WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Message>

    //@Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
    //        "last_name LIKE :last LIMIT 1")
    //fun findByName(first: String, last: String): Message

    @Insert
    fun insertAll(vararg msgs: Message)

    @Delete
    fun delete(msg: Message)

    @Query("DELETE FROM message")
    fun deleteAll();
}