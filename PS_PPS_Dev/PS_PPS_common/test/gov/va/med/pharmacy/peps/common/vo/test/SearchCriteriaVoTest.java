/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.test;


import gov.va.med.pharmacy.peps.common.utility.test.generator.SearchTermGenerator;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;

import junit.framework.TestCase;


/**
 * Test functionality of the SearchCriteriaVo
 */
public class SearchCriteriaVoTest extends TestCase {

    /**
     * Tests the default settings for simple search
     */
    public void testDefaultConstructor() {
        SearchCriteriaVo searchCriteriaVo = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);

        assertFalse("Search criteria are not advanced by default", searchCriteriaVo.isAdvanced());
    }

    /**
     * Tests the isEmpty function
     */
    public void testIsEmpty() {
        SearchTermGenerator termGenerator = new SearchTermGenerator();

        SearchCriteriaVo simpleSearch = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);
        simpleSearch.getSearchTerms().add(termGenerator.getFirst());
        assertFalse("Additional terms make the search not empty", simpleSearch.isEmpty());

        SearchCriteriaVo newSimpleSearch = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);
        newSimpleSearch.getSearchTerms().get(0).setValue("New value");
        assertFalse("Setting a value in the term should make the search not empty", newSimpleSearch.isEmpty());

        SearchCriteriaVo advancedSearch = new SearchCriteriaVo(SearchDomain.ADVANCED, Environment.LOCAL);
        advancedSearch.getSearchTerms().set(0, termGenerator.getFirst());
        assertFalse("Replacing the default term should make the search not empty", advancedSearch.isEmpty());

        SearchCriteriaVo domainSearch = new SearchCriteriaVo(SearchDomain.DOMAIN, Environment.LOCAL);
        domainSearch.setSearchTerms(termGenerator.getList());
        assertFalse("Replacing all the terms should make the search not empty", domainSearch.isEmpty());
    }

    /**
     * Tests the default settings for advanced search
     */
    public void testConstructorWithAdvancedIndicatorSetToTrue() {
        SearchCriteriaVo searchCriteriaVo = new SearchCriteriaVo(SearchDomain.ADVANCED, Environment.LOCAL);

        assertTrue("Should be advanced search", searchCriteriaVo.isAdvanced());
        assertFalse("Should not be a simple search", searchCriteriaVo.getSearchDomain().isSimpleSearch());
        assertTrue("New advanced searches should be empty", searchCriteriaVo.isEmpty());
    }

    /**
     * Tests the default settings for simple search
     */
    public void testConstructorWithAdvancedIndicatorSetToFalse() {
        SearchCriteriaVo searchCriteriaVo = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);

        assertFalse("Should not be advanced search", searchCriteriaVo.isAdvanced());
        assertTrue("Should be a simple search", searchCriteriaVo.getSearchDomain().isSimpleSearch());
        assertTrue("New simple searches should be empty", searchCriteriaVo.isEmpty());
    }

    /**
     * Tests constructor for domain searches
     */
    public void testConstructorWithDomainIndicator() {
        SearchCriteriaVo searchCriteriaVo = new SearchCriteriaVo(SearchDomain.DOMAIN, Environment.LOCAL);
        assertTrue("Should be a domain search", searchCriteriaVo.getSearchDomain().isDomainSearch());
        assertTrue("Should be initialized with domain search fields", searchCriteriaVo.getSearchFields().equals(
            searchCriteriaVo.getDomainSearchFields()));
        assertTrue("Should be defaulted to NONE search term", searchCriteriaVo.getSearchTerms().get(0).isEmpty()
            && searchCriteriaVo.getSearchTerms().size() == 1);
        assertTrue("New domain searches should be empty", searchCriteriaVo.isEmpty());

    }
}
