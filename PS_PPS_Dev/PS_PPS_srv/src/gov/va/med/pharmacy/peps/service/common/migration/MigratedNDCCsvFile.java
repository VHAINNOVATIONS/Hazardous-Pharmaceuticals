/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * MigratedNDCCsvFiler
 *
 */
public class MigratedNDCCsvFile extends MigratedDomainBase {

    private ProcessDomainType processDomainType;

    private int manufacturersMigrated;
    private int packageTypesMigrated;

    /**
     * MigratedNDCCsvFile
     */
    public MigratedNDCCsvFile() {
        super();
        
        this.processDomainType = ProcessDomainType.NDC_CSV_FILE_ACTIVE;

    }

    /**
     * getFileNumber for MigratedNdcCsvFile
     * @return fileNumber
     */
    public String getFileNumber() {
        return processDomainType.getFileNumber();
    }

    /**
     * getName()  for MigratedNdcCsvFile
     * @return name
     */
    public String getName() {
        return processDomainType.getName().toString();
    }

    /**
     * getProcessDomainType for MigratedNdcCsvFile
     * @return processDomainType
     */
    @Override
    public ProcessDomainType getProcessDomainType() {
        return processDomainType;
    }

    /**
     * setProcessDomainType() for MigratedNdcCsvFile
     * @param pProcessDomainType pProcessDomainType
     */
    @Override
    public void setProcessDomainType(ProcessDomainType pProcessDomainType) {
        processDomainType = pProcessDomainType;
    }

    /**
     * getManufacturersMigrated
     * @return manufacturersMigrated
     */
    public int getManufacturersMigrated() {
        return manufacturersMigrated;
    }

    /**
     * setManufacturersMigrated
     * @param pManufacturersMigrated pManufacturersMigrated
     */
    public void setManufacturersMigrated(int pManufacturersMigrated) {
        manufacturersMigrated = pManufacturersMigrated;
    }

    /**
     * getPackageTypesMigrated
     * @return packageTypesMigrated
     */
    public int getPackageTypesMigrated() {
        return packageTypesMigrated;
    }

    /**
     * setPackageTypesMigrated
     * @param pPackageTypesMigrated pPackageTypesMigrated
     */
    public void setPackageTypesMigrated(int pPackageTypesMigrated) {
        this.packageTypesMigrated = pPackageTypesMigrated;
    }

}
