/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.test.integration;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.UserGenerator;
import gov.va.med.pharmacy.peps.common.vo.DrugTextType;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.test.integration.IntegrationTestCase;


/**
 * Integration test calls into the {@link ManagedItemService} with {@link RxConsultVo}
 */
public class DrugTextServiceTest extends IntegrationTestCase {

    //  private ManagedItemService localManagedItemService;
    private ManagedItemService nationalManagedItemService;

    /**
     * Get local/national {@link ManagedItemService} instances from Spring.
     * 
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
      
        //    this.localManagedItemService = getLocalOneService(ManagedItemService.class);
        this.nationalManagedItemService = getNationalService(ManagedItemService.class);
    }

    /**
     * Local creates a new DrugTextVo. National creates a new one with the same name. Local's should be deleted and replaced
     * with National's.
     * 
     * @throws Exception if error
     */
    public void testMergeAtLocal() throws Exception {
        boolean isTrue = true;
        assertTrue("This test will come back when local is coded", isTrue);
        
        //      DrugTextVo localDrugText = new DrugTextGenerator().generateLocal();
        //  localDrugText.setDrugTextType(DrugTextType.LOCAL);
        //localDrugText.setTextLocal("LOCAL");
        //localDrugText.setValue(RandomGenerator.nextAlphabetic(10));

        //ProcessedItemVo localResult = localManagedItemService.create(localDrugText, new UserGenerator().getLocalManagerOne());
        //localDrugText = (DrugTextVo) localResult.getItem();
        //assertNotNull("Should get an ID back", localDrugText.getId());

        //DrugTextVo nationalDrugText = new DrugTextGenerator().generateNational();
        //nationalDrugText.setDrugTextType(DrugTextType.NATIONAL);
        //nationalDrugText.setTextNational("NATIONAL");
        //nationalDrugText.setTextLocal("LOCAL");
        //nationalDrugText.setValue(localDrugText.getValue());

        //ProcessedItemVo nationalResult = nationalManagedItemService.create(nationalDrugText, new UserGenerator()
        //    .getNationalManagerOne());
        //nationalDrugText = (DrugTextVo) nationalResult.getItem();
        //assertNotNull("Should get an ID back", nationalDrugText.getId());

        // Wait for national to send to locals
        //System.out.println("Waiting for National to send to locals...");
        //Thread.sleep(10000);

        //try {
        //           localManagedItemService.retrieve(localDrugText.getId(), EntityType.DRUG_TEXT);
        //    fail("Should have thrown an ItemNotFoundException since the Local Drug Textshould be deleted.");
        //}
        //catch (ItemNotFoundException e) {
        //    assertNotNull("Should throw ItemNotFoundException", e);
        //}

        //nationalDrugText = (DrugTextVo) nationalManagedItemService.retrieve(nationalDrugText.getId(), EntityType.DRUG_TEXT);
        //DrugTextVo merged = (DrugTextVo) localManagedItemService.retrieve(nationalDrugText.getId(), EntityType.DRUG_TEXT);
        //Collection<Difference> differences = merged.diff(nationalDrugText);
        //System.out.println("Differences:");
        //System.out.println(differences);
        //assertTrue("Should equal National Drug Text", differences.isEmpty());*/
        //
    }

    /**
     * Tests create, validates that it is returned with an id, active, pending also verifies the request has been pushed to
     * national
     * 
     * @throws Exception Exception
     */
    public void testCreateLocal() throws Exception {
        boolean isTrue = true;
        assertTrue("This test will be reconsitituted when national is coded.", isTrue);

//        ProcessedItemVo processItem = localManagedItemService.create(buildDrugText(DrugTextType.LOCAL), buildUser());

//        DrugTextVo returnedClass = (DrugTextVo) processItem.getItem();

//        assertNotNull("Returned Null Drug Text", returnedClass);

//        assertTrue("Created Drug Text is active", returnedClass.getItemStatus().isActive());
//        assertTrue("Created Drug Text is approved", returnedClass.getRequestItemStatus().isApproved());

//        Thread.sleep(15000);

//        ManagedItemVo classFromNational = null;

//        try {
//            classFromNational = nationalManagedItemService.retrieve(returnedClass.getId(), EntityType.DRUG_TEXT);
//        }
//        catch (Exception e) {
//            assertNull("National does not have item", classFromNational);
//        }*/
    }

    /**
     * Tests create, validates that it is returned with an id, active, pending also verifies the request has been pushed to
     * national
     * 
     * @throws Exception Exception
     */
    public void testCreateNational() throws Exception {
        ProcessedItemVo processItem = nationalManagedItemService.create(buildDrugText(DrugTextType.NATIONAL), buildUser());

        DrugTextVo returnedClass = (DrugTextVo) processItem.getItem();

        assertNotNull("Returned Null Drug Text", returnedClass);

        assertTrue("Created Drug Text is active", returnedClass.getItemStatus().isActive());
        assertTrue("Created Drug Text is approved", returnedClass.getRequestItemStatus().isApproved());

        Thread.sleep(new Long("15000"));

        ManagedItemVo classFromNational = nationalManagedItemService.retrieve(returnedClass.getId(), EntityType.DRUG_TEXT);

        assertNotNull("Local has item", classFromNational);

    }

    /**
     * Build a new User for the DrugTextServiceTest.
     * 
     * @return UserVo
     */
    private UserVo buildUser() {
        return new UserGenerator().getLocalManagerOne();
    }

    /**
     * Creates an arbitrary drug class for testing things
     * 
     * There is no significance to any of the values
     * 
     * @param drugTextType DrugTextType
     * @return drugClassVo
     */
    private DrugTextVo buildDrugText(DrugTextType drugTextType) {
        DrugTextVo drugText = new DrugTextVo();
        drugText.setDrugTextType(drugTextType);

        if (drugTextType.isNational()) {
            drugText.setTextNational("textNatiojnal");
        }

        drugText.setTextLocal("textLocal");

        drugText.setValue(RandomGenerator.nextAlphabetic(PPSConstants.I10));

        return drugText;
    }
}
