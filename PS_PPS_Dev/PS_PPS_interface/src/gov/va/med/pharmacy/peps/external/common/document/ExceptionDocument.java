/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.document;


import java.io.PrintWriter;
import java.io.StringWriter;

import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.exception.PharmacyRuntimeException;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.exception.Exception;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.exception.ExceptionCodeType;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.exception.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.utility.XmlDocument;

import firstdatabank.database.FDBException;
import firstdatabank.dif.FDBUnknownTypeException;


/**
 * Marshal and unmarshal XML into Java objects.
 */
public class ExceptionDocument extends XmlDocument<Exception> {
    private static final String[] CDATA_ELEMENTS = {toNamespace(Exception.class) + "^detailedMessage"};
    private static final String[] SCHEMA_FILES = new String[] {"xml/schema/status/exception.xsd"};
    private static final String[] XSL_FILES = new String[] {};
    private static final ExceptionDocument INSTANCE = new ExceptionDocument();

    
    /**
     * Protected constructor
     */
    private ExceptionDocument() {
        super(Exception.class, CDATA_ELEMENTS, SCHEMA_FILES, XSL_FILES);
    }
    
    /**
     * Get instance of document.
     * 
     * @return instance
     */
    public static ExceptionDocument instance() {
        return INSTANCE;
    }



    /**
     * Creates an error XML response message
     * 
     * @param throwable Throwable to create XML error message from
     * @return String the XML error message
     */
    public String createXmlErrorMessage(Throwable throwable) {
        ObjectFactory objectFactory = new ObjectFactory();
        Exception exception = objectFactory.createException();

        // All FDB Exceptions extend FDBException, except for FDBUnknownTypeException which extends RuntimeException
        if (throwable instanceof FDBException || throwable instanceof FDBUnknownTypeException) {
            exception.setCode(ExceptionCodeType.FDB);
        } else if (throwable instanceof PharmacyException || throwable instanceof PharmacyRuntimeException) {
            exception.setCode(ExceptionCodeType.PRE);
        } else if (throwable instanceof java.lang.Exception) {
            exception.setCode(ExceptionCodeType.JAVA);
        } else {
            exception.setCode(ExceptionCodeType.SYSTEM);
        }

        if (throwable.getLocalizedMessage() == null) {
            exception.setMessage("");
        } else {
            exception.setMessage(throwable.getLocalizedMessage());
        }

        StringWriter stackTrace = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stackTrace));
        exception.setDetailedMessage(stackTrace.toString());

        return marshal(exception);
    }
}
