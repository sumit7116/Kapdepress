package com.kapdepress.kapdepress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SteamIroningCategoryActivity extends AppCompatActivity {
private CardView card_dailywear,card_regulardailytraditional,card_regulardresstraditional,card_partywears,card_otherhouseholds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steam_ironing_category);
        initialization();
        gotoDailyWearPlans();
        gotoRegularDailyTraditional();
        gotoRegularDress();
        gotoPartyWears();
        gotoOtherHouseHold();
    }
    private  void initialization()
    {
        card_dailywear=findViewById(R.id.card_otherhouseholdcloths);
        card_regulardailytraditional=findViewById(R.id.card_regularclothssimple);
        card_regulardresstraditional=findViewById(R.id.card_regularclothssubscription);
        card_partywears=findViewById(R.id.card_partywears);
        card_otherhouseholds=findViewById(R.id.card_otherhouseholds);
    }
    private void gotoDailyWearPlans()
    {
        card_dailywear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DailyWearPlansActivity.class);
                startActivity(i);
            }
        });
    }

    private void gotoRegularDailyTraditional()
    {
        card_regulardailytraditional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegularDailyTraditionalPlansActivity.class);
                startActivity(i);
            }
        });
    }

    private void gotoRegularDress()
    {
        card_regulardresstraditional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegularDressPlansActivity.class);
                startActivity(i);
            }
        });
    }

    private void gotoPartyWears()
    {
        card_partywears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PartyWearsPlansActivity.class);
                startActivity(i);
            }
        });
    }

    private void gotoOtherHouseHold()
    {
        card_otherhouseholds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HouseHoldPlansActivity.class);
                startActivity(i);
            }
        });
    }
}