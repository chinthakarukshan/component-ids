package com.wso2telco.gsma.authenticators.voice;

/**
 * Created by chinthaka on 10/16/17.
 */
public enum Outcome {

    ACTIVE("ACTIVE"),UNKNOWN_USER("UNKNOWN_USER"),ACCOUNT_DISABLED("ACCOUNT_DISABLED"),ERROR("ERROR");

    private String outcome;

    private Outcome (String outcome) {
        this.outcome = outcome;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
