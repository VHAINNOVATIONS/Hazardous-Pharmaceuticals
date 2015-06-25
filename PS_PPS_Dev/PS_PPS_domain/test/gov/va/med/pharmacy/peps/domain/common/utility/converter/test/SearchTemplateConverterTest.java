/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.TemplateType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSearchCriteriaDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSearchTemplateDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.PrintTemplateConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.SearchTemplateConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.UserConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.test.DomainTestHelper;

import junit.framework.TestCase;


/**
 * Test {@link SearchTemplateConverter}
 */
public class SearchTemplateConverterTest extends TestCase {

    private static final String SEARCH_FIELD_VALUE = "Field value here";
    private static final String DOSAGE_FORM = "dosage.form";
    private static final String ENTITY_TYPE = "entity.type";
    private static final String DOSAGE_FORM_NAME = "dosage.form.name";
    private static final String REQUEST_ITEM_STATUS = "request.item.status";
    private static final String ITEM_STATUS = "item.status";
    private static final String PRODUCT_STRENGTH = "product.strength";
    private static final String LOCAL_USE = "local.use";
    private static final String S200 = "200";
    private static final String PRODUCT_GEN_NAME = "product.generic.name";
    private static final String ACETAMINOPHEN = "acetaminophen";
    private static final String PILL = "pill";
    

    private EplSearchCriteriaDo searchCriteriaDo;
    private SearchTermVo searchTermVo;
    private Long testSearchTemplateId = PPSConstants.L45;

    private EplSearchTemplateDo searchTemplateDo;
    private SearchTemplateVo searchTemplateVo;
    private UserVo currentUser;

    private SearchTemplateConverter searchTemplateConverter;

    /**
     * test setup
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() {
        this.searchTemplateConverter = new SearchTemplateConverter();
        searchTemplateConverter.setPrintTemplateConverter(new PrintTemplateConverter());
        searchTemplateConverter.setUserConverter(new UserConverter());

        this.searchTemplateDo = DomainTestHelper.SearchTemplates.createEplSearchTemplateDo();
        this.searchTemplateVo = DomainTestHelper.SearchTemplates.createSearchTemplateVo();
        this.currentUser = DomainTestHelper.SearchTemplates.createTemplateUserVo();
        searchTemplateVo.setUser(currentUser);

        this.searchCriteriaDo = DomainTestHelper.SearchTemplates
            .createEplSearchCriteriaDo(DOSAGE_FORM, SEARCH_FIELD_VALUE);
        this.searchTermVo = DomainTestHelper.SearchTemplates.createSearchTermVo(DOSAGE_FORM_NAME, SEARCH_FIELD_VALUE);
    }

    /**
     * The additional fields are the fields that are stored as a collection in the database but map to fields on the
     * SearchCriteriaVo and should not be included in the SearchTermsVo collection.
     * 
     * @param searchCriteriaSet searchCriteriaSet that needs additional fields
     */
    private void addAdditionalFields(Set<EplSearchCriteriaDo> searchCriteriaSet) {
        EplSearchCriteriaDo searchCriteria;


        searchCriteria = DomainTestHelper.SearchTemplates.createEplSearchCriteriaDo(ENTITY_TYPE, EntityType
            .toFieldKey(EntityType.PRODUCT));
        searchCriteriaSet.add(searchCriteria);

        searchCriteria = DomainTestHelper.SearchTemplates.createEplSearchCriteriaDo(REQUEST_ITEM_STATUS,
            RequestItemStatus.APPROVED.toString());
        searchCriteriaSet.add(searchCriteria);

        searchCriteria = DomainTestHelper.SearchTemplates.createEplSearchCriteriaDo(ITEM_STATUS, ItemStatus.ACTIVE
            .toString());
        searchCriteriaSet.add(searchCriteria);

        searchCriteria = DomainTestHelper.SearchTemplates
            .createEplSearchCriteriaDo(PRODUCT_STRENGTH, "100");
        searchCriteriaSet.add(searchCriteria);

    }

    /**
     * testToSearchTemplateVosShouldReturnExpectedSizedCollection
     */
    public void testToSearchTemplateVosShouldReturnExpectedSizedCollection() {
        List<EplSearchTemplateDo> searchTemplateDos = new ArrayList<EplSearchTemplateDo>();
        List<SearchTemplateVo> searchCriteriaVos = null;

        searchTemplateDo = DomainTestHelper.SearchTemplates.createEplSearchTemplateDo();
        searchTemplateDo.setEplId(1L);
        searchTemplateDos.add(searchTemplateDo);
        searchTemplateDo = DomainTestHelper.SearchTemplates.createEplSearchTemplateDo();
        searchTemplateDo.setEplId(2L);
        searchTemplateDos.add(searchTemplateDo);

        searchCriteriaVos = searchTemplateConverter.convert(searchTemplateDos);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, 2, searchCriteriaVos.size());
    }

    /**
     * Test
     */
    public void testToSearchTemplateVoShouldNeverReturnNull() {
        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertNotNull("should never return null.", searchTemplateVo);
    }

    /**
     * Test
     */
    public void testToSearchTemplateVoAddDefaultSearchFieldWhenThereAreNoSearchCriteriaAndAdditionFieldsArePresent() {
        searchTemplateDo.getEplSearchCriterias().clear();
        addAdditionalFields(searchTemplateDo.getEplSearchCriterias());
        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertEquals("should have one field in collection", 1, searchTemplateVo.getSearchCriteria().getSearchTerms().size());
        assertEquals("should be search with no fields", "search.no.fields", searchTemplateVo.getSearchCriteria()
            .getSearchTerms().get(0).getFieldKey().getKey());
    }

    /**
     * Test
     */
    public void testToSearchTemplateVoReturnValueShouldContainTemplateEplId() {
        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        Long eplId = Long.parseLong(searchTemplateVo.getId());

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, DomainTestHelper.SearchTemplates.SEARCH_TEMPLATE_EPL_ID, eplId);
    }

    /**
     * Test
     */
    public void testToSearchTemplateVoShouldMapAssociatedPrintTemplate() {
        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, DomainTestHelper.SearchTemplates.TEMPLATE_NAME, 
            searchTemplateVo.getPrintTemplate().getTemplateName());
    }

    /**
     * Test
     */
    public void testToSearchTemplateVoReturnValueShouldContainTemplateName() {
        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, DomainTestHelper.SearchTemplates.TEMPLATE_NAME, 
            searchTemplateVo.getTemplateName());
    }

    /**
     * Test
     */
    public void testToSearchTemplateVoReturnValueShouldContainAdvancedSearchIndicatorWhileTrue() {
        searchTemplateDo.setAdvancedSearchYn("Y");
        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertTrue("should be true.", searchTemplateVo.getSearchCriteria().isAdvanced());
    }

    /**
     * Test
     */
    public void testToSearchTemplateVoReturnValueShouldContainAdvancedSearchAndIndicator() {
        searchTemplateDo.setAndSearchYn("Y");
        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertTrue("should be true ", searchTemplateVo.getSearchCriteria().isAdvancedAndSearch());
        
        searchTemplateDo.setAndSearchYn("N");
        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertFalse("should be true!  ", searchTemplateVo.getSearchCriteria().isAdvancedAndSearch());
    }

    /**
     * Test
     */
    public void testToSearchTemplateVoReturnValueShouldContainDefaultIndicatorWhileTrue() {
        searchTemplateDo.setDefaultYn("Y");
        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertTrue("Should be true", searchTemplateVo.isDefault());
    }

    /**
     * Test
     */
    public void testToSearchTemplateVoReturnValueShouldContainTemplateType() {
        TemplateType templateType = TemplateType.LOCAL;
        searchTemplateDo.setTemplateType(templateType.toString());

        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, TemplateType.LOCAL, searchTemplateVo.getTemplateType());
    }

    /**
     * Test
     */
    public void testToSearchTemplateVoReturnValueShouldContainNotes() {
        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, DomainTestHelper.SearchTemplates.NOTES, searchTemplateVo.getNotes());
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateVoReturnValueShouldContainLocalUseIndicator() {
        EplSearchCriteriaDo searchCriteria = DomainTestHelper.SearchTemplates.createEplSearchCriteriaDo(LOCAL_USE,
            "LOCAL_USE");
        searchTemplateDo.getEplSearchCriterias().add(searchCriteria);

        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertTrue("Should be true when criteria collection has indicator.", searchTemplateVo.getSearchCriteria()
            .getLocalUse().isLocalUse());
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateVoReturnValueShouldContainRequestItemStatus() {
        EplSearchCriteriaDo searchCriteria = DomainTestHelper.SearchTemplates.createEplSearchCriteriaDo(
            REQUEST_ITEM_STATUS, RequestItemStatus.REJECTED.toString());
        searchTemplateDo.getEplSearchCriterias().add(searchCriteria);

        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertEquals("should be expected enum value ", RequestItemStatus.REJECTED, searchTemplateVo.getSearchCriteria()
            .getRequestStatus().get(0));
        assertEquals(" should have expected size", 1, searchTemplateVo.getSearchCriteria().getRequestStatus().size());
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateVoReturnValueShouldContainItemStatus() {
        EplSearchCriteriaDo searchCriteria = DomainTestHelper.SearchTemplates.createEplSearchCriteriaDo(ITEM_STATUS,
            ItemStatus.INACTIVE.toString());
        searchTemplateDo.getEplSearchCriterias().add(searchCriteria);

        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertEquals("should be expected enum value", ItemStatus.INACTIVE, searchTemplateVo.getSearchCriteria()
            .getItemStatus().get(0));
        assertEquals("should have expected size", 1, searchTemplateVo.getSearchCriteria().getItemStatus().size());
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateVoReturnValueShouldContainStrength() {
        EplSearchCriteriaDo searchCriteria = DomainTestHelper.SearchTemplates.createEplSearchCriteriaDo(
            PRODUCT_STRENGTH, S200);
        searchTemplateDo.getEplSearchCriterias().add(searchCriteria);

        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, S200, searchTemplateVo.getSearchCriteria().getStrength());
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateVoReturnValueShouldContainDosageForm() {
        String tab = "TAB";
        EplSearchCriteriaDo searchCriteria = DomainTestHelper.SearchTemplates.createEplSearchCriteriaDo(DOSAGE_FORM,
            tab);
        searchTemplateDo.getEplSearchCriterias().add(searchCriteria);

        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, tab, searchTemplateVo.getSearchCriteria().getDosageForm());
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateVoReturnValueShouldContainFieldKeys() {
        EplSearchCriteriaDo searchCriteria;

        searchCriteria = DomainTestHelper.SearchTemplates.createEplSearchCriteriaDo(PRODUCT_GEN_NAME, ACETAMINOPHEN);
        searchTemplateDo.getEplSearchCriterias().add(searchCriteria);
        searchCriteria = DomainTestHelper.SearchTemplates.createEplSearchCriteriaDo("product.dose.unit", PILL);
        searchTemplateDo.getEplSearchCriterias().add(searchCriteria);
        addAdditionalFields(searchTemplateDo.getEplSearchCriterias());
        assertEquals("criteria set should have expected size", PPSConstants.I6, 
            searchTemplateDo.getEplSearchCriterias().size());

        searchTemplateVo = searchTemplateConverter.convert(searchTemplateDo);

        SearchTermVo term = new SearchTermVo(new SearchFieldVo(FieldKey.GENERIC_NAME, EntityType.PRODUCT), ACETAMINOPHEN,
            SearchType.CONTAINS);

        assertEquals("should have expected size (and not include additional fields", 2, searchTemplateVo.getSearchCriteria()
            .getSearchTerms().size());
        assertTrue("1.Should be true when criteria collection has indicator", searchTemplateVo.getSearchCriteria()
            .getSearchTerms().contains(term));
    }

    /**
     * Test
     */
    public void testToSearchCriteriaVoReturnValueShouldContainFieldKey() {
        String fieldKeyName = "generic.name";
        String fieldValue = ACETAMINOPHEN;
        FieldKey expected = FieldKey.GENERIC_NAME;
        SearchTermVo searchTerm = null;

        searchCriteriaDo = DomainTestHelper.SearchTemplates.createEplSearchCriteriaDo(fieldKeyName,
            fieldValue);

        searchTerm = searchTemplateConverter.toSearchTermVo(searchCriteriaDo);

        assertEquals("1.check key name", expected.getKey(), searchTerm.getFieldKey().getKey());
        assertEquals("1.check field value", fieldValue, searchTerm.getValue());
    }

    /**
     * Test
     */
    public void testToSearchTemplateDoShouldNeverReturnNull() {
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        assertNotNull("1.should never return null", searchTemplateDo);
    }

    /**
     * Test
     */
    public void testToSearchTemplateDoShouldMapAssociatedPrintTemplate() {
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, DomainTestHelper.SearchTemplates.TEMPLATE_NAME, searchTemplateDo
            .getEplPrintTemplate().getTemplateName());
    }

    /**
     * Test
     */
    public void testToSearchTemplateDoReturnValueShouldContainTemplateEplId() {
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, 
            DomainTestHelper.SearchTemplates.SEARCH_TEMPLATE_EPL_ID, searchTemplateDo.getEplId());
    }

    /**
     * Test
     */
    public void testToSearchTemplateDoReturnValueShouldContainTemplateName() {
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, 
            DomainTestHelper.SearchTemplates.TEMPLATE_NAME, searchTemplateDo.getTemplateName());
    }

    /**
     * Test
     */
    public void testToSearchTemplateDoReturnValueShouldContainNotes() {
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, DomainTestHelper.SearchTemplates.NOTES, searchTemplateDo.getNotes());
    }

    /**
     * Test
     */
    public void testToSearchTemplateDoReturnValueShouldContainDefaultIndicatorWhileTrue() {
        searchTemplateVo.setDefault(true);
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        assertTrue("should be true", searchTemplateDo.getDefaultYn().equals("Y"));
    }

    /**
     * Test
     */
    public void testToSearchTemplateDoReturnValueShouldContainTemplateType() {
        searchTemplateVo.setTemplateType(TemplateType.LOCAL);
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        assertEquals("should be false", "LOCAL", searchTemplateDo.getTemplateType());
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateDoReturnValueShouldContainLocalUseIndicator() {
        boolean found = false;
        LocalUseSearchType actualValue = LocalUseSearchType.ALL_ITEMS;

        searchTemplateVo.getSearchCriteria().setLocalUse(LocalUseSearchType.LOCAL_USE);
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        for (EplSearchCriteriaDo search : searchTemplateDo.getEplSearchCriterias()) {
            if (search.getKey().getSearchFieldName() == LOCAL_USE) {
                found = true;
                actualValue = LocalUseSearchType.valueOf(search.getSearchFieldValue());
            }
        }

        assertTrue("2.Should be in collection", found);
        assertEquals("Should have expected value", LocalUseSearchType.LOCAL_USE, actualValue);
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateVoReturnValueShouldContainEntityType() {
        boolean found = false;
        EntityType actualValue = null;

        searchTemplateVo.getSearchCriteria().setEntityType(EntityType.ORDERABLE_ITEM);
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        for (EplSearchCriteriaDo search : searchTemplateDo.getEplSearchCriterias()) {
            if (search.getKey().getSearchFieldName() == ENTITY_TYPE) {
                found = true;
                actualValue = EntityType.createFromString(search.getSearchFieldValue());
            }
        }

        assertTrue("3.Should be in collection", found);
        assertEquals("3.Should be true when criteria collection has indicator", EntityType.ORDERABLE_ITEM, actualValue);
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateDoReturnValueShouldContainRequestItemStatus() {
        boolean found = false;
        RequestItemStatus actualValue = null;
        List<RequestItemStatus> requestItemStatus = new ArrayList<RequestItemStatus>();
        requestItemStatus.add(RequestItemStatus.REJECTED);

        searchTemplateVo.getSearchCriteria().setRequestStatus(requestItemStatus);
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        for (EplSearchCriteriaDo search : searchTemplateDo.getEplSearchCriterias()) {
            if (search.getKey().getSearchFieldName() == REQUEST_ITEM_STATUS) {
                found = true;
                actualValue = RequestItemStatus.valueOf(search.getSearchFieldValue());
            }
        }

        assertTrue("4.Should be in collection", found);
        assertEquals("4.Should be true when criteria collection has indicator", RequestItemStatus.REJECTED, actualValue);
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateVoReturnValueShouldContainMultipleRequestItemStatus() {
        int numberFound = 0;
        List<RequestItemStatus> actualValues = new ArrayList<RequestItemStatus>();
        List<RequestItemStatus> requestItemStatus = new ArrayList<RequestItemStatus>();
        requestItemStatus.add(RequestItemStatus.REJECTED);
        requestItemStatus.add(RequestItemStatus.PENDING);

        searchTemplateVo.getSearchCriteria().setRequestStatus(requestItemStatus);
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        for (EplSearchCriteriaDo search : searchTemplateDo.getEplSearchCriterias()) {
            if (search.getKey().getSearchFieldName() == REQUEST_ITEM_STATUS) {
                numberFound++;
                actualValues.add(RequestItemStatus.valueOf(search.getSearchFieldValue()));
            }
        }

        // Note: is returned in reverse order
        assertEquals("Should have found proper number", 2, numberFound);
        assertTrue("should contain REJECTED", actualValues.contains(RequestItemStatus.REJECTED));
        assertTrue("should contain PENDING", actualValues.contains(RequestItemStatus.PENDING));
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateDoReturnValueShouldContainItemStatus() {
        boolean found = false;
        ItemStatus actualValue = null;
        List<ItemStatus> requestItemStatus = new ArrayList<ItemStatus>();
        requestItemStatus.add(ItemStatus.INACTIVE);

        searchTemplateVo.getSearchCriteria().setItemStatus(requestItemStatus);
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        for (EplSearchCriteriaDo search : searchTemplateDo.getEplSearchCriterias()) {
            if (search.getKey().getSearchFieldName() == ITEM_STATUS) {
                found = true;
                actualValue = ItemStatus.valueOf(search.getSearchFieldValue());
            }
        }

        assertTrue("5.Should be in collection", found);
        assertEquals("Should be true when criteria collection has indicator", ItemStatus.INACTIVE, actualValue);
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateDoReturnValueShouldContainStrength() {
        boolean found = false;
        String actualValue = null;

        searchTemplateVo.getSearchCriteria().setStrength(S200);
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        for (EplSearchCriteriaDo search : searchTemplateDo.getEplSearchCriterias()) {
            if (search.getKey().getSearchFieldName() == PRODUCT_STRENGTH) {
                found = true;
                actualValue = search.getSearchFieldValue();
            }
        }

        assertTrue("6.Should be in collection", found);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, S200, actualValue);
    }

    /**
     * Test
     * 
     */
    public void testToSearchTemplateDoReturnValueShouldContainDosageForm() {
        boolean found = false;
        String actualValue = null;

        searchTemplateVo.getSearchCriteria().setDosageForm(PILL);
        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        for (EplSearchCriteriaDo search : searchTemplateDo.getEplSearchCriterias()) {
            if (search.getKey().getSearchFieldName() == DOSAGE_FORM) {
                found = true;
                actualValue = search.getSearchFieldValue();
            }
        }

        assertTrue("Should be in collection", found);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, PILL, actualValue);
    }

    /**
     * Test
     */
    public void testToSearchTemplateDoReturnValueShouldContainSearchCriteria() {
       
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();

        searchTerms.add(DomainTestHelper.SearchTemplates.createSearchTermVo(PRODUCT_GEN_NAME, ACETAMINOPHEN));
        searchTerms.add(DomainTestHelper.SearchTemplates.createSearchTermVo("product.dosage.form", PILL));

        searchTemplateVo.getSearchCriteria().setSearchTerms(searchTerms);

        searchTemplateDo = searchTemplateConverter.convert(searchTemplateVo);

        Set<EplSearchCriteriaDo> searchCriteriaSet = searchTemplateDo.getEplSearchCriterias();
        assertEquals("check criteria list size (should include additional fields)", 
            PPSConstants.I6, searchCriteriaSet.size());
    }

    /**
     * Test
     */
    public void testToSearchCriteriaDoReturnValueShouldContainFieldKeyInfo() {
        SearchTermVo searchVo = new SearchTermVo();
        EplSearchCriteriaDo searchDo = null;

        String searchField = PRODUCT_GEN_NAME;
        String fieldValue = ACETAMINOPHEN;
        searchVo.setSearchField(new SearchFieldVo(searchField));
        searchVo.setValue(fieldValue);

        searchDo = searchTemplateConverter.toSearchCriteriaDo(searchVo, null);

        assertEquals("check key name", searchField, searchDo.getKey().getSearchFieldName());
        assertEquals("check field value", fieldValue, searchDo.getSearchFieldValue());
    }

    /**
     * Test
     */
    public void testToSearchTermVoShouldNeverReturnNull() {
        searchTermVo = searchTemplateConverter.toSearchTermVo(searchCriteriaDo);

        assertNotNull("7.should never return null", searchTermVo);
    }

    /**
     * Test
     */
    public void testToSearchTermVoShouldContainFieldKey() {
        searchTermVo = searchTemplateConverter.toSearchTermVo(searchCriteriaDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, DOSAGE_FORM, searchTermVo.getFieldKey().getKey());
    }

    /**
     * Test
     */
    public void testToSearchTermVoShouldContainSearchTermValue() {
        searchTermVo = searchTemplateConverter.toSearchTermVo(searchCriteriaDo);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, SEARCH_FIELD_VALUE, searchTermVo.getValue());
    }

    /**
     * Test
     */
    public void testToSearchTermDoShouldNeverReturnNull() {
        searchCriteriaDo = searchTemplateConverter.toSearchCriteriaDo(searchTermVo, testSearchTemplateId);

        assertNotNull("should never return null", searchCriteriaDo);
    }

    /**
     * Test
     */
    public void testToSearchTermDoShouldContainFieldKey() {
        searchCriteriaDo = searchTemplateConverter.toSearchCriteriaDo(searchTermVo, testSearchTemplateId);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, DOSAGE_FORM_NAME, searchCriteriaDo.getKey().getSearchFieldName());
    }

    /**
     * Test
     */
    public void testToSearchTermDoShouldContainSearchTermValue() {
        searchCriteriaDo = searchTemplateConverter.toSearchCriteriaDo(searchTermVo, testSearchTemplateId);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, SEARCH_FIELD_VALUE, searchCriteriaDo.getSearchFieldValue());
    }

    /**
     * Test
     */
    public void testToSearchTermDoShouldContainSearchTemplateId() {
        searchCriteriaDo = searchTemplateConverter.toSearchCriteriaDo(searchTermVo, testSearchTemplateId);

        assertEquals(PPSConstants.SHOULD_BE_EQUAL, testSearchTemplateId, searchCriteriaDo.getKey().getEplIdSearchTemplateFk());
    }
}
