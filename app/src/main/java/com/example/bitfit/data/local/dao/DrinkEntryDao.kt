package com.example.bitfit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.lifecycle.LiveData
import com.example.bitfit.data.local.database.DrinkEntry

@Dao
interface DrinkEntryDao {
    @Insert
    fun insertEntry(drinkEntry: DrinkEntry)

    @Query("SELECT * FROM drinkentry")
    fun getAllEntries(): LiveData<List<DrinkEntry>>
}
