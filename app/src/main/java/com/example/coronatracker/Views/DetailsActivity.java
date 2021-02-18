package com.example.coronatracker.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.Models.Country;
import com.example.coronatracker.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private TextView txtviewcases, txtviewdeaths, txtviewuserrating,
            txtviewnotes, txtviewcntryname;
    private ImageView imgviewCountry;
    private Button btnback, btnedit;
    private Country country;
    private MenuView.ItemView itemView;
    private RecyclerView.ViewHolder viewHolder;
    private static final int R_C_edit = 2;
    private Country cntry;
    private ArrayList<Country> Allcountries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //setup references
        txtviewcases = (TextView) findViewById(R.id.txtviewCasesdetails);
        txtviewdeaths = (TextView) findViewById(R.id.txtviewdeathsdetails);
        txtviewuserrating = (TextView) findViewById(R.id.txtviewuserratingdetails);
        txtviewnotes = (TextView) findViewById(R.id.txtviewusernotesdetails);
        txtviewcntryname = findViewById(R.id.txtviewcntrynamedetails);
        //viewHolder.itemView.getex

        imgviewCountry = (ImageView) findViewById(R.id.imageviewdetails);
        btnback = (Button) findViewById(R.id.btnbackdetails);
        btnedit = (Button) findViewById(R.id.btneditdetails);

        Gson json = new Gson();

        final Intent intent = getIntent();
        final Country country = json.fromJson(intent.getStringExtra("Country"),
                Country.class);
        cntry = country;

        txtviewcases.setText("Cases: " + country.cases);
        txtviewdeaths.setText("Deaths: " + country.deaths);
        txtviewuserrating.setText("Rating: "+ country.rating);
        txtviewcntryname.setText(country.name);
        txtviewnotes.setText(country.notes);

        //int resId = getResources().getIdentifier(country.imageResourceId.toLowerCase(),
         //       "drawable", getPackageName());
        //imgviewCountry.setImageResource(resId);

        if (savedInstanceState != null)        {
            country.notes = savedInstanceState.getString("Notes");
            country.rating = savedInstanceState.getString("Ratings");
        }

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gobacktoMain();
                finish();
            }
        });

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(country);
                finish();
            }
        });

    }

    private void startActivityForResult(Country country) {
        Intent intents = new Intent(this, EditActivity.class);
        Gson json = new Gson();
        intents.putExtra("DetailsToEdit", json.toJson(country));
        startActivityForResult(intents, R_C_edit);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Activity.RESULT_OK){
            if (resultCode == RESULT_OK){
                Gson json = new Gson();
                final Country country = json.fromJson(data.getStringExtra("EditToDetails"),
                        Country.class);


                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("DetailsToMain", json.toJson(country));
                setResult(RESULT_OK, intent);
                finish();
            }
        }

    }

    public void gobacktoMain(){
        setResult(RESULT_CANCELED);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        bundle.putString("Notes", country.notes);
        bundle.putString("Ratings", country.rating);
        super.onSaveInstanceState(bundle);
    }
}