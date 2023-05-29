package com.plcoding.bluetoothchat.data.chat

import com.plcoding.bluetoothchat.domain.chat.models.BluetoothMessage

fun BluetoothMessage.toByteArray() : ByteArray {
    return "$senderName#$message".encodeToByteArray()
}

fun String.toByteArray() : ByteArray{
    return this.encodeToByteArray()
}

fun String.toBluetoothMessage(isFromLocalUser: Boolean, sendDate: String) : BluetoothMessage {
    val senderName = substringBeforeLast("#")
    val message = substringAfter("#")
    return BluetoothMessage(senderName = senderName, message = message, isFromLocalUser =  isFromLocalUser, sendDate = sendDate)
}