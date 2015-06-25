/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.test;


import gov.va.med.pharmacy.peps.common.utility.test.generator.SearchTermGenerator;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;

import junit.framework.TestCase;


/**
 * Tests for the search terms
 */
public class SearchTermVoTest extends TestCase {

    /**
     * Test the equals function
     * @throws Exception Exception
     */
    public void testEquals() throws Exception {
        SearchTermGenerator generator = new SearchTermGenerator();
        SearchTermVo term1 = generator.getFirst();
        SearchTermVo term2 = generator.getFirst();
        assertEquals("Term1 and Term2 are the same term. ", term1, term2);
        
        SearchTermVo term3 = generator.getList().get(1);
        assertFalse("Term1 and Term3 are not the same term.", term1.equals(term3));
        
        SearchTermVo term4 = (SearchTermVo) term2.clone();
        term4.setValue("New value");
        assertFalse("Term4 has a different value from Term1", term1.equals(term4));
    }

}
