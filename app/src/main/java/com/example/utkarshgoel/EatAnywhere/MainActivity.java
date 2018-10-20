package com.example.utkarshgoel.EatAnywhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnsignIn,btnsignUp;
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnsignIn = (Button)findViewById(R.id.btnSignIn);
        btnsignUp = (Button)findViewById(R.id.btnSignUp);
        txtSlogan = (TextView)findViewById(R.id.slogan);

        btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsignIn = new Intent(MainActivity.this,com.example.utkarshgoel.EatAnywhere.signIn.class);
                startActivity(intentsignIn);
            }
        });

        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsignIn = new Intent(MainActivity.this,com.example.utkarshgoel.EatAnywhere.signUp.class);
                startActivity(intentsignIn);
            }
        });

    }
}
