/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.utility.test;


import gov.va.med.pharmacy.peps.external.common.document.ExceptionDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.DrugCheckResponseDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.DrugInfoResponseDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.check.response.PEPSResponse;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.drug.info.response.DrugInfoResponse;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.exception.Exception;


/**
 * Wrapper class such that Presentation integration tests can marshal and unmarshal XML documents.
 */
public class XmlDocumentUtility {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String XML_SEPARATOR = "pre.interface.xml.line.separator";
    
    /**
     * XmlDocumentUtility
     */
    private XmlDocumentUtility() {
        
    }
    
    /**
     * String the given Drug Info response XML String with indentations.
     * 
     * @param xml Response XML
     * @return String formatted XML
     */
    public static String prettyPrintDrugInfoResponse(String xml) {
        System.setProperty(XML_SEPARATOR, LINE_SEPARATOR);
        
        return DrugInfoResponseDocument.instance().toIndentedString(xml);
    }

    /**
     * Return true if the two Drug Info response XML Strings are equal.
     * 
     * @param expectedXml Expected XML String
     * @param actualXml Actual XML String
     * @return boolean
     */
    public static boolean drugInfoResponseEquals(String expectedXml, String actualXml) {
        DrugInfoResponse expected = DrugInfoResponseDocument.instance().unmarshal(expectedXml);
        DrugInfoResponse actual = DrugInfoResponseDocument.instance().unmarshal(actualXml);

        return expected.equals(actual);
    }

    /**
     * String the given Order Check response XML String with indentations.
     * 
     * @param xml Response XML
     * @return String formatted XML
     */
    public static String prettyPrintOrderCheckResponse(String xml) {
        System.setProperty(XML_SEPARATOR, LINE_SEPARATOR);
        
        return DrugCheckResponseDocument.instance().toIndentedString(xml);
    }

    /**
     * Return true if the two Order Check response XML Strings are equal.
     * 
     * @param expectedXml Expected XML String
     * @param actualXml Actual XML String
     * @return boolean
     */
    public static boolean orderCheckResponseEquals(String expectedXml, String actualXml) {
        PEPSResponse expected = DrugCheckResponseDocument.instance().unmarshal(expectedXml);
        PEPSResponse actual = DrugCheckResponseDocument.instance().unmarshal(actualXml);

        return expected.equals(actual);
    }

    /**
     * String the given Exception response XML String with indentations.
     * 
     * @param xml Response XML
     * @return String formatted XML
     */
    public static String prettyPrintException(String xml) {
        System.setProperty(XML_SEPARATOR, LINE_SEPARATOR);
        
        return ExceptionDocument.instance().toIndentedString(xml);
    }

    /**
     * Return true if the two Exception response XML Strings are equal.
     * 
     * @param expectedXml Expected XML String
     * @param actualXml Actual XML String
     * @return boolean
     */
    public static boolean exceptionEquals(String expectedXml, String actualXml) {
        Exception expected = ExceptionDocument.instance().unmarshal(expectedXml);
        Exception actual = ExceptionDocument.instance().unmarshal(actualXml);

        return expected.getCode().value().equals(actual.getCode().value())
            && expected.getMessage().equals(actual.getMessage());
    }
}
