package com.jts.root.attendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jts.root.attendance.Implimentation.posfid;
import static com.jts.root.attendance.Login.domain_name;
import static com.jts.root.attendance.Login.port;
import static com.jts.root.attendance.userrole_menu.idS;

public class Get_Notes extends AppCompatActivity {

    Button cancleB;
    ListView noteslst;
    RequestQueue sch_RequestQueue;
    private ArrayAdapter<String> get_noteslst;
    String [] Notes,NotesId,customerName,forecastId,fullName,timestamp,userId;
    JSONArray new_array1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__notes);
        cancleB=(Button)findViewById(R.id.cancelid);
        noteslst=(ListView)findViewById(R.id.getnoteslst);
        getnotes_values();
        Log.d("foreeecateeeeeeeeeee", "---" + posfid);

        cancleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Get_Notes.this,Implimentation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    public void getnotes_values(){
        String notesS = "{\"username\":\"admin\",\"password\":\"admin\",\"userid\":\"" + idS + "\",\"forecastid\":\"" + posfid + "\"}";
        Log.d("jsnresponse roleS", "---" + notesS);
        String url = "http://"+domain_name+":"+port+"/InventoryApp/get_notes/";
        //String url = "http://cld003.jts-prod.in:20105/InventoryApp/get_notes/";
        JSONObject lstrmdt = null;
        // try {
        try {
            lstrmdt = new JSONObject(notesS);
        } catch (JSONException e) {

        }
        Log.d("jsnresponse....", "---" + notesS);
        // dialog_progress.setMessage("connecting ...");
        // dialog_progress.show();
        JSONSenderVolley(url, lstrmdt);
    }
    public void get_notes(JSONObject responseJSON){
        try {
             List<String> NotesL = new ArrayList<String>();
             List<String> NotesIdL = new ArrayList<String>();
            List<String> customerNameL = new ArrayList<String>();
            List<String> forecastIdL = new ArrayList<String>();
            List<String> fullNameL = new ArrayList<String>();
            List<String> imestampL = new ArrayList<String>();
            List<String> userIdL = new ArrayList<String>();
            try {
                new_array1 = responseJSON.getJSONArray("get_notes");
                //new_array1 = responseJSON.getJSONArray("fields");
                Log.d(" Array", " get notes list : " + new_array1);
                for (int i = 0, count = new_array1.length(); i < count; i++) {
                    String NotesS = new_array1.getJSONObject(i).getString("Notes");
                    String NotesIdS = new_array1.getJSONObject(i).getString("NotesId");
                    String customerNameS = new_array1.getJSONObject(i).getString("customerName");
                    String forecastIdS = new_array1.getJSONObject(i).getString("forecastId");
                    String fullNameS = new_array1.getJSONObject(i).getString("fullName");
                    String timestampS = new_array1.getJSONObject(i).getString("timestamp");
                    String userIdS = new_array1.getJSONObject(i).getString("userId");

                    NotesL.add(NotesS);
                    NotesIdL.add(NotesIdS);
                    customerNameL.add(customerNameS);
                    forecastIdL.add(forecastIdS);
                    fullNameL.add(fullNameS);
                    imestampL.add(timestampS);
                    userIdL.add(userIdS);
                }

                Notes = new String[NotesL.size()];
                NotesId = new String[NotesIdL.size()];
                customerName = new String[customerNameL.size()];
                forecastId = new String[forecastIdL.size()];
                fullName = new String[fullNameL.size()];
                timestamp = new String[imestampL.size()];
                userId = new String[userIdL.size()];

                for (int l = 0; l < userIdL.size(); l++) {
                    Notes[l] = NotesL.get(l);
                    NotesId[l] = NotesIdL.get(l);
                    customerName[l] = customerNameL.get(l);
                    forecastId[l] = forecastIdL.get(l);
                    fullName[l] = fullNameL.get(l);
                    timestamp[l] = imestampL.get(l);
                    userId[l] = userIdL.get(l);

                    Log.d("Notes ", Notes[l]);
                    Log.d("NotesId ", NotesId[l]);
                    Log.d("customerName ", customerName[l]);
                    Log.d("forecastId ", forecastId[l]);
                    Log.d("fullName ", fullName[l]);
                    Log.d("timestamp ", timestamp[l]);
                    Log.d("userId ", userId[l]);
                }
                Adapter_get_notes reqAdapter = new Adapter_get_notes(Get_Notes.this,customerName,fullName, timestamp, Notes);
                noteslst.setAdapter(reqAdapter);


            }
            catch (NullPointerException e){

            }
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
/*
                        Log.d(Bitmap.Config.TAG, response.toString());
*/
                        get_notes(response);
                        Log.d("----get notes values-----", "---"+response.toString());
                        /*
                        try {
                            int custom_code = response.getInt("error_code");
                            String er_discp=response.getString("error_desc");
                            String[] custom = er_discp.split("=");
                            if(custom_code==0){
                                Toast.makeText(getApplicationContext(), custom[1], Toast.LENGTH_LONG).show();
                            }else
                                Toast.makeText(getApplicationContext(), custom[1], Toast.LENGTH_LONG).show();

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
