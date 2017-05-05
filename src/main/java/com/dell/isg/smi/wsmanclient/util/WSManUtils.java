/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPBody;
import javax.xml.stream.XMLInputFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.wsmanclient.WSCommandRNDConstant;
import com.dell.isg.smi.wsmanclient.WSManConstants;
import com.dell.isg.smi.wsmanclient.WSManException;
import com.dell.isg.smi.wsmanclient.WSManRuntimeException;

public final class WSManUtils {
    private WSManUtils() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WSManUtils.class);

    private static final SimpleDateFormat WSMAN_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss.SSSSSSZ");

    static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = buildDocumentFactory();
    static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();

    private static final ThreadLocal<DocumentBuilder> DOCUMENT_BUILDER_THREAD_LOCAL = new ThreadLocal<DocumentBuilder>();


    private static Document toDocumentHelper(String xmlRecords, boolean isRetry) throws WSManException {
        if (null == xmlRecords || StringUtils.isEmpty(xmlRecords)) {
            throw new WSManException("xmlRecords input source is null or blank");
        }
        Reader reader = null;
        Document doc = null;
        try {
            DocumentBuilder db = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
            InputSource is = new InputSource();
            reader = new StringReader(xmlRecords);
            if (isRetry) {
                reader = new XMLFixingReader(reader);
            }
            is.setCharacterStream(reader);
            doc = db.parse(is);
        } catch (ParserConfigurationException e) // should not occur; we have a fixed configuration
        {
            LOGGER.error("ParserConfigurationException ", e);
        } catch (SAXException e) {
            if (isRetry) {
                LOGGER.error("SAXException: Failed to parse XML", e);
            } else {
                // The return value from EnumerateIDRACCardStrCmd on 10.255.4.76 currently contains a username
                // with characters that are not valid XML characters. SAX therefore fails to parse it.
                // Here we try again with an XMLFixingReader which will replace all invalid characters
                // with '?' to see if we can fix that problem.
                LOGGER.warn("Failed to parse XML; retrying with stream stripped of invalid characters", e);
                doc = toDocumentHelper(xmlRecords, true);
            }
        } catch (IOException e) // should not occur; all our IO is on Strings
        {
            LOGGER.error("IOException ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.warn("Failed to close reader", e);
                }
            }
        }

        if (doc == null) {
            throw new WSManException("Failed to parse XML");
        }
        return doc;
    }


    public static Document toDocument(String xmlRecords) throws WSManException {
        return toDocumentHelper(xmlRecords, false);
    }


    public static boolean isMatchingElement(Node node, String tagName) {
        // Why are we doing equalsIgnoreCase? Aren't xml tags case-sensitive? This
        // check was copied from existing spectre WSMan code...
        return node.getNodeType() == Element.ELEMENT_NODE && node.getLocalName().equalsIgnoreCase(tagName);
    }


    public static Object findObjectInDocument(SOAPBody doc, String xPathLocation, QName qname, WSManConstants.WSManClassEnum commandEnum) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new PersonalNamespaceContext(buildResourceURI(commandEnum)));
        XPathExpression expr = xpath.compile(xPathLocation);
        Object result = expr.evaluate(doc, qname);
        return result;
    }


    private static DocumentBuilderFactory buildDocumentFactory() {
        DocumentBuilderFactory ret = DocumentBuilderFactory.newInstance();
        ret.setNamespaceAware(true);
        return ret;
    }


    public static String buildResourceURI(WSManConstants.WSManClassEnum commandEnum) {
        StringBuilder b = new StringBuilder();
        b.append(WSCommandRNDConstant.WSMAN_BASE_URI);
        b.append(WSCommandRNDConstant.WS_OS_SVC_NAMESPACE);
        b.append(commandEnum);
        return b.toString();
    }


    // buildResourceURI returns a schemas.dmtf.org namespace which the iDrac will accept as
    // input but the response namespaces are in schemas.dell.com. For now we buildDellResourceURI
    // can be used to get that version but in the future the resource URI should probably come
    // directly from the CommandEnum so that we can support non-Dell resource URIs.
    static String buildDellResourceURI(WSManConstants.WSManClassEnum commandEnum) {
        StringBuilder b = new StringBuilder();
        b.append("http://schemas.dell.com/wbem/wscim/1/cim-schema/2/");
        b.append(commandEnum);
        return b.toString();
    }


    private static WSManException getWSManException(String format, Object... args) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);
        return new WSManException(formatter.format(format, args).toString());
    }


    private static <T> T tryCast(Object o, Class<T> type) throws WSManException {
        if (o == null)
            return null;
        else {
            Class<?> targetType = o.getClass();
            if (type.isAssignableFrom(targetType))
                return type.cast(o);
            else if (o instanceof JAXBElement) {
                JAXBElement element = (JAXBElement) o;
                Object value = element.getValue();
                targetType = value.getClass();
                if (type.isAssignableFrom(targetType))
                    return type.cast(value);
            }
        }
        return null;
    }


    static <T> T castOrThrow(Object o, Class<T> type, String exceptionFormat) throws WSManException {
        if (o == null)
            return null;
        else {
            Class<?> targetType = o.getClass();
            if (type.isAssignableFrom(targetType))
                return type.cast(o);
            else if (o instanceof JAXBElement) {
                JAXBElement element = (JAXBElement) o;
                Object value = element.getValue();
                targetType = value.getClass();
                if (type.isAssignableFrom(targetType))
                    return type.cast(value);
            }
            throw getWSManException(exceptionFormat, targetType.getSimpleName());
        }
    }


    private static <T> T findAndCastOrThrow(List<Object> objects, Class<T> type) throws WSManException {
        for (Object object : objects) {
            T ret = tryCast(object, type);
            if (ret != null)
                return ret;
        }
        throw new WSManException("Could not find " + type.getSimpleName() + " object in response");
    }


    /**
     * Returns the namespace of the JAXB-annotated class enumerationClass by examining the class's package for an @XMLSchema annotation.
     *
     * @param enumerationClass JAXB-annotated class
     * @param <T> class type
     * @return The class's namespace, or null if none.
     */
    public static <T> String findJAXBNamespace(Class<T> enumerationClass) {
        String nsURI = null;
        for (Annotation annotation : enumerationClass.getPackage().getAnnotations()) {
            if (annotation.annotationType() == XmlSchema.class) {
                nsURI = ((XmlSchema) annotation).namespace();
                break;
            }
        }
        return nsURI;
    }


    public static Date parseWSManDateString(String xml) {
        if (xml == null)
            return null;
        else
            try {
                String ts = DateTimeUtils.normalizeTimeStamp(xml.trim());
                // Coverity: 10428 STCAL: Static use of type Calendar or DateFormat
                // As the JavaDoc states, DateFormats are inherently unsafe for
                // multithreaded use. (From FindBugs description) (CWE-366)
                Date date = WSMAN_DATE_FORMAT.parse(ts);
                return (Date) date.clone();
            } catch (ParseException e) {
                LOGGER.warn("Invalid WS-Man date string: " + xml);
                return null;
            }
    }


    public static String printWSManDateString(Date date) {
        // Coverity: 10429 STCAL: Static use of type Calendar or DateFormat
        // As the JavaDoc states, DateFormats are inherently unsafe for multithreaded use.
        // (From FindBugs description) (CWE-366)
        return WSMAN_DATE_FORMAT.format((Date) date.clone());
    }


    public static Document newDocument() {
        DocumentBuilder builder = DOCUMENT_BUILDER_THREAD_LOCAL.get();
        if (builder == null) {
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                builder = documentBuilderFactory.newDocumentBuilder();
                DOCUMENT_BUILDER_THREAD_LOCAL.set(builder);
            } catch (ParserConfigurationException e) {
                // We are not using any custom parser configuration so this should be impossible.
                throw new WSManRuntimeException(e);
            }
        }
        return builder.newDocument();
    }
}
