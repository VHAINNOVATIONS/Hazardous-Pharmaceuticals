/**
 * Source file created in 2011 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.test;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.GenericNameGenerator;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.VuidItemType;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.VaGenericNameConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vagenericnamesyncrequest.VaGenericNameSyncRequest;

import junit.framework.TestCase;


/**
 * VaGenericNameConverterTest's brief summary
 * 
 * Details of VaGenericNameConverterTest's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class VaGenericNameConverterTest extends TestCase {
    private static final Logger LOG = Logger.getLogger(VaGenericNameConverterTest.class);
    private Map<FieldKey, Difference> differences;
    private GenericNameVo genericNameVo;
    private VuidStatusHistoryVo vuidStatusHistoryVo;
    private List<VuidStatusHistoryVo> effectiveDates;
    
    /**
     * Tests modify functionality.
     */
    public void testModify() {

        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.set(PPSConstants.I2011, Calendar.JANUARY, 1);
        calendar2.set(PPSConstants.I2011, Calendar.FEBRUARY, 1);
        String vuid = "01233456";
        
        differences.put(FieldKey.ITEM_STATUS, new Difference(FieldKey.ITEM_STATUS, ItemStatus.ACTIVE, ItemStatus.INACTIVE));
        vuidStatusHistoryVo.setEffectiveDateTime(calendar2.getTime());
        vuidStatusHistoryVo.setEffectiveDtmStatus(ItemStatus.ACTIVE);
        vuidStatusHistoryVo.setItemType(VuidItemType.GENERIC);
        effectiveDates.add(vuidStatusHistoryVo);
        genericNameVo.setValue("VAGenericName");
        genericNameVo.setInactivationDate(calendar.getTime());
        genericNameVo.setMasterEntryForVuid(true);
        genericNameVo.setVuid(vuid);
        genericNameVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        genericNameVo.setEffectiveDates(effectiveDates);
        VaGenericNameSyncRequest vaGenericNameSyncRequest = 
            VaGenericNameConverter.toVaGenericNameSyncRequest(genericNameVo, differences, ItemAction.MODIFY);
        
        assertNotNull("VA Generic Name request is null.\n", vaGenericNameSyncRequest);
        assertNotNull("VA Generic Name name is null.\n", vaGenericNameSyncRequest.getVaGenericNameName());
        assertNotNull("VA Generic Name Inactivation Date is Null.\n", vaGenericNameSyncRequest.getInactivationDate());
        assertNotNull("VA Generic Name Effective Date is Null.\n", vaGenericNameSyncRequest.getEffectiveDateTimeRecord());
        assertEquals("VA Generic Name Master Entry for VUID is not true.\n", "1", 
                     vaGenericNameSyncRequest.getMasterEntryForVuid());
        assertEquals("VA Generic Name VUID is not 0123456.\n", vuid, vaGenericNameSyncRequest.getVuid());

        LOG.debug("Test Results" + vaGenericNameSyncRequest.getVaGenericNameName() + " Tested.\n;");
    }

    /**
     * Sets up tests.
     * 
     * @throws Exception Exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        differences = new HashMap<FieldKey, Difference>();
        genericNameVo = new GenericNameGenerator().getRandom();
        vuidStatusHistoryVo = new VuidStatusHistoryVo();
        effectiveDates = new ArrayList<VuidStatusHistoryVo>();
    }

}
