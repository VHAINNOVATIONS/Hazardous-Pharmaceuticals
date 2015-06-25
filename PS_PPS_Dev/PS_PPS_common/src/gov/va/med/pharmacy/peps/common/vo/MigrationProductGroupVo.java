/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.List;


/**
 * MigrationProductGroupVo is used to contain productLists
 *
 */
public class MigrationProductGroupVo extends ValueObject {
    
    private static final long serialVersionUID = 1L;
    private List<ProductVo> productList;
    private boolean endOfFile;
    private int numberErroredOnRetrieval;
    private String lastIEN;

    /**
     * the constructor
     */
    public MigrationProductGroupVo() {
        productList = null;
        endOfFile = false;
        numberErroredOnRetrieval = 0;
    }

    /**
     * getLastIEN
     * @return lastIEN
     */
    public String getLastIEN() {
        return lastIEN;
    }
    
    /** 
     * setLastIen
     * @param lastIEN lastIEN
     */
    public void setLastIEN(String lastIEN) {
        this.lastIEN = lastIEN;
    }

    /**
     * isEndOfFile
     * @return endOfFile
     */
    public boolean isEndOfFile() {
        return endOfFile;
    }

    /**
     * setEndOfFile
     * @param endOfFile endOfFile
     */
    public void setEndOfFile(boolean endOfFile) {
        this.endOfFile = endOfFile;
    } 

    /**
     * getProductList
     * @return productList
     */
    public List<ProductVo> getProductList() {
        return productList;
    }

    /**
     * setProductList
     * @param productList productList
     */
    public void setProductList(List<ProductVo> productList) {
        this.productList = productList;
    }

    /**
     * getNumberErroredOnRetrieval
     * @return numberErroredOnRetrieval
     */
    public int getNumberErroredOnRetrieval() {
        return numberErroredOnRetrieval;
    }
    
    /**
     * setNumberErroredOnRetrieval
     * @param numberErroredOnRetrieval numberErroredOnRetrieval
     */
    public void setNumberErroredOnRetrieval(int numberErroredOnRetrieval) {
        this.numberErroredOnRetrieval = numberErroredOnRetrieval;
    } 
    
}
