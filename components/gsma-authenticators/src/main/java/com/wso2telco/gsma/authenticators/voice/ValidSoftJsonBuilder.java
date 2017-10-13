package com.wso2telco.gsma.authenticators.voice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by sheshan on 5/4/17.
 */

/*



 */
public class ValidSoftJsonBuilder {

    private String isUserenrollRequestjson;
    private String verifyUserJson;

    private static Log log = LogFactory.getLog(ValidSoftJsonBuilder.class);

    public String getIsUserEnrollRequestJson() {
        return isUserenrollRequestjson;
    }

    public void setIsUserEnrollRequestJsonJson(String logId , String msisdn , String mode) {
        StringBuilder payloadBuilder = new StringBuilder();
        payloadBuilder.append("{");
        payloadBuilder.append("\"serviceData\": {");
        payloadBuilder.append("\"loggingId\": \"");
        payloadBuilder.append(logId);
        payloadBuilder.append("\"");
        payloadBuilder.append("},");
        payloadBuilder.append("\"userData\": {");
        payloadBuilder.append("\"identifier\": \"");
        payloadBuilder.append(msisdn);
        payloadBuilder.append("\"");
        payloadBuilder.append("},");
        payloadBuilder.append("\"processingInformation\": {");
        payloadBuilder.append("\"biometric\": {");
        payloadBuilder.append("\"type\": \"text-dependent\",");
        payloadBuilder.append("\"mode\": \"");
        payloadBuilder.append(mode);
        payloadBuilder.append("\"");
        payloadBuilder.append("}");
        payloadBuilder.append("}");
        payloadBuilder.append("}");

        String payload = payloadBuilder.toString();

        log.debug("Json ::::: " + payload);
        this.isUserenrollRequestjson = payload;
    }

    public String getVerifyUserJson() {
        return verifyUserJson;
    }

    public void setVerifyUserJson(String logId , String msisdn , String mode , String voice) {
        StringBuilder payloadBuilder = new StringBuilder();
        payloadBuilder.append("{");
        payloadBuilder.append("\"serviceData\": {");
        payloadBuilder.append("\"loggingId\": \"");
        payloadBuilder.append(logId);
        payloadBuilder.append("\"");
        payloadBuilder.append("},");
        payloadBuilder.append("\"userData\": {");
        payloadBuilder.append("\"identifier\": \"");
        payloadBuilder.append(msisdn);
        payloadBuilder.append("\"");
        payloadBuilder.append("},");
        payloadBuilder.append("\"processingInformation\": {");
        payloadBuilder.append("\"biometric\": {");
        payloadBuilder.append("\"type\": \"text-dependent\",");
        payloadBuilder.append("\"mode\": \"");
        payloadBuilder.append(mode);
        payloadBuilder.append("\"");
        payloadBuilder.append("},");
        payloadBuilder.append("\"audioCharacteristics\": {");
        payloadBuilder.append("\"samplingRate\": \"8000\",");
        payloadBuilder.append("\"format\": \"alaw\"");
        payloadBuilder.append("},");
        payloadBuilder.append("\"metaInformation\": [");
        payloadBuilder.append("{");
        payloadBuilder.append("\"key\": \"usage-context\",");
        payloadBuilder.append("\"value\": {");
        payloadBuilder.append("\"value\": \"default\",");
        payloadBuilder.append("\"encrypted\": \"false\"");
        payloadBuilder.append("}");
        payloadBuilder.append("}");
        payloadBuilder.append("]");
        payloadBuilder.append("},");
        payloadBuilder.append("\"audioInput\": {");
        payloadBuilder.append("\"secondsThreshold\": \"20\",");
        payloadBuilder.append("\"audio\": {");
        payloadBuilder.append("\"base64\":");
        payloadBuilder.append("\"");
        payloadBuilder.append(voice);
        payloadBuilder.append("\"");
        payloadBuilder.append("}");
        payloadBuilder.append("}");
        payloadBuilder.append("}");

        String payload = payloadBuilder.toString();

        log.info("Json ::::: " + payload);
        this.verifyUserJson = payload;
    }
}
