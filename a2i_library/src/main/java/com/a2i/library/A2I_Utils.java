package com.a2i.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.a2i.library.volley.StatusCode;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

import static android.content.ContentValues.TAG;

public class A2I_Utils {
    public static final String PREF_NAME = "com.a2i.library";
    public static final String PROMPT_DATE_PREF = "com.a2i.library.promptdatepref";
    public static final String MESSAGE = "message";
    public static final String MESSAGE_TYPE = "message_type";

    public static boolean isOnline(Context context) {
        boolean result = false;
        if (context != null) {
            final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo != null) {
                    result = networkInfo.isConnected();
                }
            }
        }
        return result;
    }

    public static StatusCode getStatusCode(VolleyError error) {
        if (error != null && error.networkResponse != null) {
            try {
                NetworkResponse response = error.networkResponse;
                String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                return new Gson().fromJson(json, StatusCode.class);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "Exception : " + e);
            } catch (JsonSyntaxException e) {
                Log.e(TAG, "Exception : " + e);
            }
        }
        return null;
    }

}
