/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.NdfSynchQueueVo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdfSynchQueueDo;


/**
 * Convert to/from {@link IngredientVo} and {@link EplIngredientDo}.
 */
public class NdfSynchQueueConverter extends Converter<NdfSynchQueueVo, EplNdfSynchQueueDo> {

    /**
     * Standard constructor
     */
    public NdfSynchQueueConverter() {
    }

    /**
     * Fully copies data from the given NdfSynchQueueVo into a
     * {@link DataObject}.
     * 
     * @param data
     *            NdfSynchQueueVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplNdfSynchQueueDo toDataObject(NdfSynchQueueVo data) {
        EplNdfSynchQueueDo synchDo = new EplNdfSynchQueueDo();

        synchDo.setId(Long.valueOf(data.getId()));
        synchDo.setIdFk(Long.valueOf(data.getIdFk()));
        synchDo.setActionType(data.getActionType());
        synchDo.setItemType(data.getItemType());

        return synchDo;
    }

    /**
     * Copies the synch date object to a value object.
     * @param data the synch data object
     * @return fully populated ValueObject
     */
    @Override
    protected NdfSynchQueueVo toValueObject(EplNdfSynchQueueDo data) {
        NdfSynchQueueVo synchVo = new NdfSynchQueueVo();

        synchVo.setId(data.getId().toString());
        synchVo.setIdFk(data.getIdFk().toString());
        synchVo.setItemType(data.getItemType());
        synchVo.setActionType(data.getActionType());

        return synchVo;
    }
}
