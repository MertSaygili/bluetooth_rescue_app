package com.plcoding.bluetoothchat.presentation.components.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.domain.chat.BluetoothDevice
import com.plcoding.bluetoothchat.presentation.bluetooth_view_model.BluetoothUiState
import com.plcoding.bluetoothchat.presentation.components.common_components.*

@Composable
fun DeviceScreen(
    state: BluetoothUiState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    onStartServer: () -> Unit,
    connectToDevice: (BluetoothDevice) -> Unit,
    context: Context
) {
    Scaffold(
        topBar = { CustomAppbar(context = context, title = stringResource(id = R.string.appbar_title),
            needAction = true,
            goBack = false
        ) }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                BluetoothDeviceList(
                    pairedDevices = state.pairedDevices,
                    scannedDevices = state.scannedDevices,
                    connectToDevice = connectToDevice,
                    modifier = Modifier.fillMaxWidth().weight(1f),
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    CustomSmallButton(textId = R.string.scan, colorId = R.color.success) { onStartScan() }
                    CustomSmallButton(textId = R.string.stop_scan, colorId = R.color.error) { onStopScan() }
                    CustomSmallButton(textId = R.string.open_server, colorId = R.color.success) { onStartServer() }
                }
            }
        }
    }

}
@Composable
fun BluetoothDeviceList(
    pairedDevices: List<BluetoothDevice>,
    scannedDevices: List<BluetoothDevice>,
    connectToDevice: (BluetoothDevice) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        // paired devices column
        item { CustomLargeText(title = stringResource(id = R.string.connected_devices)) }
        items(pairedDevices) { device ->
            CustomBluetoothDeviceRow(deviceName = device.name, buttonTitle = stringResource(id = R.string.connect), buttonColorId = R.color.success) {
                connectToDevice(device)
            }
        }

        // nearby devices column
        item { CustomLargeText(title = stringResource(id = R.string.nearby_devices)) }
        items(scannedDevices) { device ->
            CustomBluetoothDeviceRow(deviceName = device.name, buttonTitle = stringResource(id = R.string.connect_bluetooth), buttonColorId = R.color.success){
                connectToDevice(device)
            }
        }
    }
}