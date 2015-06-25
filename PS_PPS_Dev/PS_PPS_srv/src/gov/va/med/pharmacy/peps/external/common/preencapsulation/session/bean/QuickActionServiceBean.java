/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session.bean;


import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.QuickActionService;


/**
 * description
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class QuickActionServiceBean extends AbstractPepsStatelessSessionBean<QuickActionService> implements
    QuickActionService {
    private static final long serialVersionUID = 1L;

    /**
     * description
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "RequiresNew"
     * 
     * @param user The user who initiated this call
     */
    public void sendDrugFileToExternalInterface(UserVo user) {
        getService().sendDrugFileToExternalInterface(user);
    }
}
