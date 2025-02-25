package com.example.mycontactlist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class ContactMapActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener gpsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_map);
        initListButton();
        initSettingsButton();
        initMapButton();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initGetLocationButton();
    }

    private void initListButton(){
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(v -> {
            Intent intent = new Intent(ContactMapActivity.this, ContactListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initMapButton(){
        ImageButton ibList = findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(v -> {
            Intent intent = new Intent(ContactMapActivity.this, ContactMapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initSettingsButton(){
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(v -> {
            Intent intent = new Intent(ContactMapActivity.this, ContactSettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initGetLocationButton(){
        Button locationButton = (Button) findViewById(R.id.buttonGetLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                locationManager = (LocationManager)getBaseContext().
                                        getSystemService(Context.LOCATION_SERVICE);
                gpsListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        TextView txtLatitude = (TextView) findViewById(R.id.textLatitude);
                        TextView txtLongitude = (TextView) findViewById(R.id.textLongitude);
                        TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracy);
                        txtLatitude.setText(String.valueOf(location.getLatitude()));
                        txtLongitude.setText(String.valueOf(location.getLongitude()));
                        txtAccuracy.setText(String.valueOf(location.getAccuracy()));
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                    public void onProviderEnabled(String provider) {}
                    public void onProviderDisabled(String provider){}
                };


                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
            }
                catch (Exception e){
                    Toast.makeText(getBaseContext(), "Error, Location not available",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        try{
            locationManager.removeUpdates(gpsListener);
        }
        catch (Exception e){
            Log.d("ERROR", "SOMETHING WENT WRONG");
        }
    }


}