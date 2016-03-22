package com.doppler.soufyane.smackup;

import android.app.Activity;
import android.app.VoiceInteractor;
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
    String contactName;
    String contactNumber;
    static final int PICK_CONTACT=1;
    ArrayList contacts;
    //comment

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

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() { @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String pickedContact = String.valueOf(contactsList.getItemAtPosition(position));
            Toast.makeText(MainActivity.this, pickedContact, Toast.LENGTH_LONG).show();
            }
        });

        Bugsnag.notify(new RuntimeException("Non-fatal"));
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
                    Uri contactData = data.getData();
                    Cursor c =  getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);

                            phones.moveToFirst();
                            contactName   = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            contactNumber = phones.getString(phones.getColumnIndex("data1"));
                            contacts.add(new Contact(contactName, contactNumber));
                            contactsAdapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_multiple_choice, contacts);
                            contactsList.setAdapter(contactsAdapter);
                        }
                    }
                }
                break;
        }
    }




    public void doubleCheck() {

    }








}
