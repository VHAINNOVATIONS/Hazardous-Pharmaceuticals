/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;


/**
 * This class is used to generate a StandardMedicationRouteVo
 */
public class StandardMedicationRouteGenerator extends VoGenerator<StandardMedRouteVo> {

    /**
     * this method generates a list of StandardMedicationRouteVos
     * 
     * @return List<StandardMedicationRouteVo>
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    protected List<StandardMedRouteVo> generate() {
        List<StandardMedRouteVo> routes = new ArrayList<StandardMedRouteVo>();

        for (int i = 0; i < I_5; i++) {
            routes.add(generateStandardMedicationRoute());
        }

        return routes;
    }

    /**
     * doInitialization in the StandardMedicationRouteGenerator.
     */
    @Override
    protected void doInitialization() {
        
    }
    
    /**
     * This methods generates a single StandardMedicationRouteVo
     * 
     * @return StandardMedicationRouteVo
     */
    private StandardMedRouteVo generateStandardMedicationRoute() {

        StandardMedRouteVo route = new StandardMedRouteVo();

        route.setValue("NAME");
        route.setFirstDatabankMedRoute("FIRST DATABANK MED ROUTE");
        route.setVuid("VUID");

        return route;

    }
}
