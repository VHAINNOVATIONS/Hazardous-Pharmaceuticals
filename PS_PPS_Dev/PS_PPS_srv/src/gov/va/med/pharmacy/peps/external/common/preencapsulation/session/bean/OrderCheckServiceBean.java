/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session.bean;


import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.OrderCheckService;


/**
 * Perform an order check requested by VistA using XML.
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class OrderCheckServiceBean extends AbstractPepsStatelessSessionBean<OrderCheckService> implements OrderCheckService {
    private static final long serialVersionUID = 1L;

    /**
     * Perform and order check for the given XML request.
     * 
     * @param request request XML from VistA
     * @return response XML
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public String performOrderCheck(String request) {
        return getService().performOrderCheck(request);
    }
}
