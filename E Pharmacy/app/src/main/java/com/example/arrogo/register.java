package com.example.arrogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arrogo.Utility.NetworkChangeListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    private EditText userMail,userName, pass, confirmPass, location;
    private Button confirmButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private CheckBox checkBox;
    private TextView textView;
    private int flag;
    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();
    @Override
    protected void onStart() {
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        location=findViewById(R.id.signUpUserLocation);
        userName=findViewById(R.id.signUpUserName);
        textView=findViewById(R.id.textView111);
        userMail=findViewById(R.id.signUpUserMail);
        pass=findViewById(R.id.signUpPass);
        confirmPass=findViewById(R.id.signUpConfirmPass);
        confirmButton=findViewById(R.id.signUpCreateButton);
        checkBox=findViewById(R.id.signUpCheckbox);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loc=location.getText().toString();
                String name=userName.getText().toString();
                String mail=userMail.getText().toString();
                String password=pass.getText().toString();
                String confirmPassword=confirmPass.getText().toString();
                if(name.isEmpty()){
                    userName.setError("Please enter !");
                    userName.requestFocus();
                    return;
                }
                if(mail.isEmpty()){
                    userMail.setError("Please enter !");
                    userMail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    userMail.setError("Not valid");
                    userMail.requestFocus();
                    return;
                }
                if(loc.isEmpty()){
                    location.setError("Please enter !");
                    location.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    pass.setError("Please enter !");
                    pass.requestFocus();
                    return;
                }
                if(confirmPassword.isEmpty()){
                    confirmPass.setError("Please enter !");
                    confirmPass.requestFocus();
                    return;
                }
                if(password.length()<6){
                    pass.setError("at least 6 digit");
                    pass.requestFocus();
                    return;
                }
                if(!confirmPassword.equals(password)){
                    confirmPass.setError("Didn't match with the password !");
                    confirmPass.requestFocus();
                    return;
                }
                boolean status=checkBox.isChecked();
                if(status){
                    {
                        AlertDialog.Builder dialogExit = new AlertDialog.Builder(register.this);
                        View layout_dialog = LayoutInflater.from(register.this).inflate(R.layout.pay_now_dialogue_box_1, null);
                        dialogExit.setView(layout_dialog);
                        dialogExit.setTitle("Payment !!");
                        dialogExit.setMessage("Please pay 500 tk");

                        dialogExit.setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                String tempMail=mail.replace("@","");
                                String newMail=tempMail.replace(".","");
                                firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        String tempMail=mail.replace("@","");
                                        String newMail=tempMail.replace(".","");
                                        databaseReference.child("Users").child(newMail).setValue(new ModelUser(name,"seller",loc));
                                        Toast.makeText(register.this, "Seller account created successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(register.this,SplashScreen.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                        dialogExit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = dialogExit.create();
                        dialog.show();
                        dialog.setCancelable(false);
                        dialog.getWindow().setGravity(Gravity.CENTER);
//                        dialogExit.show();
                    }
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {


                            String tempMail=mail.replace("@","");
                            String newMail=tempMail.replace(".","");

//                        System.out.println(newMail);
                            Toast.makeText(register.this, "Account Created Successfully !", Toast.LENGTH_SHORT).show();

                            databaseReference.child("Users").child(newMail).setValue(new ModelUser(name,"buyer",loc));
                            startActivity(new Intent(getApplicationContext(),SplashScreen.class));


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
    }
}