package com.kapdepress.kapdepress;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DailyWearPlansActivity extends AppCompatActivity {
private TextView txt_savingplanviewdetails,txt_smartsavingviewdetails,txt_supersavingviewdetails;
private AlertDialog.Builder builder;
private String savingplandata,smartsavingplandata,supersavingplandata;
private CardView card_savingbuy,card_smartsavingbuy,card_supersavingbuy,card_regularplan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_wear_plans);
        initialization();
        displaySavingplandetails();
        displaySmartsavingplandetails();
        displaySupersavingplandetails();
        gotobuyplan();
    }

    private void initialization()
    {
        txt_savingplanviewdetails=findViewById(R.id.txt_savingplanviewdetails);
        txt_smartsavingviewdetails=findViewById(R.id.txt_smartsavingviewdetails);
        txt_supersavingviewdetails=findViewById(R.id.txt_supersavingviewdetails);
        builder = new AlertDialog.Builder(this);
        savingplandata = getResources().getString(R.string.dailywearsavingplan);
        smartsavingplandata = getResources().getString(R.string.dailywearsmartsavingplan);
        supersavingplandata=getResources().getString(R.string.dailywearsupersavingplan);
        card_savingbuy=findViewById(R.id.card_savingbuy);
        card_smartsavingbuy=findViewById(R.id.card_smartsavingbuy);
        card_supersavingbuy=findViewById(R.id.card_supersavingbuy);
        card_regularplan=findViewById(R.id.card_regularplan);
    }

    private void displaySavingplandetails()
    {
        txt_savingplanviewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Saving Plan")
                        .setMessage(savingplandata)
                        .setCancelable(false)
                        .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }

        });
    }

    private void displaySmartsavingplandetails()
    {
        txt_smartsavingviewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Smart Saving Plan")
                        .setMessage(smartsavingplandata)
                        .setCancelable(false)
                        .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }

        });
    }

    private void displaySupersavingplandetails()
    {
        txt_supersavingviewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Super Saving Plan")
                        .setMessage(supersavingplandata)
                        .setCancelable(false)
                        .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }

        });
    }

private void gotobuyplan()
{
    card_savingbuy.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),PlanBuyActivity.class);
            i.putExtra("plantype","Saving Plan");
            i.putExtra("cloths","40");
            i.putExtra("percloth","7.47");
            i.putExtra("total","299");
            startActivity(i);
        }
    });

    card_smartsavingbuy.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),PlanBuyActivity.class);
            i.putExtra("plantype","Smart Saving Plan");
            i.putExtra("cloths","100");
            i.putExtra("percloth","6.49");
            i.putExtra("total","649");
            startActivity(i);
        }
    });

    card_supersavingbuy.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),PlanBuyActivity.class);
            i.putExtra("plantype","Super Saving Plan");
            i.putExtra("cloths","200");
            i.putExtra("percloth","5.00");
            i.putExtra("total","999");
            startActivity(i);
        }
    });
    card_regularplan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),SteamIronDailyWearClothsActivity.class);
            i.putExtra("plantype","Super Saving Plan");
            i.putExtra("cloths","200");
            i.putExtra("percloth","5.00");
            i.putExtra("total","999");
            startActivity(i);
        }
    });
}
}