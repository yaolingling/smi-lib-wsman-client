/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableDuration;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableURI;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.w3._2003._05.soap_envelope.Envelope;
import org.w3._2003._05.soap_envelope.Header;
import org.xmlsoap.schemas.ws._2004._08.addressing.AttributedURI;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;

import com.dell.isg.smi.wsmanclient.IWSManClient;
import com.dell.isg.smi.wsmanclient.IWSManCommand;
import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.WSManRuntimeException;
import com.dell.isg.smi.wsmanclient.util.SSLUtil;
import com.dell.isg.smi.wsmanclient.util.WSManUtils;

public class DefaultWSManClient implements IWSManClient {
    private static final Logger logger = LoggerFactory.getLogger(DefaultWSManClient.class);
    private static final Logger wsmanlogger = LoggerFactory.getLogger("WSMANLOGGER");
    private static final JAXBContext JAXB_CONTEXT = newJAXBContext();
    private static final ThreadLocal<DatatypeFactory> DATATYPE_FACTORY_THREAD_LOCAL = new ThreadLocal<DatatypeFactory>();

    private static final String PROP_LOGGING_ENABLED = "com.dell.pg.orion.wsman.loggingEnabled";
    final String wsManLoggingEnabled = System.getProperty(PROP_LOGGING_ENABLED);

    protected final URL destination;
    // protected final int maxElements;
    protected final String username;
    protected final String password;
    protected URLConnection connection;


    private static JAXBContext newJAXBContext() {
        try {
            return JAXBContext.newInstance(
                    // External schema: SOAP envelope, WS-Addressing, WS-Man
                    org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory.class, org.xmlsoap.schemas.ws._2004._08.addressing.ObjectFactory.class, org.xmlsoap.schemas.ws._2004._09.enumeration.ObjectFactory.class, org.w3._2003._05.soap_envelope.ObjectFactory.class);
        } catch (JAXBException e) {
            throw new WSManRuntimeException(e);
        }
    }


    protected static DatatypeFactory datatypeFactory() {
        DatatypeFactory ret = DATATYPE_FACTORY_THREAD_LOCAL.get();
        if (ret == null) {
            try {
                ret = DatatypeFactory.newInstance();
                DATATYPE_FACTORY_THREAD_LOCAL.set(ret);
            } catch (DatatypeConfigurationException e) {
                // We don't have any custom configuration; should be impossible
                throw new WSManRuntimeException(e);
            }
        }
        return ret;
    }


    protected DefaultWSManClient(String ip, String username, String password) {
        this(buildDestination(ip, 443, true), username, password);
    }


    protected DefaultWSManClient(String ip, int port, String username, String password) {
        this(buildDestination(ip, port, true), username, password);
    }


    protected DefaultWSManClient(URL destination, String username, String password) {
        this.destination = destination;
        // this.maxElements = 256;
        this.username = username;
        this.password = password;
    }


    public static URL buildDestination(String host, int port, boolean isHttps) {
        String url = (isHttps ? "https" : "http") + "://" + host + ":" + port + "/wsman";
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new WSManRuntimeException(e);
        }
    }


    protected Marshaller createMarshaller() {
        try {
            Marshaller ret = JAXB_CONTEXT.createMarshaller();
            ret.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            return ret;
        } catch (JAXBException e) {
            // Should fail every time or never; we are not changing the above between execution
            throw new WSManRuntimeException(e);
        }
    }


    protected <T> String buildWSManRequest(UUID messageId, IWSManCommand<T> cmd) throws WSManException {
        org.w3._2003._05.soap_envelope.ObjectFactory env = new org.w3._2003._05.soap_envelope.ObjectFactory();
        org.xmlsoap.schemas.ws._2004._08.addressing.ObjectFactory wsa = new org.xmlsoap.schemas.ws._2004._08.addressing.ObjectFactory();
        org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory wsman = new org.dmtf.schemas.wbem.wsman._1.wsman.ObjectFactory();

        QName mustUnderstand = new QName(WSManUtils.findJAXBNamespace(env.getClass()), "mustUnderstand");

        Marshaller m = createMarshaller();
        Envelope envelope = env.createEnvelope();

        Header header = env.createHeader();
        envelope.setHeader(header);

        // OperationTimeout header
        String timeout = cmd.getTimeout();
        if (timeout != null && !timeout.isEmpty()) {
            DatatypeFactory df = datatypeFactory();
            AttributableDuration attributableDuration = wsman.createAttributableDuration();
            attributableDuration.setValue(df.newDuration(timeout));
            JAXBElement<AttributableDuration> operationTimeout = wsman.createOperationTimeout(attributableDuration);
            header.getAny().add(operationTimeout);
        }

        // ReplyTo header
        EndpointReferenceType address = wsa.createEndpointReferenceType();
        address.getOtherAttributes();
        AttributedURI replyToURI = wsa.createAttributedURI();
        replyToURI.setValue("http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous");
        replyToURI.getOtherAttributes().put(mustUnderstand, "true");
        address.setAddress(replyToURI);
        JAXBElement<EndpointReferenceType> replyTo = wsa.createReplyTo(address);
        header.getAny().add(replyTo);

        // MessageID header
        AttributedURI messageURI = wsa.createAttributedURI();
        messageURI.setValue("uuid:" + messageId);
        messageURI.getOtherAttributes().put(mustUnderstand, "true");
        JAXBElement<AttributedURI> messageID = wsa.createMessageID(messageURI);
        header.getAny().add(messageID);

        // To header
        AttributedURI toURI = wsa.createAttributedURI();
        toURI.setValue(destination.toExternalForm());
        toURI.getOtherAttributes().put(mustUnderstand, "true");
        JAXBElement<AttributedURI> to = wsa.createTo(toURI);
        header.getAny().add(to);

        // ResourceURI header
        AttributableURI resourceURI = wsman.createAttributableURI();
        resourceURI.setValue(cmd.getResourceURI());
        JAXBElement<AttributableURI> resourceURIElem = wsman.createResourceURI(resourceURI);
        header.getAny().add(resourceURIElem);

        // Action header
        AttributedURI actionURI = wsa.createAttributedURI();
        actionURI.setValue(cmd.getAction());
        actionURI.getOtherAttributes().put(mustUnderstand, "true");
        JAXBElement<AttributedURI> action = wsa.createAction(actionURI);
        header.getAny().add(action);

        // SelectorSet header (if any)
        List<Pair<String, String>> selectors = cmd.getSelectors();
        if (selectors != null) {
            SelectorSetType selectorSetType = wsman.createSelectorSetType();
            JAXBElement<SelectorSetType> selectorSet = wsman.createSelectorSet(selectorSetType);
            header.getAny().add(selectorSet);

            for (Pair<String, String> kv : selectors) {
                SelectorType selectorType = wsman.createSelectorType();
                selectorType.setName(kv.getKey());
                selectorType.getContent().add(kv.getValue());
                selectorSetType.getSelector().add(selectorType);
            }
        }

        envelope.setBody(cmd.getBody());

        try {
            JAXBElement<Envelope> element = env.createEnvelope(envelope);
            StringWriter writer = new StringWriter();
            m.marshal(element, writer);
            return writer.toString();
        } catch (JAXBException e) {
            WSManException ret = new WSManException("Failed to build WS-Man payload: " + e.getMessage());
            ret.initCause(e);
            throw ret;
        }
    }


    private void configureSSL(HttpsURLConnection httpsURLConnection) {
        httpsURLConnection.setSSLSocketFactory(SSLUtil.getTrustingSSLSocketFactory());
        httpsURLConnection.setHostnameVerifier(SSLUtil.getTrustingHostnameVerifier());
    }


    private void configureAuthorization(URLConnection uc) {
        String userpass = username + ":" + password;
        String authString = null;
        try {
            authString = "Basic " + DatatypeConverter.printBase64Binary(userpass.getBytes("ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            logger.warn("unsupported Encoding exception", e);
        }
        uc.setRequestProperty("Authorization", authString);
    }


    @Override
    public <T> T execute(IWSManCommand<T> cmd) throws IOException, WSManException {
        return cmd.parse(executeXML(cmd));
    }


    @Override
    public <T> String executeXML(IWSManCommand<T> cmd) throws IOException, WSManException {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        InputStreamReader in1 = null;

        try {
            UUID messageId = UUID.randomUUID();

            connection = destination.openConnection();
            if (connection instanceof HttpsURLConnection) {
                configureSSL((HttpsURLConnection) connection);
            }
            configureAuthorization(connection);
            connection.setRequestProperty("Content-Type", "application/soap+xml;charset=utf-8");
            // connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Keep-Alive", "header");
            connection.setDoOutput(true);
            connection.setConnectTimeout(15000);

            out = new OutputStreamWriter(connection.getOutputStream(), "UTF8");
            String requestStr = buildWSManRequest(messageId, cmd);
            // because WSMAN logs will have passwords, we require enabling at log4j level, and require a system property to be set.
            if (wsManLoggingEnabled != null && wsManLoggingEnabled.equalsIgnoreCase(Boolean.TRUE.toString())) {
                wsmanlogger.debug("WSMAN - REQUEST - " + requestStr);
            }
            out.write(requestStr);
            out.close();
            out = null; // no need to close it in finally block

            in1 = new InputStreamReader(connection.getInputStream(), "UTF8");
            in = new BufferedReader(in1);
            StringBuilder buf = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                buf.append(line);
            }
            String responseStr = buf.toString();
            // because WSMAN logs will have passwords, we require enabling at log4j level, and require a system property to be set.
            if (wsManLoggingEnabled != null && wsManLoggingEnabled.equalsIgnoreCase(Boolean.TRUE.toString())) {
                wsmanlogger.debug("WSMAN - RESPONSE - " + responseStr);
            }
            return responseStr;
        } catch (IOException ioe) {
            int rc = this.checkResponse(connection);
            if (rc == HttpURLConnection.HTTP_UNAUTHORIZED) {
                logger.error(ioe.getMessage());
                throw new NotAuthorizedException(ioe);
            }
            if (rc == HttpURLConnection.HTTP_BAD_REQUEST) {
                logger.error(ioe.getMessage());
                throw new BadRequestException(ioe);
            }
            throw ioe;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.warn("Failed to close " + destination + " output", e);
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.warn("Failed to close " + destination + " input", e);
                }
            }

            if (in1 != null) {
                try {
                    in1.close();
                } catch (IOException e) {
                    logger.warn("Failed to close " + destination + " input", e);
                }
            }
        }
    }


    @Override
    public void close() {
        if (null != connection) {
            try {
                if (connection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) connection).disconnect();
                } else if (connection instanceof HttpURLConnection) {
                    ((HttpURLConnection) connection).disconnect();
                }
            } catch (Exception e) {
            }
        }
    }


    public String getIpAddress() {
        return destination.getHost();
    }


    public String getUser() {
        return username;
    }


    public String getPassword() {
        return password;
    }
    
    private int checkResponse(URLConnection c) throws IOException {
        int respCode = 0;
        if ( c instanceof HttpURLConnection || c instanceof HttpsURLConnection) {
        	HttpURLConnection connection = HttpURLConnection.class.cast(c);
	        try {
	            respCode = connection.getResponseCode();
	        } catch (Exception e) {
	            logger.error(e.getMessage());
	            return HttpURLConnection.HTTP_INTERNAL_ERROR;
	        }
	        switch (respCode) {
	        case HttpURLConnection.HTTP_OK:
	            logger.debug("HTTP response: OK");
	            break;
	        case HttpURLConnection.HTTP_BAD_REQUEST:
	            logger.info("HTTP response: Bad Request");
	            break;
	        case HttpURLConnection.HTTP_UNAUTHORIZED:
	            logger.info("HTTP response: Unauthorized");
	            break;
	        case HttpURLConnection.HTTP_BAD_METHOD:
	            logger.info("HTTP response: Method Not Allowed");
	            break;
	        case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
	            logger.info("HTTP response: Request Time-out");
	            break;
	        case HttpURLConnection.HTTP_UNAVAILABLE:
	            logger.info("HTTP response: Service Unavailable");
	            break;
	        default:
	            logger.info("HTTP response: Unknown Reason");
	            break;
	        }
        }
        return respCode;

    }

}
