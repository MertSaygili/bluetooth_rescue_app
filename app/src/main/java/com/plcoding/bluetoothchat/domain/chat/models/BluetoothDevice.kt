package com.plcoding.bluetoothchat.domain.chat.models

typealias BluetoothDeviceDomain = BluetoothDevice

// data class for bluetooth device
data class BluetoothDevice(
    val name: String?,
    val address: String
)
