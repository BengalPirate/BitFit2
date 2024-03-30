package com.example.bitfit.ui // Use the appropriate package name

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.data.local.database.DrinkEntry // Adjust based on your project structure
import com.example.bitfit.R // Ensure this import matches your project
import java.util.Locale

class DrinkEntryAdapter(private var entries: List<DrinkEntry>) : RecyclerView.Adapter<DrinkEntryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.date_text)
        val amountTextView: TextView = view.findViewById(R.id.amount_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.drink_entry_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = entries[position]
        holder.dateTextView.text = item.date
        holder.amountTextView.text = String.format(Locale.getDefault(), "%.2f liters", item.amount)
    }

    override fun getItemCount() = entries.size

    fun updateEntries(entries: List<DrinkEntry>) {
        this.entries = entries
        notifyDataSetChanged()
    }

}
