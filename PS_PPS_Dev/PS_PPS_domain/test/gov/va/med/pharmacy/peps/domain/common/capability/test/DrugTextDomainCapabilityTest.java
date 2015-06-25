/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.vo.DrugTextSynonymVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextType;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugTextDomainCapability;


/**
 * DrugTextDomainCapability Class
 */
public class DrugTextDomainCapabilityTest extends DomainCapabilityTestCase {
    
//    private DrugTextDomainCapability localDrugTextDomainCapability;
    private DrugTextDomainCapability nationalDrugTextDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        this.localDrugTextDomainCapability = getLocalOneCapability(DrugTextDomainCapability.class);
        this.nationalDrugTextDomainCapability = getNationalCapability(DrugTextDomainCapability.class);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testFindAllDrugTextLocal() throws Exception {
//        int originalCount = 0;
//        List<DrugTextVo> nameCollection;
//
//        nameCollection = localDrugTextDomainCapability.retrieve();
//
//        if (nameCollection != null) {
//            originalCount = nameCollection.size();
//        }
//
//        DrugTextVo dataVo = buildVo(RandomGenerator.nextAlphabetic(10));
//        dataVo.setItemStatus(ItemStatus.ACTIVE);
//        localDrugTextDomainCapability.create(dataVo, getTestUser());
//
//        nameCollection = localDrugTextDomainCapability.retrieve();
//
//        assertEquals("Collection returned correct number", originalCount + 1, nameCollection.size());
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testFindAllDrugTextNational() throws Exception {
        int originalCount = 0;
        List<DrugTextVo> rCollection;

        rCollection = nationalDrugTextDomainCapability.retrieve();
        originalCount = rCollection.size();

        DrugTextVo dataVo = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        dataVo.setItemStatus(ItemStatus.ACTIVE);
        nationalDrugTextDomainCapability.create(dataVo, getTestUser());
        rCollection = nationalDrugTextDomainCapability.retrieve();

        assertEquals("Collection returned correct number.", originalCount + 1, rCollection.size());
    }

    /**
     * This method buidlsVO List<DrugTextVo> nameCollection = nationalDrugTextDomainCapability.retrieve();
     * 
     * @param name String
     * @return DrugTextVo
     */
    private DrugTextVo buildVo(String name) {

        DrugTextVo objectVo = new DrugTextVo();
        objectVo.setValue(name);
        objectVo.setInactivationDate(new Date());
        objectVo.setItemStatus(ItemStatus.INACTIVE);
        objectVo.setRequestItemStatus(RequestItemStatus.PENDING);
        objectVo.setRejectionReasonText("S");
        objectVo.setTextLocal(DrugTextType.LOCAL.name());
        objectVo.setTextNational(DrugTextType.NATIONAL.name());
        objectVo.setDrugTextType(DrugTextType.NATIONAL);

        Collection<DrugTextSynonymVo> syns = new ArrayList<DrugTextSynonymVo>();
        DrugTextSynonymVo synVo = new DrugTextSynonymVo();
        synVo.setDrugTextSynonymName("spanish");

        syns.add(synVo);

        objectVo.setDrugTextSynonyms(syns);
        objectVo.setRevisionNumber(PPSConstants.I3);

        return objectVo;
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
////    public void testCreateDrugTextLocal() throws Exception {
//        DrugTextVo medINt = buildVo(RandomGenerator.nextAlphabetic(10));
//        DrugTextVo returnedVo = localDrugTextDomainCapability.create(medINt, getTestUser());
//        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testCreateDrugTextNational() throws Exception {

        DrugTextVo medINt = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        DrugTextVo returnedVo = nationalDrugTextDomainCapability.create(medINt, getTestUser());
        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testUpdateDrugTextLocal() throws Exception {
//
//        List<DrugTextVo> names = localDrugTextDomainCapability.retrieve();
//
//        DrugTextVo text = localDrugTextDomainCapability.retrieve(names.get(0).getId());
//
//        text.setRejectionReasonText("updatedRejectREasonTExt");
//
//        localDrugTextDomainCapability.update(text, getTestUser());
//
//        DrugTextVo retrievedUpdated = localDrugTextDomainCapability.retrieve(text.getId());
//
//        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), "updatedRejectREasonTExt");
//
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testUpdateDrugTextNational() throws Exception {

        List<DrugTextVo> names = nationalDrugTextDomainCapability.retrieve();

        DrugTextVo text = nationalDrugTextDomainCapability.retrieve(names.get(0).getId());

        text.setRejectionReasonText(PPSConstants.TEST_REASON_TEXT);

        nationalDrugTextDomainCapability.update(text, getTestUser());

        DrugTextVo retrievedUpdated = nationalDrugTextDomainCapability.retrieve(text.getId());

        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), PPSConstants.TEST_REASON_TEXT);

    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testUpdateDrugTextSynonymLocal() throws Exception {
//
//        List<DrugTextVo> names = localDrugTextDomainCapability.retrieve();
//
//        DrugTextVo text = localDrugTextDomainCapability.retrieve(names.get(0).getId());
//
//        int intSize = text.getDrugTextSynonyms().size();
//
//        DrugTextSynonymVo syn = new DrugTextSynonymVo();
//
//        syn.setDrugTextSynonymName(RandomGenerator.nextAlphabetic(10));
//
//        text.getDrugTextSynonyms().add(syn);
//
//        localDrugTextDomainCapability.update(text, getTestUser());
//
//        DrugTextVo retrievedUpdated = localDrugTextDomainCapability.retrieve(text.getId());
//
//        assertEquals("Should have synonym", intSize + 1, retrievedUpdated.getDrugTextSynonyms().size());
//
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testSearchDrugTextNational() throws Exception {
        int originalCount = 0;
        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);

        SearchTermVo searchTerm = new SearchTermVo();
        searchTerm.setSearchField(new SearchFieldVo(FieldKey.VALUE, EntityType.DRUG_TEXT));
        searchTerm.setValue("%");

        searchTerm.setSearchType(SearchType.CONTAINS);

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.INACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        // testSearchDrugTextNational
        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.PENDING);
        searchCriteria.setRequestStatus(requestStatus);

        // testSearchDrugTextNational
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        // testSearchDrugTextNational
        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(Integer.MAX_VALUE);

        List<DrugTextVo> names;
        names = nationalDrugTextDomainCapability.search(searchCriteria);
        originalCount = names.size();

        DrugTextVo dataVo = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        nationalDrugTextDomainCapability.create(dataVo, getTestUser());

        names = nationalDrugTextDomainCapability.search(searchCriteria);

        assertEquals("Collection returned correct number", originalCount + 1, names.size());
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testExistsDrugTextNational() throws Exception {

        DrugTextVo med = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        med.setItemStatus(ItemStatus.ACTIVE);
        nationalDrugTextDomainCapability.create(med, getTestUser());
        boolean exists = nationalDrugTextDomainCapability.existsByUniquenessFields(med);
        assertTrue("exists", exists);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testExistsDrugTextLocal() throws Exception {
//
//        DrugTextVo med = buildVo(RandomGenerator.nextAlphabetic(10));
//        med.setItemStatus(ItemStatus.ACTIVE);
//        localDrugTextDomainCapability.create(med, getTestUser());
//        boolean exists = localDrugTextDomainCapability.existsByUniquenessFields(med);
//        assertTrue("exists", exists);
//    }

//    /**
//     * When a National RX Consult is added at Local that matches an existing Local RX Consult name, the associations the the
//     * original Local RX Consult need to be updated to the new National one.
//     * 
//     * @throws Exception if error
//     */
////    public void testReassociateExistingItems() throws Exception {
//        String nonUniqueName = String.valueOf(System.currentTimeMillis());
//
//        DrugTextGenerator generator = new DrugTextGenerator();
//        DrugTextVo local = generator.generateLocal();
//        local.setItemStatus(ItemStatus.INACTIVE);
//        local.setValue(nonUniqueName);
//        local = localDrugTextDomainCapability.create(local, getTestUser());
//
//        ProductDomainCapability productDomainCapability = getLocalOneCapability(ProductDomainCapability.class);
//        ProductVo product = productDomainCapability.retrieve("9991");
//
//        System.out.println(product.getLocalDrugTexts());
//        List<DrugTextVo> warningLabels = new ArrayList<DrugTextVo>(1);
//        warningLabels.add(local);
//        product.setLocalDrugTexts(warningLabels);
//        product = productDomainCapability.update(product, getTestUser());
//
//        DrugTextVo national = generator.generateNational();
//        national.setValue(RandomGenerator.nextAlphabetic(10));
//        national = localDrugTextDomainCapability.create(national, getTestUser());
//
//        local.setItemStatus(ItemStatus.ACTIVE);
//        local = localDrugTextDomainCapability.update(local, getTestUser());
//
//        localDrugTextDomainCapability.reassociateExistingItems(local, national, getTestUser());
//
//        product = productDomainCapability.retrieve(product.getId());
//
//        assertEquals("Drug TExt ID should be equal to National's ID!", national.getId(), product.getLocalDrugTexts().get(0)
//            .getId());
//    }
}
