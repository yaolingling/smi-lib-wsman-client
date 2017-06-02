/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.util;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SSLUtil.
 */
public class SSLUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSLUtil.class);


    /**
     * Gets the trusting SSL socket factory.
     *
     * @return the trusting SSL socket factory
     */
    public static SSLSocketFactory getTrustingSSLSocketFactory() {
        return LazyHolder.SSL_SOCKET_FACTORY;
    }


    /**
     * Gets the trusting hostname verifier.
     *
     * @return the trusting hostname verifier
     */
    public static HostnameVerifier getTrustingHostnameVerifier() {
        return LazyHolder.HOSTNAME_VERIFIER;
    }

    /**
     * The Class LazyHolder.
     */
    private static class LazyHolder {
        private static final SSLSocketFactory SSL_SOCKET_FACTORY = createSSLSocketFactory();
        private static final HostnameVerifier HOSTNAME_VERIFIER = new TrustingHostnameVerifier();
    }

    /**
     * The Class TrustingHostnameVerifier.
     */
    private static class TrustingHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession sslSession) {
            LOGGER.info("Trusting ssl hostname " + hostname);
            return true;
        }
    }


    /**
     * Creates the SSL socket factory.
     *
     * @return the SSL socket factory
     */
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLContext sslContext =  buildSSLContext();
        if (null != sslContext){
            return sslContext.getSocketFactory();
        }
        return null;
    }


    /**
     * Builds the SSL context.
     *
     * @return the SSL context
     */
    private static SSLContext buildSSLContext() {
        // For now we don't do any certificate checking...
        LOGGER.info("Certificate checking is disabled.");
        try {
            TrustManager[] tm2 = { new OverlyTrustingTrustManager(), };
            SSLContext ret = SSLContext.getInstance("SSL");
            ret.init(null, tm2, new SecureRandom());
            return ret;
        } catch (Exception e) {
            LOGGER.error("Failed to buildSSLContext", e);
        }
        return null;
    }

    /**
     * The Class OverlyTrustingTrustManager.
     */
    // Trust Manager which will NOT do any Cert checking
    private static class OverlyTrustingTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }


        /* (non-Javadoc)
         * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
         */
        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            LOGGER.info("Skipping SSL Certificate checking for: " + arg1);
        }


        /* (non-Javadoc)
         * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
         */
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
