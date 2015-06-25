/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.test;


import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;

import junit.framework.TestCase;


/**
 * Tests for the search terms
 */
public class SearchFieldVoTest extends TestCase {

    /**
     * Test the constructor from key
     * @throws Exception Exception
     */
    public void testConstructFromKey() throws Exception {
        SearchFieldVo searchField = new SearchFieldVo("drug.unit.value");
        assertEquals("EntityType should be drug unit", searchField.getEntityType(), EntityType.DRUG_UNIT);
        assertEquals("FieldKey should be value", searchField.getFieldKey(), FieldKey.VALUE);
    }

}
