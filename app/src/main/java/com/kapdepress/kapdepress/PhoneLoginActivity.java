package com.kapdepress.kapdepress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity {
    private EditText et_mobno;
    private Button btn_sendotp;
    private Boolean MOBILE_EXIST=false;
    private ConstraintLayout layout_mainLayout;
    private String BASEURL;
    private ProgressDialog progressDialog;
    private String verificationId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        initalization();
        sendOTP();

    }

    private void initalization()
    {
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Validating");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        BASEURL=getResources().getString(R.string.base_url);
        layout_mainLayout=findViewById(R.id.layout_mainLayout);
        et_mobno=findViewById(R.id.et_mobno);
        btn_sendotp=findViewById(R.id.btn_sendotp);
    }

    private void sendOTP()
    {
        btn_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMobNo();
            }
        });

    }

    private void checkMobNo()
    {
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, BASEURL+"checkMobExist.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            if (response.equals("0"))
            {
                progressDialog.setMessage("Sending OTP");
                sendVerificationCode(et_mobno.getText().toString());
            }
            else
            {
                progressDialog.dismiss();
                Snackbar.make(layout_mainLayout,"Sorry! Account not found.",Snackbar.LENGTH_LONG).show();
            }
            }

    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
            Snackbar.make(layout_mainLayout,"Something went wrong",Snackbar.LENGTH_LONG).show();
        }
    }){
        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> param=new HashMap<String,String>();
            param.put("userMobno",et_mobno.getText().toString().trim());
            return param;
        }
    };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);

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
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            progressDialog.dismiss();
            Intent i = new Intent(getApplicationContext(), OTPScreenActivity.class);
            i.putExtra("userMobileNumber",et_mobno.getText().toString());
            i.putExtra("verificationid",s);
            startActivity(i);
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
            }
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            progressDialog.dismiss();
            Snackbar.make(layout_mainLayout,"Failed to send otp",Snackbar.LENGTH_LONG).show();
            Log.i("SAM",e.getMessage().toString());
        }
    };

}