/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.test;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.DrugUnitGenerator;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.DrugUnitConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugunitsyncrequest.DrugUnitSyncRequest;

import junit.framework.TestCase;


/**
 * DrugUnitConverterTest's brief summary
 * 
 * Details of DrugUnitConverterTest's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class DrugUnitConverterTest extends TestCase {
    private static final Logger LOG = Logger.getLogger(DrugUnitConverterTest.class);
    private Map<FieldKey, Difference> differences;
    private DrugUnitVo drugUnitVo;

    /**
     * Tests modify functionality.
     */
    public void testModify() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(PPSConstants.I2011, Calendar.JANUARY, 1);
        String newName = "New NAME";

        differences.put(FieldKey.VALUE, new Difference(FieldKey.VALUE, drugUnitVo.getValue(), newName));
        drugUnitVo.setValue("DrugUnitName");
        drugUnitVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        drugUnitVo.setInactivationDate(calendar.getTime());
        DrugUnitSyncRequest drugUnitSyncRequest =
                DrugUnitConverter.toDrugUnitSyncRequest(drugUnitVo, differences, ItemAction.MODIFY);

        assertNotNull("DrugUnitSyncRequest is null.\n", drugUnitSyncRequest);
        assertNotNull("DrugUnitSyncRequest name is null.\n", drugUnitSyncRequest.getDrugUnitName());
        assertNotNull("DrugUnitSyncRequest Inactivation Date is null.\n", drugUnitSyncRequest.getInactivationDate());
        assertEquals("DrugUnitSyncRequest request type is not Modify. \n", "MODIFY", drugUnitSyncRequest.getRequestType());

        LOG.debug(drugUnitSyncRequest.getDrugUnitName() + ", " + drugUnitSyncRequest.getInactivationDate());

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
        drugUnitVo = new DrugUnitGenerator().getRandom();
    }

}
