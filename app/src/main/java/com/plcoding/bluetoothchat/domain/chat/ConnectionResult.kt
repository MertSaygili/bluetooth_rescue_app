package com.plcoding.bluetoothchat.domain.chat

import com.plcoding.bluetoothchat.domain.chat.models.BluetoothMessage

// results of bluetooth device decide tree
sealed interface ConnectionResult {
    object ConnectionEstablished : ConnectionResult
    data class TransferSucceeded(val message: BluetoothMessage) : ConnectionResult
    data class Error(val message: String) : ConnectionResult
}