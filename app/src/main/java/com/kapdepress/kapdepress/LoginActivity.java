package com.kapdepress.kapdepress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText et_userCode,et_passowrd;
    private RelativeLayout rel_loginBtn;
    private ConstraintLayout main_layout;
    private RelativeLayout rel_phone_login;
    private TextView txt_register;
    private ProgressBar prg_login;
    private String BASEURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BASEURL=getResources().getString(R.string.base_url);
        initialization();
        loginUser();
        loginPhoneNo();
        gotoRegsiter();
        loadData();
    }
    private void initialization()
    {
        main_layout=findViewById(R.id.main_layout);
        et_userCode=findViewById(R.id.et_otp);
        et_passowrd=findViewById(R.id.et_passowrd);
        rel_loginBtn=findViewById(R.id.rel_loginBtn);
        rel_phone_login=findViewById(R.id.rel_phone_login);
        txt_register=findViewById(R.id.txt_register);
        prg_login=findViewById(R.id.prg_login);
    }

    private void loginUser()
    {
        rel_loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_userCode.getText().toString().isEmpty())
                {
                    et_userCode.setError("Please enter user code");
                    et_userCode.requestFocus();
                }
                else if(et_passowrd.getText().toString().isEmpty())
                {
                    et_passowrd.setError("Please enter password");
                    et_passowrd.requestFocus();
                }
                else {
                    prg_login.setVisibility(View.VISIBLE);
                    et_userCode.setEnabled(false);
                    et_passowrd.setEnabled(false);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL + "loginwithusercode.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("1"))
                            {
                                Snackbar.make(main_layout,"Invalid Credentials",Snackbar.LENGTH_LONG).show();
                                prg_login.setVisibility(View.GONE);
                                et_userCode.setEnabled(true);
                                et_passowrd.setEnabled(true);

                            }
                            else if(response.equals(""))
                            {
                                prg_login.setVisibility(View.GONE);
                                et_userCode.setEnabled(true);
                                et_passowrd.setEnabled(true);
                                Snackbar.make(main_layout,"Something went wrong"+response.toString(),Snackbar.LENGTH_LONG).show();
                            }
                            else
                            {
                                String name="";
                                prg_login.setVisibility(View.GONE);
                                et_userCode.setEnabled(true);
                                et_passowrd.setEnabled(true);
                                //Log.i("SAM1",response);
                                try {

                                    JSONObject object = new JSONObject(response);
                                    JSONArray array = object.getJSONArray("data");
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object1 = array.getJSONObject(i);
                                         name = object1.getString("userName");
                                        Log.i("SUMIT",name);
                                        SharedPreferences sharedPreferences = getSharedPreferences("kapdepress", MODE_PRIVATE);
                                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                        myEdit.putString("loginstatus", "acitve");
                                        myEdit.putString("username", object1.getString("userName"));
                                        myEdit.putString("mobno",object1.getString("userMobno"));
                                        myEdit.putString("alternatemob",object1.getString("useralterMobno"));
                                        myEdit.putString("emailid",object1.getString("userEmail"));
                                        myEdit.putString("pincode",object1.getString("userPincode"));
                                        myEdit.putString("refercode",object1.getString("userReferid"));
                                        myEdit.putString("userid",object1.getString("userCode"));
                                        myEdit.putString("profile_strength",object1.getString("profile_strength"));
                                        myEdit.putString("userReferid",object1.getString("genReferid"));
                                        myEdit.putString("user_ProfilePic",object1.getString("user_ProfilePic"));
                                        myEdit.apply();
                                        Intent gotodash=new Intent(getApplicationContext(),DashboardActivity.class);
                                        gotodash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        gotodash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(gotodash);

                                    }
                                    //txt_webnwork.setText("email: " + "name: " + slideModels.get(0));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                /*SharedPreferences sharedPreferences = getSharedPreferences("kapdepress", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("loginstatus", "acitve");
                                myEdit.apply();
                                Intent i=new Intent(getApplicationContext(),DashboardActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);*/
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            prg_login.setVisibility(View.GONE);
                            et_userCode.setEnabled(true);
                            et_passowrd.setEnabled(true);
                            Snackbar.make(main_layout, "Something went wrong", Snackbar.LENGTH_LONG).show();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<String, String>();
                            param.put("userCode", et_userCode.getText().toString().trim());
                            param.put("userPassword", et_passowrd.getText().toString().trim());
                            return param;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(stringRequest);
                    queue.add(stringRequest);
                }
            }
        });
    }

    private void loginPhoneNo()
    {
        rel_phone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PhoneLoginActivity.class));
            }
        });

    }

    private void gotoRegsiter()
    {
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }

    private void loadData() {
        SharedPreferences sh = getSharedPreferences("kapdepress", MODE_PRIVATE);

        String userID = sh.getString("userid", "");
        et_userCode.setText(userID);
    }
}