package com.example.coronatracker.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.coronatracker.Models.Country;

@Database(entities = {Country.class}, version = 3)

public abstract class CountryDatabase extends RoomDatabase {

    public abstract CountryDAO countryDAO();
    private static CountryDatabase instance;

    public static CountryDatabase getDatabase(final Context context){
        if (instance == null){
            synchronized (CountryDatabase.class){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CountryDatabase.class, "country_database")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build();
            }
        }
        return instance;
    }

}
