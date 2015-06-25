/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.bean;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionVo;


/**
 * 
 * FdbAddBean
 */
@Component("fdbAddBean")
@Scope("prototype")
public class FdbAddBean {
    
    private String[] index;
    private String[] ndcId;
    private String[] seqNo;
    private String[] fdbItem;
    private String[] itemIds;
    private String productSeqNo;
    private String productId;
    private Integer[] rowIdx;
    private String [] gcnSeqno;
    
    private FDBSearchOptionVo fdbSearchOption;
    

    
    /**
     * get RowIdx
     * @return the rowIdx
     */
    public Integer[] getRowIdx() {
        return rowIdx;
    }

    /**
     * set RowIdx
     * @param rowIdx the rowIdx to set
     */
    public void setRowIdx(Integer[] rowIdx) {
        this.rowIdx = rowIdx;
    }

    /**
     * getGcnSeqno
     * @return the gcnSeqno
     */
    public String[] getGcnSeqno() {
        return gcnSeqno;
    }

    /**
     * setGcnSeqno
     * @param gcnSeqno the gcnSeqno to set
     */
    public void setGcnSeqno(String[] gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    /**
     * getNdcId
     * @return the ndcId
     */
    public String[] getNdcId() {
        return ndcId;
    }

    /**
     * setNdcId
     * @param ndcId the ndcId to set
     */
    public void setNdcId(String[] ndcId) {
        this.ndcId = ndcId;
    }

    /**
     * getSeqNo
     * @return the seqNo
     */
    public String[] getSeqNo() {
        return seqNo;
    }

    /**
     * setSeqNo
     * @param seqNo the seqNo to set
     */
    public void setSeqNo(String[] seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * getFdbItem
     * @return the fdbItem
     */
    public String[] getFdbItem() {
        return fdbItem;
    }

    /**
     * setFdbItem
     * @param fdbItem the fdbItem to set
     */
    public void setFdbItem(String[] fdbItem) {
        this.fdbItem = fdbItem;
    }

    /**
     * getProductSeqNo
     * @return the productSeqNo
     */
    public String getProductSeqNo() {
        return productSeqNo;
    }

    /**
     * setProductSeqNo
     * @param productSeqNo the productSeqNo to set
     */
    public void setProductSeqNo(String productSeqNo) {
        this.productSeqNo = productSeqNo;
    }

    /**
     * getIndex
     * @return the index
     */
    public String[] getIndex() {
        return index;
    }

    /**
     * setIndex
     * @param index the index to set
     */
    public void setIndex(String[] index) {
        this.index = index;
    }

    /**
     * getFdbSearchOption
     * @return the fdbSearchOption
     */
    public FDBSearchOptionVo getFdbSearchOption() {
        return fdbSearchOption;
    }

    /**
     * setFdbSearchOption
     * @param fdbSearchOption the fdbSearchOption to set
     */
    public void setFdbSearchOption(FDBSearchOptionVo fdbSearchOption) {
        this.fdbSearchOption = fdbSearchOption;
    }

    /**
     * getProductId
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * setProductId
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * getItemIds for the FdbAddBean.
     * @return the itemIds
     */
    public String[] getItemIds() {
        return itemIds;
    }

    /**
     * setItemIds for the FdbAddBean.
     * @param itemIds the itemIds to set
     */
    public void setItemIds(String[] itemIds) {
        this.itemIds = itemIds;
    }
    

    

    
}
