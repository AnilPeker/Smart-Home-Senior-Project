package com.smarthome.SmartHome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.smarthome.SmartHome.R.layout.menu;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("anil") && password.getText().toString().equals("peker")){
                    setContentView(menu);
                }

            }
        });
    }


}
