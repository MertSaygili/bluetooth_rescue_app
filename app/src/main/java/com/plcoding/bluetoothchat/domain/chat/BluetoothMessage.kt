package com.plcoding.bluetoothchat.domain.chat

// data class for bluetooth messages
data class BluetoothMessage(
    val message: String,
    val senderName: String,
    val isFromLocalUser: Boolean
)
