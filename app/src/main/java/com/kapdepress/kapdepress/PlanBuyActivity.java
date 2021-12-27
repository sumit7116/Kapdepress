package com.kapdepress.kapdepress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PlanBuyActivity extends AppCompatActivity {
private TextView txt_plantype,txt_minimumcloth,txt_percloth,txt_planprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_buy);
        initialization();
        getdata();
    }

    private void initialization()
    {
        txt_plantype=findViewById(R.id.txt_plantype);
        txt_minimumcloth=findViewById(R.id.txt_minimumcloth);
        txt_percloth=findViewById(R.id.txt_percloth);
        txt_planprice=findViewById(R.id.txt_planprice);

    }
    private void getdata()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String plantype = extras.getString("plantype");
            String minimumcloth = extras.getString("cloths");
            String percloth = extras.getString("percloth");
            String total = extras.getString("total");
            txt_plantype.setText(plantype);
            txt_minimumcloth.setText(minimumcloth);
            txt_percloth.setText("Rs. "+percloth);
            txt_planprice.setText("Rs. "+total);

        }
        else
        {
            Intent i = new Intent(getApplicationContext(),DailyWearPlansActivity.class);
            finish();
            startActivity(i);
        }
    }
}