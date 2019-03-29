package com.jts.root.attendance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;

import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.api.services.calendar.model.Events;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.jts.root.attendance.Job_Sheet.langitude;
import static com.jts.root.attendance.Job_Sheet.latitude;
import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;

import static com.jts.root.attendance.New_Data.statusid;
import static com.jts.root.attendance.New_Data.statuspos;
import static com.jts.root.attendance.userrole_menu.idS;

public class Customer_List extends AppCompatActivity {
    ListView customlst;
    Button otherB, newB,cancelB;
    String[] custom;
    RequestQueue sch_RequestQueue;
    String cname;
    private ArrayAdapter<String> listAdapter;
    static String postion;
    private ProgressDialog dialog_progress ;
    static List<String> customL = new ArrayList<String>();
    static List<String> forecastidL = new ArrayList<String>();
    static String forecastidS;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__list);

        otherB = (Button) findViewById(R.id.otheridB);
        newB = (Button) findViewById(R.id.newidB);
        cancelB = (Button) findViewById(R.id.cancelcus);
        customlst=(ListView)findViewById(R.id.customlist);

        otherB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(Customer_List.this, Implimentation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });
        newB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Customer_List.this, New_Data.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Customer_List.this, Job_Sheet.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        Log.d("latitude values333", "---" + latitude);

        custom_values(latitude,langitude);


    }


    public void custom_values(String lat,String lag){

        Log.d("latitude data11", "---" + lat);
        Log.d("langitude data122", "---" + lag);

        String locS = "{\"username\":\"admin\",\"password\":\"admin\",\"lat\":\"" + lat + "\",\"lag\":\"" + lag + "\"}";
        Log.d("jsnresponse roleS", "---" + locS);
        String url = "http://"+domain_name+":"+port+"/InventoryApp/get_customer_map1/";
        JSONObject lstrmdt = null;
        // try {

        try {
            lstrmdt = new JSONObject(locS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + locS);
       // dialog_progress.setMessage("connecting ...");
       // dialog_progress.show();
        JSONSenderVolley(url, lstrmdt);
    }
    public void custom_spinner(JSONObject responseJSON){
        try {
            customL.clear();
            JSONArray new_array1;
            try {
                new_array1 = responseJSON.getJSONArray("get_clients");
                //new_array1 = responseJSON.getJSONArray("fields");
                Log.d(" Array", " customer list : " + new_array1);
                for (int i = 0, count = new_array1.length(); i < count; i++) {
                    String customS = new_array1.getJSONObject(i).getString("cname");
                     forecastidS = new_array1.getJSONObject(i).getString("fid");

                    customL.add(customS);
                    forecastidL.add(forecastidS);
                    Log.d("customL-.....", "---" + customL);
                    Log.d("forecastidL-.....", "---" + forecastidL);

                    listAdapter = new ArrayAdapter<String>(this, R.layout.activity_customlist,R.id.customtxtid, customL);
                    customlst.setAdapter(listAdapter);
                    customlst.setOnItemClickListener(new ListClickHandler());

                }
            }
            catch (NullPointerException e){

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub

            postion = adapter.getAdapter().getItem(position).toString();
            Log.d("postion vallue....", "---" + postion);

            Intent intent = new Intent(Customer_List.this, Notes.class);
            startActivity(intent);
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
/*
                        Log.d(Bitmap.Config.TAG, response.toString());
*/
                        custom_spinner(response);
                        Log.d("----custom values-----", "---"+response.toString());

                        try {
                            int custom_code = response.getInt("error_code");
                            String er_discp=response.getString("error_desc");


                            String[] custom = er_discp.split("=");
                            if(custom_code==0){
                                Toast.makeText(getApplicationContext(), custom[1], Toast.LENGTH_LONG).show();
                            }else
                                popup();
                                Toast.makeText(getApplicationContext(), custom[1], Toast.LENGTH_LONG).show();

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
    public void popup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = ((Activity) Customer_List.this).getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.activity_pop_up__add,
                null);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setView(dialogLayout, 0, 0, 0, 0);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wlmp = dialog.getWindow()
                .getAttributes();
        wlmp.gravity = Gravity.CENTER;


       /* final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setTitle("please enter room names");
        dialog.setContentView(R.layout.update_popup_forecast);*/

        final Button btn = (Button) dialogLayout.findViewById(R.id.addbtn);

        builder.setView(dialogLayout);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //delgroup(grpnm);

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

