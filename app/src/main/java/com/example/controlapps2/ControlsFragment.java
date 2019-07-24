package com.example.controlapps2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.controlapps2.Model.RelayControl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ControlsFragment extends Fragment {

    SwitchCompat switchCompat,switchCompat2,switchCompat3;
    String switch1On = "Switch 1 is ON";
    String switch1Off = "Switch 1 is OFF";
    String switch2On = "Switch 2 is ON";
    String switch2Off = "Switch 2 is OFF";
    String switch3On = "Switch 3 is ON";
    String switch3Off = "Switch 3 is OFF";
    ImageView l1,l2,l3;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    DatabaseReference databaseControl,databaseControl2,databaseControl3,relayCheck;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_control,container,false);
        switchCompat = (SwitchCompat)v.findViewById(R.id.r1);
        switchCompat2 = (SwitchCompat)v.findViewById(R.id.r2);
        switchCompat3 = (SwitchCompat)v.findViewById(R.id.r3);
        l1 = (ImageView)v.findViewById(R.id.L1);
        l2 = (ImageView)v.findViewById(R.id.L2);
        l3 = (ImageView)v.findViewById(R.id.L3);
        databaseControl = FirebaseDatabase.getInstance().getReference("data").child("Relay").child("0");
        databaseControl2 = FirebaseDatabase.getInstance().getReference("data").child("Relay").child("1");
        databaseControl3 = FirebaseDatabase.getInstance().getReference("data").child("Relay").child("2");
        relayCheck = FirebaseDatabase.getInstance().getReference("data").child("Relay");


        relayCheck.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String R1 = dataSnapshot.child("0").child("status").getValue(String.class);
                String R2 = dataSnapshot.child("1").child("status").getValue(String.class);
                String R3 = dataSnapshot.child("2").child("status").getValue(String.class);

                if (R1.equals("0")){
                    switchCompat.setChecked(false);
                    l1.setImageResource(R.drawable.ic_wb_incandescent_black_24dp);
                }
                if (R1.equals("1")) {
                    switchCompat.setChecked(true);
                    l1.setImageResource(R.drawable.ic_wb_incandescent_yellow_24dp);
                }
                if(R2.equals("0")){
                    switchCompat2.setChecked(false);
                    l2.setImageResource(R.drawable.ic_wb_incandescent_black_24dp);
                }
                if(R2.equals("1")){
                    switchCompat2.setChecked(true);
                    l2.setImageResource(R.drawable.ic_wb_incandescent_yellow_24dp);
                }
                if(R3.equals("0")){
                    switchCompat3.setChecked(false);
                    l3.setImageResource(R.drawable.ic_wb_incandescent_black_24dp);
                }
                if(R3.equals("1")) {
                    switchCompat3.setChecked(true);
                    l3.setImageResource(R.drawable.ic_wb_incandescent_yellow_24dp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean bChecked) {
                if (bChecked){
                    RelayControl data = new RelayControl("1");
                    databaseControl.setValue(data);
                    l1.setImageResource(R.drawable.ic_wb_incandescent_yellow_24dp);
                } else {
                    RelayControl data = new RelayControl("0");
                    databaseControl.setValue(data);
                    l1.setImageResource(R.drawable.ic_wb_incandescent_black_24dp);
                }
            }
        });


        switchCompat2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    RelayControl data = new RelayControl("1");
                    databaseControl2.setValue(data);
                    l2.setImageResource(R.drawable.ic_wb_incandescent_yellow_24dp);
                } else {
                    RelayControl data = new RelayControl("0");
                    databaseControl2.setValue(data);
                    l2.setImageResource(R.drawable.ic_wb_incandescent_black_24dp);
                }
            }
        });


        switchCompat3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    RelayControl data = new RelayControl("1");
                    databaseControl3.setValue(data);
                    l3.setImageResource(R.drawable.ic_wb_incandescent_yellow_24dp);
                } else {
                    RelayControl data = new RelayControl("0");
                    databaseControl3.setValue(data);
                    l3.setImageResource(R.drawable.ic_wb_incandescent_black_24dp);
                }
            }
        });
        return v;
    }

}
