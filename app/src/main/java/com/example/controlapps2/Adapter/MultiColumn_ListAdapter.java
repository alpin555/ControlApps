package com.example.controlapps2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.controlapps2.Model.User;
import com.example.controlapps2.R;

import java.util.ArrayList;

public class MultiColumn_ListAdapter extends ArrayAdapter<User> {

    private LayoutInflater mInflater;
    private ArrayList<User> users;
    private int mViewResourcId;

    public MultiColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<User> users){
        super(context,textViewResourceId,users);
        this.users = users;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourcId = textViewResourceId;

    }

    public View getView(int position, View convertView, ViewGroup parents){
        convertView = mInflater.inflate(mViewResourcId,null);

        User user = users.get(position);

        if(user!=null){
            TextView watts1 = (TextView)convertView.findViewById(R.id.watts1);
            TextView watts2 = (TextView)convertView.findViewById(R.id.watts2);
            TextView watts3 = (TextView)convertView.findViewById(R.id.watts3);
            TextView datets = (TextView)convertView.findViewById(R.id.datets);
            TextView volt = (TextView)convertView.findViewById(R.id.volt);

            if(watts1!=null){
                watts1.setText((user.getAm1()));
            }
            if(watts2!=null){
                watts2.setText((user.getAm2()));
            }
            if(watts3!=null){
                watts3.setText((user.getAm3()));
            }
            if(datets!=null){
                datets.setText((user.getDate1()));
            }
            if(volt!=null){
                volt.setText((user.getVolt()));
            }

        }
        return convertView;
    }
}
