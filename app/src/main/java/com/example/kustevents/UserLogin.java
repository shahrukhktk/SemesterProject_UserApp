package com.example.kustevents;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.concurrent.TimeUnit;

public class UserLogin extends AppCompatActivity {

    private Button SendVerifyCode, SignInBTN;

    private EditText userPhoneNum;
    private EditText VerificationCode;

    String code_sent;

    FirebaseAuth firebaseAuth;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        firebaseAuth = FirebaseAuth.getInstance();

        userPhoneNum = findViewById(R.id.phoneNo_id);
        VerificationCode = findViewById(R.id.phoneNo_code_id);

        SignInBTN = findViewById(R.id.SIGNIN_BTN_ID);
        SendVerifyCode = findViewById(R.id.getVerificationCodeBTNID);

        SendVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });

        SignInBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = VerificationCode.getText().toString().trim();
                if(code.isEmpty())
                {
                    VerificationCode.setError("Code is required!");
                    VerificationCode.requestFocus();
                    return;
                }
                if(code.length() < 6)
                {
                    VerificationCode.setError("Required xxxxxx");
                    VerificationCode.requestFocus();
                    return;
                }
                verifySignInCode(code);
            }
        });

    }

    //Take code from user and send it to verify
    private void verifySignInCode(String code)
    {
        String codeSendByUser = VerificationCode.getText().toString().trim();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code_sent, codeSendByUser);
        signInWithPhoneAuthCredential(credential);
//        progress = new ProgressDialog(getApplicationContext());
//        progress.setTitle("Loading");
//        progress.setMessage("Login Process...");
//        progress.setCancelable(false);
//        progress.show();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(UserLogin.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                            Intent intent = new Intent(getApplicationContext(), User_Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(UserLogin.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationCode()
    {
        String phoneNumber = "+92" + userPhoneNum.getText().toString().trim();

        if (phoneNumber.isEmpty())
        {
            userPhoneNum.setError("Phone number is required!");
            userPhoneNum.requestFocus();
            return;
        }
        if (phoneNumber.length() < 11)
        {
            userPhoneNum.setError("Format : 03000000000");
            userPhoneNum.requestFocus();
            return;
        }
        //send code to user
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    code_sent = phoneAuthCredential.getSmsCode();
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(UserLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a code_Send ID.

                    // Save verification ID and resending token so we can use them later
                    code_sent = s;
                    mResendToken = forceResendingToken;
                }
            };
}
