/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session.bean;


import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.DrugInfoService;


/**
 * Retrieve the dose routes and dose types for a given list of GCN sequence numbers.
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class DrugInfoServiceBean extends AbstractPepsStatelessSessionBean<DrugInfoService> implements DrugInfoService {
    private static final long serialVersionUID = 1L;

    /**
     * Retrieve the dose routes and dose types for the given XML request.
     * 
     * @param request request XML from VistA
     * @return response XML
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public String retrieveDrugInformation(String request) {
        return getService().retrieveDrugInformation(request);
    }
}
