/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session.bean;


import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.DrugDataService;


/**
 * Retrieve the NDC and Products for a given list of NDC numbers and Vuids.
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class DrugDataServiceBean extends AbstractPepsStatelessSessionBean<DrugDataService> implements DrugDataService {
    private static final long serialVersionUID = 1L;

    /**
     * Retrieve the NDC and Products for the given XML request.
     * 
     * @param request request XML from VistA
     * @return response XML
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "RequiresNew"
     */
    public String retrieveDrugData(String request) {
        return getService().retrieveDrugData(request);
    }
}
