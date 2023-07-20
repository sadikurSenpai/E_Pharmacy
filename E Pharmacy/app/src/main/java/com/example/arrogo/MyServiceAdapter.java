package com.example.arrogo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyServiceAdapter extends RecyclerView.Adapter<MyServiceAdapter.ViewHolder> {

   private List<MyServiceModel> list;
   private Context context;

    public MyServiceAdapter(List<MyServiceModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_posts_model,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // to do -> the bug I have been facing after posting a post !!!

        if(list.get(position).getMail().equals("") ){
            holder.delete.setEnabled(false);
            holder.delete.setAlpha(0f);
            holder.name.setText("No Post Yet !");
            holder.price.setText("");
            return;
        }
        holder.name.setText("Name : "+list.get(position).getProductName());
        holder.price.setText("Price : "+list.get(position).getProductPrice());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                databaseReference.child("My Posts").child(list.get(position).getMail()).child(list.get(position).getRandomKey()).removeValue();
                String s=list.get(position).getMail()+""+list.get(position).getProductName();
                databaseReference.child("Products").child(list.get(position).getProductTitle()).child(s).removeValue();
                Toast.makeText(context, "Deleted your post !", Toast.LENGTH_SHORT).show();
//                context.startActivity(new Intent(context,SplashScreen.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,price;
        private ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.myPostModelName);
            price=itemView.findViewById(R.id.myPostModelPrice);
            delete=itemView.findViewById(R.id.myPostModelDelete);
        }
    }
}
