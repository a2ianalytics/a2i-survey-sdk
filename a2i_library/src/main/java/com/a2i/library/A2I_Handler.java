package com.a2i.library;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.a2i.library.model.CompanySubscriptionResponse;
import com.a2i.library.volley.DataManager;
import com.a2i.library.volley.ResultListenerNG;
import com.a2i.library.volley.StatusCode;
import com.android.volley.VolleyError;

import java.util.Date;

public class A2I_Handler {
    private static final long THREE_DAYS = 259200000L;
    private static final long THREE_MONTHS = 7884000000L;
    private static final long THREE_WEEKS = 1814400000L;

    public void onCreate(Activity activity, String surveyCode, String title, final String alertBodyText, int requestCode) {
        onCreate(activity, surveyCode, title, alertBodyText, requestCode, THREE_DAYS, THREE_WEEKS, THREE_MONTHS);
    }

    public void onCreate(final Activity activity, final String surveyCode, final String title, final String alertBodyText, final int requestCode, final long afterInstallInterval, final long afterDeclineInterval, final long afterAcceptInterval) {
        final Context appContext = activity.getApplicationContext();
        SharedPreferences prefs = appContext.getSharedPreferences(A2I_Utils.PREF_NAME, Context.MODE_PRIVATE);
        final long currentDate = new Date().getTime();
        long promptDate = prefs.getLong(A2I_Utils.PROMPT_DATE_PREF, 0);
        if (promptDate == 0) {
            prefs.edit().putLong(A2I_Utils.PROMPT_DATE_PREF, currentDate + afterInstallInterval).apply();
        } else if (promptDate < currentDate) {
            if (A2I_Utils.isOnline(activity)) {
                DataManager.getInstance().checkCompanySubscription(activity.getApplicationContext(), surveyCode, new ResultListenerNG<CompanySubscriptionResponse>() {
                    @Override
                    public void onSuccess(CompanySubscriptionResponse response) {
                        if (response.isCanAddSurveyRespose()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle(title);
                            if (!TextUtils.isEmpty(alertBodyText)) {
                                builder.setMessage(alertBodyText);
                            } else {
                                builder.setMessage(activity.getString(R.string.a2i_prompt_message_text));
                            }
                            builder.setCancelable(false);
                            builder.setPositiveButton(R.string.a2i_action_give_feedback, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences prefs = appContext.getSharedPreferences(A2I_Utils.PREF_NAME, Context.MODE_PRIVATE);
                                    prefs.edit().putLong(A2I_Utils.PROMPT_DATE_PREF, currentDate + afterAcceptInterval).apply();
                                    startSurveyActivityForResult(activity, surveyCode, title, requestCode);
                                }
                            });
                            builder.setNegativeButton(R.string.a2i_action_not_now, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences prefs = appContext.getSharedPreferences(A2I_Utils.PREF_NAME, Context.MODE_PRIVATE);
                                    prefs.edit().putLong(A2I_Utils.PROMPT_DATE_PREF, currentDate + afterDeclineInterval).apply();
                                }
                            });
                            builder.create().show();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                    }
                });
            }
        }
    }

    public void startSurveyActivityForResult(final Activity activity, final String surveyCode, final String title, final int requestCode) {
        if (A2I_Utils.isOnline(activity)) {
            DataManager.getInstance().checkCompanySubscription(activity.getApplicationContext(), surveyCode, new ResultListenerNG<CompanySubscriptionResponse>() {
                @Override
                public void onSuccess(CompanySubscriptionResponse response) {
                    if (response.isCanAddSurveyRespose()) {
                        A2I_Activity.startActivityForResult(activity, surveyCode, title, requestCode);
                    } else {
                        Toast.makeText(activity, R.string.a2i_survey_closed, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    StatusCode statusCode = A2I_Utils.getStatusCode(error);
                    if (statusCode != null && !TextUtils.isEmpty(statusCode.getMessage())) {
                        Toast.makeText(activity, statusCode.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, R.string.a2i_no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(activity, R.string.a2i_no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }
}
