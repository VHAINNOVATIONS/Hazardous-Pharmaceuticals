/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.test;


import gov.va.med.pharmacy.peps.common.vo.SessionPreferenceType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;

import junit.framework.TestCase;


/**
 * Tests for the user vo
 */
public class UserVoTest extends TestCase {

    /**
     * Test
     *
     * @throws Exception Exception
     */
    public void testAddSessionPreferences() throws Exception {
        UserVo user = new UserVo();
        assertNotNull("session preferences should never be null", user.getSessionPreferences());
        
        user.getSessionPreferences().put(SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID, "42");
        
        assertEquals("should now hold session preference", 1, user.getSessionPreferences().size());
    }
}
