package com.example.bitfit.app

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.bitfit.R
import com.example.bitfit.ui.DrinkEntryAdapter
import com.example.bitfit.data.local.database.DrinkEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DrinkEntryAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupViewModel()
        setupFloatingActionButton()

        // Automatically show the dialog to add a new entry when the app starts
        showAddEntryDialog()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = DrinkEntryAdapter(emptyList())
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.allEntries.observe(this, { entries ->
            Log.d("MainActivity", "Entries observed: ${entries.size}")
            (recyclerView.adapter as DrinkEntryAdapter).updateEntries(entries)
        })
    }

    private fun setupFloatingActionButton() {
        val fab: FloatingActionButton = findViewById(R.id.fab_add_entry)
        fab.setOnClickListener {
            showAddEntryDialog()
        }
    }

    private fun showAddEntryDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_entry, null)
        val editTextWaterIntake = dialogView.findViewById<EditText>(R.id.editTextWaterIntake)
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.add_new_entry))
            .setView(dialogView)
            .setPositiveButton(getString(R.string.submit)) { dialog, _ ->
                val waterIntake = editTextWaterIntake.text.toString().toDoubleOrNull()
                if (waterIntake != null) {
                    val newEntry = DrinkEntry(date = getCurrentDate(), amount = waterIntake)
                    viewModel.insertEntry(newEntry)
                    Toast.makeText(this, "Water intake added: $waterIntake", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
