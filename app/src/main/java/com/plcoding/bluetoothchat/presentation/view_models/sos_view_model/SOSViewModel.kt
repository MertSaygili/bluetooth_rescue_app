package com.plcoding.bluetoothchat.presentation.view_models.sos_view_model

import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bluetoothchat.domain.chat.SOSMessageController
import com.plcoding.bluetoothchat.domain.chat.models.BluetoothDevice
import com.plcoding.bluetoothchat.presentation.location_controller.LocationController
import com.plcoding.bluetoothchat.util.constants.MACAddresses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SOSViewModel @Inject constructor(private val sosMessageController: SOSMessageController): ViewModel() {
    // view model for SOS message
    // private val bluetoothController: BluetoothController
    private val _state = MutableStateFlow(SOSUiState())
    val state = combine(sosMessageController.foundDevices, _state) { devices, state ->
        state.copy(
            devices = devices
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(4000), _state.value)


    private fun startDiscovery() {
        sosMessageController.startDiscovery()
    }

    private fun stopDiscovery() {
        sosMessageController.stopDiscovery()
    }

    fun searchDevice() {
         _state.update { it.copy(isLoading = true) }
         startDiscovery()

         Handler().postDelayed(
             {
                 // will search nearby arduino devices for four seconds
                 val devices: List<BluetoothDevice> = filterBluetoothDevices(sosMessageController.foundDevices.value)

                 _state.update { it.copy(
                     devices = devices
                 ) }

                 check()
             }, 4000
         )
     }

    fun connectToDevice(device : BluetoothDevice) {
        Log.d("Success", "before function call...")
        sosMessageController.connectToDevice(device = device)
        sosMessageController.sendLocation("sss")
        sosMessageController.closeSocketConnection()

    }

    private fun check() {
        if ((_state.value.devices).isEmpty()) {
            // arduino device not found
            _state.update { it.copy(notFindAnyDevice = true, isLoading = false, devices = _state.value.devices) }
        } else {
            // arduino device found
            _state.update { it.copy(isFindDevice = true, isLoading = false, devices = _state.value.devices) }
        }
        stopDiscovery()
    }

     fun filterBluetoothDevices(bluetoothDevices : List<BluetoothDevice>) : List<BluetoothDevice> {
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
}