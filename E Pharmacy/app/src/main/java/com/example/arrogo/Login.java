package com.example.arrogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arrogo.Utility.NetworkChangeListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText email,pass;
    private FirebaseAuth mAuth;
    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.signInUserMail);
        pass=findViewById(R.id.signInPass);

    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    @Override
    protected void onStart() {
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            startActivity(new Intent(this,SplashScreen.class));
            finish();
        }
    }

    public void LogIn(View view) {
        String mail=email.getText().toString().trim();
        String password=pass.getText().toString().trim();
        if(mail.isEmpty()){
            email.setError("Enter mail");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("Not valid");
            email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            pass.setError("Enter password");
            pass.requestFocus();
            return;
        }
        if(password.length()<6){
            pass.setError("At least 6 digit");
            pass.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Login.this, "Successful !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),SplashScreen.class));
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e.getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")){
                    email.setError("Wrong email");
                    email.requestFocus();
                }
                else if(e.getMessage().equals("The password is invalid or the user does not have a password.")){
                    pass.setError("Wrong Password");
                    pass.requestFocus();
                }
                else {
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void SignUp(View view) {
        Intent intent=new Intent(this,register.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogExit = new AlertDialog.Builder(Login.this);

        dialogExit.setTitle("EXIT!!");
        dialogExit.setMessage("Are you sure?");

        dialogExit.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
                finish();
            }
        });

        dialogExit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogExit.show();
    }
}