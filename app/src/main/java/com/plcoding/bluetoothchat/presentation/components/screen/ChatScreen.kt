@file:OptIn(ExperimentalComposeUiApi::class)

package com.plcoding.bluetoothchat.presentation.components.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.presentation.bluetooth_view_model.BluetoothUiState
import com.plcoding.bluetoothchat.presentation.components.common_components.ChatMessage
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomAppbar


@Composable
fun ChatScreen(
    state: BluetoothUiState,
    onDisconnect: () -> Unit,
    context: Context,
    onSendMessage: (String) -> Unit
) {
    val message = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = { CustomAppbar(
            context = context,
            title = stringResource(id = R.string.appbar_title),
            needAction = true,
            goBack = true
        ) }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.messages) { message ->
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ChatMessage(
                                message = message,
                                modifier = Modifier.align(if(message.isFromLocalUser) Alignment.End else Alignment.Start)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = message.value,
                        onValueChange = { message.value = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text(text = stringResource(id = R.string.send_message)) }
                    )
                    IconButton(onClick = {
                        onSendMessage(message.value)
                        message.value = ""
                        keyboardController?.hide()
                    }) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send message")
                    }
                }
            }
        }
    }
}