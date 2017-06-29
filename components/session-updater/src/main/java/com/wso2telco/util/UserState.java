package com.wso2telco.util;

/**
 * Created by root on 4/6/16.
 */
public enum UserState {

    SEND_USSD_PUSH, SEND_USSD_PIN, SEND_SMS,
    RECEIVE_USSD_PUSH_APPROVED, RECEIVE_USSD_PUSH_FAIL, RECEIVE_USSD_PIN_APPROVED, RECEIVE_SMS_RESPONSE_SUCCESS,
    RECEIVE_USSD_PUSH_REJECTED,  RECEIVE_USSD_PIN_REJECTED,
    RECEIVE_SMS_RESPONSE_FAIL, OFFLINE_USER_REGISTRATION,OFFLINE_USER_UNREGISTRATION_SUCCESS,OFFLINE_USER_UNREGISTRATION_FAILED_;


}
