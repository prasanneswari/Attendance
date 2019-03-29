package com.jts.root.attendance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adapeter_Implement_task extends ArrayAdapter<String> {

    private Context context;
    private String[] tcname;
    private String[] tstatusname;
    private String[] tapntdate;
    private String[] tcmpltdate;
    private String[] tapnttype;
    private String[] tforecast;



    public static String ltcame;
    public static String ltstatusname;
    public static String lDtapntdate;
    public static String lStcmpltdate;
    public static String ltapnttype;
    public static String lforecast;

    TextView tcnameT,tstatusnameT,tapntdateT,tcmpltdateT,tapnttypeT,tforecastT;

    public Adapeter_Implement_task(Context context,String[] tforecastS, String[] tcnameS, String[] tstatusnameS, String[] tapntdateS, String[] tcmpltdateS,String[] dtapnttypeS) {

        super(context, R.layout.activity_implimentation, tforecastS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.tforecast = tforecastS;
        this.tcname = tcnameS;
        this.tstatusname = tstatusnameS;
        this.tapntdate = tapntdateS;
        this.tcmpltdate = tcmpltdateS;
        this.tapnttype = dtapnttypeS;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapeter__implement_task, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml

        tcnameT = (TextView) rowView.findViewById(R.id.tcname);
        tstatusnameT = (TextView) rowView.findViewById(R.id.statusname);
        tapntdateT = (TextView) rowView.findViewById(R.id.tapntdate);
        tcmpltdateT = (TextView) rowView.findViewById(R.id.tcmpltdate);
        tapnttypeT = (TextView) rowView.findViewById(R.id.tapnttype);
        tforecastT = (TextView) rowView.findViewById(R.id.tfid);

        try {

            //Assigning values from array to individual layouts in list view
            tcnameT.setText(tcname[position]);
            tstatusnameT.setText(tstatusname[position]);
            tapntdateT.setText(tapntdate[position]);
            tcmpltdateT.setText(tcmpltdate[position]);
            tapnttypeT.setText(tapnttype[position]);
            tforecastT.setText(tforecast[position]);


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Userview_task.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                ltcame = tcname[position];
                ltstatusname = tstatusname[position];
                lDtapntdate = tapntdate[position];
                lStcmpltdate = tcmpltdate[position];
                ltapnttype = tapnttype[position];
                lforecast = tforecast[position];

                Log.d("Location" ," ltcame1111 :" + ltcame);
                Log.d("Location" ," ltstatusname2222 :" + ltstatusname);
                Log.d("Location" ," lDtapntdate33333 :" + lDtapntdate);
                Log.d("Location" ," lStcmpltdate44444 :" + lStcmpltdate);
                Log.d("Location" ," ltapnttype55555 :" + ltapnttype);
                Log.d("Location" ," lforecast66666 :" + lforecast);

            }
        });

        return rowView;
    }
}
