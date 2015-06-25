/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.MissingResourceException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateMessageVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.SearchTemplateDomainCapability;
import gov.va.med.pharmacy.peps.service.common.capability.SearchTemplateCapability;


/**
 * perform search operations
 */
public class SearchTemplateCapabilityImpl implements SearchTemplateCapability {

    /**
     * The domain capability that manages search templates
     */
    private SearchTemplateDomainCapability searchTemplateDomainCapability;

    /**
     * Checks user permissions and then saves the search criteria as a search template
     * 
     * @param user - user that wishes to save the template
     * @param search - the search that should be saved as a template
     * @param warned - user has been warned about duplicates
     * @return boolean that indicates that a duplicate exists
     * 
     * @throws MissingResourceException 
     * @throws ValueObjectValidationException 
     */
    public boolean saveSearch(UserVo user, SearchTemplateVo search, boolean warned) throws MissingResourceException,
        ValueObjectValidationException {
        search.validate(user);

        return searchTemplateDomainCapability.saveSearch(user, search, warned);
    }

    /**
     * Saves the search that comes in the searchTemplateMessage
     * 
     * essentially saveSearch but does no validation (since this comes from national)
     * 
     * @param message The request message to save
     * @throws ValidationException if error validating search template
     */
    public void saveSearch(SearchTemplateMessageVo message) throws ValidationException {
        saveSearch(message.getUser(), message.getSearchTemplate(), message.isWarned());
    }

    /**
     * Retrieves the saved search templates
     * 
     * @param user - user retrieving templates
     * @return search templates for user
     * @throws MissingResourceException 
     * 
     */
    public List<SearchTemplateVo> retrieveSearchTemplates(UserVo user) throws MissingResourceException {
        return searchTemplateDomainCapability.retrieveSearchTemplates(user);
    }

    /**
     * retrieve
     * 
     * @param id - the search should be retrieved
     * @return search for user
     * @throws ItemNotFoundException 
     */
    public SearchTemplateVo retrieve(String id) throws ItemNotFoundException {
        long templateId = Long.parseLong(id.trim());

        return this.searchTemplateDomainCapability.retrieve(templateId);
    }

    /**
     * removeTemplate
     * 
     * @param user - user wishing to remove template
     * @param search - template to be removed
     * @throws ValueObjectValidationException
     * @throws MissingResourceException exception 
     * @throws ValueObjectValidationException exception 
     * 
     */
    public void removeTemplate(UserVo user, SearchTemplateVo search) throws MissingResourceException,
        ValueObjectValidationException {

        search.validate(user);

        this.searchTemplateDomainCapability.removeTemplate(user, search);
    }

    /**
     * getSearchTemplateDomainCapability
     * @return searchTemplateDomainCapability property
     */
    public SearchTemplateDomainCapability getSearchTemplateDomainCapability() {
        return searchTemplateDomainCapability;
    }

    /**
     * setSearchTemplateDomainCapability
     * @param searchTemplateDomainCapability property
     */
    public void setSearchTemplateDomainCapability(SearchTemplateDomainCapability searchTemplateDomainCapability) {
        this.searchTemplateDomainCapability = searchTemplateDomainCapability;
    }
}
