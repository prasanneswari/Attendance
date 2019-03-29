package com.jts.root.attendance;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Employee_Details extends AppCompatActivity {

    EditText NameE,MobileE,EmailE;
    Spinner RoleS,SpecialS;
    Button SaveB;
    String[] role={"implimentation lead","implimentation","sales","sales lead"};
    String[] special={"sales","wiring","installation","electrical","implimentation"};
    RequestQueue sch_RequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee__details);
        NameE=(EditText)findViewById(R.id.nameid);
        MobileE=(EditText)findViewById(R.id.mobileid);
        EmailE=(EditText)findViewById(R.id.mailid);
        RoleS=(Spinner) findViewById(R.id.roleid);
        SpecialS=(Spinner) findViewById(R.id.specialid);
        SaveB=(Button) findViewById(R.id.saveid);
        ArrayAdapter<String> RoleA= new ArrayAdapter<String>(Employee_Details.this,android.R.layout.simple_spinner_item, role);
        RoleA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RoleS.setAdapter(RoleA);

        ArrayAdapter<String> SpecialA= new ArrayAdapter<String>(Employee_Details.this,android.R.layout.simple_spinner_item, special);
        SpecialA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpecialS.setAdapter(SpecialA);

        SaveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void name_values(){
        String enameS = "{\"username\":\"admin\",\"password\":\"admin\"}";
        //String add_posts = "{\"orderid\":\"" + lID + "\"}";
        Log.d("jsnresponse enameS", "---" + enameS);
        //trigger[asycount]=new SendJsonDataToServer1().execute(String.valueOf(water_status));
        //String room_ssid_url = "http://cld003.jts-prod.in:5906/AssetTrackerApp/get_rooms_ssid/";
        String save_url = "\n" + "http://cld003.jts-prod.in:5909/DimmingLightApp/add_modes/";
        // String urlrs= "https://jtsha.in/service/validate_web";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(enameS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + enameS);
        JSONSenderVolley(save_url, lstrmdt);
    }
    public void JSONSenderVolley(String save_url, final JSONObject json)
    {
        Log.d("save_url-", "---"+save_url);
        Log.d("555555", "00000000"+json.toString());
        //RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                save_url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
/*
                        Log.d(Bitmap.Config.TAG, response.toString());
*/
                        Log.d("----pur_url values-----", "---"+response.toString());
                        //getval_request(response);
                        //pur_request(response);
                        try {
                            int login_code = response.getInt("error_code");
                            String er_discp=response.getString("error_desc");

                            String[] separated = er_discp.split("=");
                            if(login_code==0){
                                Toast.makeText(getApplicationContext(), separated[1], Toast.LENGTH_LONG).show();
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
}
