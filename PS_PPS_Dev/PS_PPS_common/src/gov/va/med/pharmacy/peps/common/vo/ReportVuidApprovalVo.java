/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.List;


/**
 * 
 * ReportVuidApprovalVo
 *
 */
public class ReportVuidApprovalVo {

    private List<ReportVuidVo> newIngredientList;
    private List<ReportVuidVo> newGenericList;
    private List<ReportVuidVo> newDrugClassList;
    private List<ReportVuidVo> newProductList;
    private List<ReportVuidVo> modifedProductList;
    private List<ReportVuidVo> modifiedIngredientList;
    private List<ReportVuidVo> modifiedGenericList;
    
    /**
     * setNewIngredientList
     * @param newIngredientList newIngredientList
     */
    public void setNewIngredientList(List<ReportVuidVo> newIngredientList) {
        this.newIngredientList = newIngredientList;
    }
    
    /**
     * getNewIngredientList
     * @return newIngredientList newIngredientList
     */
    public List<ReportVuidVo> getNewIngredientList() {
        return newIngredientList;
    }
    
    /**
     * setNewGenericList
     * @param newGenericList newGenericList
     */
    public void setNewGenericList(List<ReportVuidVo> newGenericList) {
        this.newGenericList = newGenericList;
    }
    
    /**
     * getNewGenericList
     * @return newGenericList newGenericList
     */
    public List<ReportVuidVo> getNewGenericList() {
        return newGenericList;
    }
    
    /**
     * setNewDrugClassList
     * @param newDrugClassList newDrugClassList
     */
    public void setNewDrugClassList(List<ReportVuidVo> newDrugClassList) {
        this.newDrugClassList = newDrugClassList;
    }
    
    /**
     * getNewDrugClassList
     * @return newDrugClassList newDrugClassList
     */
    public List<ReportVuidVo> getNewDrugClassList() {
        return newDrugClassList;
    }
    
    /**
     * setNewProductList
     * @param newProductList newProductList
     */
    public void setNewProductList(List<ReportVuidVo> newProductList) {
        this.newProductList = newProductList;
    }
    
    /**
     * getNewProductList
     * @return newProductList newProductList
     */
    public List<ReportVuidVo> getNewProductList() {
        return newProductList;
    }
    

    /**
     * setModifedProductList
     * @param modifedProductList modifedProductList
     */
    public void setModifedProductList(List<ReportVuidVo> modifedProductList) {
        this.modifedProductList = modifedProductList;
    }
    
    /**
     * getModifedProductList
     * @return modifedProductList modifedProductList
     */
    public List<ReportVuidVo> getModifedProductList() {
        return modifedProductList;
    }

    
    /**
     * setModifiedIngredientList
     * @param modifiedIngredientList modifiedIngredientList
     */
    public void setModifiedIngredientList(List<ReportVuidVo> modifiedIngredientList) {
        this.modifiedIngredientList = modifiedIngredientList;
    }
    
    /**
     * getModifiedIngredientList
     * @return modifiedIngredientList modifiedIngredientList
     */
    public List<ReportVuidVo> getModifiedIngredientList() {
        return modifiedIngredientList;
    }
    
    /**
     * setModifiedGenericList
     * @param modifiedGenericList modifiedGenericList
     */
    public void setModifiedGenericList(List<ReportVuidVo> modifiedGenericList) {
        this.modifiedGenericList = modifiedGenericList;
    }
    
    /**
     * getModifiedGenericList
     * @return modifiedGenericList modifiedGenericList
     */
    public List<ReportVuidVo> getModifiedGenericList() {
        return modifiedGenericList;
    }
}
