/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.MissingResourceException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Search template service to create, view, and update SearchTemplateVo
 */
public interface SearchTemplateService {

    /**
     * Saves the search criteria as a search template
     * 
     * @param user - user that wishes to save the template
     * @param search - the search that should be saved as a template
     * @param b - overwrite if duplicates exist
     * @return boolean that indicates if duplicates exist
     * @throws MissingResourceException exception
     * @throws ValueObjectValidationException exception
     */
    boolean create(UserVo user, SearchTemplateVo search, boolean b) throws MissingResourceException,
        ValueObjectValidationException;

    /**
     * Removes the search template
     * 
     * @param user - user wishing to remove search template
     * @param template - template to be removed
     * @throws ValueObjectValidationException exception
     * @throws MissingResourceException exception
     */
    void delete(UserVo user, SearchTemplateVo template) throws ValueObjectValidationException, MissingResourceException;

    /**
     * Retrieves the saved search templates
     * 
     * @param user - User that wishes to retrieve templates
     * @return List of templates
     * @throws MissingResourceException exception
     */
    List<SearchTemplateVo> retrieve(UserVo user) throws MissingResourceException;

    /**
     * Retrieves a selected search by Id
     * 
     * @param templateId - search template to retrieve
     * @return search template
     * @throws ItemNotFoundException exception
     */
    SearchTemplateVo retrieve(String templateId) throws ItemNotFoundException;

}
