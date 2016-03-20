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
    // CONTACT INFO
    String contactName;
    int      contactNumber;
    static final int PICK_CONTACT=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ALL VARIABLES DECLARED HERE
        addContact = (FloatingActionButton) findViewById(R.id.addContactView);

        OptionsIntent = new Intent(this, OptionsActivity.class);

        pickContact    = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        pickContact.setType(ContactsContract.Contacts.CONTENT_TYPE);

        sendSMS = SmsManager.getDefault();
         // AN ARRAY OF CONTACTS
        String[] contactsArray = {};

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
        startActivityForResult(pickContact, PICK_CONTACT);

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
                            contactName = phones.getString(phones.getColumnIndex("data1"));
                            System.out.println("number is:"+contactName);
                        }
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                    }
                }
                break;
        }
    }




}