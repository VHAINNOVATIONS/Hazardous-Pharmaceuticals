/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.session.bean;


import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.StatusConsoleVo;
import gov.va.med.pharmacy.peps.service.local.session.ConsoleService;


/**
 * A Local Status Console (System Information) has a national site config vo and a list with 1 local site config (this site)
 * (always only 1) A National Status Console (System Information) has a national site config vo and a list with 0..n local
 * site config from the national db table
 * 
 * @ejb.bean
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 * 
 */
public class ConsoleServiceBean extends AbstractPepsStatelessSessionBean<ConsoleService> implements ConsoleService {
    private static final long serialVersionUID = 1;

    /**
     * LocalConsoleServiceBean getConsole
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     * 
     * @return StatusConsoleVo
     * 
     * @see gov.va.med.pharmacy.peps.service.local.session.ConsoleService#getConsole()
     */
    public StatusConsoleVo getConsole() {
        return getService().getConsole();
    }
}
