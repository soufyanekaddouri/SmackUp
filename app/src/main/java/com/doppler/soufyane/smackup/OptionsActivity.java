package com.doppler.soufyane.smackup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    Switch autoMessage;
    // SAVE AND CANCEL
    Button cancel;
    Button saveSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        autoMessage = (Switch) findViewById(R.id.autoMessageSwitch);

        cancel = (Button) findViewById(R.id.cancelButton);
        saveSettings = (Button) findViewById(R.id.saveSettingsButton);
    }

    public void saveButtonListener(View v) {
        // CODE
        finish();
    }

    public void automaticMessaging(View v) {
        autoMessage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (autoMessage.isChecked()) {
                    Toast.makeText(OptionsActivity.this,"Automatic Messaging Enabled",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OptionsActivity.this,"Automatic Messaging Disabled",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void cancelButtonListener(View v) { finish();}
}
