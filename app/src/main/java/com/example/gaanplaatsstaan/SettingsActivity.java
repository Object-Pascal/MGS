package com.example.gaanplaatsstaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import DataTier.Database.DatabaseManager;
import PresentationTier.Fragments.Setting.Settings;

public class SettingsActivity extends AppCompatActivity {

    private boolean isSwitchOn;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        databaseManager = new DatabaseManager(this);
        Settings settings = databaseManager.getSettingsFromDB();

        Switch colorBlindSwitch = findViewById(R.id.colorSwitch);
        colorBlindSwitch.setChecked(settings.isColorBlindmode());

        colorBlindSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    isSwitchOn = true;
                }else{
                    isSwitchOn = false;
                }

            }
        });
    }

    public void onSaveClick(View v) {

        Settings settings = new Settings("NL", isSwitchOn);
        databaseManager.insertSettingsIntoDB(settings);

        Intent intent = new Intent(this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
