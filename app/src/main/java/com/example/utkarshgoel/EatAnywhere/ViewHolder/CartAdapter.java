package com.example.utkarshgoel.EatAnywhere.ViewHolder;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.utkarshgoel.EatAnywhere.Interface.ItemClickListener;
import com.example.utkarshgoel.EatAnywhere.Model.Order;
import com.example.utkarshgoel.EatAnywhere.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView cart_item_name,cart_item_price;
    ImageView cart_item_count;

    ItemClickListener itemClickListener;

    public void setCart_item_name(TextView cart_item_name) {
        this.cart_item_name = cart_item_name;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        cart_item_name = (TextView)itemView.findViewById(R.id.cart_item_name);
        cart_item_price = (TextView)itemView.findViewById(R.id.cart_item_price);
        cart_item_count = (ImageView)itemView.findViewById(R.id.cart_item_count);
    }

    @Override
    public void onClick(View v) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>
{

    private Context context;
    private List<Order> listData = new ArrayList<>();

    public CartAdapter(Context context, List<Order> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cart_layout,viewGroup,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {

        TextDrawable textDrawable = TextDrawable.builder().buildRound(""+listData.get(i).getQuantity(),Color.RED);
        cartViewHolder.cart_item_count.setImageDrawable(textDrawable);

        Locale locale = new Locale("en","US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        int price = (Integer.parseInt(listData.get(i).getPrice()))*(Integer.parseInt(listData.get(i).getQuantity()));
        cartViewHolder.cart_item_price.setText(numberFormat.format(price));
        cartViewHolder.cart_item_name.setText(listData.get(i).getProductName());


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
