package com.example.controlapps2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlapps2.Adapter.MultiColumn_ListAdapter;
import com.example.controlapps2.Adapter.MultiColumn_ListAdapter2;
import com.example.controlapps2.Adapter.MultiColumn_ListAdapter3;
import com.example.controlapps2.Adapter.MultiColumn_ListAdapter4;
import com.example.controlapps2.Model.User;
import com.example.controlapps2.Model.User2;
import com.example.controlapps2.Model.User3;
import com.example.controlapps2.Model.User4;
import com.example.controlapps2.Model.UserCum;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecordFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference reference,reference2,reference3;
    BarChart barChart;
    PieChart pieChart;
    TextView tbys,bys1,bys2,bys3,tkws,tkws1,tkws2,tkws3,tbyshr,byshr1,byshr2,byshr3,tkwshr,tkwshr1,tkwshr2,tkwshr3;
    ListView listView,listView2, listView3, listView4;
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Long yesterday() {

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yest = cal.getTime();
        long millis = yest.getTime();
        return millis;
    };

    private Long today() {

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        Date yest = cal.getTime();
        long millis = yest.getTime();
        return millis;
    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_record,container,false);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("sensorC");
        reference2 = database.getReference("kumData");
        reference3 = database.getReference("kumDataHour");
        listView = (ListView)v.findViewById(R.id.listView1);
        listView2 = (ListView)v.findViewById(R.id.listView2);
        barChart = (BarChart)v.findViewById(R.id.chart2);
        tbys = (TextView)v.findViewById(R.id.totby);
        bys1 = (TextView)v.findViewById(R.id.tby1);
        bys2 = (TextView)v.findViewById(R.id.tby2);
        bys3 = (TextView)v.findViewById(R.id.tby3);
        tkws = (TextView)v.findViewById(R.id.totkwh);
        tkws1 = (TextView)v.findViewById(R.id.tkwh1);
        tkws2 = (TextView)v.findViewById(R.id.tkwh2);
        tkws3 = (TextView)v.findViewById(R.id.tkwh3);
        listView3 = (ListView)v.findViewById(R.id.listView3);
        listView4 = (ListView)v.findViewById(R.id.listView4);
        tbyshr = (TextView)v.findViewById(R.id.totbyhr);
        byshr1 = (TextView)v.findViewById(R.id.tbyhr1);
        byshr2 = (TextView)v.findViewById(R.id.tbyhr2);
        byshr3 = (TextView)v.findViewById(R.id.tbyhr3);
        tkwshr = (TextView)v.findViewById(R.id.totkwhhr);
        tkwshr1 = (TextView)v.findViewById(R.id.tkwhhr1);
        tkwshr2 = (TextView)v.findViewById(R.id.tkwhhr2);
        tkwshr3 = (TextView)v.findViewById(R.id.tkwhhr3);
        Spinner tglSpinner = (Spinner) v.findViewById(R.id.spdate);

        Query lQuery = reference2.orderByKey().limitToLast(15);
        Query lQuery2 = reference2.orderByKey().limitToLast(30);
        //Query lQuery = reference2.orderByKey().limitToLast(15);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> tanggal = new ArrayList<String>();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Long ts = ds.child("timestamp").getValue(Long.class);
                    Date date = new java.util.Date(ts*1000L);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.DATE,1);
                    calendar.add(Calendar.HOUR,-7);
                    Date tgl = calendar.getTime();
                    String datets = sdf1.format(tgl);
                    tanggal.add(datets);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tanggal);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Collections.reverse(tanggal);
                tglSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        lQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User> data = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Double watt1 = ds.child("watthour1").getValue(Double.class);
                    Double watt2 = ds.child("watthour2").getValue(Double.class);
                    Double watt3 = ds.child("watthour3").getValue(Double.class);
                    Long ts = ds.child("timestamp").getValue(Long.class);
                    Date date = new java.util.Date(ts*1000L);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.HOUR,-7);
                    Date tgl = calendar.getTime();
                    String datets = sdf.format(tgl);
                    Double kWh1 = ((watt1*0.0013888888888889)/1000);
                    Double kWh2 = ((watt2*0.0013888888888889)/1000);
                    Double kWh3 = ((watt3*0.0013888888888889)/1000);
                    Double tKwh = kWh1+kWh2+kWh3;
                    String tkwh1 = String.valueOf(String.format("%.2f", tKwh));
                    String am1 = String.valueOf(String.format("%.3f", kWh1));
                    String am2 = String.valueOf(String.format("%.3f", kWh2));
                    String am3 = String.valueOf(String.format("%.3f", kWh3));
                    User user = new User(tkwh1,am1,am2,am3,datets);
                    data.add(0,user);
                }
                MultiColumn_ListAdapter adapter = new MultiColumn_ListAdapter(getActivity(),R.layout.list_adapter_view,data);
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.smoothScrollToPosition(0);
                    }
                });
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        lQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User2> data = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Double watt1 = ds.child("watthour1").getValue(Double.class);
                    Double watt2 = ds.child("watthour2").getValue(Double.class);
                    Double watt3 = ds.child("watthour3").getValue(Double.class);
                    Long ts = ds.child("timestamp").getValue(Long.class);
                    Date date = new java.util.Date(ts*1000L);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.HOUR,-7);
                    Date tgl = calendar.getTime();
                    String datets = sdf.format(tgl);
                    Double by1 = ((watt1*0.0013888888888889)/1000)*1467.28;
                    Double by2 = ((watt2*0.0013888888888889)/1000)*1467.28;
                    Double by3 = ((watt3*0.0013888888888889)/1000)*1467.28;
                    Double tby = by1+by2+by3;
                    String tkwh1 = String.valueOf(String.format("%.2f", tby));
                    String am1 = String.valueOf(String.format("%.3f", by1));
                    String am2 = String.valueOf(String.format("%.3f", by2));
                    String am3 = String.valueOf(String.format("%.3f", by3));
                    User2 user = new User2(tkwh1,am1,am2,am3,datets);
                    data.add(0,user);
                }
                MultiColumn_ListAdapter2 adapter = new MultiColumn_ListAdapter2(getActivity(),R.layout.list_adapter_view2,data);
                listView2.post(new Runnable() {
                    @Override
                    public void run() {
                        listView2.smoothScrollToPosition(0);
                    }
                });
                listView2.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        lQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User2> data = new ArrayList<>();
                Double count1 = 0.0;
                Double count2 = 0.0;
                Double count3 = 0.0;
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Double watt1 = ds.child("watthour1").getValue(Double.class);
                    Double watt2 = ds.child("watthour2").getValue(Double.class);
                    Double watt3 = ds.child("watthour3").getValue(Double.class);
                    Long ts = ds.child("timestamp").getValue(Long.class);
                    Date date = new java.util.Date(ts*1000L);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.HOUR,-7);
                    Date tgl = calendar.getTime();
                    String datets = sdf.format(tgl);
                    count1 +=((watt1*0.0013888888888889)/1000);
                    count2 +=((watt2*0.0013888888888889)/1000);
                    count3 +=((watt3*0.0013888888888889)/1000);
                    Double by1 = (count1*1467.28);
                    Double by2 = (count2*1467.28);
                    Double by3 = (count3*1467.28);
                    Double tby = by1+by2+by3;
                    Double tkw = count1+count2+count3;
                    String tby1 = String.valueOf(String.format("%.2f", tby));
                    String am1 = String.valueOf(String.format("%.2f", by1));
                    String am2 = String.valueOf(String.format("%.2f", by2));
                    String am3 = String.valueOf(String.format("%.2f", by3));
                    String tkwh1 = String.valueOf(String.format("%.2f", tkw));
                    String amk1 = String.valueOf(String.format("%.3f", count1));
                    String amk2 = String.valueOf(String.format("%.3f", count2));
                    String amk3 = String.valueOf(String.format("%.3f", count3));
                    tbys.setText(tby1);
                    bys1.setText(am1);
                    bys2.setText(am2);
                    bys3.setText(am3);
                    tkws.setText(tkwh1);
                    tkws1.setText(amk1);
                    tkws2.setText(amk2);
                    tkws3.setText(amk3);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        Query barQuery = reference2.orderByKey().limitToLast(8);
        barQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float groupSpace = 0.46f;
                float barSpace = 0.08f; // x3 dataset
                float barWidth = 0.1f; // x3 dataset

                final SimpleDateFormat df = new SimpleDateFormat("dd MMM");
                final ArrayList<UserCum> stu = new ArrayList<UserCum>();
                final ArrayList<String> ts = new ArrayList<String>();
                final ArrayList<Float> wt = new ArrayList<Float>();
                final ArrayList<Float> wt2 = new ArrayList<Float>();
                final ArrayList<Float> wt3 = new ArrayList<Float>();
                ArrayList<BarEntry> entriesY = new ArrayList<BarEntry>();
                ArrayList<BarEntry> entriesY2 = new ArrayList<BarEntry>();
                ArrayList<BarEntry> entriesY3 = new ArrayList<BarEntry>();
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
                    Date date = new java.util.Date(ts1*1000L);
                    String datets = sdf.format(date);
                    UserCum user = new UserCum(wat1,wat2,wat3,datets);
                    stu.add(user);
                    ts.add(user.getDate1());
                    wt.add(user.getwHour1());
                    wt2.add(user.getwHour2());
                    wt3.add(user.getwHour3());
                }

                Object[] objNames = ts.toArray();
                Object[] objNames1 = wt.toArray();
                Integer [] x = new Integer[objNames1.length];
                for(int i = 0; i<stu.size();i++){
                    UserCum currentMyEntry = stu.get(i);
                    entriesY.add(new BarEntry(x[i]=i,currentMyEntry.getwHour1()));
                    entriesY2.add(new BarEntry(x[i]=i,currentMyEntry.getwHour2()));
                    entriesY3.add(new BarEntry(x[i]=i,currentMyEntry.getwHour3()));
                }

                BarDataSet setComp1 = new BarDataSet(entriesY, "Rp. Unit 1");
                BarDataSet setComp2 = new BarDataSet(entriesY2, "Rp. Unit 2");
                BarDataSet setComp3 = new BarDataSet(entriesY3, "Rp. Unit 3");
                setComp1.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
                setComp1.setValueTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                setComp2.setColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
                setComp2.setValueTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                setComp3.setColor(ResourcesCompat.getColor(getResources(), R.color.colorBlue, null));
                setComp3.setValueTextColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));

                final String[] mMonth = Arrays.copyOf(objNames, objNames.length, String[].class);

                BarData data2 = new BarData(setComp1,setComp2,setComp3);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                xAxis.setValueFormatter(new IndexAxisValueFormatter(mMonth));
                xAxis.setCenterAxisLabels(true);
                xAxis.setLabelRotationAngle(270);
                xAxis.setGranularityEnabled(true);

                //***
                // Controlling right side of y axis
                YAxis yAxisRight = barChart.getAxisRight();
                yAxisRight.setEnabled(false);

                //***
                // Controlling left side of y axis
                YAxis yAxisLeft = barChart.getAxisLeft();
                yAxisLeft.setGranularity(1f);

                data2.setBarWidth(barWidth);

                // Setting Data
                barChart.setData(data2);
                barChart.setDragEnabled(true);
                barChart.setVisibleXRangeMaximum(objNames.length);
                barChart.getDescription().setEnabled(false);
                barChart.getXAxis().setAxisMinimum(0);
                barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*objNames.length);
                barChart.getAxisLeft().setAxisMinimum(0);
                barChart.groupBars(0,groupSpace,barSpace);
                barChart.invalidate(); // refresh
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tglSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tglTerpilih = (String) parent.getItemAtPosition(position);
                try {
                    DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                    Date date = (Date)formatter.parse(tglTerpilih);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(date);
                    calendar2.add(Calendar.HOUR,8);
                    Date tglsk = calendar2.getTime();
                    long tglsekarang = (tglsk.getTime())/1000L;
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTime(date);
                    calendar3.add(Calendar.DATE,1);
                    calendar3.add(Calendar.HOUR,8);
                    Date tglKm = calendar3.getTime();
                    long tglBesok = (tglKm.getTime())/1000L;
                    String tglkmr = String.valueOf(tglsekarang);
                    //Toast.makeText(getContext(),"tgl skrg"+tglkmr,Toast.LENGTH_LONG).show();
                    Query mQuery = reference3.orderByChild("timestamp").startAt(tglsekarang).endAt(tglBesok);
                    //Query mQuery = reference3.orderByKey().limitToLast(24);
                    mQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<User3> data = new ArrayList<>();
                            for (DataSnapshot ds: dataSnapshot.getChildren()){
                                Double watt1 = ds.child("watthour1").getValue(Double.class);
                                Double watt2 = ds.child("watthour2").getValue(Double.class);
                                Double watt3 = ds.child("watthour3").getValue(Double.class);
                                Long ts = ds.child("timestamp").getValue(Long.class);
                                Date date = new java.util.Date(ts*1000L);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                calendar.add(Calendar.HOUR,-7);
                                Date tgl = calendar.getTime();
                                String datets = sdf2.format(tgl);
                                Double kWh1 = ((watt1*0.0013888888888889)/1000);
                                Double kWh2 = ((watt2*0.0013888888888889)/1000);
                                Double kWh3 = ((watt3*0.0013888888888889)/1000);
                                Double tKwh = kWh1+kWh2+kWh3;
                                String tkwh1 = String.valueOf(String.format("%.2f", tKwh));
                                String am1 = String.valueOf(String.format("%.3f", kWh1));
                                String am2 = String.valueOf(String.format("%.3f", kWh2));
                                String am3 = String.valueOf(String.format("%.3f", kWh3));
                                User3 user = new User3(tkwh1,am1,am2,am3,datets);
                                data.add(0,user);
                            }
                            MultiColumn_ListAdapter3 adapter = new MultiColumn_ListAdapter3(getActivity(),R.layout.list_adapter_view3,data);
                            listView3.post(new Runnable() {
                                @Override
                                public void run() {
                                    listView3.smoothScrollToPosition(0);
                                }
                            });
                            listView3.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });

                    mQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<User4> data = new ArrayList<>();
                            for (DataSnapshot ds: dataSnapshot.getChildren()){
                                Double watt1 = ds.child("watthour1").getValue(Double.class);
                                Double watt2 = ds.child("watthour2").getValue(Double.class);
                                Double watt3 = ds.child("watthour3").getValue(Double.class);
                                Long ts = ds.child("timestamp").getValue(Long.class);
                                Date date = new java.util.Date(ts*1000L);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                calendar.add(Calendar.HOUR,-7);
                                Date tgl = calendar.getTime();
                                String datets = sdf2.format(tgl);
                                Double by1 = ((watt1*0.0013888888888889)/1000)*1467.28;
                                Double by2 = ((watt2*0.0013888888888889)/1000)*1467.28;
                                Double by3 = ((watt3*0.0013888888888889)/1000)*1467.28;
                                Double tby = by1+by2+by3;
                                String tkwh1 = String.valueOf(String.format("%.2f", tby));
                                String am1 = String.valueOf(String.format("%.2f", by1));
                                String am2 = String.valueOf(String.format("%.2f", by2));
                                String am3 = String.valueOf(String.format("%.2f", by3));
                                User4 user = new User4(tkwh1,am1,am2,am3,datets);
                                data.add(0,user);
                            }
                            MultiColumn_ListAdapter4 adapter = new MultiColumn_ListAdapter4(getActivity(),R.layout.list_adapter_view4,data);
                            listView4.post(new Runnable() {
                                @Override
                                public void run() {
                                    listView4.smoothScrollToPosition(0);
                                }
                            });
                            listView4.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });

                    mQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<User4> data = new ArrayList<>();
                            Double count1 = 0.0;
                            Double count2 = 0.0;
                            Double count3 = 0.0;
                            for (DataSnapshot ds: dataSnapshot.getChildren()){
                                Double watt1 = ds.child("watthour1").getValue(Double.class);
                                Double watt2 = ds.child("watthour2").getValue(Double.class);
                                Double watt3 = ds.child("watthour3").getValue(Double.class);
                                Long ts = ds.child("timestamp").getValue(Long.class);
                                Date date = new java.util.Date(ts*1000L);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                calendar.add(Calendar.HOUR,-7);
                                Date tgl = calendar.getTime();
                                String datets = sdf2.format(tgl);
                                count1 +=((watt1*0.0013888888888889)/1000);
                                count2 +=((watt2*0.0013888888888889)/1000);
                                count3 +=((watt3*0.0013888888888889)/1000);
                                Double by1 = (count1*1467.28);
                                Double by2 = (count2*1467.28);
                                Double by3 = (count3*1467.28);
                                Double tby = by1+by2+by3;
                                Double tkw = count1+count2+count3;
                                String tby1 = String.valueOf(String.format("%.2f", tby));
                                String am1 = String.valueOf(String.format("%.2f", by1));
                                String am2 = String.valueOf(String.format("%.2f", by2));
                                String am3 = String.valueOf(String.format("%.2f", by3));
                                String tkwh1 = String.valueOf(String.format("%.2f", tkw));
                                String amk1 = String.valueOf(String.format("%.3f", count1));
                                String amk2 = String.valueOf(String.format("%.3f", count2));
                                String amk3 = String.valueOf(String.format("%.3f", count3));
                                tbyshr.setText(tby1);
                                byshr1.setText(am1);
                                byshr2.setText(am2);
                                byshr3.setText(am3);
                                tkwshr.setText(tkwh1);
                                tkwshr1.setText(amk1);
                                tkwshr2.setText(amk2);
                                tkwshr3.setText(amk3);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                } catch (ParseException e) {
                    //handle exception
                }





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }



}
