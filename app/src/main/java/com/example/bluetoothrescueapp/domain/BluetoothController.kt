package com.example.bluetoothrescueapp.domain

import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    // StateFlow type is mutable type,
    // MutableType provides some optimization for app, large amount of changeable data
    val scannedDevices: StateFlow<List<BluetoothDevice>> // keep scanned devices
    val pairedDevices: StateFlow<List<BluetoothDevice>>  // keeps paired devices


    fun startDiscovery() // for looking new devices
    fun stopDiscovery() // stops discovery
    fun release() // free up all memory

}