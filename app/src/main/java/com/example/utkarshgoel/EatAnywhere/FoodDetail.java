package com.example.utkarshgoel.EatAnywhere;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.utkarshgoel.EatAnywhere.Databases.Database;
import com.example.utkarshgoel.EatAnywhere.Model.Food;
import com.example.utkarshgoel.EatAnywhere.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,food_desc;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodID;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference food;
    Food currFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        firebaseDatabase = FirebaseDatabase.getInstance();
        food = firebaseDatabase.getReference("Food");

        food_name=(TextView)findViewById(R.id.txtFoodName);
        food_image = (ImageView)findViewById(R.id.img_food);
        food_price = (TextView)findViewById(R.id.txtFoodPrice);
        food_desc = (TextView)findViewById(R.id.food_desc);

        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(foodID,currFood.getName(),
                        numberButton.getNumber(),currFood.getPrice(),currFood.getDiscount()));
                Toast.makeText(FoodDetail.this,"Added to Cart Successfully",Toast.LENGTH_SHORT).show();
            }
        });

        numberButton = (ElegantNumberButton)findViewById(R.id.btnCount);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollpasedAppbar);

        if(getIntent()!=null)
        {
            foodID = getIntent().getStringExtra("FoodID");
        }
        if(foodID.isEmpty()==false)
            loadFoodDetails(foodID);
    }

    private void loadFoodDetails(String foodID) {
        food.child(foodID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currFood = dataSnapshot.getValue(Food.class);
                Picasso.get().load(currFood.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(currFood.getName());
                food_price.setText(currFood.getPrice());
                food_name.setText(currFood.getName());
                food_desc.setText(currFood.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
