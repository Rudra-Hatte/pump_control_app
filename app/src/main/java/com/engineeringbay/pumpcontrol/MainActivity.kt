package com.engineeringbay.pumpcontrol

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SMS_PERMISSION_REQUEST_CODE = 101
    }

    private lateinit var statusDisplay: TextView
    private lateinit var statusIcon: ImageView
    private lateinit var statusCardLayout: LinearLayout
    private lateinit var btnPumpOn: MaterialButton
    private lateinit var btnPumpOff: MaterialButton
    private lateinit var btnAutoMode: MaterialButton
    private lateinit var btnDataLog: MaterialButton

    private var isPumpOn = false
    private var isAutoMode = false

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize database helper
        databaseHelper = DatabaseHelper(this)

        // Initialize views
        initializeViews()

        // Set click listeners
        setClickListeners()

        // Request SMS permissions
        requestSMSPermission()

        // Update initial status
        updatePumpStatus()
    }

    private fun initializeViews() {
        statusDisplay = findViewById(R.id.statusDisplay)
        statusIcon = findViewById(R.id.statusIcon)
        statusCardLayout = findViewById(R.id.statusCardLayout)
        btnPumpOn = findViewById(R.id.btnPumpOn)
        btnPumpOff = findViewById(R.id.btnPumpOff)
        btnAutoMode = findViewById(R.id.btnAutoMode)
        btnDataLog = findViewById(R.id.btnDataLog)
    }

    private fun setClickListeners() {
        btnPumpOn.setOnClickListener {
            sendPumpCommand(SMSManager.COMMAND_PUMP_ON, "PUMP_COMMAND", "ON")
            isPumpOn = true
            updatePumpStatus()
        }

        btnPumpOff.setOnClickListener {
            sendPumpCommand(SMSManager.COMMAND_PUMP_OFF, "PUMP_COMMAND", "OFF")
            isPumpOn = false
            updatePumpStatus()
        }

        btnAutoMode.setOnClickListener {
            isAutoMode = !isAutoMode
            val command = if (isAutoMode) SMSManager.COMMAND_AUTO_ON else SMSManager.COMMAND_AUTO_OFF
            val status = if (isAutoMode) "ON" else "OFF"

            sendPumpCommand(command, "AUTO_MODE", status)
            updateAutoModeButton()
        }

        btnDataLog.setOnClickListener {
            val intent = Intent(this, DataLogActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sendPumpCommand(smsCommand: String, logAction: String, logStatus: String) {
        // Send SMS command
        SMSManager.sendSMS(this, smsCommand)

        // Log to database
        val log = PumpLog(logAction, logStatus, DatabaseHelper.getCurrentTimestamp(), "User initiated")
        databaseHelper.insertLog(log)

        Toast.makeText(this, "$logAction command sent successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun updatePumpStatus() {
        if (isPumpOn) {
            statusDisplay.text = "ON"
            statusCardLayout.setBackgroundColor(Color.parseColor("#27ae60"))
        } else {
            statusDisplay.text = "OFF"
            statusCardLayout.setBackgroundColor(Color.parseColor("#e74c3c"))
        }
    }

    private fun updateAutoModeButton() {
        btnAutoMode.text = if (isAutoMode) "AUTO MODE: ON" else "AUTO MODE: OFF"
    }

    private fun requestSMSPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS
                ),
                SMS_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "SMS permission denied. App may not work properly.", Toast.LENGTH_LONG).show()
            }
        }
    }
}