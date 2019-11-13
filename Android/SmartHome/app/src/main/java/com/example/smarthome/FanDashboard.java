package com.example.smarthome;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class FanDashboard extends AppCompatActivity {
    private SessionHandler session;
    private User user;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_TEMPERATURE = "temperature";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_FANSTATUS = "fanstatus";
    private String temperature;
    private String humidity;
    private int fanstatus;
    TextView temperaturetw;
    TextView humiditytw;
    TextView fanstatustw;
    ImageView tempDay;
    ImageView tempMonth;
    ImageView humDay;
    ImageView humMonth;
    private String url1 = "http://192.168.137.1/tempGet.php";
    private String url2 = "http://192.168.137.1/fanGet.php";
    private String url3 = "http://192.168.137.1/fanPost.php";
    private String url4 = "http://192.168.137.1/AvgDayMonthTempVstime.png";
    private String url5 = "http://192.168.137.1/AvgtempMonthVstime.png";
    private String url6 = "http://192.168.137.1/AvgHumidityDayMonthVstime.png";
    private String url7 = "http://192.168.137.1/AvgHumidityMonthVstime.png";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fandashboard);
        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();


        tempDay = findViewById(R.id.averageTempByDay);
        tempMonth = findViewById(R.id.averageTempByMonth);
        humDay = findViewById(R.id.averageHumByDay);
        humMonth = findViewById(R.id.averageHumByMonth);


        temperaturetw = findViewById(R.id.TemperatureFanDashboard);
        humiditytw = findViewById(R.id.HumidityFanDashboard);
        fanstatustw =  findViewById(R.id.fanValue);



        Button fanButton = findViewById(R.id.fanButton);

        getTemperature();
        getFan();
        getChart();

        fanButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postFan();
            }

        });


    }
    public void getChart(){
        ImageRequest imageRequest = new ImageRequest(url4,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        tempDay.setImageBitmap(response);

                    }
                }


                , 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(FanDashboard.this, "Chart is retrieved", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(imageRequest);

        ImageRequest imageRequest1 = new ImageRequest(url5,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        tempMonth.setImageBitmap(response);
                    }
                }


                , 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FanDashboard.this, "Chart is retrieved", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(imageRequest1);
        ImageRequest imageRequest2 = new ImageRequest(url6,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        humDay.setImageBitmap(response);
                    }
                }


                , 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FanDashboard.this, "Chart is retrieved", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(imageRequest2);

        ImageRequest imageRequest3 = new ImageRequest(url7,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        humMonth.setImageBitmap(response);
                    }
                }


                , 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FanDashboard.this, "Chart is retrieved", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(imageRequest3);



    }
    public void getTemperature(){
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
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);

    }

    public void postFan(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
            if(fanstatustw.getText().equals("OPEN")){
                request.put(KEY_FANSTATUS,0);
            }
            else {
                request.put(KEY_FANSTATUS, 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest4 = new JsonObjectRequest
                (Request.Method.POST, url3, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            fanstatus = response.getInt(KEY_FANSTATUS);
                            if(fanstatus == 1 && fanstatustw.getText().equals("OPEN")){
                                fanstatustw.setText("CLOSE");
                            }
                            else if(fanstatus == 1 && fanstatustw.getText().equals("CLOSE")){
                                fanstatustw.setText("OPEN");
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
    public void getFan(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest1 = new JsonObjectRequest
                (Request.Method.POST, url2, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            fanstatus = response.getInt(KEY_FANSTATUS);
                            if(fanstatus == 1){
                                fanstatustw.setText("OPEN");
                            }
                            else{
                                fanstatustw.setText("CLOSE");
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
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest1);


    }
}
