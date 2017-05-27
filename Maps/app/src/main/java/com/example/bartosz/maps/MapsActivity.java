package com.example.bartosz.maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, ActivityCompat.OnRequestPermissionsResultCallback, AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
    private GoogleMap mMap;

    // Needed to Snackbar
    private View mLayout;

    // Needed to change permissions
    private static final int REQUEST_FINE_LOCATION = 0;
    private Spinner spinner;

    City[] cities;
    MarkerOptions marker;

    private ArrayList<LatLng> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_maps);

        mLayout = findViewById(R.id.main_layout);

        String data = readRaw(R.raw.miasta);
        Gson gson = new Gson();
        cities = gson.fromJson(data, City[].class);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private String readRaw(int fileID) {
        InputStream is = getResources().openRawResource(fileID);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return writer.toString();
    }

    void addActGpsLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        }
        double myLat = 51.116;
        double myLng = 17.06;
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc == null) {
            // fall back to network if GPS is not available
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (loc != null) {
            myLat = loc.getLatitude();
            myLng = loc.getLongitude();
        }
        // Add a marker in Wroclaw and move the camera
        LatLng wroclaw = new LatLng(myLat, myLng);
        marker = new MarkerOptions().position(wroclaw).title("Marker in Sydney");
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wroclaw, 13));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(this);

        double myLat = -33.865143;
        double myLng = 151.209900;

        // Get location form GPS system
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions java.lang.Stringfor more details.

            // Jesli nie mamy dostepu to prosimy uzytkownika o dostep
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        } else {

            // In case we have permission just make marker and move
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (loc == null) {
                // fall back to network if GPS is not available
                loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (loc != null) {
                myLat = loc.getLatitude();
                myLng = loc.getLongitude();
            }
            // Add a marker in Wroclaw and move the camera
            LatLng wroclaw = new LatLng(myLat, myLng);
            marker = new MarkerOptions().position(wroclaw).title("Marker in Sydney");
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wroclaw, 13));
            googleMap.setOnMapLoadedCallback(this);
        }
    }

    @Override
    public void onMapLoaded() {
        Snackbar.make(mLayout, R.string.READY,
                Snackbar.LENGTH_SHORT)
                .show();

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> miasta = new ArrayList<>();
        for (City c : cities){
            miasta.add(c.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, miasta);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // we should move camera and add marker on gps location
                    addActGpsLocation();
                } else {
                    setDefaultLocation();
                }
            }
        }
    }

    private void setDefaultLocation() {

        double myLat = 52.237049;
        double myLng = 21.017532;

        // Add a marker in Wroclaw and move the camera
        LatLng wroclaw = new LatLng(myLat, myLng);
        marker = new MarkerOptions().position(wroclaw).title("Marker in Sydney");
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wroclaw, 13));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,String.valueOf(position));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(cities[position].Lat, cities[position].Lng)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void makePolyline(View view) {
        if(points == null){
            points = new ArrayList<>();
            points.add(mMap.getCameraPosition().target);
        } else {
            points.add(mMap.getCameraPosition().target);
            mMap.addPolyline(new PolylineOptions().addAll(points));
        }
    }

    public void deletePolyline(View view) {
        points.clear();
        mMap.clear();
        mMap.addMarker(marker);
    }
}
