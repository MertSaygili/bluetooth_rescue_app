package com.plcoding.bluetoothchat.presentation.view_models.sos_view_model

import com.plcoding.bluetoothchat.domain.chat.BluetoothDevice
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface SOSController {

    val isConnected: StateFlow<Boolean>
    val devices: StateFlow<List<BluetoothDevice>>
    val errors: SharedFlow<String>
}