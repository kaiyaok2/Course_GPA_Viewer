package com.example.uiuccourseexploler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Uncscourse extends AppCompatActivity {

    TextView courseName;
    TextView courseNumber;
    TextView avgGPA;
    TextView creditHours;
    TextView A;
    TextView B;
    TextView C;
    TextView D;
    TextView F;
    private DatabaseReference myRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uncscourse);

        Intent intent = getIntent();
        final String selectedCollege = intent.getStringExtra("College");
        final String selectedMajor = intent.getStringExtra("Major");
        final String selectedCourse = intent.getStringExtra("Course");

        courseName = findViewById(R.id.courseName2);
        courseNumber = findViewById(R.id.courseNumber2);
        avgGPA = findViewById(R.id.avgGPA2);
        creditHours = findViewById(R.id.creditHours2);

        A = findViewById(R.id.a);
        B = findViewById(R.id.b);
        C = findViewById(R.id.c);
        D = findViewById(R.id.d);
        F = findViewById(R.id.f);

        myRef = FirebaseDatabase.getInstance().getReference().child("college").child(selectedCollege).child(selectedMajor)
                .child(selectedCourse);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String number = dataSnapshot.child("Course Number").getValue().toString();
                String gpa = dataSnapshot.child("Avg GPA").getValue().toString();
                String hour = dataSnapshot.child("Credit Hours").getValue().toString();
                String AA = dataSnapshot.child("Percentage A's").getValue().toString();
                String BB = dataSnapshot.child("Percentage B's").getValue().toString();
                String CC = dataSnapshot.child("Percentage C's").getValue().toString();
                String DD = dataSnapshot.child("Percentage D's").getValue().toString();
                String FF = dataSnapshot.child("Percentage F's").getValue().toString();

                courseName.setText(name);
                courseNumber.setText(number);
                avgGPA.setText(gpa);
                creditHours.setText(hour);
                A.setText(AA);
                B.setText(BB);
                C.setText(CC);
                D.setText(DD);
                F.setText(FF);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
