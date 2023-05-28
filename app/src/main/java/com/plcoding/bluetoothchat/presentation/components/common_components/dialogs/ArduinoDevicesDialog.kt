package com.plcoding.bluetoothchat.presentation.components.common_components.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.domain.chat.models.BluetoothDevice
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomBluetoothDeviceRow
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomDivider
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomIconButton
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomLargeText
import com.plcoding.bluetoothchat.presentation.view_models.sos_view_model.SOSUiState
import com.plcoding.bluetoothchat.util.constants.Strings

@Composable
fun ShowArduinoDevicesDialog(stateSOS: SOSUiState, filterFunction: (List<BluetoothDevice>) -> List<BluetoothDevice>, connectToDevice: (BluetoothDevice) -> Unit, closeButton: () -> Unit) {
    val devices: List<BluetoothDevice> = filterFunction(stateSOS.devices)
    BaseDialog(arduinoDevices = devices,  connectToDevice = connectToDevice) {
        closeButton()
    }
}

@Composable
fun BaseDialog(arduinoDevices: List<BluetoothDevice>, connectToDevice: (device: BluetoothDevice) -> Unit, closeButton: () -> Unit) {
    Dialog(
        onDismissRequest = {},
        content = {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(modifier = Modifier.padding(5.dp)) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CustomLargeText(title = stringResource(id = R.string.nearby_arduino_devices))
                                CustomIconButton(
                                    icon = Icons.Filled.Close,
                                    contentDescription = "close button",
                                    colorId = android.R.color.darker_gray,
                                    event = closeButton
                                )
                            }
                            CustomDivider()
                        }
                        items(arduinoDevices) { device ->
                            CustomBluetoothDeviceRow(deviceName = device.name, buttonTitle = stringResource(id = R.string.send_location), buttonColorId = R.color.success){
                                connectToDevice(device)
                            }
                            if(arduinoDevices.last() == device) {
                                Text(
                                    Strings.hintArduinoHelpText,
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 5.dp, bottom = 2.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
