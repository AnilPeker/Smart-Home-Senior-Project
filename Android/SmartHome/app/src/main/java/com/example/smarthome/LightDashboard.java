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

public class LightDashboard extends AppCompatActivity {
    private SessionHandler session;
    private User user;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_LIGHT1 = "light1";
    private static final String KEY_LIGHT2 = "light2";
    private static final String KEY_LIGHT3 = "light3";
    private int light1;
    private int light2;
    private int light3;
    TextView light1tw;
    TextView light2tw;
    TextView light3tw;
    private String url1 = "http://192.168.137.1/light1Get.php";
    private String url2 = "http://192.168.137.1/light2Get.php";
    private String url3 = "http://192.168.137.1/light3Get.php";
    private String url4 = "http://192.168.137.1/light1Post.php";
    private String url5 = "http://192.168.137.1/light2Post.php";
    private String url6 = "http://192.168.137.1/light3Post.php";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightdashboard);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();

        light1tw = findViewById(R.id.light1value);
        light2tw = findViewById(R.id.light2value);
        light3tw = findViewById(R.id.light3value);

        Button light1but = findViewById(R.id.light1button);
        Button light2but = findViewById(R.id.light2button);
        Button light3but = findViewById(R.id.light3button);

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

                            light1 = response.getInt(KEY_LIGHT1);
                            if(light1 == 1){
                                light1tw.setText("ON");
                            }
                            else{
                                light1tw.setText("OFF");
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
        JsonObjectRequest jsArrayRequest2 = new JsonObjectRequest
                (Request.Method.POST, url2, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            light2 = response.getInt(KEY_LIGHT2);
                            if(light2 == 1){
                                light2tw.setText("ON");
                            }
                            else{
                                light2tw.setText("OFF");
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
        JsonObjectRequest jsArrayRequest3 = new JsonObjectRequest
                (Request.Method.POST, url3, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            light3 = response.getInt(KEY_LIGHT3);
                            if(light3 == 1){
                                light3tw.setText("ON");
                            }
                            else{
                                light3tw.setText("OFF");
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
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest3);


        light1but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postLight1();
            }

        });
        light2but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postLight2();
            }

        });
        light3but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postLight3();
            }

        });



    }
    public void postLight1(){
        User user = session.getUserDetails();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
            if(light1tw.getText().equals("ON")){
                request.put(KEY_LIGHT1,0);
            }
            else{
                request.put(KEY_LIGHT1,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest4 = new JsonObjectRequest
                (Request.Method.POST, url4, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            light1 = response.getInt(KEY_LIGHT1);
                            if(light1 == 1 && light1tw.getText().equals("OFF")){
                                light1tw.setText("ON");
                            }
                            else if(light1 == 1 && light1tw.getText().equals("ON")){
                                light1tw.setText("OFF");
                            }
                            else{
                                light1tw.setText("Error");
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
    public void postLight2(){
        User user = session.getUserDetails();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
            if(light2tw.getText().equals("ON")){
                request.put(KEY_LIGHT2,0);
            }
            else{
                request.put(KEY_LIGHT2,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest4 = new JsonObjectRequest
                (Request.Method.POST, url5, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            light2 = response.getInt(KEY_LIGHT2);
                            if(light2 == 1 && light2tw.getText().equals("OFF")){
                                light2tw.setText("ON");
                            }
                            else if(light2 == 1 && light2tw.getText().equals("ON")){
                                light2tw.setText("OFF");
                            }
                            else{
                                light2tw.setText("Error");
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
    public void postLight3(){
        User user = session.getUserDetails();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
            if(light3tw.getText().equals("ON")){
                request.put(KEY_LIGHT3,0);
            }
            else{
                request.put(KEY_LIGHT3,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest4 = new JsonObjectRequest
                (Request.Method.POST, url6, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            light3 = response.getInt(KEY_LIGHT3);
                            if(light3 == 1 && light3tw.getText().equals("OFF")){
                                light3tw.setText("ON");
                            }
                            else if(light3 == 1 && light3tw.getText().equals("ON")){
                                light3tw.setText("OFF");
                            }
                            else{
                                light3tw.setText("Error");
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
