/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.MissingResourceException;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Perform CRUD operations on search template
 */
public interface SearchTemplateDomainCapability {

    /**
     * Creates a new SearchTemplate
     * 
     * @param templateOwner template owner
     * @param searchCriteria template to be saved
     * @return SearchTemplateVo
     * @throws DuplicateItemException exception
     */
    SearchTemplateVo create(UserVo templateOwner, SearchTemplateVo searchCriteria) throws DuplicateItemException;

    /**
     * Retrieves all search criteria for the specified template owner
     * 
     * @param templateOwner template owner
     * @return List<SearchTemplateVo>
     */
    List<SearchTemplateVo> retrieveDomain(UserVo templateOwner);

    /**
     * Retrieve Search template by Id
     * 
     * @param eplId criteria ID
     * @return SearchTemplateVo
     * @throws ItemNotFoundException exception
     */
    SearchTemplateVo retrieve(Long eplId) throws ItemNotFoundException;

    /**
     * Retrieve Search Template by name
     * 
     * @param templateName template's name (should be unique)
     * @return SearchCriteriaVo
     * @throws ItemNotFoundException exception
     */
    SearchTemplateVo retrieveByTemplateName(String templateName) throws ItemNotFoundException;

    /**
     * Check if the search template already exists
     * 
     * @param templateName template name to check
     * @return true if template with the same name is already in the system, false otherwise
     */
    boolean exists(String templateName);

    /**
     * Retrieves the saved search templates
     * 
     * @param user - user for which templates should be returned
     * @return List of saved searches
     * @throws MissingResourceException exception
     */
    List<SearchTemplateVo> retrieveSearchTemplates(UserVo user) throws MissingResourceException;

    /**
     * Saves the search criteria as a search template
     * 
     * @param user - user that wishes to save the template
     * @param search - the search that should be saved as a template
     * @param warned - the user has been warned about possible duplicates
     * @return boolean that indicates that a duplicate was found
     * @throws MissingResourceException exception
     */
    boolean saveSearch(UserVo user, SearchTemplateVo search, boolean warned) throws MissingResourceException;

    /**
     * Removes the given search template
     * 
     * @param user - user attempting to remove search template
     * @param template - template to be removed
     * @throws MissingResourceException exception
     */
    void removeTemplate(UserVo user, SearchTemplateVo template) throws MissingResourceException;

}
