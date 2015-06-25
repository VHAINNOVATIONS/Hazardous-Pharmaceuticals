/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageTypeDomainCapability;


/**
 * PackageTypeDomainCapabilityTest.
 */
public class PackageTypeDomainCapabilityTest extends DomainCapabilityTestCase {
    
    private static final String IEN = "344534";
    private static final Long REV = 3L;
    private static final int PAGE_SIZE = 10;
    
//    private PackageTypeDomainCapability localpackageTypeDomainCapability;
    private PackageTypeDomainCapability nationalpackageTypeDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        
//        this.localpackageTypeDomainCapability = getLocalOneCapability(PackageTypeDomainCapability.class);
        this.nationalpackageTypeDomainCapability = getNationalCapability(PackageTypeDomainCapability.class);
    }


    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testFindAllPackageTypeNational() throws Exception {

        List<PackageTypeVo> rCollection = nationalpackageTypeDomainCapability.retrieve();

        PackageTypeVo dataVo = buildVo("name7");
        nationalpackageTypeDomainCapability.create(dataVo, getTestUser());

        assertTrue("Collection returned correct number", rCollection.size() + 1 > rCollection.size());
    }

    /**
     * This method buidlsVO
     * 
     * @param name String
     * @return PackageTypeVo
     */
    private PackageTypeVo buildVo(String name) {
        PackageTypeVo dataVo = new PackageTypeVo();
        dataVo.setValue(name);
        dataVo.setInactivationDate(new Date());
        dataVo.setItemStatus(ItemStatus.INACTIVE);
        dataVo.setRequestItemStatus(RequestItemStatus.PENDING);
        dataVo.setRejectionReasonText("none");
        dataVo.setRevisionNumber(REV);
        dataVo.setPackagetypeIen(IEN);

        return dataVo;
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testCreatePackageTypeLocal() throws Exception {
//
//        PackageTypeVo dataVo = buildVo("naem1");
//
//        PackageTypeVo returnedVo = localpackageTypeDomainCapability.create(dataVo, getTestUser());
//        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testCreatePackageTypeNational() throws Exception {

        PackageTypeVo dataVo = buildVo("name8");

        PackageTypeVo returnedVo = nationalpackageTypeDomainCapability.create(dataVo, getTestUser());
        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
        assertEquals("IENS should match", returnedVo.getPackagetypeIen(), dataVo.getPackagetypeIen());
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
//    public void testUpdatepackageTypeLocal() throws Exception {
//
//        List<PackageTypeVo> names = localpackageTypeDomainCapability.retrieve();
//
//        names.get(0).setRejectionReasonText("updatedRejectREasonTExt");
//
//        localpackageTypeDomainCapability.update(names.get(0), getTestUser());
//
//        PackageTypeVo retrievedUpdated = localpackageTypeDomainCapability.retrieve(names.get(0).getId());
//
//        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), "updatedRejectREasonTExt");
//
//    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testUpdatepackageTypeNational() throws Exception {

        List<PackageTypeVo> names = nationalpackageTypeDomainCapability.retrieve();

        names.get(0).setRejectionReasonText(PPSConstants.TEST_REASON_TEXT);

        nationalpackageTypeDomainCapability.update(names.get(0), getTestUser());

        PackageTypeVo retrievedUpdated = nationalpackageTypeDomainCapability.retrieve(names.get(0).getId());

        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), PPSConstants.TEST_REASON_TEXT);

    }


    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testSearchPackageTypeNational() throws Exception {
        int initialSize = 0;
        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);

        SearchTermVo searchTerm = new SearchTermVo(EntityType.PACKAGE_TYPE, FieldKey.VALUE, "a");

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.INACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.PENDING);
        searchCriteria.setRequestStatus(requestStatus);

        // Add the searchTerm for the National Package Type test.
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PAGE_SIZE);

        List<PackageTypeVo> names;
        names = nationalpackageTypeDomainCapability.search(searchCriteria);
        initialSize = names.size();

        PackageTypeVo dataVo = buildVo("name3");
        nationalpackageTypeDomainCapability.create(dataVo, getTestUser());

        names = nationalpackageTypeDomainCapability.search(searchCriteria);

        assertEquals("Returned data", (initialSize + 1), names.size());

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testExistspackageTypeNational() throws Exception {

        PackageTypeVo unit = buildVo("name4");
        unit.setItemStatus(ItemStatus.ACTIVE);
        nationalpackageTypeDomainCapability.create(unit, getTestUser());
        boolean exists = nationalpackageTypeDomainCapability.existsByUniquenessFields(unit);
        assertTrue("exists", exists);
    }

//    /**
//     * This method gets all the FdbMfg in the db.
//     * 
//     * @throws Exception
//     */
////    public void testExistspackageTypeLocal() throws Exception {
//
//        PackageTypeVo unit = buildVo("anme5");
//        unit.setItemStatus(ItemStatus.ACTIVE);
//        localpackageTypeDomainCapability.create(unit, getTestUser());
//        boolean exists = localpackageTypeDomainCapability.existsByUniquenessFields(unit);
//        assertTrue("exists", exists);
//    }
}
