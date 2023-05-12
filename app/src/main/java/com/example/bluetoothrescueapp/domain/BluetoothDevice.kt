package com.example.bluetoothrescueapp.domain

// for name conflict
typealias BluetoothDeviceDomain = BluetoothDevice

data class BluetoothDevice(
    val name: String?, // bluetooth device name
    val address: String // bluetooth mac address
)
