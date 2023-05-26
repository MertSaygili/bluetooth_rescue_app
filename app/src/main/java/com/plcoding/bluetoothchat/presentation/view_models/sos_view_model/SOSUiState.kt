package com.plcoding.bluetoothchat.presentation.view_models.sos_view_model

import com.plcoding.bluetoothchat.domain.chat.BluetoothDevice


data class SOSUiState (
    // scanned and paired devices
    var scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
    // filtered devices
    var devices : List<BluetoothDevice> = emptyList(),
    // for loading indicator
    val isLoading: Boolean = false,
    // if finds arduino SOS devices, show dialog
    val isFindDevice: Boolean = false,
    // is not find any device, show TOAST message
    val notFindAnyDevice : Boolean? = null,
    // is message send successfully?
    val messageSend: Boolean = false,
    // if error occur show TOAST message, and error message content of it
    val errorMessage: String? = null,
)