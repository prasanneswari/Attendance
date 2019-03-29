package com.jts.root.attendance;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import java.util.Locale;
import java.util.Map;
import static com.jts.root.attendance.Job_Sheet.langitude;
import static com.jts.root.attendance.Job_Sheet.latitude;
import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;
import static com.jts.root.attendance.userrole_menu.idS;

public class New_Data extends AppCompatActivity {

    EditText cname,phnum,email,address,distancetravelled;
    TextView duedateT;
    Button addbtn,cancelbtn;
    Spinner statusS,salesS;
    RequestQueue sch_RequestQueue;
    static String gtime;
    static List<String> userL = new ArrayList<String>();
    static List<String> statusL = new ArrayList<String>();
    static List<String> statusidL = new ArrayList<String>();
    static List<String> salesL = new ArrayList<String>();
    static List<String> salesidL = new ArrayList<String>();
    static String[] Status,  Sales, statusid, salesid;
    static int statuspos, salespos;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__data);
        cname = (EditText) findViewById(R.id.cnameid);
        phnum = (EditText) findViewById(R.id.cnumberid);
        email = (EditText) findViewById(R.id.cmailid);
        address = (EditText) findViewById(R.id.caddressid);
        distancetravelled = (EditText) findViewById(R.id.distravelledid);
        duedateT = (TextView) findViewById(R.id.duedateid);
        addbtn = (Button) findViewById(R.id.addid);
        cancelbtn = (Button) findViewById(R.id.cancelid);

        statusS = (Spinner) findViewById(R.id.statusid);
        salesS = (Spinner) findViewById(R.id.salesid);

        Log.d("user list values", "--------------" + userL);
        Log.d("status list values", "--------------" + statusL);
        Log.d("sales list values", "--------------" + salesL);

        statusL.clear();
        statusidL.clear();
        salesL.clear();
        salesidL.clear();
        status_values();
        sales_values();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameS = cname.getText().toString();
                String phnumS = phnum.getText().toString();
                String mailS = email.getText().toString();
                String addressS = address.getText().toString();
                String duedateS = duedateT.getText().toString();
                Log.d("name value", " ..  " + duedateS);
                String distravelledS = distancetravelled.getText().toString();
                Log.d("distravelledS", " ..  " + distravelledS);


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(new Date()).toString();
                Log.d("date formate", "---" + formattedDate);
                Log.d("langitude", "---" + langitude);
                Log.d("latitude", "---" + latitude);

                String AddS = "{\"username\":\"admin\",\"password\":\"admin\",\"cname\":\"" + nameS + "\",\"mobile\":\"" + phnumS + "\",\"email\":\"" + mailS + "\",\"address\":\"" + addressS + "\",\"dateOfVisit\":\"" + formattedDate + "\",\"dueDate\":\"" + duedateS + "\",\"statusId\":\"" + statusid[statuspos] + "\",\"userId\":\"" + idS + "\",\"salesCategory\":\"" + salesid[salespos] + "\",\"langitude\":\"" + langitude + "\",\"latitude\":\"" + latitude + "\"}";
                Log.d("sending string is :", AddS.toString());
                Log.d("jsnresponse AddS", "---" + AddS);
                String Add_url = "http://"+domain_name+":"+port+"/InventoryApp/add_customer1/";
                JSONObject lstrmdt = null;
                try {
                    lstrmdt = new JSONObject(AddS);
                }
                catch (JSONException e) {

                }
                Log.d("jsnresponse....", "---" + AddS);
                JSONSenderVolleyAdd(Add_url, lstrmdt);
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(New_Data.this, Customer_List.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        duedateT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(New_Data.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                // set day of month , month and year value in the edit text
                                gtime = year + "-" + (month + 1) + "-" + day;
                                duedateT.setText(year + "-" + (month + 1) + "-" + day);
                                Log.d("setting date", " ..  " + duedateT);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
    public void status_values(){

        String statusS = "{\"username\":\"admin\",\"password\":\"admin\"}";
        Log.d("jsnresponse statusS", "---" + statusS);
        String addcus_url = "http://"+domain_name+":"+port+"/InventoryApp/get_status1/";
        JSONObject lstrmdt = null;
        try {
            lstrmdt = new JSONObject(statusS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + statusS);
        JSONSenderVolley(addcus_url, lstrmdt);
    }
    public void sales_values(){
        String salesS = "{\"username\":\"admin\",\"password\":\"admin\"}";
        Log.d("jsnresponse salesS", "---" + salesS);
        String user_url = "http://"+domain_name+":"+port+"/InventoryApp/get_sales_category1/";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(salesS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + salesS);
        JSONSenderVolley(user_url, lstrmdt);
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
            ArrayAdapter<String> statusA= new ArrayAdapter<String>(New_Data.this,android.R.layout.simple_spinner_item, statusL);
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
    public void sales_spinner(JSONObject responseJSON){
        try {
            JSONArray new_array1;

            new_array1 = responseJSON.getJSONArray("get_sales_category");
            Log.d( " Array", " employee : " + new_array1);
            for (int i = 0, count = new_array1.length(); i < count; i++) {
                String salesidS = new_array1.getJSONObject(i).getString("scid");
                String salesS = new_array1.getJSONObject(i).getString("scname");
                salesL.add(salesS);
                salesidL.add(salesidS);
            }
            Sales = new String[salesL.size()];
            salesid = new String[salesidL.size()];

            for (int l = 0; l < salesL.size(); l++) {
                salesid[l] = salesidL.get(l);
                Sales[l] = salesL.get(l);
                Log.d("salesid ", salesid[l]);
                Log.d("Sales ", Sales[l]);
            }

            ArrayAdapter<String> salesA= new ArrayAdapter<String>(New_Data.this,android.R.layout.simple_spinner_item, salesL);
            salesA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            salesS.setAdapter(salesA);
            salesS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    int index=position;
                    // TODO Auto-generated method stub
                    String selectedperspinner = parent.getItemAtPosition(position).toString();
                    salespos=index;

                    Log.d("salespos", "--------------" +salespos);

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

       public void JSONSenderVolleyAdd(String Add_url, final JSONObject json)
       {
           Log.d("---url-----", "---"+Add_url);
           Log.d("555555", "00000000"+json.toString());
           JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                   Add_url, json,

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

                                   intent = new Intent(New_Data.this, Customer_List.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                   startActivity(intent);

                               }
                               else {
                                   // Toast.makeText(getApplicationContext(), "Not added the notes", Toast.LENGTH_LONG).show();

                                   Log.d("errorCode","" + errorCode);
                                   final AlertDialog.Builder builder = new AlertDialog.Builder(New_Data.this);
                                   builder.setMessage("Reasponse=Failed to add_customers");

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
                   final AlertDialog.Builder builder = new AlertDialog.Builder(New_Data.this);
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

    public void JSONSenderVolley(String user_url, final JSONObject json)
    {
        Log.d("save_url-", "---"+user_url);
        Log.d("555555", "00000000"+json.toString());
        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                user_url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        status_spinner(response);
                        sales_spinner(response);
                        Log.d("----New Data Response-----", "---"+response.toString());

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
            }
        }) {

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
    }

}