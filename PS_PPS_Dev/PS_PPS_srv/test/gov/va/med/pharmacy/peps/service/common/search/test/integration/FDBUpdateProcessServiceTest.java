/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.search.test.integration;





import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionResultVo;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.FdbUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.FdbSearchValidator;
import gov.va.med.pharmacy.peps.domain.common.capability.test.DomainCapabilityTestCase;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.session.DrugReferenceService;
import gov.va.med.pharmacy.peps.service.common.session.FDBUpdateProcessService;
import gov.va.med.pharmacy.peps.service.common.utility.test.SpringTestConfigUtility;


/**
 * Unit tests for FDBUpdateProcessServiceTest
 */
public class FDBUpdateProcessServiceTest extends DomainCapabilityTestCase {

    private static final String PRODUCT_KEY = "productAdd";
    private static final Logger LOG = Logger.getLogger(FDBUpdateProcessServiceTest.class);
    private static final String NDC_1 = "0781-6153-94";
    private static final String NDC_2 = "0182-1061-05";
    private static final String NDC_3 = "70074-51113";
    private static final String NDC_4 = "0280-2000-20";
    private static final String NDC_5 = "0536-3086-41";
    private static final String NDC_6 = "38779-1216-8";
    private static final String NDC_7 = "00378-4010-05";
    private static final String PRODUCTID = "9995";

    
    
    private FDBUpdateProcessService fDBUpdateProcessService;
    private DrugReferenceService drugReferenceService;
    private FdbSearchValidator validator;
   


    /**
     * setup()
     */
    @Override
    @Before
    public void setUp() {
        
        this.validator = new FdbSearchValidator();
        fDBUpdateProcessService = getNationalCapability(FDBUpdateProcessService.class);
        drugReferenceService = SpringTestConfigUtility.getNationalSpringBean(DrugReferenceService.class);
        
    }

    
    /**
     * test Find manufacturer by name
     */
    @Test
    public void testFindManufacturersByName() {
        
        String searchString = "ORTHO PHARM";

        ManufacturerVo vo = fDBUpdateProcessService.findManufacturerByName(searchString);
        assertNotNull("no results found!", vo);
            
        LOG.debug("----------------------------------------------------- "); 
        LOG.debug("Manufacturer ID: "  + vo.getId());
        LOG.debug("Manufacturer Name: "  + vo.getValue());
        LOG.debug("request status: "  + vo.getRequestItemStatus());
        LOG.debug("Item status: "  + vo.getItemStatus());
    }

    /**
     * test add NDC to Product
     * @throws ValidationException 
     */
    @Test
    public void testAddNdcToProduct() throws ValidationException {

        String[] pNdcNumbers = { NDC_1 }; // NDC must not exist in DB
        String productId = PRODUCTID;

        List<NdcVo> ndcs = fDBUpdateProcessService.searchNdcByNumber(pNdcNumbers[0]);
        
        if (ndcs.size() == 0) {
            fDBUpdateProcessService.addProductToNdcs(getTestUser(), pNdcNumbers, productId);
            List<NdcVo> results = fDBUpdateProcessService.searchNdcByNumber(pNdcNumbers[0]);
            assertTrue("no results.", results.size() > 0);
        }

    }

    /**
     * 
     * testDeleteNdcFromFdbAddTable
     *
     */
    @Test
    public void testDeleteNdcsFromFdbAddTable() {
        
        List<FdbAddVo> fdbAddList = fDBUpdateProcessService.retrieveEPLAddList();
        
        assertTrue("no ndc records found. ", fdbAddList.size() > 0);
        
        int fdbAddSizeStart = fdbAddList.size();
        
        Map<String, ProductVo> flowScope = createNdcs(fdbAddList);
        
        List<String> ndcList = new ArrayList<String>();
        ProductVo productVo = flowScope.get(PRODUCT_KEY);

        if (productVo != null && productVo.getId() != null) {
            List<NdcVo> ndcs = productVo.getNdcs();
            
            for (NdcVo ndcVo : ndcs) {
                if (StringUtils.isBlank(ndcVo.getNdc())) {
                    continue;
                }
                
                ndcList.add(ndcVo.getNdc());
            }
            
            String[] ndcArray = ndcList.toArray(new String[ndcList.size()]);
            fDBUpdateProcessService.deleteItemsFromAddList(ndcArray);
            
            List<FdbAddVo> fdbAddListAfter = fDBUpdateProcessService.retrieveEPLAddList();
            int fdbAddSizeAfter = fdbAddListAfter.size();
            
            
            assertTrue("did not remove item from fdbAdd table", fdbAddSizeStart > fdbAddSizeAfter);
            
        }
    }
    
    /**
     * createNdcs from epl_fdb_add table.
     * @param fdbAddList 
     * @return Map
     */
    private Map<String, ProductVo> createNdcs(List<FdbAddVo> fdbAddList) {
        
        List<NdcVo> ndcList = new ArrayList<NdcVo>();
        Map<String, ProductVo> flowScope = new HashMap<String, ProductVo>();
        
        ProductVo productVo = new ProductVo();
        productVo.setId("1");
        int count = 0;
        
        for (FdbAddVo vo : fdbAddList) {
            
            NdcVo ndcVo = new NdcVo();
            ndcVo.setNdc(vo.getNdc());
            ndcList.add(ndcVo);
            
            if (count >= PPSConstants.I3) {
                break;
            }
            
            count++;
        }
        
        productVo.setNdcs(ndcList);
        
        flowScope.put(PRODUCT_KEY, productVo);
        
        return flowScope;
    }


    /**
     * test retrieve FdbAddDomain Capability
     */
    @Test
    public void testRetrieveEPLPAddList() {
        List<FdbAddVo> fdbAddVo = fDBUpdateProcessService.retrieveEPLAddList();
        assertNotNull("fdbAddVo is null", fdbAddVo);
    }

    /**
     * test Seq number are the same
     */
    @Test
    public void testSeqNoSame() {

        String s1 = "1111";
        String[] seqNos = { s1, s1, s1, s1, "3333", "2222" };

        List<String> selectedSeqNos = new ArrayList<String>();
        selectedSeqNos.add(s1);
        selectedSeqNos.add(s1);
        selectedSeqNos.add(s1);
        selectedSeqNos.add(s1);

        boolean sucess = validateSequenceNos(seqNos, selectedSeqNos);
        assertTrue("Selected seq nos not the same", sucess);
    }

    /**
     * testRetrievePackagedDrugPendingList
     */
    @Test
    public void testRetrievePackagedDrugPendingList() {

        FDBSearchOptionVo fDBSearchOptionVos = fDBUpdateProcessService
                .retrieveNdc(NDC_2, getTestUser());

        assertNotNull("fDBSearchOptionVo is null!", fDBSearchOptionVos);

        List<FDBSearchOptionResultVo> detailList = (List<FDBSearchOptionResultVo>) fDBSearchOptionVos
                .getSearchOptionResults();

        for (FDBSearchOptionResultVo fdbSearchOptionResultVo2 : detailList) {
            fdbSearchOptionResultVo2.getNDC();
            fdbSearchOptionResultVo2.getGCNSeqNo();
            fdbSearchOptionResultVo2.getNDCFormatCode();
            fdbSearchOptionResultVo2.getLabelName();

            LOG.debug(" Label Name: "
                    + fdbSearchOptionResultVo2.getLabelName());
            LOG.debug(" NDC: " + fdbSearchOptionResultVo2.getNDC());
            LOG.debug(" Gcn Seq No: "
                    + fdbSearchOptionResultVo2.getGCNSeqNo());
            LOG.debug(" Ndc Format Code: "
                    + fdbSearchOptionResultVo2.getNDCFormatCode());
        }

    }

    /**
     * Unit Test for retrieving NDCs from FDB
     */
    @Test
    public void testFDBSearch() {
        
        String ndcSearchStr = NDC_2;
        List<FDBSearchOptionResultVo> searchResults =  doFDBSearch(ndcSearchStr, FDBSearchOptionType.NDC_SEARCH, 
            getTestUser());
        assertNotNull(" Search List is null", searchResults);
        
        for (FDBSearchOptionResultVo searchResult : searchResults) {

            FdbAddVo fdbAddVo = new FdbAddVo();
            fdbAddVo.setNdc(searchResult.getNDC());

            if (StringUtils.isBlank(searchResult.getNDCFormatCode())) {
                searchResult.setNDCFormatCode("0");
            }

            fdbAddVo.setGcnSeqno(Long.valueOf(searchResult.getGCNSeqNo()));

            LOG.debug("-------------------------------------------------------------------------");
            LOG.debug("ManufacturerDistributorName: "  + searchResult.getManufacturerDistributorName());
            LOG.debug("Label Name: "  + searchResult.getLabelName());
            LOG.debug("LabelName25:  "  + searchResult.getLabelName25());
            LOG.debug("NDC:  " + searchResult.getNDC());
            LOG.debug("Gcn Seq No: " + searchResult.getGCNSeqNo());
            LOG.debug("Ndc Format Code: " + searchResult.getNDCFormatCode());
        }
        
       
    }
   
    /**
     * testFdbSearchValidation
     */
    public void testFdbSearchGcnSeqNoValidation() {
        
        FDBSearchOptionVo fdbSearchOption = new FDBSearchOptionVo();
        fdbSearchOption.setFdbSearchOptionType(FDBSearchOptionType.GCNSEQNO_SEARCH);
        fdbSearchOption.setFdbSearchString("");
        
        // do validation for emtpy string
        Errors errors1 =  new Errors();
        validator.validate(fdbSearchOption, errors1);
        
        for (String error : errors1.getLocalizedErrors()) {
            LOG.debug(" error: " + error);
        }
        
        assertTrue("Empty search results ", errors1.hasError(ErrorKey.EMPTY_SEARCH));
        
        // do validation with non numeric values
        fdbSearchOption.setFdbSearchString("ABCDE");
        Errors errors2 =  new Errors();
        validator.validate(fdbSearchOption, errors2);
        
        for (String error : errors2.getLocalizedErrors()) {
            LOG.debug("error: " + error);
        }
        
        assertTrue("Entry was non numeric ", errors2.hasError(ErrorKey.COMMON_NOT_NUMERIC));
        
    }
    
    /**
     * testRemoveDuplicates
     */
    public void testRemovedIfExistList() {
        boolean selected = false;
        String[] selectedNdcArray = 
        { "00182-0141-01", NDC_1, "00162-0151-11", "00006-0237-50", "33045-0387-05-1", "00054-4744-25-1" };
        
        List<String> selectedNdcs = new ArrayList<String>();
        List<FdbAddVo> pendingList = new ArrayList<FdbAddVo>();
        
        
        FdbAddVo fdbAddVo1 = new FdbAddVo();
        fdbAddVo1.setNdc(selectedNdcArray[0]);
        
        FdbAddVo fdbAddVo2 = new FdbAddVo();
        fdbAddVo2.setNdc(selectedNdcArray[1]);
        
        FdbAddVo fdbAddVo3 = new FdbAddVo();
        fdbAddVo3.setNdc(selectedNdcArray[2]);
        
        FdbAddVo fdbAddVo4 = new FdbAddVo();
        fdbAddVo4.setNdc(selectedNdcArray[PPSConstants.I3]);
        
        FdbAddVo fdbAddVo5 = new FdbAddVo();
        fdbAddVo5.setNdc(selectedNdcArray[PPSConstants.I4]);
        
        FdbAddVo fdbAddVo6 = new FdbAddVo();
        fdbAddVo6.setNdc(selectedNdcArray[PPSConstants.I5]);
        
        pendingList.add(fdbAddVo1);
        pendingList.add(fdbAddVo2);
        pendingList.add(fdbAddVo3);
        pendingList.add(fdbAddVo4);
        pendingList.add(fdbAddVo5);
        pendingList.add(fdbAddVo6);
        
        selectedNdcs.add(selectedNdcArray[0]); // in db
        selectedNdcs.add(selectedNdcArray[1]);
        selectedNdcs.add(selectedNdcArray[2]); 
        selectedNdcs.add(selectedNdcArray[PPSConstants.I3]); //in db
     
        //selectedNdcs.add(selectedNdcArray[4]); //in db
        
        List<FdbAddVo> removedlist =  fDBUpdateProcessService.getRemovedIfExistList(selectedNdcs, pendingList, selected);
        
        for (FdbAddVo fdbAddVo : removedlist) {
            LOG.debug("fdbAddVo: ndc: " + fdbAddVo.getNdc());
        }
        
        LOG.debug("removedList size: " +  removedlist.size());
        LOG.debug("pendinglist size: " +  pendingList.size());
        
        assertTrue("test is true" , removedlist.size() > 0);
    }
    
    
    
    
    /**
     * Unit Test for retrieving NDCs from FDB
     * @param type type
     * @param user UserVo
     * @param criteria criteria
     * @return FDBSearchOptionResultVo list
     */
    private List<FDBSearchOptionResultVo> doFDBSearch(String criteria, FDBSearchOptionType type, UserVo user) {

        FDBSearchOptionVo fdbSearchOption = new FDBSearchOptionVo();

        // set type of search
        fdbSearchOption.setFdbSearchOptionType(type);
        fdbSearchOption.setFdbSearchString(criteria);

        fdbSearchOption = drugReferenceService.performFDBSearchOption(fdbSearchOption, user);
        List<FDBSearchOptionResultVo> searchResults = (List<FDBSearchOptionResultVo>) fdbSearchOption.getSearchOptionResults();

        assertNotNull("search List is null", searchResults);
       
        return searchResults;

    }

    /**
     * Unit Test for gettting NDC Ids
     */
    @Test
    public void testGetNdcsIds() {
        String[] ndcs = { NDC_2, NDC_4, NDC_5, NDC_6, NDC_3 };
        String[] idx = { "1", "4", "3", "2" };

        List<String> ndcList = getSelectedItems(ndcs, idx);
        assertTrue("wrong list size", ndcList.size() == PPSConstants.I4);

        assertTrue("1results do not match", ndcList.contains(NDC_6));
        assertTrue("2results do not match", ndcList.contains(NDC_5));
        assertTrue("3results do not match", ndcList.contains(NDC_3));
        assertTrue("4results do not match", ndcList.contains(NDC_4));

    }

    /**
     * Unit Test for Retrieve Pending List
     */
    @Test
    public void testRetrieveAddList() {

        List<FdbAddVo> fdbAddVoList = fDBUpdateProcessService.retrieveEPLAddList();

        LOG.debug("Before sorting..");

        for (FdbAddVo fdbAddVo : fdbAddVoList) {
            LOG.debug("GCNSeqNo: " + fdbAddVo.getGcnSeqno());
        }

        sortPendingListByGCNSeqNo(fdbAddVoList);

        LOG.debug("After sorting..");

        for (FdbAddVo fdbAddVo : fdbAddVoList) {
            LOG.debug("After Sort, GCNSeqNo: "
                    + fdbAddVo.getGcnSeqno());
        }

        for (FdbAddVo fdbAddVo : fdbAddVoList) {

            LOG.debug("|--------------------------------------------------------------------|");
            LOG.debug("  GNC SEQ NO: " + fdbAddVo.getGcnSeqno());
            LOG.debug("  NDC: " + fdbAddVo.getNdc());
            LOG.debug("  FDB Creation Date: "
                    + fdbAddVo.getFdbCreationDate());

            for (ProductVo productVo : fdbAddVo.getProductVos()) {
                LOG.debug("  Va Product Name: "
                        + productVo.getVaProductName());
                LOG.debug("  Product id: " + productVo.getId());
            }

            LOG.debug("|----------------------------------------------------------------------|");

        }

        assertNotNull("fdbAddVoList is null", fdbAddVoList);
    }

    /**
     * 
     * test Retrieve FdbADD by NDCs
     * 
     */
    @Test
    public void testRetrieveFdbAddByNDCs() {

        String[] ndcs = { NDC_2, NDC_4, NDC_5, NDC_6, NDC_3 };
        String[] idx = { "1", "4", "3", "2" };

        List<String> ndcList = getSelectedItems(ndcs, idx);

        List<FdbAddVo> selectedList = fDBUpdateProcessService.retrieveEPLPendingListByNdc(ndcList);
        assertTrue("selected list size is not correct! ", selectedList.size() == PPSConstants.I4);

        for (FdbAddVo fdbAddVo : selectedList) {
            LOG.debug("|-------------------------------------------------------------------|");
            LOG.debug("1.GNC SEQ NO: " + fdbAddVo.getGcnSeqno());
            LOG.debug("2.NDC: " + fdbAddVo.getNdc());
            LOG.debug("FDB Creation Date: " + fdbAddVo.getFdbCreationDate());

            for (ProductVo productVo : fdbAddVo.getProductVos()) {
                LOG.debug("Va Product Name: " + productVo.getVaProductName());
                LOG.debug("Product id: " + productVo.getId());
            }

        }
    }

    /**
     * test searchEPL by NDC number
     */
    @Test
    public void testSearchEPLByNdcNumber() {

        List<NdcVo> ndcs = fDBUpdateProcessService.searchNdcByNumber(NDC_7);

        assertTrue("ndc.size = 0 ", ndcs.size() > 0);

        for (NdcVo ndcVo : ndcs) {
            LOG.debug("ndc: " + ndcVo.getNdc());
            LOG.debug("gcnSeqNo : " + ndcVo.getSequenceNumber());
            LOG.debug("trade name : " + ndcVo.getTradeName());
            LOG.debug("UpcUpn : " + ndcVo.getUpcUpn());
            LOG.debug("Item Status : "
                    + ndcVo.getItemStatus().toString());
        }
    }

    /**
     * test DeleteItemsFromAddList
     */
    public void testDeleteItemsFromAddList() {
        
        String[] ndcs = { NDC_7, " 00006-0237-58", "0054-0243-24", NDC_6, "49685-928-01" };
        
        List<FdbAddVo> fdbAddVoList = fDBUpdateProcessService.retrieveEPLAddList();
        int size1 = fdbAddVoList.size();
        
        fDBUpdateProcessService.deleteItemsFromAddList(ndcs);
        
        List<FdbAddVo> fdbAddVoList2 = fDBUpdateProcessService.retrieveEPLAddList();
        int size2 = fdbAddVoList2.size();
        
        assertTrue("items were not deleted", (size1 - size2)  == PPSConstants.I4);
        
    }
    
    
    
    
    /**
     * test create productVo template
     */
    @Test
    public void testCreateBlankProductTemplate() {

        String[] ndcs = { NDC_2, NDC_4, NDC_5, NDC_6, NDC_3 };
        String[] idx = { "1", "4", "3", "2" };

        List<String> ndcList = getSelectedItems(ndcs, idx);
        assertTrue("ndc list is empty! ", ndcList.size() > 0);

        ProductVo productVo = fDBUpdateProcessService.createBlankTemplate(getTestUser(), ndcList);

        assertTrue("No NDCs created! ", productVo.getNdcs().size() > 0);
    }

    /**
     * test create ProductVo from existing.
     */
    @Test
    public void testCreateFromExisting() {

        String[] selectedNdcs = { NDC_2, NDC_4, NDC_5 };
        String productSeqNo = PRODUCTID; // ID must exist in EPL_PRODUCTS table

        ProductVo productVo = fDBUpdateProcessService.createFromExisting(this.getTestUser(), selectedNdcs, productSeqNo);

        assertNotNull("productVo is null ", productVo);

    }
    
  
    
    /**
     * test Delete Items By Id From UpdateList
     */
    @Test
    public void testDeleteUpdateItemsById() {
    
        String[] ids = { "1", "2", "3", "4"};
    
        List<FdbUpdateVo> fdbUpdateVo = fDBUpdateProcessService.retrieveEPLUpdateList();
        int size1 = fdbUpdateVo.size();
        
        fDBUpdateProcessService.deleteItemsFromUpdateList(ids);
    
        List<FdbUpdateVo> fdbUpdateVo2 = fDBUpdateProcessService.retrieveEPLUpdateList();
        int size2 = fdbUpdateVo2.size();
    
        assertTrue("product items were not deleted!", (size1 - size2)  == PPSConstants.I4);
    }
    
    /**
     * test Retrieve UpdateList
     */
    @Test
    public void testRetrieveUpdateList() {
        
        
        List<FdbUpdateVo> fdbUpdateVoList = fDBUpdateProcessService.retrieveEPLUpdateList();
        
        assertTrue("fdbUpdateVoList is empty", fdbUpdateVoList.size() > 0);

        for (FdbUpdateVo fdbUpdateVo : fdbUpdateVoList) {
            LOG.debug("------------------------------------------------- "); 
            LOG.debug("2.message: " + fdbUpdateVo.getMessage());
        }
    }
    
   /**
    * test retrieve Auto update list
    */
    @Test
    public void testRetrieveAutoUpdateList() {
        
        List<FdbAutoUpdateVo> fdbAutoUpdateList = fDBUpdateProcessService.retrieveEplAutoUpdateList();
        
        assertTrue("fdbAutoUpdateList is empty", fdbAutoUpdateList.size() > 0);
        
        for (FdbAutoUpdateVo vo : fdbAutoUpdateList) {
            LOG.debug("--------------------------------------------------- "); 
            LOG.debug("type: " + vo.getItemType());
            LOG.debug("formatCode: " + vo.getFormatIndicator());
            LOG.debug("message: " + vo.getMessage());
        }
    }
    

    /**
     * test retrieve auto add list
     *
     */
    @Test
    public void testRetrieveAutoAddList() {
        
        List<FdbAutoAddVo> fdbAutoAddList = fDBUpdateProcessService.retrieveEplAutoAddList();
        
        
        assertTrue("fdbAutoAddList is empty", fdbAutoAddList.size() > 0);
        
        for (FdbAutoAddVo vo : fdbAutoAddList) {
            
            LOG.debug("NDC: " + vo.getNdc());    
            LOG.debug("GNC SEQ NO: " + vo.getGcnSeqno());
            LOG.debug("Created Date: " + vo.getCreatedDate());
        }
    }
    
    
    /**
     * testFdbDownloadCSVFile
     * @throws IOException 
     */
    @Test
    public void testCreateCSVFile() throws IOException {
        String criteria = "motrin";
        List<FDBSearchOptionResultVo> searchResults =  doFDBSearch(criteria, FDBSearchOptionType.GENERIC_SEARCH,
            getTestUser());
        assertNotNull("fDBSearchOptionVo is null", searchResults);
        
        for (FDBSearchOptionResultVo searchResult : searchResults) {
            
            StringBuilder sb =  fDBUpdateProcessService.createFdbCSVFile(searchResults);
            assertTrue("sb length is < 0 ", sb.length() > 0);
            
            if (StringUtils.isBlank(searchResult.getNDCFormatCode())) {
                searchResult.setNDCFormatCode("0");
            }

            LOG.debug("------------------------------ START ---------------------------------------");
            LOG.debug("CSV output : "  + sb.toString());
            LOG.debug("------------------------------- END ------------------------------------------");

        }
        
        
    }

    
    
    
    
    /**
     * Sorting Pending List by GCNSeqNo.
     * 
     * @param pPendingList list to sort
     * @return listof FdbADdVo
     */
    private List<FdbAddVo> sortPendingListByGCNSeqNo(List<FdbAddVo> pPendingList) {
        Collections.sort(pPendingList, new Comparator<Object>() {

            @Override
            public int compare(Object o1, Object o2) {
                FdbAddVo p1 = (FdbAddVo) o1;
                FdbAddVo p2 = (FdbAddVo) o2;

                return p1.getGcnSeqno().compareTo(p2.getGcnSeqno());
            }
        });

        return pPendingList;
    }

    /**
     * Helper method to simulate the retrieval of Selected Items
     * 
     * @param pNdcIds - user selected NDCs
     * @param pFdbItem  item
     * @return selected items
     */
    private List<String> getSelectedItems(String[] pNdcIds, String[] pFdbItem) {
        int i = 0;
        List<String> ndcList = new ArrayList<String>();

        for (int f = 0; f < pFdbItem.length; f++) {

            if (!StringUtils.isBlank(pFdbItem[f])) {
                i = Integer.valueOf((pFdbItem[f]));
                ndcList.add(pNdcIds[i]);
            }
        }

        return ndcList;
    }


    /**
     * valiates the sequence nos.
     * 
     * @param allSeqNos - all seqNos.
     * @param pSelectedSeqNos - selectedSeqNos.
     * @return validated - true success
     */
    private boolean validateSequenceNos(String[] allSeqNos,
            List<String> pSelectedSeqNos) {
        int selectedCount = 0;
        int foundSeqNoCount = 0;
        String firstSeqNo = pSelectedSeqNos.get(0);

        for (String selectedSeqNo : pSelectedSeqNos) {

            if (!selectedSeqNo.equals(firstSeqNo)) {
                return false;
            }

            selectedCount++;
            LOG.debug("selected count: " + selectedCount);
        }

        for (String seqNo : allSeqNos) {

            if (seqNo.equals(firstSeqNo)) {
                foundSeqNoCount++;
                LOG.debug("found count: " + foundSeqNoCount);
            }
        }

        if (foundSeqNoCount != selectedCount) {

            return false;
        }

        return true;
    }

  
}
