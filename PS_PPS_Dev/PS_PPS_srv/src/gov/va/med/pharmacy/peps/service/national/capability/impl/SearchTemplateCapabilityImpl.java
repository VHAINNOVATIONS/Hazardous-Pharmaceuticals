/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.capability.impl;


import gov.va.med.pharmacy.peps.common.exception.MissingResourceException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.national.messagingservice.outbound.capability.SendToLocalCapability;


/**
 * SearchTemplateCapabilityImpl
 */
public class SearchTemplateCapabilityImpl extends
    gov.va.med.pharmacy.peps.service.common.capability.impl.SearchTemplateCapabilityImpl {

    private SendToLocalCapability sendToLocalCapability;

    /**
     * Checks user permissions and then saves the search criteria as a search template
     * 
     * @param user - user that wishes to save the template
     * @param search - the search that should be saved as a template
     * @param warned - user has been warned about duplicates
     * @return boolean that indicates that a duplicate exists
     * 
     * @throws MissingResourceException exception
     * @throws ValueObjectValidationException exception
     */
    @Override
    public boolean saveSearch(UserVo user, SearchTemplateVo search, boolean warned) throws MissingResourceException,
        ValueObjectValidationException {
        search.validate(user);
        
        boolean ret = getSearchTemplateDomainCapability().saveSearch(user, search, warned);
        
//        if (ret && search.getTemplateType().equals(TemplateType.NATIONAL)) {
//            //search.setId(null);
//            SearchTemplateMessageVo message = new SearchTemplateMessageVo(user, search, warned);
//            sendToLocalCapability.send(message);
//            
//        }
        
        return ret;
    }

    /**
     * get the sendToLocalCapability
     * 
     * @return the sendToLocalCapability
     */
    public SendToLocalCapability getSendToLocalCapability() {
        return sendToLocalCapability;
    }

    /**
     * setSendToLocalCapability
     * @param sendToLocalCapability sendToLocalCapability property
     */
    public void setSendToLocalCapability(SendToLocalCapability sendToLocalCapability) {
        this.sendToLocalCapability = sendToLocalCapability;
    }

}
