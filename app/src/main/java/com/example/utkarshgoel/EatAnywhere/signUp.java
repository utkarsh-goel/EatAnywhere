package com.example.utkarshgoel.EatAnywhere;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.utkarshgoel.EatAnywhere.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class signUp extends AppCompatActivity {

    MaterialEditText phone,pswrd,name;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        phone = (MaterialEditText)findViewById(R.id.phn);
        pswrd = (MaterialEditText)findViewById(R.id.pswrd);
        name = (MaterialEditText)findViewById(R.id.name);

        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(signUp.this);
                progressDialog.setMessage("Signing Up");
                progressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        progressDialog.dismiss();
                        if (dataSnapshot.child(phone.getText().toString()).exists())
                        {
                            Toast.makeText(signUp.this, "Phone Number already registered", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            User user = new User(name.getText().toString(),pswrd.getText().toString());
                            table_user.child(phone.getText().toString()).setValue(user);
                            Toast.makeText(signUp.this, "Sign  Up Successfull", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
