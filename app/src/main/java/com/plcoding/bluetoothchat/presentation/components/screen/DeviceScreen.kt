package com.plcoding.bluetoothchat.presentation.components.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.domain.chat.BluetoothDevice
import com.plcoding.bluetoothchat.presentation.components.bluetooth_vm.BluetoothUiState
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomResponsiveButton
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomLargeText
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomMediumText

@Composable
fun DeviceScreen(
    state: BluetoothUiState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    onStartServer: () -> Unit,
    connectToDevice: (BluetoothDevice) -> Unit,
    disconnectFromDevice: (BluetoothDevice) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BluetoothDeviceList(
            pairedDevices = state.pairedDevices,
            scannedDevices = state.scannedDevices,
            connectToDevice = connectToDevice,
            disconnectFromDevice = disconnectFromDevice,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = onStartScan) {
                CustomMediumText(title = stringResource(id = R.string.s))
            }
            Button(onClick = onStopScan) {
                Text(text = "Stop scan")
            }
            Button(onClick = onStartServer) {
                Text(text = "Start  server")
            }
        }
    }
}
@Composable
fun BluetoothDeviceList(
    pairedDevices: List<BluetoothDevice>,
    scannedDevices: List<BluetoothDevice>,
    connectToDevice: (BluetoothDevice) -> Unit,
    disconnectFromDevice: (BluetoothDevice) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            CustomLargeText(title = stringResource(id = R.string.connected_devices))
        }
        items(pairedDevices) { device ->
            CustomResponsiveButton(deviceName = device.name, buttonTitle = stringResource(id = R.string.cancel_bluetooth_connection), buttonColorId = R.color.error) {
                disconnectFromDevice(device)
            }
        }

        item {
            CustomLargeText(title = stringResource(id = R.string.nearby_devices))
        }
        items(scannedDevices) { device ->
            CustomResponsiveButton(deviceName = device.name, buttonTitle = stringResource(id = R.string.connect_bluetooth), buttonColorId = R.color.success){
                connectToDevice(device)
            }
        }
    }
}