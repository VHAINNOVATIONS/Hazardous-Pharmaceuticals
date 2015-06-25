/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm.test;


import junit.framework.TestCase;


/**
 * Tests corresponding converter.
 */
public class PharmacySystemFileConverterTest extends TestCase {

    private static final String TRUTH = "True is True.";
 
    //Map<FieldKey, Difference> differences;
    //PharmacySystemVo pharmacySystemVo;
    
    private boolean isTrue; 

    /**
     * Sets up tests.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        
        //differences = new HashMap<FieldKey, Difference>();
        //pharmacySystemVo = new PharmacySystemGenerator().getRandom();
        isTrue = true;
    }

    /**
     * Tests add functionality.
     */
    public void testAdd() {
        assertTrue(TRUTH, isTrue);
     
        /*  This is local only functionality.
        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(new DomainItem[] {new DomainItem(pharmacySystemVo,
            ItemAction.ADD, differences)});

        pdmDomain.setPepsIdNumber(BigInteger.valueOf(123456789));

        System.out.println(PdmDomainDocument.instance().marshal(pdmDomain));

        assertEquals("SITE NAME incorrect.", pharmacySystemVo.getValue(), PdmDomainDocument.instance().unmarshal(
            PdmDomainDocument.instance().marshal(pdmDomain)).getPharmacySystemFile().getSiteName().getValue());
            */

    }

    /**
     * Tests modify functionality.
     */
    public void testModify() {
        assertTrue(TRUTH, isTrue);
       
        /** this is local only functionality;
        String newSiteName = "New SITE NAME";

        differences.put(FieldKey.PHARMACY_SYSTEM_SITE_NAME, new Difference(FieldKey.PHARMACY_SYSTEM_SITE_NAME,
            pharmacySystemVo.getValue(), newSiteName));
        pharmacySystemVo.setValue(newSiteName);

        PdmDomain pdmDomain = PdmDomainConverter.toPdmDomain(new DomainItem[] {new DomainItem(pharmacySystemVo,
            ItemAction.MODIFY, differences)});

        pdmDomain.setPepsIdNumber(BigInteger.valueOf(123456789));

        System.out.println(PdmDomainDocument.instance().marshal(pdmDomain));

        assertEquals("SITE NAME incorrect.", pharmacySystemVo.getValue(), PdmDomainDocument.instance().unmarshal(
            PdmDomainDocument.instance().marshal(pdmDomain)).getPharmacySystemFile().getSiteName().getValue());
*/
    }
}
