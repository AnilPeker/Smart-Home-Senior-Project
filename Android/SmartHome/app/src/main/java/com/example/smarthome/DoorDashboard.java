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

public class DoorDashboard extends AppCompatActivity {
    private SessionHandler session;
    private User user;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_DOORLOCK = "doorlock";
    private static final String KEY_DOORSTATUS = "doorstatus";
    private static final String KEY_WINDOWSTATUS = "windowstatus";
    private static final String KEY_GARAGESTATUS = "garagestatus";
    private int doorlock;
    private int doorstatus;
    private int windowstatus;
    private int garagestatus;
    TextView doorlocktw;
    TextView doorstatustw;
    TextView windowstatustw;
    TextView garagestatustw;
    private String url1 = "http://192.168.137.1/doorLockGet.php";
    private String url2 = "http://192.168.137.1/doorLockPost.php";
    private String url3 = "http://192.168.137.1/doorStatusGet.php";
    private String url4 = "http://192.168.137.1/doorStatusPost.php";
    private String url5 = "http://192.168.137.1/garageGet.php";
    private String url6 = "http://192.168.137.1/garagePost.php";
    private String url7 = "http://192.168.137.1/windowStatusPost.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doordashboard);
        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        doorlocktw = findViewById(R.id.mainDoorLockValue);
        doorstatustw = findViewById(R.id.mainDoorValue);
        windowstatustw = findViewById(R.id.windowValue );
        garagestatustw = findViewById(R.id.garageDoorValue);


        Button doorlockbut = findViewById(R.id.mainDoorLockButton);
        Button doorstatusbut = findViewById(R.id.mainDoorButton);
        Button windowstatusbut = findViewById(R.id.windowButton );
        Button garagestatusbut = findViewById(R.id.garageDoorButton);

        getDoorLock();
        getDoorStatus();
        getGarageStatus();

        doorlockbut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postDoorLock();
            }

        });
        doorstatusbut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postDoorStatus();
            }

        });
        windowstatusbut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postWindowStatus();
            }

        });
        garagestatusbut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postGarageStatus();
            }

        });


    }
    public void postWindowStatus(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
            if(windowstatustw.getText().equals("OPEN")){
                request.put(KEY_WINDOWSTATUS,0);
            }
            else {
                request.put(KEY_WINDOWSTATUS, 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest4 = new JsonObjectRequest
                (Request.Method.POST, url7, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            windowstatus = response.getInt(KEY_WINDOWSTATUS);
                            if(windowstatus == 1 && windowstatustw.getText().equals("OPEN")){
                                windowstatustw.setText("CLOSE");
                            }
                            else if(windowstatus == 1 && windowstatustw.getText().equals("CLOSE")){
                                windowstatustw.setText("OPEN");
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

    public void postDoorLock(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
            if(doorstatustw.getText().equals("CLOSE")){
                request.put(KEY_DOORSTATUS,0);
            }
            else{
                request.put(KEY_DOORSTATUS,1);
            }
            if(doorlocktw.getText().equals("LOCK")){
                request.put(KEY_DOORLOCK,0);
            }
            else{
                request.put(KEY_DOORLOCK,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest4 = new JsonObjectRequest
                (Request.Method.POST, url2, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            doorlock = response.getInt(KEY_DOORLOCK);
                            if(doorlock == 1 && doorlocktw.getText().equals("UNLOCK")){
                                doorlocktw.setText("LOCK");
                            }
                            else if(doorlock == 1 && doorlocktw.getText().equals("LOCK")){
                                doorlocktw.setText("UNLOCK");
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
    public void getDoorLock(){
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

                            doorlock = response.getInt(KEY_DOORLOCK);
                            if(doorlock == 1){
                                doorlocktw.setText("LOCK");
                            }
                            else{
                                doorlocktw.setText("UNLOCK");
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
    public void postDoorStatus(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
            if(doorstatustw.getText().equals("OPEN")){
                request.put(KEY_DOORSTATUS,0);
            }
            else{
                request.put(KEY_DOORSTATUS,1);
            }
            if(doorlocktw.getText().equals("UNLOCK")){
                request.put(KEY_DOORLOCK,0);
            }
            else{
                request.put(KEY_DOORLOCK,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest4 = new JsonObjectRequest
                (Request.Method.POST, url4, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            doorstatus = response.getInt(KEY_DOORSTATUS);
                            if(doorstatus == 1 && doorstatustw.getText().equals("OPEN")){
                                doorstatustw.setText("CLOSE");
                            }
                            else if(doorstatus == 1 && doorstatustw.getText().equals("CLOSE")){
                                doorstatustw.setText("OPEN");
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
    public void getDoorStatus(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest1 = new JsonObjectRequest
                (Request.Method.POST, url3, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            doorstatus = response.getInt(KEY_DOORSTATUS);
                            windowstatus = response.getInt(KEY_WINDOWSTATUS);
                            if(doorstatus == 1){
                                doorstatustw.setText("OPEN");
                            }
                            else{
                                doorstatustw.setText("CLOSE");
                            }
                            if(windowstatus == 1){
                                windowstatustw.setText("OPEN");
                            }
                            else{
                                windowstatustw.setText("CLOSE");
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
    public void postGarageStatus(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
            if(garagestatustw.getText().equals("OPEN")){
                request.put(KEY_GARAGESTATUS,0);
            }
            else {
                request.put(KEY_GARAGESTATUS, 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest4 = new JsonObjectRequest
                (Request.Method.POST, url6, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            garagestatus = response.getInt(KEY_GARAGESTATUS);
                            if(garagestatus == 1 && garagestatustw.getText().equals("OPEN")){
                                garagestatustw.setText("CLOSE");
                            }
                            else if(garagestatus == 1 && garagestatustw.getText().equals("CLOSE")){
                                garagestatustw.setText("OPEN");
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
    public void getGarageStatus(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, user.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest1 = new JsonObjectRequest
                (Request.Method.POST, url5, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            garagestatus = response.getInt(KEY_GARAGESTATUS);
                            if(garagestatus == 1){
                                garagestatustw.setText("OPEN");
                            }
                            else{
                                garagestatustw.setText("CLOSE");
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
