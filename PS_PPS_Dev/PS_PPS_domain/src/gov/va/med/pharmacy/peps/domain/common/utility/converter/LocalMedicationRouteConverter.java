/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LocalMedicationRouteVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PackageUsageVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalMedRouteDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPackageUseAssocDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPackageUseAssocDoKey;


/**
 * Convert to/from {@link LocalMedicationRouteVo} and {@link EplLocalMedRouteDo}.
 */
public class LocalMedicationRouteConverter extends Converter<LocalMedicationRouteVo, EplLocalMedRouteDo> {
    private StandardMedRouteConverter standardMedRouteConverter;

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplLocalMedRouteDo toDataObject(LocalMedicationRouteVo data) {
        EplLocalMedRouteDo route = new EplLocalMedRouteDo();

        if (data.getId() != null) {
            route.setEplId(Long.valueOf(data.getId()));
        }

        route.setRevisionNumber(Long.valueOf(data.getRevisionNumber()));
        route.setCreatedBy(data.getCreatedBy());
        route.setCreatedDtm(data.getCreatedDate());
        route.setLastModifiedBy(data.getModifiedBy());
        route.setLastModifiedDtm(data.getModifiedDate());

        route.setDisplayOnIvpIvpb(toYesOrNo(data.getDisplayOnIvp()));

        if (data.getStandardMedRoute() != null) {
            route.setEplStandardMedRoute(standardMedRouteConverter.convertMinimal(data.getStandardMedRoute()));
        }

        route.setIvFlag(toYesOrNo(data.getIvFlag()));
        route.setMedRouteAbbreviation(data.getAbbreviation());
        route.setMedRouteName(data.getValue());
        route.setMedRouteOutpatientExpansion(data.getOutpatientExpansion());
        route.setOtherLanguageExpansion(data.getOtherLanguageExpansion());
        route.setPromptForInjectionSite(toYesOrNo(data.getPromptForInjectionSite()));

        if (data.getItemStatus() != null) {
            route.setItemStatus(data.getItemStatus().toString());
        }

        if (data.getRequestItemStatus() != null) {
            route.setRequestStatus(data.getRequestItemStatus().toString());
        }

        route.setRejectReasonText(data.getRejectionReasonText());

        if (data.getInactivationDate() != null) {
            route.setInactivationDate(data.getInactivationDate());
        }

        if (data.getPackageUsages() != null && data.getPackageUsages().size() > 0) {
            Set<EplPackageUseAssocDo> assocs = new HashSet<EplPackageUseAssocDo>();

            for (PackageUsageVo useVo : data.getPackageUsages()) {
                EplPackageUseAssocDoKey key = new EplPackageUseAssocDoKey();
                key.setEplIdLocalMedRouteFk(route.getEplId());
                key.setEplIdPackageUseFk(new Long(useVo.getId()));

                EplPackageUseAssocDo assocDo = new EplPackageUseAssocDo();
                assocDo.setKey(key);

                assocs.add(assocDo);
            }

            route.setEplPackageUseAssocs(assocs);

        }

        return route;
    }

    /**
     * Minimally copies data from the given LocalMedicationRouteVo into a {@link DataObject}.
     * <p>
     * The returned {@link DataObject} likely only has the primary key data populated.
     * <p>
     * Default implementation calls {@link #toDataObject(ValueObject)}.
     * 
     * @param data LocalMedicationRouteVo to convert
     * @return minimally populated {@link DataObject}
     */
    @Override
    protected EplLocalMedRouteDo toMinimalDataObject(LocalMedicationRouteVo data) {
        EplLocalMedRouteDo route = new EplLocalMedRouteDo();
        route.setEplId(Long.valueOf(data.getId()));

        return route;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a LocalMedicationRouteVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated LocalMedicationRouteVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected LocalMedicationRouteVo toValueObject(EplLocalMedRouteDo data) {
        LocalMedicationRouteVo route = new LocalMedicationRouteVo();
        route.setId(String.valueOf(data.getEplId()));
        route.setAbbreviation(data.getMedRouteAbbreviation());
        route.setDisplayOnIvp(toBoolean(data.getDisplayOnIvpIvpb()));
        route.setIvFlag(toBoolean(data.getIvFlag()));
        route.setOtherLanguageExpansion(data.getOtherLanguageExpansion());
        route.setOutpatientExpansion(data.getMedRouteOutpatientExpansion());
        route.setPromptForInjectionSite(toBoolean(data.getPromptForInjectionSite()));
        route.setRevisionNumber(data.getRevisionNumber().longValue());
        route.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        route.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        route.setRejectionReasonText(data.getRejectReasonText());
        route.setValue(data.getMedRouteName());
        route.setCreatedBy(data.getCreatedBy());
        route.setCreatedDate(data.getCreatedDtm());
        route.setModifiedBy(data.getLastModifiedBy());
        route.setModifiedDate(data.getLastModifiedDtm());

        if (data.getEplStandardMedRoute() != null) {
            route.setStandardMedRoute(standardMedRouteConverter.convert(data.getEplStandardMedRoute()));
        }

        if (data.getInactivationDate() != null) {
            route.setInactivationDate(data.getInactivationDate());
        }

        if (data.getEplPackageUseAssocs() != null && data.getEplPackageUseAssocs().size() > 0) {
            Collection<PackageUsageVo> packages = new ArrayList<PackageUsageVo>();

            for (EplPackageUseAssocDo assocDo : data.getEplPackageUseAssocs()) {
                PackageUsageVo packageVo = new PackageUsageVo();
                packageVo.setId(assocDo.getKey().getEplIdPackageUseFk().toString());

                if (assocDo.getEplPackageUsage() != null) {
                    packageVo.setDescription(assocDo.getEplPackageUsage().getDescription());
                    packageVo.setValue(assocDo.getEplPackageUsage().getPackageUseCode());
                }

                packages.add(packageVo);
            }

            route.setPackageUsages(packages);
        }

        return route;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a LocalMedicationRouteVo.
     * <p>
     * The returned LocalMedicationRouteVo likely only has enough data for the {@link ValueObject#toShortString()} and
     * {@link ValueObject#getUniqueId()} methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the {@link ValueObject} in a drop-down or multi-select list
     * where a simple text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated {LocalMedicationRouteVo
     */
    @Override
    protected LocalMedicationRouteVo toMinimalValueObject(EplLocalMedRouteDo data) {
        LocalMedicationRouteVo route = new LocalMedicationRouteVo();

        route.setId(String.valueOf(data.getEplId()));
        route.setValue(data.getMedRouteName());
        route.setAbbreviation(data.getMedRouteAbbreviation());

        return route;
    }

    /**
     * setStandardMedRouteConverter.
     * @param standardMedRouteConverter standardMedRouteConverter property
     */
    public void setStandardMedRouteConverter(StandardMedRouteConverter standardMedRouteConverter) {
        this.standardMedRouteConverter = standardMedRouteConverter;
    }
}
