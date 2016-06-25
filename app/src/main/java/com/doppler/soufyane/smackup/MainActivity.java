package com.doppler.soufyane.smackup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
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


import com.bugsnag.android.Bugsnag;
import com.bugsnag.android.Client;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addContact;
    Intent OptionsIntent;
    Intent pickContact;
    SmsManager sendSMS;
    ListView contactsList;
    ListAdapter contactsAdapter;
    // GET CONTACT INFO VARIABLES
    String hasPhoneNumber;
    String contactName;
    String contactNumber;
    String id;
    Cursor c;
    Cursor phones;
    Uri contactData;

    static final int PICK_CONTACT=1;
    ArrayList contacts;

    AlertDialog.Builder reportError;
    String errorMessage;

    SmsManager smsManager = SmsManager.getDefault();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bugsnag.init(this);

        pickContact    = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        pickContact.setType(ContactsContract.Contacts.CONTENT_TYPE);
        addContact     = (FloatingActionButton) findViewById(R.id.addContactView);
        OptionsIntent  = new Intent(this, OptionsActivity.class);
        contacts       = new ArrayList<Contact>();
        contactsList   = (ListView) findViewById(R.id.contactsView);

        reportError = new AlertDialog.Builder(this);

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pickedContact = String.valueOf(contactsList.getItemAtPosition(position));
                Toast.makeText(MainActivity.this, pickedContact, Toast.LENGTH_LONG).show();
            }
        });


    }

    // ADD A CONTACT (Material Design Button)
    public void addContactListener(View v) {
        startActivityForResult(pickContact, PICK_CONTACT);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) { getMenuInflater().inflate(R.menu.menu_main, menu); return true; }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_options) {
            startActivity(new Intent(getApplicationContext(), OptionsActivity.class).putExtra("contactName", contactName).putExtra("contactNumber", contactNumber));
        }
        // WHEN YOU CLICK ON ABOUT , YOU SEE MY NAME
        if (id == R.id.action_about) { Toast.makeText(this, "Developer: Soufyane Kaddouri", Toast.LENGTH_LONG).show(); } return super.onOptionsItemSelected(item);}

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    contactData        = data.getData();
                    c                  = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        id             = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        hasPhoneNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        errorContaining();
                    }
                }
                break;
        }
    }

    public Boolean errorContaining() {
        // CHECK IF CONTACT HAS PHONE NUMBER, 1 = TRUE, 0 = FALSE
        if (hasPhoneNumber.equalsIgnoreCase("1")) {
            phones = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id, null, null); phones.moveToFirst();

            contactName       = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contactNumber     = phones.getString(phones.getColumnIndex("data1")).toString();
            contactsAdapter   = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_multiple_choice, contacts);
            contactsList.setAdapter(contactsAdapter);
            contacts.add(new Contact(contactName, contactNumber));

            return false;
        }

        if (hasPhoneNumber.equalsIgnoreCase("0")) {
            errorMessage = "No Phone Number Found!";
            reportThisError();
            return true;
        }
        return false;
    }



    // SELF MADE METHOD
    public void reportThisError() {
        reportError.setMessage(errorMessage)
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "CONTACT DEVELOPER FOR HELP", Toast.LENGTH_LONG);
                    }
                })
                .setNegativeButton("Close App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
            }
        });
        reportError.create().show();
    }

    public void sendMessage() {
        smsManager.sendTextMessage("phoneNo", null, "sms message", null, null);

    }


}