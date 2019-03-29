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

public class Adapter_get_notes extends ArrayAdapter<String> {
    private Context context;
    private String[] Ncusname;
    private String[] Nfullname;
    private String[] Ntimestamp;
    private String[] Nnotes;

    public static String lcusname;
    public static String lfullname;
    public static String lcuscontact;
    public static String lapntdate;
    public static String lvisitdate;

    public static String lnotes;
    TextView NcusnameT,NfullnameT,NtimestampT,NnotesT;

    public Adapter_get_notes(Context context, String[] NcusnameS, String[] NfullnameS, String[] NtimestampS, String[] NnotesS) {

        super(context, R.layout.activity_get__notes, NcusnameS);

        //Assinging the 'RequisitionData' array values to the local arrays inside adapter

        this.context = context;
        this.Ncusname = NcusnameS;
        this.Nfullname = NfullnameS;
        this.Ntimestamp = NtimestampS;
        this.Nnotes = NnotesS;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_get_notes, parent, false);  //Setting content view of xml
        // ImageView imageView=(ImageView)rowView.findViewById(R.id.image);
        //Assigning IDs from xml
        NcusnameT = (TextView) rowView.findViewById(R.id.Ncusname);
        NfullnameT = (TextView) rowView.findViewById(R.id.Nfullname);
        NtimestampT = (TextView) rowView.findViewById(R.id.Ntimestamp);
        NnotesT = (TextView) rowView.findViewById(R.id.Nnotes);

        try {

            //Assigning values from array to individual layouts in list view
            NcusnameT.setText(Ncusname[position]);
            NfullnameT.setText(Nfullname[position]);
            NtimestampT.setText(Ntimestamp[position]);
            NnotesT.setText(Nnotes[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        /*rowView.setOnClickListener(new View.OnClickListener() {
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

                Log.d("Location" ," ltcame1111 :" + ltcame);
                Log.d("Location" ," ltstatusname2222 :" + ltstatusname);
                Log.d("Location" ," lDtapntdate33333 :" + lDtapntdate);
                Log.d("Location" ," lStcmpltdate44444 :" + lStcmpltdate);
                Log.d("Location" ," ltapnttype55555 :" + ltapnttype);

            }
        });*/
return rowView;
}
}
