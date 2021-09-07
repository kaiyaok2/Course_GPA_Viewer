package com.example.uiuccourseexploler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<String> college;

    public MyAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCollege(ArrayList<String> collegeList) {
        this.college = collegeList;
    }

    public Object getItem(int position) {
        return college.get(position);
    }

    @Override
    public int getCount() {
        return college.size();
    }

    @Override
    public long getItemId(int position) {
        return 100;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.listrow,parent,false);

        ((TextView)convertView.findViewById(R.id.collegeName)).setText(college.get(position));

        return convertView;
    }

}
