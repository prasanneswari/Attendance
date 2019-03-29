package com.jts.root.attendance;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;

public class Get_Cus_Log extends AppCompatActivity {
    ListView get_lst;
    String[] cname = {};
    String[] ename = {};
    String[] status = {};
    String[] visitdate = {};
    String[] distravel = {};

    TextView fromT,toT;
    Button sunB,cancelB;
    static String fromtime;
    static String totime;
    private ProgressDialog dialog_progress ;




    com.android.volley.RequestQueue sch_RequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__cus__log);
        get_lst=(ListView)findViewById(R.id.cuslist);
        fromT=(TextView)findViewById(R.id.fromdateid);
        toT=(TextView)findViewById(R.id.todateid);
        sunB=(Button)findViewById(R.id.submitid);
        cancelB=(Button)findViewById(R.id.cancelid);
        dialog_progress = new ProgressDialog(Get_Cus_Log.this);

        getcus_post();

        fromT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(Get_Cus_Log.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                // set day of month , month and year value in the edit text
                                fromtime = year + "-" + (month + 1) + "-" + day;
                                fromT.setText(year + "-" + (month + 1) + "-" + day);
                                Log.d("setting fromT", " ..  " + fromT);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        toT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(Get_Cus_Log.this,

                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                // set day of month , month and year value in the edit text
                                totime = year + "-" + (month + 1) + "-" + day;
                                toT.setText(year + "-" + (month + 1) + "-" + day);
                                Log.d("setting toT", " ..  " + toT);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Get_Cus_Log.this, Sales_Incharge.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        sunB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_progress.setMessage("connecting ...");
                dialog_progress.show();
                String fromdateS = fromT.getText().toString();
                Log.d("name fromdateS", " ..  " + fromdateS);
                String todateS = toT.getText().toString();
                Log.d("name todateS", " ..  " + todateS);

                String getcusS = "{\"username\":\"admin\",\"password\":\"admin\",\"fromDate\":\"" + fromdateS + "\",\"toDate\":\"" + todateS + "\"}";
                Log.d("jsnresponse customerS", "---" + getcusS);
                String get_url = "http://"+domain_name+":"+port+"/InventoryApp/get_cus_logs1/";
                JSONObject lstrmdt = null;
                // try {
                try {
                    lstrmdt = new JSONObject(getcusS);
                } catch (JSONException e) {

                }
                Log.d("jsnresponse....", "---" + getcusS);
                JSONSenderVolley(get_url, lstrmdt);
            }
        });

    }

    public void getcus_post(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        String formattedDate = dateFormat.format(new Date()).toString();
        Log.d("date formate", "---" + formattedDate);

        String getcusS = "{\"username\":\"admin\",\"password\":\"admin\",\"fromDate\":\"" + formattedDate + "\",\"toDate\":\"" + formattedDate + "\"}";
        Log.d("jsnresponse customerS", "---" + getcusS);
        String get_url = "http://"+domain_name+":"+port+"/InventoryApp/get_cus_logs1/";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(getcusS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + getcusS);
        dialog_progress.setMessage("connecting ...");
        dialog_progress.show();
        JSONSenderVolley(get_url, lstrmdt);
    }
    public  void getcus_request(JSONObject responseJSON){


            //Log.d( " Array", " response ->  " + response);
            JSONArray new_array1;
            List<String> cnameL = new ArrayList<String>();
            List<String> enameL = new ArrayList<String>();
            List<String> statusL = new ArrayList<String>();
            List<String> visitdataL = new ArrayList<String>();
            List<String> distravelL = new ArrayList<String>();
        try {
            new_array1 = responseJSON.getJSONArray("get_cus_logs");
            Log.d( " Array", " : " + new_array1);
            for (int i = 0, count = new_array1.length(); i < count; i++) {
                String cnameS = new_array1.getJSONObject(i).getString("cname");
                String enameS = new_array1.getJSONObject(i).getString("ename");
                String sattusS = new_array1.getJSONObject(i).getString("status");
                String visitdataS = new_array1.getJSONObject(i).getString("visitDate");
                String distravelS = new_array1.getJSONObject(i).getString("distance");

                cnameL.add(cnameS);
                enameL.add(enameS);
                statusL.add(sattusS);
                visitdataL.add(visitdataS);
                distravelL.add(distravelS);

            }
            cname = new String[cnameL.size()];
            ename = new String[enameL.size()];
            status = new String[statusL.size()];
            visitdate = new String[visitdataL.size()];
            distravel = new String[distravelL.size()];

            for (int l = 0; l < distravelL.size(); l++) {
                cname[l] = cnameL.get(l);
                ename[l] = enameL.get(l);
                status[l] = statusL.get(l);
                visitdate[l] = visitdataL.get(l);
                distravel[l] = distravelL.get(l);

                Log.d("cnameid ", cname[l]);
                Log.d("ename ", ename[l]);
                Log.d("status ", status[l]);
                Log.d("visitdate ", visitdate[l]);
                Log.d("distravel ", distravel[l]);


            }

            Adapter_GetCusLog reqAdapter = new Adapter_GetCusLog(Get_Cus_Log.this,cname,ename, status, visitdate,distravel);
            get_lst.setAdapter(reqAdapter);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void JSONSenderVolley(String get_url, final JSONObject json)
    {
        Log.d("update_received_post-", "---"+get_url);
        Log.d("555555", "00000000"+json.toString());

        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                get_url, json,

                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
/*
                        Log.d(Bitmap.Config.TAG, response.toString());
*/
                        Log.d("----pur_url values-----", "---" + response.toString());

                        getcus_request(response);
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
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Get_Cus_Log.this);
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

                        /*try {
                            int login_code = response.getInt("error_code");
                            String er_discp=response.getString("error_desc");

                            String[] separated = er_discp.split("=");
                            if(login_code==0){
                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
                            }else
                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
                        }
                        catch (JSONException e) {

                        }*/
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