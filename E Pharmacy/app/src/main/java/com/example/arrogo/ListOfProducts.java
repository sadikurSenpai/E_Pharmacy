package com.example.arrogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListOfProducts extends AppCompatActivity {
private Bundle bundle;
private String title;
private TextView titleOfProduct;
private DatabaseReference databaseReference;
private Button postButton;

private LinearLayoutManager layoutManager;
private List<ProductModel> list;
private RecyclerView recyclerView;
private CategoryProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_products);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        bundle=getIntent().getExtras();
        title=bundle.getString("title");
        titleOfProduct=findViewById(R.id.listOfProductTitle);
        titleOfProduct.setText(title);
        postButton=findViewById(R.id.listOfProductPostButton);
        if(SplashScreen.Occupation.equals("buyer")){
            postButton.setEnabled(false);
            postButton.setAlpha(0f);
        }
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListOfProducts.this,add_product.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });

        recyclerView=findViewById(R.id.listOfProductRecyclerView);
        list=new ArrayList<>();
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new CategoryProductAdapter(list,this);
        recyclerView.setAdapter(adapter);


        databaseReference.child("Products").child(title).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        ProductModel model=snapshot1.getValue(ProductModel.class);
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
         list.add(new ProductModel("--","","","","","",""));
         adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListOfProducts.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}