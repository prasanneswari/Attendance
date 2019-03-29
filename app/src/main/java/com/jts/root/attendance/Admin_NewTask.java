package com.jts.root.attendance;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jts.root.attendance.Adapter_NewTask.employee;
import static com.jts.root.attendance.Adapter_NewTask.employeeid;
import static com.jts.root.attendance.Customer_List.forecastidS;
import static com.jts.root.attendance.Job_Sheet.langitude;
import static com.jts.root.attendance.Job_Sheet.latitude;
import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;

public class Admin_NewTask extends AppCompatActivity {

    Spinner cusName,jobtypeSpiner;
    TextView address, contactno;
    TextView duedateT,cmpletdateT;
    Button save, cancel;
    ImageView plusimg;
    ListView emplist;
    static List<String> Addspinner_dummy = new ArrayList<>();
    static List<String> Addspinner_dummyid = new ArrayList<>();

    static List<String> empnamelist = new ArrayList<>();
    static String gtime;
    RequestQueue sch_RequestQueue;
    static List<String> jobtypeidL = new ArrayList<>();
    static List<String> jobtypeL = new ArrayList<>();

    static List<String> newtask_customL = new ArrayList<String>();
    static List<String> newtask_forecastidL = new ArrayList<String>();
    static String newtask_forecastidS;


    static String[] jobtype,jobtypeid;
    static int jobtypepos,getcuspos;

    String[] cnamecus,addresscus,contactcus,visitdatecus,categorycus,forecastid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__new_task);
        cusName = (Spinner) findViewById(R.id.cusnameid);
        jobtypeSpiner = (Spinner) findViewById(R.id.jobtypeid);
        address = (TextView) findViewById(R.id.addressid);
        contactno = (TextView) findViewById(R.id.contactid);
        duedateT = (TextView) findViewById(R.id.duedateid);
        cmpletdateT = (TextView) findViewById(R.id.cmpltdateid);
        save = (Button) findViewById(R.id.saveid);
        cancel = (Button) findViewById(R.id.cancelid);
        plusimg = (ImageView) findViewById(R.id.plusimg);
        emplist = (ListView) findViewById(R.id.emplist);
        getcustomer_value();
        Job_type();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addjobtype_values();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_NewTask.this, Sales_Incharge.class);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(Admin_NewTask.this,
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

        cmpletdateT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(Admin_NewTask.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                // set day of month , month and year value in the edit text
                                gtime = year + "-" + (month + 1) + "-" + day;
                                cmpletdateT.setText(year + "-" + (month + 1) + "-" + day);
                                Log.d("setting date", " ..  " + cmpletdateT);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });


        Addspinner_dummy.clear();
        empnamelist.clear();
        Addspinner_dummyid.clear();
        plusimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Addspinner_dummy.add("john");//idi

                empnamelist.add("john");
                Log.d("this is main class....", "--------------" + Addspinner_dummy);


                final Adapter_NewTask adapter = new Adapter_NewTask(getApplicationContext(), Addspinner_dummy, empnamelist);
                emplist.setAdapter(adapter);

            }
        });
        Addspinner_dummy.add("john");//idi

        empnamelist.add("john");

        final Adapter_NewTask adapter = new Adapter_NewTask(getApplicationContext(), Addspinner_dummy, empnamelist);
        emplist.setAdapter(adapter);
    }
    public void Job_type(){
        String jobtypeS = "{\"username\":\"admin\",\"password\":\"admin\"}";
        Log.d("jsnresponse jobtypeS", "---" + jobtypeS);
        String url = "http://"+domain_name+":"+port+"/InventoryApp/get_appointmenttypes/";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(jobtypeS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + jobtypeS);
        JSONSenderVolley(url, lstrmdt);
    }
    public void jobtype_spinner(JSONObject responseJSON){
        try {
            jobtypeL.clear();

            //requisition = responseJSON.getString("requisition");
            //Log.d( " String", " requisition : " + requisition);
            JSONArray new_array1;

            new_array1 = responseJSON.getJSONArray("get_appointmenttypes");
            //new_array1 = responseJSON.getJSONArray("fields");
            Log.d( " Array", " employee : " + new_array1);
            for (int i = 0, count = new_array1.length(); i < count; i++) {
                String jobtypeidS = new_array1.getJSONObject(i).getString("appointmenttypeid");
                String jobttypeS = new_array1.getJSONObject(i).getString("appointmenttype");
                jobtypeidL.add(jobtypeidS);
                jobtypeL.add(jobttypeS);
            }
            jobtypeid = new String[jobtypeidL.size()];
            jobtype = new String[jobtypeL.size()];

            for (int l = 0; l < jobtypeL.size(); l++) {
                jobtypeid[l] = jobtypeidL.get(l);
                jobtype[l] = jobtypeL.get(l);
                Log.d("jobtypeid ", jobtypeid[l]);
                Log.d("jobtype ", jobtype[l]);
            }
            ArrayAdapter<String> jobtypeA= new ArrayAdapter<String>(Admin_NewTask.this,android.R.layout.simple_spinner_item, jobtype);
            jobtypeA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            jobtypeSpiner.setAdapter(jobtypeA);

            jobtypeSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    int index=position;
                    // TODO Auto-generated method stub
                    String selectedperspinner = parent.getItemAtPosition(position).toString();
                    jobtypepos=index;
                    Log.d("statuspos", "--------------" +jobtypepos);
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

    public void getcustomer_value(){
        String customerS = "{\"username\":\"admin\",\"password\":\"admin\"}";
        Log.d("jsnresponse getcustomer", "---" + customerS);
        String url = "http://"+domain_name+":"+port+"/InventoryApp/get_customer1/";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(customerS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + customerS);
        JSONSenderVolley(url, lstrmdt);
    }
    public void customer_spinner(JSONObject responseJSON){
        try {
            List<String> forecastidL = new ArrayList<>();
            List<String> cnameL = new ArrayList<>();
             List<String> contactL = new ArrayList<>();
             List<String> addressL = new ArrayList<>();
             List<String> visitdateL = new ArrayList<>();
             List<String> categoryL=new ArrayList<>();

            //requisition = responseJSON.getString("requisition");
            //Log.d( " String", " requisition : " + requisition);
            JSONArray new_array1;

            new_array1 = responseJSON.getJSONArray("get_employee");
            //new_array1 = responseJSON.getJSONArray("fields");
            Log.d( " Array", " employee : " + new_array1);
            for (int i = 0, count = new_array1.length(); i < count; i++) {
                String cnameS = new_array1.getJSONObject(i).getString("cname");
                String contactS = new_array1.getJSONObject(i).getString("contact");
                String addressS = new_array1.getJSONObject(i).getString("address");
                String visitdateS = new_array1.getJSONObject(i).getString("visitdate");
                String categoryS = new_array1.getJSONObject(i).getString("category");
                String forcastIdS = new_array1.getJSONObject(i).getString("forcastId");
                forecastidL.add(forcastIdS);
                cnameL.add(cnameS);
                contactL.add(contactS) ;
                addressL.add(addressS);
                visitdateL.add(visitdateS);
                categoryL.add(categoryS);

            }
            forecastid = new String[forecastidL.size()];
            cnamecus = new String[cnameL.size()];
            addresscus = new String[addressL.size()];
            contactcus = new String[contactL.size()];
            visitdatecus = new String[visitdateL.size()];
            categorycus = new String[categoryL.size()];

            for (int l = 0; l < categoryL.size(); l++) {
                forecastid[l] = forecastidL.get(l);
                cnamecus[l] = cnameL.get(l);
                addresscus[l] = addressL.get(l);
                contactcus[l] = contactL.get(l);
                visitdatecus[l] = visitdateL.get(l);
                categorycus[l] = categoryL.get(l);

                Log.d("cnamecus ", cnamecus[l]);
                Log.d("addresscus ", addresscus[l]);
                Log.d("contactcus ", contactcus[l]);
                Log.d("visitdatecus ", visitdatecus[l]);
                Log.d("categorycus ", categorycus[l]);
                Log.d("forecastid ", forecastid[l]);

            }
            ArrayAdapter<String> getcusA= new ArrayAdapter<String>(Admin_NewTask.this,android.R.layout.simple_spinner_item, cnamecus);
            getcusA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cusName.setAdapter(getcusA);

            cusName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    int index=position;
                    // TODO Auto-generated method stub
                    String selectedperspinner = parent.getItemAtPosition(position).toString();
                    getcuspos=index;
                    Log.d("addressposssssssss", "--------------" +addresscus[getcuspos]);
                    Log.d("contactpossssssssss", "--------------" +contactcus[getcuspos]);
                    Log.d("getcuspos", "--------------" +getcuspos);
                    address.setText(addresscus[getcuspos]);
                    contactno.setText(contactcus[getcuspos]);
                    String forepos=forecastid[getcuspos];
                    Log.d("forepos@@@@@", "--------------" +forepos);

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


    public void addjobtype_values(){
        // String bookapnS = bookapnEdt.getText().toString();
        // String nameS = nameEdt.getText().toString();
        // String mobileS = mobileEdt.getText().toString();
        // String addressS = addressEdt.getText().toString();
        String appointmentdateS = duedateT.getText().toString();
        Log.d("date values", " ..  " + appointmentdateS);
        String cmpletdate = cmpletdateT.getText().toString();
        Log.d("date cmpletdate", " ..  " + cmpletdate);
        for(int i=0;i<Addspinner_dummy.size();i++){
            //System.out.println("this is add ele:"+Addspinner_dummy.get(i));
            for (int j=0;j<employee.length;j++){
                System.out.println("this is emp ele:"+employee[j]);
                if(Addspinner_dummy.get(i).contentEquals(employee[j])){
                  // Log.d("these are equal", " ..  " +j+"value is: "+employee[j]);
                    Addspinner_dummyid.add(employeeid[j]);
                }
            }
        }

        String AddS = "{\"username\":\"admin\",\"password\":\"admin\",\"forecastid\":\"" +forecastid[getcuspos] + "\",\"completeby\":\""+cmpletdate+"\",\"userid\":\""+Addspinner_dummyid+"\",\"appointmentdate\":\"" + appointmentdateS + "\",\"appointmenttype\":\""+jobtypeid[jobtypepos]+"\"}";
        Log.d("sending string is@@@ :", AddS.toString());
        Log.d("jsnresponse AddS", "---" + AddS);
        String url = "http://"+domain_name+":"+port+"/InventoryApp/assign_implementation/";
        //String url = "http://cld003.jts-prod.in:20105/InventoryApp/assign_implementation/";
        JSONObject lstrmdt = null;

        try {
            lstrmdt = new JSONObject(AddS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + AddS);
        JSONSenderVolleyjobtype(url, lstrmdt);
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
                        customer_spinner(response);
                        jobtype_spinner(response);
                        //getemp_spinner(response);
                        Log.d("----New Data Response-----", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                            // String errorDesc = response.getString("error_desc");
                            //String[] newdata = errorDesc.split("=");

                            if (errorCode.contentEquals("0")) {
                                Toast.makeText(getApplicationContext(), " Reasponse=Successfully get_employees ", Toast.LENGTH_LONG).show();


                            } else {
                                // Toast.makeText(getApplicationContext(), "Not added the notes", Toast.LENGTH_LONG).show();

                                Log.d("errorCode", "" + errorCode);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Admin_NewTask.this);
                                builder.setMessage("Reasponse=Failed to get_employees");

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

    public void JSONSenderVolleyjobtype(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

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

                                 Intent intent = new Intent(Admin_NewTask.this, Sales_Incharge.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else {
                                Log.d("errorCode","" + errorCode+"--" + errorDesc);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Admin_NewTask.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Admin_NewTask.this);
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
