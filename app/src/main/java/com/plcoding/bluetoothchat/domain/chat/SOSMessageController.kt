package com.plcoding.bluetoothchat.domain.chat

import com.plcoding.bluetoothchat.domain.chat.models.BluetoothDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface SOSMessageController {
    val foundDevices: StateFlow<List<BluetoothDevice>>
    val isConnected: StateFlow<Boolean>
    val errors: SharedFlow<String>

    // override functions
    fun stopDiscovery()
    fun startDiscovery()
    fun closeSocketConnection()
    fun connectToDevice(device: BluetoothDevice)
    fun sendLocation(location: String) : Boolean
}