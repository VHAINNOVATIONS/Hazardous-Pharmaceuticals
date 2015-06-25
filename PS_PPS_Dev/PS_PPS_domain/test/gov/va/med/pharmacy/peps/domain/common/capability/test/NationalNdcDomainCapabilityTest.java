/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.DomainException;
import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.test.generator.ManufacturerGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.NdcGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.OrderUnitGenerator;
import gov.va.med.pharmacy.peps.common.utility.test.generator.PackageTypeGenerator;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.ColorVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchFieldVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SearchType;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.SubCategory;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.domain.common.capability.NdcDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ResetNationalDatabaseDomainCapability;




/**
 * NationalNdcDomainCapabilityTest.
 */
public class NationalNdcDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(NationalNdcDomainCapabilityTest.class);
    private static final String WHITE = "White";

    private NdcDomainCapability nationalndcDomainCapability;
    private ResetNationalDatabaseDomainCapability resetNationalDatabaseDomainCapability;
    private NdcVo testSampleVo;

    /**
     * Retrieve the capability being tested from the Spring application context.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() {
        LOG.debug("---------------" + getName() + "----------------");
        this.nationalndcDomainCapability = getNationalCapability(NdcDomainCapability.class);
        this.resetNationalDatabaseDomainCapability = getNationalCapability(ResetNationalDatabaseDomainCapability.class);

        NdcVo ndc = new NdcGenerator().getRandom();

        try {
            testSampleVo = nationalndcDomainCapability.create(ndc, getTestUser());
        } catch (DuplicateItemException e) {
            LOG.debug("Could not create test vo: " + e.getMessage());
        }

    }

    /**
     * testGetAll.
     * @throws Exception Exception.
     */
    public void testGetAll() throws Exception {

        List<Long> list = resetNationalDatabaseDomainCapability.getIds(EntityType.NDC, false);

        assertNotNull("This list should not be null at this time.", list);

        for (Long value : list) {
            NdcVo myVo = nationalndcDomainCapability.retrieve(value.toString());

            LOG.info("Show information for NDC: " + myVo.getId());

            if (myVo.getCategories() != null && myVo.getCategories().size() > 0) {
                for (Category cat : myVo.getCategories()) {
                    LOG.info("category  is " + cat);
                }
            }

            // save the sub-categories
            if (myVo.getSubCategories() != null && myVo.getSubCategories().size() > 0) {
                for (SubCategory subCategory : myVo.getSubCategories()) {
                    LOG.info("sub-category is " + subCategory);
                }
            }
        }

    }

    /**
     * testAddNdcVoNational.
     * @throws Exception Exception
     */
    public void testAddNdcVoNational() throws Exception {

        NdcVo ndc = new NdcGenerator().getRandom();

        NdcVo returnedVo = nationalndcDomainCapability.create(ndc, getTestUser());
        assertNotNull("Returned Ndc", returnedVo);

        LOG.debug(ndc.getId());

        DataFields<DataField> dfs = returnedVo.getVaDataFields();
        DataField<Boolean> chemoMed = dfs.getDataField(FieldKey.PATIENT_SPECIFIC_LABEL);

        assertNotNull("VA DF field protect from light not inserted", chemoMed);
    }

    /**
     * Checks if an exception is throw if data field id is not specified.
     * 
     * @throws Exception Exception
     */
    public void testAddNdcVoNationalForException() throws Exception {

        NdcVo ndc = new NdcGenerator().getRandom();

        DataField<Double> compounded = DataField.newInstance(FieldKey.NDC_PRICE_PER_DISPENSE_UNIT);

        // removed for testing purpose: compounded.setDataFieldId(new Long(69));
        // if the foreign key is not specified will get an exception.
        compounded.selectValue(new Double(PPSConstants.D1POINT1));
        ndc.getVaDataFields().setDataField(compounded);

        try {
            nationalndcDomainCapability.create(ndc, getTestUser());
            fail();
        } catch (DomainException ex) {

            // this test should fail because datafiled id is null
            assertNotNull("execption", ex);

        }

    }

    /**
     * testRevisionNumber.
     * @throws Exception Exception
     */
    public void testRevisionNumber() throws Exception {
        long revNumber = nationalndcDomainCapability.getRevisionNumber(testSampleVo.getId());
        assertNotNull("Id not returned", testSampleVo.getId());
        assertNotNull("Version number not returned", revNumber);
    }

    /**
     * testIen.
     * @throws Exception Exception
     */
    public void testIEN() throws Exception {
        NdcVo ndcVo = nationalndcDomainCapability.retrieve(testSampleVo.getId());
        assertNotNull("Id not returned.", testSampleVo.getId());
        assertEquals("IEN number should be the same.", testSampleVo.getNdcIen(), ndcVo.getNdcIen());
    }

    /**
     * ndc.setNdcDispUnitsPerOrdUnit(32); retrieve Ndc count
     * 
     * @throws Exception Exception
     */
    public void testRetrieveNdcCountNational() throws Exception {

        int ndcs = nationalndcDomainCapability.retrieveChildrenCount("9995");
        assertEquals("Number of Ndcs returned is  correct", 1, ndcs);
    }

    /**
     * retrieve Ndc count
     * 
     * @throws Exception Exception
     */
    public void testSearchSimpleNdc() throws Exception {

        testSampleVo.setItemStatus(ItemStatus.ACTIVE);
        testSampleVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        testSampleVo = nationalndcDomainCapability.update(testSampleVo, getTestUser());

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);
        searchCriteria.getCategories().clear();

        String ndcWithoutDashes = testSampleVo.getNdcPart1() + testSampleVo.getNdcPart2() + testSampleVo.getNdcPart3();
        LOG.debug("NDC is: " + ndcWithoutDashes);
        SearchTermVo searchTerm = new SearchTermVo(EntityType.NDC, FieldKey.NDC, ndcWithoutDashes);

        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);
        searchCriteria.setSearchTerms(searchTerms);

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        searchCriteria.setEntityType(EntityType.NDC);
        searchCriteria.setSortedFieldKey(FieldKey.NDC);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PPSConstants.I10);

        List<NdcVo> items = nationalndcDomainCapability.search(searchCriteria);

        assertFalse(" Number returned should not be empty!", items.isEmpty());

    }
    
    
    /**
     * retrieve Ndc count
     * 
     * @throws Exception Exception
     */
    public void testSearchSimpleUpcUpn() throws Exception {

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);
        searchCriteria.getCategories().clear();

        SearchTermVo searchTerm = new SearchTermVo(EntityType.NDC, FieldKey.UPC_UPN, "999");

        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);
        searchCriteria.setSearchTerms(searchTerms);

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        searchCriteria.setSortedFieldKey(FieldKey.UPC_UPN);
        searchCriteria.setEntityType(EntityType.NDC);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PPSConstants.I10);

        List<NdcVo> items = nationalndcDomainCapability.search(searchCriteria);

        assertFalse("Number returned should not be empty!", items.isEmpty());

    }

    /**
     * testSearchNdcByNumber
     * @throws Exception Exception
     */
    public void testSearchNdcByNumber() throws Exception {

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);
        searchCriteria.getCategories().clear();

        String ndcNumber = "62794-0146-01";

        String replacedValue = ndcNumber.replace("-", "");

        LOG.debug("NDC is " + replacedValue);

        SearchTermVo searchTerm = new SearchTermVo(EntityType.NDC, FieldKey.NDC, replacedValue);

        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);
        searchCriteria.setSearchTerms(searchTerms);

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        searchCriteria.setSortedFieldKey(FieldKey.NDC);
        searchCriteria.setEntityType(EntityType.NDC);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PPSConstants.I10);

        List<NdcVo> items = nationalndcDomainCapability.search(searchCriteria);

        assertFalse("Number returned should not be empty", items.isEmpty());

    }

    /**
     * search by criteria
     * 
     * @throws Exception Exception
     */
    public void testSearchAdvancedColor() throws Exception {
        List<NdcVo> ndcs;
        int initialCount;

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);
        searchCriteria.setSearchDomain(SearchDomain.ADVANCED);
        searchCriteria.setLocalUse(LocalUseSearchType.LOCAL_USE);
        searchCriteria.setAdvancedAndSearch(true);
        SearchTermVo searchTerm = new SearchTermVo();
        searchTerm.setSearchField(new SearchFieldVo(FieldKey.COLOR, EntityType.NDC));
        searchTerm.setValue("W");

        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);
        searchCriteria.setSearchTerms(searchTerms);
        ndcs = nationalndcDomainCapability.search(searchCriteria);
        initialCount = ndcs.size();

        NdcVo ndc = new NdcGenerator().getRandom();
        ColorVo color = new ColorVo();
        color.setValue(WHITE);
        ndc.setColor(color);
        ndc.setItemStatus(ItemStatus.ACTIVE);
        ndc.setRequestItemStatus(RequestItemStatus.APPROVED);

        // create the domain capability to testSearchAdvancedColor.
        nationalndcDomainCapability.create(ndc, getTestUser());

        ndcs = nationalndcDomainCapability.search(searchCriteria);

        LOG.debug(ndcs.size());
        LOG.debug(initialCount);

        // since the random generator inserts any color with any status
        // it is hard to compare numbers
        assertTrue("New NDC not found!", ndcs.size() > initialCount);
    }

    /**
     * search by criteria
     * 
     * @throws Exception Exception
     */
    public void testSearchAdvancedLocalUse() throws Exception {
        List<NdcVo> ndcs;
        int initialCount;

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.NATIONAL);
        searchCriteria.setSearchDomain(SearchDomain.ADVANCED);
        searchCriteria.setLocalUse(LocalUseSearchType.LOCAL_USE);
        searchCriteria.setAdvancedAndSearch(true);
        SearchTermVo searchTerm = new SearchTermVo();
        searchTerm.setSearchField(new SearchFieldVo(FieldKey.COLOR, EntityType.NDC));
        searchTerm.setSearchType(SearchType.CONTAINS);
        searchTerm.setValue("i");

        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        ndcs = nationalndcDomainCapability.search(searchCriteria);
        initialCount = ndcs.size();

        NdcVo ndc = new NdcGenerator().getRandom();
        ColorVo color = new ColorVo();
        color.setValue(WHITE);
        ndc.setColor(color);
        ndc.setItemStatus(ItemStatus.ACTIVE);
        ndc.setRequestItemStatus(RequestItemStatus.APPROVED);

        nationalndcDomainCapability.create(ndc, getTestUser());

        ndcs = nationalndcDomainCapability.search(searchCriteria);

        LOG.debug(ndcs.size());
        LOG.debug(initialCount);

        // since the random generator inserts any color with any status
        // it is hard to compare numbers
        assertTrue("New NDC not found", ndcs.size() > initialCount);
    }

    /**
     * testAddMinimalNdcVoNational
     * 
     * @throws Exception Exception
     */
    public void testAddMinimalNdcVoNational() throws Exception {

        // create a new NDC item
        NdcVo ndc = new NdcGenerator().generateMinimalNdc();

        // set the values we're testing for
        ndc.setPackageSize(new Double(PPSConstants.D1POINT1));
        ndc.setPackageType(null);
        ndc.setTradeName(null);
        ndc.setUpcUpn("iouiouoiu");

        // save the ndc
        NdcVo returnedVo = nationalndcDomainCapability.create(ndc, getTestUser());

        assertNotNull("Returned Item Result not null", returnedVo);
        assertNotNull("Returned Package size should be null", returnedVo.getPackageSize());
        assertNull("Returned Package type should be null", returnedVo.getPackageType());
        assertNotNull("Returned Manufacturer should be not null", returnedVo.getManufacturer());
        assertNull("Returned trade name should be null", returnedVo.getTradeName());
    }

    /**
     * testRetrieveByIdNational
     * @throws Exception Exception
     */
    public void testRetrieveByIdNational() throws Exception {
        NdcVo testVo = nationalndcDomainCapability.retrieve(testSampleVo.getId());
        assertNotNull("Returned Item Result not returned", testVo);
    }

    /**
     * testUpdateNDCNational
     * @throws Exception Exception
     */
    public void testUpdateNDCNational() throws Exception {

        // first retrieve
        NdcVo testVo = nationalndcDomainCapability.retrieve(testSampleVo.getId());
        testVo.setTradeName("Lorataditt");

        // update package size
        testVo.setPackageSize(new Double(PPSConstants.I25));

        // update package type
        PackageTypeVo pType = new PackageTypeGenerator().getRandom();
        testVo.setPackageType(pType);

        // update manufacturer
        ManufacturerVo manu = new ManufacturerGenerator().getRandom();
        testVo.setManufacturer(manu);

        OrderUnitVo orderUnitVo = new OrderUnitGenerator().getRandom();
        testVo.setOrderUnit(orderUnitVo);

        nationalndcDomainCapability.update(testVo, getTestUser());

        NdcVo retrievedVo = nationalndcDomainCapability.retrieve(testSampleVo.getId());

        assertEquals("IEN should be equals.", testVo.getNdcIen(), retrievedVo.getNdcIen());
        assertEquals("Manufacturer Should be equal", manu.getId(), retrievedVo.getManufacturer().getId());
        assertEquals("Package TYpe Should be equal", pType.getId(), retrievedVo.getPackageType().getId());

    }

}
