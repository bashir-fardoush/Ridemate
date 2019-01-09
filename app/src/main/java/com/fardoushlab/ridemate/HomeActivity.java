package com.fardoushlab.ridemate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fardoushlab.ridemate.R;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback{
    private static final int MINI_INDEX = 1;
    private static final int SUB_INDEX = 2;
    private static final int MICRO_INDEX = 3;
    private static final int BIKE_INDEX = 4;
    private static final int NOVA_INDEX = 5;
    private Toolbar toolbar;
    private ImageView arrowIV, pointerIv;
    private Button miniBtn, microBtn, bikeBtn, subBtn, novaBtn;
    private LinearLayout hideViewLayout;
    private static int selectedOption = 0;

    private GoogleMap mMap;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        selectedOption = 0;

        toolbar = findViewById(R.id.home_toolbar);
        toolbar.setTitle("Confirmation");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrowIV = findViewById(R.id.iv_arrow);
        pointerIv = findViewById(R.id.iv_pointer);

        hideViewLayout = findViewById(R.id.ll_hideview);
        miniBtn = findViewById(R.id.btn_mini);
        microBtn = findViewById(R.id.btn_micro);
        subBtn = findViewById(R.id.btn_sub);
        bikeBtn = findViewById(R.id.btn_bike);
        novaBtn = findViewById(R.id.btn_nova);


        pointerIv.setOnClickListener(this);
        arrowIV.setOnClickListener(this);
        miniBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        microBtn.setOnClickListener(this);
        bikeBtn.setOnClickListener(this);
        novaBtn.setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(15000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    Toast.makeText(HomeActivity.this, "" + location.getLatitude(), Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
         locationProviderClient.requestLocationUpdates(locationRequest,locationCallback, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow:
                if (hideViewLayout.getVisibility() == View.VISIBLE) {
                    hideViewLayout.setVisibility(View.GONE);
                    arrowIV.setImageResource(R.drawable.arrow_up);

                } else {
                    hideViewLayout.setVisibility(View.VISIBLE);
                    arrowIV.setImageResource(R.drawable.arrow_down);
                }

                break;


            case R.id.btn_mini:

                if (selectedOption == MINI_INDEX) {
                    selectedOption = 0;
                    miniBtn.setBackgroundResource(R.drawable.option_bg_gray);
                } else {
                    selectedOption = MINI_INDEX;
                    miniBtn.setBackgroundResource(R.drawable.option_bg);
                    deselectOtherBtn(MINI_INDEX);
                }

                break;

            case R.id.btn_sub:

                if (selectedOption == SUB_INDEX) {
                    selectedOption = 0;
                    subBtn.setBackgroundResource(R.drawable.option_bg_gray);
                } else {
                    selectedOption = SUB_INDEX;
                    subBtn.setBackgroundResource(R.drawable.option_bg);
                    deselectOtherBtn(SUB_INDEX);
                }

                break;
            case R.id.btn_micro:

                if (selectedOption == MICRO_INDEX) {
                    selectedOption = 0;
                    microBtn.setBackgroundResource(R.drawable.option_bg_gray);
                } else {
                    selectedOption = MICRO_INDEX;
                    microBtn.setBackgroundResource(R.drawable.option_bg);
                    deselectOtherBtn(MICRO_INDEX);
                }

                break;

            case R.id.btn_bike:
                bikeBtn.setBackgroundResource(R.drawable.option_bg);
                if (selectedOption == BIKE_INDEX) {
                    selectedOption = 0;
                    bikeBtn.setBackgroundResource(R.drawable.option_bg_gray);
                } else {
                    selectedOption = BIKE_INDEX;
                    deselectOtherBtn(BIKE_INDEX);
                }

                break;
            case R.id.btn_nova:
                if (selectedOption == NOVA_INDEX) {
                    selectedOption = 0;
                    novaBtn.setBackgroundResource(R.drawable.option_bg_gray);
                } else {
                    selectedOption = NOVA_INDEX;
                    novaBtn.setBackgroundResource(R.drawable.option_bg);
                    deselectOtherBtn(NOVA_INDEX);
                }

                break;
        }
    }

    private void deselectOtherBtn(int i) {
        if (i != MINI_INDEX){
            miniBtn.setBackgroundResource(R.drawable.option_bg_gray);
        }  if (i != SUB_INDEX){
            subBtn.setBackgroundResource(R.drawable.option_bg_gray);
        }  if (i != MICRO_INDEX){
            microBtn.setBackgroundResource(R.drawable.option_bg_gray);
        }  if (i != BIKE_INDEX){
            bikeBtn.setBackgroundResource(R.drawable.option_bg_gray);
        }  if (i != NOVA_INDEX){
            novaBtn.setBackgroundResource(R.drawable.option_bg_gray);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
