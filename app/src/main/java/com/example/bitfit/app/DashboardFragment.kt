package com.example.bitfit.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.R
import com.example.bitfit.ui.DrinkEntryAdapter

class DashboardFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var entriesAdapter: DrinkEntryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("DashboardFragmentDebug", "onCreateView in DashboardFragment")
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("DashboardFragmentDebug", "onViewCreated in DashboardFragment")

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupRecyclerView(view)

        viewModel.allEntries.observe(viewLifecycleOwner) { entries ->
            Log.d("DashboardFragmentDebug", "Updating entries in DashboardFragment: ${entries.size} entries found")
            entriesAdapter.updateEntries(entries)
        }
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.entriesRecyclerView)
        entriesAdapter = DrinkEntryAdapter(emptyList())
        recyclerView.adapter = entriesAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        Log.d("DashboardFragmentDebug", "RecyclerView setup in DashboardFragment")
    }
}


