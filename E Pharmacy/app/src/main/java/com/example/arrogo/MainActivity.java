package com.example.arrogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private RelativeLayout relativeLayout,relativeLayout1,relativeLayout2,relativeLayout3,relativeLayout4,relativeLayout5,relativeLayout6,relativeLayout7;
    private int flag=0;
    private ImageView cart;
    private TextView name,callToOrder;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageSlider imageSlider;
        imageSlider =findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<>();

            // to do -> if you want to add any more images just copy the bottom line and edit

        imageList.add(new SlideModel(R.drawable.slider_img_one, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.slider_img_two,ScaleTypes.FIT));

        imageSlider.setImageList(imageList);

        callToOrder=findViewById(R.id.mainActivityCallToOrder);
        name=findViewById(R.id.mainActivityName);
        name.setText("Hi, "+SplashScreen.name);
        databaseReference=FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        cart=findViewById(R.id.mainActivityCart);
        relativeLayout=findViewById(R.id.mainActivityExploreCovid19Products);
        relativeLayout1=findViewById(R.id.mainActivityExploreBabyProducts);
        relativeLayout2=findViewById(R.id.mainActivityExploreBeautyProducts);
        relativeLayout3=findViewById(R.id.mainActivityExploreHerbalProducts);
        relativeLayout4=findViewById(R.id.mainActivityExploreSexualWellness);
        relativeLayout5=findViewById(R.id.mainActivityExplorePersonalHealthCare);
        relativeLayout6=findViewById(R.id.mainActivityExploreSupplementProducts);
        relativeLayout7=findViewById(R.id.mainActivityExploreSportsProducts);
        relativeLayout.setOnClickListener(this);
        relativeLayout1.setOnClickListener(this);
        relativeLayout2.setOnClickListener(this);
        relativeLayout3.setOnClickListener(this);
        relativeLayout4.setOnClickListener(this);
        relativeLayout5.setOnClickListener(this);
        relativeLayout6.setOnClickListener(this);
        relativeLayout7.setOnClickListener(this);
        callToOrder.setOnClickListener(this);
        cart.setOnClickListener(this);


        if(SplashScreen.Occupation.equals("seller")){
            flag=1;
        }
    }
    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder dialogExit = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);

        dialogExit.setTitle("EXIT!!");
        dialogExit.setMessage("Are you sure?");

        dialogExit.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
                finish();
            }
        });

        dialogExit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogExit.show();
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.myServicesOrBecomeASeller);
        if (flag==1) {
            item.setTitle("My posts");

        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutUser:

                mAuth.signOut();
                startActivity(new Intent(this,Login.class));
                finish();
                return true;

            case R.id.historyOption:
                startActivity(new Intent(this,History.class));
                return true;
            case R.id.myServicesOrBecomeASeller:
        if(flag==1){
            Intent x=new Intent(this,MyPosts.class);
            startActivity(x);
        }
        else{
            AlertDialog.Builder dialogExit = new AlertDialog.Builder(MainActivity.this);
            View layout_dialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.pay_now_dialogue_box_1, null);
            dialogExit.setView(layout_dialog);
            dialogExit.setTitle("Payment !!");
            dialogExit.setMessage("Please pay 500 tk");

            dialogExit.setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    databaseReference.child("Users").child(SplashScreen.trimmedMail).child("occupation").setValue("seller");
                    startActivity(new Intent(MainActivity.this,SplashScreen.class));
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mainActivityCallToOrder:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+"123456789"));
                startActivity(callIntent);
                break;
            case R.id.mainActivityCart:
                startActivity(new Intent(this,Cart.class));
                break;
            case R.id.mainActivityExploreCovid19Products:
                Intent intent=new Intent(this,ListOfProducts.class);
                intent.putExtra("title","Covid 19 Products");
                startActivity(intent);
                break;
            case R.id.mainActivityExploreBabyProducts:
                Intent intent1=new Intent(this,ListOfProducts.class);
                intent1.putExtra("title","Baby Products");
                startActivity(intent1);
                break;
            case R.id.mainActivityExploreBeautyProducts:
                Intent intent2=new Intent(this,ListOfProducts.class);
                intent2.putExtra("title","Beauty Products");
                startActivity(intent2);
                break;
            case R.id.mainActivityExploreHerbalProducts:
                Intent intent3=new Intent(this,ListOfProducts.class);
                intent3.putExtra("title","Herbal Products");
                startActivity(intent3);
                break;
            case R.id.mainActivityExploreSexualWellness:
                Intent intent4=new Intent(this,ListOfProducts.class);
                intent4.putExtra("title","Sexual Wellness");
                startActivity(intent4);
                break;
            case R.id.mainActivityExplorePersonalHealthCare:
                Intent intent5=new Intent(this,ListOfProducts.class);
                intent5.putExtra("title","Personal Health Care");
                startActivity(intent5);
                break;
            case R.id.mainActivityExploreSupplementProducts:
                Intent intent6=new Intent(this,ListOfProducts.class);
                intent6.putExtra("title","Supplement Products");
                    startActivity(intent6);
                break;
            case R.id.mainActivityExploreSportsProducts:
                Intent intent7=new Intent(this,ListOfProducts.class);
                intent7.putExtra("title","Sports Products");
                startActivity(intent7);
                break;

        }

    }
}