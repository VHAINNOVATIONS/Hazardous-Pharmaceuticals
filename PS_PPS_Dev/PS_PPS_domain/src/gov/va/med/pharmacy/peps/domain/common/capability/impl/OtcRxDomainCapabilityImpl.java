/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;
import gov.va.med.pharmacy.peps.domain.common.capability.OtcRxDomainCapability;


/**
 * Perform CRUD operations on over the counter prescription domains
 */
public class OtcRxDomainCapabilityImpl implements OtcRxDomainCapability {

    /**
     * return the list of over the counter prescriptions
     * 
     * @return OtcRxVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.OtcRxDomainCapability#retrieveDomain()
     */
    public List<OtcRxVo> retrieveDomain() {
        ArrayList<OtcRxVo> options = new ArrayList<OtcRxVo>();
        
        OtcRxVo otc = new OtcRxVo();
        otc.setId(PPSConstants.OVER_THE_COUNTER.toLowerCase(Locale.US));
        otc.setValue(PPSConstants.OVER_THE_COUNTER);
    

        
        OtcRxVo rx = new OtcRxVo();
        rx.setId(PPSConstants.PRESCRIPTION.toLowerCase(Locale.US));
        rx.setValue(PPSConstants.PRESCRIPTION);
        
        options.add(otc);
        options.add(rx);
        
        return options;
    }

}
