/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * MigratedIngredients
 *
 */
public class MigratedIngredients extends MigratedDomainBase {

    private ProcessDomainType processDomainType;

    /**
     * MigratedIngredients
     */
    public MigratedIngredients() {
        super();
        
        this.processDomainType = ProcessDomainType.DRUG_INGREDIENTS_ACTIVE;

    }

    /**
     * getFileNumber
     * @return fileNumber
     */
    public String getFileNumber() {
        return processDomainType.getFileNumber();
    }

    /**
     * getName
     * @return name
     */
    public String getName() {
        return processDomainType.getName().toString();
    }

    /**
     * getProcessDomaintype
     * @return ProcessDomainType
     */
    @Override
    public ProcessDomainType getProcessDomainType() {
        return processDomainType;
    }

    /**
     * setProcessDomainType
     * @param pProcessDomainType pProcessDomainType
     */
    @Override
    public void setProcessDomainType(ProcessDomainType pProcessDomainType) {
        processDomainType = pProcessDomainType;
    }
}
