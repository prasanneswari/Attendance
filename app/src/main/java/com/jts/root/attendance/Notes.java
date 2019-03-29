package com.jts.root.attendance;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import static com.jts.root.attendance.Customer_List.postion;
import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;
import static com.jts.root.attendance.userrole_menu.idS;

public class Notes extends AppCompatActivity {
    EditText noteE,distanceE;
    TextView choosedate;
    Button noticebtn,historybtn,appointmentbtn,savebtn,cancelbtn;
    static String choosetime;
    RequestQueue sch_RequestQueue;
    static int statuspos;
    static List<String> statusL = new ArrayList<String>();
    static List<String> statusidL = new ArrayList<String>();
    static String[] Status,  statusid;
    Spinner statusS;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        noteE=(EditText)findViewById(R.id.noteid);
        distanceE=(EditText)findViewById(R.id.distanceid);
        choosedate=(TextView)findViewById(R.id.dateid);
       // noticebtn=(Button)findViewById(R.id.noticebtn);
       // historybtn=(Button)findViewById(R.id.historybtn);
        appointmentbtn=(Button)findViewById(R.id.appointmentbtn);
        savebtn=(Button)findViewById(R.id.savebtn);
        cancelbtn=(Button)findViewById(R.id.cancelnote);
        statusS = (Spinner) findViewById(R.id.statusid);
        statusL.clear();
        statusidL.clear();
        status_values();
        choosedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(Notes.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                // set day of month , month and year value in the edit text
                                choosetime = year + "-" + (month + 1) + "-" + day;
                                choosedate.setText(year + "-" + (month + 1) + "-" + day);
                                Log.d("setting fromT", " ..  " + choosedate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        appointmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notes.this, Appointment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnotes_values();

            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notes.this, Customer_List.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }


    public void status_values(){

        String statusS = "{\"username\":\"admin\",\"password\":\"admin\"}";
        Log.d("jsnresponse statusS", "---" + statusS);
        String url = "http://"+domain_name+":"+port+"/InventoryApp/get_status1/";
        JSONObject lstrmdt = null;
        try {
            lstrmdt = new JSONObject(statusS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + statusS);
        JSONSenderVolley(url, lstrmdt);
    }


    public void status_spinner(JSONObject responseJSON){
        try {
            JSONArray new_array1;

            new_array1 = responseJSON.getJSONArray("get_status");
            Log.d( " Array", " employee : " + new_array1);
            for (int i = 0, count = new_array1.length(); i < count; i++) {
                String statusidS = new_array1.getJSONObject(i).getString("sid");
                String statusS = new_array1.getJSONObject(i).getString("sname");
                statusL.add(statusS);
                statusidL.add(statusidS);
            }
            Status = new String[statusL.size()];
            statusid = new String[statusidL.size()];

            for (int l = 0; l < statusL.size(); l++) {
                statusid[l] = statusidL.get(l);
                Status[l] = statusL.get(l);
                Log.d("statusid ", statusid[l]);
                Log.d("Status ", Status[l]);
            }
            ArrayAdapter<String> statusA= new ArrayAdapter<String>(Notes.this,android.R.layout.simple_spinner_item, statusL);
            statusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            statusS.setAdapter(statusA);

            statusS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    int index=position;
                    // TODO Auto-generated method stub
                    String selectedperspinner = parent.getItemAtPosition(position).toString();
                    statuspos=index;

                    Log.d("statuspos", "--------------" +statuspos);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addnotes_values(){

        String noteS = noteE.getText().toString();
        String distanceS = distanceE.getText().toString();
        String nextdateS = choosedate.getText().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(new Date()).toString();
        Log.d("date formate", "---" + formattedDate);                //{"username":"admin","password":"admin","cname":"jts6","dateOfVisit":"2018-05-07","statusId":"21","userId":"3","notes":"visited","nextdate":"2018-06-01"}

        String AddS = "{\"username\":\"admin\",\"password\":\"admin\",\"cname\":\"" + postion + "\",\"dateOfVisit\":\"" + formattedDate + "\",\"statusId\":\"" + statusid[statuspos] + "\",\"userId\":\""+idS+"\",\"notes\":\"" + noteS + "\",\"nextdate\":\"" + nextdateS + "\",\"distance\":\"" + distanceS + "\"}";
        Log.d("sending string is :", AddS.toString());
        Log.d("jsnresponse AddS", "---" + AddS);
        String notes_url = "http://"+domain_name+":"+port+"/InventoryApp/add_cus_logs1/";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(AddS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + AddS);
        JSONSenderVolleyAddNotes(notes_url, lstrmdt);
    }

    public void JSONSenderVolleyAddNotes(String notes_url, final JSONObject json)
    {
        Log.d("---url-----", "---"+notes_url);
        Log.d("555555", "00000000"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                notes_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("----JSONSenderVolley--", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                           // String errorDesc = response.getString("error_desc");
                            //String[] newdata = errorDesc.split("=");

                            if (errorCode.contentEquals("0")){
                                 Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();

                                intent = new Intent(Notes.this, Customer_List.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }
                            else {
                               // Toast.makeText(getApplicationContext(), "Not added the notes", Toast.LENGTH_LONG).show();

                                Log.d("errorCode","" + errorCode);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Notes.this);
                                builder.setMessage("Reasponse=Failed to add_notes");

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
                VolleyLog.d(" Error---------", "shulamithi: " + String.valueOf(error));
                Log.d("my test error-----","shulamithi: " + String.valueOf(error));
                Toast.makeText(getApplicationContext(), "connection error ", Toast.LENGTH_LONG).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Notes.this);
                builder.setTitle("Info");
                builder.setMessage(String.valueOf(error));
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface1, int i) {

                        dialogInterface1.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                //headers.put("Content-Type","application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                //return (headers != null || headers.isEmpty()) ? headers : super.getHeaders();
                return headers;
            }
        };

        jsonObjReq.setTag("");
        addToRequestQueue1(jsonObjReq);
    }

    public <T> void addToRequestQueue1(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        sch_RequestQueue.add(req);
    }


    public void JSONSenderVolley(String url, final JSONObject json)
    {
        Log.d("save_url-", "---"+url);
        Log.d("555555", "00000000"+json.toString());
        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("----New Data Response-----", "---"+response.toString());
                        status_spinner(response);
                        /*try {
                            int new_data = response.getInt("error_code");
                            String er_discp=response.getString("error_desc");
                            String[] newdata = er_discp.split("=");
                            if(new_data==0){
                                Toast.makeText(getApplicationContext(), newdata[1], Toast.LENGTH_LONG).show();
                            }else
                                Toast.makeText(getApplicationContext(), newdata[1], Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {

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
