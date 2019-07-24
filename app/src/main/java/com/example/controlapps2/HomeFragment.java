package com.example.controlapps2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.controlapps2.Model.UserCum;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView volt,amp1, amp2, amp3,watt1,watt2,watt3,kwh1,kwh2,kwh3,tkwh,tbiaya,by1,by2,by3,tgl;
    LineChart chart;

    ImageView l1,l2,l3;

    FirebaseDatabase database;
    DatabaseReference reference,relayCheck,reference2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home,container,false);


        volt = (TextView)v.findViewById(R.id.nilaiVolt);
        amp1 = (TextView)v.findViewById(R.id.nilaiAmp1);
        amp2 = (TextView)v.findViewById(R.id.nilaiAmp2);
        amp3 = (TextView)v.findViewById(R.id.nilaiAmp3);
        watt1 = (TextView)v.findViewById(R.id.nilaiWatt1);
        watt2 = (TextView)v.findViewById(R.id.nilaiWatt2);
        watt3 = (TextView)v.findViewById(R.id.nilaiWatt3);
        kwh1 = (TextView)v.findViewById(R.id.nilaiKwh1);
        kwh2 = (TextView)v.findViewById(R.id.nilaiKwh2);
        kwh3 = (TextView)v.findViewById(R.id.nilaiKwh3);
        tkwh = (TextView)v.findViewById(R.id.tkwh);
        tbiaya = (TextView)v.findViewById(R.id.tbiaya);
        by1 = (TextView)v.findViewById(R.id.nilaiBiaya1);
        by2 = (TextView)v.findViewById(R.id.nilaiBiaya2);
        by3 = (TextView)v.findViewById(R.id.nilaiBiaya3);
        chart = (LineChart)v.findViewById(R.id.chart1);
        l1 = (ImageView)v.findViewById(R.id.iv1);
        l2 = (ImageView)v.findViewById(R.id.iv2);
        l3 = (ImageView)v.findViewById(R.id.iv3);
        tgl = (TextView)v.findViewById(R.id.tgl);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("sensorC");
        reference2 = database.getReference("kumData");

        relayCheck = FirebaseDatabase.getInstance().getReference("data").child("Relay");


        relayCheck.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String R1 = dataSnapshot.child("0").child("status").getValue(String.class);
                String R2 = dataSnapshot.child("1").child("status").getValue(String.class);
                String R3 = dataSnapshot.child("2").child("status").getValue(String.class);

                if (R1.equals("0")){
                    l1.setImageResource(R.drawable.ic_wb_incandescent_black_24dp);
                }
                if (R1.equals("1")) {
                    l1.setImageResource(R.drawable.ic_wb_incandescent_yellow_24dp);
                }
                if(R2.equals("0")){
                    l2.setImageResource(R.drawable.ic_wb_incandescent_black_24dp);
                }
                if(R2.equals("1")){
                    l2.setImageResource(R.drawable.ic_wb_incandescent_yellow_24dp);
                }
                if(R3.equals("0")){
                    l3.setImageResource(R.drawable.ic_wb_incandescent_black_24dp);
                }
                if(R3.equals("1")) {
                    l3.setImageResource(R.drawable.ic_wb_incandescent_yellow_24dp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query lastQuery = reference.orderByKey().limitToLast(1);
        lastQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Double v1 = dataSnapshot.child("tegangan").getValue(Double.class);
                Double ws1 = dataSnapshot.child("watt1").getValue(Double.class);
                Double ws2 = dataSnapshot.child("watt2").getValue(Double.class);
                Double ws3 = dataSnapshot.child("watt3").getValue(Double.class);
                Double a1 = ws1/v1;
                Double a2 = ws2/v1;
                Double a3 = ws3/v1;
                String volt1 = String.valueOf(String.format("%.2f", v1));
                String am1 = String.valueOf(String.format("%.3f", a1));
                String am2 = String.valueOf(String.format("%.3f", a2));
                String am3 = String.valueOf(String.format("%.3f", a3));
                String wa1 = String.valueOf(String.format("%.1f", ws1));
                String wa2 = String.valueOf(String.format("%.1f", ws2));
                String wa3 = String.valueOf(String.format("%.1f", ws3));
                volt.setText(volt1);
                amp1.setText(am1);
                amp2.setText(am2);
                amp3.setText(am3);
                watt1.setText(wa1);
                watt2.setText(wa2);
                watt3.setText(wa3);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Double v1 = dataSnapshot.child("tegangan").getValue(Double.class);
                Double ws1 = dataSnapshot.child("watt1").getValue(Double.class);
                Double ws2 = dataSnapshot.child("watt2").getValue(Double.class);
                Double ws3 = dataSnapshot.child("watt3").getValue(Double.class);
                Double a1 = ws1/v1;
                Double a2 = ws2/v1;
                Double a3 = ws3/v1;
                String volt1 = String.valueOf(String.format("%.2f", v1));
                String am1 = String.valueOf(String.format("%.3f", a1));
                String am2 = String.valueOf(String.format("%.3f", a2));
                String am3 = String.valueOf(String.format("%.3f", a3));
                String wa1 = String.valueOf(String.format("%.1f", ws1));
                String wa2 = String.valueOf(String.format("%.1f", ws2));
                String wa3 = String.valueOf(String.format("%.1f", ws3));
                volt.setText(volt1);
                amp1.setText(am1);
                amp2.setText(am2);
                amp3.setText(am3);
                watt1.setText(wa1);
                watt2.setText(wa2);
                watt3.setText(wa3);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Query kwhQuery = reference.orderByKey().limitToLast(1);
        kwhQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Double w1 = ds.child("watthour1").getValue(Double.class);
                    Double w2 = ds.child("watthour2").getValue(Double.class);
                    Double w3 = ds.child("watthour3").getValue(Double.class);
                    Double whs1 = ((w1*0.0013888888888889)/1000); // 0.001388 adalah 5 detik / 3600
                    Double whs2 = ((w2*0.0013888888888889)/1000);
                    Double whs3 = ((w3*0.0013888888888889)/1000);
                    Double tkwhs = whs1+whs2+whs3;
                    Double cost1 = whs1*1467.28;
                    Double cost2 = whs2*1467.28;
                    Double cost3 = whs3*1467.28;
                    Double tcost = tkwhs*1467.28; //1.467,28/kWH merupakan tarif untuk rumah tangga 1300 VA keatas
                    String wh1 = String.valueOf(String.format("%.2f", whs1));
                    String wh2 = String.valueOf(String.format("%.2f", whs2));
                    String wh3 = String.valueOf(String.format("%.2f", whs3));
                    String costs1 = String.valueOf(String.format("%.2f", cost1));
                    String costs2 = String.valueOf(String.format("%.2f", cost2));
                    String costs3 = String.valueOf(String.format("%.2f", cost3));
                    String totkwh = String.valueOf(String.format("%.2f", tkwhs));
                    String totcost = String.valueOf(String.format("%.2f", tcost));
                    kwh1.setText(wh1);
                    kwh2.setText(wh2);
                    kwh3.setText(wh3);
                    by1.setText("Rp. " +costs1);
                    by2.setText("Rp. " +costs2);
                    by3.setText("Rp. " +costs3);
                    tkwh.setText(totkwh);
                    tbiaya.setText("Rp. " +totcost);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query lqry = reference.orderByKey().limitToLast(10);
        lqry.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                final ArrayList<UserCum> stu = new ArrayList<UserCum>();
                final ArrayList<String> ts = new ArrayList<String>();
                final ArrayList<Float> wt1 = new ArrayList<Float>();
                final ArrayList<Float> wt2 = new ArrayList<Float>();
                final ArrayList<Float> wt3 = new ArrayList<Float>();
                ArrayList<Entry> entries1 = new ArrayList<>();
                ArrayList<Entry> entries2 = new ArrayList<>();
                ArrayList<Entry> entries3 = new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Double wH1 = ds.child("watthour1").getValue(Double.class);
                    Double wH2 = ds.child("watthour2").getValue(Double.class);
                    Double wH3 = ds.child("watthour3").getValue(Double.class);
                    Long ts1 = ds.child("timestamp").getValue(Long.class);
                    Double kWh1 = ((wH1*0.0013888888888889)/1000)*1467.28;
                    Double kWh2 = ((wH2*0.0013888888888889)/1000)*1467.28;
                    Double kWh3 = ((wH3*0.0013888888888889)/1000)*1467.28;
                    Float wat1 = kWh1.floatValue();
                    Float wat2 = kWh2.floatValue();
                    Float wat3 = kWh3.floatValue();
                    String date = df.format(ts1);
                    UserCum user = new UserCum(wat1,wat2,wat3,date);
                    stu.add(user);
                    ts.add(user.getDate1());
                    wt1.add(user.getwHour1());
                    wt2.add(user.getwHour2());
                    wt3.add(user.getwHour3());
                }
                Object[] objNames = ts.toArray();
                Object[] objNames1 = wt1.toArray();
                Object[] objNames2 = wt2.toArray();
                Object[] objNames3 = wt3.toArray();
                Integer [] x = new Integer[objNames1.length];
                for(int i = 0; i<stu.size();i++){
                    UserCum currentMyEntry = stu.get(i);
                    Entry currentEntry1 = new Entry();
                    Entry currentEntry2 = new Entry();
                    Entry currentEntry3 = new Entry();
                    currentEntry1.setX(x[i]=i);
                    currentEntry1.setY(currentMyEntry.getwHour1());
                    entries1.add(currentEntry1);
                    currentEntry2.setX(x[i]=i);
                    currentEntry2.setY(currentMyEntry.getwHour2());
                    entries2.add(currentEntry2);
                    currentEntry3.setX(x[i]=i);
                    currentEntry3.setY(currentMyEntry.getwHour3());
                    entries3.add(currentEntry3);
                }
                LineDataSet setComp1 = new LineDataSet(entries1, "Unit 1");
                LineDataSet setComp2 = new LineDataSet(entries2, "Unit 2");
                LineDataSet setComp3 = new LineDataSet(entries3, "Unit 3");
                setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
                setComp1.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
                setComp1.setValueTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
                setComp2.setColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
                setComp2.setValueTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                setComp3.setAxisDependency(YAxis.AxisDependency.LEFT);
                setComp3.setColor(ResourcesCompat.getColor(getResources(), R.color.colorBlue, null));
                setComp3.setValueTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                dataSets.add(setComp1);
                dataSets.add(setComp2);
                dataSets.add(setComp3);
                LineData data1 = new LineData(dataSets);
                XAxis xAxis = chart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                final String[] mMonth = Arrays.copyOf(objNames, objNames.length, String[].class);

                IAxisValueFormatter formatter = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return mMonth[(int) value];
                    }
                };

                xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                xAxis.setValueFormatter(formatter);

                //***
                // Controlling right side of y axis
                YAxis yAxisRight = chart.getAxisRight();
                yAxisRight.setEnabled(false);

                //***
                // Controlling left side of y axis
                YAxis yAxisLeft = chart.getAxisLeft();
                yAxisLeft.setGranularity(1f);

                // Setting Data
                LineData data2 = new LineData(dataSets);
                chart.getDescription().setEnabled(false);
                chart.setData(data2);
                chart.invalidate(); // refresh
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                Date cal = Calendar.getInstance().getTime();
                String date = sdf.format(cal);
                tgl.setText(date);
            }
            public void onFinish() {

            }
        };
        newtimer.start();

        return v;
    }

}
