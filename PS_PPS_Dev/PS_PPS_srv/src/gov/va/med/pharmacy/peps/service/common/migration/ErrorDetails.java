/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * ErrorDetails
 *
 */
public class ErrorDetails {

    private String ien;
    private String fileId;
    private String itemName;
    private String field;
    private String fieldValue;
    private String reasonForError;
    private int multipleIen;
    private String multipleField;

    /**
     * getIen
     * @return ien
     */
    public String getIen() {
        return ien;
    }

    /**
     * setIen
     * @param pIen pIen
     */
    public void setIen(String pIen) {
        this.ien = pIen;
    }

    /**
     * getItemName for ErrorDetails.
     * @return itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * setItemName for ErrorDetails.
     * @param pItemName pItemName
     */
    public void setItemName(String pItemName) {
        this.itemName = pItemName;
    }

    /**
     * getField
     * @return field
     */
    public String getField() {
        return field;
    }

    /**
     * getField
     * @param pField pField
     */
    public void setField(String pField) {
        this.field = pField;
    }

    /**
     * getFieldValue for ErrorDetails.
     * @return fieldValue
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * setFieldValue for ErrorDetails.
     * @param pFieldValue pFieldValue
     */
    public void setFieldValue(String pFieldValue) {
        this.fieldValue = pFieldValue;
    }

    /**
     * getReasonForError for ErrorDetails.
     * @return reasonForError
     */
    public String getReasonForError() {
        return reasonForError;
    }

    /**
     * setReasonForError for ErrorDetails.
     * @param pReasonForError pReasonForError
     */
    public void setReasonForError(String pReasonForError) {
        this.reasonForError = pReasonForError;
    }

    /**
     * getMultipleIen
     * @return multipleIen
     */
    public int getMultipleIen() {
        return multipleIen;
    }

    /**
     * setMultipleIen
     * @param pMultipleIen pMultipleIen
     */
    public void setMultipleIen(int pMultipleIen) {
        this.multipleIen = pMultipleIen;
    }

    /**
     * getMultipleField
     * @return multipleField
     */
    public String getMultipleField() {
        return multipleField;
    }

    /**
     * multipleField
     * @param pMultipleField pMultipleField
     */
    public void setMultipleField(String pMultipleField) {
        this.multipleField = pMultipleField;
    }

    /**
     * getFileId
     * @return fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * setFileId
     * @param pFileId pFileId
     */
    public void setFileId(String pFileId) {
        this.fileId = pFileId;
    }

}
