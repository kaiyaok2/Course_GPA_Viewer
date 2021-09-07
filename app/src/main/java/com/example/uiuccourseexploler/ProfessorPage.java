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

public class ProfessorPage extends AppCompatActivity {
    TextView A;
    TextView B;
    TextView C;
    TextView D;
    TextView F;
    TextView profName;
    TextView avgGPA;
    private DatabaseReference myRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_page);

        Intent intent = getIntent();
        final String selectedCollege = intent.getStringExtra("collegeName");
        final String selectedMajor = intent.getStringExtra("majorName");
        final String selectedCourse = intent.getStringExtra("courseName");
        final String selectedProfessor = intent.getStringExtra("ProfessorName");

        A = findViewById(R.id.A);
        B = findViewById(R.id.B);
        C = findViewById(R.id.C);
        D = findViewById(R.id.D);
        F = findViewById(R.id.F);
        profName = findViewById(R.id.ProfName);
        avgGPA = findViewById(R.id.avg);

        myRef = FirebaseDatabase.getInstance().getReference().child("college").child(selectedCollege)
                .child(selectedMajor).child(selectedCourse).child("Professors").child(selectedProfessor);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profname = dataSnapshot.child("name").getValue().toString();
                String gpa = dataSnapshot.child("Avg GPA").getValue().toString();
                String AA = dataSnapshot.child("Percentage A's").getValue().toString();
                String BB = dataSnapshot.child("Percentage B's").getValue().toString();
                String CC = dataSnapshot.child("Percentage C's").getValue().toString();
                String DD = dataSnapshot.child("Percentage D's").getValue().toString();
                String FF = dataSnapshot.child("Percentage F's").getValue().toString();

                profName.setText(profname);
                avgGPA.setText(gpa);
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
