package com.example.bitfit.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.R
import com.example.bitfit.data.local.database.DrinkEntry
import java.util.Locale

class DrinkEntryAdapter(private var entries: List<DrinkEntry>) : RecyclerView.Adapter<DrinkEntryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dateTextView: TextView = view.findViewById(R.id.date_text)
        private val amountTextView: TextView = view.findViewById(R.id.amount_text)

        fun bind(entry: DrinkEntry) {
            dateTextView.text = entry.date
            amountTextView.text = String.format(Locale.getDefault(), "%.2f liters", entry.amount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.drink_entry_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entries[position])
    }

    override fun getItemCount(): Int = entries.size

    fun updateEntries(newEntries: List<DrinkEntry>) {
        this.entries = newEntries
        notifyDataSetChanged() // Consider using DiffUtil for more efficient updates
    }
}
