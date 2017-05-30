package com.example.bartosz.maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        AdapterView.OnItemSelectedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private static final String TAG = "MainActivity";
    private GoogleMap mMap;

    // Needed to Snackbar
    private View mLayout;

    // Needed to change permissions
    private static final int REQUEST_FINE_LOCATION = 0;
    private Spinner spinner;

    City[] cities;
    MarkerOptions marker;
    Marker m;
    CircleOptions circle;
    Circle c;

    // points to make polyline
    private ArrayList<LatLng> points;

    // to customwindow
    private RadioGroup mOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_maps);

        mOptions = (RadioGroup) findViewById(R.id.custom_info_window_options);
        mOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (m != null && m.isInfoWindowShown()) {
                    // Refresh the info window when the info window's content has changed.
                    m.showInfoWindow();
                }
            }
        });
        mLayout = findViewById(R.id.main_layout);

        String data = readRaw(R.raw.miasta);
        Gson gson = new Gson();
        cities = gson.fromJson(data, City[].class);

        spinner = (Spinner) findViewById(R.id.spinner);
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
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
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
            // Add a marker in your gps location and move the camera
            LatLng location = new LatLng(myLat, myLng);
            marker = new MarkerOptions().position(location).title("Marker in GPS location");
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
            googleMap.setOnMapLoadedCallback(this);
        }
    }

    @Override
    public void onMapLoaded() {
        Snackbar.make(mLayout, R.string.READY,
                Snackbar.LENGTH_SHORT)
                .show();
        mMap.setOnMarkerDragListener(this);
        marker.draggable(true);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                circle.setStrokeColor(circle.getStrokeColor() ^ 0x00ffffff);
            }
        });
        circle = new CircleOptions()
                .center(marker.getPosition())
                .radius(1000)
                .strokeColor(Color.CYAN)
                .fillColor(0x220000FF)
                .strokeWidth(5).clickable(true);
        c = mMap.addCircle(circle);

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
        if(points != null) points.clear();
        mMap.clear();
        mMap.addMarker(marker);
        mMap.addCircle(circle);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        this.marker = new MarkerOptions().position(marker.getPosition()).draggable(true).title("After drag");
        this.circle = new CircleOptions()
                .center(marker.getPosition())
                .radius(1000)
                .strokeColor(Color.CYAN)
                .fillColor(0x220000FF)
                .strokeWidth(5).clickable(true);
        review();
    }

    private void review() {
        mMap.clear();
        if(points != null){
            mMap.addPolyline(new PolylineOptions().addAll(points));
        }
        mMap.addMarker(marker);
        mMap.addCircle(circle);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        this.marker = new MarkerOptions().position(latLng).draggable(true).title("After longClick");
        this.circle = new CircleOptions()
                .center(marker.getPosition())
                .radius(1000)
                .strokeColor(Color.CYAN)
                .fillColor(0x220000FF)
                .strokeWidth(5).clickable(true);
        review();
    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

        private final View mWindow;

        private final View mContents;

        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }


        @Override
        public View getInfoWindow(Marker marker) {
            if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_window) {
                // This means that getInfoContents will be called.
                return null;
            }
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_contents) {
                // This means that the default info contents will be used.
                return null;
            }
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            int badge = R.mipmap.badge_info_window;
            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
            TextView titleUi = ((TextView) view.findViewById(R.id.title));

            String title = marker.getTitle();

            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
    }
}
