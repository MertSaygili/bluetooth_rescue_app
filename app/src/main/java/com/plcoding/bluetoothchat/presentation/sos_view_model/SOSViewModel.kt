package com.plcoding.bluetoothchat.presentation.sos_view_model

import android.os.Handler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bluetoothchat.domain.chat.BluetoothController
import com.plcoding.bluetoothchat.domain.chat.BluetoothDevice
import com.plcoding.bluetoothchat.presentation.bluetooth_view_model.BluetoothUiState
import com.plcoding.bluetoothchat.util.constants.MACAddresses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SOSViewModel @Inject constructor(private val bluetoothController: BluetoothController): ViewModel() {
    // view model for SOS messages
    private val _state = MutableStateFlow(SOSUiState())
    val state = combine(bluetoothController.scannedDevices, bluetoothController.pairedDevices, _state) {
            scannedDevices, pairedDevices, state ->
        state.copy(
            scannedDevices = scannedDevices,
            pairedDevices = pairedDevices,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)


     fun searchDevice(state: BluetoothUiState){
         bluetoothController.startDiscovery()
         // will search nearby arduino devices for three seconds
         val pairedDevices : List<BluetoothDevice> = filterBluetoothDevices(state.pairedDevices)
         val scannedDevices : List<BluetoothDevice> = filterBluetoothDevices(state.scannedDevices)

         _state.value.devices = pairedDevices + scannedDevices


         Handler().postDelayed({
             if((_state.value.devices).isEmpty()) {
                 // arduino device not found
                 _state.update { it.copy(notFindAnyDevice = false, isFindDevice = false, isLoading = false) }
             }
             else{
                 // arduino device found
                 _state.update { it.copy(isFindDevice = true, isLoading = false) }
             }
         }, 2000)
     }

    private fun filterBluetoothDevices(bluetoothDevices : List<BluetoothDevice>) : List<BluetoothDevice> {
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