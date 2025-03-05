package com.example.mycontactlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private ArrayList<Contact> contactData;
    private View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private Context parentContext;

    public ContactAdapter(ArrayList<Contact> arrayList, Context context, View.OnClickListener itemClickListener) {
        this.parentContext = context;
        this.contactData = arrayList;
        this.mOnItemClickListener = itemClickListener; // ✅ Ensure listener is assigned
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewContact;
        public TextView textPhone;
        public TextView textEmail;
        public Button deleteButton;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContact = itemView.findViewById(R.id.textContactName);
            textPhone = itemView.findViewById(R.id.textPhoneNumber);
            textEmail = itemView.findViewById(R.id.textEMail);
            deleteButton = itemView.findViewById(R.id.buttonDeleteContact);

            // ✅ Attach the listener here
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, final int position) {
        Contact contact = contactData.get(position);
        holder.textViewContact.setText(contact.getContactName());
        holder.textPhone.setText(contact.getPhoneNumber());
        holder.textEmail.setText(contact.getEMail());

        // ✅ Ensure the click listener is correctly assigned
        holder.itemView.setTag(contact);
        holder.itemView.setOnClickListener(mOnItemClickListener);

        if (isDeleting) {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setOnClickListener(v -> deleteItem(position));
        } else {
            holder.deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setDelete(boolean b) {
        isDeleting = b;
        notifyDataSetChanged();
    }

    private void deleteItem(int position) {
        Contact contact = contactData.get(position);
        ContactDataSource ds = new ContactDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteContact(contact.getContactID());
            ds.close();
            if (didDelete) {
                contactData.remove(position);
                notifyDataSetChanged();
                Toast.makeText(parentContext, "Deleted: " + contact.getContactName(), Toast.LENGTH_SHORT).show();
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
