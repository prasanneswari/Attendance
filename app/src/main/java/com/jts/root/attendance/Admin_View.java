package com.jts.root.attendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;

public class Admin_View extends AppCompatActivity {
    ListView custom_lst,tasklst;
     String[] Acusname;
     String[] Afullname;
     String[] Acuscontact;
     String[] Aapntdate;
     String[] Avisitdate;
    static String[] Anotes;
     ProgressDialog dialog_progress ;


    public static String cnameS, contactS, addressS, visitdataS, categoryS;

    AlertDialog.Builder builder;
    AlertDialog alertdialog;
    com.android.volley.RequestQueue sch_RequestQueue;
    Button logout;
     String[] AssignId;
     String[] AssignedTo;
     String[] appointmentType;
     String[] appointmentdate;
     String[] completeby;
     String[] customerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__view);
        custom_lst = (ListView) findViewById(R.id.admin_list);
        tasklst = (ListView) findViewById(R.id.tasklist);

        logout = (Button) findViewById(R.id.btn_logout);
        dialog_progress = new ProgressDialog(Admin_View.this);

        //httpRequest();
        custom_post();
        tasklst_post();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_View.this, Sales_Incharge.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void custom_post() {
        String tasklstS = "{\"username\":\"admin\",\"password\":\"admin\"}";
        //String pur_prds = "{\"orderid\":\"" + lID + "\"}";
        Log.d("jsnresponse tasklstS", "---" + tasklstS);
        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        //String room_ssid_url = "http://cld003.jts-prod.in:5906/AssetTrackerApp/get_rooms_ssid/";
        String url = "http://" + domain_name + ":" + port + "/InventoryApp/get_appointments/";
        // String urlrs= "https://jtsha.in/service/validate_web";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(tasklstS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + tasklstS);
        JSONSenderVolley(url, lstrmdt);
    }

    public void tasklst_post() {
        String customerS = "{\"username\":\"admin\",\"password\":\"admin\"}";
        //String pur_prds = "{\"orderid\":\"" + lID + "\"}";
        Log.d("jsnresponse customerS", "---" + customerS);
        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        //String room_ssid_url = "http://cld003.jts-prod.in:5906/AssetTrackerApp/get_rooms_ssid/";
        String url = "http://" + domain_name + ":" + port + "/InventoryApp/get_adminimplementationtasks/";
        // String urlrs= "https://jtsha.in/service/validate_web";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(customerS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + customerS);
        JSONSenderVolleytask(url, lstrmdt);
    }

    public void custom_request(JSONObject responseJSON) {


            //Log.d( " Array", " response ->  " + response);
            JSONArray adminview;
            List<String> AcusnameL = new ArrayList<String>();
            List<String> AfullnameL = new ArrayList<String>();
            List<String> AcuscontactL = new ArrayList<String>();
            List<String> AapntdateL = new ArrayList<String>();
            List<String> AvisitdateL = new ArrayList<String>();
            List<String> AnoteL = new ArrayList<String>();
        try {
            adminview = responseJSON.getJSONArray("get_appointments");
            Log.d(" adminview", " : " + adminview);
            for (int i = 0, count = adminview.length(); i < count; i++) {
                String AcusnameS = adminview.getJSONObject(i).getString("customername");
                String AfullnameS = adminview.getJSONObject(i).getString("fullname");
                String AcuscontactS = adminview.getJSONObject(i).getString("customercontact");
                String AapntdateS = adminview.getJSONObject(i).getString("appointmentdate");
                String AvisitdataS = adminview.getJSONObject(i).getString("visiteddate");
                String AnoteS = adminview.getJSONObject(i).getString("notes");

                AcusnameL.add(AcusnameS);
                AfullnameL.add(AfullnameS);
                AcuscontactL.add(AcuscontactS);
                AapntdateL.add(AapntdateS);
                AvisitdateL.add(AvisitdataS);
                AnoteL.add(AnoteS);

            }
            Acusname = new String[AcusnameL.size()];
            Afullname = new String[AfullnameL.size()];
            Acuscontact = new String[AcuscontactL.size()];
            Aapntdate = new String[AapntdateL.size()];
            Avisitdate = new String[AvisitdateL.size()];
            Anotes = new String[AnoteL.size()];


            for (int l = 0; l < AcusnameL.size(); l++) {
                Acusname[l] = AcusnameL.get(l);
                Afullname[l] = AfullnameL.get(l);
                Acuscontact[l] = AcuscontactL.get(l);
                Aapntdate[l] = AapntdateL.get(l);
                Avisitdate[l] = AvisitdateL.get(l);
                Anotes[l] = AnoteL.get(l);

                Log.d("Acusname ", Acusname[l]);
                Log.d("Afullname ", Afullname[l]);
                Log.d("Acuscontact ", Acuscontact[l]);
                Log.d("Aapntdate ", Aapntdate[l]);
                Log.d("Avisitdate ", Avisitdate[l]);
                Log.d("Anotes ", Anotes[l]);

            }
            Adapter_Admin_View reqAdapter = new Adapter_Admin_View(Admin_View.this, Acusname, Afullname, Acuscontact, Aapntdate, Avisitdate);
            custom_lst.setAdapter(reqAdapter);
        }

        catch (NullPointerException e) {

    }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void tasklst_request(JSONObject responseJSON) {


            //Log.d( " Array", " response ->  " + response);
            JSONArray tastlat;
            List<String> AssignIdL = new ArrayList<String>();
            List<String> AssignedToL = new ArrayList<String>();
            List<String> appointmentTypeL = new ArrayList<String>();
            List<String> appointmentdateL = new ArrayList<String>();
            List<String> completebyL = new ArrayList<String>();
            List<String> customerNameL = new ArrayList<String>();
        try {
            tastlat = responseJSON.getJSONArray("get_adminimplementationtasks");
            Log.d(" tastlat", " : " + tastlat);
            for (int i = 0, count = tastlat.length(); i < count; i++) {
                String AssignIdS = tastlat.getJSONObject(i).getString("AssignId");
                String AssignedToS = tastlat.getJSONObject(i).getString("AssignedTo");
                String appointmentTypeS = tastlat.getJSONObject(i).getString("appointmentType");
                String appointmentdateS = tastlat.getJSONObject(i).getString("appointmentdate");
                String completebyS = tastlat.getJSONObject(i).getString("completeby");
                String customerNameS = tastlat.getJSONObject(i).getString("customerName");

                AssignIdL.add(AssignIdS);
                AssignedToL.add(AssignedToS);
                appointmentTypeL.add(appointmentTypeS);
                appointmentdateL.add(appointmentdateS);
                completebyL.add(completebyS);
                customerNameL.add(customerNameS);

            }
            AssignId = new String[AssignIdL.size()];
            AssignedTo = new String[AssignedToL.size()];
            appointmentType = new String[appointmentTypeL.size()];
            appointmentdate = new String[appointmentdateL.size()];
            completeby = new String[completebyL.size()];
            customerName = new String[customerNameL.size()];


            for (int l = 0; l < AssignIdL.size(); l++) {
                AssignId[l] = AssignIdL.get(l);
                AssignedTo[l] = AssignedToL.get(l);
                appointmentType[l] = appointmentTypeL.get(l);
                appointmentdate[l] =appointmentdateL.get(l);
                completeby[l] = completebyL.get(l);
                customerName[l] = customerNameL.get(l);

                Log.d("AssignId ", AssignId[l]);
                Log.d("AssignedTo ", AssignedTo[l]);
                Log.d("appointmentType ", appointmentType[l]);
                Log.d("appointmentdate ", appointmentdate[l]);
                Log.d("completeby ", completeby[l]);
                Log.d("customerName ", customerName[l]);

            }
            Adapter_Task reqAdapter = new Adapter_Task(Admin_View.this, AssignId, AssignedTo, appointmentType, appointmentdate, completeby,customerName);
            tasklst.setAdapter(reqAdapter);
        }

        catch (NullPointerException e) {

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

                        custom_request(response);

                        try {
                            if (dialog_progress.isShowing()) {
                                dialog_progress.dismiss();

                            }
                            int login_code = response.getInt("error_code");
                            String er_discp=response.getString("error_desc");

                            String[] separated = er_discp.split("=");
                            if(login_code==0){
                                Toast.makeText(getApplicationContext(), "successfully get the data", Toast.LENGTH_LONG).show();
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
                        Log.d("----tasklist values-----", "---"+response.toString());

                        tasklst_request(response);

                        try {
                            if (dialog_progress.isShowing()) {
                                dialog_progress.dismiss();

                            }
                            int login_code = response.getInt("error_code");
                            String er_discp=response.getString("error_desc");

                            String[] separated = er_discp.split("=");
                            if(login_code==0){
                                Toast.makeText(getApplicationContext(), "successfully get the data", Toast.LENGTH_LONG).show();
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
        addToRequestQueue1(jsonObjReq);
    }

    public <T> void addToRequestQueue1(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }

}
