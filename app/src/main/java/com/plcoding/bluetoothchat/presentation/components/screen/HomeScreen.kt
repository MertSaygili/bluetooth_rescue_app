package com.plcoding.bluetoothchat.presentation.components.screen

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.*
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.util.constants.Strings
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomAppbar
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomHorizontalButton
import com.plcoding.bluetoothchat.presentation.components.common_components.dialogs.CustomAlertDialog
import com.plcoding.bluetoothchat.presentation.location_controller.LocationController

@Composable
fun HomeScreen(navController: NavController, fusedLocationClient: FusedLocationProviderClient) {

    // location launcher
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Log.d("Success", "Suan buradayim 1")
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Log.d("Success", "Suan buradayim 2")
                // Only approximate location access granted.
            } else -> {
            Log.d("Success", "Suan hicbir yerdeyim 0")
            // No location access granted.
            }
        }
    }

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }


//    val db = Room.databaseBuilder(
//        context,
//        AppDatabase::class.java, Strings.db_name
//    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    Scaffold(
        topBar = { CustomAppbar(context = null, title = stringResource(id = R.string.appbar_title),
            needAction = false,
            goBack = false,
            goBackFunction = {},
        ) }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomHorizontalButton(text = stringResource(id = R.string.send_SOS), colorId = R.color.error) {


//                    val messageDao = db.messageDao()
//                    val messages: List<Message> = messageDao.getAll()
//
//                    val message = Message(content="Selam",sender="Omer",isMe=true)
//                    messageDao.insertAll(message)
//                     messageDao.deleteAll()

//                    Log.d("Omer",messages.toString())

                    when (PackageManager.PERMISSION_GRANTED) {
                        // if fine location has been granted before
                        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) -> {
                            if(LocationController().checkGPSIsOn(context = context)) {
                                // GPS is On
                                LocationController().getCurrentCoordinates(fusedLocationClient)
                            }
                            else{
                                // GPS is off
                                // shows warning dialog
                                showDialog = true
                            }
                        }
                        else -> {
                            // Asking for permission
                            launcher.launch(arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION)
                            )
                        }
                    }
                }

                // connect with other devices button
                CustomHorizontalButton(text = stringResource(id = R.string.connect_with_devices), colorId = R.color.success) {
                    navController.navigate(Strings.bluetooth_devices_route_name)
                }

                // shows location alert, if gps is off, alert shows
                if(showDialog) {
                    CustomAlertDialog() {
                        showDialog = false
                    }
                }
            }
        }
    }
}

