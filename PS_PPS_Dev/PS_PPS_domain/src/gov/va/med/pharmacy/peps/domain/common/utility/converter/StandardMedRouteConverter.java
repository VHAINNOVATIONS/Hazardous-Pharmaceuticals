/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplStandardMedRouteDo;


/**
 * Convert to/from {@link StandardMedRouteVo} and {@link EplStandardMedRouteDo}.
 */
public class StandardMedRouteConverter extends Converter<StandardMedRouteVo, EplStandardMedRouteDo> {

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
    protected EplStandardMedRouteDo toDataObject(StandardMedRouteVo data) {
        EplStandardMedRouteDo route = new EplStandardMedRouteDo();

        route.setStandardMedRouteName(data.getValue());
        route.setEplId(Long.valueOf(data.getId()));
        route.setVuid(data.getVuid());
        
        if (data.isMasterEntryForVuid()) {
            route.setMasterEntryForVuid("1");
        } else {
            route.setMasterEntryForVuid("0");
        }

        route.setRevisionNumber(Long.valueOf(data.getRevisionNumber()));

        route.setCreatedBy(data.getCreatedBy());
        route.setCreatedDtm(data.getCreatedDate());
        route.setLastModifiedBy(data.getModifiedBy());
        route.setLastModifiedDtm(data.getModifiedDate());

        if (data.getReplacementStandardMedRoute() != null) {
            EplStandardMedRouteDo parent = new EplStandardMedRouteDo();
            parent.setEplId(Long.valueOf(data.getReplacementStandardMedRoute().getId()));

            route.setEplStandardMedRoute(parent);
        }

        if (data.getItemStatus() != null) {
            route.setItemStatus(data.getItemStatus().name());
        }

        if (data.getRequestItemStatus() != null) {
            route.setRequestStatus(data.getRequestItemStatus().name());
        }

        route.setRejectReasonText(data.getRejectionReasonText());
        route.setFirstDatabankMedRoute(data.getFirstDatabankMedRoute());

        if (data.getInactivationDate() != null) {
            route.setInactivationDate(data.getInactivationDate());
        }

        return route;
    }

    /**
     * Minimally copies data from the given {@link ValueObject} into a {@link DataObject}.
     * <p>
     * The returned {@link DataObject} likely only has the primary key data populated.
     * <p>
     * Default implementation calls {@link #toDataObject(ValueObject)}.
     * 
     * @param data {@link ValueObject} to convert
     * @return minimally populated {@link DataObject}
     */
    @Override
    protected EplStandardMedRouteDo toMinimalDataObject(StandardMedRouteVo data) {
        EplStandardMedRouteDo route = new EplStandardMedRouteDo();

        if (data.getId() != null && data.getId().length() > 0) {
            route.setEplId(Long.valueOf(data.getId()));
        }

        return route;
    }
    
    /**
     * Fully copies data from the given {@link DataObject} into a StandardMedRouteVo.
     * <p>
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated StandardMedRouteVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected StandardMedRouteVo toValueObject(EplStandardMedRouteDo data) {
        StandardMedRouteVo route = new StandardMedRouteVo();

        route.setId(String.valueOf(data.getEplId()));
        route.setRevisionNumber(data.getRevisionNumber().longValue());
        route.setValue(data.getStandardMedRouteName());
        route.setVuid(data.getVuid());
        
        if ("1".equals(data.getMasterEntryForVuid())) {
            route.setMasterEntryForVuid(true);
        } else {
            route.setMasterEntryForVuid(false);
        }

        
        route.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        route.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        route.setRejectionReasonText(data.getRejectReasonText());
        route.setFirstDatabankMedRoute(data.getFirstDatabankMedRoute());
        route.setCreatedBy(data.getCreatedBy());
        route.setCreatedDate(data.getCreatedDtm());
        route.setModifiedBy(data.getLastModifiedBy());
        route.setModifiedDate(data.getLastModifiedDtm());

        if (data.getEplStandardMedRoute() != null) {
            StandardMedRouteVo parent = new StandardMedRouteVo();
            parent.setId(data.getEplStandardMedRoute().getEplId().toString());
            parent.setValue(data.getEplStandardMedRoute().getStandardMedRouteName());
            route.setReplacementStandardMedRoute(parent);
        }

        route.setInactivationDate(data.getInactivationDate());

        return route;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a StandardMedRouteVo.
     * <p>
     * The returned StandardMedRouteVo likely only has enough data for the {@link ValueObject#toShortString()} and
     * {@link ValueObject#getUniqueId()} methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the {@link ValueObject} in a drop-down or multi-select list
     * where a simple text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data {@link DataObject} to convert
     * @return minimally populated StandardMedRouteVo
     */
    @Override
    protected StandardMedRouteVo toMinimalValueObject(EplStandardMedRouteDo data) {
        StandardMedRouteVo route = new StandardMedRouteVo();
        route.setId(String.valueOf(data.getEplId()));
        route.setValue(data.getStandardMedRouteName());

        return route;
    }
}
