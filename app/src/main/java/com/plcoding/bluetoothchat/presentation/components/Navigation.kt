package com.plcoding.bluetoothchat.presentation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.util.constants.Strings
import com.plcoding.bluetoothchat.presentation.bluetooth_view_model.BluetoothViewModel
import com.plcoding.bluetoothchat.presentation.components.screen.*


@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable(route = Strings.splash_route_name){
            SplashScreen(navController = navController)
        }
        composable(route = Strings.home_route_name){
            val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            HomeScreen(navController, fusedLocationClient)
        }
        composable(route = Strings.bluetooth_devices_route_name) {
            val viewModel = hiltViewModel<BluetoothViewModel>()
            val state by viewModel.state.collectAsState()
            LaunchedEffect(key1 = state.errorMessage) {
                state.errorMessage?.let{
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }

            LaunchedEffect(key1 = state.isConnected) {
                if(state.isConnected) {
                    Toast.makeText(context, "You are connected", Toast.LENGTH_LONG).show()

                }
            }

            when{
                state.isConnecting -> {
                    LoadingScreen()
                }
                state.isConnected -> {
                    ChatScreen(
                        state = state,
                        onDisconnect = viewModel::disconnectFromDevice,
                        onSendMessage = viewModel::sendMessage,
                        context = context
                    )
                }
                else -> {
                    DeviceScreen(
                        state = state,
                        onStartScan = viewModel::startScan,
                        onStopScan = viewModel::stopScan,
                        onStartServer = viewModel::waitForIncomingConnections,
                        connectToDevice = viewModel::connectToDevice,
                        context = context
                    )
                }
            }
        }


    }

}
