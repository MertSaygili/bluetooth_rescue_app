package com.plcoding.bluetoothchat.presentation.components.common_components.dialogs

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.domain.chat.BluetoothDevice
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomBluetoothDeviceRow
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomDivider
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomIconButton
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomLargeText
import com.plcoding.bluetoothchat.presentation.view_models.sos_view_model.SOSUiState
import com.plcoding.bluetoothchat.presentation.view_models.sos_view_model.SOSViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun ShowArduinoDevicesDialog(arduinoDevices: List<BluetoothDevice>, stateSOS: SOSUiState, closeButton: () -> Unit) {
    when{
       stateSOS.isLoading -> {
            // searching for arduino device
            LoadingDialog(titleID = R.string.searching_arduino_devices, closeButton)
        }
        stateSOS.isFindDevice -> {
            BaseDialog(arduinoDevices = stateSOS.devices) {
                closeButton()
            }
        }
    }
}

@Composable
fun BaseDialog(arduinoDevices: List<BluetoothDevice>, closeButton: () -> Unit) {
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

                            }
                        }
                    }
                }
            }
        }
    )
}
