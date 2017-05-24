package com.a2i.library.volley;
import com.android.volley.VolleyError;

public interface ResultListenerNG<T> {
    void onSuccess(final T response);

    void onError(final VolleyError error);
}
