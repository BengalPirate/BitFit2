package com.example.bitfit.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.R
import com.example.bitfit.data.local.database.DrinkEntry
import com.example.bitfit.ui.DrinkEntryAdapter
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d("FragmentLifecycle", "MainFragment - onCreateView")
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MainFragmentLog", "onViewCreated - MainFragment")

        // Initialize your ViewModel here
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        // Setup UI elements
        setupUI(view)
    }


    private fun setupUI(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.my_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = DrinkEntryAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.allEntries.observe(viewLifecycleOwner) { entries ->
            // Check if entries is not empty to avoid IndexOutOfBoundsException
            if (entries.isNotEmpty()) {
                // Update adapter with the last entry only
                adapter.updateEntries(listOf(entries.last()))
            } else {
                // Handle the case where there are no entries, if necessary
                adapter.updateEntries(emptyList())
            }
        }


        val editTextWaterIntake: EditText = view.findViewById(R.id.editTextWaterIntake)
        val buttonSubmit: Button = view.findViewById(R.id.buttonSubmit)
        buttonSubmit.setOnClickListener {
            Log.d("MainFragmentDebug", "Submit button clicked in MainFragment")
            val waterIntake = editTextWaterIntake.text.toString().toDoubleOrNull()
            if (waterIntake != null) {
                val newEntry = DrinkEntry(System.currentTimeMillis().toInt(), getCurrentDate(), waterIntake)
                viewModel.insertEntry(newEntry)
                editTextWaterIntake.text.clear()
                Toast.makeText(requireContext(), "Entry added: $waterIntake", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
