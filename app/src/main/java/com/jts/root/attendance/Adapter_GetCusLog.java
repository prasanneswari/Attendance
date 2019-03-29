package com.jts.root.attendance;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adapter_GetCusLog extends ArrayAdapter<String> {

    private Context context;
    private String[] cname;
    private String[] ename;
    private String[] status;
    private String[] visitdate;
    private String[] distravel;


    public static String lID;
    public static String lReqdate;
    public static String lDuedate;
    public static String lStatus;
    TextView cnameT,enameT,statusT,visitdataT,distanceT;

    public Adapter_GetCusLog(Context context, String[] cnameS, String[] enameS, String[] statusS, String[] visitdataS,String[] distravelS) {

        super(context, R.layout.activity_get__cus__log, cnameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.cname = cnameS;
        this.ename = enameS;
        this.status = statusS;
        this.visitdate = visitdataS;
        this.distravel = distravelS;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter__get_cus_log, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
        cnameT = (TextView) rowView.findViewById(R.id.cnameid);
        enameT = (TextView) rowView.findViewById(R.id.enameid);
        statusT = (TextView) rowView.findViewById(R.id.statusid);
        visitdataT = (TextView) rowView.findViewById(R.id.visitdateid);
        distanceT = (TextView) rowView.findViewById(R.id.distravel);

        try {

            //Assigning values from array to individual layouts in list view
            cnameT.setText(cname[position]);
            enameT.setText(ename[position]);
            statusT.setText(status[position]);
            visitdataT.setText(visitdate[position]);
            distanceT.setText(distravel[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Purchase_products.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                lID = purid[position];
                lReqdate = purReqdate[position];
                lDuedate = purDuedate[position];
                lStatus = purStatus[position];

                Log.d("Location" ," Id1111 :" + lID);
                Log.d("Location" ," Reqdate2222 :" + lReqdate);
                Log.d("Location" ," Duedate33333 :" + lDuedate);
                Log.d("Location" ," Status44444 :" + lStatus);

            }
        });*/

        return rowView;
    }
}
