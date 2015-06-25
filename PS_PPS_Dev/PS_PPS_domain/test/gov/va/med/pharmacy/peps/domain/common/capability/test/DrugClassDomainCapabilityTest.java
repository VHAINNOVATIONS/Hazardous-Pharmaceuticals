/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.PharmacyException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.RandomGenerator;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassificationTypeVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.domain.common.capability.DrugClassDomainCapability;


/**
 * DrugClassDomainCapabilityTest
 */
public class DrugClassDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final String I9991 = "9991";
    private static final String I9992 = "9992";
    private static final String REJECT_REASON = "updatedRejectREasonTExt";

    // private DrugClassDomainCapability localdrugClassDomainCapability;
    private DrugClassDomainCapability nationaldrugClassDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {

        // this.localdrugClassDomainCapability =
        // getLocalOneCapability(DrugClassDomainCapability.class);
        this.nationaldrugClassDomainCapability = getNationalCapability(DrugClassDomainCapability.class);
    }

    /**
     * This method gets all the FdbMfg in the db.
     * @throws PharmacyException PharmacyException
     */
    public void testFindAllDrugClassNational() throws PharmacyException {
        int originalCount = 0;
        List<DrugClassVo> rCollection;

        rCollection = nationaldrugClassDomainCapability.retrieve();
        originalCount = rCollection.size();

        DrugClassVo dataVo = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I5),
                RandomGenerator.nextAlphabetic(PPSConstants.I5));
        dataVo.setItemStatus(ItemStatus.ACTIVE);
        nationaldrugClassDomainCapability.create(dataVo, getTestUser());
        rCollection = nationaldrugClassDomainCapability.retrieve();

        assertEquals("Collection returned correct number", originalCount + 1,
                rCollection.size());
    }

    /**
     * testRevisionNumber
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testRevisionNumber() throws PharmacyException {
        String id = I9991;
        long revNumber = nationaldrugClassDomainCapability
                .getRevisionNumber(id);

        assertNotNull("Version number not returned", revNumber);
    }

    /**
     * This method buidlsVO
     * 
     * @param classi String
     * @param code String
     * @return DrugClassificationVo
     */
    private DrugClassVo buildVo(String classi, String code) {
        DrugClassVo drugClass = new DrugClassVo();
        drugClass.setVuid("23423");
        drugClass.setDrugClassIen("23432234");
        drugClass.setMasterEntryForVuid(true);
        drugClass.setDescription("newTestDispValue");
        drugClass.setItemStatus(ItemStatus.INACTIVE);
        drugClass.setRequestItemStatus(RequestItemStatus.PENDING);
        drugClass.setInactivationDate(new java.util.Date());
        drugClass.setRejectionReasonText("reason");
        drugClass.setRevisionNumber(PPSConstants.I25);
        drugClass.setClassification(classi);
        drugClass.setCode(code);

        DrugClassificationTypeVo type = new DrugClassificationTypeVo();
        type.setId("1");

        drugClass.setClassificationType(type);

        DrugClassVo parent = new DrugClassVo();
        parent.setId(I9992);
        drugClass.setParentDrugClass(parent);
        VuidStatusHistoryVo statusVo = new VuidStatusHistoryVo();
        statusVo.setEffectiveDtmStatus(ItemStatus.ACTIVE);
        statusVo.setEffectiveDateTime(new Date());
        List<VuidStatusHistoryVo> list = new ArrayList<VuidStatusHistoryVo>();
        list.add(statusVo);
        drugClass.setEffectiveDates(list);

        return drugClass;
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testCreateDrugClassNational() throws PharmacyException {

        DrugClassVo drugClass = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I5),
                RandomGenerator.nextAlphabetic(PPSConstants.I5));
        DrugClassVo returnedVo = nationaldrugClassDomainCapability.create(
                drugClass, getTestUser());
        
        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
    }

    
    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testUpdatedrugClassNational() throws PharmacyException {

        List<DrugClassVo> names = nationaldrugClassDomainCapability.retrieve();

        DrugClassVo drugClass = nationaldrugClassDomainCapability
                .retrieve(names.get(0).getId());

        drugClass.setRejectionReasonText(REJECT_REASON);

        nationaldrugClassDomainCapability.update(drugClass, getTestUser());

        DrugClassVo retrievedUpdated = nationaldrugClassDomainCapability
                .retrieve(drugClass.getId());

        assertEquals("Should be equal ",
                retrievedUpdated.getRejectionReasonText(),
                REJECT_REASON);

    }

    

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testSearchdrugClassNational() throws PharmacyException {

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(
                SearchDomain.SIMPLE, Environment.NATIONAL);

        SearchTermVo searchTerm = new SearchTermVo();
        searchTerm.setSearchField(new SearchFieldVo(FieldKey.CLASSIFICATION,
                EntityType.DRUG_CLASS));
        searchTerm.setValue("%");

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.INACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.PENDING);
        searchCriteria.setRequestStatus(requestStatus);

        // set SearchTerms for testSearchdrugClassNational
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(Integer.MAX_VALUE);

        List<DrugClassVo> names = nationaldrugClassDomainCapability
                .search(searchCriteria);

        assertFalse("Returned data", names.isEmpty());

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testExistsdrugClassNational() throws PharmacyException {

        DrugClassVo unit = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I5),
                RandomGenerator.nextAlphabetic(PPSConstants.I5));
        unit.setItemStatus(ItemStatus.ACTIVE);
        nationaldrugClassDomainCapability.create(unit, getTestUser());
        boolean exists = nationaldrugClassDomainCapability
                .existsByUniquenessFields(unit);
        assertTrue("exists", exists);
    }


    /**
     * This method gets all the FdbMfg in the db.
     * @throws PharmacyException PharmacyException
     * 
     * @throws Exception
     */
    public void testUpdateDrugClassParentNational() throws PharmacyException {

        DrugClassVo name = nationaldrugClassDomainCapability.retrieve(I9991);

        DrugClassVo parent = nationaldrugClassDomainCapability.retrieve(I9992);

        name.setParentDrugClass(parent);
        nationaldrugClassDomainCapability.update(name, getTestUser());

        DrugClassVo retrievedUpdatedName = nationaldrugClassDomainCapability
                .retrieve(I9991);

        assertEquals("Should be equal", retrievedUpdatedName
                .getParentDrugClass().getId(), parent.getId());

    }
}
