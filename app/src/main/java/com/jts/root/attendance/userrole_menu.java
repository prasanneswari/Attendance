package com.jts.root.attendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;
import static com.jts.root.attendance.Login.usernameS;

public class userrole_menu extends AppCompatActivity {
    ListView listrole;
    static  int val=0;
    String url1, jES;
    static String eid;
    String[] idA,eNameA,usermenuA,implementA,storeforecastA;
    String implementS="IMPLEMENTATION";
    String storeforecast="STORE FORECAST";
    JSONObject jEmp = null;
    Button logout;
    public ProgressDialog dialog_progress ;
    com.android.volley.RequestQueue sch_RequestQueue;
    static String idS;
    List<String> usermenuL = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userrole_menu);
        listrole=(ListView)findViewById(R.id.listRoles);
        logout=(Button)findViewById(R.id.btn_logout);
        //usermenuL.add("IMPLEMENTATION");


        dialog_progress = new ProgressDialog(userrole_menu.this);
       // dialog_progress.setMessage("connecting ...");
        //dialog_progress.show();
        jES = "{\"username\":\""+usernameS+"\"}";
        Log.d("username1111111",""+usernameS);

        url1 ="http://"+domain_name+":"+port+"/InventoryApp/get_user/";
        try {
            jEmp = new JSONObject(jES);
            getUserVolley(url1, jEmp);
        } catch (JSONException e) {

        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userrole_menu.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    public void getUserVolley(String url, final JSONObject json)
    {
        Log.d("---url-----", "---"+url);
        Log.d("555555", "00000000"+json.toString());

        //Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_LONG).show();
        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());
                        dialog_progress.hide();
                        Log.d("----getUserVolley-----", "---"+response.toString());
                        try {
                            JSONArray new_array1;
                            List<String> idL = new ArrayList<String>();
                            List<String> eNameL = new ArrayList<String>();
                            //List<String> jobroleL = new ArrayList<String>();
                            List<String> implementsL = new ArrayList<String>();
                            List<String> storeforecastL = new ArrayList<String>();

                            new_array1 = response.getJSONArray("user_details");
                            Log.d(" Array", " : " + new_array1);

                            for (int i = 0, count = new_array1.length(); i < count; i++){
                                 idS = new_array1.getJSONObject(i).getString("employeeid");
                                String usernameS = new_array1.getJSONObject(i).getString("username");
                                //String jobroleS = new_array1.getJSONObject(i).getString("jobrole");
                                String usermenuS = new_array1.getJSONObject(i).getString("usermenus");
                                idL.add(idS);
                                eNameL.add(usernameS);
                                //jobroleL.add(jobroleS);
                                usermenuL.add(usermenuS);
                                System.out.println("new size: " + usermenuL.size() );

                                //implementsL.add(implementS);
                                //storeforecastL.add(storeforecast);
                            }

                            idA = new String[idL.size()];
                            eNameA = new String[eNameL.size()];
                            //eRoleA = new String[jobroleL.size()];
                            usermenuA = new String[usermenuL.size()];
                           // implementA = new String[implementsL.size()];
                           // storeforecastA = new String[storeforecastL.size()];


                            for (int l = 0; l < usermenuL.size(); l++) {

                               // idA[l] = idL.get(l);
                               // eid = idA[l];
                                //eNameA[l] = eNameL.get(l);
                                //eRoleA[l] = jobroleL.get(l);
                                usermenuA[l] = usermenuL.get(l);
                               // implementA[l] = implementsL.get(l);
                               // storeforecastA[l] = storeforecastL.get(l);

                               // Log.d("id ", idA[l]);
                                //Log.d("eName ", eNameA[l]);
                                //Log.d("eRole ", eRoleA[l]);
                                Log.d("usermenuA ", usermenuA[l]);
                               // Log.d("implementA ", implementA[l]);
                               // Log.d("storeforecastA ", storeforecastA[l]);

                            }

                            Adapter_userrole reqAdapter = new Adapter_userrole(userrole_menu.this,usermenuA);
                            listrole.setAdapter(reqAdapter);


                            //Toast.makeText(getApplicationContext(), errorDesc, Toast.LENGTH_LONG).show();
                            //LIntent intentog.d("usernameS","" + idS+"--" + usernameS);

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
            }
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                //headers.put("Content-Type","application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                //return (headers != null || headers.isEmpty()) ? headers : super.getHeaders();
                return headers;
            }

//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("userid", txtemailS);
//                params.put("password", txtpassS);
//                params.put("jobrole", JobRoleS);
//                return params;
//            }
        };

        // Adding request to request queue
        jsonObjReq.setTag("");
        addToRequestQueue(jsonObjReq);
//        AppController.getInstance().addToRequestQueue(jsonObjReq,tag_json_obj);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (sch_RequestQueue == null) {

            sch_RequestQueue = Volley.newRequestQueue(getApplicationContext());

        }
        sch_RequestQueue.add(req);
        //getRequestQueue().add(req);
    }
}
