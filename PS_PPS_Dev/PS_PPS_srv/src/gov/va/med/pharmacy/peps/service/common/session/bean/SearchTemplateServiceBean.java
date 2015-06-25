/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.bean;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.MissingResourceException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.SearchTemplateService;


/**
 * Perform simple and advanced search
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class SearchTemplateServiceBean extends AbstractPepsStatelessSessionBean<SearchTemplateService> implements
    SearchTemplateService {

    private static final long serialVersionUID = 1L;

    /**
     * Retrieves the saved search templates
     * 
     * @param user UserVo
     * @return Map<String, SearchCriteiraVo>
     * @throws MissingResourceException if error
     * 
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    public List<SearchTemplateVo> retrieve(UserVo user) throws MissingResourceException {
        return this.getService().retrieve(user);
    }

    /**
     * Saves the search criteria as a search template
     * 
     * @param user - user that wishes to save the template
     * @param search - the search that should be saved as a template
     * @param warned - user has been warned of duplicates
     * @return boolean indicating if duplicates exist
     * @throws MissingResourceException if error
     * @throws ValueObjectValidationException exception
     * 
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    public boolean create(UserVo user, SearchTemplateVo search, boolean warned) throws MissingResourceException,
        ValueObjectValidationException {
        return this.getService().create(user, search, warned);
    }

    /**
     * Retrieves a selected search by Id
     * 
     * @param templateId - search template to retrieve
     * @return search template
     * @throws ItemNotFoundException exception
     * 
     * @ejb.interface-method
     * 
     * @ejb.transaction type = "Required"
     */
    public SearchTemplateVo retrieve(String templateId) throws ItemNotFoundException {
        return this.getService().retrieve(templateId);
    }

    /**
     * Removes the user's search template
     * 
     * @param user UserVo
     * @param search SearchTemplateVo
     * @throws MissingResourceException exception
     * @throws ValueObjectValidationException exception
     * 
     * @ejb.interface-method
     * @ejb.transaction type = "Required"
     */
    public void delete(UserVo user, SearchTemplateVo search) throws MissingResourceException, ValueObjectValidationException {
        this.getService().delete(user, search);
    }

}
