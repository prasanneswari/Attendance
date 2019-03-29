package com.jts.root.attendance;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Sales_Incharge extends AppCompatActivity {
    Button getdata,getlocation,logout,newtask,adminview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales__incharge);
        getdata=(Button)findViewById(R.id.getdataid);
        newtask=(Button)findViewById(R.id.newtaskid);
        adminview=(Button)findViewById(R.id.adminviewid);

        getlocation=(Button)findViewById(R.id.lastlocationid);


        getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sales_Incharge.this, Get_Cus_Log.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        newtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sales_Incharge.this, Admin_NewTask.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        adminview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sales_Incharge.this, Admin_View.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup();
                /*Intent intent = new Intent(Sales_Incharge.this, Loctaion_webview.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });

    }
    public void popup() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Sales_Incharge.this);

        // Setting Dialog Title
        alertDialog.setTitle("Attendance");

        // Setting Dialog Message
        alertDialog.setMessage("Not yet implemeting");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        /*alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });*/

        // Showing Alert Message
        alertDialog.show();
    }
}
