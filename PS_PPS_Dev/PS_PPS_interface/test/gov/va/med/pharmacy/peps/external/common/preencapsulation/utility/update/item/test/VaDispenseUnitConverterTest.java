/**
 * Source file created in 2011 by Southwest Research Institute
 * 
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.test;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.DispenseUnitGenerator;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.VaDispenseUnitConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vadispenseunitsyncrequest.VaDispenseUnitSyncRequest;

import junit.framework.TestCase;


/**
 * VaDispenseUnitConverterTest's brief summary
 * 
 * Details of VaDispenseUnitConverterTest's operations, special dependencies or protocols developers shall know about when using
 * the class.
 * 
 */
public class VaDispenseUnitConverterTest extends TestCase {
    private static final Logger LOG = Logger.getLogger(VaDispenseUnitConverterTest.class);
    private Map<FieldKey, Difference> differences;
    private DispenseUnitVo dispenseUnitVo;

    /**
     * Tests modify functionality.
     */
    public void testModify() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(PPSConstants.I2011, Calendar.JANUARY, 1);
        String newName = "New NAME";

        differences.put(FieldKey.VALUE, new Difference(FieldKey.VALUE, dispenseUnitVo.getValue(), newName));
        dispenseUnitVo.setValue("VADispenseUnitName");
        dispenseUnitVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        dispenseUnitVo.setInactivationDate(calendar.getTime());
        VaDispenseUnitSyncRequest vaDispenseUnitSyncRequest = VaDispenseUnitConverter.toVaDispenseUnitSyncRequest(
                dispenseUnitVo, differences, ItemAction.MODIFY);

        assertNotNull("VADispenseUnitSyncRequest is null.\n", vaDispenseUnitSyncRequest);
        assertNotNull("VADispenseUnitSyncRequest name is null.\n", vaDispenseUnitSyncRequest.getVaDispenseUnitName());
        assertNotNull("VADispenseUnitSyncRequest Inactivation Date is null.\n", 
                vaDispenseUnitSyncRequest.getInactivationDate());
        assertEquals("VADispenseUnitSyncRequest request type is not Modify. \n", "MODIFY",
                vaDispenseUnitSyncRequest.getRequestType());

        LOG.debug(vaDispenseUnitSyncRequest.getVaDispenseUnitName() + ", "
                + vaDispenseUnitSyncRequest.getInactivationDate());

    }

    /**
     * Sets up tests.
     *  
     * @throws java.lang.Exception java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        differences = new HashMap<FieldKey, Difference>();
        dispenseUnitVo = new DispenseUnitGenerator().getRandom();
    }

}
