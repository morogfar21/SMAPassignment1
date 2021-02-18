package com.example.coronatracker.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coronatracker.Models.Country;

import java.util.ArrayList;

@Dao
public interface CountryDAO {
    @Query("SELECT * FROM Country")
    LiveData<ArrayList<Country>> getall();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCountry(Country country);

    @Update
    void updateCountries(Country country);


}
