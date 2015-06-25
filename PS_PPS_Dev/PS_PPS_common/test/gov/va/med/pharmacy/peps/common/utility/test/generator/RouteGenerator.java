/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;


/**
 * Generate a list of Routes to use in test cases
 */
public class RouteGenerator extends VoGenerator<StandardMedRouteVo> {

    /**
     * Generate a list of Routes
     * 
     * @return List of Routes
     */
    protected List<StandardMedRouteVo> generate() {
        List<StandardMedRouteVo> routeVos = new ArrayList<StandardMedRouteVo>();

        // 1
        StandardMedRouteVo route = new StandardMedRouteVo();
        route.setId("DT");
        route.setValue("Dental");
        routeVos.add(route);

        // 2
        route = new StandardMedRouteVo();
        route.setId("RE");
        route.setValue("RECTAL");
        routeVos.add(route);

        // 3
        route = new StandardMedRouteVo();
        route.setId("IN");
        route.setValue("INJ");
        routeVos.add(route);

        return routeVos;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
