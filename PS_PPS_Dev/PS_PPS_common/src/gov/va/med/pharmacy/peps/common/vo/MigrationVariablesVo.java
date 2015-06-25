/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.List;


/**
 * Data representing the Migration variables.
 */
public class MigrationVariablesVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private int iSuccessfullyMigrated = 0;
    private int iErroredOnMigration = 0;
    private int iDuplicatesNotMigrated = 0;
    private boolean bEndOfFile = false;
    private String strLastIEN = "0";

    private int iNumManufacturersMigrated = 0;
    private int iNumPackageTypesMigrated = 0;
    private List<IngredientVo> ingredientList = new ArrayList<IngredientVo>();

    /**
     * getStrLastIEN
     * 
     * @return strLastIEN property
     */
    public String getStrLastIEN() {
        return strLastIEN;
    }

    /**
     * getIngredientList
     * 
     * @return ingredientList property
     */
    public List<IngredientVo> getIngredientList() {
        return ingredientList;
    }

    /**
     * getISuccessfullyMigrated
     * 
     * @return iErroredOnMigration property
     */
    public int getISuccessfullyMigrated() {
        return iSuccessfullyMigrated;
    }

    /**
     * getIErroredOnMigration
     * 
     * @return iDuplicatesNotMigrated property
     */
    public int getIErroredOnMigration() {
        return iErroredOnMigration;
    }

    /**
     * getIDuplicatesNotMigrated
     * 
     * @return iSuccerssfullyMigrated property
     */
    public int getIDuplicatesNotMigrated() {
        return iDuplicatesNotMigrated;
    }

    /**
     * isEndOfFile
     * 
     * @return bEndOfFile property
     */
    public boolean isEndOfFile() {
        return bEndOfFile;
    }

    /**
     * set the strLastIEN property
     * @param strLastIEN strLastIEN
     */
    public void setStrLastIEN(String strLastIEN) {
        this.strLastIEN = strLastIEN;
    }

    /**
     * set the ingredientList property
     * @param ingredientList ingredientList
     */
    public void setIngredientList(List<IngredientVo> ingredientList) {
        this.ingredientList = (ArrayList<IngredientVo>) ingredientList;
    }

    /**
     * set the iSuccessfullyMigrated property
     * @param successfullyMigrated iSuccessfullyMigrated
     */
    public void setISuccessfullyMigrated(int successfullyMigrated) {
        this.iSuccessfullyMigrated = successfullyMigrated;
    }
    
    /**
     * set the iErroredOnMigration property
     * @param errorMigration iErroredOnMigration
     */
    public void setIErroredOnMigration(int errorMigration) {
        this.iErroredOnMigration = errorMigration;
    }

    /**
     * set the iDuplicatesNotMigrated property
     * @param dupesNotMigrated iDuplicatesNotMigrated 
     */
    public void setIDuplicatesNotMigrated(int dupesNotMigrated) {
        this.iDuplicatesNotMigrated = dupesNotMigrated;
    }

    /**
     * getINumManufacturersMigrated
     * @return iNumManufacturersMigrated property
     */
    public int getINumManufacturersMigrated() {
        return iNumManufacturersMigrated;
    }

    /**
     * set the iNumManufacturersMigrated property
     * 
     * @param manufacturersMigrated iNumManufacturersMigrated
     */
    public void setINumManufacturersMigrated(int manufacturersMigrated) {
        this.iNumManufacturersMigrated = manufacturersMigrated;
    }

    /**
     * getINumPackageTypesMigrated
     * 
     * @return iNumPackageTypesMigrated property
     */
    public int getINumPackageTypesMigrated() {
        return iNumPackageTypesMigrated;
    }

    /**
     * set the iNumPackageTypesMigrated property
     * 
     * @param packageTypesMigrated iNumPackageTypesMigrated
     */
    public void setINumPackageTypesMigrated(int packageTypesMigrated) {
        this.iNumPackageTypesMigrated = packageTypesMigrated;
    }

    /**
     * set the bEndOfFile property
     * @param beof bEndOfFile
     */
    public void setEndOfFile(boolean beof) {
        this.bEndOfFile = beof;
    }
}
