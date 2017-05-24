package com.a2i.library.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestManagerApi {
    private static final String TAG = RequestManagerApi.class.getSimpleName();
    private static RequestQueue mRequestQueue;
    private static RequestManagerApi singleton;

    private RequestManagerApi() {
    }

    public static RequestManagerApi getInstance() {
        if (singleton == null) {
            singleton = new RequestManagerApi();
        }
        return singleton;
    }

    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Context context, Request<T> req) {
        req.setTag(TAG);
        getRequestQueue(context).add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
