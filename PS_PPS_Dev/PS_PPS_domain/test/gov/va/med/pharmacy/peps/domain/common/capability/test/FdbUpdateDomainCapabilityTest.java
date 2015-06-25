/**
 * Source file created in 2033 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Test;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.FdbUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchTermVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbAddDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbAutoAddDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbAutoUpdateDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.FdbUpdateDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.ManufacturerDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageTypeDomainCapability;


/**
 * 
 * FdbUpdateDomainCapabilityTest unit tests
 * 
 */
public class FdbUpdateDomainCapabilityTest extends DomainCapabilityTestCase {

    private static final Logger LOG = Logger.getLogger(FdbUpdateDomainCapabilityTest.class);
    private static final String NDC2 = "0536-3086-43";
    private static final String NDC1 = "14182-0141-04";
    private static final String ROUND = "Round";
    private static final String NAME = "name";
    private static final String ADD_DESC = "Additional_Description";
    private static final String S6767 = "6767";
    private static final String S9991 = "9991";
    private static final String S9992 = "9992";
    private static final String S9993 = "9993";
    private static final String S9996 = "9996";
    private static final String S9997 = "9997";
    
    private static final String S4343 = "4343";

    private FdbAddDomainCapability fdbAddDomainCapability;
    private FdbUpdateDomainCapability fdbUpdateDomainCapability;
    private FdbAutoUpdateDomainCapability fdbAutoUpdateDomainCapability;
    private FdbAutoAddDomainCapability fdbAutoAddDomainCapability;

    private ManufacturerDomainCapability manufacturerDomainCapability;
    private PackageTypeDomainCapability packageTypeDomainCapability;

    /**
     * Setup() method
     */
    @Override
    protected void setUp() {
        fdbAddDomainCapability = getNationalCapability(FdbAddDomainCapability.class);
        fdbUpdateDomainCapability = getNationalCapability(FdbUpdateDomainCapability.class);

        fdbAutoAddDomainCapability = getNationalCapability(FdbAutoAddDomainCapability.class);
        fdbAutoUpdateDomainCapability = getNationalCapability(FdbAutoUpdateDomainCapability.class);

        manufacturerDomainCapability = getNationalCapability(ManufacturerDomainCapability.class);
        packageTypeDomainCapability = getNationalCapability(PackageTypeDomainCapability.class);
    }

    /**
     * This method gets all the FdbMfg in the db.
     * @throws Exception Exception
     */
    @Test
    public void testSearchManufacturerNational() throws Exception {

        String pManufacturerName = "WILL STAN";

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);

        SearchTermVo searchTerm = new SearchTermVo(EntityType.MANUFACTURER, FieldKey.VALUE, pManufacturerName);

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        // add the APPROVED request item status to search field.
        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.APPROVED);
        searchCriteria.setRequestStatus(requestStatus);

        // add all the search terms to test the manufacturer
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PPSConstants.I10);
        List<ManufacturerVo> manufacturers = manufacturerDomainCapability.search(searchCriteria);

        assertTrue("The Returned data is ", manufacturers.size() > 0);

        for (ManufacturerVo vo : manufacturers) {
            String name = vo.getValue();

            if (name.equals(pManufacturerName)) {
                LOG.debug("-->found a match: " + name);
                LOG.debug("-->Found a match Manufacturer ID: " + vo.getId());
            }

            LOG.debug("----------------------------------------------------- ");
            LOG.debug("Manufacturer Name : " + vo.getValue());
            LOG.debug("request status : " + vo.getRequestItemStatus());
            LOG.debug("Item status : " + vo.getItemStatus());
        }

    }

    /**
     * test Create manufacturer
     *
     * @throws DuplicateItemException DuplicateItemException
     */
    @Test
    public void testCreateManufacturer() throws DuplicateItemException {
        int originalCount = 0;

        List<ManufacturerVo> manuList = manufacturerDomainCapability.retrieve();
        originalCount = manuList.size();

        // provide a random number to avoid creating same name

        ManufacturerVo vo = buildManufacturerVo();
        vo.setItemStatus(ItemStatus.ACTIVE);

        manufacturerDomainCapability.create(vo, getTestUser());
        manuList = manufacturerDomainCapability.retrieve();

        assertEquals("Collection did not return correct number!", originalCount + 1, manuList.size());
    }

    /**
     * test create package
     * @throws DuplicateItemException 
     */
    @Test
    public void testCreatePackageType() throws DuplicateItemException {

        List<PackageTypeVo> origPackageList1 = packageTypeDomainCapability.retrieve();
        PackageTypeVo vo = buildPackageTypeVo();
        packageTypeDomainCapability.create(vo, getTestUser());

        List<PackageTypeVo> packageList2 = packageTypeDomainCapability.retrieve();

        assertEquals("Collection did not return correct number", origPackageList1.size() + 1, packageList2.size());

    }

    /**
     * 
     * test find package type
     *
     */
    @Test
    public void testFindPackageType() {

        String packageTypeName = "BOTTLE AND DEVICE";

        SearchCriteriaVo searchCriteria = new SearchCriteriaVo(SearchDomain.SIMPLE, Environment.LOCAL);

        SearchTermVo searchTerm = new SearchTermVo(EntityType.PACKAGE_TYPE, FieldKey.VALUE, packageTypeName);

        List<ItemStatus> itemStatus = new ArrayList<ItemStatus>();
        itemStatus.add(ItemStatus.ACTIVE);
        searchCriteria.setItemStatus(itemStatus);

        List<RequestItemStatus> requestStatus = new ArrayList<RequestItemStatus>();
        requestStatus.add(RequestItemStatus.APPROVED);
        searchCriteria.setRequestStatus(requestStatus);

        //FdbUpdateDomainCapability. testFindPackageType set search terms
        List<SearchTermVo> searchTerms = new ArrayList<SearchTermVo>();
        searchTerms.add(searchTerm);

        searchCriteria.setSearchTerms(searchTerms);

        searchCriteria.setSortedFieldKey(FieldKey.VALUE);
        searchCriteria.setSortOrder(SortOrder.ASCENDING);
        searchCriteria.setStartRow(0);
        searchCriteria.setPageSize(PPSConstants.I10);

        List<PackageTypeVo> results = packageTypeDomainCapability.search(searchCriteria);

        for (PackageTypeVo vo : results) {
            String name = vo.getValue();

            LOG.debug("--------------------------------------------------- ");

            if (name.equals(packageTypeName)) {
                LOG.debug("-->found match: " + name);
                LOG.debug("-->Found match packageType ID: " + vo.getId());
            }

            LOG.debug("packageType ID: " + vo.getId());
            LOG.debug("PackageType Name: " + vo.getValue());
            LOG.debug("request status: " + vo.getRequestItemStatus());
            LOG.debug("Item status: " + vo.getItemStatus());
        }

        assertTrue("Returned data is ", results.size() > 0);

    }

    /**
     * This method buidlsVO
     * 
     * @return PackageTypeVo
     */
    private PackageTypeVo buildPackageTypeVo() {
        PackageTypeVo dataVo = new PackageTypeVo();
        Random random = new Random((new Date()).getTime());
        dataVo.setValue(NAME + random.nextInt());
        dataVo.setPackagetypeIen(String.valueOf(random.nextInt()));
        dataVo.setInactivationDate(new Date());

        dataVo.setItemStatus(ItemStatus.ACTIVE);
        dataVo.setRequestItemStatus(RequestItemStatus.APPROVED);
        dataVo.setRejectionReasonText("no rejection reason.");
        dataVo.setRevisionNumber(PPSConstants.I3);

        return dataVo;
    }

    /**
     * Test Add to Pending list
     *
     * @throws Exception Exception
     */
    public void testAddDataToPendingList() throws Exception {

        fdbAddDomainCapability.deleteAll();

        List<FdbAddVo> fdbAddVoList = retrieveEPLPendingList();

        for (FdbAddVo fdbAddVo : fdbAddVoList) {
            FdbAddVo newFdbAddVos2 = fdbAddDomainCapability.create(fdbAddVo, getTestUser());
            assertNotNull("newFdbAddVos2 is  null", newFdbAddVos2);
        }
    }

    /**
     * test retrieve all Items in epl_fdb_add list
     *
     * @throws Exception exception
     */
    public void testRetrieveAddList() throws Exception {
        List<FdbAddVo> fdbAddVos = fdbAddDomainCapability.retrieve();

        // if none exist, add one and try it again.        
        if (fdbAddVos.size() == 0) {
            FdbAddVo fdbAddVo = new FdbAddVo();
            fdbAddVo.setAddDesc(ADD_DESC);
            fdbAddVo.setFdbCreationDate(new Date());
            fdbAddVo.setFdbProductName("ProductName is Product2");
            fdbAddVo.setGcnSeqno(new Long("23234"));
            fdbAddVo.setLabelName("LabelName");
            fdbAddVo.setManufacturer("manufacturere wer");
            fdbAddVo.setNdc(NDC1);
            fdbAddVo.setPackageSize("23.23");
            fdbAddVo.setPackageType("Package type");
            fdbAddVo.setTradeName("TradeName");
            fdbAddDomainCapability.create(fdbAddVo, getTestUser());
            fdbAddVos = fdbAddDomainCapability.retrieve();
        }

        assertTrue("pending list is empty ", fdbAddVos.size() > 0);
    }

    /**
    * delete all the pending list entries 
    */
    @Test
    public void testDeleteAllAddList() {
        fdbAddDomainCapability.deleteAll();

        List<FdbAddVo> fdbAddVos = fdbAddDomainCapability.retrieve();
        assertTrue("The pending list was not deleted!", fdbAddVos.size() == 0);
    }

    /**
     * deletes item in epl_fdb_add list by NDC number
     *
     */
    @Test
    public void testDeleteAddListByNdcNo() {
        String[] pNdcNumbers = { NDC2, "0382-3063-05", "0280-2000-20" };

        for (String ndcNo : pNdcNumbers) {
            fdbAddDomainCapability.deleteByNdcNumber(ndcNo);
        }

        assertTrue("ndc numbers are null", pNdcNumbers.length > 0);
    }

    /**
     *  testGetSequenceAndIndexes
     */
    public void testGetSequenceAndIndexes() {

        String[] selvalues = { S6767, S9992, S4343 };
        String[] allvalues = { "123", "3435", S6767, S9992, S4343 };

        //seqNos
        String[] gcnSeqNos = { "5656", "8989", "1099", "300", "3333" };

        List<Integer> indexes = getIndexes(allvalues, selvalues);

        for (Integer integer : indexes) {
            LOG.debug("index: " + integer);
        }

        List<String> seqNos = getSequenceNumbers(gcnSeqNos, indexes);

        for (String seqNo : seqNos) {
            LOG.debug("seqNos: " + seqNo);
        }

        assertTrue("indexes is empty", indexes.size() > 0);

    }

    /**
     * getIndexes
     *
     * @param allItems All Itmes
     * @param selItems selected items
     * @return List of Integers
     */
    private List<Integer> getIndexes(String[] allItems, String[] selItems) {

        List<Integer> indexList = new ArrayList<Integer>();

        for (int f = 0; f < selItems.length; f++) {
            for (int i = 0; i < allItems.length; i++) {
                if (allItems[i].equals(selItems[f])) {
                    indexList.add(i);
                }
            }
        }

        return indexList;
    }

    /**
     * getSequenceNumbers
     * @param gcnSeqno gcnSeqno
     * @param indexes indexes
     * @return List of values
     */
    private List<String> getSequenceNumbers(String[] gcnSeqno, List<Integer> indexes) {
        int i = 0;
        List<String> seqList = new ArrayList<String>();

        for (int f = 0; f < indexes.size(); f++) {
            i = indexes.get(f);
            seqList.add(gcnSeqno[i]);
        }

        return seqList;
    }

    /**
     * retrieve All items in EPL_FDB_UPDATE table
     * @throws DuplicateItemException 
     */
    @Test
    public void testRetrieveAllUpdateList() throws DuplicateItemException {

        List<FdbUpdateVo> fdbUpdateList = fdbUpdateDomainCapability.retrieve();

        // if none exist, add one and try it again.        
        if (fdbUpdateList.size() == 0) {
            FdbUpdateVo fdbUpdateVo = new FdbUpdateVo();
            fdbUpdateVo.setEplId(PPSConstants.L600000);
            fdbUpdateVo.setVaProductName("VAProductName is VAProduct3");
            fdbUpdateVo.setFdbProductName("FDBProductname is testRetrieveAllUpdateList");
            fdbUpdateVo.setGcnSeqno(new Long("23243"));
            fdbUpdateVo.setMessage("Long Long Message");
            fdbUpdateVo.setNdc(NDC1);
            fdbUpdateVo.setNdcIdFk(Long.valueOf("423323"));
            fdbUpdateDomainCapability.create(fdbUpdateVo, getTestUser());
            fdbUpdateList = fdbUpdateDomainCapability.retrieve();
        }

        for (FdbUpdateVo fdbUpdateVo : fdbUpdateList) {

            LOG.debug("----------------------------------------------------");
            LOG.debug("getGcnSequenceNumber:  " + fdbUpdateVo.getGcnSequenceNumber());
            LOG.debug("getNdcIdFk:  " + fdbUpdateVo.getNdcIdFk());
            LOG.debug("getFdbProductName:  " + fdbUpdateVo.getFdbProductName());
        }

        assertTrue("fdbUpdateList is empty!", fdbUpdateList.size() > 0);

    }

    /**
     * test Delete NdcItem UpdateList By Id
     */
    @Test
    public void testDeleteItemUpdateListById() {

        fdbUpdateDomainCapability.deleteAll();

        FdbUpdateVo fdbUpdateVo1 = new FdbUpdateVo();
        fdbUpdateVo1.setNdc("1231-0000-12");
        fdbUpdateVo1.setVaProductName("RISPERIDONE 1MG TAB,ORAL DISINTEGRATING");
        fdbUpdateVo1.setFdbProductName("FDBRISPERIDONE 1MG TAB,ORAL DISINTEGRATING");
        fdbUpdateVo1.setGcnSeqno(new Long("518012"));
        fdbUpdateVo1.setMessage("This ndc has been added to the update queue at right now!");
        fdbUpdateVo1.setNdcIdFk(new Long(S9993));
        fdbUpdateVo1.setProductFk(new Long(S9997));
        fdbUpdateVo1.setCreatedDate(new Date());

        try {
            FdbUpdateVo fdbUpdateVo2 = fdbUpdateDomainCapability.create(fdbUpdateVo1, getTestUser());
            assertNotNull("did not create fdbUpdate record:", fdbUpdateVo2);

            fdbUpdateDomainCapability.deleteItemById(fdbUpdateVo2.getId().toString());

            List<FdbUpdateVo> fdbAddVos = fdbUpdateDomainCapability.retrieve();
            assertTrue("fdbUpdate record was not deleted!", fdbAddVos.size() == 0);

        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /**
     * delete all the EPL_FDB_UPDATE list entries 
     */
    @Test
    public void testDeleteAllUpdateList() {

        fdbUpdateDomainCapability.deleteAll();

        List<FdbUpdateVo> fdbAddVos = fdbUpdateDomainCapability.retrieve();
        assertTrue("pending list was not deleted!", fdbAddVos.size() == 0);
    }

    /**
     * test Add Item sTo Update List
     * @throws Exception Exception
     */
    @Test
    public void testAddItemsToUpdateList() throws Exception {

        List<FdbUpdateVo> fdbUpdateList = retrieveUpdateList();

        assertNotNull("fdbUpdateList is null.", fdbUpdateList);
    }

    /**
     * retrieve All items in EPL_FDB_AUTO_UPDATE table
     * @throws DuplicateItemException 
     */
    @Test
    public void testRetrieveAllAutoUpdateList() throws DuplicateItemException {

        List<FdbAutoUpdateVo> fdbAutoUpdateVoList = fdbAutoUpdateDomainCapability.retrieve();

        // if none exist, add one and try it again.        

        FdbAutoUpdateVo fdbAutoUpdateVo = new FdbAutoUpdateVo();
        fdbAutoUpdateVo.setEplId(new Long("1212"));
        fdbAutoUpdateVo.setVaProductName("ProductName is Product4");
        fdbAutoUpdateVo.setFdbProductName("VAProductName4 testRetrieveAllUpdateList");
        fdbAutoUpdateVo.setGcnSeqno(new Long(S9991));
        fdbAutoUpdateVo.setMessage("Very Long Message");
        fdbAutoUpdateVo.setNdc("14182-9991-04");
        fdbAutoUpdateVo.setNdcIdFk(Long.valueOf(S9991));
        fdbAutoUpdateDomainCapability.create(fdbAutoUpdateVo, getTestUser());

        FdbAutoUpdateVo fdbAutoUpdateVo2 = new FdbAutoUpdateVo();
        fdbAutoUpdateVo2.setEplId(new Long("1213"));
        fdbAutoUpdateVo2.setVaProductName("ProductName is Product5");
        fdbAutoUpdateVo2.setFdbProductName("VAProductName5 testRetrieveAllUpdateList");
        fdbAutoUpdateVo2.setGcnSeqno(new Long(S9992));
        fdbAutoUpdateVo2.setMessage("Long Message");
        fdbAutoUpdateVo2.setNdc("14182-9992-04");
        fdbAutoUpdateVo2.setNdcIdFk(Long.valueOf(S9992));
        fdbAutoUpdateDomainCapability.create(fdbAutoUpdateVo2, getTestUser());
        fdbAutoUpdateVoList = fdbAutoUpdateDomainCapability.retrieve();

        for (FdbAutoUpdateVo vo : fdbAutoUpdateVoList) {

            LOG.debug("---------------------------------------------------");
            LOG.debug("GetVaProductName: " + vo.getVaProductName());
            LOG.debug("getGcnSeqNo: " + vo.getGcnSeqno());
            LOG.debug("getNdc: " + vo.getNdc());
            LOG.debug("getItemType: " + vo.getItemType());
            LOG.debug("getFormatIndicator: " + vo.getFormatIndicator());
        }

        assertTrue("FfdbUpdateList is empty ", fdbAutoUpdateVoList.size() > 0);
    }

    /**
     * test Add Items To Auto UpdateList
     * @throws Exception Exception
     */
    @Test
    public void testAddItemsToAutoUpdateList() throws Exception {

        List<FdbAutoUpdateVo> fdbAutoUpdateList = retrieveAutoUpdateList();

        assertTrue("newFdbAddVos2 is empty ", fdbAutoUpdateList.size() > 0);
    }

    /**
     * test Delete All AutoAddList
     */
    @Test
    public void testDeleteAllAutoUpdateList() {

        fdbAutoUpdateDomainCapability.deleteAll();

        List<FdbAutoUpdateVo> fdbAutoUpdateVos = fdbAutoUpdateDomainCapability.retrieve();
        assertTrue("fdbAutoUpdateVos list was not deleted!", fdbAutoUpdateVos.size() == 0);
    }

    /**
     * retrieve All items in EPL_FDB_AUTO_ADD table
     */
    @Test
    public void testRetrieveAllAutoAddList() {

        List<FdbAutoAddVo> fdbAutoAddVoList = fdbAutoAddDomainCapability.retrieve();

        // if none exist, add one and try it again.        
        if (fdbAutoAddVoList.size() == 0) {
            FdbAutoAddVo autoAddVo = new FdbAutoAddVo();

            autoAddVo.setVaProductName("ProductName is Product3");
            autoAddVo.setFdbProductName("VAProductName testRetrieveAllUpdateList");
            autoAddVo.setGcnSeqno(new Long("12323"));
            autoAddVo.setPackageSize(new Double("234.234"));
            autoAddVo.setPackageType("PackageType");
            autoAddVo.setNdc(NDC1);
            autoAddVo.setNdcIdFk(Long.valueOf("23323"));
            fdbAutoAddDomainCapability.create(autoAddVo, getTestUser());
            fdbAutoAddVoList = fdbAutoAddDomainCapability.retrieve();
        }

        for (FdbAutoAddVo vo : fdbAutoAddVoList) {

            LOG.debug("-----------------------------------------------------");

            LOG.debug("GNC SEQ NO: " + vo.getGcnSeqno());
            LOG.debug("NDC: " + vo.getNdc());
            LOG.debug("getFdbProductName: " + vo.getFdbProductName());
            LOG.debug("getVaProductName: " + vo.getVaProductName());
            LOG.debug("Created Date: " + vo.getCreatedDate());
        }

        assertTrue("fdbUpdateList is emtpy", fdbAutoAddVoList.size() > 0);
    }

    /**
     * test Add Items To Auto Add List
     * @throws Exception Exception
     */
    @Test
    public void testAddItemsToAutoAddList() throws Exception {

        List<FdbAutoAddVo> fdbAutoUpdateList = retrieveAutoAddList();

        assertTrue("newFdbAddVos2 is empty", fdbAutoUpdateList.size() > 0);
    }

    /**
     * test Delete All AutoAddList
     */
    @Test
    public void testDeleteAllAutoAddList() {

        fdbAutoAddDomainCapability.deleteAll();

        List<FdbAutoAddVo> fdbAutoAddVos = fdbAutoAddDomainCapability.retrieve();
        assertTrue("fdbAutoAdd list was not deleted!", fdbAutoAddVos.size() == 0);
    }

    /**
     * retrieves list of FdbUpdateVo with mock data
     * and updates the EPL_FDB_AUTO_ADD table with this sample data
     * @return list of FdbUpdateVo
     */
    private List<FdbAutoAddVo> retrieveAutoAddList() {

        fdbAutoAddDomainCapability.deleteAll();

        List<FdbAutoAddVo> fdbAutoAddVoList = new ArrayList<FdbAutoAddVo>();

        FdbAutoAddVo fdbAutoAddVo1 = new FdbAutoAddVo();
        fdbAutoAddVo1.setNdc("49685-928-02");
        fdbAutoAddVo1.setPackageSize(new Double("45"));
        fdbAutoAddVo1.setPackageType(ROUND);
        fdbAutoAddVo1.setGcnSeqno(new Long("44891"));
        fdbAutoAddVo1.setVaProductName("ACETAMINOPHEN1");
        fdbAutoAddVo1.setFdbProductName("TYLONEL1");
        fdbAutoAddVo1.setAddDesc(ADD_DESC);
        fdbAutoAddVo1.setNdcIdFk(new Long("4968594802"));
        fdbAutoAddVo1.setCreatedDate(new Date());
        fdbAutoAddVo1.setLabelName("LABEL NAME1");
        fdbAutoAddVoList.add(fdbAutoAddVo1);

        FdbAutoAddVo fdbAutoAddVo2 = new FdbAutoAddVo();
        fdbAutoAddVo2.setNdc("00182-0141-03");
        fdbAutoAddVo2.setPackageSize(new Double("45.1"));
        fdbAutoAddVo2.setPackageType(ROUND);
        fdbAutoAddVo2.setGcnSeqno(new Long("44892"));
        fdbAutoAddVo2.setVaProductName("ACETAMINOPHEN2");
        fdbAutoAddVo2.setFdbProductName("TYLONEL2");
        fdbAutoAddVo2.setAddDesc(ADD_DESC);
        fdbAutoAddVo2.setNdcIdFk(new Long("00382034303"));
        fdbAutoAddVo2.setCreatedDate(new Date());
        fdbAutoAddVo2.setLabelName("LABEL NAME2");
        fdbAutoAddVoList.add(fdbAutoAddVo2);
        
        FdbAutoAddVo fdbAutoAddVo3 = new FdbAutoAddVo();
        fdbAutoAddVo3.setNdc("00182-0141-05");
        fdbAutoAddVo3.setPackageSize(new Double("45.2"));
        fdbAutoAddVo3.setPackageType(ROUND);
        fdbAutoAddVo3.setGcnSeqno(new Long("44893"));
        fdbAutoAddVo3.setVaProductName("ACETAMINOPHEN3");
        fdbAutoAddVo3.setFdbProductName("TYLONEL3");
        fdbAutoAddVo3.setAddDesc(ADD_DESC);
        fdbAutoAddVo3.setNdcIdFk(new Long("00182014102"));
        fdbAutoAddVo3.setCreatedDate(new Date());
        fdbAutoAddVo3.setLabelName("LABEL NAME3");
        fdbAutoAddVoList.add(fdbAutoAddVo3);

        FdbAutoAddVo fdbAutoAddVo4 = new FdbAutoAddVo();
        fdbAutoAddVo4.setNdc("00182-0141-04");
        fdbAutoAddVo4.setPackageSize(new Double("45.3"));
        fdbAutoAddVo4.setPackageType(ROUND);
        fdbAutoAddVo4.setGcnSeqno(new Long("44894"));
        fdbAutoAddVo4.setVaProductName("ACETAMINOPHEN4");
        fdbAutoAddVo4.setFdbProductName("TYLONEL4");
        fdbAutoAddVo4.setAddDesc(ADD_DESC);
        fdbAutoAddVo4.setNdcIdFk(new Long("00182014103"));
        fdbAutoAddVo4.setCreatedDate(new Date());
        fdbAutoAddVo4.setLabelName("LABEL NAME4");
        fdbAutoAddVoList.add(fdbAutoAddVo4);

        FdbAutoAddVo fdbAutoAddVo5 = new FdbAutoAddVo();
        fdbAutoAddVo5.setNdc("00006-0211-58");
        fdbAutoAddVo5.setPackageSize(new Double("45.5"));
        fdbAutoAddVo5.setPackageType(ROUND);
        fdbAutoAddVo5.setGcnSeqno(new Long("44895"));
        fdbAutoAddVo5.setVaProductName("ACETAMINOPHEN5");
        fdbAutoAddVo5.setFdbProductName("TYLONEL5");
        fdbAutoAddVo5.setAddDesc(ADD_DESC);
        fdbAutoAddVo5.setNdcIdFk(new Long(S9992));
        fdbAutoAddVo5.setCreatedDate(new Date());
        fdbAutoAddVo5.setLabelName("LABEL NAME5");
        fdbAutoAddVoList.add(fdbAutoAddVo5);
     
        return fdbAutoAddVoList;
    }

    /**
     * retrieves list of FdbUpdateVo with mock data
     * @return list of FdbUpdateVo
     * @throws DuplicateItemException 
     */
    private List<FdbAutoUpdateVo> retrieveAutoUpdateList() throws DuplicateItemException {

        fdbAutoUpdateDomainCapability.deleteAll();

        List<FdbAutoUpdateVo> fdbUpdateVoList = new ArrayList<FdbAutoUpdateVo>();

        FdbAutoUpdateVo fdbAutoUpdateVo1 = new FdbAutoUpdateVo();
        fdbAutoUpdateVo1.setEplId(PPSConstants.L20);
        fdbAutoUpdateVo1.setNdc("");
        fdbAutoUpdateVo1.setItemType(EntityType.PRODUCT.toString());
        fdbAutoUpdateVo1.setGcnSeqno(new Long("4490"));
        fdbAutoUpdateVo1.setMessage("This product has quietly been added to the update queue.");
        fdbAutoUpdateVo1.setVaProductName("VACETAMINOPHEN");
        fdbAutoUpdateVo1.setFdbProductName("FACETAMINOPHEN");
        fdbAutoUpdateVo1.setFormatIndicator("");
        fdbAutoUpdateVo1.setNdcIdFk(null);
        fdbAutoUpdateVo1.setProductFk(new Long(S9993));
        fdbAutoUpdateVo1.setCreatedDate(new Date());
        FdbAutoUpdateVo vo1 = fdbAutoUpdateDomainCapability.create(fdbAutoUpdateVo1, getTestUser());
        fdbUpdateVoList.add(vo1);

        FdbAutoUpdateVo fdbAutoUpdateVo2 = new FdbAutoUpdateVo();
        fdbAutoUpdateVo2.setEplId(PPSConstants.L24);
        fdbAutoUpdateVo2.setNdc("01382-0343-03");
        fdbAutoUpdateVo2.setItemType(EntityType.NDC.toString());
        fdbAutoUpdateVo2.setGcnSeqno(new Long("4489"));
        fdbAutoUpdateVo2.setMessage("This National Drug Code has been added to the update queue");
        fdbAutoUpdateVo2.setVaProductName("");
        fdbAutoUpdateVo2.setFdbProductName("");
        fdbAutoUpdateVo2.setFormatIndicator("04-0-2 NDC");
        fdbAutoUpdateVo2.setNdcIdFk(new Long(S9993));
        fdbAutoUpdateVo2.setProductFk(null);
        fdbAutoUpdateVo2.setCreatedDate(new Date());
        FdbAutoUpdateVo vo2 = fdbAutoUpdateDomainCapability.create(fdbAutoUpdateVo2, getTestUser());
        fdbUpdateVoList.add(vo2);
        
        FdbAutoUpdateVo fdbAutoUpdateVo3 = new FdbAutoUpdateVo();
        fdbAutoUpdateVo3.setEplId(PPSConstants.L30);
        fdbAutoUpdateVo3.setNdc("");
        fdbAutoUpdateVo3.setItemType(EntityType.PRODUCT.toString());
        fdbAutoUpdateVo3.setGcnSeqno(new Long("51801"));
        fdbAutoUpdateVo3.setMessage("This product has now been added to the update queue");
        fdbAutoUpdateVo3.setVaProductName("VRISPERIDONE 2MG TAB,ORAL DISINTEGRATING");
        fdbAutoUpdateVo3.setFdbProductName("FRISPERIDONE 2MG TAB,ORAL DISINTEGRATING");
        fdbAutoUpdateVo3.setFormatIndicator("");
        fdbAutoUpdateVo3.setNdcIdFk(null);
        fdbAutoUpdateVo3.setProductFk(new Long(S9996));
        fdbAutoUpdateVo3.setCreatedDate(new Date());
        FdbAutoUpdateVo vo3 = fdbAutoUpdateDomainCapability.create(fdbAutoUpdateVo3, getTestUser());
        fdbUpdateVoList.add(vo3);

        FdbAutoUpdateVo fdbAutoUpdateVo4 = new FdbAutoUpdateVo();
        fdbAutoUpdateVo4.setEplId(PPSConstants.L35);
        fdbAutoUpdateVo4.setNdc("62794-0346-03");
        fdbAutoUpdateVo4.setItemType(EntityType.NDC.toString());
        fdbAutoUpdateVo4.setGcnSeqno(new Long("93562"));
        fdbAutoUpdateVo4.setMessage("This NDC has now been added to the update queue because it needed it");
        fdbAutoUpdateVo4.setVaProductName("");
        fdbAutoUpdateVo4.setFdbProductName("");
        fdbAutoUpdateVo4.setFormatIndicator("04-4-2NDC");
        fdbAutoUpdateVo4.setNdcIdFk(new Long(S9992));
        fdbAutoUpdateVo4.setProductFk(null);
        fdbAutoUpdateVo4.setCreatedDate(new Date());
        FdbAutoUpdateVo vo4 = fdbAutoUpdateDomainCapability.create(fdbAutoUpdateVo4, getTestUser());
        fdbUpdateVoList.add(vo4);
             
        FdbAutoUpdateVo fdbAutoUpdateVo5 = new FdbAutoUpdateVo();
        fdbAutoUpdateVo5.setEplId(PPSConstants.L45);
        fdbAutoUpdateVo5.setNdc("");
        fdbAutoUpdateVo5.setItemType(EntityType.PRODUCT.toString());
        fdbAutoUpdateVo5.setGcnSeqno(new Long("534943"));
        fdbAutoUpdateVo5.setMessage("This product has been added to the update queue.");
        fdbAutoUpdateVo5.setVaProductName("VATOMOXETINE 60MG CAP");
        fdbAutoUpdateVo5.setFdbProductName("FRATOMOXETINE 60MG CAP");
        fdbAutoUpdateVo5.setFormatIndicator("");
        fdbAutoUpdateVo5.setNdcIdFk(null);
        fdbAutoUpdateVo5.setProductFk(new Long(S9997));
        fdbAutoUpdateVo5.setCreatedDate(new Date());
        FdbAutoUpdateVo vo5 = fdbAutoUpdateDomainCapability.create(fdbAutoUpdateVo5, getTestUser());
        fdbUpdateVoList.add(vo5);

        return fdbUpdateVoList;
    }

    /**
     * retrieves list of FdbUpdateVo with mock data
     * and updates the epl_fdb_Update table
     * @return list of FdbUpdateVo
     * @throws DuplicateItemException 
     */
    private List<FdbUpdateVo> retrieveUpdateList() throws DuplicateItemException {

        fdbUpdateDomainCapability.deleteAll();

        List<FdbUpdateVo> fdbUpdateVoList = new ArrayList<FdbUpdateVo>();

        
        FdbUpdateVo fdbUpdateVo1 = new FdbUpdateVo();
        fdbUpdateVo1.setNdc("");
        fdbUpdateVo1.setVaProductName("VRISPERIDONE 3MG TAB,ORAL DISINTEGRATING");
        fdbUpdateVo1.setFdbProductName("FRISPERIDONE 3MG TAB,ORAL DISINTEGRATING");
        fdbUpdateVo1.setGcnSeqno(new Long("51800"));
        fdbUpdateVo1.setMessage("This product has been added to the update queue!");
        fdbUpdateVo1.setNdcIdFk(null);
        fdbUpdateVo1.setProductFk(new Long(S9991));
        fdbUpdateVo1.setCreatedDate(new Date());
        FdbUpdateVo vo1 = fdbUpdateDomainCapability.create(fdbUpdateVo1, getTestUser());
        fdbUpdateVoList.add(vo1);
        
        FdbUpdateVo fdbUpdateVo2 = new FdbUpdateVo();
        fdbUpdateVo2.setNdc("00382-0343-03");
        fdbUpdateVo2.setVaProductName("");
        fdbUpdateVo2.setFdbProductName("");
        fdbUpdateVo2.setGcnSeqno(new Long("73852"));
        fdbUpdateVo2.setMessage("This NDC has been added to the update queue!");
        fdbUpdateVo2.setNdcIdFk(new Long(S9991));
        fdbUpdateVo2.setProductFk(new Long("99910"));
        fdbUpdateVo2.setCreatedDate(new Date());
        FdbUpdateVo vo2 = fdbUpdateDomainCapability.create(fdbUpdateVo2, getTestUser());
        fdbUpdateVoList.add(vo2);
        
        FdbUpdateVo fdbUpdateVo3 = new FdbUpdateVo();
        fdbUpdateVo3.setNdc("");
        fdbUpdateVo3.setVaProductName("VOXYMORPHONE HCL 10MG TAB");
        fdbUpdateVo3.setFdbProductName("FOXYMORPHONE HCL 10MG TAB");
        fdbUpdateVo3.setGcnSeqno(new Long("61087"));
        fdbUpdateVo3.setMessage("This product has been added to the update queu");
        fdbUpdateVo3.setNdcIdFk(null);
        fdbUpdateVo3.setProductFk(new Long("9999"));
        fdbUpdateVo3.setCreatedDate(new Date());
        FdbUpdateVo vo3 = fdbUpdateDomainCapability.create(fdbUpdateVo3, getTestUser());
        fdbUpdateVoList.add(vo3);
        
        FdbUpdateVo fdbUpdateVo4 = new FdbUpdateVo();
        fdbUpdateVo4.setNdc("00093715598");
        fdbUpdateVo4.setVaProductName("");
        fdbUpdateVo4.setFdbProductName("");
        fdbUpdateVo4.setGcnSeqno(new Long("97322"));
        fdbUpdateVo4.setMessage("this is a message about NDC!");
        fdbUpdateVo4.setNdcIdFk(new Long(S9996));
        fdbUpdateVo4.setProductFk(new Long(S9996));
        fdbUpdateVo4.setCreatedDate(new Date());
        FdbUpdateVo vo4 = fdbUpdateDomainCapability.create(fdbUpdateVo4, getTestUser());
        fdbUpdateVoList.add(vo4);

        FdbUpdateVo fdbUpdateVo5 = new FdbUpdateVo();
        fdbUpdateVo5.setNdc("");
        fdbUpdateVo5.setVaProductName("VATOMOXETINE 80MG CAP");
        fdbUpdateVo5.setFdbProductName("FATOMOXETINE 80MG CAP");
        fdbUpdateVo5.setGcnSeqno(new Long("53493"));
        fdbUpdateVo5.setMessage("This product has been added to the update queue today.");
        fdbUpdateVo5.setNdcIdFk(null);
        fdbUpdateVo5.setProductFk(new Long(S9997));
        fdbUpdateVo5.setCreatedDate(new Date());
        FdbUpdateVo vo5 = fdbUpdateDomainCapability.create(fdbUpdateVo5, getTestUser());
        fdbUpdateVoList.add(vo5);
        
        return fdbUpdateVoList;
    }

    /**
     * This method buidlsVO
     * @return ManufacturerVo
     */
    private ManufacturerVo buildManufacturerVo() {
        ManufacturerVo dataVo = new ManufacturerVo();
        Random random = new Random((new Date()).getTime());
        dataVo.setValue(NAME + random.nextInt());
        dataVo.setManufacturerIen(String.valueOf(random.nextInt()));
        dataVo.setInactivationDate(new Date());
        dataVo.setItemStatus(ItemStatus.INACTIVE);
        dataVo.setRequestItemStatus(RequestItemStatus.PENDING);
        dataVo.setAddress("newAddress");
        dataVo.setPhone("232232");
        dataVo.setRejectionReasonText("none");

        return dataVo;
    }

    /**
     * Creates the sample data for the EPL Pending list
     * @return returns populated FdbAddVo 
     */
    private List<FdbAddVo> retrieveEPLPendingList() {

        List<FdbAddVo> fdbAddVoList = new ArrayList<FdbAddVo>();

        FdbAddVo fdbAddVo1 = new FdbAddVo();
        fdbAddVo1.setAddDesc("Additional Description - 1");
        fdbAddVo1.setFdbCreationDate(new Date());
        fdbAddVo1.setNdc("00378-4030-05");
        fdbAddVo1.setPackageSize("4");
        fdbAddVo1.setPackageType("BOTTLE1");
        fdbAddVo1.setManufacturer("MAN1");
        fdbAddVo1.setLabelName("LABEL TEMAZEPAM 35MG");
        fdbAddVo1.setTradeName("TradeName1");
        fdbAddVo1.setFdbProductName("TEMAZEPAM 35MG");
        fdbAddVo1.setGcnSeqno(new Long("234234"));
        addClosestMatchProductsToFdbAddVo(fdbAddVo1);
        fdbAddVoList.add(fdbAddVo1);

        FdbAddVo fdbAddVo2 = new FdbAddVo();
        fdbAddVo2.setAddDesc("Additional Description-2");
        fdbAddVo2.setFdbCreationDate(new Date());
        fdbAddVo2.setNdc("0536-3086-41");
        fdbAddVo2.setPackageSize("2.0");
        fdbAddVo2.setPackageType("BOTTLE2");
        fdbAddVo2.setManufacturer("MAN2");
        fdbAddVo2.setLabelName("LABEL PSEUDOEPHEDRINE HCL 60MG TAB");
        fdbAddVo2.setTradeName("TradeName2");
        fdbAddVo2.setFdbProductName("FDB PRSEUDOEPHEDRINE HCL 60MG TAB");
        fdbAddVo2.setGcnSeqno(new Long("5090"));
        addClosestMatchProductsToFdbAddVo(fdbAddVo2);
        fdbAddVoList.add(fdbAddVo2);
       
        FdbAddVo fdbAddVo3 = new FdbAddVo();
        fdbAddVo3.setAddDesc("Additional Description-3");
        fdbAddVo3.setFdbCreationDate(new Date());
        fdbAddVo3.setNdc("00006-0237-58");
        fdbAddVo3.setPackageSize("3.0");
        fdbAddVo3.setPackageType("BOTTLE3");
        fdbAddVo3.setManufacturer("MAN3");
        fdbAddVo3.setLabelName("LABEL PRINVIL");
        fdbAddVo3.setTradeName("TradeName3");
        fdbAddVo3.setFdbProductName("PRINIVIL-FDB");
        fdbAddVo3.setGcnSeqno(new Long("79913"));
        addClosestMatchProductsToFdbAddVo(fdbAddVo3);
        fdbAddVoList.add(fdbAddVo3);

        FdbAddVo fdbAddVo4 = new FdbAddVo();
        fdbAddVo4.setAddDesc("Additional Description-4");
        fdbAddVo4.setFdbCreationDate(new Date());
        fdbAddVo4.setNdc("0054-0243-24");
        fdbAddVo4.setPackageSize("4.0");
        fdbAddVo4.setPackageType("BOTTLE4");
        fdbAddVo4.setManufacturer("MAN4");
        fdbAddVo4.setLabelName("LABEL AMOXICILLIN TRIHYDRATE 500MG");
        fdbAddVo4.setTradeName("TradeName4");
        fdbAddVo4.setFdbProductName("AMOXICILLIN TRIHYDRATE 500MG-FDB");
        fdbAddVo4.setGcnSeqno(new Long("8992"));
        addClosestMatchProductsToFdbAddVo(fdbAddVo4);
        fdbAddVoList.add(fdbAddVo4);

        FdbAddVo fdbAddVo5 = new FdbAddVo();
        fdbAddVo5.setAddDesc("Additional Description-5");
        fdbAddVo5.setFdbCreationDate(new Date());
        fdbAddVo5.setNdc("38779-1216-8");
        fdbAddVo5.setPackageSize("5.0");
        fdbAddVo5.setPackageType("BOTTLE5");
        fdbAddVo5.setManufacturer("MAN5");
        fdbAddVo5.setLabelName("LABEL CEPHALEXIN 250MG/5ML SUSP");
        fdbAddVo5.setTradeName("TradeName5");
        fdbAddVo5.setFdbProductName("CEPHALEXIN 250MG/5ML SUSP-FDB");
        fdbAddVo5.setGcnSeqno(new Long("9046"));
        addClosestMatchProductsToFdbAddVo(fdbAddVo5);
        fdbAddVoList.add(fdbAddVo5);

        return fdbAddVoList;
    }

    /**
     *  Add Closes match products to FDbAddVo
     * @param pFdbAddVo FdbAddVo
     */
    private void addClosestMatchProductsToFdbAddVo(FdbAddVo pFdbAddVo) {

        // FdbAddVo will contain the prouduct Ids in the table,
        // which will be generated by the ranking Algorithm and stored in the 
        // EPL_FDB_ADD table. 
        // when we retieve the fdbAddVos, that will contain the ranking product ids.
        // Then we will make a service call to retrieve the products by these ranking 
        // product Ids.

        //simulates retrieving product by ProductEplId
        ProductVo productVo1 = new ProductVo();
        productVo1.setVaProductName("LISINOPRIL 10MG TAB");
        productVo1.setId(String.valueOf(new Long(S9993)));
        pFdbAddVo.addProductVo(productVo1);

        ProductVo productVo2 = new ProductVo();
        productVo2.setVaProductName("LISINOPRIL 40MG TAB");
        productVo2.setId(String.valueOf(new Long("9994")));
        pFdbAddVo.addProductVo(productVo2);

        ProductVo productVo3 = new ProductVo();
        productVo3.setVaProductName("PSEUDOEPHEDRINE HCL 60MG TAB");
        productVo3.setId(String.valueOf(new Long("9995")));
        pFdbAddVo.addProductVo(productVo3);

        ProductVo productVo4 = new ProductVo();
        productVo4.setVaProductName("SIMVASTATIN 40MG TAB");
        productVo4.setId(String.valueOf(new Long(S9996)));
        pFdbAddVo.addProductVo(productVo4);

        ProductVo productVo5 = new ProductVo();
        productVo5.setVaProductName("ATOMOXETINE 60MG CAP");
        productVo5.setId(String.valueOf(new Long(S9997)));
        pFdbAddVo.addProductVo(productVo5);

    }

}
