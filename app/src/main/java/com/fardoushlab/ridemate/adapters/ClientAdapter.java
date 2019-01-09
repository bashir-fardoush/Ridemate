package com.fardoushlab.ridemate.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fardoushlab.ridemate.R;
import com.fardoushlab.ridemate.models.Client;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientViewHolder> {
    private Context context = null;
    private List<Client> clients = null;

    public ClientAdapter(Context context, List<Client> clients) {
        this.context = context;
        this.clients = clients;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View view  = inflater.inflate(R.layout.item_client,viewGroup,false);

        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder clientViewHolder, int i) {
        clientViewHolder.clientNameTv.setText(clients.get(i).getName());
        clientViewHolder.companyNameTv.setText(clients.get(i).getCompany());
        clientViewHolder.countryTv.setText(clients.get(i).getCountry());
        Picasso.get().load(clients.get(i).getLogo()).into(clientViewHolder.productLogo);



    }

    @Override
    public int getItemCount() {
        return clients.size();
    }
}
