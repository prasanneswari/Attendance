package com.jts.root.attendance;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Login extends AppCompatActivity {

    static String usernameS, pwdS, eid1, sessionid;
    Intent intent;
    Dialog popup;
    String jsonS, jES, JobRoleS, url1;
    private ProgressDialog dialog_progress ;
    AlertDialog.Builder builderLoading;
    JSONObject rmdt,jEmp = null;
    com.android.volley.RequestQueue sch_RequestQueue;
     EditText usernameE,pwdE;
    Button Btnlogin;
    TextView newuserT;

    //static String domain_name = "cld003.jts-prod.in";  //cloud_3
     static String domain_name = "203.109.110.192";  //cloud_3
    static String port = "20105";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameE = (EditText) findViewById(R.id.usernameid);
        pwdE = (EditText) findViewById(R.id.pwdid);
        Btnlogin = (Button) findViewById(R.id.btnid);
        newuserT = (TextView) findViewById(R.id.newpwdid);
        dialog_progress = new ProgressDialog(Login.this);
        builderLoading = new AlertDialog.Builder(Login.this);
        newuserT.setPaintFlags(newuserT.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //startService(new Intent(this,Notifications.class));//notification mesg

        Btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usernameE.getText().toString().trim().length()==0){
                    usernameE.setError("Username is not entered");
                    usernameE.requestFocus();
                }
                if(pwdE.getText().toString().trim().length()==0){
                    pwdE.setError("Password is not entered");
                    pwdE.requestFocus();
                }
               /* else {
                    Intent intent = new Intent(Login.this, Job_Sheet.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }*/


                usernameS = usernameE.getText().toString();
                pwdS= pwdE.getText().toString();
                jsonS = "{\"username\":\""+usernameS+"\",\"password\":\""+pwdS+"\"}";
                jES = "{\"username\":\""+usernameS+"\",\"password\":\"" + pwdS + "\"}";
                Log.d("-jsnresponse add---",""+jsonS);
                String urlrs ="http://"+domain_name+":"+port+"/InventoryApp/login/";
                //url1 ="http://cld003.jts-prod.in:20105/InventoryApp/login/";

                try {

                    rmdt = new JSONObject(jsonS);
                    jEmp = new JSONObject(jES);
                    dialog_progress.setMessage("connecting ...");
                    dialog_progress.show();
                    //JSONSenderVolley1(urlrs,jsonS);
                    JSONSenderVolley(urlrs, rmdt);

                }
                catch (JSONException e) {

                }
                Log.d("-jsnresponse enter---", "" + jsonS);

            }
        });

        ////////////////////////////////////////////////////////////
        try {
            TrustManager[] victimizedManager = new TrustManager[]{

                    new X509TrustManager() {

                        public X509Certificate[] getAcceptedIssuers() {

                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];

                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, victimizedManager, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void JSONSenderVolley(String urlrs, final JSONObject json)
    {
        Log.d("---url-----", "---"+urlrs);
        Log.d("555555", "00000000"+json.toString());
        //dialog_progress.hide();
        //Toast.makeText(getApplicationContext(), json.toString(), Toast.LENGTH_LONG).show();
        //R
        // equestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                urlrs, json,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(" ", response.toString());

                        /*builderLoading.setNegativeButton("", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                            }
                        });*/
                        Log.d("----JSONSenderVolley--", "---"+response.toString());

                        try {
                            String errorCode = response.getString("error_code");
                            String errorDesc = response.getString("error_desc");
                            sessionid = response.getString("sessionid");
                            //Log.d("errorCode","" + errorCode+"--" + errorDesc);

                            /*if (dialog_progress.isShowing()) {
                                dialog_progress.dismiss();

                            }*/
                            if (errorCode.contentEquals("1")){

                                //getUserVolley(url1, jEmp);
                                intent = new Intent(Login.this, userrole_menu.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }
                            else {
                                //Toast.makeText(getApplicationContext(), errorDesc, Toast.LENGTH_LONG).show();builderLoading
                                Log.d("errorCode","" + errorCode+"--" + errorDesc);
                                //builderLoading.setMessage(errorDesc);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
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
