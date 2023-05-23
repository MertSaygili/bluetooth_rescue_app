package com.plcoding.bluetoothchat.presentation.sos_view_model

import com.plcoding.bluetoothchat.domain.chat.BluetoothDevice


data class SOSUiState (
    var scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
    var devices : List<BluetoothDevice> = emptyList(),
    val isLoading: Boolean = true,
    val isFindDevice: Boolean = false,
    val notFindAnyDevice : Boolean = true,
    val errorMessage: String? = null,
    val messageSend: String? = null,
)