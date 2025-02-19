package com.example.mycontactlist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter{
    private ArrayList<Contact> contactData;
    private View.OnClickListener mOnItemClickListener;
    public class ContactViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewContact;

        public TextView textPhone;
        public Button deleteButton;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContact = itemView.findViewById(R.id.textContactName);
            textPhone = itemView.findViewById(R.id.textPhoneNumber);
            deleteButton = itemView.findViewById(R.id.buttonDeleteContact);

            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getPhoneTextView(){
            return textPhone;
        }

        public Button getDeleteButton(){
            return deleteButton;
        }

        public TextView getContactTextView(){
            return textViewContact;
        }
    }

    public ContactAdapter(ArrayList<Contact> arrayList){
        this.contactData = arrayList;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ContactViewHolder cvh = (ContactViewHolder) holder;
        Contact contact = contactData.get(position);

        cvh.getContactTextView().setText(contact.getContactName());
        cvh.getPhoneTextView().setText(contact.getPhoneNumber());

        cvh.itemView.setTag(cvh);
        if (mOnItemClickListener != null) {
            cvh.itemView.setOnClickListener(mOnItemClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return contactData.size();
    }
}
