/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.quickaction.test;


import java.math.BigInteger;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.DrugFileToExternalInterfaceDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.quickaction.DrugFileToExternalInterfaceConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.quickaction.drugfiletoexternalinterface.DrugFileToExternalInterface;

import junit.framework.TestCase;


/**
 * This is the Drug FIle To External Interface Converter Test
 */
public class DrugFileToExternalInterfaceConverterTest extends TestCase {

    private static final Logger LOG = org.apache.log4j.Logger.getLogger(DrugFileToExternalInterfaceConverterTest.class);
    
    /**
     * Setup the test
     * 
     * @throws Exception exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception {
    }

    /**
     * The action test
     */
    public void testToDrugFileToExternalInterface() {
        DrugFileToExternalInterface drugFileToExternalInterface = DrugFileToExternalInterfaceConverter
            .toDrugFileToExternalInterface();
        drugFileToExternalInterface.setPepsIdNumber(BigInteger.valueOf(PPSConstants.I123456789));

        LOG.debug(DrugFileToExternalInterfaceDocument.instance().marshal(drugFileToExternalInterface));
        assertNotNull("Marshal failed.", DrugFileToExternalInterfaceDocument.instance().unmarshal(
            DrugFileToExternalInterfaceDocument.instance().marshal(drugFileToExternalInterface)).getPepsIdNumber());
    }
}
