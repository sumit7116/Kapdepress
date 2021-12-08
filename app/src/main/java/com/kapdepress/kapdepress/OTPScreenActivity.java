package com.kapdepress.kapdepress;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OTPScreenActivity extends AppCompatActivity {
private ImageView img_back;
private TextView txt_phoneno;
private EditText et_otp;
private Button btn_verify;
private FirebaseAuth mAuth;
private static String MOBILE_NUMBER=null;
private String VerificationId;
private ProgressDialog progress;
private ConstraintLayout layout_mainLayout;
private String BASEURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);
        initialization();
        getMobNo();
        verifyUser();
    }

    private void initialization()
    {
        BASEURL=getResources().getString(R.string.base_url);
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
                        PhoneLoginActivity.class);
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
                            progress.setMessage("Please wait");
                            getUserData();
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            progress.dismiss();
                            Snackbar.make(layout_mainLayout,"Please enter correct otp",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }


private void getUserData()
{

    StringRequest stringRequest = new StringRequest(Request.Method.POST, BASEURL + "loginwithmob.php", new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            if(response.equals("1"))
            {
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to Login", Toast.LENGTH_SHORT).show();
                Intent i =new Intent(getApplicationContext(),LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
            else if(response.equals(""))
            {
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to Login", Toast.LENGTH_SHORT).show();
                Intent i =new Intent(getApplicationContext(),LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
            else
            {
                String name="";
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
                        progress.dismiss();
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
            progress.dismiss();
            Toast.makeText(getApplicationContext(), "Failed to Login", Toast.LENGTH_SHORT).show();
            Intent i =new Intent(getApplicationContext(),LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }) {
        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> param = new HashMap<String, String>();
            param.put("userMobno", MOBILE_NUMBER);
            //param.put("userPassword", et_passowrd.getText().toString().trim());
            return param;
        }
    };

    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
    queue.add(stringRequest);
    queue.add(stringRequest);
}
}

