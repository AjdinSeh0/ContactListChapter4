package com.example.mycontactlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter{
    private ArrayList<Contact> contactData;
    private View.OnClickListener mOnItemClickListener;

    private boolean isDeleting;

    private Context parentContext;

    public ContactAdapter(ArrayList<Contact> arrayList, Context context){
        this.parentContext = context;
        contactData = arrayList;
    }
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
        ContactViewHolder cvh = (ContactViewHolder) holder;
        Contact contact = contactData.get(position);
        cvh.getContactTextView().setText(contact.getContactName());
        cvh.getPhoneTextView().setText(contact.getPhoneNumber());
        cvh.itemView.setTag(cvh);

        if(isDeleting){
            cvh.getDeleteButton().setVisibility(View.VISIBLE);
            cvh.getDeleteButton().setOnClickListener(v ->{
                deleteItem(position);
            });
        }
        else{
            cvh.getDeleteButton().setVisibility(View.INVISIBLE);
        }
    }

    public void setDelete(boolean b){
        isDeleting = b;
        notifyDataSetChanged();
    }

    private void deleteItem(int position) {
        Contact contact = contactData.get(position);
        ContactDataSource ds = new ContactDataSource(parentContext); // ✅ Use parentContext
        try {
            ds.open();
            boolean didDelete = ds.deleteContact(contact.getContactID());
            ds.close();
            if (didDelete) {
                contactData.remove(position);
                notifyDataSetChanged();
                Toast.makeText(parentContext, "Deleted: " + contact.getContactName(), Toast.LENGTH_SHORT).show(); // ✅ Fix
                System.out.println("Deleted: " + contact.getContactName()); // Debugging
            } else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(parentContext, "Error Deleting Contact!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public int getItemCount() {
        return contactData.size();
    }
}
