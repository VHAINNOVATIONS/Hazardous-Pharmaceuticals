/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * MigratedVaProduct
 *
 */
public class MigratedVaProduct extends MigratedDomainBase {

    private ProcessDomainType processDomainType;

    /**
     * MigratedVaProduct
     */
    public MigratedVaProduct() {
        super();
        
        this.processDomainType = ProcessDomainType.VA_PRODUCT_ACTIVE;

    }

    /**
     * getFileNumber for MigratedVaProduct
     * @return fileNumber
     */
    public String getFileNumber() {
        return processDomainType.getFileNumber();
    }

    /**
     * getName for MigratedVaProduct
     * @return name
     */
    public String getName() {
        return processDomainType.getName().toString();
    }

    /**
     * getProcessDomaintype for MigratedVaProduct
     * @return processDomainType
     */
    @Override
    public ProcessDomainType getProcessDomainType() {
        return processDomainType;
    }

    /**
     * setProcessDomainType for MigratedVaProduct.
     * @param pProcessDomainType pProcessDomainType
     */
    @Override
    public void setProcessDomainType(ProcessDomainType pProcessDomainType) {
        processDomainType = pProcessDomainType;
    }

}
