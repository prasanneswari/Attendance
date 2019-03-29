package com.jts.root.attendance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;
import static com.jts.root.attendance.Login.usernameS;
import static com.jts.root.attendance.userrole_menu.idS;
//public class Job_Sheet extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, LocationListener{

public class Job_Sheet extends AppCompatActivity{
    Button calenderB,arrivedbtn,cancelB;
    LocationManager locationManager;
    RequestQueue sch_RequestQueue;
    static String latitude,eid1;
    static String langitude;
    private ProgressDialog dialog_progress ;
    ListView eventslst;

    String[] cusname = {};
    String[] fullname = {};
    String[] cuscontact = {};
    String[] apntdate = {};
    String[] visitdate = {};
    static String[] notes = {};

    private static final String TAG = Job_Sheet.class.getSimpleName();

    // private static final int REQUEST_PERMISSIONS_LOCATION_SETTINGS_REQUEST_CODE = 33;


    // private FusedLocationProviderClient mFusedLocationClient;


    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;
    String lat_send="",lng_snd="";

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters



    protected static long MIN_UPDATE_INTERVAL = 30 * 1000; // 1  minute is the minimum Android recommends, but we use 30 seconds


    private TextView resultTextView;
    LocationRequest locationRequest;
    Location lastLocation = null;
    Location currentLocation = null;

    private FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job__sheet);
        //get_locbtn=(Button)findViewById(R.id.getLocationBtn);
        //get_cusbtn=(Button)findViewById(R.id.get_cusid);
        calenderB = (Button) findViewById(R.id.calender_id);
        arrivedbtn = (Button) findViewById(R.id.arrivedbtn);
        cancelB = (Button) findViewById(R.id.cancelid);
        eventslst = (ListView) findViewById(R.id.eventlist);
        dialog_progress = new ProgressDialog(Job_Sheet.this);

        requestPermission();

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Job_Sheet.this, userrole_menu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        arrivedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Job_Sheet.this, Customer_List.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        calenderB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                long startMillis = System.currentTimeMillis();
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, startMillis);
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
                startActivity(intent);
            }
        });
        getapnt_post();

        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(Job_Sheet.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            return;

        }

        client.getLastLocation().addOnSuccessListener(Job_Sheet.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location!= null){
                   // TextView textView = findViewById(R.id.location);
                    //textView.setText(location.toString());
                    double latitude1 = location.getLatitude();
                    double longitude1 = location.getLongitude();
                    System.out.println("lat is : "+latitude1);
                    System.out.println("lan is : "+longitude1);
                    latitude=String.valueOf(latitude1);
                    langitude=String.valueOf(longitude1);
                   // custom_values(latitude,langitude);

                    Toast.makeText(
                            getApplicationContext(),
                            "Latitude: " + latitude
                                    + "\nLongitude: " + langitude,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

        /*if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        //siteUrl_R = "http://192.168.4.1/getLocation";
        //add_espdtdialog(siteUrl_R);

        displayLocation();
       // checkLocation();

    }

    @SuppressLint("SetTextI18n")
    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude1 = mLastLocation.getLatitude();
            double longitude1 = mLastLocation.getLongitude();
            double time = mLastLocation.getTime();
            Date date = new Date((long) time);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String text = sdf.format(date);
            System.out.println("time is : "+text);
            System.out.println("lat is : "+latitude1);
            System.out.println("lan is : "+longitude1);

            latitude=String.valueOf(latitude1);
            langitude=String.valueOf(longitude1);
            custom_values(latitude,langitude);

            // lblLocation.setText(latitude + ", " + longitude+","+text);
        }
    }
    *//**
     * Creating google api client object
     *//*
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    *//**
     * Method to verify google play services on the device
     *//*
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }

    *//**
     * Google api callback methods
     *//*
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
    }
    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }*/

    public void getapnt_post(){

        String getapntS = "{\"username\":\"admin\",\"password\":\"admin\",\"userid\":\""+idS+"\"}";
        //String pur_prds = "{\"orderid\":\"" + lID + "\"}";
        Log.d("jsnresponse customerS", "---" + getapntS);
        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        //String room_ssid_url = "http://cld003.jts-prod.in:5906/AssetTrackerApp/get_rooms_ssid/";
        String url = "http://"+domain_name+":"+port+"/InventoryApp/get_myappointments/";
        // String urlrs= "https://jtsha.in/service/validate_web";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(getapntS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + getapntS);
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        JSONSenderVolley(url, lstrmdt);
    }
    public  void getapnt_request(JSONObject responseJSON){

        try {
            //Log.d( " Array", " response ->  " + response);
            JSONArray new_array1;
            List<String> cusnameL = new ArrayList<String>();
            List<String> fullnameL = new ArrayList<String>();
            List<String> cuscontactL = new ArrayList<String>();
            List<String> apntdateL = new ArrayList<String>();
            List<String> visitdateL = new ArrayList<String>();
            List<String> noteL = new ArrayList<String>();

            try {
                new_array1 = responseJSON.getJSONArray("get_myappointments");
                Log.d(" Array", " : " + new_array1);
                for (int i = 0, count = new_array1.length(); i < count; i++) {
                    String cusnameS = new_array1.getJSONObject(i).getString("customername");
                    String fullnameS = new_array1.getJSONObject(i).getString("fullname");
                    String cuscontactS = new_array1.getJSONObject(i).getString("customercontact");
                    String apntdateS = new_array1.getJSONObject(i).getString("appointmentdate");
                    String visitdataS = new_array1.getJSONObject(i).getString("visiteddate");
                    String noteS = new_array1.getJSONObject(i).getString("notes");

                    cusnameL.add(cusnameS);
                    fullnameL.add(fullnameS);
                    cuscontactL.add(cuscontactS);
                    apntdateL.add(apntdateS);
                    visitdateL.add(visitdataS);
                    noteL.add(noteS);

                }
                cusname = new String[cusnameL.size()];
                fullname = new String[fullnameL.size()];
                cuscontact = new String[cuscontactL.size()];
                apntdate = new String[apntdateL.size()];
                visitdate = new String[visitdateL.size()];
                notes = new String[noteL.size()];


                for (int l = 0; l < cusnameL.size(); l++) {
                    cusname[l] = cusnameL.get(l);
                    fullname[l] = fullnameL.get(l);
                    cuscontact[l] = cuscontactL.get(l);
                    apntdate[l] = apntdateL.get(l);
                    visitdate[l] = visitdateL.get(l);
                    notes[l] = noteL.get(l);

                    Log.d("cusname ", cusname[l]);
                    Log.d("fullname ", fullname[l]);
                    Log.d("cuscontact ", cuscontact[l]);
                    Log.d("apntdate ", apntdate[l]);
                    Log.d("visitdate ", visitdate[l]);
                    Log.d("notes ", notes[l]);

                }
            }
            catch (NullPointerException e){

            }

            Adapter_jobsheet reqAdapter = new Adapter_jobsheet(Job_Sheet.this,cusname,fullname, cuscontact, apntdate,visitdate);
            eventslst.setAdapter(reqAdapter);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void JSONSenderVolley(String url, final JSONObject json)
    {
        Log.d("custom_url-", "---"+url);
        Log.d("555555", "00000000"+json.toString());
        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("----admin values-----", "---"+response.toString());
                        try {
                        getapnt_request(response);
                        if (dialog_progress.isShowing()) {
                            dialog_progress.dismiss();

                        }
                            int login_code = response.getInt("error_code");
                            String er_discp=response.getString("error_desc");

                            String[] separated = er_discp.split("=");
                            if(login_code==0){
                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
                            }else
                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(" ", "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
            }
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adding request to request queue
        // jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }


}