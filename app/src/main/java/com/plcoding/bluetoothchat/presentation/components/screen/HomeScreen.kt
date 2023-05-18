package com.plcoding.bluetoothchat.presentation.components.screen

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.plcoding.bluetoothchat.constants.Strings
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.plcoding.bluetoothchat.R
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomAppbar
import com.plcoding.bluetoothchat.presentation.components.common_components.CustomHorizontalButton
import com.plcoding.bluetoothchat.presentation.location_controller.LocationController

@Composable
fun HomeScreen(navController: NavController, fusedLocationClient: FusedLocationProviderClient) {
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


//    val db = Room.databaseBuilder(
//        context,
//        AppDatabase::class.java, Strings.db_name
//    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    Scaffold(
        topBar = { CustomAppbar(title = stringResource(id = R.string.appbar_title)) }
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

                    //TODO Izinden sonra konumu actirmam lazim

                    when (PackageManager.PERMISSION_GRANTED) {
                        //Check permission
                        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) -> {
                            Log.d("Success","calisacak mi acep")
                            LocationController().getCurrentCoordinates(fusedLocationClient)
                        }
                        else -> {
                            // Asking for permission
                            launcher.launch(arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION)
                            )
                            Log.d("Success", "eee kanka")
                        }
                    }
                }
                CustomHorizontalButton(text = stringResource(id = R.string.connect_with_devices), colorId = R.color.success) {
                    navController.navigate(Strings.main_route_name)
                }
            }
        }
    }
}

