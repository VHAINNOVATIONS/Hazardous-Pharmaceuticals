/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * MigratedOrderableItemsCsv
 *
 */
public class MigratedOrderableItemsCsv extends MigratedDomainBase {

    private ProcessDomainType processDomainType;

    /**
     * MigratedOrderableItemsCsv
     */
    public MigratedOrderableItemsCsv() {
        super();
        
        this.processDomainType = ProcessDomainType.ORDERABLE_ITEMS_CSV_FILE_ACTIVE;

    }

    /**
     * getFileNumber() {
     * @return processDomainType.getFileNumber();
     */
    public String getFileNumber() {
        return processDomainType.getFileNumber();
    }

    /**
     * getName for MigratedOrderableitemsCsv.
     * @return name
     */
    public String getName() {
        return processDomainType.getName().toString();
    }

    /**
     * getProcessDomainType for MigratedOrderableitemsCsv.
     * @return processDomainType
     */
    @Override
    public ProcessDomainType getProcessDomainType() {
        return processDomainType;
    }

    /**
     * setProcessDomainType for MigratedOrderableitemsCsv.
     * @param pProcessDomainType pProcessDomainType
     */
    @Override
    public void setProcessDomainType(ProcessDomainType pProcessDomainType) {
        processDomainType = pProcessDomainType;
    }

}
