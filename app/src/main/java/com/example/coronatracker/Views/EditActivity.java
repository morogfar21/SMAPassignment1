package com.example.coronatracker.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronatracker.Models.Country;
import com.example.coronatracker.R;
import com.google.gson.Gson;

public class EditActivity extends AppCompatActivity {

    private TextView txtviewcntryname;
    private EditText editText;
    private Button btnOk, btnCancel;
    private SeekBar seekBarRating;
    private int rating;
    Country cntry = null;
    //private Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // setting up variables
        editText = (EditText) findViewById(R.id.editTextUserinfo);
        btnCancel = (Button) findViewById(R.id.btncanceledit);
        btnOk = (Button) findViewById(R.id.buttonOk);
        seekBarRating = (SeekBar) findViewById(R.id.seekBarrating);
        txtviewcntryname = findViewById(R.id.txtviewcntrynameedit);


        //intent
        final Gson json = new Gson();
        Intent intent = getIntent();
        final Country country = json.fromJson(intent.getStringExtra("DetailsToEdit"),
                Country.class);

        cntry = country;
        editText.setText(country.notes);
        txtviewcntryname.setText(country.name);

        //Seekbar setup
                seekBarRating.setMax(10);
                final TextView seekBarValue = (TextView) findViewById(R.id.seekbarvaluetxtview);
                seekBarValue.setText(country.rating);
                seekBarRating.setProgress(Integer.parseInt(country.rating));

        seekBarRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rating  = progress;
                seekBarValue.setText(String.valueOf(rating));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoMainActivity();
                finish();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });
    }

    private void GotoMainActivity() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void Save() {
        cntry.rating = String.valueOf(rating);
        cntry.notes = editText.getText().toString();

        Gson gson = new Gson();
        Intent intent = new Intent();
        intent.putExtra("EditToDetails", gson.toJson(cntry));

        setResult(RESULT_OK, intent);
        finish();
    }


}