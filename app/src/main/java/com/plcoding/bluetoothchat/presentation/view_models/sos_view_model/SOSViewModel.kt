package com.plcoding.bluetoothchat.presentation.view_models.sos_view_model

import android.os.Handler
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bluetoothchat.domain.chat.BluetoothController
import com.plcoding.bluetoothchat.domain.chat.BluetoothDevice
import com.plcoding.bluetoothchat.presentation.view_models.bluetooth_view_model.BluetoothUiState
import com.plcoding.bluetoothchat.util.constants.MACAddresses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SOSViewModel @Inject constructor(private val bluetoothController: BluetoothController): ViewModel() {
    // view model for SOS messages
    private val _state = MutableStateFlow(SOSUiState())
    val state = combine(bluetoothController.scannedDevices, bluetoothController.pairedDevices, _state) { scannedDevices, pairedDevices, state  ->
        state.copy(
            scannedDevices = scannedDevices,
            pairedDevices = pairedDevices,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)


     fun searchDevice(state: BluetoothUiState) {
         _state.update { it.copy(isLoading = true) }
         bluetoothController.startDiscovery()

         Handler().postDelayed(
             {
                 // will search nearby arduino devices for two seconds
                 _state.update { it.copy(
                     devices = filterBluetoothDevices(state.pairedDevices) + filterBluetoothDevices(state.scannedDevices)
                 ) }
                 check()
             }, 3000
         )
     }

    private fun check() {
        if ((_state.value.devices).isEmpty()) {
            // arduino device not found
            _state.update { it.copy(notFindAnyDevice = true, isLoading = false) }
        } else {
            // arduino device found
            _state.update { it.copy(isFindDevice = true, isLoading = false) }
        }
    }

    private fun filterBluetoothDevices(bluetoothDevices : List<BluetoothDevice>) : List<BluetoothDevice> {
        // gets all paired devices and nearby devices
        // then filtering them
        // takes only arduino devices
        val macAddresses = MACAddresses.macAddressOfArduinos
        var filteredBluetoothDevices : List<BluetoothDevice> = emptyList()
        for( device in bluetoothDevices) {
            if(macAddresses.contains(device.address)){
                filteredBluetoothDevices = filteredBluetoothDevices + device
            }
        }
        print(filteredBluetoothDevices)
        return filteredBluetoothDevices
    }

     fun clearState() {
        _state.update {
            it.copy(
                isLoading = false,
                isFindDevice = false,
                notFindAnyDevice  = null,
                messageSend = false,
            )
        }
     }
}