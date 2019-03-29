package com.jts.root.attendance;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jts.root.attendance.Job_Sheet.langitude;
import static com.jts.root.attendance.Job_Sheet.latitude;
import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;
import static com.jts.root.attendance.userrole_menu.idS;

public class Implimentation extends AppCompatActivity {

    ListView tasklst,cuslst;
    static List<String> customersL = new ArrayList<String>();
    static List<String> cusforecastidL = new ArrayList<String>();
    static String indexval,pos;
    private ArrayAdapter<String> CustomersAdapter;
    RequestQueue sch_RequestQueue;
    Button cancelB;
    static String posfid;
    String [] tAssignId,tappointmentType, tappointmentdate,tappointmenttypeId,tcompleteby,tcustomerName,forecastId,statusId,statusName;
    private ProgressDialog dialog_progress ;
    JSONArray new_array1;
    String[] cusforecastid,customer;
    static int forecastpos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implimentation);
        tasklst=(ListView)findViewById(R.id.tasklist);
        cuslst=(ListView)findViewById(R.id.customerlist);
        cancelB=(Button)findViewById(R.id.cancelid);
        dialog_progress = new ProgressDialog(Implimentation.this);

        Task_values();
        customers_values(latitude,langitude);

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Implimentation.this, userrole_menu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    public void Task_values(){
        String locS = "{\"username\":\"admin\",\"password\":\"admin\",\"userid\":\"" + idS + "\"}";
        Log.d("jsnresponse roleS", "---" + locS);
        String url = "http://"+domain_name+":"+port+"/InventoryApp/get_implementationtasks/";
        //String url = "http://cld003.jts-prod.in:20105/InventoryApp/get_implementationtasks/";

        JSONObject lstrmdt = null;
        // try {

        try {
            lstrmdt = new JSONObject(locS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + locS);
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        JSONSenderVolleytask(url, lstrmdt);
    }
    public void Task_listview(JSONObject responseJSON){
        try {
            List<String> tAssignIdL = new ArrayList<String>();
            List<String> tappointmentTypeL = new ArrayList<String>();
            List<String> tappointmentdateL = new ArrayList<String>();
            List<String> tappointmenttypeIdL = new ArrayList<String>();
            List<String> tcompletebyL = new ArrayList<String>();
            List<String> tcustomerNameL = new ArrayList<String>();
            List<String> forecastIdL = new ArrayList<String>();
            List<String> statusIdL = new ArrayList<String>();
            List<String> statusNameL = new ArrayList<String>();

            JSONArray new_array1;
            try {
                new_array1 = responseJSON.getJSONArray("get_implementationtasks");
                //new_array1 = responseJSON.getJSONArray("fields");
                Log.d(" Array", " get_implementationtasks : " + new_array1);
                for (int i = 0, count = new_array1.length(); i < count; i++) {
                    String tAssignIdS = new_array1.getJSONObject(i).getString("AssignId");
                    String tappointmentTypeS = new_array1.getJSONObject(i).getString("appointmentType");
                    String tappointmentdateS = new_array1.getJSONObject(i).getString("appointmentdate");
                    String tappointmenttypeIdS = new_array1.getJSONObject(i).getString("appointmenttypeId");
                    String tcompletebyS = new_array1.getJSONObject(i).getString("completeby");
                    String tcustomerNameS = new_array1.getJSONObject(i).getString("customerName");
                    String forecastIdS = new_array1.getJSONObject(i).getString("forecastId");
                    String statusIdS = new_array1.getJSONObject(i).getString("statusId");
                    String statusNameS = new_array1.getJSONObject(i).getString("statusName");

                    tAssignIdL.add(tAssignIdS);
                    tappointmentTypeL.add(tappointmentTypeS);
                    tappointmentdateL.add(tappointmentdateS);
                    tappointmenttypeIdL.add(tappointmenttypeIdS);
                    tcompletebyL.add(tcompletebyS);
                    tcustomerNameL.add(tcustomerNameS);
                    forecastIdL.add(forecastIdS);
                    statusIdL.add(statusIdS);
                    statusNameL.add(statusNameS);

                }
                tAssignId = new String[tAssignIdL.size()];
                tappointmentType = new String[tappointmentTypeL.size()];
                tappointmentdate = new String[tappointmentdateL.size()];
                tappointmenttypeId = new String[tappointmenttypeIdL.size()];
                tcompleteby = new String[tcompletebyL.size()];
                tcustomerName = new String[tcustomerNameL.size()];
                forecastId = new String[forecastIdL.size()];
                statusId = new String[statusIdL.size()];
                statusName = new String[statusNameL.size()];

                for (int l = 0; l < statusNameL.size(); l++) {
                    tAssignId[l] = tAssignIdL.get(l);
                    tappointmentType[l] = tappointmentTypeL.get(l);
                    tappointmentdate[l] = tappointmentdateL.get(l);
                    tappointmenttypeId[l] = tappointmenttypeIdL.get(l);
                    tcompleteby[l] = tcompletebyL.get(l);
                    tcustomerName[l] = tcustomerNameL.get(l);
                    forecastId[l] = forecastIdL.get(l);
                    statusId[l] = statusIdL.get(l);
                    statusName[l] = statusNameL.get(l);

                    Log.d("tAssignId ", tAssignId[l]);
                    Log.d("tappointmentType ", tappointmentType[l]);
                    Log.d("tappointmentdate ", tappointmentdate[l]);
                    Log.d("tappointmenttypeId ", tappointmenttypeId[l]);
                    Log.d("tcompleteby ", tcompleteby[l]);
                    Log.d("tcustomerName ", tcustomerName[l]);
                    Log.d("forecastId ", forecastId[l]);
                    Log.d("statusId ", statusId[l]);
                    Log.d("statusName ", statusName[l]);
                }
                Adapeter_Implement_task reqAdapter = new Adapeter_Implement_task(Implimentation.this,forecastId,tcustomerName,statusName, tappointmentdate, tappointmenttypeId,tappointmentType);
                tasklst.setAdapter(reqAdapter);
            }
            catch (NullPointerException e){

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void customers_values(String lat,String lag){

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
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        JSONSenderVolley(url, lstrmdt);
    }
    public void customers_listview(JSONObject responseJSON){
        try {
            customersL.clear();
            try {
                new_array1 = responseJSON.getJSONArray("get_clients");
                //new_array1 = responseJSON.getJSONArray("fields");
                Log.d(" Array", " customer list : " + new_array1);
                for (int i = 0, count = new_array1.length(); i < count; i++) {
                    String IcustomersS = new_array1.getJSONObject(i).getString("cname");
                    String IforecastidS = new_array1.getJSONObject(i).getString("fid");
                    Log.d("foreeecateeeeeeeeeee", "---" + IforecastidS);

                    customersL.add(IcustomersS);
                    cusforecastidL.add(IforecastidS);

                    customer = new String[customersL.size()];
                    cusforecastid = new String[cusforecastidL.size()];

                    for (int l = 0; l < customersL.size(); l++) {
                        customer[l] = customersL.get(l);
                        cusforecastid[l] = cusforecastidL.get(l);

                        Log.d("tAssignId ", customer[l]);
                        Log.d("tappointmentType ", cusforecastid[l]);

                    }

                    CustomersAdapter = new ArrayAdapter<String>(this, R.layout.activity_customlist,R.id.customtxtid, customersL);
                    cuslst.setAdapter(CustomersAdapter);
                    cuslst.setOnItemClickListener(new ListClickHandler());

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
            forecastpos=position;
            pos = adapter.getAdapter().getItem(position).toString();
            Log.d("postion vallue....", "---" + pos);

            posfid=cusforecastidL.get(forecastpos);

            Log.d("@@@@@@@@@@@", "---" + cusforecastidL.get(forecastpos));


            Intent intent = new Intent(Implimentation.this, Get_Notes.class);
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
                        customers_listview(response);

                        if (dialog_progress.isShowing()) {
                            dialog_progress.dismiss();
                        }

                        try {
                            String errorCode = response.getString("error_code");
                            // String errorDesc = response.getString("error_desc");
                            //String[] newdata = errorDesc.split("=");

                            if (errorCode.contentEquals("0")) {
                                Toast.makeText(getApplicationContext(), " Reasponse=Successfully Get_Customers ", Toast.LENGTH_LONG).show();


                            } else {
                                // Toast.makeText(getApplicationContext(), "Not added the notes", Toast.LENGTH_LONG).show();

                                Log.d("errorCode", "" + errorCode);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Implimentation.this);
                                builder.setMessage("Reasponse=Failed to Customers");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface1, int i) {

                                        dialogInterface1.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
        addToRequestQueue1(jsonObjReq);
    }
    public <T> void addToRequestQueue1(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }

    public void JSONSenderVolleytask(String url, final JSONObject json)
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
                        Task_listview(response);

                        if (dialog_progress.isShowing()) {
                            dialog_progress.dismiss();
                        }
                       /* try {
                            String errorCode1 = response.getString("error_code");
                            // String errorDesc = response.getString("error_desc");
                            //String[] newdata = errorDesc.split("=");

                            if (errorCode1.contentEquals("0")) {
                                Toast.makeText(getApplicationContext(), " Reasponse=Successfully Get_Customers ", Toast.LENGTH_LONG).show();


                            } else {
                                // Toast.makeText(getApplicationContext(), "Not added the notes", Toast.LENGTH_LONG).show();

                                Log.d("errorCode", "" + errorCode1);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Implimentation.this);
                                builder.setMessage("Reasponse=Failed to Customers");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface1, int i) {

                                        dialogInterface1.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
*/
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
