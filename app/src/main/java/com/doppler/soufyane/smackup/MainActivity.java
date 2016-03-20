package com.doppler.soufyane.smackup;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.database.DataSetObserver;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
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
    ListView contactsList;
    ListAdapter contactsAdapter;




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
         // AN ARRAY OF CONTACTS
        String[] contactsArray = {"Soufyane", "Mootje", "asaskd"};

        contactsList = (ListView) findViewById(R.id.contactsView);
        contactsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsArray);
        contactsList.setAdapter(contactsAdapter);

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() { @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String pickedContact = String.valueOf(contactsList.getItemAtPosition(position));
            Toast.makeText(MainActivity.this, pickedContact, Toast.LENGTH_LONG).show();
            }
        });
    }




    // ADD A CONTACT (Material Design Button)
    public void addContactListener(View v) {
        startActivity(pickContact);
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
        if (id == R.id.action_about) { Toast.makeText(this, "Developer: Soufyane Kaddouri", Toast.LENGTH_LONG).show(); }
        return super.onOptionsItemSelected(item);
    }
}