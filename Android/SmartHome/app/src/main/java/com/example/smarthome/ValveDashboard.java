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

public class ValveDashboard extends AppCompatActivity {
    private SessionHandler session;
    private User user;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_FLOOD = "flood";
    private static final String KEY_FIRE = "fire";
    private static final String KEY_GAS = "gas";
    private static final String KEY_WATERVALVE = "watervalve";
    private static final String KEY_GASVALVE = "gasvalve";
    private int floodstatus;
    private int firestatus;
    private int gasstatus;
    private int watervalvestatus;
    private int gasvalvestatus;
    TextView floodtw;
    TextView firetw;
    TextView gastw;
    TextView watervalvetw;
    TextView gasvalvetw;
    Button waterbut;
    Button gasbut;
    private String url1 = "http://192.168.137.1/alertGet.php";
    private String url2 = "http://192.168.137.1/valveGet.php";
    private String url3 = "http://192.168.137.1/waterValvePost.php";
    private String url4 = "http://192.168.137.1/gasValvePost.php";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valvedashboard);
        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();


        floodtw = findViewById(R.id.floodstatus);
        firetw = findViewById(R.id.firestatus);
        gastw =  findViewById(R.id.gasleakstatus);
        watervalvetw = findViewById(R.id.watervalvestatus);
        gasvalvetw = findViewById(R.id.gasvalvestatus);


        getAllStatus();
        getValvesStatus();

        waterbut = findViewById(R.id.watervalvebutton);
        gasbut = findViewById(R.id.gasvalvebutton);
        waterbut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postWaterValve();
            }

        });
        gasbut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postGasValve();
            }

        });





    }
    public void getValvesStatus() {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url2, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            watervalvestatus = response.getInt(KEY_WATERVALVE);
                            gasvalvestatus = response.getInt(KEY_GASVALVE);

                            if(watervalvestatus == 1){
                                watervalvetw.setText("ON");
                            }
                            else if(watervalvestatus == 0){
                                watervalvetw.setText("OFF");
                            }


                            if(watervalvestatus == 1){
                                gasvalvetw.setText("ON");
                            }
                            else if(gasvalvestatus == 0){
                                gasvalvetw.setText("OFF");
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

    public void getAllStatus(){


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

                            floodstatus = response.getInt(KEY_FLOOD);
                            firestatus = response.getInt(KEY_FIRE);
                            gasstatus = response.getInt(KEY_GAS);

                            if(floodstatus == 1)
                                floodtw.setText("ALERT");
                            else
                                floodtw.setText("SECURE");
                            if(firestatus == 1)
                                firetw.setText("ALERT");
                            else
                                firetw.setText("SECURE");
                            if(gasstatus == 1)
                                gastw.setText("ALERT");
                            else
                                gastw.setText("SECURE");
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
    public void postWaterValve(){

        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
            if(watervalvetw.getText().equals("ON")){
                request.put(KEY_WATERVALVE,0);
            }
            else{
                request.put(KEY_WATERVALVE,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest4 = new JsonObjectRequest
                (Request.Method.POST, url3, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            watervalvestatus = response.getInt(KEY_WATERVALVE);
                            if(watervalvestatus == 1 && watervalvetw.getText().equals("OFF")){
                                watervalvetw.setText("ON");
                            }
                            else if(watervalvestatus == 1 && watervalvetw.getText().equals("ON")){
                                watervalvetw.setText("OFF");
                            }
                            else{
                                watervalvetw.setText("Error");
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


    public void postGasValve(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
            if(gasvalvetw.getText().equals("ON")){
                request.put(KEY_GASVALVE,0);
            }
            else{
                request.put(KEY_GASVALVE,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest4 = new JsonObjectRequest
                (Request.Method.POST, url4, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            gasvalvestatus = response.getInt(KEY_GASVALVE);
                            if(gasvalvestatus == 1 && gasvalvetw.getText().equals("OFF")){
                                gasvalvetw.setText("ON");
                            }
                            else if(gasvalvestatus == 1 && gasvalvetw.getText().equals("ON")){
                                gasvalvetw.setText("OFF");
                            }
                            else{
                                gasvalvetw.setText("Error");
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
