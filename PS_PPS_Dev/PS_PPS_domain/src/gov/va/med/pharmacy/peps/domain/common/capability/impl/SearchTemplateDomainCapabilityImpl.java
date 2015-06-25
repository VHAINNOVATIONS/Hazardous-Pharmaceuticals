/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.MissingResourceException;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.TemplateType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.PrintTemplateDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.SearchTemplateDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.SeqNumDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplPrintFieldDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplPrintTemplateDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplSearchCriteriaDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplSearchTemplateDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplPrintFieldDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSearchCriteriaDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSearchTemplateDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.SearchTemplateConverter;


/**
 * Perform CRUD operations on Search template
 */
public class SearchTemplateDomainCapabilityImpl implements SearchTemplateDomainCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(SearchTemplateDomainCapabilityImpl.class);

    private SeqNumDomainCapability seqNumDomainCapability;
    private EplSearchTemplateDao eplSearchTemplateDao;
    private EplSearchCriteriaDao eplSearchCriteriaDao;
    private PrintTemplateDomainCapability printTemplateDomainCapability;
    private EplPrintFieldDao eplPrintFieldDao;
    private EplPrintTemplateDao eplPrintTemplateDao;

    private SearchTemplateConverter searchTemplateConverter;

    /**
     * Saves the search criteria as a search template
     * 
     * @param user - user that wishes to save the template
     * @param search - the search that should be saved as a template
     * @param warned - the user has been warned about possible duplicates
     * @return boolean that indicates that a duplicate was found
     * @throws MissingResourceException 
     */
    public boolean saveSearch(UserVo user, SearchTemplateVo search, boolean warned) throws MissingResourceException {
        try {
            SearchTemplateVo existing = retrieveByTemplateName(search.getTemplateName());

            if (existing != null) {
                if (warned) {
                    search.setId(existing.getId());
                    search.getPrintTemplate().setId(existing.getPrintTemplate().getId());
                    update(user, search);

                    return true;
                } else {
                    return false;
                }
            }

            create(user, search);

            return true;
        } catch (Exception e) {
            LOG.error("Failed to save search template", e);
            throw new MissingResourceException(e, MissingResourceException.UNABLE_TO_SAVE_SEARCH_TEMPLATE, search
                .getTemplateName());
        }
    }

    /**
     * Retrieves the saved search templates
     * 
     * @param user - user for which templates should be returned
     * @return List of saved searches
     * @throws MissingResourceException 
     */
    public List<SearchTemplateVo> retrieveSearchTemplates(UserVo user) throws MissingResourceException {
        List<SearchTemplateVo> searchTemplates = null;

        try {
            searchTemplates = retrieveDomain(user);
        } catch (Exception e) {
            throw new MissingResourceException(e, MissingResourceException.USER_SEARCH_TEMPLATES_NOT_FOUND, user
                .getUsername());
        }

        return searchTemplates;
    }

    /**
     * Removes the given search template
     * 
     * @param user - user attempting to remove search template
     * @param template - template to be removed
     * @throws MissingResourceException 
     */
    public void removeTemplate(UserVo user, SearchTemplateVo template) throws MissingResourceException {
        Long eplId = Long.valueOf(template.getId());

        eplSearchCriteriaDao.delete(EplSearchCriteriaDo.EPL_SEARCH_TEMPLATE_ID, eplId);
        eplSearchTemplateDao.delete(EplSearchTemplateDo.EPL_ID, eplId);
    }

    /**
     * Create a Search template
     * 
     * @param templateOwner template owner
     * @param searchTemplate search criteria to be saved
     * @return SearchCriteriaVo
     * @throws DuplicateItemException 
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.SearchTemplateDomainCapability#
     *      create(gov.va.med.pharmacy.peps.common.vo.UserVo, gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo)
     */
    public SearchTemplateVo create(UserVo templateOwner, SearchTemplateVo searchTemplate) throws DuplicateItemException {
        LOG.debug("Creating a new SearchTemplate for owner: " + templateOwner);

        if (searchTemplate.getId() != null) {
            throw new DuplicateItemException(DuplicateItemException.DUPLICATE_ITEM, new Object[] { searchTemplate.getId()
                .toString() });
        }

        if (exists(searchTemplate.getTemplateName())) {
            throw new DuplicateItemException(DuplicateItemException.DUPLICATE_ITEM, new Object[] { searchTemplate
                .getTemplateName() });
        }

        // setId on the searchTemplateVo
        searchTemplate.setId(String.valueOf(seqNumDomainCapability.generateSearchTemplateId(templateOwner)));

        // set Id on the printTemplate
        searchTemplate.getPrintTemplate().setId(
            String.valueOf(seqNumDomainCapability.generatePrintTemplateId(templateOwner)));

        // convert vo to do
        searchTemplate.setUser(templateOwner);
        EplSearchTemplateDo searchTemplateDo = searchTemplateConverter.convert(searchTemplate);

        eplPrintTemplateDao.insert(searchTemplateDo.getEplPrintTemplate(), templateOwner);

        for (EplPrintFieldDo tempPrintField : searchTemplateDo.getEplPrintTemplate().getEplPrintFields()) {
            eplPrintFieldDao.insert(tempPrintField, templateOwner);
        }

        eplSearchTemplateDao.insert(searchTemplateDo, templateOwner);
        eplSearchCriteriaDao.insert(searchTemplateDo.getEplSearchCriterias(), templateOwner);

        return searchTemplate;
    }

    /**
     * Retrieve a Search template by id
     * 
     * @param eplId criteria ID
     * @return SearchCriteriaVo
     * @throws ItemNotFoundException 
     */
    public SearchTemplateVo retrieve(Long eplId) throws ItemNotFoundException {
        try {
            EplSearchTemplateDo searchTemplateDo = eplSearchTemplateDao.retrieve(eplId);

            return searchTemplateConverter.convert(searchTemplateDo);
        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(ItemNotFoundException.ITEM_NOT_FOUND, eplId);
        }
    }

    /**
     * Retrieve a search template by name
     * 
     * @param templateName template's name (should be unique)
     * @return SearchCriteriaVo
     * @throws ItemNotFoundException 
     */
    public SearchTemplateVo retrieveByTemplateName(String templateName) throws ItemNotFoundException {
        List<EplSearchTemplateDo> matchingSearchTemplates = null;

        try {
            matchingSearchTemplates = eplSearchTemplateDao.retrieve("templateName", templateName);

            if (matchingSearchTemplates != null && matchingSearchTemplates.size() > 0) {
                return searchTemplateConverter.convert(matchingSearchTemplates.get(0));
            } else {
                return null;
            }
        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(ItemNotFoundException.ITEM_NOT_FOUND, new Object[] { templateName });

        }
    }

    /**
     * Retrieves all search criteria for the specified template owner
     * 
     * @param templateOwner template owner
     * @return List<SearchCriteriaVo>
     */
    public List<SearchTemplateVo> retrieveDomain(UserVo templateOwner) {
        List<Criterion> criteria = new ArrayList<Criterion>();
        Criterion criterion;

        criterion = Restrictions.or(Restrictions.and(Restrictions.eq(EplSearchTemplateDo.TEMPLATE_TYPE,
            TemplateType.PERSONAL.name()), Restrictions.eq("eplUser.id", templateOwner.getId())), Restrictions.or(
                Restrictions.eq(EplSearchTemplateDo.TEMPLATE_TYPE, TemplateType.NATIONAL.name()), Restrictions.eq(
                    EplSearchTemplateDo.TEMPLATE_TYPE, TemplateType.LOCAL.name())));

        criteria.add(criterion);

        List<EplSearchTemplateDo> searchTemplates = eplSearchTemplateDao.retrieve(criteria);

        return searchTemplateConverter.convert(searchTemplates);
    }

    /**
     * Updates a template DO.
     * 
     * @param templateOwner owner of the template
     * @param searchTemplateVo SearchTemplateVo
     * @return EplSearchTemplateDo updated search template DO.
     */
    private EplSearchTemplateDo update(UserVo templateOwner, SearchTemplateVo searchTemplateVo) {

        if (searchTemplateVo.getPrintTemplate() != null) {
            printTemplateDomainCapability.update(templateOwner, searchTemplateVo.getPrintTemplate());
        }

        // convert vo to do
        searchTemplateVo.setUser(templateOwner);
        EplSearchTemplateDo searchTemplate = searchTemplateConverter.convert(searchTemplateVo);

        // delete and reinsert search Criteria
        eplSearchCriteriaDao.delete("eplSearchTemplate.eplId", searchTemplate.getEplId());
        eplSearchCriteriaDao.insert(searchTemplate.getEplSearchCriterias(), templateOwner);

        eplSearchTemplateDao.update(searchTemplate, templateOwner);

        return searchTemplate;
    }

    /**
     * Description
     * 
     * @param eplSearchCriteriaDao dao to inject
     */
    public void setEplSearchCriteriaDao(EplSearchCriteriaDao eplSearchCriteriaDao) {
        this.eplSearchCriteriaDao = eplSearchCriteriaDao;
    }

    /**
     * Description
     * 
     * @param eplSearchTemplateDao dao to inject
     */
    public void setEplSearchTemplateDao(EplSearchTemplateDao eplSearchTemplateDao) {
        this.eplSearchTemplateDao = eplSearchTemplateDao;
    }

    /**
     * Description
     * 
     * @param seqNumDomainCapability seqNumDomainCapability property
     */
    public void setSeqNumDomainCapability(SeqNumDomainCapability seqNumDomainCapability) {
        this.seqNumDomainCapability = seqNumDomainCapability;
    }

    /**
     * Description
     * 
     * @param templateName template name to check
     * @return true if template with the same name is already in the system, false otherwise
     */
    public boolean exists(String templateName) {
        Long count = getCount(String.format("where template.templateName='%s'", templateName));

        return count > 0;
    }

    /**
     * 
     * @param whereClause where clause to modify count query
     * @return Long
     */
    private Long getCount(String whereClause) {
        String query = String.format("select count(*) from EplSearchTemplateDo template %s", whereClause);

        return (Long) eplSearchTemplateDao.executeHqlQuery(query).iterator().next();
    }

    /**
     * Description
     * 
     * @param eplPrintFieldDao eplPrintFieldDao property
     */
    public void setEplPrintFieldDao(EplPrintFieldDao eplPrintFieldDao) {
        this.eplPrintFieldDao = eplPrintFieldDao;
    }

    /**
     * Description
     * 
     * @param printTemplateDomainCapability printTemplateDomainCapability property
     */
    public void setPrintTemplateDomainCapability(PrintTemplateDomainCapability printTemplateDomainCapability) {
        this.printTemplateDomainCapability = printTemplateDomainCapability;
    }

    /**
     * Description
     * 
     * @param eplPrintTemplateDao eplPrintTemplateDao property
     */
    public void setEplPrintTemplateDao(EplPrintTemplateDao eplPrintTemplateDao) {
        this.eplPrintTemplateDao = eplPrintTemplateDao;
    }

    /**
     * Description
     * 
     * @param searchTemplateConverter searchTemplateConverter property
     */
    public void setSearchTemplateConverter(SearchTemplateConverter searchTemplateConverter) {
        this.searchTemplateConverter = searchTemplateConverter;
    }
}
