/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.test;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.TransformerException;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.external.common.document.ExceptionDocument;
import gov.va.med.pharmacy.peps.external.common.document.ResponseDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.LocalVistADomainRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.LocalVistADomainResponseDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.NdcItemDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.NdfDomainDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.OrderableItemDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.PdmDomainDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.ProductItemDocument;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.ndf.NdfDomain;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.pdm.PdmDomain;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.facility.vista.request.VistaDomainsRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.facility.vista.response.VistaDomainsResponse;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.ndcitem.NdcItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.orderableitem.OrderableItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.productitem.ProductItem;
import gov.va.med.pharmacy.peps.external.common.vo.status.response.Response;

import junit.framework.TestCase;


/**
 * Tests the example XML documents.
 * 
 * 
 */
public class XmlDocumentTest extends TestCase {

    private static final String NOTNULL = "item shouldn't be null!";
    
    /**
     * setUp
     * @throws Exception exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
    }

    /**
     * Test NDC Item.
     * 
     * @throws Exception exception
     */
    public void testNdcItemDocument() throws Exception {
        NdcItem item = NdcItemDocument.instance().unmarshal(readDocument(new File("etc/xml/document/item/ndcItem.xml")));
        assertNotNull(NOTNULL, item);

        String xml = NdcItemDocument.instance().marshal(item);

        assertFalse("UPN should not be nil", NdcItemDocument.instance().unmarshal(xml).getNdcUpnFile().getUpn().isNil());
    }

    /**
     * Test nullable NDC Item.
     * 
     * @throws Exception exception
     */
    public void testNullableNdcItemDocument() throws Exception {
        NdcItem item = NdcItemDocument.instance().unmarshal(
            readDocument(new File("etc/xml/document/item/ndcItem-Nullable.xml")));
        assertNotNull(NOTNULL, item);

        // (mspears:Nov 17, 2008) Defect in JAXB - https://jaxb.dev.java.net/issues/show_bug.cgi?id=565
        //      (jbarde: 29 Mar 2012) - defect fixed as of JAXB 2.1.10, which is used currently. 
        item.getNdcUpnFile().getOtxRxIndicator().setNil(false);
        item.getNdcUpnFile().getPackageSize().setNil(false);
        item.getNdcUpnFile().getPackageType().setNil(false);
        item.getNdcUpnFile().getTradeName().setNil(false);

        String xml = NdcItemDocument.instance().marshal(item);
        assertTrue("UPN should be nil", NdcItemDocument.instance().unmarshal(xml).getNdcUpnFile().getUpn().isNil());
    }

    /**
     * Test Orderable Item.
     * 
     * @throws Exception exception
     */
    public void testOrderableItemDocument() throws Exception {
        OrderableItem item = OrderableItemDocument.instance().unmarshal(
            readDocument(new File("etc/xml/document/item/orderableItem.xml")));
        assertNotNull(NOTNULL, item);

        String xml = OrderableItemDocument.instance().marshal(item);
        OrderableItemDocument.instance().unmarshal(xml);
    }

    /**
     * Test Response.
     * 
     * @throws Exception exception
     */
    public void testResponseDocument() throws Exception {
        Response item = ResponseDocument.instance().unmarshal(
            readDocument(new File("etc/xml/document/status/response (queued).xml")));
        assertNotNull(NOTNULL, item);

        Response item2 = ResponseDocument.instance().unmarshal(
            readDocument(new File("etc/xml/document/status/response (failure).xml")));
        assertNotNull(NOTNULL, item2);

        Response item3 = ResponseDocument.instance().unmarshal(
            readDocument(new File("etc/xml/document/status/response (success).xml")));
        assertNotNull(NOTNULL, item3);

        String xml = ResponseDocument.instance().marshal(item);
        ResponseDocument.instance().unmarshal(xml);
    }

    /**
     * Test Exception.
     * 
     * @throws Exception exception
     */
    public void testExceptionDocument() throws Exception {
        gov.va.med.pharmacy.peps.external.common.preencapsulation.vo.exception.Exception item = ExceptionDocument.instance()
            .unmarshal(readDocument(new File("etc/xml/document/status/exception.xml")));
        assertNotNull(NOTNULL, item);

        String xml = ExceptionDocument.instance().marshal(item);

        ExceptionDocument.instance().unmarshal(xml);
    }

    /**
     * Test Product Item.
     * 
     * @throws Exception exception
     */
    public void testProductItemDocument() throws Exception {
        ProductItem item = ProductItemDocument.instance().unmarshal(
            readDocument(new File("etc/xml/document/item/productItem.xml")));
        assertNotNull(NOTNULL, item);

        String xml = ProductItemDocument.instance().marshal(item);

        ProductItemDocument.instance().unmarshal(xml);
    }

    /**
     * Test Ndf Domain.
     * 
     * @throws Exception exception
     */
    public void testNdfDomainDocument() throws Exception {
        NdfDomain item = NdfDomainDocument.instance().unmarshal(
            readDocument(new File("etc/xml/document/domain/ndfDomain.xml")));
        assertNotNull(NOTNULL, item);

        String xml = NdfDomainDocument.instance().marshal(item);
        NdfDomainDocument.instance().unmarshal(xml);
    }

    /**
     * Test Pdm Domain.
     * 
     * @throws Exception exception
     */
    public void testPdmDomainDocument() throws Exception {
        PdmDomain item = PdmDomainDocument.instance().unmarshal(
            readDocument(new File("etc/xml/document/domain/pdmDomain.xml")));
        assertNotNull(NOTNULL, item);

        String xml = PdmDomainDocument.instance().marshal(item);
        PdmDomainDocument.instance().unmarshal(xml);
    }

    /**
     * Test Inactivate action with invalid XML.
     * 
     * @throws Exception exception
     */
    public void testInactivateActionInDocument() throws Exception {
        try {
            NdfDomain item = NdfDomainDocument.instance().unmarshal(
                readDocument(new File("etc/xml/document/domain/ndfDomain (inactivate).xml")));

            fail("expecting an exception for " + item.toString());
        } catch (InterfaceException e) {
            assertTrue("expecting an exception", e.getCause() instanceof TransformerException);
        }
    }

    /**
     * Test Vista Domain request.
     * 
     * @throws Exception exception
     */
    public void testVistADomainRequestDocument() throws Exception {
        VistaDomainsRequest item = LocalVistADomainRequestDocument.instance().unmarshal(
            readDocument(new File("etc/xml/document/facility/localVistADomainRequest.xml")));
        assertNotNull(NOTNULL, item);

        String xml = LocalVistADomainRequestDocument.instance().marshal(item);
        LocalVistADomainRequestDocument.instance().unmarshal(xml);
    }

    /**
     * Test Vista Domain response.
     * 
     * @throws Exception exception
     */
    public void testVistADomainResponseDocument() throws Exception {
        VistaDomainsResponse item = LocalVistADomainResponseDocument.instance().unmarshal(
            readDocument(new File("etc/xml/document/facility/localVistADomainResponse.xml")));
        assertNotNull(NOTNULL, item);

        String xml = LocalVistADomainResponseDocument.instance().marshal(item);

        LocalVistADomainResponseDocument.instance().unmarshal(xml);
    }

    /**
     * Read XML document for the XmlDocumentTest 
     * 
     * 
     * @param file file to read
     * @return string
     * @throws IOException exception
     */
    private String readDocument(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Read XML document for the XmlDocumentTest
        try {
            byte[] buffer = new byte[PPSConstants.I1024];

            for (int length = in.read(buffer); length > 0; length = in.read(buffer)) {
                out.write(buffer, 0, length);
            }
        } finally {
            in.close();
            out.close();
        }

        // return the Read XML document for the XmlDocumentTest
        return new String(out.toByteArray());
    }
}
