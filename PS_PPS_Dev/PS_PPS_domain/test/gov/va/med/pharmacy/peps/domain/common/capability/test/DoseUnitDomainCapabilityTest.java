/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitSynonymVo;
import gov.va.med.pharmacy.peps.common.vo.DoseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.domain.common.capability.DoseUnitDomainCapability;


/**
 * DoseUnitDomainCapabilityTest
 */
public class DoseUnitDomainCapabilityTest extends DomainCapabilityTestCase {
    
//    private DoseUnitDomainCapability localDoseUnitDomainCapability;
    private DoseUnitDomainCapability nationalDoseUnitDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        this.localDoseUnitDomainCapability = getLocalOneCapability(DoseUnitDomainCapability.class);
        this.nationalDoseUnitDomainCapability = getNationalCapability(DoseUnitDomainCapability.class);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//   /* public void testFindAllDoseUnitLocal() throws Exception {
//
//        List<DoseUnitVo> nameCollection = localDoseUnitDomainCapability.retrieve();
//
//        DoseUnitVo dataVo = buildVo(RandomGenerator.nextAlphabetic(10));
//        localDoseUnitDomainCapability.create(dataVo, getTestUser());
//
//        assertTrue("Collection returned correct number", nameCollection.size() + 1 > nameCollection.size());
//    }*/

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testFindAllDoseUnitNational() throws Exception {

        List<DoseUnitVo> nameCollection = nationalDoseUnitDomainCapability.retrieve();

        DoseUnitVo dataVo = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        nationalDoseUnitDomainCapability.create(dataVo, getTestUser());
        assertTrue("Collection returned correct number", nameCollection.size() + 1 > nameCollection.size());
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
// /*   public void testRetrieveDoseUnitLocal() throws Exception {
//
//        DoseUnitVo nameCollection = localDoseUnitDomainCapability.retrieve("9991");
//
//        assertEquals("Ids should be equal", nameCollection.getId(), "9991");
//
//    }*/

    /**
     * This method builds VO
     * 
     * @param name String
     * @return DoseUnitVo
     */
    private DoseUnitVo buildVo(String name) {

        DoseUnitVo dataVo = new DoseUnitVo();

        dataVo.setInactivationDate(new java.util.Date());
        dataVo.setItemStatus(ItemStatus.INACTIVE);
        dataVo.setRequestItemStatus(RequestItemStatus.PENDING);
        dataVo.setRejectionReasonText("test");
        dataVo.setRevisionNumber(PPSConstants.I3);
        dataVo.setDoseUnitName(name);
        dataVo.setFdbDoseUnit("firstDoseUnit");
        dataVo.setRequestRejectionReason(RequestRejectionReason.INAPPROPRIATE_REQUEST);
        dataVo.setDoseUnitSynonyms(buildSynonyms());

        DoseUnitVo replacemnet = new DoseUnitVo();
        replacemnet.setId("9991");
        dataVo.setReplacementDoseUnit(replacemnet);

        return dataVo;
    }

    /**
     * Build synonyms for newly created dose unit.
     * 
     * @return Collection<DoseUnitSynonymVo>
     */
    private Collection<DoseUnitSynonymVo> buildSynonyms() {
        Collection<DoseUnitSynonymVo> synonyms = new ArrayList<DoseUnitSynonymVo>();
        DoseUnitSynonymVo syn = new DoseUnitSynonymVo();

        syn.setDoseUnitSynonymName("first");
        synonyms.add(syn);

        syn.setDoseUnitSynonymName("second");
        synonyms.add(syn);

        syn.setDoseUnitSynonymName("third");
        synonyms.add(syn);

        return synonyms;

    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
// /*   public void testCreateDoseUnitLocal() throws Exception {
//        DoseUnitVo unit = buildVo(RandomGenerator.nextAlphabetic(10));
//        DoseUnitVo returnedVo = localDoseUnitDomainCapability.create(unit, getTestUser());
//        assertNotNull("Returned with id", returnedVo.getId());
//    }*/

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testCreateDoseUnitNational() throws Exception {

        DoseUnitVo unit = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        DoseUnitVo returnedVo = nationalDoseUnitDomainCapability.create(unit, getTestUser());
        assertNotNull("Returned with id", returnedVo.getId());
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
///*    public void testUpdateDoseUnitLocal() throws Exception {
//
//        List<DoseUnitVo> names = localDoseUnitDomainCapability.retrieve();
//
//        DoseUnitVo doseUnit = localDoseUnitDomainCapability.retrieve(names.get(0).getId());
//
//        doseUnit.setRejectionReasonText("updatedRejectREasonTExt");
//
//        localDoseUnitDomainCapability.update(doseUnit, getTestUser());
//
//        DoseUnitVo retrievedUpdated = localDoseUnitDomainCapability.retrieve(doseUnit.getId());
//
//        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), "updatedRejectREasonTExt");
//
//    }*/

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
// /*   public void testUpdateDoseUnitFoeignKeyLocal() throws Exception {
//
//        List<DoseUnitVo> names = localDoseUnitDomainCapability.retrieve();
//
//        DoseUnitVo doseUnit = localDoseUnitDomainCapability.retrieve(names.get(0).getId());
//
//        doseUnit.setRejectionReasonText("updatedRejectREasonTExt");
//
//        // the first one is 9991
//        // updated the fk to one just created which is second
//        DoseUnitVo fkDoseUnit = names.get(1);
//        DoseUnitVo rfkDoseUnit = new DoseUnitVo();
//        rfkDoseUnit.setValue(fkDoseUnit.getValue());
//        rfkDoseUnit.setFdbDoseUnit(fkDoseUnit.getFdbDoseUnit());
//        rfkDoseUnit.setId(fkDoseUnit.getId());
//        doseUnit.setReplacementDoseUnit(rfkDoseUnit);
//
//        localDoseUnitDomainCapability.update(doseUnit, getTestUser());
//
//        DoseUnitVo retrievedUpdated = localDoseUnitDomainCapability.retrieve(doseUnit.getId());
//
//        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), "updatedRejectREasonTExt");
//
//    }*/

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testUpdateDoseUnitNational() throws Exception {

        List<DoseUnitVo> names = nationalDoseUnitDomainCapability.retrieve();
        DoseUnitVo doseUnit = names.get(0);

        //        DoseUnitVo doseUnit = localDoseUnitDomainCapability.retrieve(names.get(0).getId());

        doseUnit.setRejectionReasonText(PPSConstants.TEST_REASON_TEXT);
        doseUnit.setFdbDoseUnit("FDBDoseUnit");

        nationalDoseUnitDomainCapability.update(doseUnit, getTestUser());

        DoseUnitVo retrievedUpdated = nationalDoseUnitDomainCapability.retrieve(doseUnit.getId());

        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), PPSConstants.TEST_REASON_TEXT);

    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
///*    public void testUpdateDoseUnitSynonymLocal() throws Exception {
//        String otherSynonym = UUID.randomUUID().toString();
//        List<DoseUnitVo> names = localDoseUnitDomainCapability.retrieve();
//
//        DoseUnitVo dosetoBeUpdated = localDoseUnitDomainCapability.retrieve(names.get(0).getId());
//        DoseUnitSynonymVo synVo = new DoseUnitSynonymVo();
//        synVo.setDoseUnitSynonymName(otherSynonym);
//        synVo.setDoseUnitId(dosetoBeUpdated.getId());
//
//        dosetoBeUpdated.setFdbDoseUnit("UpdatedfdbDoseUnit");
//
//        int initialSize = dosetoBeUpdated.getDoseUnitSynonyms().size();
//
//        dosetoBeUpdated.getDoseUnitSynonyms().add(synVo);
//
//        localDoseUnitDomainCapability.update(dosetoBeUpdated, getTestUser());
//
//        DoseUnitVo retrievedUpdated = localDoseUnitDomainCapability.retrieve(dosetoBeUpdated.getId());
//
//        assertEquals("Should be equal", retrievedUpdated.getFdbDoseUnit(), "UpdatedfdbDoseUnit");
//        assertEquals("Should be equal", retrievedUpdated.getDoseUnitSynonyms().size(), initialSize + 1);
//
//    }*/
//

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testSearchDoseUnitNational() throws Exception {

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);

        SearchTermVo searchTerm = new SearchTermVo();
        searchTerm.setSearchField(new SearchFieldVo(FieldKey.DOSE_UNIT_NAME, EntityType.DOSE_UNIT));
        searchTerm.setValue("APP");

        searchTerm.setSearchType(SearchType.CONTAINS);

        // set the ItemStatus for testSearchDoseUnitNational
        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.APPROVED);
        searchCriteria.setRequestStatus(requestStatus);

        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        // set the setSearchTerms for testSearchDoseUnitNational
        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.DOSE_UNIT_NAME);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(Integer.MAX_VALUE);

        List<DoseUnitVo> names = nationalDoseUnitDomainCapability.search(searchCriteria);
        
        if (names.size() < PPSConstants.I8) {
            boolean isTrue = false;
            assertTrue("We should have at least 8 dose units.", isTrue);
        }
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testExistsDoseUnitNational() throws Exception {

        DoseUnitVo dosageForm = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I10));
        dosageForm.setItemStatus(ItemStatus.ACTIVE);
        nationalDoseUnitDomainCapability.create(dosageForm, getTestUser());
        boolean exists = nationalDoseUnitDomainCapability.existsByUniquenessFields(dosageForm);
        assertTrue("exists", exists);
    }

}
