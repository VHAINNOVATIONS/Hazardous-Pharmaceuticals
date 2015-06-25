/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.TemplateLocation;
import gov.va.med.pharmacy.peps.service.common.session.PrintTemplateService;


/**
 * Perform create, retrieve, update, and delete operations on PrintTemplateVo
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class PrintTemplateServiceBean extends AbstractPepsStatelessSessionBean<PrintTemplateService> implements
    PrintTemplateService {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new Print Template for the given user.
     * 
     * @param user UserVo
     * @param printTemplate PrintTemplateVo to persist
     * @return persisted Print Template
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public PrintTemplateVo create(UserVo user, PrintTemplateVo printTemplate) {
        return getService().create(user, printTemplate);
    }

    /**
     * Delete the given Print Template
     * 
     * @param printTemplate PrintTemplateVo to delete
     * @return deleted PrintTemplateVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public PrintTemplateVo delete(PrintTemplateVo printTemplate) {
        return getService().delete(printTemplate);
    }

    /**
     * Retrieve the PrintTemplate for the user at the given location.
     * 
     * @param user UserVo
     * @param templateLocation Location where table/print template is to be displayed
     * @return PrintTemplateVo
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public PrintTemplateVo retrieve(UserVo user, TemplateLocation templateLocation) {
        return getService().retrieve(user, templateLocation);
    }
}
