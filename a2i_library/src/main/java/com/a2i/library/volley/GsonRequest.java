package com.a2i.library.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class GsonRequest<T> extends JsonRequest<T> {
    private Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers = new HashMap<>();
    private final Listener<T> listener;

    /**
     * Make a request and return a parsed object from JSON.
     * Note that this request object supports caching!
     *
     * @param url   URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     */
    private GsonRequest(final int method, final String url, final String paramsJsonObject, final Class<T> clazz, final Listener<T> listener, final ErrorListener errorListener) {
        super(method, url, (paramsJsonObject == null) ? null : paramsJsonObject.toString(), listener, errorListener);
        // Added to avoid com.android.volley.TimeoutError
        setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 25, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.clazz = clazz;
        this.listener = listener;
    }

    public GsonRequest(int method, String url, JSONObject paramsJsonObject, Class<T> className, final ResultListenerNG<T> resultListenerNG) {
        this(method, url, (paramsJsonObject == null) ? null : paramsJsonObject.toString(), className, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                resultListenerNG.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resultListenerNG.onError(error);
            }
        });
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String utf8String = new String(response.data, "UTF-8");
            T o = gson.fromJson(utf8String, clazz);
            return Response.success(o, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}