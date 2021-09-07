package com.example.uiuccourseexploler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CoursePage extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    String courseName;
    private DatabaseReference myRef;
    ArrayList<String> data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_page);

        Intent intent = getIntent();
        final String selectedCollege = intent.getStringExtra("selectedCollege");
        final String selectedMajor = intent.getStringExtra("selectedMajor");

        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listview3);
        data = new ArrayList<>();


        myRef = FirebaseDatabase.getInstance().getReference().child("college").child(selectedCollege).child(selectedMajor);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    try {
                        String name = child.child("Course Number").getValue().toString();
                        data.add(name);
                    } catch (NullPointerException e) {
                        continue;
                    }
                }
                MyAdapter adapter = new MyAdapter(CoursePage.this);
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
                if (selectedMajor.equals("Computer Science")) {
                    Intent intent = new Intent(CoursePage.this, Course.class);
                    String selectedCourse = data.get(position);
                    intent.putExtra("Course", selectedCourse);
                    intent.putExtra("Major", selectedMajor);
                    intent.putExtra("College", selectedCollege);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(CoursePage.this, Uncscourse.class);
                    String selectedCourse = data.get(position);
                    intent.putExtra("Course", selectedCourse);
                    intent.putExtra("Major", selectedMajor);
                    intent.putExtra("College", selectedCollege);
                    startActivity(intent);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                courseName = query;
                Intent intent = new Intent(CoursePage.this, Course.class);
                intent.putExtra("Course", courseName);
                intent.putExtra("Major", selectedMajor);
                intent.putExtra("College", selectedCollege);
                try {
                    String check = FirebaseDatabase.getInstance().getReference().child("college").child(selectedCollege).child(selectedMajor).child(courseName).toString();
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(CoursePage.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }
}
