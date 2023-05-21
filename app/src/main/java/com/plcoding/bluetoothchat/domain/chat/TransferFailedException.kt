package com.plcoding.bluetoothchat.domain.chat

import java.io.IOException

// exception class
class TransferFailedException : IOException("Reading incoming data failed")