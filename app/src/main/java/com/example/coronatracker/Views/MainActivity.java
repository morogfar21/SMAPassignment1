package com.example.coronatracker.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.CSVFile;
import com.example.coronatracker.FileAdapter;
import com.example.coronatracker.Models.Country;
import com.example.coronatracker.Models.CountryRepository;
import com.example.coronatracker.R;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FileAdapter.ICountryItemClickedListener {


    private Button exitBtn;
    FileAdapter fileAdapter;
    private CSVFile csvFile;
    private ArrayList<Country> Allcountries;
    private static final int R_C_details = 1;
    private CountryRepository repository;

    //Code inspiration:
    //https://javapapers.com/android/android-read-csv-file/
    //Lecture solutions

    //Intent references
    TextView txtviewcases, txtviewrating, txtviewcntryname, txtviewdeaths;
    ImageView imageviewcountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //references
        exitBtn = (Button) findViewById(R.id.exitButton);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // set up the RecyclerView
        fileAdapter = new FileAdapter(this, Allcountries, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(fileAdapter);
        loadCountries();
        fileAdapter.updateList(Allcountries);

        // Setup repository and livedata for country
        repository = new CountryRepository(getApplication());
        repository.getCountries().observe(this, new Observer<ArrayList<Country>>() {
            @Override
            public void onChanged(ArrayList<Country> countries) {
                //txtviewcntryname.setText();
            }
        });

    }
        int indexhack;
        @Override
        public void onCountryClicked(int index) {
            indexhack = index;
            //Country cntry= Allcountries.get(index);

            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            startActivityForResult(intent);
        }

    private void startActivityForResult(Intent intent) {
        Country cntry= Allcountries.get(indexhack);
        Intent intents = new Intent(this, DetailsActivity.class);
        Gson json = new Gson();
        intents.putExtra("Country", json.toJson(cntry));
        startActivityForResult(intents,R_C_details );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == R_C_details) {
            if (resultCode == RESULT_OK) {
                //Country cntry= Allcountries.get(indexhack);
                Gson json = new Gson();
                Intent intent = new Intent();
                final Country cntry = json.fromJson(data.getStringExtra("DetailsToMain"),
                        Country.class);
                intent.putExtra("Country", json.toJson(cntry));
                intent.putExtra("Index", indexhack);
                updateCountry(cntry);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    //Update countries in collection
    public void updateCountry(Country countryToUpdate) {
        for (int i = 0; i < Allcountries.size(); i++)
        {
            if (Allcountries.get(i).name.equals(countryToUpdate.name))
            {
                Country ctyArrList = Allcountries.get(indexhack);

                ctyArrList.rating = countryToUpdate.rating;
                ctyArrList.notes = countryToUpdate.notes;
                Allcountries.set(indexhack,ctyArrList);
            }
        }
    }

    public void loadCountries() {
        InputStream inputStream = getResources().openRawResource(R.raw.corona_stats);
        CSVFile csvFile = new CSVFile(inputStream);
        List countryList = csvFile.readFile();
        Allcountries = new ArrayList<Country>();

        for (Country cntry : Allcountries) {

            int resId = getResources().getIdentifier(cntry.imageResourceId.toLowerCase(),
                    "drawable", getPackageName());
            cntry.setImageResourceId(Integer.toString(resId));
        }


        for (int i = 0; i < countryList.size(); i++) {
            Object element = countryList.get(i);
            String[] listelements = (String[]) element;

            Country cntry = new Country(listelements[0],
                    listelements[2], listelements[3],
                    listelements[1].toLowerCase(), "","0");
            Allcountries.add(cntry);
        }
    }
}