/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.servlet;


import gov.va.med.pharmacy.peps.common.servlet.AbstractPepsServlet;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.session.DrugReferenceService;


/**
 * Retrieve the PMI information.
 */
public class PatientMedicationInformationServlet extends AbstractPepsServlet<DrugReferenceService> {
    private static final long serialVersionUID = 1L;

    /**
     * The method retrieves the PMI data from firstdatabase.
     * 
     * @param xmlRequest String request from Vista
     * @return String XML Response for Vista
     */
    @Override
    protected String getResponse(String xmlRequest) {
        return getService().retrievePatientMedicationInformation(xmlRequest);
    }
}
