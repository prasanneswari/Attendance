package com.jts.root.attendance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;


public class Adapter_userrole extends ArrayAdapter<String> {

    private Context context;
    private String[] id;
    private String[] NSName,NSimplement,NSStoreforecast;
    public static String sID, sName,sForecast,sStoreforecast;
    TextView NSIdT,NSNameT;
    String menu;
    Button roles,implementB,storeforecastB,btn1;

    public Adapter_userrole(Context context, String[] NSNameS) {

        super(context, R.layout.activity_userrole_menu, NSNameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter
        this.context = context;
        this.NSName = NSNameS;
       // this.NSimplement = NSimplementS;
       // this.NSStoreforecast = NSStoreforecastS;

    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_adapter_userrole, parent, false);  //Setting content view of xml
        //Assigning IDs from xml
        //NSNameT = (TextView) rowView.findViewById(R.id.NSName);
        roles = (Button) rowView.findViewById(R.id.jobRole);
        //implementB = (Button) rowView.findViewById(R.id.implementid);

        // forecastB = (Button) rowView.findViewById(R.id.forecast);
       // storeforecastB = (Button) rowView.findViewById(R.id.storeforecast);


        try {

            //Assigning values from array to individual layouts in list view
            //NSNameT.setText(NSName[position]);
            roles.setText(NSName[position]);
           // implementB.setText(NSimplement[position]);
           // storeforecastB.setText(NSStoreforecast[position]);


            Log.d("role " ," Name :" + NSName[position]);
            //Log.d("implementB " ," Name :" + NSimplement[position]);
           // Log.d("NSStoreforecast " ," Name :" + NSStoreforecast[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Log.d("selected Status " ," Name :" + sName);
            }
        });*/

        /*forecastB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), User_Forecast.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        storeforecastB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Store_Forecast.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });*/
        roles.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (NSName[position].contentEquals("Forecast Admin")) {
                    Intent intent = new Intent(getContext(), Sales_Incharge.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                } else if (NSName[position].contentEquals("Forecasts")) {
                    Intent intent = new Intent(getContext(), Job_Sheet.class); //UserMain
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("requisition")){
                    popup2();
        }

                else if (NSName[position].contentEquals("Implementation")){
                    Intent intent = new Intent(getContext(), Implimentation.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
               /* else if (NSName[position].contentEquals("issue")){
                    Intent intent = new Intent(getContext(), ConsumableIssues.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("locations")){
                    Intent intent = new Intent(getContext(), Locations.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("positions")){
                    Intent intent = new Intent(getContext(), Positions.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("products")){
                    Intent intent = new Intent(getContext(), Products.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("warehouse")){
                    Intent intent = new Intent(getContext(), Warehouses.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("purchasable")){
                    Intent intent = new Intent(getContext(), josh.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("Purchase")){
                    Intent intent = new Intent(getContext(), purchase_order.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                else if (NSName[position].contentEquals("admin")){
                    Intent intent = new Intent(getContext(), Admin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            */
            }
        });


        return rowView;
    }

    public void popup2(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Attendance");

        // set dialog message
        alertDialogBuilder
                .setMessage("GO TO YOUR INVENTORY_APP")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        //MainActivity.this.finish();
                    }
                });
                /*.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });*/

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}
