package com.example.mycontactlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import android.text.format.*;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {

    private Contact currentContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initListButton();
        initMapButton();
        initSettingsButton();
        initToggleButton();
        initTextChangedEvents();
        initSaveButton();
        setForEditing(false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        initChangeDateButton();
        currentContact = new Contact();
    }

    private void initListButton(){
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }


    private void initMapButton(){
        ImageButton ibList = findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactMapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initSettingsButton(){
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactSettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initToggleButton() {
        final ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButton);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setForEditing(editToggle.isChecked());
            }
        });
    }

    private void initSaveButton(){
        Button saveButton = findViewById(R.id.ButtonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean wasSuccessful;
                hideKeyboard();
                ContactDataSource ds = new ContactDataSource(MainActivity.this);
                try{
                    ds.open();

                    if (currentContact.getContactID() == -1){
                        wasSuccessful = ds.insertContact(currentContact);
                        if(wasSuccessful){
                            int newID = ds.getLastContactID();
                            currentContact.setContactID(newID);
                        }
                    }
                    else{
                        wasSuccessful = ds.updateContact(currentContact);
                    }
                    ds.close();
                }
                catch (Exception e){
                    wasSuccessful = false;
                }
            }
        });
    }


    private void initTextChangedEvents(){
        final EditText etContactName = findViewById(R.id.editName);
        etContactName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s){
                currentContact.setContactName(etContactName.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
                // auto
            }
            public void onTextChanged(CharSequence s, int start, int before, int count){
                // auto
            }
        });
        final EditText etStreetAddress = findViewById(R.id.editAddress);
        etStreetAddress.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s){
                currentContact.setStreetAddress(etStreetAddress.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }
        });
        final EditText etCity = findViewById(R.id.editCity);
        etCity.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s){
                currentContact.setCity(etCity.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }
        });
        final EditText etState = findViewById(R.id.editState);
        etState.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s){
                currentContact.setState(etState.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }
        });
        final EditText etZipCode = findViewById(R.id.editZipCode);
        etZipCode.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s){
                currentContact.setZipCode(etZipCode.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }
        });
        final EditText etPhoneNumber = findViewById(R.id.editHome);
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s){
                currentContact.setPhoneNumber(etPhoneNumber.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }
        });
        final EditText etCellPhoneNumber = findViewById(R.id.editCell);
        etCellPhoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s){
                currentContact.setCellNumber(etCellPhoneNumber.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }
        });
        final EditText etEMail = findViewById(R.id.editEMail);
        etEMail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s){
                currentContact.setEMail(etEMail.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }
        });
        // need to find way to format the phones because method is depreciated
    }


    private void setForEditing(Boolean enabled){
        EditText editName = findViewById(R.id.editName);
        EditText editAddress = findViewById(R.id.editAddress);
        EditText editCity = findViewById(R.id.editCity);
        EditText editState = findViewById(R.id.editState);
        EditText editZipCode = findViewById(R.id.editZipCode);
        EditText editPhone = findViewById(R.id.editHome);
        EditText editCell = findViewById(R.id.editCell);
        EditText editEmail = findViewById(R.id.editEMail);
        Button buttonChange = findViewById(R.id.btnBirthday);
        Button buttonSave = findViewById(R.id.ButtonSave);

        editName.setEnabled(enabled);
        editAddress.setEnabled(enabled);
        editCity.setEnabled(enabled);
        editState.setEnabled(enabled);
        editZipCode.setEnabled(enabled);
        editPhone.setEnabled(enabled);
        editCell.setEnabled(enabled);
        editEmail.setEnabled(enabled);
        buttonChange.setEnabled(enabled);
        buttonSave.setEnabled(enabled);

        if (enabled){
            editName.requestFocus();
        }
        else{
            ScrollView s = findViewById(R.id.scrollView);
            s.fullScroll(ScrollView.FOCUS_UP);
        }

    }

    private void initChangeDateButton() {
        Button changeDate = findViewById(R.id.btnBirthday);
        changeDate.setOnClickListener(v -> {
            FragmentManager fm = getSupportFragmentManager();
            DatePickerDialog datePickerDialog = new DatePickerDialog();
            datePickerDialog.show(fm, "DatePick");
        });
    }


    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        TextView birthDay = findViewById(R.id.textBirthday);
        birthDay.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
        currentContact.setBirthday(selectedTime);
    }
    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)
        getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editName = findViewById(R.id.editName);
        imm.hideSoftInputFromWindow(editName.getWindowToken(), 0);
        EditText editAddress = findViewById(R.id.editAddress);
        imm.hideSoftInputFromWindow(editAddress.getWindowToken(), 0);
        EditText editCity = findViewById(R.id.editCity);
        imm.hideSoftInputFromWindow(editCity.getWindowToken(), 0);
        EditText editState = findViewById(R.id.editState);
        imm.hideSoftInputFromWindow(editState.getWindowToken(), 0);
        EditText editZip = findViewById(R.id.editZipCode);
        imm.hideSoftInputFromWindow(editZip.getWindowToken(), 0);
        EditText editHome = findViewById(R.id.editHome);
        imm.hideSoftInputFromWindow(editHome.getWindowToken(), 0);
        EditText editCell = findViewById(R.id.editCell);
        imm.hideSoftInputFromWindow(editCell.getWindowToken(), 0);
        EditText editEMail = findViewById(R.id.editEMail);
        imm.hideSoftInputFromWindow(editEMail.getWindowToken(), 0);
    }




}