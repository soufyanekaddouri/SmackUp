package com.doppler.soufyane.smackup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsActivity extends AppCompatActivity {

    Button cancel;
    Button saveSettings;

    Intent goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        cancel = (Button) findViewById(R.id.cancelButton);
        saveSettings = (Button) findViewById(R.id.saveSettingsButton);

        goBack = new Intent(this, MainActivity.class);
    }

    public void cancelButtonListener(View v) {
        startActivity(goBack);
    }



}
