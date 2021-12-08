package com.kapdepress.kapdepress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class signupotpScreenActivity extends AppCompatActivity {
private ImageView img_back;
private TextView txt_phoneno;
private Button btn_verify;
private EditText et_otp;
private FirebaseAuth mAuth;
private static String MOBILE_NUMBER=null;
private String VerificationId;
private ProgressDialog progress;
private ConstraintLayout layout_mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupotp_screen);
        initialization();
        goBack();
        getMobNo();
        verifyUser();
    }
    private void initialization()
    {
        layout_mainLayout=findViewById(R.id.layout_mainLayout);
        progress=new ProgressDialog(this, R.style.MyAlertDialogStyle);
        mAuth = FirebaseAuth.getInstance();
        img_back=findViewById(R.id.img_back);
        txt_phoneno=findViewById(R.id.txt_phoneno);
        btn_verify=findViewById(R.id.btn_verify);
        et_otp=findViewById(R.id.et_otp);
        progress.setMessage("Verifying");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
    }

    private void getMobNo()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            MOBILE_NUMBER = extras.getString("userMobileNumber");
            VerificationId = extras.getString("verificationid");
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

    private void verifyUser()
    {
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_otp.getText().toString().isEmpty())
                {
                    et_otp.setError("Please Enter OTP");
                }
                else
                {
                    progress.show();
                    verifyCode(et_otp.getText().toString());
                }
            }
        });
    }

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationId, code);
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
                            progress.dismiss();
                            Intent i=new Intent(getApplicationContext(),CompleteProfileActivity.class);
                            i.putExtra("mobno",MOBILE_NUMBER);
                            startActivity(i);
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            progress.dismiss();
                            Snackbar.make(layout_mainLayout,"Please enter correct otp",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }
}