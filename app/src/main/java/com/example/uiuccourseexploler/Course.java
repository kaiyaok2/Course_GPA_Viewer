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

public class Course extends AppCompatActivity {
    TextView courseName;
    TextView courseNumber;
    TextView avgGPA;
    TextView creditHours;
    TextView professor;
    ListView listView;
    private DatabaseReference myRef;
    private DatabaseReference reference;
    ArrayList<String> data;
    MyAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course);

        Intent intent = getIntent();
        final String selectedCollege = intent.getStringExtra("College");
        final String selectedMajor = intent.getStringExtra("Major");
        final String selectedCourse = intent.getStringExtra("Course");

        courseName = findViewById(R.id.CourseName);
        courseNumber = findViewById(R.id.courseNumber);
        avgGPA = findViewById(R.id.avgGPA);
        creditHours = findViewById(R.id.creditHours);
        listView = findViewById(R.id.professors);
        data = new ArrayList<>();
        professor = findViewById(R.id.textView13);

        myRef = FirebaseDatabase.getInstance().getReference().child("college").child(selectedCollege).child(selectedMajor)
                .child(selectedCourse);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String number = dataSnapshot.child("Course Number").getValue().toString();
                String gpa = dataSnapshot.child("Avg GPA").getValue().toString();
                String hour = dataSnapshot.child("Credit Hours").getValue().toString();

                courseName.setText(name);
                courseNumber.setText(number);
                avgGPA.setText(gpa);
                creditHours.setText(hour);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("college").child(selectedCollege)
                .child(selectedMajor).child(selectedCourse).child("Professors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    try {
                        String profName = child.child("name").getValue().toString();
                        data.add(profName);
                    } catch (NullPointerException e) {
                        continue;
                    }
                }
                adapter = new MyAdapter(Course.this);
                adapter.setCollege(data);
                listView.setAdapter(adapter);

                if (adapter.getCount() == 0) {
                    listView.setVisibility(View.GONE);
                    professor.setVisibility(View.GONE);
                } else {
                    listView.setVisibility(View.VISIBLE);
                    professor.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Course.this, ProfessorPage.class);
                String profName = data.get(position);
                intent.putExtra("ProfessorName", profName);
                intent.putExtra("collegeName", selectedCollege);
                intent.putExtra("majorName", selectedMajor);
                intent.putExtra("courseName", selectedCourse);
                startActivity(intent);
            }
        });


    }
}
