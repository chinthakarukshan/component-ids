package com.wso2telco.gsma.authenticators.voice;

import com.wso2telco.Util;
import com.wso2telco.core.config.model.MobileConnectConfig;
import com.wso2telco.core.config.service.ConfigurationService;
import com.wso2telco.core.config.service.ConfigurationServiceImpl;
import com.wso2telco.gsma.authenticators.Constants;
import com.wso2telco.gsma.authenticators.util.AuthenticationContextHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.wso2.carbon.identity.application.authentication.framework.AbstractApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.AuthenticatorFlowStatus;
import org.wso2.carbon.identity.application.authentication.framework.LocalApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.config.model.ApplicationConfig;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.AuthenticationFailedException;
import org.wso2.carbon.identity.application.authentication.framework.exception.LogoutFailedException;
import org.wso2.carbon.identity.application.authentication.framework.util.FrameworkUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by sheshan on 4/26/17.
 */
public class VoiceCallAuthenticator extends AbstractApplicationAuthenticator
        implements LocalApplicationAuthenticator {

    private static final String MSISDN = "msisdn";
    private static final String CLIENT_ID = "relyingParty";
    private static final String IS_FLOW_COMPLETED = "isFlowCompleted";
    private static final String BLOB = "blob";
    private static ConfigurationService configurationService = new ConfigurationServiceImpl();
    private static MobileConnectConfig mobileConnectConfig = null;

    private static Log log = LogFactory.getLog(VoiceCallAuthenticator.class);


    static {
        mobileConnectConfig = configurationService.getDataHolder().getMobileConnectConfig();
    }



    @Override
    public AuthenticatorFlowStatus process(HttpServletRequest request, HttpServletResponse response, AuthenticationContext context) throws AuthenticationFailedException, LogoutFailedException {
        log.info("VoiceCallAuthenticator process triggered");

        super.process(request, response, context);
        if (context.isLogoutRequest()) {
            return AuthenticatorFlowStatus.SUCCESS_COMPLETED;
        }
        else {
            boolean isFlowCompleted = (boolean) context.getProperty(IS_FLOW_COMPLETED);

            if (isFlowCompleted) {
                return AuthenticatorFlowStatus.SUCCESS_COMPLETED;
            } else {
                return AuthenticatorFlowStatus.INCOMPLETE;
            }
        }

    }


    @Override
    protected void initiateAuthenticationRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationContext context) throws AuthenticationFailedException {
        super.initiateAuthenticationRequest(request, response, context);
        log.info("#### #### Initiating authentication request from Voice Call Authentication");

        String queryParams = FrameworkUtils.getQueryStringWithFrameworkContextId(context.getQueryParams(),
                context.getCallerSessionKey(), context.getContextIdentifier());
        ApplicationConfig applicationConfig = context.getSequenceConfig().getApplicationConfig();
        Map<String, String> paramMap = Util.createQueryParamMap(queryParams);
        String msisdn = (String) context.getProperty(MSISDN);
        String clientId = paramMap.get(CLIENT_ID);
        String applicationName = applicationConfig.getApplicationName();
        String sessionKey = context.getCallerSessionKey();
        String isUserEnrolledUrl = mobileConnectConfig.getVoiceConfig().getUserEnrollmentCheckEndpoint();
        String processingMode = mobileConnectConfig.getVoiceConfig().getBiometricProcessingMode();

        log.info("MSISDN : " + msisdn);
        log.info("Client ID : " + clientId);
        log.info("Application name : " + applicationName);
        log.info("sessionKey  : " + sessionKey);

        ValidSoftJsonBuilder validSoftJsonBuilder = new ValidSoftJsonBuilder();
        validSoftJsonBuilder.setIsUserEnrollRequestJsonJson(sessionKey, msisdn, processingMode);
        StringEntity postData = null;
        try {
            postData = new StringEntity(validSoftJsonBuilder.getIsUserEnrollRequestJson());
            log.debug("Json Object : " + postData);

            boolean isUserEnrolledInValidSoft = checkUserIsEnrolledValidSoftServer(isUserEnrolledUrl, postData);
            context.setProperty(IS_FLOW_COMPLETED, false);
            if (isUserEnrolledInValidSoft) {
                response.sendRedirect("https://localhost:9443/voice/rec.html?sessionDataKey=" + sessionKey);
            } else {
                response.sendRedirect("https://localhost:9443/voice/rec.html?sessionDataKey=" + sessionKey);
            }
        } catch (UnsupportedEncodingException ex) {
            log.error("Error in building the Request", ex);
            throw new AuthenticationFailedException("Error in Building the Request");
        } catch (IOException ex) {
            log.error("Error occurred while redirecting request", ex);
            throw new AuthenticationFailedException("Error occured while redirecting request");
        }
    }

    @Override
    protected void processAuthenticationResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationContext authenticationContext) throws AuthenticationFailedException {
        log.info("VoiceCallAuthenticator Authenticator process Authentication Response Triggered");
        String queryParams = FrameworkUtils.getQueryStringWithFrameworkContextId(authenticationContext.getQueryParams(),
                authenticationContext.getCallerSessionKey(), authenticationContext.getContextIdentifier());
        ApplicationConfig applicationConfig = authenticationContext.getSequenceConfig().getApplicationConfig();
        Map<String, String> paramMap = Util.createQueryParamMap(queryParams);
        String msisdn = (String) authenticationContext.getProperty(MSISDN);
        String clientId = paramMap.get(CLIENT_ID);
        String applicationName = applicationConfig.getApplicationName();
        String sessionKey = authenticationContext.getCallerSessionKey();
        String voiceBlob = httpServletRequest.getParameter(BLOB);
        String isUserEnrolledUrl = mobileConnectConfig.getVoiceConfig().getUserEnrollmentCheckEndpoint();
        String verifyUserUrl = mobileConnectConfig.getVoiceConfig().getVerifyUserEndpoint();
        String processingMode = mobileConnectConfig.getVoiceConfig().getBiometricProcessingMode();

        log.info("~~~~ MSISDN : " + msisdn);
        log.info("~~~~ Client ID : " + clientId);
        log.info("~~~~ Application name : " + applicationName);
        log.info("~~~~ sessionKey  : " + sessionKey);
        log.info("~~~~ Voice blob recieved" + voiceBlob);
        ValidSoftJsonBuilder validSoftJsonBuilder = new ValidSoftJsonBuilder();
        validSoftJsonBuilder.setIsUserEnrollRequestJsonJson(sessionKey, msisdn, processingMode);
        StringEntity postData = null;

        try {
            postData = new StringEntity(validSoftJsonBuilder.getIsUserEnrollRequestJson());
            log.debug("Json Object : " + postData);
            boolean isUserEnrolledInValidSoft = checkUserIsEnrolledValidSoftServer(isUserEnrolledUrl, postData);
            if (isUserEnrolledInValidSoft) {
                validSoftJsonBuilder.setVerifyUserJson(sessionKey,msisdn,processingMode,voiceBlob);
                postData = new StringEntity(validSoftJsonBuilder.getVerifyUserJson());
                verifyUserFromValidSoftServer(verifyUserUrl , postData);
                AuthenticationContextHelper.setSubject(authenticationContext, (String) authenticationContext.getProperty(Constants.MSISDN));
                authenticationContext.setProperty(IS_FLOW_COMPLETED, true);
            } else {
                authenticationContext.setProperty(IS_FLOW_COMPLETED, true);
                AuthenticationContextHelper.setSubject(authenticationContext, (String) authenticationContext.getProperty(Constants.MSISDN));
            }
        } catch (UnsupportedEncodingException ex) {
            log.error("Error in building the Request", ex);
            throw new AuthenticationFailedException("Error in Building the Request");
        } catch (IOException ex) {
            log.error("Error occurred while redirecting request", ex);
            throw new AuthenticationFailedException("Error occured while redirecting request");
        }

    }

    @Override
    public boolean canHandle(HttpServletRequest httpServletRequest) {
        log.info("VoiceCallAuthenticator canHandle invoked");
        return true;
    }

    @Override
    public String getContextIdentifier(HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public String getName() {
        return Constants.VOICECALL_AUTHENTICATOR_NAME;
    }

    @Override
    public String getFriendlyName() {
        return Constants.VOICECALL_AUTHENTICATOR_FRIENDLY_NAME;
    }


    private boolean checkUserIsEnrolledValidSoftServer(String url, StringEntity postData) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        postData.setContentType("application/json");
        httpPost.setEntity(postData);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        log.info("Http code retirned from IsuserEnrolled" + httpResponse.getStatusLine().getStatusCode());
        if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            log.error("ValidSoft server replied with invalid HTTP status [ " + httpResponse.getStatusLine().getStatusCode()
                    + "] ");
            throw new IOException(
                    "Error occurred while Calling ValidSoft server");

        } else {
            log.info("ValidSoft server replied with OK HTTP status [ " + httpResponse.getStatusLine().getStatusCode());
            return true;
        }

    }

    private boolean verifyUserFromValidSoftServer(String url, StringEntity postData) throws IOException {
        log.info("~~~~ Calling VlaidSoft to verify User ");
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        postData.setContentType("application/json");
        httpPost.setEntity(postData);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            log.error("ValidSoft server replied with invalid HTTP status [ " + httpResponse.getStatusLine().getStatusCode()
                    + "] ");
            throw new IOException(
                    "Error occurred while Calling ValidSoft server");

        } else {
            log.info("ValidSoft server replied with OK HTTP status [ " + httpResponse.getStatusLine().getStatusCode());
            return true;
        }




    }


}
