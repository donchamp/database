package com.example.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter {

    private ArrayList<Contact> contactData;

    private View.OnClickListener OnClickListener;

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewContact;
        public TextView textViewAddress;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContact = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            itemView.setTag(this);
            itemView.setOnClickListener(OnClickListener);
        }

        public TextView getContactTextView() {
            return textViewContact;
        }

        public TextView getAddressTextView() { return textViewAddress; }
    }
    public void setOnClickListener(View.OnClickListener listener) {
        OnClickListener = listener;

    }

    public Adapter(ArrayList<Contact> arrayList) {
        contactData = arrayList;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_view, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ContactViewHolder cvh = (ContactViewHolder) holder;
        cvh.getContactTextView().setText(contactData.get(position).getName());
        cvh.getAddressTextView().setText(contactData.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return contactData.size();
    }
}

