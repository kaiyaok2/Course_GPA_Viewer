package com.example.uiuccourseexploler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Desclaimer extends AppCompatActivity {
    TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desclaimer);

        textView = (TextView) findViewById(R.id.textView10);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Desclaimer.this, CollegeList.class);
                startActivity(intent);
            }
        });
    }
}
