package com.jts.root.attendance;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adapter_Task extends ArrayAdapter<String> {

    private Context context;
    private String[] AssignId;
    private String[] AssignedTo;
    private String[] appointmentType;
    private String[] appointmentdate;
    private String[] completeby;
    private String[] customerName;


    public static String lID;
    public static String lReqdate;
    public static String lDuedate;
    public static String lStatus;
    TextView AssignIdT,AssignedToT,appointmentTypeT,appointmentdateT,completebyT,customerNameT;

    public Adapter_Task(Context context, String[] AssignIdS, String[] AssignedToS, String[] appointmentTypeS, String[] appointmentdateS,String[] completebyS,String[] customerNameS) {

        super(context, R.layout.activity_admin__view, AssignIdS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.AssignId = AssignIdS;
        this.AssignedTo = AssignedToS;
        this.appointmentType = appointmentTypeS;
        this.appointmentdate = appointmentdateS;
        this.completeby = completebyS;
        this.customerName = customerNameS;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_task, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
        AssignIdT = (TextView) rowView.findViewById(R.id.assignid);
        AssignedToT = (TextView) rowView.findViewById(R.id.assignto);
        appointmentTypeT = (TextView) rowView.findViewById(R.id.apntype);
        appointmentdateT = (TextView) rowView.findViewById(R.id.apntdate);
        completebyT = (TextView) rowView.findViewById(R.id.apntcmpltby);
        customerNameT = (TextView) rowView.findViewById(R.id.cusname);

        try {

            //Assigning values from array to individual layouts in list view
            AssignIdT.setText(AssignId[position]);
            AssignedToT.setText(AssignedTo[position]);
            appointmentTypeT.setText(appointmentType[position]);
            appointmentdateT.setText(appointmentdate[position]);
            completebyT.setText(completeby[position]);
            customerNameT.setText(customerName[position]);

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
