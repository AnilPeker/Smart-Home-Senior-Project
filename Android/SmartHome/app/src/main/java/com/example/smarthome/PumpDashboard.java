package com.example.smarthome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class PumpDashboard extends AppCompatActivity {
    private SessionHandler session;
    private User user;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_SOILHUMIDITY = "soilHumidity";
    private static final String KEY_WATERPUMP = "waterPump";

    private String soilHumidityStatus;
    private int waterPumpStatus;
    TextView soilHumiditytw;
    TextView waterPumptw;
    Button waterPumpBut;
    private String url1 = "http://192.168.137.1/gardenGet.php";
    private String url2 = "http://192.168.137.1/waterPumpPost.php";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pumpdashboard);
        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();


        soilHumiditytw = findViewById(R.id.gardenSoilHumidityValue);
        waterPumptw = findViewById(R.id.waterpumptatus);
        getAllGarden();

        waterPumpBut = findViewById(R.id.waterpumpbutton);


        waterPumpBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postWaterPump();
            }

        });
    }
    public void getAllGarden() {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url1, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            soilHumidityStatus = response.getString(KEY_SOILHUMIDITY);
                            System.out.println(soilHumidityStatus);
                            waterPumpStatus = response.getInt(KEY_WATERPUMP);


                            soilHumiditytw.setText(soilHumidityStatus);

                            if(waterPumpStatus == 1){
                                waterPumptw.setText("ON");
                            }
                            else if(waterPumpStatus == 0){
                                waterPumptw.setText("OFF");
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
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);


    }
    public void postWaterPump(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
            if(waterPumptw.getText().equals("ON")){
                request.put(KEY_WATERPUMP ,0);
            }
            else{
                request.put(KEY_WATERPUMP,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest4 = new JsonObjectRequest
                (Request.Method.POST, url2, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            waterPumpStatus = response.getInt(KEY_WATERPUMP);
                            if(waterPumpStatus == 1 && waterPumptw.getText().equals("OFF")){
                                waterPumptw.setText("ON");
                            }
                            else if(waterPumpStatus == 1 && waterPumptw.getText().equals("ON")){
                                waterPumptw.setText("OFF");
                            }
                            else{
                                waterPumptw.setText("Error");
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
