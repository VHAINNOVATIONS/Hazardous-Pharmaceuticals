/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.AtcCanisterVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.WardGroupForAtcVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplAtcCanisterDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;


/**
 * Convert to/from {@link AtcCanisterVo} and {@link EplAtcCanisterDo}.
 */
public class AtcCanisterConverter extends AssociationConverter<AtcCanisterVo, EplAtcCanisterDo, EplProductDo> {

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
    protected EplAtcCanisterDo toDataObject(AtcCanisterVo data) {
        EplAtcCanisterDo atcCanister = new EplAtcCanisterDo();
        atcCanister.setWardGroupForAtc(data.getWardGroupForAtc().getValue());
        atcCanister.setAtcCanister(data.getAtcCanister());

        return atcCanister;
    }

    /**
     * Fully copies data from the given AtcCanisterVo into a EplAtcCanisterDo.
     * <p>
     * Implementations should populate the data with a call to AtcCanisterVo and then populate the parent
     * data into the association.
     * <p>
     * The parentEplAtcCanisterDo must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data {@link ValueObject} to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @param sequence ordinal location of the current {@link ValueObject} to convert, used only if order matters in this
     *            association
     * @return fully populated {@link DataObject}
     */
    protected EplAtcCanisterDo toDataObject(AtcCanisterVo data, EplProductDo parent, int sequence) {
        EplAtcCanisterDo atcCanister = convert(data);
        atcCanister.setEplProduct(parent);

        return atcCanister;
    }

    /**
     * Fully copies data from the given EplAtcCanisterDo into a AtcCanisterVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated AtcCanisterVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected AtcCanisterVo toValueObject(EplAtcCanisterDo data) {
        AtcCanisterVo atcCanister = new AtcCanisterVo();
        atcCanister.setAtcCanister(data.getAtcCanister());
        atcCanister.setId(data.getId());

        WardGroupForAtcVo wardGroupForAtc = new WardGroupForAtcVo();
        wardGroupForAtc.setValue(data.getWardGroupForAtc());
        atcCanister.setWardGroupForAtc(wardGroupForAtc);

        return atcCanister;
    }
}
