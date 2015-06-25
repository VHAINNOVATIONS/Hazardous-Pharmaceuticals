/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;




/**
 * DataFieldsDomainCapabilityTest
 */
public class DataFieldsDomainCapabilityTest extends DomainCapabilityTestCase {

    //    private DataFieldsDomainCapability localdataFieldsDomainCapability;
//    private NdcDomainCapability localNdcDomainCapability;
    

    // private DataFieldsDomainCapability nationaldataFieldsDomainCapability;

    /**
     * testSkipped
     * @throws Exception Exception
     */
    public void testSkipped() throws Exception {
        boolean isTrue = true;
        assertTrue("Entire class is skipped.", isTrue);
    }

    /**
     * Retrieve the capability being tested from the Spring application context.
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        localdataFieldsDomainCapability = getLocalOneCapability(DataFieldsDomainCapability.class);
//        localNdcDomainCapability = getLocalOneCapability(NdcDomainCapability.class);
        
//        this.user = UserVo.getSystemUser(getLocalOneCapability(EnvironmentUtility.class));

        // this.nationaldataFieldsDomainCapability = getNationalCapability(DataFieldsDomainCapability.class);
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception
     */
    
//    public void testFindVaDfOwnerLocal() throws Exception {
//        NdcVo ndc = new NdcGenerator().generateMinimalNdc();
//      
//        ndc = localNdcDomainCapability.create(ndc, user);
//        Long ndcId = new Long(ndc.getId());
//
//        EplVadfOwnerDo ownerDo = localdataFieldsDomainCapability.retrieveOwnerTest("eplNdc", createMinimalNdc(ndcId));
//
//        System.out.println(ownerDo.getId());
//        System.out.println(ownerDo.getEplNdc().getEplId());
//        assertNotNull("Returned Owner", ownerDo);
//    }

    /**
     * @param id of type Long
     * @return EplNdcDo
     */
    
 //   private EplNdcDo createMinimalNdc(Long id) {
 //       EplNdcDo testa = new EplNdcDo();
 //       testa.setEplId(id);

 //       return testa;
 //   }

    // /**
    // *
    // * @return EplVadfOwnerDo
    // */
    // private EplVadfOwnerDo createMinimalVaDfOwner() {
    // EplVadfOwnerDo testa = new EplVadfOwnerDo();
    // testa.setId(new Long(10));
    //
    // return testa;
    // }

}
