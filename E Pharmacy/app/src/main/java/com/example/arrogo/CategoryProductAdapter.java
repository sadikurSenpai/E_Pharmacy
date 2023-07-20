package com.example.arrogo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.ViewHolder>  {
   private List<ProductModel> list;

   private Context context;

    public CategoryProductAdapter(List<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_category_products,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(list.size()==1 && list.get(position).getProductImage().equals("--")){
            holder.add.setEnabled(false);
            holder.add.setText("");
            holder.details.setEnabled(false);
            holder.details.setText("");
            return;
        }

        Glide.with(context).load(list.get(position).getProductImage()).into(holder.imageView);
        holder.productName.setText(list.get(position).getProductName());
        holder.price.setText(list.get(position).getSellingPrice());

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                String key = databaseReference.push().getKey();
                databaseReference.child("Carts").child(SplashScreen.trimmedMail).child(key)
                        .setValue(new CartModel(key,list.get(position).getProductImage(),list.get(position).getProductName(),list.get(position).getSellingPrice(),"1",list.get(position).getSellingPrice()));
                Toast.makeText(context, "Added to cart !", Toast.LENGTH_SHORT).show();


            }
        });

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ProductDetails.class);
                intent.putExtra("productName",list.get(position).getProductName());
                intent.putExtra("companyName",list.get(position).getCompanyName());
                intent.putExtra("sellingPrice",list.get(position).getSellingPrice());
                intent.putExtra("MRP",list.get(position).getMRP());
                intent.putExtra("description",list.get(position).getDescription());
                intent.putExtra("pictureLink",list.get(position).getProductImage());
                intent.putExtra("title",list.get(position).getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
       private ImageView imageView;
       private TextView productName,price,details,add;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.categoryProductImage);
            productName=itemView.findViewById(R.id.categoryProductName);
            price=itemView.findViewById(R.id.categoryProductPrice);
            details=itemView.findViewById(R.id.categoryProductDetails);
            add=itemView.findViewById(R.id.categoryProductAdd);
        }
    }

}
