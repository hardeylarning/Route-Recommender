package com.rocktech.routerecommendersystem;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.content.ContentValues.TAG;

public class DetailActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationClient;
    MarkerOptions place1, place2;
    double origin_latitude, origin_longitude, latitude, longitude;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getIntentData();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrag);
        supportMapFragment.getMapAsync(this);
    }

    void getLocation ()
    {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        // Logic to handle location object
                        origin_latitude = location.getLatitude();
                        origin_longitude = location.getLongitude();
                        //
                        Log.d(TAG, "onSuccess: origin Detail Latitude: "+origin_latitude);
                    }
                });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        getIntentData();

        map = googleMap;
        Log.d(TAG, "onMapReady: Latitude: "+latitude + "\n Origin Latitude: "+origin_latitude);
        LatLng current = new LatLng(origin_latitude,origin_longitude);
        LatLng getLatLong = new LatLng(latitude,longitude);
        place1 = new MarkerOptions().position(current).title("Current Location");
        place2 = new MarkerOptions().position(getLatLong).title(name+" Building");
        map.addMarker(place1);
        map.addMarker(place2);
        map.addPolyline((new PolylineOptions()).add(current, getLatLong).width(5)
                .color(Color.RED).geodesic(true));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(getLatLong, 15));
    }



    private void getIntentData() {
        if (getIntent().hasExtra("latitude"))
        {
            name = getIntent().getStringExtra("name");
            latitude = getIntent().getDoubleExtra("latitude", 0);
            longitude = getIntent().getDoubleExtra("longitude", 0);
        }
        else {
            Toast.makeText(this, "No Intent Data Found", Toast.LENGTH_SHORT).show();
        }
    }
}