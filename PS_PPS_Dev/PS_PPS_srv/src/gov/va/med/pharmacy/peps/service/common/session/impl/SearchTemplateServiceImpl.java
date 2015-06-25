/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.MissingResourceException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.capability.SearchTemplateCapability;
import gov.va.med.pharmacy.peps.service.common.session.SearchTemplateService;


/**
 * search service for simple and advance search
 */
public class SearchTemplateServiceImpl implements SearchTemplateService {

    /**
     * Capability for searches
     */
    private SearchTemplateCapability searchTemplateCapability;

    /**
     * Saves the search criteria as a search template
     * 
     * @param user - user that wishes to save the template
     * @param search - the search that should be saved as a template
     * @param warned - the user has been warned of duplicates
     * @return boolean indicating duplicate exists
     * @throws MissingResourceException exception
     * @throws ValueObjectValidationException exception
     */
    public boolean create(UserVo user, SearchTemplateVo search, boolean warned) throws MissingResourceException,
        ValueObjectValidationException {
        return searchTemplateCapability.saveSearch(user, search, warned);
    }

    /**
     * setSearchTemplateCapability
     * @param searchTemplateCapability SearchTemplateCapability property
     */
    public void setSearchTemplateCapability(SearchTemplateCapability searchTemplateCapability) {
        this.searchTemplateCapability = searchTemplateCapability;
    }

    /**
     * Retrieves the saved search templates
     * 
     * @param user - user for which the templates should be retrieved
     * @return List of saved search templates
     * @throws MissingResourceException exception
     */
    public List<SearchTemplateVo> retrieve(UserVo user) throws MissingResourceException {
        return this.searchTemplateCapability.retrieveSearchTemplates(user);
    }

    /**
     * Retrieves search by Id
     * 
     * @param id - search
     * @return search template
     * @throws ItemNotFoundException exception
     * 
     */
    public SearchTemplateVo retrieve(String id) throws ItemNotFoundException {
        return this.searchTemplateCapability.retrieve(id);
    }

    /**
     * Removes the template
     * 
     * @param user - user wishing to remove template
     * @param template - search to be removed
     * @throws MissingResourceException exception
     * @throws ValueObjectValidationException exception
     */
    public void delete(UserVo user, SearchTemplateVo template) throws MissingResourceException,
        ValueObjectValidationException {
        this.searchTemplateCapability.removeTemplate(user, template);
    }

}
