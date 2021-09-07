package com.example.uiuccourseexploler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MajorPage extends AppCompatActivity {
    TextView major;
    ListView listView;
    private DatabaseReference myRef;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.major_page);
        Intent intent = getIntent();
        final String selectedCollege = intent.getStringExtra("Text");

        major = findViewById(R.id.textView5);
        listView = findViewById(R.id.listview2);
        data = new ArrayList<>();

        myRef = FirebaseDatabase.getInstance().getReference().child("college").child(selectedCollege);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    try {
                        String name = child.child("name").getValue().toString();
                        data.add(name);
                    } catch (NullPointerException e) {
                        continue;
                    }
                }
                MyAdapter adapter = new MyAdapter(MajorPage.this);
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
                Intent intent = new Intent(MajorPage.this, CoursePage.class);
                String selectedMajor = data.get(position);
                intent.putExtra("selectedMajor", selectedMajor);
                intent.putExtra("selectedCollege", selectedCollege);
                startActivity(intent);
            }
        });
    }
}
