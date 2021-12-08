package com.kapdepress.kapdepress;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {
private ImageView img_back;
private EditText et_userName,et_mobno,et_alternate_mobno,et_email,et_pinCode,et_referCode,et_passowrd,et_confirmPassowrd,et_userOtp;
private TextView  txt_gotologin,txt_verifymobno,txt_verifyotp;
private String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
private Boolean anyValidationError=false;
private RelativeLayout rel_mainlayout,rel_signupphone,rel_signup,rel_circle_shadow1;
private String BASEURL;
private ProgressBar prg_signup;
private  AlertDialog.Builder builder;
private  View customLayout;
private Boolean mobVerified=false;
private  TextView editText;
private String verificationId;
private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        BASEURL=getResources().getString(R.string.base_url);
        initialization();
        gotoBack();
        signupUser();
        gotoLogin();
        signupPhone();
        validate_otp();
        sendOTP();
    }

    private void initialization()
    {
        mAuth = FirebaseAuth.getInstance();
        txt_verifyotp=findViewById(R.id.txt_verifyotp);
        txt_verifymobno=findViewById(R.id.txt_verifymobno);
        et_userOtp=findViewById(R.id.et_userOtp);
        img_back=findViewById(R.id.img_back);
        rel_mainlayout=findViewById(R.id.rel_mainlayout);
        et_userName=findViewById(R.id.et_userName);
        et_mobno=findViewById(R.id.et_mobno );
        et_alternate_mobno=findViewById(R.id.et_alternate_mobno );
        et_email=findViewById(R.id.et_email );
        et_pinCode=findViewById(R.id.et_pinCode );
        et_referCode=findViewById(R.id. et_referCode);
        et_passowrd=findViewById(R.id.et_passowrd );
        et_confirmPassowrd=findViewById(R.id. et_confirmPassowrd);
        rel_signup=findViewById(R.id.rel_signup);
        txt_gotologin=findViewById(R.id.txt_gotologin );
        rel_signupphone=findViewById(R.id.rel_signupphone);
        prg_signup=findViewById(R.id.prg_signup);

        txt_gotologin=findViewById(R.id.txt_gotologin);
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

    }

    private void gotoBack()
    {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        LoginActivity.class);
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
                    if(mobVerified==true)
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
                        rel_signupphone.setEnabled(false);
                        rel_circle_shadow1.setEnabled(false);
                        txt_gotologin.setEnabled(false);
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
                                rel_signupphone.setEnabled(true);
                                rel_circle_shadow1.setEnabled(true);
                                txt_gotologin.setEnabled(true);
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
                                    rel_signupphone.setEnabled(true);
                                    rel_circle_shadow1.setEnabled(true);
                                    txt_gotologin.setEnabled(true);
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
                    else
                    {
                     Snackbar.make(rel_mainlayout,"Please Verify Mobile Number",Snackbar.LENGTH_LONG).show();
                    }

                }

            }
        });
    }

    private void gotoLogin()
    {
        txt_gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void signupPhone()
    {
        rel_signupphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignupwithphoneActivity.class));
            }
        });
    }
    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Snackbar.make(rel_mainlayout,"OTP sent", BaseTransientBottomBar.LENGTH_LONG).show();
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
            }
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Snackbar.make(rel_mainlayout,"Failed to send otp",Snackbar.LENGTH_LONG).show();
            Log.i("SAM",e.getMessage().toString());
        }
    };

    private void sendOTP()
    {
        txt_verifymobno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_mobno.getText().toString().isEmpty())
                {
                    et_mobno.setError("Please Enter Mobile Number");
                    et_mobno.requestFocus();
                }
                else
                {
                    sendVerificationCode(et_mobno.getText().toString());
                    et_userOtp.setVisibility(View.VISIBLE);
                    txt_verifyotp.setVisibility(View.VISIBLE);
                    Snackbar.make(rel_mainlayout,"Sending OTP",Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }

    private void validate_otp()
    {
        txt_verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode(et_userOtp.getText().toString());
            }
        });
    }

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            txt_verifyotp.setText("Verified");
                            txt_verifyotp.setTextColor(Color.GREEN);
                            et_userOtp.setVisibility(View.GONE);
                            txt_verifymobno.setVisibility(View.GONE);
                            mobVerified=true;
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Snackbar.make(rel_mainlayout,"Invalid OTP",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }
}