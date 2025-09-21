package com.engineeringbay.pumpcontrol

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class RegistrationActivity : AppCompatActivity() {

    private lateinit var etDeviceNumber: EditText
    private lateinit var btnSave: MaterialButton
    private lateinit var btnCancel: MaterialButton
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREFS_NAME = "PumpControlPrefs"
        private const val KEY_DEVICE_NUMBER = "device_number"
        const val DEFAULT_DEVICE_NUMBER = "+1234567890"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Enable back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Change Device Number"

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Initialize views
        initializeViews()

        // Load current device number
        loadCurrentDeviceNumber()

        // Set click listeners
        setClickListeners()
    }

    private fun initializeViews() {
        etDeviceNumber = findViewById(R.id.etDeviceNumber)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)
    }

    private fun loadCurrentDeviceNumber() {
        val currentNumber = sharedPreferences.getString(KEY_DEVICE_NUMBER, DEFAULT_DEVICE_NUMBER)
        etDeviceNumber.setText(currentNumber)
    }

    private fun setClickListeners() {
        btnSave.setOnClickListener {
            saveDeviceNumber()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveDeviceNumber() {
        val deviceNumber = etDeviceNumber.text.toString().trim()

        if (deviceNumber.isEmpty()) {
            Toast.makeText(this, "Please enter a device number", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidPhoneNumber(deviceNumber)) {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
            return
        }

        // Save to SharedPreferences
        sharedPreferences.edit()
            .putString(KEY_DEVICE_NUMBER, deviceNumber)
            .apply()

        Toast.makeText(this, "Device number updated successfully", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Basic phone number validation
        return phoneNumber.matches(Regex("^\\+?[1-9]\\d{1,14}$"))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun getDeviceNumber(context: Context): String {
            val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(KEY_DEVICE_NUMBER, DEFAULT_DEVICE_NUMBER) ?: DEFAULT_DEVICE_NUMBER
        }
    }
}