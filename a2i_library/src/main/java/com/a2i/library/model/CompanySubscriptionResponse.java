package com.a2i.library.model;

import java.io.Serializable;

/**
 * Created by admin on 23/05/2017.
 */

public class CompanySubscriptionResponse implements Serializable {
    private boolean canAddSurveyRespose;
    private int code;

    public boolean isCanAddSurveyRespose() {
        return canAddSurveyRespose;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "CompanySubscriptionResponse{" +
                "canAddSurveyRespose=" + canAddSurveyRespose +
                ", code=" + code +
                '}';
    }
}
