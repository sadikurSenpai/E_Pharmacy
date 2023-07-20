package com.example.arrogo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
   private List<CartModel> list;
   private Context context;


    public CartAdapter(List<CartModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_model,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       if(list.get(position).getRandomKey().equals("--") || list.size()==0){
           holder.add.setEnabled(false);
           holder.remove.setEnabled(false);
           holder.add.setAlpha(0f);
           holder.remove.setAlpha(0f);
           holder.delete.setEnabled(false);
           holder.delete.setAlpha(0f);
           holder.productName.setText("Nothing here yet !");
           holder.productPrice.setText("");
           holder.productQuantity.setText("");
           holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.notfound));
            return;
       }


        Glide.with(context).load(list.get(position).getPictureLink()).into(holder.imageView);
        holder.productName.setText(list.get(position).getProductName());
        String price=list.get(position).getProductPrice();
//        int x=Integer.parseInt(price);
        holder.productPrice.setText(price+" BDT");
        String quantity=list.get(position).getProductQuantity();
        holder.productQuantity.setText(quantity);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity=list.get(position).getProductQuantity();
                int q=Integer.parseInt(quantity);
                q=q+1;
                holder.productQuantity.setText(q+"");
                databaseReference.child("Carts").child(SplashScreen.trimmedMail).child(list.get(position).getRandomKey())
                        .child("productQuantity").setValue(q+"");
               String sPrice= list.get(position).getSingleProductPrice();
                int singleProductPrice=Integer.parseInt(sPrice);
                int p=singleProductPrice*q;
                holder.productPrice.setText(p+"");
                databaseReference.child("Carts").child(SplashScreen.trimmedMail).child(list.get(position).getRandomKey())
                        .child("productPrice").setValue(p+"");

            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity=list.get(position).getProductQuantity();
                int q=Integer.parseInt(quantity);
                q=q-1;
                if(q==0){
                    return;
                }
                holder.productQuantity.setText(q+"");
                databaseReference.child("Carts").child(SplashScreen.trimmedMail).child(list.get(position).getRandomKey())
                        .child("productQuantity").setValue(q+"");
                String sPrice= list.get(position).getSingleProductPrice();
                int singleProductPrice=Integer.parseInt(sPrice);
                int p=singleProductPrice*q;
                holder.productPrice.setText(p+"");
                databaseReference.child("Carts").child(SplashScreen.trimmedMail).child(list.get(position).getRandomKey())
                        .child("productPrice").setValue(p+"");
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                databaseReference.child("Carts").child(SplashScreen.trimmedMail).child(list.get(position).getRandomKey()).removeValue();
                Toast.makeText(context, "Removed !", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView,add,remove,delete;
        private TextView productName,productPrice,productQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.cartModelImage);
            add=itemView.findViewById(R.id.cartModelProductAdd);
            remove=itemView.findViewById(R.id.cartModelProductRemove);
            productName=itemView.findViewById(R.id.cartModelProductName);
            productPrice=itemView.findViewById(R.id.cartModelProductPrice);
            productQuantity=itemView.findViewById(R.id.cartModelProductQuantity);
            delete=itemView.findViewById(R.id.cartModelDelete);

        }
    }
}
