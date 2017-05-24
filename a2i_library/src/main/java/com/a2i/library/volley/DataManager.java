package com.a2i.library.volley;

import android.content.Context;
import android.util.Log;

import com.a2i.library.BuildConfig;
import com.a2i.library.model.CompanySubscriptionResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class DataManager {
    private static final String TAG = DataManager.class.getSimpleName();
    private static DataManager singleton;
    private static final String URL_CHECK_COMPANY_SUBSCRIPTION = BuildConfig.BASE_URL + "survey/mobile_check_company_subscription/%s";

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (singleton == null) {
            singleton = new DataManager();
        }
        return singleton;
    }

    private <T> void makeJsonObjectRequest(Context context,int method, final String url, final JSONObject paramsJsonObject, Class<T> className, final ResultListenerNG<T> resultListenerNG) {
        Log.d(TAG, "Params : " + paramsJsonObject);
        GsonRequest<T> gsonRequest = new GsonRequest<>(method, url, paramsJsonObject, className, new ResultListenerNG<T>() {
            @Override
            public void onSuccess(T response) {
                if (response != null)
                    resultListenerNG.onSuccess(response);
                else
                    resultListenerNG.onError(new VolleyError());
            }

            @Override
            public void onError(VolleyError error) {
                resultListenerNG.onError(error);
            }
        });
        Log.d(TAG, "Url : " + gsonRequest.getUrl());
        // Adding request to request queue
        RequestManagerApi.getInstance().addToRequestQueue(context,gsonRequest);
    }

    public void checkCompanySubscription(Context context, String surveyCode, ResultListenerNG<CompanySubscriptionResponse> resultListenerNG) {
        String url = String.format(URL_CHECK_COMPANY_SUBSCRIPTION, surveyCode);
        makeJsonObjectRequest(context,Request.Method.GET, url, null, CompanySubscriptionResponse.class, resultListenerNG);
    }
}