package com.example.codenameeh.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codenameeh.classes.Geolocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.LatLng;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.codenameeh.R;

public class GeolocationActivity extends BaseActivity implements OnMapReadyCallback,LocationListener{
    private MapView mapView;
    LatLng locationLTLN;
    double latitudeClicked;
    double longitudeClicked;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocation);


        Bundle mapViewBundle = null;
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        Button saveButton = findViewById(R.id.AcceptLocationButton);
        final TextView addressText = findViewById(R.id.GeolocationText);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addressText.getText().toString().matches(""))
                {
                    Intent intent = new Intent();
                    intent.putExtra("Longitude",longitudeClicked);
                    intent.putExtra("Latitude",latitudeClicked);
                    intent.putExtra("Address",addressText.getText().toString());
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }else {
                    Toast.makeText(GeolocationActivity.this,"Select a location",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if(mapViewBundle == null){
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY,mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocationManager lm = (LocationManager)getSystemService(getApplicationContext().LOCATION_SERVICE);
        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                final double longitude = location.getLongitude();
                final double latitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            locationLTLN=new LatLng(40.7143528, -74.0059731);
        }
        else {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            locationLTLN = new LatLng(latitude,longitude);
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,10,locationListener);
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng latlng){
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try{
                    List<Address> addressList = geocoder.getFromLocation(latlng.latitude,latlng.longitude,1);
                    latitudeClicked = latlng.latitude;
                    longitudeClicked = latlng.longitude;
                    String addressLine="";
                    for (int i = 0; i < addressList.get(0).getMaxAddressLineIndex();i++){
                        addressLine+=addressList.get(0).getAddressLine(i)+" ";
                    }
                    String displayMessage = addressList.get(0).getThoroughfare()+", "
                            +addressList.get(0).getAdminArea()+", "+
                            addressList.get(0).getCountryName();
                    if(!addressLine.equals("")){
                        displayMessage+=", "+addressLine;
                    }
                    if(addressList.get(0).getPremises() != null){
                        displayMessage+=", "+addressList.get(0).getPremises();
                    }
                    TextView addressText = findViewById(R.id.GeolocationText);
                    addressText.setText(displayMessage);


                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
        GoogleMap gmap;
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(locationLTLN));
        mapView.onResume();

    }

}
