package com.kapdepress.kapdepress;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.kapdepress.kapdepress.adapters.MainServicesAdapter;
import com.kapdepress.kapdepress.models.MainServicesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private View rootView;
    private ImageSlider slider_banner;
    private String BASEURL,BANNERURL;
    private List<SlideModel> slideModels;
    private RecyclerView recycle_mainServices;
    private TextView txt_userName;
    private ImageView img_profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_home, container, false);
        BASEURL=getResources().getString(R.string.base_url);
        BANNERURL=getResources().getString(R.string.banner_url);
        intialization();
        fetchBanner();
        loadServices();
        getUserInfo();
        gotoUserProfile();
        return rootView;
    }


    private void intialization()
    {
        slider_banner=rootView.findViewById(R.id.slider_banner);
        recycle_mainServices=rootView.findViewById(R.id.recycle_mainServices);
        txt_userName=rootView.findViewById(R.id.txt_userName);
        img_profile=rootView.findViewById(R.id.img_profile);
        }


     private void loadServices()
     {
         MainServicesModel[] myListData = new MainServicesModel[]{
                 new MainServicesModel(1,"Steam  Ironing", R.drawable.img_ironlogo),
                 new MainServicesModel(2,"Laundry", R.drawable.img_landuarylogo),
                 new MainServicesModel(3,"Dry Cleaning", R.drawable.img_drycleaninglogo),
         };
         MainServicesAdapter adapter=new MainServicesAdapter(myListData);
         recycle_mainServices.setHasFixedSize(true);
         recycle_mainServices.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
         recycle_mainServices.setAdapter(adapter);
     }

    private void fetchBanner()
    {
        RequestQueue queue;
        slideModels=new ArrayList<>();
        queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, BASEURL+"reteriveBanner.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        String name = object1.getString("bannerImage");
                        String finalUrl=BANNERURL+"Images/banner/"+name;
                        Log.i("SUMIT",name);
                        slideModels.add(new SlideModel(finalUrl, ScaleTypes.FIT));
                    }
                    slider_banner.setImageList(slideModels);
                    //txt_webnwork.setText("email: " + "name: " + slideModels.get(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);
    }

    private void getUserInfo()
    {
        SharedPreferences sh = this.getActivity().getSharedPreferences("kapdepress", Context.MODE_PRIVATE);
        txt_userName.setText("Hey "+sh.getString("username", ""));

    }

    private void gotoUserProfile()
    {
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ProfileActivity.class);
                startActivity(i);
            }
        });
    }
}