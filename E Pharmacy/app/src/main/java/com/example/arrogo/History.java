package com.example.arrogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<HistoryModel> list;
    private DatabaseReference databaseReference;
    private HistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        list=new ArrayList<>();
        recyclerView=findViewById(R.id.historyRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new HistoryAdapter(list,this);
        recyclerView.setAdapter(adapter);

        databaseReference.child("History").child(SplashScreen.trimmedMail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for (DataSnapshot x:
                            snapshot.getChildren()) {
                        HistoryModel model=x.getValue(HistoryModel.class);
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(History.this, "No Purchase Yet!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(History.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}