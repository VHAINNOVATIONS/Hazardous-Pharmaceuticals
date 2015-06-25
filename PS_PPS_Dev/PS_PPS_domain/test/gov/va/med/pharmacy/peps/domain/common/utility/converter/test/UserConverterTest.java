/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.SessionPreferenceType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplSessionPreferenceDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplUserDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.UserConverter;

import junit.framework.TestCase;


/**
 * Test {@link UserConverter}
 */
public class UserConverterTest extends TestCase {

    private EplUserDo dataDo;
    private UserVo objectVo;

    private UserConverter userConverter = new UserConverter();

    private UserVo user;
    private EplUserDo userDo;

    /**
     * test setup
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() {
        user = new UserGenerator().pseudoRandom();
        userDo = null;
    }

    /**
     * Test
     * 
     * Returning null will break other code
     */
    public void testShouldMapLocation() {
        String testLoc = "testloc";
        user.setLocation(testLoc);
        userDo = userConverter.convert(user);

        assertEquals("should have expected location value", testLoc, userDo.getLocation());
    }

    /**
     * Test
     */
    public void testTopLevelFieldsForToUser() {
        dataDo = new EplUserDo();

        dataDo.setLocation("local1");
        dataDo.setFirstName("Joe");
        dataDo.setLastName("Schmoe");
        dataDo.setUserName("jschmoe");
        dataDo.setId(PPSConstants.L45);

        objectVo = userConverter.convert(dataDo);

        assertEquals("id", dataDo.getId(), objectVo.getId());
        assertEquals("location", dataDo.getLocation(), objectVo.getLocation());
        assertEquals("first name", dataDo.getFirstName(), objectVo.getFirstName());
        assertEquals("last name", dataDo.getLastName(), objectVo.getLastName());
        assertEquals("user name", dataDo.getUserName(), objectVo.getUsername());
    }

    /**
     * Test
     * 
     * this doesn't really belong here, but we currently don't have DO-only tests.
     */
    public void testDefaultSessionPreferenceDoConstructor() {
        EplSessionPreferenceDo eplSessionPreferenceDo;
        dataDo = new EplUserDo();
        Long testID = PPSConstants.L45;

        dataDo.setId(testID);

        eplSessionPreferenceDo = new EplSessionPreferenceDo();
        assertNotNull("should never be null!", eplSessionPreferenceDo.getKey());
        assertNull("user FK should not be set on key", eplSessionPreferenceDo.getKey().getUserIdFk());

        eplSessionPreferenceDo.setEplUser(dataDo);
        eplSessionPreferenceDo.setPrefName(SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID.name());
        assertEquals("user FK should be set on key", testID, eplSessionPreferenceDo.getKey().getUserIdFk());
        assertEquals("pref val should be set on key", SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID.name(),
            eplSessionPreferenceDo.getKey().getPrefName());
    }

    /**
     * Test
     */
    public void testSessionPreferencesForToUser() {
        EplSessionPreferenceDo eplSessionPreferenceDo;
        dataDo = new EplUserDo();

        dataDo.setLocation("local2");
        dataDo.setFirstName("Joe2");
        dataDo.setLastName("Schmoe2");
        dataDo.setUserName("jschmoe2");
        dataDo.setId(PPSConstants.L45);

        assertNotNull("should never be null", dataDo.getEplSessionPreferences());
        String s101 = "101";
        eplSessionPreferenceDo = new EplSessionPreferenceDo();
        eplSessionPreferenceDo.setEplUser(dataDo);
        eplSessionPreferenceDo.setPrefName(SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID.name());
        eplSessionPreferenceDo.setPrefValue(s101);
        dataDo.getEplSessionPreferences().add(eplSessionPreferenceDo);

        objectVo = userConverter.convert(dataDo);

        assertEquals("should contain new preference", 1, objectVo.getSessionPreferences().size());
        assertTrue("should have pref name", objectVo.getSessionPreferences().keySet().contains(
            SessionPreferenceType.DEFAULT_SEARCH_TEMPLATE_ID));
        assertTrue("should have pref value", objectVo.getSessionPreferences().values().contains(s101));
    }

}
