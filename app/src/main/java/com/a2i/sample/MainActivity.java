package com.a2i.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.a2i.library.A2I_Handler;
import com.a2i.library.A2I_Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DEFAULT_SURVEY_CODE = "NJXyEUf6";
    //    private static final String DEFAULT_SURVEY_CODE = "9jJUdtZy";
    private static final int REQUEST_CODE = 1;
    private A2I_Handler a2IHandler = new A2I_Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a2IHandler.onCreate(this, DEFAULT_SURVEY_CODE, getString(R.string.app_name), "", REQUEST_CODE);
        initViews();
    }

    private void initViews() {
        Button btnShowDefaultSurvey = (Button) findViewById(R.id.btnShowDefaultSurvey);
        btnShowDefaultSurvey.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnShowDefaultSurvey:
                a2IHandler.startSurveyActivityForResult(this, DEFAULT_SURVEY_CODE, getString(R.string.app_name), REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String msg = data.getStringExtra(A2I_Utils.MESSAGE);
            String msgType = data.getStringExtra(A2I_Utils.MESSAGE_TYPE);
            Log.d(TAG, "Msg type : " + msgType + " msg : " + msg);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(false);
            String title = msgType.substring(0, 1).toUpperCase() + msgType.substring(1);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setPositiveButton(android.R.string.ok, null);
            builder.create().show();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}


