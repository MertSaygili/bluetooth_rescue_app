package com.plcoding.bluetoothchat.presentation.components.common_components.dialogs

import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomDivider
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomIconButton
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomLargeText
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomSmallButton
import com.plcoding.bluetoothchat.ui.theme.BluetoothChatTheme
import java.time.format.TextStyle

@Composable
fun CustomAlertDialog(confirmButton: () -> Unit) {
    // alert dialog
    // pop ups if user does not open location
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(id = R.string.warning), fontWeight = FontWeight.Bold) },
        text = { Text(stringResource(id = R.string.open_GPS)) },
        confirmButton = { CustomSmallButton(textId = R.string.ok, colorId = R.color.error) { confirmButton() } },
    )
}

@Composable
fun ShowArduinoDevicesDialog(arduinoDevices : List<BluetoothDevice>, closeButton: () -> Unit) {
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
                    LazyColumn(modifier = Modifier.padding(20.dp)) {
                        item{
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
                        items (arduinoDevices){

                        }
                    }
                }
            }
        }
    )
}