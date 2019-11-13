package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {
    private SessionHandler session;
    private User user;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_TEMPERATURE = "temperature";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_ALARM = "alarm_status";
    private String url1 = "http://192.168.137.1/tempGet.php";
    private String url2 = "http://192.168.137.1/alarmGet.php";
    private String url3 = "http://192.168.137.1/alarmPost.php";
    private String temperature;
    private String humidity;
    private int alarm;
    TextView temperaturetw;
    TextView humiditytw;
    TextView alarmtw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome "+user.getFullName()+", your session will expire on "+user.getSessionExpiryDate());

        temperaturetw = findViewById(R.id.TemperatureMainDashboard);
        humiditytw = findViewById(R.id.HumidityMainDashboard);
        alarmtw = findViewById(R.id.AlarmMainDashboard);

        getTemp();
        getAlarm();

        Button logoutBtn = findViewById(R.id.btnLogout);
        CardView lightDashboard =findViewById(R.id.lightDashboard);
        CardView doorDashboard =findViewById(R.id.doorDashboard);
        CardView fanDashboard = findViewById(R.id.fanDashboard);
        CardView alarmControl = findViewById(R.id.alarmControl);
        CardView valveDashboard = findViewById(R.id.valveDashboard);
        CardView pumpDashboard = findViewById(R.id.waterPumpDashboard);


        lightDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, LightDashboard.class);
                startActivity(i);

            }
        });
        doorDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, DoorDashboard.class);
                startActivity(i);

            }
        });
        fanDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, FanDashboard.class);
                startActivity(i);

            }
        });
        valveDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, ValveDashboard.class);
                startActivity(i);

            }
        });
        pumpDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, PumpDashboard.class);
                startActivity(i);

            }
        });
        alarmControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAlarm();

            }
        });


       logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
    public void getTemp(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest1 = new JsonObjectRequest
                (Request.Method.POST, url1, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            temperature = response.getString(KEY_TEMPERATURE);
                            humidity = response.getString(KEY_HUMIDITY);

                            temperaturetw.setText(temperature);
                            humiditytw.setText(humidity);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest1);

    }
    public void getAlarm(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest2 = new JsonObjectRequest
                (Request.Method.POST, url2, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            alarm = response.getInt(KEY_ALARM);
                            if(alarm==1){
                                alarmtw.setText("ON");
                            }
                            if(alarm==0){
                                alarmtw.setText("OFF");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest2);

    }
    public void postAlarm(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
            if(alarmtw.getText().equals("ON")){
                request.put(KEY_ALARM,0);
            }
            else {
                request.put(KEY_ALARM, 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest4 = new JsonObjectRequest
                (Request.Method.POST, url3, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            alarm = response.getInt(KEY_ALARM);
                            if(alarm == 1 && alarmtw.getText().equals("ON")){
                                alarmtw.setText("OFF");
                            }
                            else if(alarm == 1 && alarmtw.getText().equals("OFF")){
                                alarmtw.setText("ON");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest4);

    }

}