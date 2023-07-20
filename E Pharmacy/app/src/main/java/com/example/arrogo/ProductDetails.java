package com.example.arrogo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductDetails extends AppCompatActivity {
    private Bundle bundle;
    private String productNameString,companyNameString,sellingPriceString,MRPString,descriptionString,pictureLinkString,titleString;
    private ImageView imageView;
    private TextView productName,companyName,sellingPrice,MRP,description;
    private Button addToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        bundle=getIntent().getExtras();
        productNameString=bundle.getString("productName");
        companyNameString=bundle.getString("companyName");
        sellingPriceString=bundle.getString("sellingPrice");
        MRPString=bundle.getString("MRP");
        descriptionString=bundle.getString("description");
        pictureLinkString=bundle.getString("pictureLink");
        titleString=bundle.getString("title");

        imageView=findViewById(R.id.productDetailsImage);
        productName=findViewById(R.id.productDetailsProductName);
        companyName=findViewById(R.id.productDetailsCompanyName);
        sellingPrice=findViewById(R.id.productDetailsSellingPrice);
        MRP=findViewById(R.id.productDetailsMRP);
        description=findViewById(R.id.productDetailsDetails);
        addToCart=findViewById(R.id.productDetailsAddButton);


        productName.setText(productNameString);
        companyName.setText(companyNameString);
        sellingPrice.setText("Price: "+sellingPriceString+" BDT");
        MRP.setText("MRP: "+MRPString+" BDT");
        description.setText(descriptionString);
        Glide.with(this).load(pictureLinkString).into(imageView);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                String key = databaseReference.push().getKey();
                databaseReference.child("Carts").child(SplashScreen.trimmedMail).child(key)
                        .setValue(new CartModel(key,pictureLinkString,productNameString,sellingPriceString,"1",sellingPriceString));
                Toast.makeText(ProductDetails.this, "Added into Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}