package com.kapdepress.kapdepress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kapdepress.kapdepress.adapters.SteamIroninDailyWearClothsAdapter;
import com.kapdepress.kapdepress.adapters.SteamIroninDailyWearClothsAdapter1;
import com.kapdepress.kapdepress.models.SteamIroninDailyWearClothsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SteamIronDailyWearClothsActivity extends AppCompatActivity {
private RecyclerView recyecler_clothlist;
private String BASEURL,BANNERURL;
private SteamIroninDailyWearClothsModel[] myListData;
private ProgressDialog prgloadCloths;
private List<SteamIroninDailyWearClothsModel> steamIroninDailyWearClothsModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steam_iron_daily_wear_cloths);
        initialization();
        BASEURL=getResources().getString(R.string.base_url);
        BANNERURL=getResources().getString(R.string.banner_url);
        //loadCloths();
        fetchCloths();
    }

    private void initialization()
    {
        recyecler_clothlist=findViewById(R.id.recyecler_clothlist);
        steamIroninDailyWearClothsModels=new ArrayList<>();
        prgloadCloths = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        prgloadCloths.setMessage("Loading");
        prgloadCloths.setIndeterminate(true);
        prgloadCloths.setCancelable(false);
        prgloadCloths.setCanceledOnTouchOutside(false);
    }
    private void loadCloths()
    {
       /* SteamIroninDailyWearClothsModel[] myListData = new SteamIroninDailyWearClothsModel[]{
                new SteamIroninDailyWearClothsModel(1, R.drawable.img_ironlogo,"abc","40","0"),
                new SteamIroninDailyWearClothsModel(2, R.drawable.img_ironlogo,"Blazer","40","0"),
                new SteamIroninDailyWearClothsModel(3, R.drawable.img_ironlogo,"Blazer","40","0"),
                new SteamIroninDailyWearClothsModel(3, R.drawable.img_ironlogo,"Blazer","40","0"),
                new SteamIroninDailyWearClothsModel(3, R.drawable.img_ironlogo,"Blazer","40","0"),
                new SteamIroninDailyWearClothsModel(3, R.drawable.img_ironlogo,"Blazer","40","0"),
                new SteamIroninDailyWearClothsModel(3, R.drawable.img_ironlogo,"Blazer","40","0"),
                new SteamIroninDailyWearClothsModel(3, R.drawable.img_ironlogo,"Blazer","40","0"),
        };
        SteamIroninDailyWearClothsAdapter adapter=new SteamIroninDailyWearClothsAdapter(myListData);
        recyecler_clothlist.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyecler_clothlist.setLayoutManager(mLayoutManager);
        recyecler_clothlist.setAdapter(adapter);*/
    }

    private void fetchCloths()
    {
        //myListData = new SteamIroninDailyWearClothsModel[];
        prgloadCloths.show();
        RequestQueue queue;
        queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, BASEURL+"reteriveSteamIronDailyWearCloths.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        int c_id = object1.getInt("c_id");
                        String c_name = object1.getString("c_name");
                        String c_img = object1.getString("c_img");
                        String finalUrl=BANNERURL+"Images/cloths/"+c_img;
                        SteamIroninDailyWearClothsModel ld=new SteamIroninDailyWearClothsModel(c_id, finalUrl,c_name,"8","0");
                        steamIroninDailyWearClothsModels.add(ld);
                    }
                    SteamIroninDailyWearClothsAdapter adapter=new SteamIroninDailyWearClothsAdapter(steamIroninDailyWearClothsModels,getApplicationContext());
                    recyecler_clothlist.setHasFixedSize(true);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    recyecler_clothlist.setLayoutManager(mLayoutManager);
                    recyecler_clothlist.setAdapter(adapter);
                    prgloadCloths.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    prgloadCloths.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                prgloadCloths.dismiss();
            }
        });
        queue.add(request);
    }
}