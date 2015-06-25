/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;


/**
 * Generate {@link LocalMedicationRouteVo}
 */
public class LocalMedicationRouteGenerator extends VoGenerator<LocalMedicationRouteVo> {

    /**
     * generate
     * @return List of {@link LocalMedicationRouteVo}
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    @Override
    protected List<LocalMedicationRouteVo> generate() {
        List<LocalMedicationRouteVo> routes = new ArrayList<LocalMedicationRouteVo>();

        for (int i = 0; i < I_5; i++) {
            routes.add(generateLocalMedicationRoute());
        }

        return routes;
    }

    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
    
    /**
     * Generate pseudo-random {@link LocalMedicationRouteVo}.
     * 
     * @return {@link LocalMedicationRouteVo}
     */
    private LocalMedicationRouteVo generateLocalMedicationRoute() {
        LocalMedicationRouteVo route = new LocalMedicationRouteVo();
        route.setInactivationDate(new Date());
        route.setValue(RandomGenerator.nextAlphabetic(I_10));
        route.setItemStatus(ItemStatus.INACTIVE);
        route.setRequestItemStatus(RequestItemStatus.PENDING);
        route.setRejectionReasonText(RandomGenerator.nextAlphabetic(I_10));
        route.setRevisionNumber(I_3);
        route.setAbbreviation(RandomGenerator.nextAlphabetic(I_3));
        route.setIvFlag(true);
        route.setPromptForInjectionSite(true);
        route.setDisplayOnIvp(true);
        route.setOtherLanguageExpansion(RandomGenerator.nextAlphabetic(I_10));
        route.setOutpatientExpansion(RandomGenerator.nextAlphabetic(I_10));
        route.setId(RandomGenerator.nextString(I_10));

        return route;
    }
}
