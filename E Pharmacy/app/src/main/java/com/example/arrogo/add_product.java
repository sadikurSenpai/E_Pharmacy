package com.example.arrogo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Locale;

public class add_product extends AppCompatActivity {
    private Bundle bundle;
    private String title;
    private EditText productName,companyName,sellingPrice,MRP,description;
    private ImageView imageView;
    private Button createButton;
    private ProgressBar progressBar;
//    private String trimmedTitle;
    private String pictureLink="";
    final int code = 999;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        progressBar=findViewById(R.id.addProductProgressBar);
        createButton=findViewById(R.id.addProductCreateButton);
        imageView=findViewById(R.id.addProductChoosePicture);
        productName=findViewById(R.id.addProductProductName);
        companyName=findViewById(R.id.addProductCompanyName);
        sellingPrice=findViewById(R.id.addProductSellingPicture);
        MRP=findViewById(R.id.addProductMRP);
        description=findViewById(R.id.addProductDescription);

        bundle=getIntent().getExtras();
        title=bundle.getString("title");
        System.out.println(title);
//        trimmedTitle=title.replace(" ","");
//        System.out.println(trimmedTitle);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String productNameString=productName.getText().toString();
               String companyNameString=companyName.getText().toString();
               String sellingPriceString=sellingPrice.getText().toString();
               String MRPString=MRP.getText().toString();
               String descriptionString=description.getText().toString();
                if(productNameString.isEmpty()){
                    productName.setError("Fill Up");
                    productName.requestFocus();
                    return;
                }
                if(companyNameString.isEmpty()){
                    companyName.setError("Fill Up");
                    companyName.requestFocus();
                    return;
                }
                if(sellingPriceString.isEmpty()){
                    sellingPrice.setError("Fill Up");
                    sellingPrice.requestFocus();
                    return;
                }
                if(MRPString.isEmpty()){
                    MRP.setError("Fill Up");
                    MRP.requestFocus();
                    return;
                }
                if(descriptionString.isEmpty()){
                    description.setError("Fill Up");
                    description.requestFocus();
                    return;
                }
                if(pictureLink.isEmpty()){
                    Toast.makeText(add_product.this, "Upload A picture !", Toast.LENGTH_SHORT).show();
                    return;
                }

//                String trimmedProductName=productNameString.replace(" ","");
            databaseReference.child("Products").child(title).child(SplashScreen.trimmedMail+productNameString).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        productName.setError("Already Exists");
                        productName.requestFocus();
                    }
                    else {
                        databaseReference.child("Products").child(title).
                                child(SplashScreen.trimmedMail+productNameString).
                                setValue(new ProductModel(pictureLink,productNameString,companyNameString,
                                        sellingPriceString,MRPString,descriptionString,title));
                        String key = databaseReference.push().getKey();

                        databaseReference.child("My Posts").child(SplashScreen.trimmedMail).child(key).
                                setValue(new MyServiceModel(key,productNameString,sellingPriceString,title,SplashScreen.trimmedMail));
                        Toast.makeText(add_product.this, "Posted !", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(add_product.this,SplashScreen.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finishAffinity();
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            }
        });
    }
    public void signUpPictureChoosingButton(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == code && resultCode == Activity.RESULT_OK && data != null) {
            progressBar.setVisibility(View.VISIBLE);
            createButton.setEnabled(false);
            Uri result = data.getData();
            String key= databaseReference.push().getKey();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("picLink"+SplashScreen.trimmedMail+key);
            storageRef.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            pictureLink=uri.toString();

                            imageView.setImageURI(result);
                            progressBar.setVisibility(View.INVISIBLE);
                            createButton.setEnabled(true);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Couldn't Upload !!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}