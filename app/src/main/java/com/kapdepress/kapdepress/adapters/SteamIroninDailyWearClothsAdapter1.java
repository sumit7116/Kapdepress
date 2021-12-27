package com.kapdepress.kapdepress.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kapdepress.kapdepress.R;
import com.kapdepress.kapdepress.models.SteamIroninDailyWearClothsModel;

import java.util.List;

public class SteamIroninDailyWearClothsAdapter1 extends RecyclerView.Adapter<SteamIroninDailyWearClothsAdapter1.ViewHolder> {
    SteamIroninDailyWearClothsModel[] SteamIroninDailyWearClothsModel;
    private int mainCount=1;
    private Context context;

    public SteamIroninDailyWearClothsAdapter1(com.kapdepress.kapdepress.models.SteamIroninDailyWearClothsModel[] steamIroninDailyWearClothsModel) {
        SteamIroninDailyWearClothsModel = steamIroninDailyWearClothsModel;
    }

    public SteamIroninDailyWearClothsAdapter1(List<com.kapdepress.kapdepress.models.SteamIroninDailyWearClothsModel> steamIroninDailyWearClothsModels, Context applicationContext) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_clothssteamirondailywear, parent, false);
        SteamIroninDailyWearClothsAdapter1.ViewHolder viewHolder = new SteamIroninDailyWearClothsAdapter1.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SteamIroninDailyWearClothsModel myListData =SteamIroninDailyWearClothsModel[position];
        holder.txt_clothName.setText(SteamIroninDailyWearClothsModel[position].getClothName());
        holder.txt_price.setText("Rs. "+SteamIroninDailyWearClothsModel[position].getClothPrice()+"/Cloth");
        holder.txt_quantity.setText(SteamIroninDailyWearClothsModel[position].getClothQuantity());
        //holder.img_cloth.setImageResource(SteamIroninDailyWearClothsModel[position].getClothImg());
        Glide.with(holder.img_cloth.getContext()).load(SteamIroninDailyWearClothsModel[position].getClothImg()).placeholder(R.drawable.bwlogo).into(holder.img_cloth);
        holder.img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prevCount= Integer.parseInt(holder.txt_quantity.getText().toString());
                if(mainCount>10)
                {
                    Toast.makeText(v.getContext(),"You Reached Cloths Limit",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Log.i("SAM",""+prevCount);
                    prevCount++;
                    Log.i("SAM",""+prevCount);
                    mainCount++;
                    holder.txt_quantity.setText(""+prevCount);
                }

            }
        });

        holder.img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prevCount= Integer.parseInt(holder.txt_quantity.getText().toString());
                if(prevCount>0)
                {
                    prevCount--;
                    mainCount--;
                    holder.txt_quantity.setText(""+prevCount);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return SteamIroninDailyWearClothsModel.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_cloth,img_minus,img_plus;
        public TextView txt_clothName,txt_price,txt_quantity;
        public LinearLayout layout_lineraLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.img_cloth = (ImageView) itemView.findViewById(R.id.img_cloth);
            this.img_minus = (ImageView) itemView.findViewById(R.id.img_minus);
            this.img_plus = (ImageView) itemView.findViewById(R.id.img_plus);
            this.txt_clothName = (TextView) itemView.findViewById(R.id.txt_clothName);
            this.txt_price = (TextView) itemView.findViewById(R.id.txt_price);
            this.txt_quantity = (TextView) itemView.findViewById(R.id.txt_quantity);

        }
    }
}
