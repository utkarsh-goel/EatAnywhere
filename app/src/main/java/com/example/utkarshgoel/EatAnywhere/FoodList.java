package com.example.utkarshgoel.EatAnywhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.utkarshgoel.EatAnywhere.Interface.ItemClickListener;
import com.example.utkarshgoel.EatAnywhere.Model.Food;
import com.example.utkarshgoel.EatAnywhere.ViewHolder.FoodViewHolder;
import com.example.utkarshgoel.EatAnywhere.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference food_databaseReference;

    String CategoryID;

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        firebaseDatabase = FirebaseDatabase.getInstance();
        food_databaseReference = firebaseDatabase.getReference("Food");

        recyclerView = (RecyclerView)findViewById(R.id.recyler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent()!=null)
        {
            CategoryID = getIntent().getStringExtra("CategoryID");
        }

        if(CategoryID.isEmpty()==false && CategoryID!=null)
        {
            loadFood(CategoryID);
        }
        else
        {
            Toast.makeText(FoodList.this,"Error parsing CategoryID",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFood(final String categoryID)
    {
        FirebaseRecyclerOptions<Food> firebaseRecyclerOptions= new FirebaseRecyclerOptions.Builder<Food>().setQuery(food_databaseReference,Food.class).build();

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder foodViewHolder, int i, @NonNull Food food) {
                foodViewHolder.txtFoodName.setText(food.getName());
                Picasso.get().load(food.getImage()).into(foodViewHolder.food_image);

                final Food clickItem = food;
                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(FoodList.this,""+clickItem.getName(),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.food_item, viewGroup, false);

                food_databaseReference.orderByChild("MenuID").equalTo(CategoryID);

                return new FoodViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
