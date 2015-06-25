/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.servlet;


import gov.va.med.pharmacy.peps.common.servlet.AbstractPepsServlet;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.DrugInfoService;


/**
 * Retrieve the dose routes and dose types for a given list of GCN sequence numbers.
 */
public class DrugInfoServlet extends AbstractPepsServlet<DrugInfoService> {
    private static final long serialVersionUID = 1L;

    /**
     * Retrieve the dose routes and dose types for a given list of GCN sequence numbers.
     * 
     * @param xmlRequest String request from Vista
     * @return String XML Response for Vista
     */
    @Override
    protected String getResponse(String xmlRequest) {
        return getService().retrieveDrugInformation(xmlRequest);
    }
}
