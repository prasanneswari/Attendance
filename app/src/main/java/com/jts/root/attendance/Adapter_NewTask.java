package com.jts.root.attendance;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_spinner_item;
import static com.jts.root.attendance.Admin_NewTask.Addspinner_dummy;
import static com.jts.root.attendance.Admin_NewTask.empnamelist;
import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;

public class Adapter_NewTask extends ArrayAdapter<String> {

    private Context context;
    private List<String> newarray;
    private List<String> position;
    String empnamespinnerS;
    RequestQueue sch_RequestQueue;
    static String[] employee, employeeid;
    static int spinnerpos;


    public Adapter_NewTask(Context context, List<String> newarrayS, List<String> positionS) {
        super(context, R.layout.activity_admin__new_task, newarrayS);
        this.context = context;
        this.newarray = newarrayS;
        this.position = positionS;

    }

    @Override
    public View getView(final int positionv, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter__new_task, parent, false);

        final Spinner empnamespinner = (Spinner) rowView.findViewById(R.id.empnameid);
        empnamespinner.setEnabled(true);

        String getempS = "{\"username\":\"admin\",\"password\":\"admin\"}";
        Log.d("jsnresponse getempS", "---" + getempS);
        String url = "http://" + domain_name + ":" + port + "/InventoryApp/get_employee1/";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(getempS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + getempS);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, lstrmdt,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("employee data adapter-", "---" + response.toString());

                        final List<String> employeeidL = new ArrayList<>();
                        final List<String> employeeL = new ArrayList<>();
                        employeeL.clear();

                        JSONArray new_array1 = null;
                        try {
                            new_array1 = response.getJSONArray("get_employee");

                            //new_array1 = responseJSON.getJSONArray("fields");
                            Log.d(" Array", " employee : " + new_array1);
                            for (int i = 0, count = new_array1.length(); i < count; i++) {
                                String employeeidS = new_array1.getJSONObject(i).getString("eid");
                                String employeeS = new_array1.getJSONObject(i).getString("ename");

                                employeeidL.add(employeeidS);
                                employeeL.add(employeeS);
                            }
                            employeeid = new String[employeeidL.size()];
                            employee = new String[employeeL.size()];

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int l = 0; l < employeeL.size(); l++) {
                            employeeid[l] = employeeidL.get(l);
                            employee[l] = employeeL.get(l);
                            Log.d("Adapetr employeeid.. ", employeeid[l]);
                            Log.d("Adapter employee.. ", employee[l]);

                            ArrayAdapter empnameA = new ArrayAdapter(context, simple_spinner_item, employee);
                            empnameA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            empnamespinner.setAdapter(empnameA);
                        }

                        try {
                            final int index1 = positionv;

                            for (int i = 0; i < employee.length; i++) {
                                if (Addspinner_dummy.get(index1).trim().equals(employee[i])) {
                                    empnamespinner.setSelection(i);
                                    Log.d(" data 34343434", "--------------" + Addspinner_dummy.get(index1));
                                    break;
                                }
                                //Log.d(" data 67676767", "--------------" + Addspinner_dummy.get(index1));
                            }

                            Log.d("empnamelist value@@@@@", "--------------" + empnamelist);

                            Log.d("Addspinner_dummy @@@@@", "--------------" + Addspinner_dummy);

                            //Addspinner_dummyid.clear();
                            empnamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    empnamespinnerS = parent.getItemAtPosition(position).toString();
                                    int cuspos = position;
                                    spinnerpos = cuspos;
                                    Log.d("values are ", "--------------" + employeeL);
                                    Log.d("id are ", "--------------" + employeeidL);

                                    Log.d("spinner pos0000", "--------------" + spinnerpos);
                                    Log.d("index valueeee ", "--------------" + index1);

                                    Log.d("selectedperspinner", "--------------" + String.valueOf(index1) + employeeid[spinnerpos]);
                                    Addspinner_dummy.set(index1, empnamespinnerS);
                                    Log.d("add the position values", "--------------" + Addspinner_dummy);

                                    //positionL.add(employeeid[spinnerpos]);

                                } // to close the onItemSelected

                                public void onNothingSelected(AdapterView<?> parent) {
                                    //poaddnw.set(positionv, String.valueOf("0"));
                                }
                            });

                        } catch (NullPointerException e) {
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

        addToRequestQueue1(jsonObjReq);

        return rowView;

    }

    public <T> void addToRequestQueue1(Request<T> req) {
        if (sch_RequestQueue == null) {
            sch_RequestQueue = Volley.newRequestQueue(getContext());
        }
        sch_RequestQueue.add(req);

    }
}
   /* public void getemployes_type(){
        String getempS = "{\"username\":\"admin\",\"password\":\"admin\"}";
        Log.d("jsnresponse getempS", "---" + getempS);
        String url = "http://"+domain_name+":"+port+"/InventoryApp/get_employee1/";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(getempS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + getempS);
        JSONSenderVolley(url, lstrmdt);
    }
    public void getemp_spinner(JSONObject responseJSON){
        try {
            employeeL.clear();

            //requisition = responseJSON.getString("requisition");
            //Log.d( " String", " requisition : " + requisition);
            JSONArray new_array1;

            new_array1 = responseJSON.getJSONArray("get_employee");
            //new_array1 = responseJSON.getJSONArray("fields");
            Log.d( " Array", " employee : " + new_array1);
            for (int i = 0, count = new_array1.length(); i < count; i++) {
                String employeeidS = new_array1.getJSONObject(i).getString("eid");
                 employeeS = new_array1.getJSONObject(i).getString("ename");
                employeeidL.add(employeeidS);
                employeeL.add(employeeS);
            }
            employeeid = new String[employeeidL.size()];
            employee = new String[employeeL.size()];

            for (int l = 0; l < employeeL.size(); l++) {
                employeeid[l] = employeeidL.get(l);
                employee[l] = employeeL.get(l);
                Log.d("Adapetr employeeid.. ", employeeid[l]);
                Log.d("Adapter employee.. ", employee[l]);

            }

            try {

                ArrayAdapter empnameA = new ArrayAdapter(context, simple_spinner_item, employeeL);
                empnameA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                empnamespinner.setAdapter(empnameA);

                for (int i = 0; i < employee.length; i++) {
                    if (Addspinner_dummy.get(index1).trim().equals(employee[i])) {
                        empnamespinner.setSelection(i);
                        Log.d(" data 34343434", "--------------" + Addspinner_dummy.get(index1));
                        break;
                    }
                    Log.d(" data 67676767", "--------------" + Addspinner_dummy.get(index1));
                }

                Log.d("empnamelist value@@@@@", "--------------" + empnamelist);

                Log.d("Addspinner_dummy @@@@@", "--------------" + Addspinner_dummy);

                //Addspinner_dummyid.clear();
                empnamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        empnamespinnerS = parent.getItemAtPosition(position).toString();
                        int cuspos=position;
                        spinnerpos=cuspos;
                        Log.d("values are ", "--------------" +employeeL);
                        Log.d("id are ", "--------------" +employeeidL);

                        Log.d("spinner pos0000", "--------------" +spinnerpos);
                        Log.d("index valueeee ", "--------------" +index1);

                        Log.d("selectedperspinner", "--------------" + String.valueOf(index1) + employeeid[spinnerpos]);
                        Addspinner_dummy.set(index1, empnamespinnerS);
                        Log.d("add the position values", "--------------" + Addspinner_dummy);

                        //positionL.add(employeeid[spinnerpos]);

                    } // to close the onItemSelected

                    public void onNothingSelected(AdapterView<?> parent) {
                        //poaddnw.set(positionv, String.valueOf("0"));
                    }
                });

            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
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
                       // getemp_spinner(response);
                        Log.d("----employee data adapter-----", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                            // String errorDesc = response.getString("error_desc");
                            //String[] newdata = errorDesc.split("=");

                            *//*if (errorCode.contentEquals("0")) {
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
                            }*//*
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
            sch_RequestQueue = Volley.newRequestQueue(getContext());
        }
        sch_RequestQueue.add(req);
    }
*/


