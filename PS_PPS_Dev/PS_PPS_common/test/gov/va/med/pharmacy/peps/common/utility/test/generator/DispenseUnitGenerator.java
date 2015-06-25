/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;




/**
 * Generate DispenseUnitVo
 */
public class DispenseUnitGenerator extends VoGenerator<DispenseUnitVo> {

    /**
     * Generate a list of DispenseUnitVo
     * 
     * @return List of DispenseUnitVo
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    @Override
    protected List<DispenseUnitVo> generate() {
        List<DispenseUnitVo> dispenseUnits = new ArrayList<DispenseUnitVo>();

        dispenseUnits.add(pseudoRandom());
        dispenseUnits.add(pseudoRandom());

        return dispenseUnits;
    }

    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
    
    /**
     * Generate a pseudo-random (not all fields are actually random) NdcVo.
     * 
     * @return NdcVo
     */
    public DispenseUnitVo pseudoRandom() {
        
        DispenseUnitVo dataVo = new DispenseUnitVo();
        dataVo.setValue(("du" + UUID.randomUUID()).substring(0, I_9));
        dataVo.setInactivationDate(new Date());
        dataVo.setRejectionReasonText("rjrected");
        dataVo.setRevisionNumber(I_3);
        dataVo.setRequestItemStatus(RequestItemStatus.values()[RandomGenerator.nextInt(RequestItemStatus.values().length)]);
        dataVo.setItemStatus(ItemStatus.values()[RandomGenerator.nextInt(ItemStatus.values().length)]);

        return dataVo;
    }
}
