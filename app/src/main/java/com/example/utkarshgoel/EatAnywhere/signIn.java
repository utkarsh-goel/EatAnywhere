package com.example.utkarshgoel.EatAnywhere;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.utkarshgoel.EatAnywhere.Common.Common;
import com.example.utkarshgoel.EatAnywhere.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class signIn extends AppCompatActivity {

    MaterialEditText phone,pswrd;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        phone = (MaterialEditText)findViewById(R.id.phn);
        pswrd = (MaterialEditText)findViewById(R.id.pswrd);

        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final ProgressDialog progressDialog = new ProgressDialog(signIn.this);
                progressDialog.setMessage("Verifying credentials");
                progressDialog.show();

                ValueEventListener valueEventListener = table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        progressDialog.dismiss();
                        if (dataSnapshot.child(phone.getText().toString()).exists()) {
                            User user = dataSnapshot.child(phone.getText().toString()).getValue(User.class);
                            user.setPhone(phone.getText().toString());
                            if (user.getPassword().equals(pswrd.getText().toString()))
                            {
                                Toast.makeText(signIn.this, "SignIn Successfull", Toast.LENGTH_SHORT).show();
                                Intent homeintent = new Intent(signIn.this,Home.class);
                                Common.currentUser = user;
                                startActivity(homeintent);
                                finish();
                            }
                            else {
                                Toast.makeText(signIn.this, "SignIn Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(signIn.this, "No user with given credentials found.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
