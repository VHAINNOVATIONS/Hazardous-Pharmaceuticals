/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import gov.va.med.pharmacy.peps.external.common.drugdatavendor.outbound.capability.DrugInfoCapability;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DoseRouteVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DoseTypeVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugInfoResultsVo;
import gov.va.med.pharmacy.peps.external.common.vo.ordercheck.DrugInfoVo;

import firstdatabank.database.FDBException;
import firstdatabank.dif.DispensableDrug;
import firstdatabank.dif.DoseRoutes;
import firstdatabank.dif.DoseTypes;
import firstdatabank.dif.FDBDispensableDrugLoadType;
import firstdatabank.dif.FDBDrugType;
import firstdatabank.dif.ScreenDrug;


/**
 * Lookup the dose routes and types for the given drugs.
 * 
 * This class is abstract so that Spring can provide lookup method injection to give new instances of ScreenDrugs and
 * ScreenDrug FDB objects.
 * 
 * @see http://static.springframework.org/spring/docs/2.0.x/reference/beans.html#beans-factory-lookup-method-injection
 */
public abstract class DrugInfoCapabilityImpl implements DrugInfoCapability {

    /**
     * Lookup the dose routes and types for the given drugs.
     * 
     * @param drugs Collection of DrugInfoVo
     * @return DrugInfoResultsVo containing drugs not found and drugs with does routes and types
     */
    public DrugInfoResultsVo processDrugInfoRequest(Collection<DrugInfoVo> drugs) {
        DrugInfoResultsVo results = new DrugInfoResultsVo();

        for (DrugInfoVo drugInfoVo : drugs) {
            try {
                ScreenDrug screenDrug = newScreenDrugInstance();
                screenDrug.load(drugInfoVo.getGcnSeqNo(), FDBDrugType.fdbDTGCNSeqNo);

                drugInfoVo.setDoseRoutes(convertDoseRoutes(screenDrug.getDoseRoutes()));
                drugInfoVo.setDoseTypes(convertDoseTypes(screenDrug.getDoseTypes()));

                DispensableDrug dispensableDrug = newDispensableDrugInstance();
                dispensableDrug.load(Long.valueOf(drugInfoVo.getGcnSeqNo()), FDBDispensableDrugLoadType.fdbDDLTGCNSeqNo, "",
                    "", "");

                // Strength Units are lower case, but data from database in dose checks are upper case
                String strengthUnit = dispensableDrug.getStrengthUnit();

                if (strengthUnit != null) {
                    strengthUnit = strengthUnit.toUpperCase(Locale.US);
                }

                drugInfoVo.setStrengthUnit(strengthUnit);

                results.getDrugs().add(drugInfoVo);
            } catch (FDBException e) {
                results.getDrugsNotFound().add(drugInfoVo);
            }
        }

        return results;
    }

    /**
     * Convert the FDB DoseTypes type to a collection of strings, representing the description of types.
     * 
     * @param doseTypes DoseTypes FDB object
     * @return Collection of DoseTypeVo each the description of a FDB DoseRoute
     */
    private Collection<DoseTypeVo> convertDoseTypes(DoseTypes doseTypes) {
        Collection<DoseTypeVo> types = new ArrayList<DoseTypeVo>(doseTypes.count());

        for (int i = 0; i < doseTypes.count(); i++) {
            DoseTypeVo doseType = new DoseTypeVo();
            doseType.setId(doseTypes.item(i).getID());
            doseType.setName(doseTypes.item(i).getDescription());
            types.add(doseType);
        }

        return types;
    }

    /**
     * Convert the FDB DoseRoutes type to a collection of strings, representing the description of routes.
     * 
     * @param doseRoutes DoseRoutes FDB object
     * @return Collection of DoseRouteVo
     */
    private Collection<DoseRouteVo> convertDoseRoutes(DoseRoutes doseRoutes) {
        Collection<DoseRouteVo> routes = new ArrayList<DoseRouteVo>(doseRoutes.count());

        for (int i = 0; i < doseRoutes.count(); i++) {
            DoseRouteVo doseRoute = new DoseRouteVo();
            doseRoute.setId(doseRoutes.item(i).getID());
            doseRoute.setName(doseRoutes.item(i).getDescription());
            routes.add(doseRoute);
        }

        return routes;
    }

    /**
     * Spring overridden method through lookup method injection. Spring will provide a new instance of ScreenDrug each time
     * this is called, because the FdbScreenDrug Spring bean is defined with a scope of "prototype".
     * 
     * @see http://static.springframework.org/spring/docs/2.0.x/reference/beans.html#beans-factory-lookup-method-injection
     * 
     * @return new instance of ScreenDrug
     */
    protected abstract ScreenDrug newScreenDrugInstance();

    /**
     * Spring overridden method through lookup method injection. Spring will provide a new instance of DispensableDrug each
     * time this is called, because the FdbDispensableDrug Spring bean is defined with a scope of "prototype".
     * 
     * @see http://static.springframework.org/spring/docs/2.0.x/reference/beans.html#beans-factory-lookup-method-injection
     * 
     * @return new instance of DispensableDrug
     */
    protected abstract DispensableDrug newDispensableDrugInstance();
}
