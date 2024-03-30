package com.example.bitfit.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import com.example.bitfit.data.local.database.AppDatabase
import com.example.bitfit.data.local.database.DrinkEntry
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    val allEntries: LiveData<List<DrinkEntry>>

    init {
        allEntries = db.drinkEntryDao().getAllEntries()
    }

    fun insertEntry(drinkEntry: DrinkEntry) {
        // Use a background thread for database operations
        viewModelScope.launch(Dispatchers.IO) {
            db.drinkEntryDao().insertEntry(drinkEntry)
        }
    }

}
