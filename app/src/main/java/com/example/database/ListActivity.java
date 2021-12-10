package com.example.database;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

public class ListActivity extends AppCompatActivity {
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();

            Intent intent = new Intent(ListActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("position", position);
            startActivity(intent);

        }
    };

    private class SortByName implements Comparator<Contact> {

        private boolean isAscending;

        public SortByName(boolean isAscending) {
            this.isAscending = isAscending;
        }

        @Override
        public int compare(Contact contact, Contact t1) {
            if(isAscending)
                return contact.getName().compareTo(t1.getName());
            else
                return t1.getName().compareTo(contact.getName());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initListButton();



        DataSource ds = new DataSource(this);
        ArrayList<Contact> contacts;
        try {
            ds.open();
            contacts = ds.getContacts();
            ds.close();
            SharedPreferences sharedPref = getSharedPreferences("MyContactListPreferences",
                    Context.MODE_PRIVATE);
            String sortBy = sharedPref.getString("sortfield", "name");
            String sortOrder = sharedPref.getString("sortorder", "ascending");
            if(sortBy.equals("name"))
                if(sortOrder.equals("ascending"))
                    contacts.sort(new SortByName(true));
                else
                    contacts.sort(new SortByName(false));
            RecyclerView contactList = findViewById(R.id.rvContacts);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            contactList.setLayoutManager(layoutManager);
            Adapter contactAdapter = new Adapter(contacts);
            contactAdapter.setOnClickListener(onClickListener);
            contactList.setAdapter(contactAdapter);

        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();

        }
    }



    private void initListButton() {
        Button listButton = findViewById(R.id.buttonList);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ListActivity.this, ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


}