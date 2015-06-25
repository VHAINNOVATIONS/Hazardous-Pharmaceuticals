/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Drug data vendor version information
 */
public class DrugMonographGcnseqnoAssocsVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Long monographId;
    private Long gcnSeqNo;
    private Long eplId;

    /**
     * getMonographId.
     * @return monographId property
     */
    public Long getMonographId() {
        return monographId;
    }

    /**
     * setMonographId.
     * @param monographId monographId property
     */
    public void setMonographId(Long monographId) {
        this.monographId = monographId;
    }
    
    /**
     * getGcnSeqNo.
     * @return gcnSeqNo property
     */
    public Long getGcnSeqNo() {
        return gcnSeqNo;
    }

    /**
     * setGcnSeqNo.
     * @param gcnSeqNo gcnSeqNo property
     */
    public void setGcnSeqNo(Long gcnSeqNo) {
        this.gcnSeqNo = gcnSeqNo;
    }

    
    public Long getEplId() {
    
        return eplId;
    }

    
    public void setEplId(Long eplId) {
    
        this.eplId = eplId;
    }

}
