package com.doppler.soufyane.smackup;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // MATERIAL DESIGN FLOATING ACTION BUTTON
    FloatingActionButton addContact;
    // ALL INTENTS
    Intent OptionsIntent;
    Intent pickContact;
    // SMS MANAGER
    SmsManager sendSMS;
    // CONTACTS LIST
    ListView contacts;
    // RANDOM MESSAGES

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ALL VARIABLES DECLARED HERE
        addContact = (FloatingActionButton) findViewById(R.id.addContactView);
        OptionsIntent = new Intent(this, OptionsActivity.class);
        pickContact    = new Intent(Intent.ACTION_PICK);
        pickContact.setType(ContactsContract.Contacts.CONTENT_TYPE);
        sendSMS = SmsManager.getDefault();
        contacts = (ListView) findViewById(R.id.contactsView);

    }
    // ADD A CONTACT FOR AUTOMATIC SMS SENDING
    public void addContactListener(View v) {
        startActivity(pickContact);
//        sendSMS.sendTextMessage("+31624257825", "+31637305151", "Test",);
    }







    // MENU AND OPTIONS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // WHEN CLICKED ON SELECTED CHOICE:
        if (id == R.id.action_options) { startActivity(OptionsIntent); }

        if (id == R.id.action_about) {
            Toast.makeText(this, "Developer: Soufyane Kaddouri", Toast.LENGTH_LONG);
        }
        return super.onOptionsItemSelected(item);
    }
}