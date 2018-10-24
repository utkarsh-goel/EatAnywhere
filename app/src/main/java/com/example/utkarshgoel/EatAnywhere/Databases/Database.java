package com.example.utkarshgoel.EatAnywhere.Databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.utkarshgoel.EatAnywhere.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "EatAnywhereDB.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String sqlSelect[]={"ProductName","ProductID","Quantity","Price","Discount"};
        String sqlTable = "OrderDetails";

        qb.setTables(sqlTable);

        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do
                {
                    result.add(new Order(cursor.getString(cursor.getColumnIndex("ProductID")),
                            cursor.getString(cursor.getColumnIndex("ProductName")),
                            cursor.getString(cursor.getColumnIndex("Quantity")),
                            cursor.getString(cursor.getColumnIndex("Price")),
                            cursor.getString(cursor.getColumnIndex("Discount"))));
                }while (cursor.moveToNext());
        }
        return  result;
    }

    public void addToCart(Order order)
    {
        SQLiteDatabase db = getReadableDatabase();

        String query = String.format("INSERT INTO OrderDetails(ProductID,ProductName,Quantity,Price,Discount) VALUES " +
                "('%s','%s','%s','%s','%s')",
                order.getProductID(),order.getProductName(),order.getQuantity(),order.getPrice(),order.getDiscount());

        db.execSQL(query);
    }

    public void cleanCart()
    {
        SQLiteDatabase db = getReadableDatabase();

        String query = String.format("DELETE from OrderDetails");

        db.execSQL(query);
    }
}
