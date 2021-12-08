package com.kapdepress.kapdepress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class CompleteProfileActivity extends AppCompatActivity {
private static String MOBILE_NUMBER;
private EditText et_userName,et_mobno,et_alternate_mobno,et_email,et_pinCode,et_referCode,et_passowrd,et_confirmPassowrd;
private ImageView img_back;
private Boolean anyValidationError=false;
private String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
private RelativeLayout rel_signup,rel_mainlayout;
private ProgressBar prg_signup;
private String BASEURL;
private  AlertDialog.Builder builder;
private  View customLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        initialization();
        getMobNo();
        signupUser();
    }


    private void initialization()
    {
        BASEURL=getResources().getString(R.string.base_url);
        et_userName=findViewById(R.id.et_userName);
        et_mobno=findViewById(R.id.et_mobno);
        et_alternate_mobno=findViewById(R.id.et_alternate_mobno);
        et_email=findViewById(R.id.et_email);
        et_pinCode=findViewById(R.id.et_pinCode);
        et_referCode=findViewById(R.id.et_referCode);
        et_passowrd=findViewById(R.id.et_passowrd);
        et_confirmPassowrd=findViewById(R.id.et_confirmPassowrd);
        img_back=findViewById(R.id.img_back);
        rel_signup=findViewById(R.id.rel_signup);
        rel_mainlayout=findViewById(R.id.rel_mainlayout);
        prg_signup=findViewById(R.id.prg_signup);
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
    }

    private void getMobNo()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            MOBILE_NUMBER = extras.getString("userMobileNumber");
            et_mobno.setText(MOBILE_NUMBER);
            et_mobno.setEnabled(false);
        }
        else
        {
            goBack();
        }
    }

    private void goBack()
    {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        SignupwithphoneActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void validateFields()
    {

        if(et_userName.getText().toString().isEmpty())
        {
            anyValidationError=true;
            et_userName.setError("Please enter your name");
            et_userName.requestFocus();
        }
        else if(!et_userName.getText().toString().matches("^[A-Za-z]+$"))
        {
            anyValidationError=true;
            et_userName.setError("Please enter valid name");
            et_userName.requestFocus();
        }
        else if(et_mobno.getText().toString().isEmpty())
        {
            anyValidationError=true;
            et_mobno.setError("Please enter mobile number");
            et_mobno.requestFocus();
        }
        else if(et_mobno.getText().toString().length()>10)
        {
            anyValidationError=true;
            et_mobno.setError("Please enter valid mobile number");
            et_mobno.requestFocus();
        }
        else if(et_mobno.getText().toString().length()<10)
        {
            anyValidationError=true;
            et_mobno.setError("Please enter valid mobile number");
            et_mobno.requestFocus();
        }
        else if(!et_alternate_mobno.getText().toString().isEmpty())
        {
            if(!TextUtils.isDigitsOnly(et_alternate_mobno.getText().toString())) {
                anyValidationError = true;
                et_alternate_mobno.setError("Please enter valid mobile number");
                et_alternate_mobno.requestFocus();
            }
            else if(et_alternate_mobno.getText().toString().length()>10)
            {
                anyValidationError=true;
                et_alternate_mobno.setError("Please enter valid mobile number");
                et_alternate_mobno.requestFocus();
            }
            else if(et_alternate_mobno.getText().toString().length()<10)
            {
                anyValidationError=true;
                et_alternate_mobno.setError("Please enter valid mobile number");
                et_alternate_mobno.requestFocus();
            }
            else if(!TextUtils.isDigitsOnly(et_alternate_mobno.getText().toString()))
            {
                anyValidationError=true;
                et_alternate_mobno.setError("Please enter valid mobile number");
                et_alternate_mobno.requestFocus();
            }
        }
        else if(et_email.getText().toString().isEmpty())
        {
            anyValidationError=true;
            et_email.setError("Please enter valid email");
            et_email.requestFocus();
        }
        else if(!et_email.getText().toString().matches(EMAIL_PATTERN))
        {
            anyValidationError=true;
            et_email.setError("Please enter valid email");
            et_email.requestFocus();
        }
        else if(et_pinCode.getText().toString().isEmpty())
        {
            anyValidationError=true;
            et_pinCode.setError("Please enter pin code");
            et_pinCode.requestFocus();
        }
        else if(et_pinCode.getText().toString().length()>6)
        {
            anyValidationError=true;
            et_pinCode.setError("Please enter valid pin code");
            et_pinCode.requestFocus();
        }
        else if(et_pinCode.getText().toString().length()<6)
        {
            anyValidationError=true;
            et_pinCode.setError("Please enter valid pin code");
            et_pinCode.requestFocus();
        }
        else if(et_passowrd.getText().toString().isEmpty())
        {
            anyValidationError=true;
            et_passowrd.setError("Please enter password");
            et_passowrd.requestFocus();
        }
        else if(et_passowrd.getText().toString().length()<6)
        {
            anyValidationError=true;
            et_passowrd.setError("Password length more than 6 characters");
            et_passowrd.requestFocus();
        }
        else if(et_confirmPassowrd.getText().toString().isEmpty())
        {
            anyValidationError=true;
            et_confirmPassowrd.setError("Please enter password");
            et_confirmPassowrd.requestFocus();
        }
        else if(!et_confirmPassowrd.getText().toString().equals(et_passowrd.getText().toString()))
        {
            anyValidationError=true;
            et_confirmPassowrd.setError("Password not matched");
            et_confirmPassowrd.requestFocus();
        }
        else
        {
            anyValidationError=false;
        }
    }

    private void signupUser()
    {
        rel_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customLayout = getLayoutInflater().inflate(R.layout.layout_authentication_msg,null,false);
                builder.setView(customLayout);
                validateFields();
                if(anyValidationError==true)
                {
                    // Toast.makeText(getApplicationContext(), "in validation error", Toast.LENGTH_SHORT).show();
                }
                else
                {

                        prg_signup.setVisibility(View.VISIBLE);
                        et_userName.setEnabled(false);
                        et_mobno.setEnabled(false);
                        et_alternate_mobno.setEnabled(false);
                        et_email.setEnabled(false);
                        et_pinCode.setEnabled(false);
                        et_referCode.setEnabled(false);
                        et_passowrd.setEnabled(false);
                        et_confirmPassowrd.setEnabled(false);
                        rel_signup.setEnabled(false);
                        TextView editText = customLayout.findViewById(R.id.msg);
                        editText.setText("sam");
                        AlertDialog dialog= builder.create();
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        Button button=customLayout.findViewById(R.id.buttonOk);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                prg_signup.setVisibility(View.GONE);
                                et_userName.setEnabled(true);
                                et_mobno.setEnabled(true);
                                et_alternate_mobno.setEnabled(true);
                                et_email.setEnabled(true);
                                et_pinCode.setEnabled(true);
                                et_referCode.setEnabled(true);
                                et_passowrd.setEnabled(true);
                                et_confirmPassowrd.setEnabled(true);
                                rel_signup.setEnabled(true);

                            }
                        });
                        StringRequest stringRequest=new StringRequest(Request.Method.POST, BASEURL+"addUser.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("10"))
                                {
                                    editText.setText("Mobile Number Already Registered.");
                                    try {
                                        dialog.show();

                                    }catch (Exception ex)
                                    {

                                    }

                                }
                                else if(response.equals("11"))
                                {
                                    editText.setText("Email Already Registered.");
                                    dialog.show();
                                }
                                else if(response.equals("12"))
                                {
                                    editText.setText("Sorry Presently we are not serving in your area we will contact you soon.");
                                    dialog.show();
                                }
                                else if(response.equals("13"))
                                {
                                    editText.setText("Refer Code is not Valid.");
                                    dialog.show();
                                }
                                else if(response.equals("15"))
                                {
                                    editText.setText("Something went wrong");
                                    dialog.show();
                                }
                                else if(response.equals("14"))
                                {
                                    prg_signup.setVisibility(View.GONE);
                                    et_userName.setEnabled(true);
                                    et_mobno.setEnabled(true);
                                    et_alternate_mobno.setEnabled(true);
                                    et_email.setEnabled(true);
                                    et_pinCode.setEnabled(true);
                                    et_referCode.setEnabled(true);
                                    et_passowrd.setEnabled(true);
                                    et_confirmPassowrd.setEnabled(true);
                                    rel_signup.setEnabled(true);
                                    Snackbar.make(rel_mainlayout,"You have registered successfully.",Snackbar.LENGTH_LONG).show();
                                    StringRequest stringRequest1=new StringRequest(Request.Method.POST, BASEURL+"reterive_userID.php", new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            SharedPreferences sharedPreferences = getSharedPreferences("kapdepress", MODE_PRIVATE);
                                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                            myEdit.putString("loginstatus", "acitve");
                                            myEdit.putString("username", et_userName.getText().toString());
                                            myEdit.putString("mobno",et_mobno.getText().toString());
                                            myEdit.putString("alternatemob",et_alternate_mobno.getText().toString());
                                            myEdit.putString("emailid",et_email.getText().toString());
                                            myEdit.putString("pincode",et_pinCode.getText().toString());
                                            myEdit.putString("refercode",et_referCode.getText().toString());
                                            myEdit.putString("userid",response.toString());
                                            myEdit.apply();
                                            Intent i=new Intent(getApplicationContext(),DashboardActivity.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                        }

                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Snackbar.make(rel_mainlayout,error.toString(),Snackbar.LENGTH_LONG).show();
                                        }
                                    }){
                                        @Nullable
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String,String> param=new HashMap<String,String>();
                                            param.put("userMobno",et_mobno.getText().toString());
                                            return param;
                                        }
                                    };

                                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                                    queue.add(stringRequest1);


                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                editText.setText("Something went wrong");
                                dialog.show();
                            }
                        }){
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> param=new HashMap<String,String>();
                                param.put("userName",et_userName.getText().toString().trim());
                                param.put("userMobno",et_mobno.getText().toString().trim());
                                param.put("useralterMobno",et_alternate_mobno.getText().toString().trim());
                                param.put("userEmail",et_email.getText().toString().trim());
                                param.put("userPincode",et_pinCode.getText().toString().trim());
                                param.put("userReferid",et_referCode.getText().toString().trim());
                                param.put("userSignupmode","mobile");
                                param.put("userPassword",et_passowrd.getText().toString().trim());
                                param.put("userActivestatus","offline");
                                param.put("profile_strength","100");
                                param.put("vStatus","0");
                                return param;
                            }
                        };

                        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                        queue.add(stringRequest);


                }

            }
        });
    }

}