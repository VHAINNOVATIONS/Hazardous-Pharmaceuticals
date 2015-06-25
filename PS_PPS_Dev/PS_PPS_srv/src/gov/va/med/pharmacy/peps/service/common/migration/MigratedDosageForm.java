/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * 
 * MigratedDosageForm
 *
 */
public class MigratedDosageForm extends MigratedDomainBase {

    private ProcessDomainType processDomainType;

    /**
     * MigratedDosageForm
     */
    public MigratedDosageForm() {
        super();
        
        this.processDomainType = ProcessDomainType.DOSAGE_FORM_ACTIVE;
    }

    /**
     * getFileNumber
     * @return fileNumber
     */
    public String getFileNumber() {
        return processDomainType.getFileNumber();
    }

    /**
     * getName()
     * @return name
     */
    public String getName() {
        return processDomainType.getName().toString();
    }

    /**
     * getProcessDomainType() for MigratedDosageForm.
     * @return processDomainType
     */
    @Override
    public ProcessDomainType getProcessDomainType() {
        return processDomainType;
    }

    /**
     * setProcessDomainType  for MigratedDosageForm.
     * @param pProcessDomainType pProcessDomainType
     */
    @Override
    public void setProcessDomainType(ProcessDomainType pProcessDomainType) {
        processDomainType = pProcessDomainType;
    }

}
