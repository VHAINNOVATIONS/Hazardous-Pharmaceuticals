/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;


/**
 * Generate a list of search terms
 */
public class SearchTermGenerator extends VoGenerator<SearchTermVo> {
    private static final Map<FieldKey, String> TEST_SEARCH_TERM_DATA = new HashMap<FieldKey, String>();
    private static final String RISP = "Risp";

    /**
     * Create the test search term data
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#doInitialization()
     */
    @Override
    protected void doInitialization() {
        TEST_SEARCH_TERM_DATA.put(FieldKey.SEARCH_ALL_FIELDS, RISP);
        TEST_SEARCH_TERM_DATA.put(FieldKey.SEARCH_ALL_DESIGNATED_FIELDS, RISP);
        TEST_SEARCH_TERM_DATA.put(FieldKey.PRODUCT_STRENGTH, "5");
    };

    /**
     * Generate a list of search terms
     * 
     * @return List of search terms
     */
    protected List<SearchTermVo> generate() {
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();

        for (FieldKey key : TEST_SEARCH_TERM_DATA.keySet()) {
            searchTerms.add(new SearchTermVo(EntityType.PRODUCT, key, TEST_SEARCH_TERM_DATA.get(key)));
        }

        return searchTerms;
    }

}
