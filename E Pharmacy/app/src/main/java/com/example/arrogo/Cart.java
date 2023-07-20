package com.example.arrogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cart extends AppCompatActivity {
    private List<CartModel> list;
    private LinearLayoutManager layoutManager;
    private CartAdapter adapter;
    private RecyclerView recyclerView;
    private Button totalCost;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        totalCost=findViewById(R.id.cartTotalCost);
        list=new ArrayList<>();
        recyclerView=findViewById(R.id.cartRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new CartAdapter(list,this);
        recyclerView.setAdapter(adapter);

        databaseReference.child("Carts").child(SplashScreen.trimmedMail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    int cost=0;
                    for (DataSnapshot x:snapshot.getChildren()){
                        CartModel model=x.getValue(CartModel.class);
                        cost=cost+ Integer.parseInt(model.getProductPrice()) ;
                        totalCost.setText("Pay "+cost+" BDT");
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    totalCost.setEnabled(false);
                    totalCost.setAlpha(0f);
                    list.add(new CartModel("--","","","","",""));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Cart.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        totalCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogExit = new AlertDialog.Builder(Cart.this);
                View layout_dialog = LayoutInflater.from(Cart.this).inflate(R.layout.pay_now_dialogue_box, null);
                dialogExit.setView(layout_dialog);
                dialogExit.setTitle("Payment !!");
//                dialogExit.setMessage("Please pay the total cost");

                dialogExit.setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                        for(int j=0;j<list.size();j++){
                            String x=databaseReference.push().getKey();
                        databaseReference.child("History").child(SplashScreen.trimmedMail).child(x).setValue( new HistoryModel(list.get(j).getProductName(), list.get(j).getProductPrice()));

                        }
                       databaseReference.child("Carts").child(SplashScreen.trimmedMail).removeValue();

                       startActivity(new Intent(Cart.this,SplashScreen.class));
                       finish();
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
            }
        });
    }
}