package com.engineeringbay.pumpcontrol

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

object SmsHandler {

    private const val TAG = "SmsHandler"
    
    // Intent action for status updates
    const val ACTION_STATUS_UPDATE = "com.engineeringbay.pumpcontrol.STATUS_UPDATE"
    const val EXTRA_PUMP_STATUS = "pump_status"
    const val EXTRA_AUTO_STATUS = "auto_status"

    fun handleConfirmationSMS(context: Context, messageBody: String) {
        Log.d(TAG, "Processing confirmation SMS: $messageBody")
        
        // Clean the message body to get just the 4-bit code
        val confirmationCode = messageBody.trim()
        
        when (confirmationCode) {
            SMSManager.COMMAND_PUMP_ON -> {
                // Pump ON confirmed
                logConfirmedAction(context, "PUMP_COMMAND", "ON", "Pump turned ON via SMS confirmation")
                showConfirmationToast(context, "Pump turned ON successfully")
                broadcastStatusUpdate(context, true, false)
            }
            SMSManager.COMMAND_PUMP_OFF -> {
                // Pump OFF confirmed  
                logConfirmedAction(context, "PUMP_COMMAND", "OFF", "Pump turned OFF via SMS confirmation")
                showConfirmationToast(context, "Pump turned OFF successfully")
                broadcastStatusUpdate(context, false, false)
            }
            SMSManager.COMMAND_AUTO_ON -> {
                // Auto Mode ON + Pump ON confirmed
                logConfirmedAction(context, "AUTO_MODE", "ON", "Auto mode enabled with pump ON via SMS confirmation")
                showConfirmationToast(context, "Auto mode enabled with pump ON")
                broadcastStatusUpdate(context, true, true)
            }
            SMSManager.COMMAND_AUTO_OFF -> {
                // Auto Mode + Pump OFF confirmed
                logConfirmedAction(context, "AUTO_MODE", "OFF", "Auto mode enabled with pump OFF via SMS confirmation")
                showConfirmationToast(context, "Auto mode with pump OFF")
                broadcastStatusUpdate(context, false, true)
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
    
    private fun broadcastStatusUpdate(context: Context, pumpStatus: Boolean, autoStatus: Boolean) {
        val intent = Intent(ACTION_STATUS_UPDATE)
        intent.putExtra(EXTRA_PUMP_STATUS, pumpStatus)
        intent.putExtra(EXTRA_AUTO_STATUS, autoStatus)
        context.sendBroadcast(intent)
    }
}