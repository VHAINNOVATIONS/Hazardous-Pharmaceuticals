/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.AdministrationScheduleVo;


/**
 * Utility class to generate an AdministrationScheduleVo for testing
 */
public class AdministrationScheduleGenerator extends VoGenerator<AdministrationScheduleVo> {

    /**
     * Generates a list of AdministrationScheduleVo
     * 
     * @return List of AdministrationScheduleVos
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    protected List<AdministrationScheduleVo> generate() {
        
        List<AdministrationScheduleVo> administrationScheduleVos = new ArrayList<AdministrationScheduleVo>();

        AdministrationScheduleVo administrationScheduleVo = new AdministrationScheduleVo();
        administrationScheduleVo.setScheduleName("schedule 1");
        administrationScheduleVo.setPackagePrefix("IP");
        administrationScheduleVos.add(administrationScheduleVo);

        administrationScheduleVo = new AdministrationScheduleVo();
        administrationScheduleVo.setScheduleName("schedule 2");
        administrationScheduleVo.setPackagePrefix("IP");
        administrationScheduleVos.add(administrationScheduleVo);

        return administrationScheduleVos;
        
    }

    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
