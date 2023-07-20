package com.example.arrogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPosts extends AppCompatActivity {
    private List<MyServiceModel> list;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyServiceAdapter adapter;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        databaseReference=FirebaseDatabase.getInstance().getReference();
        list=new ArrayList<>();
        recyclerView=findViewById(R.id.myPostRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MyServiceAdapter(list,this);
        recyclerView.setAdapter(adapter);
        databaseReference.child("My Posts").child(SplashScreen.trimmedMail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot x: snapshot.getChildren()){
                        MyServiceModel model=x.getValue(MyServiceModel.class);
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    list.add(new MyServiceModel("","No Post Yet","","",""));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyPosts.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}