package com.fardoushlab.ridemate.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fardoushlab.ridemate.R;

public class ClientViewHolder extends RecyclerView.ViewHolder {
  public ImageView productLogo;
  public TextView clientNameTv, companyNameTv,countryTv;
  public Button arrowBtn;
  public ListView tagLv;

    public ClientViewHolder(@NonNull View itemView) {
        super(itemView);
        productLogo = itemView.findViewById(R.id.logo_product);
        clientNameTv = itemView.findViewById(R.id.tv_client_name);
        companyNameTv = itemView.findViewById(R.id.tv_company_name);
        countryTv = itemView.findViewById(R.id.tv_country_name);
        clientNameTv = itemView.findViewById(R.id.tv_client_name);
        arrowBtn = itemView.findViewById(R.id.btn_arrow);
        tagLv = itemView.findViewById(R.id.lv_tag);


    }
}
