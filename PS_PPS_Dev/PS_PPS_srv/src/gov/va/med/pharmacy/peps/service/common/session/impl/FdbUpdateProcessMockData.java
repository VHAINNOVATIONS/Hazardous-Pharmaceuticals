/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.session.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.FdbUpdateVo;


/**
 * 
 * FdbUpdateProcessMockData
 */
public class FdbUpdateProcessMockData {
    
    private static String PRODUCT = "PRODUCT"; 
    private static String NDC1 = "00182-0141-01";
    private static String NDC = "NDC";

    /** fdbUpdateList */
    private List<FdbUpdateVo> fdbUpdateList = new ArrayList<FdbUpdateVo>();

    /** fdbAutoUpdateList */
    private List<FdbAutoUpdateVo> fdbAutoUpdateList = new ArrayList<FdbAutoUpdateVo>();

    /** fdbAutoAddList */
    private List<FdbAutoAddVo> fdbAutoAddList = new ArrayList<FdbAutoAddVo>();
    
    /**
     * Mock update list
     *
     * @return List of FdbUpdateVo
     */
    public List<FdbUpdateVo> retrieveMockEplUpdateList() {

     
        
        long eplId1 = PPSConstants.L9991;
        String name1 = "ACETAMINOPHEN 325MG TAB";
        String type1 = PRODUCT;
        String gcnSeqNo1 = "4489";
        String ndcFormatCode1 = "";
        String messsage1 = "this product was modified";
        Date creationDate1 = new Date();
        FdbUpdateVo fdbUpdateVo1 = createFdbUpdateVo(eplId1, type1, name1, gcnSeqNo1, ndcFormatCode1, messsage1, creationDate1);
        fdbUpdateList.add(fdbUpdateVo1);
        
        long eplId2 = PPSConstants.L9991;
        String name2 = NDC1;
        String type2 = NDC;
        String gcnSeqNo2 = "73852";
        String ndcFormatCode2 = "04-4-2NDC";
        String messsage2 = "this is a message about NDC";
        Date creationDate2 = new Date();
        FdbUpdateVo fdbUpdateVo2 = createFdbUpdateVo(eplId2, type2, name2, gcnSeqNo2, ndcFormatCode2, messsage2, creationDate2);
        fdbUpdateList.add(fdbUpdateVo2);


        long eplId3 = new Long("9992");
        String name3 = "62794-0146-01";
        String type3 = NDC;
        String gcnSeqNo3 = "93562";
        String ndcFormatCode3 = "04-4-2NDC";
        String messsage3 = "this is a message about NDC";
        Date creationDate3 = new Date();
        FdbUpdateVo fdbUpdateVo3 = createFdbUpdateVo(eplId3, type3, name3, gcnSeqNo3, ndcFormatCode3, messsage3, creationDate3);
        fdbUpdateList.add(fdbUpdateVo3);
        

        long eplId4 = new Long("99910");
        String name4 = "RISPERIDONE 2MG TAB,ORAL DISINTEGRATING";
        String type4 = PRODUCT;
        String gcnSeqNo4 = "51800";
        String ndcFormatCode4 = "";
        String messsage4 = "this is a message about NDC";
        Date creationDate4 = new Date();
        FdbUpdateVo fdbUpdateVo4 = createFdbUpdateVo(eplId4, type4, name4, gcnSeqNo4, ndcFormatCode4, messsage4, creationDate4);
        fdbUpdateList.add(fdbUpdateVo4);
        
        long eplId5 = new Long("9997");
        String name5 = "ATOMOXETINE 60MG CAP";
        String type5 = PRODUCT;
        String gcnSeqNo5 = "51493";
        String ndcFormatCode5 = "";
        String messsage5 = "This product was not auto updated";
        Date creationDate5 = new Date();
        FdbUpdateVo fdbUpdateVo5 = createFdbUpdateVo(eplId5, type5, name5, gcnSeqNo5, ndcFormatCode5, messsage5, creationDate5);
        fdbUpdateList.add(fdbUpdateVo5);
        
        
        return fdbUpdateList;
     
    }

    /**
     * Mock create FdbUpdateVo - remove later
     * @param eplId eplId
     * @param type type
     * @param name name
     * @param gcnSeqNo gcnSeqNo
     * @param formatCode formatCode
     * @param message  message
     * @param creationDate creationDate
     * @return FdbUpdateVo
     */
    private FdbUpdateVo createFdbUpdateVo(Long eplId, String type, String name, String gcnSeqNo, String formatCode, 
            String message, Date creationDate) {
        
        FdbUpdateVo fdbUpdateVo = new FdbUpdateVo();

        
        fdbUpdateVo.setMessage(message);
        fdbUpdateVo.setCreatedDate(creationDate);

        return fdbUpdateVo;
    }

    /**
     * retrieve auto UPDATE
     *
     * @return list of FdbAutoUpdateVos
     */
    public List<FdbAutoUpdateVo> retrieveMockEplAutoUpdateList() {

        long eplId = new Long("99910");
        String name = NDC1;  // ndc or product name
        String type = NDC;
        String gcnSeqNo = "73852";
        String ndcFormatCode = "";
        String messsage = "this is a message about NDC";
        Date creationDate = new Date();
        FdbAutoUpdateVo fdbAutoUpdateVo = createFdbAutoUpdateVo(eplId, name, type,
                gcnSeqNo, ndcFormatCode, messsage, creationDate);
        fdbAutoUpdateList.add(fdbAutoUpdateVo);
        
        
        long eplId1 =  new Long("99910");
        String name1 = "RISPERIDONE 2MG TAB,ORAL DISINTEGRATING";  // ndc or product name
        String type1 = PRODUCT;
        String gcnSeqNo1 = "51800";
        String ndcFormatCode1 = "";
        String messsage1 = "this is a message about PRODUCT";
        Date creationDate1 = new Date();
        FdbAutoUpdateVo fdbAutoUpdateVo1 = createFdbAutoUpdateVo(eplId1, name1, type1,
                gcnSeqNo1, ndcFormatCode1, messsage1, creationDate1);
        fdbAutoUpdateList.add(fdbAutoUpdateVo1);
        
        
        long eplId2 = new Long("9997");
        String name2 = "ATOMOXETINE 60MG CA";  // ndc or product name
        String type2 = PRODUCT;
        String gcnSeqNo2 = "51493";
        String ndcFormatCode2 = "";
        String messsage2 = "this is a message about PRODUCT";
        Date creationDate2 = new Date();
        FdbAutoUpdateVo fdbAutoUpdateVo2 = createFdbAutoUpdateVo(eplId2, name2, type2,
                gcnSeqNo2, ndcFormatCode2, messsage2, creationDate2);
        fdbAutoUpdateList.add(fdbAutoUpdateVo2);
        
        long eplId3 = new Long("9992");
        String name3 = "62794-0146-01";  // ndc or product name
        String type3 = NDC;
        String gcnSeqNo3 = "93562";
        String ndcFormatCode3 = "04-4-2NDC";
        String messsage3 = "this is a message about NDC";
        Date creationDate3 = new Date();
        FdbAutoUpdateVo fdbAutoUpdateVo3 = createFdbAutoUpdateVo(eplId3, name3, type3,
                gcnSeqNo3, ndcFormatCode3, messsage3, creationDate3);
        fdbAutoUpdateList.add(fdbAutoUpdateVo3);
        
        return fdbAutoUpdateList;
    }
    
    /**
     * retrieve auto ADD
     *
     * @return FdbAutoAddVo
     */
    public List<FdbAutoAddVo> retrieveMockEplAutoAddList() {
        
        long eplId = new Long("9991");         //ndc id.
        String ndc = NDC1;
        String addDesc = "addDesc";
        String gcnSeqNo = "73852";
        String vaProductName = "RISPERIDONE 2MG TAB,ORAL DISINTEGRATING";
        Date creationDate = new Date();
        FdbAutoAddVo fdbAutoAddVo = createFdbAutoAddVo(eplId, ndc, addDesc, gcnSeqNo, vaProductName, creationDate);
        fdbAutoAddList.add(fdbAutoAddVo);
        

        long eplId1 = new Long("9992");         //ndc id.
        String ndc1 = "62794-0146-01";
        String addDesc1 = "addDesc1";
        String gcnSeqNo1 = "93562";
        String vaProductName1 = " DIGITEK 0.25MG";
        Date creationDate1 = new Date();
        FdbAutoAddVo fdbAutoAddVo1 = createFdbAutoAddVo(eplId1, ndc1, addDesc1, gcnSeqNo1, vaProductName1, creationDate1);
        fdbAutoAddList.add(fdbAutoAddVo1);
        
        
        long eplId2 = new Long("9993");         //ndc id.
        String ndc2 = "00310-0131-10";
        String addDesc2 = "addDesc2";
        String gcnSeqNo2 = "79898";
        String vaProductName2 = "ZESTRIL";
        Date creationDate2 = new Date();
        FdbAutoAddVo fdbAutoAddVo2 = createFdbAutoAddVo(eplId2, ndc2, addDesc2, gcnSeqNo2, vaProductName2, creationDate2);
        fdbAutoAddList.add(fdbAutoAddVo2);
        
        
        long eplId3 = new Long("9994");         //ndc id.
        String ndc3 = "00006-0237-58";
        String addDesc3 = "addDesc3";
        String gcnSeqNo3 = "79913";
        String vaProductName3 = "PRINIVIL";
        Date creationDate3 = new Date();
        FdbAutoAddVo fdbAutoAddVo3 = createFdbAutoAddVo(eplId3, ndc3, addDesc3, gcnSeqNo3, vaProductName3, creationDate3);
        fdbAutoAddList.add(fdbAutoAddVo3);
        
        
        long eplId4 = new Long("9995");         //ndc id.
        String ndc4 = "00054-4744-25";
        String addDesc4 = "addDesc4";
        String gcnSeqNo4 = "59316";
        String vaProductName4 = "PSEUDOEPHEDRINE HYDROCHLORIDE";
        Date creationDate4 = new Date();
        FdbAutoAddVo fdbAutoAddVo4 = createFdbAutoAddVo(eplId4, ndc4, addDesc4, gcnSeqNo4, vaProductName4, creationDate4);
        fdbAutoAddList.add(fdbAutoAddVo4);

        return fdbAutoAddList;
    }
    
    
    

    /**
     * Creates Mock data for the retrieve Auto Update
     *
     * @param eplId id
     * @param name - NDC/PRODUCT
     * @param type - PRODUCT/NDC
     * @param gcnSeqNo - gnc seq NO
     * @param ndcFormatCode  ndcFormatCode
     * @param message message
     * @param creationDate messsage
     * @return FdbAutoUpdateVo
     */
    private FdbAutoUpdateVo createFdbAutoUpdateVo(long eplId, String name, String type, String gcnSeqNo, String ndcFormatCode,
            String message, Date creationDate) {
        
        FdbAutoUpdateVo fdbAutoUpdateVo = new FdbAutoUpdateVo();

        fdbAutoUpdateVo.setFormatIndicator(ndcFormatCode);
        fdbAutoUpdateVo.setMessage(message);
        fdbAutoUpdateVo.setCreatedDate(creationDate);
        
        return fdbAutoUpdateVo;
    }

   

    /**
     * Creates Mock data for the retrieve Auto Add
     *
     * @param eplId id 
     * @param ndc NDC
     * @param addDesc addDesc
     * @param  gcnSeqNo gcn Seq No
     * @param vaProductName va Product Name
     * @param creationDate creationDate
     * @return FdbAutoAddVo
     */
    private FdbAutoAddVo createFdbAutoAddVo(long eplId, String ndc, String addDesc,
            String gcnSeqNo, String vaProductName, Date creationDate) {
        
        FdbAutoAddVo fdbAutoAddVo = new FdbAutoAddVo();
        fdbAutoAddVo.setNdc(ndc);
        fdbAutoAddVo.setAddDesc(addDesc);
        fdbAutoAddVo.setGcnSeqno(Long.valueOf(gcnSeqNo));
        fdbAutoAddVo.setVaProductName(vaProductName);
        fdbAutoAddVo.setCreatedDate(creationDate);
        
        return fdbAutoAddVo;
    }

    
    
}
