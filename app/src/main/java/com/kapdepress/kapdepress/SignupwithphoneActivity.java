package com.kapdepress.kapdepress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignupwithphoneActivity extends AppCompatActivity {
private EditText et_mobno;
private Button btn_sendotp;
private ImageView img_back;
private String verificationId;
private FirebaseAuth mAuth;
private ProgressDialog progress;
private ConstraintLayout layout_mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupwithphone);
        initialization();
        sendOtp();
        goBack();
    }
    private void initialization()
    {
        mAuth = FirebaseAuth.getInstance();
        progress=new ProgressDialog(this, R.style.MyAlertDialogStyle);
        layout_mainLayout=findViewById(R.id.layout_mainLayout);
        et_mobno=findViewById(R.id.et_mobno);
        btn_sendotp=findViewById(R.id.btn_sendotp);
        img_back=findViewById(R.id.img_back);
        progress.setMessage("Sending OTP");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
    }
    private void goBack()
    {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
    private void sendOtp()
    {
        btn_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_mobno.getText().toString().isEmpty())
                {
                    et_mobno.setError("Please enter mobile number");
                }
                else if(et_mobno.getText().toString().length()>10)
                {
                    et_mobno.setError("Please enter valid mobile number");
                }
                else if(et_mobno.getText().toString().length()<10)
                {
                    et_mobno.setError("Please enter valid mobile number");
                }
                else if(!TextUtils.isDigitsOnly(et_mobno.getText().toString()))
                {
                    et_mobno.setError("Please enter valid mobile number");
                }
                else
                {

                   progress.show();
                   sendVerificationCode(et_mobno.getText().toString());



                }

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
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            progress.dismiss();
            Intent i = new Intent(getApplicationContext(), signupotpScreenActivity.class);
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
            Snackbar.make(layout_mainLayout,"Failed to send otp",Snackbar.LENGTH_LONG).show();
            Log.i("SAM",e.getMessage().toString());
        }
    };


}