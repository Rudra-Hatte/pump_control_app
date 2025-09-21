package com.engineeringbay.pumpcontrol

import android.content.Context
import android.util.Log
import android.widget.Toast

object SmsHandler {

    private const val TAG = "SmsHandler"

    fun handleConfirmationSMS(context: Context, messageBody: String) {
        Log.d(TAG, "Processing confirmation SMS: $messageBody")
        
        // Clean the message body to get just the 4-bit code
        val confirmationCode = messageBody.trim()
        
        when (confirmationCode) {
            SMSManager.COMMAND_PUMP_ON -> {
                // Pump ON confirmed
                logConfirmedAction(context, "PUMP_COMMAND", "ON", "Pump turned ON via SMS confirmation")
                showConfirmationToast(context, "Pump turned ON successfully")
            }
            SMSManager.COMMAND_PUMP_OFF -> {
                // Pump OFF confirmed  
                logConfirmedAction(context, "PUMP_COMMAND", "OFF", "Pump turned OFF via SMS confirmation")
                showConfirmationToast(context, "Pump turned OFF successfully")
            }
            SMSManager.COMMAND_AUTO_ON -> {
                // Auto Mode ON + Pump ON confirmed
                logConfirmedAction(context, "AUTO_MODE", "ON", "Auto mode enabled with pump ON via SMS confirmation")
                showConfirmationToast(context, "Auto mode enabled with pump ON")
            }
            SMSManager.COMMAND_AUTO_OFF -> {
                // Auto Mode + Pump OFF confirmed
                logConfirmedAction(context, "AUTO_MODE", "OFF", "Auto mode with pump OFF via SMS confirmation")
                showConfirmationToast(context, "Auto mode enabled with pump OFF")
            }
            else -> {
                Log.w(TAG, "Unknown confirmation code received: $confirmationCode")
                Toast.makeText(context, "Unknown confirmation received: $confirmationCode", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logConfirmedAction(context: Context, action: String, status: String, sensorData: String) {
        val databaseHelper = DatabaseHelper(context)
        val log = PumpLog(action, status, DatabaseHelper.getCurrentTimestamp(), sensorData)
        databaseHelper.insertLog(log)
        
        Log.d(TAG, "Logged confirmed action: $action - $status")
    }

    private fun showConfirmationToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}