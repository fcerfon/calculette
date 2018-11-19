package com.example.formation1.calculator;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import android.content.Intent;
import android.widget.Button;

import android.util.Log;
import android.net.Uri;

public class InfoActivity extends Activity {

    /*@Override
    protected void onPause() {
        super.onPause();
        Log.e("logOnPause", "InfoActivity paused");
        this.finish();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView text = findViewById(R.id.textview);
        text.setText("Awesome app\n" +
                "Created by Florent Cerfon\n" +
                "For Diginamic\n" +
                "Promotion : mobile development\n");

        Button returnButton = findViewById(R.id.returnToMain);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button callDev = findViewById(R.id.callTheDeveloper);

        callDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });

        Button sendMail = findViewById(R.id.sendMail);
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                Uri uri = Uri.parse("mailto:test@gmail.com");
                intent.setData(uri);
                intent.putExtra("subject", "mysubject");
                intent.putExtra("body", "my message");
                startActivity(intent);
            }
        });
    }
}
