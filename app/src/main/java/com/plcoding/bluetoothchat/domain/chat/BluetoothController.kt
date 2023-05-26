package com.plcoding.bluetoothchat.domain.chat

import com.plcoding.bluetoothchat.domain.chat.models.BluetoothDevice
import com.plcoding.bluetoothchat.domain.chat.models.BluetoothMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    // interface for bluetooth functions and attributes

    val isConnected: StateFlow<Boolean>
    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>
    val errors: SharedFlow<String>

    fun startDiscovery()
    fun stopDiscovery()
    fun startBluetoothServer() : Flow<ConnectionResult>
    fun connectToDevice(device: BluetoothDevice) : Flow<ConnectionResult>
    fun disconnectFromBluetoothDevice(device: BluetoothDevice)
    suspend fun trySendMessage(message: String) : BluetoothMessage?
    fun closeConnection()
    fun release()
}