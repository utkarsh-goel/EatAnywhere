package com.example.utkarshgoel.EatAnywhere;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utkarshgoel.EatAnywhere.Common.Common;
import com.example.utkarshgoel.EatAnywhere.Databases.Database;
import com.example.utkarshgoel.EatAnywhere.Model.Order;
import com.example.utkarshgoel.EatAnywhere.Model.Request;
import com.example.utkarshgoel.EatAnywhere.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference request;

    TextView txtTotalPrice;
    FButton btnPlaceOrder;

    List<Order> cart= new ArrayList<>();
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseDatabase = FirebaseDatabase.getInstance();
        request = firebaseDatabase.getReference("Requests");

        recyclerView = (RecyclerView)findViewById(R.id.recyclerCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlaceOrder = (FButton)findViewById(R.id.btnPlaceOrder);

        loadFoodList();

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    inputAddress();
            }
        });
    }

    private void inputAddress()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Enter Address");
        alertDialog.setMessage("Enter Delivery Address");

        final EditText edtAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("Place Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request orderDetails = new Request(Common.currentUser.getPhone(),Common.currentUser.getName(),
                        edtAddress.getText().toString(),txtTotalPrice.getText().toString(),cart);

                request.child(String.valueOf(System.currentTimeMillis())).setValue(orderDetails);
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this,"Order Placed Successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadFoodList()
    {
        cart = new Database(this).getCarts();
        cartAdapter = new CartAdapter(this,cart);
        recyclerView.setAdapter(cartAdapter);

        int total=0;

        for(Order order:cart) {
            total = total + (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        }

        Locale locale = new Locale("en","US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(numberFormat.format(total));

    }
}
