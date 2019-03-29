package com.jts.root.attendance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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

import static com.jts.root.attendance.Adapeter_Implement_task.lforecast;
import static com.jts.root.attendance.Adapeter_Implement_task.ltcame;
import static com.jts.root.attendance.Admin_NewTask.getcuspos;
import static com.jts.root.attendance.Customer_List.forecastidS;
import static com.jts.root.attendance.Customer_List.postion;
import static com.jts.root.attendance.Implimentation.posfid;
import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;
import static com.jts.root.attendance.userrole_menu.idS;

public class Userview_task extends AppCompatActivity {
    TextView cusnameT,addressT,contactnoT;
    Button addnotesB,appointB,cancelB;
    RequestQueue sch_RequestQueue;
    static List<String> AapntL = new ArrayList<String>();
    static List<String> AapntidL = new ArrayList<String>();
    static List<String> AteamneedL = new ArrayList<String>();
    static List<String> AsteamidL = new ArrayList<String>();
    static String[] AApnt, ATeamneed, ATeamneedid, userid, AApntid, salesid;
    static int Aapntpos, Ateampos, userpos;
    static Spinner appointmentS,teamneedS;
    private ProgressDialog dialog_progress ;
    String [] cname,contact,address ,visitdate,category,forecastid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userview_task);
        cusnameT=(TextView)findViewById(R.id.cusmnameid);
        addressT=(TextView)findViewById(R.id.addressid);
        contactnoT=(TextView)findViewById(R.id.contactnoid);
        addnotesB=(Button) findViewById(R.id.addnotesid);
        appointB=(Button) findViewById(R.id.apntid);
        cancelB=(Button) findViewById(R.id.cancelid);
        dialog_progress = new ProgressDialog(Userview_task.this);

        customer_values();
        //contactnoT.setText(addresscus[getcuspos]);


        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Userview_task.this, Implimentation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        addnotesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notes_popup();
            }
        });

        appointB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apnt_popup();
            }
        });

    }

    public void notes_popup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = ((Activity) Userview_task.this).getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.add_notes_popup,
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

        final Button notessave = (Button) dialogLayout.findViewById(R.id.notessaveid);
        final Button notescancel = (Button) dialogLayout.findViewById(R.id.notescancelbtn);
        final EditText addnotes = (EditText) dialogLayout.findViewById(R.id.addnoteid);

        builder.setView(dialogLayout);

        notescancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //delgroup(grpnm);

                dialog.dismiss();
            }
        });

        notessave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String noteS = addnotes.getText().toString();
                Log.d(" edit text notes", "---" + noteS);

                String AddnotesS = "{\"username\":\"admin\",\"password\":\"admin\",\"notes\":\"" + noteS + "\",\"forecastid\":\"" + lforecast + "\",\"userid\":\"" + idS + "\"}";
                Log.d("sending string is :", AddnotesS.toString());
                Log.d("jsnresponse AddnotesS", "---" + AddnotesS);
                String addnotes_url = "http://"+domain_name+":"+port+"/InventoryApp/add_notes/";
                //String addnotes_url = "http://cld003.jts-prod.in:20105/InventoryApp/add_notes/";

                JSONObject lstrmdt = null;

                try {
                    lstrmdt = new JSONObject(AddnotesS);
                } catch (JSONException e) {

                }
                Log.d("jsnresponse....", "---" + AddnotesS);
                JSONSenderVolleyAddNotes(addnotes_url, lstrmdt);

            }
        });

        dialog.show();
    }

    public void apnt_popup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = ((Activity) Userview_task.this).getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.add_apnt_popup,
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
         appointmentS=(Spinner) dialogLayout.findViewById(R.id.apntypeid);
         teamneedS=(Spinner) dialogLayout.findViewById(R.id.teamneedid);
        final Button Asavebtn=(Button)dialogLayout.findViewById(R.id.apntsaveid);
        final Button Acanclebtn=(Button)dialogLayout.findViewById(R.id.apntcancelid);
        final EditText AnoteEdt=(EditText)dialogLayout.findViewById(R.id.noteid);
        final TextView Avisitdate=(TextView) dialogLayout.findViewById(R.id.visitdateid);
        final TextView Aappointmentdate=(TextView) dialogLayout.findViewById(R.id.appindateid);

        builder.setView(dialogLayout);


        AapntL.clear();
        AapntidL.clear();
        apn_tytpe();
        teamneed();

        Avisitdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(Userview_task.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                // set day of month , month and year value in the edit text
                                String fromtime = year + "-" + (month + 1) + "-" + day;
                                Avisitdate.setText(year + "-" + (month + 1) + "-" + day);

                                Log.d("setting fromT", " ..  " + Avisitdate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        Aappointmentdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(Userview_task.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                // set day of month , month and year value in the edit text
                                String totime = year + "-" + (month + 1) + "-" + day;
                                Aappointmentdate.setText(year + "-" + (month + 1) + "-" + day);
                                Log.d("setting toT", " ..  " + Aappointmentdate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        Acanclebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //delgroup(grpnm);

                dialog.dismiss();
            }
        });
        Asavebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String noteS = AnoteEdt.getText().toString();
                String visitdateS = Avisitdate.getText().toString();
                String appointmentdateS = Aappointmentdate.getText().toString();
                Log.d("date values", " ..  " + visitdateS);
                String formattedDate = Avisitdate.getText().toString();
                Log.d("forecastvalues...", " ..  " + posfid);


                String AddS = "{\"username\":\"admin\",\"password\":\"admin\",\"appointmenttype\":\"" + AApntid[Aapntpos] + "\",\"forecastid\":\""+lforecast+"\",\"userid\":\""+idS+"\",\"teamneeded\":\"" + ATeamneedid[Ateampos] + "\",\"appointmentdate\":\""+appointmentdateS+"\",\"notes\":\"" + noteS + "\",\"visiteddate\":\"" + formattedDate + "\"}";
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
        });

        dialog.show();
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
                AapntL.add(apntS);
                AapntidL.add(apntidS);
            }
            AApnt = new String[AapntL.size()];
            AApntid = new String[AapntidL.size()];

            for (int l = 0; l < AapntL.size(); l++) {
                AApntid[l] = AapntidL.get(l);
                AApnt[l] = AapntL.get(l);
                Log.d("appointmenttypeid ", AApntid[l]);
                Log.d("appointmenttype ", AApnt[l]);
            }
            ArrayAdapter<String> statusA= new ArrayAdapter<String>(Userview_task.this,android.R.layout.simple_spinner_item, AApnt);
            statusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            appointmentS.setAdapter(statusA);

            appointmentS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    int index=position;
                    // TODO Auto-generated method stub
                    String selectedperspinner = parent.getItemAtPosition(position).toString();
                    Aapntpos=index;
                    Log.d("statuspos", "--------------" +Aapntpos);
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
                AteamneedL.add(teamneedS);
                AsteamidL.add(teamidS);
            }
            ATeamneed = new String[AteamneedL.size()];
            ATeamneedid = new String[AsteamidL.size()];

            for (int l = 0; l < AteamneedL.size(); l++) {
                ATeamneedid[l] = AsteamidL.get(l);
                ATeamneed[l] = AteamneedL.get(l);
                Log.d("teamneedid ", ATeamneedid[l]);
                Log.d("teamneed ", ATeamneed[l]);
            }
            ArrayAdapter<String> teamneedA= new ArrayAdapter<String>(Userview_task.this,android.R.layout.simple_spinner_item, ATeamneed);
            teamneedA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            teamneedS.setAdapter(teamneedA);

            teamneedS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    int index=position;
                    // TODO Auto-generated method stub
                    String selectedperspinner = parent.getItemAtPosition(position).toString();
                    Ateampos=index;
                    Log.d("teamneedpos", "--------------" +Ateampos);
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

    public void customer_values(){


        String cusS = "{\"username\":\"admin\",\"password\":\"admin\",\"ForecastId\":\""+lforecast+"\"}";
        Log.d("jsnresponse roleS", "---" + cusS);
        String url = "http://"+domain_name+":"+port+"/InventoryApp/get_customer_details1/";
        JSONObject lstrmdt = null;
        // try {

        try {
            lstrmdt = new JSONObject(cusS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + cusS);
        //dialog_progress.setMessage("connecting ...");
       // dialog_progress.show();
        JSONSenderVolley(url, lstrmdt);
    }

    public void cusval(JSONObject responseJSON){
        try {
            List<String> cnameL = new ArrayList<String>();
            List<String> contactL = new ArrayList<String>();
            List<String> addressL = new ArrayList<String>();
            JSONArray new_array1;
            try {
                new_array1 = responseJSON.getJSONArray("get_customer_details");
                //new_array1 = responseJSON.getJSONArray("fields");
                Log.d(" Array", " get_employee : " + new_array1);
                for (int i = 0, count = new_array1.length(); i < count; i++) {
                    String cnameS = new_array1.getJSONObject(i).getString("cname");
                    String tcontactS = new_array1.getJSONObject(i).getString("contact");
                    String taddressS = new_array1.getJSONObject(i).getString("address");

                    cnameL.add(cnameS);
                    contactL.add(tcontactS);
                    addressL.add(taddressS);

                }
                cname = new String[cnameL.size()];
                contact = new String[contactL.size()];
                address = new String[addressL.size()];

                for (int l = 0; l < addressL.size(); l++) {
                    cname[l] = cnameL.get(l);
                    contact[l] = contactL.get(l);
                    address[l] = addressL.get(l);
                    Log.d("cname ", cname[l]);
                    Log.d("contact ", contact[l]);
                    Log.d("address ", address[l]);
                    cusnameT.setText(cname[l]);
                    addressT.setText(address[l]);
                    contactnoT.setText(contact[l]);

                }

            }
            catch (NullPointerException e){

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
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

                                Intent intent = new Intent(Userview_task.this, Userview_task.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else {
                                Log.d("errorCode","" + errorCode+"--" + errorDesc);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Userview_task.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Userview_task.this);
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
        addToRequestQueue2(jsonObjReq);
    }

    public <T> void addToRequestQueue2(Request<T> req) {
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
                        cusval(response);
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

                            if (errorCode.contentEquals("1")){
                                Toast.makeText(getApplicationContext(), "Response=successfully added", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(Userview_task.this, Userview_task.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }
                            else {
                                // Toast.makeText(getApplicationContext(), "Not added the notes", Toast.LENGTH_LONG).show();

                                Log.d("errorCode","" + errorCode);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Userview_task.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Userview_task.this);
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


}
