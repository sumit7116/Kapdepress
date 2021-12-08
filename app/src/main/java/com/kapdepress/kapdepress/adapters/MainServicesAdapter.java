package com.kapdepress.kapdepress.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kapdepress.kapdepress.LaundaryCategoriesActivity;
import com.kapdepress.kapdepress.R;
import com.kapdepress.kapdepress.SteamIroningCategoryActivity;
import com.kapdepress.kapdepress.models.MainServicesModel;

public class MainServicesAdapter extends RecyclerView.Adapter<MainServicesAdapter.ViewHolder>{
    MainServicesModel[] mainServicesModels;


    public MainServicesAdapter(MainServicesModel[] mainServicesModels) {
        this.mainServicesModels = mainServicesModels;
    }

    @NonNull
    @Override
    public MainServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_mainservices, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MainServicesModel myListData = mainServicesModels[position];
        holder.textView.setText(mainServicesModels[position].getServiceName());
        holder.imageView.setImageResource(mainServicesModels[position].getServiceImage());
        holder.layout_lineraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(view.getContext(),"click on item: "+myListData.getServiceName(),Toast.LENGTH_LONG).show();
                if(String.valueOf(myListData.getId()).equals("1"))
                {
                    Intent i =new Intent(view.getContext(),SteamIroningCategoryActivity.class);
                    view.getContext().startActivity(i);

                }
                else if(String.valueOf(myListData.getId()).equals("2"))
                {
                    Intent i =new Intent(view.getContext(), LaundaryCategoriesActivity.class);
                    view.getContext().startActivity(i);

                }
            }
        });
    }




    @Override
    public int getItemCount() {
        return mainServicesModels.length;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public LinearLayout layout_lineraLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            this.layout_lineraLayout=(LinearLayout)itemView.findViewById(R.id.layout_lineraLayout);
            this.imageView = (ImageView) itemView.findViewById(R.id.serviceImg);
            this.textView = (TextView) itemView.findViewById(R.id.txt_serviceName);

        }
    }
}


