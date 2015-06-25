/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.domain.common.capability.ManufacturerDomainCapability;


/**
 * ManufacturerDomainCapabilityTest.
 */
public class ManufacturerDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final String IEN = "344534";
    private static final Long REV = 3L;
    private static final int PAGE_SIZE = 10;
    
    private ManufacturerDomainCapability nationalmanufacturerDomainCapability;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        nationalmanufacturerDomainCapability = getNationalCapability(ManufacturerDomainCapability.class);
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testFindAllNational() throws Exception {
        int originalCount = 0;
        List<ManufacturerVo> rCollection;

        rCollection = nationalmanufacturerDomainCapability.retrieve();
        originalCount = rCollection.size();

        ManufacturerVo dataVo = buildVo("name2");
        dataVo.setItemStatus(ItemStatus.ACTIVE);
        nationalmanufacturerDomainCapability.create(dataVo, getTestUser());
        rCollection = nationalmanufacturerDomainCapability.retrieve();

        assertEquals("Collection returned correct number", originalCount + 1, rCollection.size());
    }

    /**
     * This method buidlsVO
     * @param name String 
     * @return ManufacturerVo
     */
    private ManufacturerVo buildVo(String name) {
        ManufacturerVo dataVo = new ManufacturerVo();
        dataVo.setValue(name);
        dataVo.setInactivationDate(new Date());
        dataVo.setItemStatus(ItemStatus.INACTIVE);
        dataVo.setRequestItemStatus(RequestItemStatus.PENDING);
        dataVo.setRevisionNumber(REV);
        dataVo.setManufacturerIen(IEN);
        dataVo.setAddress("newAddress");
        dataVo.setPhone("212212");
        dataVo.setRejectionReasonText("none");

        return dataVo;
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testCreateManufacturerNational() throws Exception {

        ManufacturerVo dataVo = buildVo("name7");

        ManufacturerVo returnedVo = nationalmanufacturerDomainCapability.create(dataVo, getTestUser());
        assertNotNull("Returned NdcUnit with id", returnedVo.getId());
        assertEquals("IEN should be the same.", dataVo.getManufacturerIen(), returnedVo.getManufacturerIen());
    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testUpdatemanufacturerNational() throws Exception {

        String text = "updatedRejectREasonTExt2";
        List<ManufacturerVo> names = nationalmanufacturerDomainCapability.retrieve();

        names.get(0).setRejectionReasonText(text);

        nationalmanufacturerDomainCapability.update(names.get(0), getTestUser());

        ManufacturerVo retrievedUpdated = nationalmanufacturerDomainCapability.retrieve(names.get(0).getId());

        assertEquals("Should be equal", retrievedUpdated.getRejectionReasonText(), text);

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testSearchManufacturerNational() throws Exception {

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);

        SearchTermVo searchTerm = new SearchTermVo(EntityType.MANUFACTURER, FieldKey.VALUE, "name");

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.APPROVED);
        searchCriteria.setRequestStatus(requestStatus);

        // set the SearchTerms for testSearchManufacturerNational
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PAGE_SIZE);

        List<ManufacturerVo> names = nationalmanufacturerDomainCapability.search(searchCriteria);

        assertFalse("Returned data", names.isEmpty());

    }

    /**
     * This method gets all the FdbMfg in the db.
     * 
     * @throws Exception Exception
     */
    public void testExistsmanufacturerNational() throws Exception {

        ManufacturerVo unit = buildVo("name4");
        unit.setItemStatus(ItemStatus.ACTIVE);
        nationalmanufacturerDomainCapability.create(unit, getTestUser());
        boolean exists = nationalmanufacturerDomainCapability.existsByUniquenessFields(unit);
        assertTrue("exists", exists);
    }

}
