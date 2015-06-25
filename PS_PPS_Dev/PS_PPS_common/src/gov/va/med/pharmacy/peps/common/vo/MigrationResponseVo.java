/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;


/**
 * Data representing a Migration Response Class
 */
public class MigrationResponseVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    private List<ManagedItemVo> managedItemsVos;
    private List<MigrationException> migrationExceptions;
    private Boolean eof;

    /**
     * the constructor
     */
    public MigrationResponseVo() {
        managedItemsVos = new ArrayList<ManagedItemVo>();
        migrationExceptions = new ArrayList<MigrationException>();
        eof = false;
    }

    /**
     * isEof
     * @return Managed Item List
     */
    public Boolean isEof() {
        return eof;
    }
    
    /**
     * setEof
     * @param eof sets the eof state  
     */
    public void setEof(Boolean eof) {
        this.eof = eof;
    }

    /**
     * getManagedItemVos
     * @return Managed Item List
     */
    public List<ManagedItemVo> getManagedItemsVos() {
        return managedItemsVos;
    }
    
    /**
     * setManagedItemVos
     * @param managedItemsVos creates a List of Managed Item VO's  
     */
    public void setManagedItemsVos(List<ManagedItemVo> managedItemsVos) {
        this.managedItemsVos = managedItemsVos;
    }

    /**
     * getMigrationExceptions
     * @return Migration Exception List
     */
    public List<MigrationException> getMigrationExceptions() {
        return migrationExceptions;
    }

    /**
     * setMigrationExceptions
     * @param migrationExceptions creates a List of Migration Exceptions  
     */
    public void setMigrationExceptions(List<MigrationException> migrationExceptions) {
        this.migrationExceptions = migrationExceptions;
    }

}
