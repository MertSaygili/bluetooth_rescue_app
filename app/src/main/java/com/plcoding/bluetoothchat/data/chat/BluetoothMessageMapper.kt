package com.plcoding.bluetoothchat.data.chat

import com.plcoding.bluetoothchat.domain.chat.models.BluetoothMessage

fun BluetoothMessage.toByteArray() : ByteArray {
    return "$senderName#$message".encodeToByteArray()
}

fun String.toBluetoothMessage(isFromLocalUser: Boolean) : BluetoothMessage {
    val senderName = substringBeforeLast("#")
    val message = substringAfter("#")
    return BluetoothMessage(senderName = senderName, message = message, isFromLocalUser =  isFromLocalUser)
}