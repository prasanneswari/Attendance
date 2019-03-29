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

import static com.jts.root.attendance.Customer_List.forecastidL;
import static com.jts.root.attendance.Customer_List.forecastidS;
import static com.jts.root.attendance.Job_Sheet.langitude;
import static com.jts.root.attendance.Job_Sheet.latitude;
import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;
import static com.jts.root.attendance.userrole_menu.idS;

public class Appointment extends AppCompatActivity {

    EditText bookapnEdt,nameEdt,mobileEdt,addressEdt,noteEdt;
    Spinner appointmentS,teamneedS;
    Button savebtn,canclebtn;
    RequestQueue sch_RequestQueue;
    static List<String>apntL = new ArrayList<String>();
    static List<String> apntidL = new ArrayList<String>();
    static List<String> teamneedL = new ArrayList<String>();
    static List<String> steamidL = new ArrayList<String>();
    static String[] Apnt, Teamneed, Teamneedid, userid, Apntid, salesid;
    static int apntpos, teampos, userpos;
    TextView visitdate,appointmentdate;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        /*bookapnEdt=(EditText)findViewById(R.id.appointmentid);
        nameEdt=(EditText)findViewById(R.id.nameid);
        mobileEdt=(EditText)findViewById(R.id.mobileid);
        addressEdt=(EditText)findViewById(R.id.addressid);*/
        appointmentS=(Spinner) findViewById(R.id.apntypeid);
        teamneedS=(Spinner) findViewById(R.id.teamneedid);
        savebtn=(Button) findViewById(R.id.saveid);
        canclebtn=(Button) findViewById(R.id.cancelbtn);
        noteEdt=(EditText)findViewById(R.id.noteid);
        visitdate=(TextView) findViewById(R.id.visitdateid);
        appointmentdate=(TextView) findViewById(R.id.appindateid);


        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Appointment.this, Notes.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addapnt_values();
            }
        });

        apntL.clear();
        apntidL.clear();
        apn_tytpe();
        teamneed();
        visitdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(Appointment.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                // set day of month , month and year value in the edit text
                                String fromtime = year + "-" + (month + 1) + "-" + day;
                                visitdate.setText(year + "-" + (month + 1) + "-" + day);

                                Log.d("setting fromT", " ..  " + visitdate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        appointmentdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(Appointment.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                // set day of month , month and year value in the edit text
                                String totime = year + "-" + (month + 1) + "-" + day;
                                appointmentdate.setText(year + "-" + (month + 1) + "-" + day);
                                Log.d("setting toT", " ..  " + appointmentdate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
    public void apn_tytpe(){
        String statusS = "{\"username\":\"admin\",\"password\":\"admin\"}";
        Log.d("jsnresponse statusS", "---" + statusS);
        String url = "http://"+domain_name+":"+port+"/InventoryApp/get_appointmenttypes/";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(statusS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + statusS);
        JSONSenderVolley(url, lstrmdt);
    }
    public void teamneed(){
        String teamneedS = "{\"username\":\"admin\",\"password\":\"admin\"}";
        //String add_posts = "{\"orderid\":\"" + lID + "\"}";
        Log.d("jsnresponse statusS", "---" + teamneedS);
        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        //String room_ssid_url = "http://cld003.jts-prod.in:5906/AssetTrackerApp/get_rooms_ssid/";
        String url = "http://"+domain_name+":"+port+"/InventoryApp/get_status1/";
        // String urlrs= "https://jtsha.in/service/validate_web";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(teamneedS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + teamneedS);
        JSONSenderVolley(url, lstrmdt);
    }
    public void apntype_spinner(JSONObject responseJSON){
        try {
            //requisition = responseJSON.getString("requisition");
            //Log.d( " String", " requisition : " + requisition);
            JSONArray new_array1;

            new_array1 = responseJSON.getJSONArray("get_appointmenttypes");
            //new_array1 = responseJSON.getJSONArray("fields");
            Log.d( " Array", " employee : " + new_array1);
            for (int i = 0, count = new_array1.length(); i < count; i++) {
                String apntidS = new_array1.getJSONObject(i).getString("appointmenttypeid");
                String apntS = new_array1.getJSONObject(i).getString("appointmenttype");
                apntL.add(apntS);
                apntidL.add(apntidS);
            }
            Apnt = new String[apntL.size()];
            Apntid = new String[apntidL.size()];

            for (int l = 0; l < apntL.size(); l++) {
                Apntid[l] = apntidL.get(l);
                Apnt[l] = apntL.get(l);
                Log.d("appointmenttypeid ", Apntid[l]);
                Log.d("appointmenttype ", Apnt[l]);
            }
            ArrayAdapter<String> statusA= new ArrayAdapter<String>(Appointment.this,android.R.layout.simple_spinner_item, Apnt);
            statusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            appointmentS.setAdapter(statusA);

            appointmentS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    int index=position;
                    // TODO Auto-generated method stub
                    String selectedperspinner = parent.getItemAtPosition(position).toString();
                    apntpos=index;
                    Log.d("statuspos", "--------------" +apntpos);
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
    public void teamneed_spinner(JSONObject responseJSON){
        try {
            //requisition = responseJSON.getString("requisition");
            //Log.d( " String", " requisition : " + requisition);
            JSONArray new_array1;

            new_array1 = responseJSON.getJSONArray("get_status");
            //new_array1 = responseJSON.getJSONArray("fields");
            Log.d( " Array", " employee : " + new_array1);
            for (int i = 0, count = new_array1.length(); i < count; i++) {
                String teamidS = new_array1.getJSONObject(i).getString("sid");
                String teamneedS = new_array1.getJSONObject(i).getString("sname");
                teamneedL.add(teamneedS);
                steamidL.add(teamidS);
            }
            Teamneed = new String[teamneedL.size()];
            Teamneedid = new String[steamidL.size()];

            for (int l = 0; l < teamneedL.size(); l++) {
                Teamneedid[l] = steamidL.get(l);
                Teamneed[l] = teamneedL.get(l);
                Log.d("teamneedid ", Teamneedid[l]);
                Log.d("teamneed ", Teamneed[l]);
            }
            ArrayAdapter<String> teamneedA= new ArrayAdapter<String>(Appointment.this,android.R.layout.simple_spinner_item, Teamneed);
            teamneedA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            teamneedS.setAdapter(teamneedA);

            teamneedS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    int index=position;
                    // TODO Auto-generated method stub
                    String selectedperspinner = parent.getItemAtPosition(position).toString();
                    teampos=index;
                    Log.d("teamneedpos", "--------------" +teampos);
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

    public void addapnt_values(){
        // String bookapnS = bookapnEdt.getText().toString();
        // String nameS = nameEdt.getText().toString();
        // String mobileS = mobileEdt.getText().toString();
        // String addressS = addressEdt.getText().toString();
        String noteS = noteEdt.getText().toString();
        String visitdateS = visitdate.getText().toString();
        String appointmentdateS = appointmentdate.getText().toString();
        Log.d("date values", " ..  " + visitdateS);
        String formattedDate = visitdate.getText().toString();


        String AddS = "{\"username\":\"admin\",\"password\":\"admin\",\"appointmenttype\":\"" + Apntid[apntpos] + "\",\"forecastid\":\""+forecastidS+"\",\"userid\":\""+idS+"\",\"teamneeded\":\"" + Teamneedid[teampos] + "\",\"appointmentdate\":\""+appointmentdateS+"\",\"notes\":\"" + noteS + "\",\"visiteddate\":\"" + formattedDate + "\"}";
        Log.d("sending string is :", AddS.toString());
        Log.d("jsnresponse AddS", "---" + AddS);
        String job_url = "http://"+domain_name+":"+port+"/InventoryApp/add_appointments/";
        // String urlrs= "https://jtsha.in/service/validate_web";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(AddS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + AddS);
        JSONSenderVolleyjobadd(job_url, lstrmdt);
    }

    public void JSONSenderVolleyjobadd(String job_url, final JSONObject json)
    {
        Log.d("---url-----", "---"+job_url);
        Log.d("555555", "00000000"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                job_url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        Log.d("----JSONSenderVolley--", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            if (errorCode.contentEquals("1")){
                                Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();

                                intent = new Intent(Appointment.this, Notes.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else {
                                Log.d("errorCode","" + errorCode+"--" + errorDesc);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Appointment.this);
                                builder.setTitle("Info");
                                builder.setMessage(errorDesc);

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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Appointment.this);
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
                        apntype_spinner(response);
                        teamneed_spinner(response);
                        Log.d("----New Data Response-----", "---"+response.toString());

                       /* try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            String[] newdata = errorDesc.split("=");
                            if(errorCode.contentEquals("1")){
                                Toast.makeText(getApplicationContext(), "Added the Appointments", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "No Added Appointments", Toast.LENGTH_LONG).show();
                            }
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
