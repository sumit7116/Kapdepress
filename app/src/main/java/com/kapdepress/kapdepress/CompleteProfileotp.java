package com.kapdepress.kapdepress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CompleteProfileotp extends AppCompatActivity {
    private ImageView img_back;
    private TextView txt_phoneno;
    private Button btn_verify;
    private EditText et_otp;
    private static String MOBILE_NUMBER=null;
    private FirebaseAuth mAuth;
    private String verificationId;
    private RelativeLayout rel_mainlayout;
    private String BASEURL;
    private ProgressBar prg_signup;
    private  AlertDialog.Builder builder;
    private  View customLayout;
    private  TextView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profileotp);
        initialization();
        getMobNo();
        goBack();
        verifyUser();
        sendVerificationCode(MOBILE_NUMBER);
    }

    private void initialization()
    {
        mAuth = FirebaseAuth.getInstance();
        img_back=findViewById(R.id.img_back);
        txt_phoneno=findViewById(R.id.txt_phoneno);
        btn_verify=findViewById(R.id.btn_verify);
        et_otp=findViewById(R.id.et_otp);
        rel_mainlayout=findViewById(R.id.rel_mainlayout);
    }

    private void getMobNo()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            MOBILE_NUMBER = extras.getString("userMobileNumber");
            txt_phoneno.setText("We have sent an \nOTP to +91"+MOBILE_NUMBER);
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
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                et_otp.setText(code);

            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Snackbar.make(rel_mainlayout,"Failed to send otp",Snackbar.LENGTH_LONG).show();
            Log.i("SAM",e.getMessage().toString());
        }
    };

    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
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
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                            Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Snackbar.make(rel_mainlayout,"Invalid OTP",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void verifyUser()
    {
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_otp.getText().toString().isEmpty())
                {
                    et_otp.setError("Please Enter OTP");
                    et_otp.requestFocus();
                }
                else
                {
                    verifyCode(et_otp.getText().toString());
                }
            }
        });
    }
}