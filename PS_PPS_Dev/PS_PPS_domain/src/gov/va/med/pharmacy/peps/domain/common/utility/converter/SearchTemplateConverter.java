/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.TemplateType;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplSearchCriteriaDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSearchCriteriaDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplSearchTemplateDo;


/**
 * Convert to/from {@link SearchTemplateVo} and {@link EplSearchTemplateDo}.
 */
public class SearchTemplateConverter extends Converter<SearchTemplateVo, EplSearchTemplateDo> {
    private PrintTemplateConverter printTemplateConverter;
    private UserConverter userConverter;

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     * gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplSearchTemplateDo toDataObject(SearchTemplateVo data) {
        SearchCriteriaVo searchCriteriaVo = data.getSearchCriteria();

        EplSearchTemplateDo searchTemplateDo = new EplSearchTemplateDo();

        searchTemplateDo.setEplId(Long.parseLong(data.getId()));

        searchTemplateDo.setTemplateName(data.getTemplateName());
        searchTemplateDo.setNotes(data.getNotes());

        searchTemplateDo.setEplUser(userConverter.convert(data.getUser()));

        searchTemplateDo.setAdvancedSearchYn(toYesOrNo(searchCriteriaVo.isAdvanced()));
        searchTemplateDo.setAndSearchYn(toYesOrNo(searchCriteriaVo.isAdvancedAndSearch()));
        searchTemplateDo.setDefaultYn(toYesOrNo(data.isDefault()));

        searchTemplateDo.setTemplateType(data.getTemplateType().toString());
        searchTemplateDo.setCreatedBy(data.getUser().getCreatedBy());

        EplSearchCriteriaDo tempCriteria;
        Long searchTemplateId = searchTemplateDo.getEplId();

        for (SearchTermVo searchTerm : searchCriteriaVo.getSearchTerms()) {
            tempCriteria = toSearchCriteriaDo(searchTerm, searchTemplateId);
            searchTemplateDo.getEplSearchCriterias().add(tempCriteria);
        }

        mapAdditionalFields(searchCriteriaVo, searchTemplateDo, searchTemplateId);
        data.getPrintTemplate().setTemplateName(data.getTemplateName());
        
        searchTemplateDo.setEplPrintTemplate(printTemplateConverter.convert(data.getPrintTemplate()));

        return searchTemplateDo;
    }

    /**
     * maps additional fields per specified data model
     * 
     * @param searchCriteriaVo from
     * @param searchTemplateDo to
     * @param searchTemplateId current template id
     */
    private void mapAdditionalFields(SearchCriteriaVo searchCriteriaVo, EplSearchTemplateDo searchTemplateDo,
        Long searchTemplateId) {

        EplSearchCriteriaDo tempCriteria;

        tempCriteria = toSearchCriteriaDo(FieldKey.LOCAL_USE.getKey(), searchCriteriaVo.getLocalUse().name(),
            searchTemplateId);
        searchTemplateDo.getEplSearchCriterias().add(tempCriteria);

        tempCriteria = toSearchCriteriaDo(FieldKey.ENTITY_TYPE.getKey(), searchCriteriaVo.getEntityType().toFieldKey(),
            searchTemplateId);
        searchTemplateDo.getEplSearchCriterias().add(tempCriteria);

        mapEnumCollection(FieldKey.REQUEST_ITEM_STATUS.getKey(), searchCriteriaVo.getRequestStatus(), searchTemplateDo,
            searchTemplateId);
        mapEnumCollection(FieldKey.ITEM_STATUS.getKey(), searchCriteriaVo.getItemStatus(), searchTemplateDo,
            searchTemplateId);

        if (searchCriteriaVo.getStrength() != null) {

//            tempCriteria =
//                toSearchCriteriaDo(FieldKey.DISPLAYABLE_INGREDIENT_STRENGTH.getKey(), searchCriteriaVo.getStrength(),
//                    searchTemplateId);
            tempCriteria =
                toSearchCriteriaDo(FieldKey.PRODUCT_STRENGTH.getKey(), searchCriteriaVo.getStrength(),
                    searchTemplateId);
            searchTemplateDo.getEplSearchCriterias().add(tempCriteria);
        }

        if (searchCriteriaVo.getDosageForm() != null) {
            tempCriteria = toSearchCriteriaDo(FieldKey.DOSAGE_FORM.getKey(), searchCriteriaVo.getDosageForm(),
                searchTemplateId);
            searchTemplateDo.getEplSearchCriterias().add(tempCriteria);
        }
    }

    /**
     * maps an enum collection to a set of new EplSearchCriteriaDo objects that are added to the specified
     * EplSearchTemplateDo's EplSearchCriterias collection
     * 
     * @param <T> enum type
     * @param fieldName field name for new criteria object
     * @param fromEnumList enum list to map from
     * @param searchTemplateDo search tempalte do to map to
     * @param searchTemplateId current search template id
     */
    private <T extends Enum<T>> void mapEnumCollection(String fieldName, List<T> fromEnumList,
                                                       EplSearchTemplateDo searchTemplateDo, Long searchTemplateId) {
        EplSearchCriteriaDo tempCriteria;

        for (T enumValue : fromEnumList) {
            tempCriteria = toSearchCriteriaDo(fieldName, enumValue.toString(), searchTemplateId);
            searchTemplateDo.getEplSearchCriterias().add(tempCriteria);
        }
    }

    /**
     * Convert SearchTermVo to EplSearchCriteriaDo
     * @param searchTerm search term object
     * @param searchTemplateId search template ID
     * @return EplSearchCriteriaDo
     */
    public EplSearchCriteriaDo toSearchCriteriaDo(SearchTermVo searchTerm, Long searchTemplateId) {
        return toSearchCriteriaDo(searchTerm.getSearchField().getKey(), searchTerm.getValue(), searchTemplateId);
    }

    /**
     * 
     * @param fieldName field name
     * @param fieldValue field value
     * @param searchTemplateId search template ID
     * 
     * @return EplSearchCriteriaDo
     */
    private EplSearchCriteriaDo toSearchCriteriaDo(String fieldName, String fieldValue, Long searchTemplateId) {
        EplSearchCriteriaDo searchCriteriaDo = new EplSearchCriteriaDo();
        EplSearchCriteriaDoKey searchCriteriaDoKey = new EplSearchCriteriaDoKey();

        searchCriteriaDoKey.setSearchFieldName(fieldName);
        searchCriteriaDoKey.setEplIdSearchTemplateFk(searchTemplateId);
        searchCriteriaDo.setKey(searchCriteriaDoKey);
        searchCriteriaDo.setSearchFieldValue(fieldValue);

        return searchCriteriaDo;
    }

    /**
     * Fully copies data from the given EplSearchTemplateDo into a {@link ValueObject}.
     * 
     * @param data EplSearchTemplateDo to convert
     * @return fully populated {@link ValueObject}
     * 
     */
    @Override
    protected SearchTemplateVo toValueObject(EplSearchTemplateDo data) {
        SearchTemplateVo searchTemplateVo = new SearchTemplateVo();

        searchTemplateVo.setId(String.valueOf(data.getEplId()));
        searchTemplateVo.setTemplateName(data.getTemplateName());
        searchTemplateVo.setNotes(data.getNotes());
        searchTemplateVo.setDefault(toBoolean(data.getDefaultYn()));

        searchTemplateVo.setUser(userConverter.convert(data.getEplUser()));

        if (data.getAdvancedSearchYn().equals("Y")) {
            searchTemplateVo.getSearchCriteria().setSearchDomain(SearchDomain.ADVANCED);
        } else {
            searchTemplateVo.getSearchCriteria().setSearchDomain(SearchDomain.SIMPLE);
        }

        searchTemplateVo.getSearchCriteria().setAdvancedAndSearch(data.getAndSearchYn().equals("Y"));
        
        if (data.getTemplateType() != null) {
            searchTemplateVo.setTemplateType(TemplateType.valueOf(data.getTemplateType()));
        }

        mapSearchCriteria(data, searchTemplateVo.getSearchCriteria());

        searchTemplateVo.setPrintTemplate(printTemplateConverter.convert(data.getEplPrintTemplate()));

        return searchTemplateVo;
    }

    /**
     * Maps the EplSearchTemplateDo search criteria to the search criteria Vo
     * 
     * @param searchTemplateDo from
     * @param searchCriteriaVo to
     */
    private void mapSearchCriteria(EplSearchTemplateDo searchTemplateDo, SearchCriteriaVo searchCriteriaVo) {
        if (searchTemplateDo.getEplSearchCriterias() != null && searchTemplateDo.getEplSearchCriterias().size() > 0) {
            searchCriteriaVo.getSearchTerms().clear();
        }

        searchCriteriaVo.setRequestStatus(new ArrayList<RequestItemStatus>());
        searchCriteriaVo.setItemStatus(new ArrayList<ItemStatus>());

        SearchTermVo tempSearchTerm;
        String searchFieldName;

        for (EplSearchCriteriaDo searchCriteriaDo : searchTemplateDo.getEplSearchCriterias()) {
            searchFieldName = searchCriteriaDo.getKey().getSearchFieldName();

            if (searchFieldName.equals(FieldKey.LOCAL_USE.getKey())) {
                searchCriteriaVo.setLocalUse(LocalUseSearchType.valueOf(searchCriteriaDo.getSearchFieldValue()));
            } else if (searchFieldName.equals(FieldKey.ENTITY_TYPE.getKey())) {
                searchCriteriaVo.setEntityType(EntityType.createFromString(searchCriteriaDo.getSearchFieldValue()));

                // Do this because the setter above set this property to true as a side effect, which causes problems on the 
                // presentation side.
                searchCriteriaVo.setEntityTypeChanged(false);
            } else if (searchFieldName.equals(FieldKey.REQUEST_ITEM_STATUS.getKey())) {
                RequestItemStatus requestItemStatus = RequestItemStatus.valueOf(searchCriteriaDo.getSearchFieldValue());
                searchCriteriaVo.getRequestStatus().add(requestItemStatus);
            } else if (searchFieldName.equals(FieldKey.ITEM_STATUS.getKey())) {
                ItemStatus itemStatus = ItemStatus.valueOf(searchCriteriaDo.getSearchFieldValue());
                searchCriteriaVo.getItemStatus().add(itemStatus);
            } else if (searchFieldName.equals(FieldKey.PRODUCT_STRENGTH.getKey())) {

                //DISPLAYABLE_INGREDIENT_STRENGTH
                searchCriteriaVo.setStrength(searchCriteriaDo.getSearchFieldValue());
            } else if (searchFieldName.equals(FieldKey.DOSAGE_FORM.getKey())) {
                searchCriteriaVo.setDosageForm(searchCriteriaDo.getSearchFieldValue());
            } else {
                tempSearchTerm = toSearchTermVo(searchCriteriaDo);
                searchCriteriaVo.getSearchTerms().add(tempSearchTerm);
            }
        }

        searchCriteriaVo.initSearchTerms();
    }

    /**
     * Convert EplSearchCriteriaDo to SearchTermVo
     * @param searchCriteriaDo data object to convert from
     * @return SearchTermVo
     */
    public SearchTermVo toSearchTermVo(EplSearchCriteriaDo searchCriteriaDo) {
        SearchTermVo searchTermVo = new SearchTermVo();
        SearchFieldVo searchField = new SearchFieldVo(searchCriteriaDo.getKey().getSearchFieldName());
        searchTermVo.setSearchField(searchField);
        searchTermVo.setValue(searchCriteriaDo.getSearchFieldValue());

        return searchTermVo;
    }

    /**
     * setPrintTemplateConverter
     * 
     * @param printTemplateConverter printTemplateConverter property
     */
    public void setPrintTemplateConverter(PrintTemplateConverter printTemplateConverter) {
        this.printTemplateConverter = printTemplateConverter;
    }

    /**
     * setUserConverter
     * 
     * @param userConverter userConverter property
     */
    public void setUserConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

}
