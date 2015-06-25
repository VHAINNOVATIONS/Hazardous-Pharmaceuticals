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
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.domain.common.capability.IngredientDomainCapability;


/**
 * IngredientDomainCapabiiltyTest.
 */
public class IngredientDomainCapabiiltyTest extends DomainCapabilityTestCase {
    private static final String I9991 = "9991";
    private static final String REASON_TEXT = "updatedRejectREasonTExt";
    private IngredientDomainCapability nationalIngredientDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        this.nationalIngredientDomainCapability = getNationalCapability(IngredientDomainCapability.class);
    }


    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testFindAllIngredientNational() throws PharmacyException {
        int originalCount = 0;
        List<IngredientVo> rCollection;

        rCollection = nationalIngredientDomainCapability.retrieve();
        originalCount = rCollection.size();

        IngredientVo dataVo = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I5));
        dataVo.setItemStatus(ItemStatus.ACTIVE);
        nationalIngredientDomainCapability.create(dataVo, getTestUser());
        rCollection = nationalIngredientDomainCapability.retrieve();

        assertEquals("Collection returned correct number", originalCount + 1, rCollection.size());
    }

    /**
     * This method buidlsVO
     * 
     * @param name String
     * @return IngredientVo
     */
    private IngredientVo buildVo(String name) {
        IngredientVo dataVo = new IngredientVo();

        IngredientVo primaryVo = new IngredientVo();
        primaryVo.setId(I9991);
        primaryVo.setValue(RandomGenerator.nextAlphabetic(PPSConstants.I5));
        
        dataVo.setPrimaryIngredient(dataVo);
        dataVo.setIngredientIen(String.valueOf(RandomGenerator.nextInt()));
        dataVo.setInactivationDate(new Date());
        dataVo.setVuid("testDispVuid");
        dataVo.setValue(name);
        dataVo.setInactivationDate(new Date());
        dataVo.setItemStatus(ItemStatus.INACTIVE);
        dataVo.setRequestItemStatus(RequestItemStatus.PENDING);
        dataVo.setRejectionReasonText("reason");
        dataVo.setRevisionNumber(PPSConstants.I3);

        VuidStatusHistoryVo statusVo = new VuidStatusHistoryVo();
        statusVo.setEffectiveDtmStatus(ItemStatus.ACTIVE);
        statusVo.setEffectiveDateTime(new Date());
        List<VuidStatusHistoryVo> list = new ArrayList<VuidStatusHistoryVo>();
        list.add(statusVo);
        dataVo.setEffectiveDates(list);
        
        return dataVo;
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testCreateIngredientNational() throws PharmacyException {

        IngredientVo dataVo = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I5));

        IngredientVo returnedVo = nationalIngredientDomainCapability.create(dataVo, getTestUser());
        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testUpdateIngredientNational() throws PharmacyException {

        List<IngredientVo> names = nationalIngredientDomainCapability.retrieve();

        IngredientVo ingredient = nationalIngredientDomainCapability.retrieve(names.get(0).getId());

        ingredient.setRejectionReasonText(REASON_TEXT);

        nationalIngredientDomainCapability.update(ingredient, getTestUser());

        IngredientVo retrievedUpdated = nationalIngredientDomainCapability.retrieve(ingredient.getId());

        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), REASON_TEXT);

    }

    /**
     * testRevisionNumber.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testRevisionNumber() throws PharmacyException {
        
        long revNumber = nationalIngredientDomainCapability.getRevisionNumber(I9991);

        assertNotNull("Version number not returned", revNumber);
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testSearchIngredientNational() throws PharmacyException {

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);

        SearchTermVo searchTerm = new SearchTermVo(EntityType.PRODUCT, FieldKey.ACTIVE_INGREDIENT, "%");

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.APPROVED);
        searchCriteria.setRequestStatus(requestStatus);

        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PPSConstants.I10);

        List<IngredientVo> names = nationalIngredientDomainCapability.search(searchCriteria);

        assertFalse("Returned data", names.isEmpty());

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws PharmacyException PharmacyException
     */
    public void testExistsIngredientNational() throws PharmacyException {

        IngredientVo unit = buildVo(RandomGenerator.nextAlphabetic(PPSConstants.I5));
        unit.setItemStatus(ItemStatus.ACTIVE);
        nationalIngredientDomainCapability.create(unit, getTestUser());
        boolean exists = nationalIngredientDomainCapability.existsByUniquenessFields(unit);
        assertTrue("exists", exists);
    }
   
}
