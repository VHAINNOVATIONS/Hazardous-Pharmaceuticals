/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.quickaction;


import gov.va.med.pharmacy.peps.external.common.vo.outbound.quickaction.drugfiletoexternalinterface.DrugFileToExternalInterface;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.quickaction.drugfiletoexternalinterface.ObjectFactory;


/**
 * Convert quick action.
 */
public class DrugFileToExternalInterfaceConverter {
    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Constructor
     *
     */
    private DrugFileToExternalInterfaceConverter() {
        super();
    }

    /**
     * Convert quick action to XML document.
     * 
     * @return XML document
     */
    public static DrugFileToExternalInterface toDrugFileToExternalInterface() {
        DrugFileToExternalInterface drugDataToExternalInterface = FACTORY.createDrugFileToExternalInterface();

        drugDataToExternalInterface.setSendDrugFileToExternalInterface(FACTORY
            .createDrugFileToExternalInterfaceSendDrugFileToExternalInterface());


        return drugDataToExternalInterface;
    }
}
