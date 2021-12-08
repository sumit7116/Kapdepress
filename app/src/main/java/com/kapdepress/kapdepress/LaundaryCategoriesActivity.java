package com.kapdepress.kapdepress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LaundaryCategoriesActivity extends AppCompatActivity {
private CardView card_regularclothssimple,card_regularclothssubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundary_categories);
        initialization();
        gotoregularplans();
        gotoregularsubscriptionplans();
    }
    private void initialization()
    {
        card_regularclothssimple=findViewById(R.id.card_regularclothssimple);
        card_regularclothssubscription=findViewById(R.id.card_regularclothssubscription);
    }

    private void gotoregularplans()
    {
        card_regularclothssimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LaundaryRegularClothesRatesActivity.class);
                startActivity(i);
            }
        });

    }

    private void gotoregularsubscriptionplans()
    {
        card_regularclothssubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LaundaryRegularDressPlansActivity.class);
                startActivity(i);
            }
        });

    }
}