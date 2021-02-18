package com.example.coronatracker.Models;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.coronatracker.Database.CountryDatabase;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountryRepository {

    private CountryDatabase db;
    private ExecutorService executorService;
    private LiveData<ArrayList<Country>> countries;

    public CountryRepository(Application app) {
        db = CountryDatabase.getDatabase(app.getApplicationContext());
        executorService = Executors.newSingleThreadExecutor();
        countries = db.countryDAO().getall();
    }

    public LiveData<ArrayList<Country>> getCountries() {return countries;}

}
