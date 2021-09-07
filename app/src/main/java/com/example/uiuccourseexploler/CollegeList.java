package com.example.uiuccourseexploler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CollegeList extends AppCompatActivity {
    ListView listView;
    private DatabaseReference myRef;
    ArrayList<String> data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.college_list);
        listView = (ListView)findViewById(R.id.listView);
        data = new ArrayList<>();

        myRef = FirebaseDatabase.getInstance().getReference().child("college");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String name = child.child("name").getValue().toString();
                    data.add(name);
                }
                MyAdapter adapter = new MyAdapter(CollegeList.this);
                adapter.setCollege(data);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CollegeList.this, MajorPage.class);
                String selectedCollege = data.get(position);
                if (selectedCollege.equals("College of Engineering")) {
                    selectedCollege = "engineering";
                }
                intent.putExtra("Text", selectedCollege);
                startActivity(intent);
            }
        });
    }
}
