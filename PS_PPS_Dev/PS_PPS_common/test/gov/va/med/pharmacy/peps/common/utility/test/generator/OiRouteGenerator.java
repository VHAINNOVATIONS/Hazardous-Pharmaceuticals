/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.OiRouteVo;


/**
 * Generate List of {@link OiRouteVo}
 */
public class OiRouteGenerator extends VoGenerator<OiRouteVo> {

    /**
     * Generate List of {@link OiRouteVo}.
     * <p>
     * Use the {@link LocalMedicationRouteGenerator} to generate list of routes.
     * 
     * @return List of {@link OiRouteVo}
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    @Override
    protected List<OiRouteVo> generate() {
        List<LocalMedicationRouteVo> localMedicationRoutes = new LocalMedicationRouteGenerator().getList();

        List<OiRouteVo> oiRoutes = new ArrayList<OiRouteVo>(localMedicationRoutes.size());
        
        for (LocalMedicationRouteVo localMedicationRoute : localMedicationRoutes) {
            OiRouteVo oiRoute = new OiRouteVo();
            oiRoute.setLocalMedicationRoute(localMedicationRoute);
            oiRoutes.add(oiRoute);
        }
        
        oiRoutes.get(0).setDefaultRoute(true);
        
        return oiRoutes;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
}
