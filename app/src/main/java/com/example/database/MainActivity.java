package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import android.text.format.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Contact currentContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentContact = new Contact();
        initToggleButton();
        initListButton();


        initTextChangeEvents();
        initSaveButton();
        setForEditing(false);
    }




    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        if(position != -1) {
            DataSource ds = new DataSource(this);
            try {
                ds.open();
                currentContact = ds.getContact(position+1);
                ds.close();
                EditText nameEdit = findViewById(R.id.editContact);
                nameEdit.setText(currentContact.getName());
                EditText addressEdit = findViewById(R.id.editAddress);
                addressEdit.setText(currentContact.getAddress());

            } catch (Exception e) {
                Toast.makeText(this, "Error accessing contact", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initListButton() {
        Button listButton = findViewById(R.id.buttonList);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    private void initTextChangeEvents() {
        EditText nameEdit = findViewById(R.id.editContact);
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentContact.setName(nameEdit.getText().toString());
                currentContact.setContactID(-1);
            }
        });
        EditText addressEdit = findViewById(R.id.editAddress);
        addressEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentContact.setAddress(addressEdit.getText().toString());
            }
        });



        EditText phoneNumberEdit = findViewById(R.id.editHome);
        phoneNumberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentContact.setPhoneNumber(phoneNumberEdit.getText().toString());
            }
        });



    }

    private void initToggleButton() {
        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setForEditing(toggleButton.isChecked());
            }
        });
    }
    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean wasSuccessful;
                DataSource ds = new DataSource(MainActivity.this);
                try {
                    ds.open();
                    if (currentContact.getContactID() == -1) {
                        wasSuccessful = ds.insertContact(currentContact);
                        if(wasSuccessful) {
                            int newId = ds.getLastContactID();
                            currentContact.setContactID(newId);
                        }
                    } else {
                        wasSuccessful = ds.updateContact(currentContact);

                    }
                    ds.close();
                } catch (Exception e) {
                    wasSuccessful = false;
                }
                if (wasSuccessful) {
                    ToggleButton editToggle = findViewById(R.id.toggleButton);
                    editToggle.toggle();
                    setForEditing(false);
                }
            }
        });
    }

    private void setForEditing(boolean enabled) {
        EditText editContact = findViewById(R.id.editContact);
        EditText editAddress = findViewById(R.id.editAddress);

        EditText editHome = findViewById(R.id.editHome);

        Button buttonSave = findViewById(R.id.buttonSave);


        editContact.setEnabled(enabled);
        editAddress.setEnabled(enabled);

        editHome.setEnabled(enabled);

        buttonSave.setEnabled(enabled);

    }


}