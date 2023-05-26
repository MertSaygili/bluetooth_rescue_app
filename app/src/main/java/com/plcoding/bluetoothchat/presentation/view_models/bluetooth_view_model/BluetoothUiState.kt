package com.plcoding.bluetoothchat.presentation.view_models.bluetooth_view_model

import com.plcoding.bluetoothchat.domain.chat.BluetoothDevice
import com.plcoding.bluetoothchat.domain.chat.BluetoothMessage

data class BluetoothUiState (
    // devices control
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
    // device connection
    val isConnected: Boolean = false,
    val isConnecting: Boolean = false,
    // error statement
    val errorMessage: String? = null,
    // bluetooth messages
    val messages: List<BluetoothMessage> = emptyList()
)
