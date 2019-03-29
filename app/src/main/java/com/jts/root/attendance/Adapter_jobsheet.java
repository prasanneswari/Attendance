package com.jts.root.attendance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import static com.jts.root.attendance.Adapeter_Implement_task.ltcame;
import static com.jts.root.attendance.Job_Sheet.notes;

public class Adapter_jobsheet extends ArrayAdapter<String> {

    private Context context;
    private String[] cusname;
    private String[] fullname;
    private String[] cuscontact;
    private String[] apntdate;
    private String[] visitdate;
    //private String[] notes;

    public static String lcusname;
    public static String lfullname;
    public static String lcuscontact;
    public static String lapntdate;
    public static String lvisitdate;

    public static String lnotes;

    TextView cusnameT,fullnameT,cuscontactT,apntdateT,visitdateT,notesT;

    public Adapter_jobsheet(Context context, String[] cusnameS, String[] fullnameS, String[] cuscontactS, String[] apndateS,String[] visitdateS) {

        super(context, R.layout.activity_job__sheet, cusnameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.cusname = cusnameS;
        this.fullname = fullnameS;
        this.cuscontact = cuscontactS;
        this.apntdate = apndateS;
        this.visitdate = visitdateS;
        //this.notes = noteS;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_jobsheet, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
        cusnameT = (TextView) rowView.findViewById(R.id.cusname);
        fullnameT = (TextView) rowView.findViewById(R.id.fullname);
        cuscontactT = (TextView) rowView.findViewById(R.id.cuscontact);
        apntdateT = (TextView) rowView.findViewById(R.id.apndate);
        visitdateT = (TextView) rowView.findViewById(R.id.visitdate);
        notesT = (TextView) rowView.findViewById(R.id.note);
        notesT.setPaintFlags(notesT.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        try {

            //Assigning values from array to individual layouts in list view
            cusnameT.setText(cusname[position]);
            fullnameT.setText(fullname[position]);
            cuscontactT.setText(cuscontact[position]);
            apntdateT.setText(apntdate[position]);
            visitdateT.setText(visitdate[position]);
           // notesT.setText(notes[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lcusname = cusname[position];
                lfullname = fullname[position];
                lcuscontact = cuscontact[position];
                lapntdate = apntdate[position];
                lvisitdate = visitdate[position];
                lnotes = notes[position];

                Log.d("Location" ," lcusname1111 :" + lcusname);
                Log.d("Location" ," lfullname2222 :" + lfullname);
                Log.d("Location" ," lcuscontact33333 :" + lcuscontact);
                Log.d("Location" ," lapntdate44444 :" + lapntdate);
                Log.d("Location" ," lvisitdate5555 :" + lvisitdate);
                Log.d("Location" ," lnotes66666 :" + lnotes);
                showPopup(lnotes);

            }
        });

        return rowView;
    }

    public void showPopup(String notes){

        Log.d("Location" ," notes@@@@@ :" + notes);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogLayout = inflater.inflate(R.layout.notes_view_popup,
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

        final Button btn = (Button) dialogLayout.findViewById(R.id.Naddbtn);
        final TextView textnotesT = (TextView) dialogLayout.findViewById(R.id.getnotesid);
        textnotesT.setMovementMethod(new ScrollingMovementMethod());
        textnotesT.setText(notes);

        builder.setView(dialogLayout);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //delgroup(grpnm);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
