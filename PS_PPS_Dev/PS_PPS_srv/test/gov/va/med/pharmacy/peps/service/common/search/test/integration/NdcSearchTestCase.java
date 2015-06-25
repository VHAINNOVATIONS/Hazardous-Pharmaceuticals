/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.search.test.integration;


import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;


/**
 * Tests the NDC search
 */
public abstract class NdcSearchTestCase extends ManagedItemSearchTestCase { 

    private static boolean INITIALIZED = false;

    private NdcVo ndc;

    /**
     * Constructor
     * 
     * @param name String
     */
    public NdcSearchTestCase(String name) {
        super(name);
    }

    /**
     * Sets up the test fixtures
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        super.setUp();
        getSearchCriteria().setEntityType(EntityType.NDC);

        if (!INITIALIZED) {
            try {
                createNdcData();
            } catch (Exception e) {
                fail(e.toString());
            }
        }
    }

     /**
     * Initializes the database
     * 
     */
    private void createNdcData() {
        NdcGenerator generator = new NdcGenerator();

        try {
            this.ndc = generator.getFirst();
            initializeNdcVo(ndc);
            getManagedItemService().create(ndc, getUser());
        } catch (DuplicateItemException ex) { 
            fail("This should not be a duplicate item.");
        } catch (ValidationException e) {
            fail(e.toString());
        }

        INITIALIZED = true;
    }

    /**
     * Utility method that creates a stubbed ndc object for testing.
     * 
     * @param ndcVo - the NDC to intialize
     */
    public void initializeNdcVo(NdcVo ndcVo) {
        ndcVo.setNdc(getTermFixture().getString("ndc"));
        ndcVo.setTradeName(getTermFixture().getString("trade_name"));
        ndcVo.setPackageSize(new Double("66.5"));
    };
}
