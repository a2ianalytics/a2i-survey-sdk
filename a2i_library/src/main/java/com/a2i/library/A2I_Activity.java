package com.a2i.library;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class A2I_Activity extends AppCompatActivity {
    private static final String TAG = A2I_Activity.class.getSimpleName();
    private static final String SURVEY_CODE = "survey_code";
    private static final String DASHBOARD_CODE = "dashboard_code";
    private static final String TITLE = "title";
    private WebView a2iWebView;
    private RelativeLayout a2iRlForProgressBar;
    private RelativeLayout a2iRlForOffline;

    public static void startActivityForResult(Activity activity, String surveyCode, String title, int requestCode) {
        Intent intent = new Intent(activity, A2I_Activity.class);
        intent.putExtra(A2I_Activity.SURVEY_CODE, surveyCode);
        if (title != null) {
            intent.putExtra(A2I_Activity.TITLE, title);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForDashboard(Activity activity, String dashboardCode, String title) {
        Intent intent = new Intent(activity, A2I_Activity.class);
        intent.putExtra(A2I_Activity.DASHBOARD_CODE, dashboardCode);
        if (title != null) {
            intent.putExtra(A2I_Activity.TITLE, title);
        }
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2i);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (getIntent().hasExtra(TITLE)) {
                String title = getIntent().getStringExtra(TITLE);
                if (title != null) {
                    actionBar.setTitle(title);
                }
            }
        }

        initViews();
        loadWebView();
    }

    private void loadWebView() {
        if (A2I_Utils.isOnline(this)) {
            a2iRlForOffline.setVisibility(View.GONE);
            a2iRlForProgressBar.setVisibility(View.VISIBLE);
            String url = "";
            if (getIntent().hasExtra(SURVEY_CODE)) {
                String surveyId = getIntent().getStringExtra(SURVEY_CODE);
                url = BuildConfig.BASE_URL + BuildConfig.SURVEY_FUNCTION + surveyId;
            } else if (getIntent().hasExtra(DASHBOARD_CODE)) {
                String dashboardId = getIntent().getStringExtra(DASHBOARD_CODE);
                url = BuildConfig.BASE_URL + BuildConfig.SURVEY_DASHBAORD + dashboardId;
            }
            Log.d(TAG, "URL : " + url);
            a2iWebView.loadUrl(url);
        } else {
            a2iRlForProgressBar.setVisibility(View.GONE);
            a2iRlForOffline.setVisibility(View.VISIBLE);
        }
    }

    private void initViews() {
        a2iRlForProgressBar = (RelativeLayout) findViewById(R.id.a2iRlForProgressBar);
        a2iRlForOffline = (RelativeLayout) findViewById(R.id.a2iRlForOffline);
        a2iWebView = (WebView) findViewById(R.id.a2iWebView);

        a2iWebView.setWebViewClient(new CustomWebViewClient());
        a2iWebView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.a2i_menu_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        } else if (item.getItemId() == R.id.a2i_action_refresh) {
            loadWebView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "shouldOverrideUrlLoading : " + url);
            if (url.contains(BuildConfig.ACTION_TYPE)) {
                String msg = url.substring(url.indexOf(BuildConfig.DISPLAY_MESSAGE) + BuildConfig.DISPLAY_MESSAGE.length(), url.indexOf(BuildConfig.SURVEY_MSG_TYPE));
                String msgType = url.substring(url.indexOf(BuildConfig.SURVEY_MSG_TYPE) + BuildConfig.SURVEY_MSG_TYPE.length(), url.length());
                Log.d(TAG, "Msg type : " + msgType + " msg : " + msg);
                Toast.makeText(A2I_Activity.this, msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra(A2I_Utils.MESSAGE, msg);
                intent.putExtra(A2I_Utils.MESSAGE_TYPE, msgType);
                setResult(RESULT_OK, intent);
                finish();
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            a2iRlForProgressBar.setVisibility(View.GONE);
            a2iRlForOffline.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            a2iRlForProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.clearCache(true);
            a2iRlForProgressBar.setVisibility(View.GONE);
        }
    }
}
