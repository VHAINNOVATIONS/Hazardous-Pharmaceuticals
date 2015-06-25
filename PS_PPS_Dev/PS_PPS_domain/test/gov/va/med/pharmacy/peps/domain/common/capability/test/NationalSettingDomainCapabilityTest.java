/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;






import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;



import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.domain.common.capability.NationalSettingDomainCapability;


/**
 * NationalSettingDomainCapabilityTest
 * 
 * Test the capability of the NationalSettings
 */
public class NationalSettingDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(NationalSettingDomainCapabilityTest.class);
    private static final String STOPPED = "STOPPED";
    private NationalSettingDomainCapability nationalSettingDomainCapability;

    @Override
    @Before
    public void setUp() throws Exception {
        nationalSettingDomainCapability = getNationalCapability(NationalSettingDomainCapability.class);
    }

  
    /**
     * testUpdateFdbLastRun
     */
    public void testUpdateAll() {
        
        NationalSettingVo vo1 = new NationalSettingVo();
        vo1.setKeyName(NationalSetting.FDB_ADD_RUN_STATE.toString());
        vo1.setStringValue(STOPPED);
        assertTrue("update FDB_ADD_RUN_STATE failed!", updateNationalSettingsVo(vo1));
        
        NationalSettingVo vo2 = new NationalSettingVo();
        vo2.setKeyName(NationalSetting.FDB_UPDATE_RUN_STATE.toString());
        vo2.setStringValue(STOPPED);
        assertTrue("update FDB_UPDATE_RUN_STATE failed!", updateNationalSettingsVo(vo2));
        
        NationalSettingVo vo3 = new NationalSettingVo();
        vo3.setKeyName(NationalSetting.STS_RUN_STATE.toString());
        vo3.setStringValue(STOPPED);
        assertTrue("update STS_RUN_STATE failed!", updateNationalSettingsVo(vo3));

        NationalSettingVo vo4 = new NationalSettingVo();
        vo4.setKeyName(NationalSetting.FSS_RUN_STATE.toString());
        vo4.setStringValue(STOPPED);
        assertTrue("update FSS_RUN_STATE failed!", updateNationalSettingsVo(vo4));

        NationalSettingVo vo5 = new NationalSettingVo();
        vo5.setKeyName(NationalSetting.FDB_ADD_LAST_RUN.toString());
        vo5.setDateValue(new Date());
        assertTrue("update FDB_ADD_LAST_RUN failed!", updateNationalSettingsVo(vo5));

        NationalSettingVo vo6 = new NationalSettingVo();
        vo6.setKeyName(NationalSetting.FSS_UPDATE_LAST_RUN.toString());
        vo6.setDateValue(new Date());
        assertTrue("update FSS_UPDATE_LAST_RUN failed! ", updateNationalSettingsVo(vo6));

        NationalSettingVo vo7 = new NationalSettingVo();
        vo7.setKeyName(NationalSetting.FSS_UPDATE_LAST_RUN.toString());
        vo7.setDateValue(new Date());
        assertTrue("update FSS_UPDATE_LAST_RUN failed!  ", updateNationalSettingsVo(vo7));

        NationalSettingVo vo8 = new NationalSettingVo();
        vo8.setKeyName(NationalSetting.FSS_UPDATE_LAST_RUN.toString());
        vo8.setDateValue(new Date());
        assertTrue("update FSS_UPDATE_LAST_RUN failed!", updateNationalSettingsVo(vo8));

        NationalSettingVo vo9 = new NationalSettingVo();
        vo9.setKeyName(NationalSetting.HOST_NAME.toString());
        vo9.setStringValue("www.national.cmop.va.gov/FDAMedGuides2");   
        assertTrue("update HOST_NAME failed!", updateNationalSettingsVo(vo9));

        NationalSettingVo vo10 = new NationalSettingVo();
        vo10.setKeyName(NationalSetting.MESSAGE_STATUS.toString());
        vo10.setBooleanValue(false);
        assertTrue("update MESSAGE_STATUS failed!", updateNationalSettingsVo(vo10));
        
        NationalSettingVo vo11 = new NationalSettingVo();
        vo11.setKeyName(NationalSetting.MESSAGE_ERROR.toString());
        vo11.setStringValue("No errors 2");   
        assertTrue("update MESSAGE_ERROR failed! ", updateNationalSettingsVo(vo11));

        NationalSettingVo vo12 = new NationalSettingVo();
        vo12.setKeyName(NationalSetting.NUM_MSG_QUEUE.toString());
        vo12.setIntegerValue(new Long("20"));
        assertTrue("update MESSAGE_ERROR failed!", updateNationalSettingsVo(vo12));
        
        
        
    }
    
    
    /**
     * testGetNationalSettingsByKey
     */
    public void testGetNationalSettingsByKey() {

        NationalSettingVo vo = (NationalSettingVo) getNationalSettingFromMap(NationalSetting.HOST_NAME);
        LOG.debug("keyS: " + vo.getKeyName());
        
        assertTrue("key did not match ", vo.getKeyName().equals(NationalSetting.HOST_NAME.toString()));
    }

    
    /**
     * createNationalSettingsMap
     *
     * @return Map
     */
    private Map<String, Object> retrieveNationalSettingsMap() {
        
        Map<String, Object> nsMap = new HashMap<String, Object>();
        List<NationalSettingVo> nationalSettingList = nationalSettingDomainCapability.retrieve();
        
        for (NationalSettingVo nationalSettingVo2 : nationalSettingList) {
            nsMap.put(nationalSettingVo2.getKeyName(), nationalSettingVo2);
            LOG.debug("key: " + nationalSettingVo2.getKeyName());
        }
        
        return nsMap; 
    }
    
    /**
     * updates updateNationalSettingsVo
     *
     * @param vo vo
     * @return true/false
     */
    private boolean updateNationalSettingsVo(NationalSettingVo vo) {
        
        Map<String, Object> nsMap = retrieveNationalSettingsMap();
        
        if (nsMap == null) {
            LOG.debug("update did not succeed! ");
            
            return false;
        } else {
            NationalSettingVo pVo = (NationalSettingVo) nsMap.get(vo.getKeyName());
        
            pVo.setKeyName(vo.getKeyName());
            pVo.setStringValue(vo.getStringValue());
            pVo.setBooleanValue(vo.getBooleanValue());
            pVo.setDateValue(vo.getDateValue());
            pVo.setDecimalValue(vo.getDecimalValue());
            pVo.setIntegerValue(vo.getIntegerValue());
            pVo.setCreatedBy(vo.getCreatedBy());
            pVo.setCreatedDate(vo.getCreatedDate());
            
            nationalSettingDomainCapability.update(pVo, getTestUser());
            
            return true;
        }
        
    }
    
    
    /**
     * gets the NationalSettingFromMap
     * @param key key
     * @return NationalSettingVo
     */
    private NationalSettingVo getNationalSettingFromMap(NationalSetting key) {
        NationalSettingVo pVo = null;
        
        Map<String, Object> nsMap = retrieveNationalSettingsMap();
        
        if (nsMap != null) {
            pVo = (NationalSettingVo) nsMap.get(key.toString());
        }
        
        return pVo;
    }
    
    /**
     * Retrieves all data in the table
     */
    public void testRetrieveAll() {
        List<NationalSettingVo> nameCollection;

        nameCollection = nationalSettingDomainCapability.retrieve();

        for (NationalSettingVo vo : nameCollection) {
            LOG.debug("ID: " + vo.getId());
            LOG.debug("Name: " + vo.getKeyName());
            LOG.debug("String: " + vo.getStringValue());
            LOG.debug("Date: " + vo.getDateValue());
            LOG.debug("Integer: " + vo.getIntegerValue());
            LOG.debug("Boolean: " + vo.getBooleanValue());
        }
        
        assertTrue("Collection OK", nameCollection.size() != 0);

    }

    
}
