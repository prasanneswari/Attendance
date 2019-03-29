package com.jts.root.attendance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import static com.jts.root.attendance.Admin_View.Anotes;


public class Adapter_Admin_View extends ArrayAdapter<String> {
    private Context context;
    private String[] Acusname;
    private String[] Afullname;
    private String[] Acuscontact;
    private String[] Aapntdate;
    private String[] Avisitdate;
    //private String[] notes;
    public static String Alcusname;
    public static String Alfullname;
    public static String Alcuscontact;
    public static String Alapntdate;
    public static String Alvisitdate;
    public static String Alnotes;

    TextView AcusnameT,AfullnameT,AcuscontactT,AapntdateT,AvisitdateT,AnotesT;

    public Adapter_Admin_View(Context context, String[] AcusnameS, String[] AfullnameS, String[] AcuscontactS, String[] AapndateS,String[] AvisitdateS) {

        super(context, R.layout.activity_admin__view, AcusnameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.Acusname = AcusnameS;
        this.Afullname = AfullnameS;
        this.Acuscontact = AcuscontactS;
        this.Aapntdate = AapndateS;
        this.Avisitdate = AvisitdateS;
        //this.notes = notesS;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter__admin__view, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
        AcusnameT = (TextView) rowView.findViewById(R.id.cusname);
        AfullnameT = (TextView) rowView.findViewById(R.id.fullname);
        AcuscontactT = (TextView) rowView.findViewById(R.id.cuscontact);
        AapntdateT = (TextView) rowView.findViewById(R.id.apndate);
        AvisitdateT = (TextView) rowView.findViewById(R.id.visitdate);
        AnotesT = (TextView) rowView.findViewById(R.id.note);
        AnotesT.setPaintFlags(AnotesT.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        try {

            //Assigning values from array to individual layouts in list view
            AcusnameT.setText(Acusname[position]);
            AfullnameT.setText(Afullname[position]);
            AcuscontactT.setText(Acuscontact[position]);
            AapntdateT.setText(Aapntdate[position]);
            AvisitdateT.setText(Avisitdate[position]);
           // notesT.setText(notes[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alcusname = Acusname[position];
                Alfullname = Afullname[position];
                Alcuscontact = Acuscontact[position];
                Alapntdate = Aapntdate[position];
                Alvisitdate = Avisitdate[position];
                Alnotes = Anotes[position];

                Log.d("Location" ," lcusname1111 :" + Alcusname);
                Log.d("Location" ," lfullname2222 :" + Alfullname);
                Log.d("Location" ," lcuscontact33333 :" + Alcuscontact);
                Log.d("Location" ," lapntdate44444 :" + Alapntdate);
                Log.d("Location" ," lvisitdate5555 :" + Alvisitdate);
                Log.d("Location" ," lnotes66666 :" + Alnotes);
                showPopup(Alnotes);

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
