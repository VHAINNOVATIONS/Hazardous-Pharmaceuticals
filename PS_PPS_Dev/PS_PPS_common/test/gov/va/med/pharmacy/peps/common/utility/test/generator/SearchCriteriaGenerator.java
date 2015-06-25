/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;


/**
 * Generate a list of search criteria
 */
public class SearchCriteriaGenerator extends VoGenerator<SearchCriteriaVo> {

    private static final String DOSAGE_FORM = "PILL";
    private static final String STRENGTH = "200";



    /**
     * Generate a list of search criteria
     * 
     * @return List of search criteria
     */
    protected List<SearchCriteriaVo> generate() {
        SearchTermGenerator termGenerator = new SearchTermGenerator();
        List<SearchCriteriaVo> searches = new ArrayList<SearchCriteriaVo>();

        SearchCriteriaVo firstSearch = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);
        firstSearch.setSearchTerms(termGenerator.generate());
        firstSearch.setDosageForm(DOSAGE_FORM);
        firstSearch.setStrength(STRENGTH);

        SearchCriteriaVo secondSearch = new SearchCriteriaVo(SearchDomain.ADVANCED, Environment.LOCAL);
        secondSearch.setSearchTerms(termGenerator.generate());
        secondSearch.setDosageForm(DOSAGE_FORM);
        secondSearch.setStrength(STRENGTH);

        searches.add(firstSearch);
        searches.add(secondSearch);

        return searches;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
