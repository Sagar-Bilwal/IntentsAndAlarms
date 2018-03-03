package com.example.ralph.intentsandalarms;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        String action = intent.getAction();

        if(action != null){
            if(action.equals(Intent.ACTION_SEND)){
                if(intent.hasExtra(Intent.EXTRA_TEXT)){
                    String text = intent.getStringExtra(Intent.EXTRA_TEXT);
                    textView.setText(text);
                }
            }
            else if(action.equals(Intent.ACTION_VIEW)){
                Uri data = intent.getData();
                if(data != null){
                    String url = data.toString();
                    textView.setText(url);
                }
            }
        }

    }
}
