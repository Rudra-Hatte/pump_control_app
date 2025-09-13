package com.engineeringbay.pumpcontrol

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DataLogActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: LinearLayout
    private lateinit var adapter: LogAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var logList: List<PumpLog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_log)

        // Enable back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView)
        emptyState = findViewById(R.id.emptyState)

        // Initialize database helper
        databaseHelper = DatabaseHelper(this)

        // Setup RecyclerView
        setupRecyclerView()

        // Load logs
        loadLogs()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadLogs() {
        logList = databaseHelper.getAllLogs()

        if (logList.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyState.visibility = View.VISIBLE
        } else {
            emptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            if (!::adapter.isInitialized) {
                adapter = LogAdapter(logList)
                recyclerView.adapter = adapter
            } else {
                adapter.updateLogs(logList)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh logs when returning to this activity
        loadLogs()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}