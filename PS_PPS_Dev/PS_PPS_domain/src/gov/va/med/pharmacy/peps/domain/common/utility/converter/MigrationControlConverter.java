/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.MigrationControlVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplMigrationControlDo;


/**
 * Convert to/from MigrationControlVo and {@link EplMigrationControlDo}.
 */
public class MigrationControlConverter extends
        Converter<MigrationControlVo, EplMigrationControlDo> {

    /**
     * Fully copies data from the given MigrationControlVo into a
     * {@link DataObject}.
     * 
     * @param data MigrationControlVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplMigrationControlDo toDataObject(MigrationControlVo data) {
        EplMigrationControlDo unit = new EplMigrationControlDo();

        if (data.getEplId() != null) {
            unit.setEplId(data.getEplId());
        }

        if (data.getUserName() != null) {
            unit.setUserName(data.getUserName().toString());
        }

        unit.setStartDtm(data.getStartDateTime());
        unit.setStopDtm(data.getStopDateTime());
        unit.setThreadId(data.getThreadId());

        if (data.getMigrationStatus() != null) {
            unit.setMigrationStatus(data.getMigrationStatus().toString());
        }

        if (data.getUserNdcFile() != null) {
            unit.setUserNdcFile(data.getUserNdcFile().toString());
        }

        if (data.getUserOiFile() != null) {
            unit.setUserOiFile(data.getUserOiFile().toString());
        }

        if (data.getUserProductFile() != null) {
            unit.setUserProductFile(data.getUserProductFile().toString());
        }

        return unit;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * MigrationControlVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link MigrationControlVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return fully populated MigrationControlVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected MigrationControlVo toValueObject(EplMigrationControlDo data) {
        MigrationControlVo unit = new MigrationControlVo();

        unit.setUserName(data.getUserName().toString());
        unit.setStartDateTime(data.getStartDtm());
        unit.setStopDateTime(data.getStopDtm());
        unit.setThreadId(data.getThreadId());
        unit.setMigrationStatus(data.getMigrationStatus());
        unit.setUserNdcFile(data.getUserNdcFile());
        unit.setUserOiFile(data.getUserOiFile());
        unit.setUserProductFile(data.getUserProductFile());
        
        return unit;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a
     * MigrationControlVo.
     * <p>
     * The returned {@link MigrationControlVo} likely only has enough data for the
     * {@link ValueObject#toShortString()} and {@link ValueObject#getUniqueId()}
     * methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the
     * {@link MigrationControlVo} in a drop-down or multi-select list where a simple
     * text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated MigrationControlVo
     */
    @Override
    protected MigrationControlVo toMinimalValueObject(EplMigrationControlDo data) {
        MigrationControlVo unit = new MigrationControlVo();

        unit.setStartDateTime(data.getStartDtm());
        unit.setThreadId(data.getThreadId());
        unit.setMigrationStatus(data.getMigrationStatus());

        return unit;
    }
}
