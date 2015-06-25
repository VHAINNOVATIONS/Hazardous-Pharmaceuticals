/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.MissingResourceException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateMessageVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * perform search operations
 */
public interface SearchTemplateCapability {

    /**
     * Retrieves the saved search templates
     * 
     * @param user - user wishing to retrieve templates
     * @return List<SearchTemplateVo> List of search templates
     * @throws MissingResourceException 
     */
    List<SearchTemplateVo> retrieveSearchTemplates(UserVo user) throws MissingResourceException;

    /**
     * Saves the search criteria as a search template
     * 
     * @param user - user that wishes to save the template
     * @param search - the search that should be saved as a template
     * @param warned - indicates that the user has been warned of duplicates
     * @return boolean indicating that duplicates exist
     * 
     * @throws MissingResourceException MissingResourceException
     * @throws ValueObjectValidationException ValueObjectValidationException
     */
    boolean saveSearch(UserVo user, SearchTemplateVo search, boolean warned) throws MissingResourceException,
        ValueObjectValidationException;

    /**
     * Saves the search that comes in the searchTemplateMessage
     * 
     * essentially saveSearch but does no validation (since this comes from national)
     * 
     * @param searchTemplateMessage template to save
     * @throws ValidationException if error validating search template
     */
    void saveSearch(SearchTemplateMessageVo searchTemplateMessage) throws ValidationException;

    /**
     * Retrieves the specified search for user
     * 
     * @param id - search to retrieve
     * @return search
     * @throws ItemNotFoundException 
     */
    SearchTemplateVo retrieve(String id) throws ItemNotFoundException;

    /**
     * Removes search template
     * 
     * @param user - user wishing to remove template
     * @param template - template to be removed
     * @throws MissingResourceException  exception
     * @throws ValueObjectValidationException  exception
     */
    void removeTemplate(UserVo user, SearchTemplateVo template) throws MissingResourceException, ValueObjectValidationException;
}
