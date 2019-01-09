package com.fardoushlab.ridemate.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;

import java.security.Permission;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    private static final int MINI_INDEX = 1;
    private static final int SUB_INDEX = 2;
    private static final int MICRO_INDEX = 3;
    private static final int BIKE_INDEX = 4;
    private static final int NOVA_INDEX = 5;
    private Toolbar toolbar;
    private ImageView arrowIV, pointerIv;
    private Button miniBtn, microBtn, bikeBtn, subBtn, novaBtn, pointerBtn;
    private LinearLayout hideViewLayout;
    private static int selectedOption = 0;

    private GoogleMap mMap;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private SnackbarOnDeniedPermissionListener snackbarPermissionListener;

    private RelativeLayout parent;
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

        parent = findViewById(R.id.rl_parent);
        arrowIV = findViewById(R.id.iv_arrow);
        pointerBtn = findViewById(R.id.btn_pointer);

        hideViewLayout = findViewById(R.id.ll_hideview);
        miniBtn = findViewById(R.id.btn_mini);
        microBtn = findViewById(R.id.btn_micro);
        subBtn = findViewById(R.id.btn_sub);
        bikeBtn = findViewById(R.id.btn_bike);
        novaBtn = findViewById(R.id.btn_nova);


        pointerBtn.setOnClickListener(this);
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
                   // Toast.makeText(HomeActivity.this, "" + location.getLatitude(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        pointerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_DENIED) {
                        checkLocationPermission();
                    }
                    else {
                        locationProviderClient.getLastLocation().addOnSuccessListener(HomeActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                if (location != null){
                                    updateMapMarker(location);
                                }else {
                                    Toast.makeText(HomeActivity.this, "failed to get Location", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }
                else {

                    locationProviderClient.getLastLocation().addOnSuccessListener(HomeActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null){
                                updateMapMarker(location);
                            }else {
                                Toast.makeText(HomeActivity.this, "failed to get Location", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }



            }
        });//pointer btn click

//        snackbarPermissionListener =
//                SnackbarOnDeniedPermissionListener.Builder
//                        .with(parent, "GPS Location permission is necessary to show in map")
//                        .withOpenSettingsButton("Settings")
//                        .withCallback(new Snackbar.Callback() {
//                            @Override
//                            public void onShown(Snackbar snackbar) {
//                                // Event handler for when the given Snackbar is visible
//                            }
//                            @Override
//                            public void onDismissed(Snackbar snackbar, int event) {
//                                // Event handler for when the given Snackbar has been dismissed
//                            }
//                        }).build();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){

                locationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
            }
        }else {
            locationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationProviderClient != null){
            locationProviderClient.removeLocationUpdates(locationCallback);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.see_clients) {
            startActivity(new Intent(this, ClientListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void checkLocationPermission() {
        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                       // Toast.makeText(HomeActivity.this, "Granted", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        if (response.isPermanentlyDenied()) {
                            openSettingsDialog();
                        }
                        //Toast.makeText(HomeActivity.this, "Please give location permission first", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                        // show dialog
                       // Toast.makeText(HomeActivity.this, "on rational", Toast.LENGTH_SHORT).show();
                    }
                }).check();
    }

    private void openSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Required Permissions");
        builder.setMessage("This app require location permission for ride");
        builder.setPositiveButton("Take Me To SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void updateMapMarker(Location location) {
        mMap.clear();
        LatLng place = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.addMarker(new MarkerOptions().position(place).title("Your location"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place,18));


        LatLng latLng = new LatLng(location.getLatitude()+0.00066, location.getLongitude()+0.00026);
        LatLng latLng1 = new LatLng(location.getLatitude()-0.0005666, location.getLongitude()-0.00026);
        LatLng latLng2 = new LatLng(location.getLatitude()+0.0002266, location.getLongitude()-0.00056);
        mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.small_car)));
        mMap.addMarker(new MarkerOptions().position(latLng1).icon(BitmapDescriptorFactory.fromResource(R.drawable.small_car)));
        mMap.addMarker(new MarkerOptions().position(latLng2).icon(BitmapDescriptorFactory.fromResource(R.drawable.small_car)));
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
        LatLng sydney = new LatLng(23.777176, 90.399452);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Dhaka"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
