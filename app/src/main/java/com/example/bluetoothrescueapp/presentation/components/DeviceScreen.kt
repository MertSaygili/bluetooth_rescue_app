package com.example.bluetoothrescueapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.bluetoothrescueapp.domain.BluetoothDevice
import com.example.bluetoothrescueapp.presentation.BluetoothUiState

@Composable
fun DeviceScreen(
    state: BluetoothUiState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit
    ){
    Column (
        modifier = Modifier.fillMaxWidth()
            ) {
        Row  (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Button(onClick = onStartScan) {
                Text(text = "Start scan")
            }
            Button(onClick = onStopScan) {
                Text(text = "Stop scan")
            }
        }
    }
}

@Composable
fun BluetoothDeviceList(
    pairedDevices: List<BluetoothDevice>,
    scannedDevices: List<BluetoothDevice>,
    onClick: (BluetoothDevice) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item{
            Text("Paired Devices", fontWeight = FontWeight.Bold)
        }
        items(pairedDevices){
            Text(text = it.name.toString(), modifier = Modifier.fillMaxWidth().clickable { onClick(it)})
        }

        item{
            Text("Scanned Devices", fontWeight = FontWeight.Bold)
        }
        items(scannedDevices){
            Text(text = it.name.toString(), modifier = Modifier.fillMaxWidth().clickable { onClick(it)})
        }
    }
}