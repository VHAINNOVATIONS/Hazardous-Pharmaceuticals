/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter.test;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.LocalConsoleInfoVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalConsoleInfoDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalConsoleInfoDoKey;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.LocalConsoleInfoConverter;

import junit.framework.TestCase;


/**
 * Test {@link LocalConsoleInfoConverter}
 */
public class LocalConsoleInfoConverterTest extends TestCase {
    private LocalConsoleInfoConverter localConsoleInfoConverter = new LocalConsoleInfoConverter();
    
    /**
     * Test
     */
    public void testToLocalConsole() {
        EplLocalConsoleInfoDo eplDo = new EplLocalConsoleInfoDo();
        EplLocalConsoleInfoDoKey key = new EplLocalConsoleInfoDoKey("siteNumber", "softwareUpdateType");
        eplDo.setKey(key);
        eplDo.setServerName("serverName");
        eplDo.setSiteName("siteName");
        eplDo.setSoftwareUpdateVersion("softwareUpdateVersion");
        eplDo.setVersionInstallDtm("versionInstallDtm");
        LocalConsoleInfoVo localConsoleVo = localConsoleInfoConverter.convert(eplDo);
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, eplDo.getServerName(), localConsoleVo.getServerName());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, eplDo.getSiteName(), localConsoleVo.getSiteName());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, eplDo.getSoftwareUpdateVersion(), localConsoleVo.getSoftwareUpdateVersion());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, eplDo.getVersionInstallDtm(), localConsoleVo.getVersionInstallDtm());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, eplDo.getKey().getSiteNumber(), localConsoleVo.getSiteNumber());
        assertEquals(PPSConstants.SHOULD_BE_EQUAL, eplDo.getKey().getSoftwareUpdateType(), 
            localConsoleVo.getSoftwareUpdateType());
    }
}
