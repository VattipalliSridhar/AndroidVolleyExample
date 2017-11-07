package com.example.androidvolleyexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.androidvolleyexample.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button json_array_button, json_object_button, string_button, image_button;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        json_array_button = (Button) findViewById(R.id.json_array_button);
        json_array_button.setOnClickListener(this);

        json_object_button = (Button) findViewById(R.id.json_object_button);
        json_object_button.setOnClickListener(this);

        string_button = (Button) findViewById(R.id.string_button);
        string_button.setOnClickListener(this);

        image_button = (Button) findViewById(R.id.image_button);
        image_button.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.json_array_button:

                intent = new Intent(MainActivity.this, JsonArrayActivity.class);

                break;
            case R.id.json_object_button:

                intent = new Intent(MainActivity.this, JsonObjectActivity.class);

                break;
            case R.id.string_button:

                intent = new Intent(MainActivity.this, StringRequestActivity.class);

                break;
            case R.id.image_button:

                intent = new Intent(MainActivity.this, ImageRequestActivity.class);

                break;
        }

        startActivity(intent);

    }
}
