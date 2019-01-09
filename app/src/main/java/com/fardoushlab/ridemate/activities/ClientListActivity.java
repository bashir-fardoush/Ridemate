package com.fardoushlab.ridemate.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.fardoushlab.ridemate.R;
import com.fardoushlab.ridemate.Utils;
import com.fardoushlab.ridemate.adapters.ClientAdapter;
import com.fardoushlab.ridemate.models.Response;
import com.fardoushlab.ridemate.network.ApiInterface;
import com.fardoushlab.ridemate.network.RetrofitApiClient;

import retrofit2.Call;
import retrofit2.Callback;

public class ClientListActivity extends AppCompatActivity {
    private static final String TAG = "_"+ClientListActivity.class.getSimpleName() ;
    private RecyclerView clientListRv;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Clients");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        clientListRv = findViewById(R.id.rv_client_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        clientListRv.setLayoutManager(manager);


        if (Utils.isConnected(getApplicationContext())){
            getClientData();
        }else {
            Toast.makeText(this, "No interntet connection! connect then try again,", Toast.LENGTH_LONG).show();
        }




    }

    private void getClientData() {

        final ApiInterface apiInterface = RetrofitApiClient.getInstence().create(ApiInterface.class);
        Call<Response> clientsCall = apiInterface.getClients();
        clientsCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
               // Toast.makeText(ClientListActivity.this, "code: "+response.code(), Toast.LENGTH_SHORT).show();
                if (response.code() == 200){

                    Log.d(TAG, "onResponse: "+response.body().getMessage());
                    if (response.body().getClient().size() > 0){
                        ClientAdapter clientAdapter = new ClientAdapter(ClientListActivity.this,response.body().getClient());
                        clientListRv.setAdapter(clientAdapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(ClientListActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getGroupId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
