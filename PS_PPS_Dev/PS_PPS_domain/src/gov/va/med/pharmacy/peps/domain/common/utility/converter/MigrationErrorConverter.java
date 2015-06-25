/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationControlVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationErrorVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplMigrationControlDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplMigrationErrorDo;


/**
 * Convert to/from {@link MigrationControlVo} and {@link EplMigrationControlDo}.
 */
public class MigrationErrorConverter extends
        Converter<MigrationErrorVo, EplMigrationErrorDo> {

    /**
     * Fully copies data from the given {@link ValueObject} into a
     * {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplMigrationErrorDo toDataObject(MigrationErrorVo data) {
        EplMigrationErrorDo unit = new EplMigrationErrorDo();

        if (!StringUtils.isBlank(data.getFileId())) {
            try {
                unit.setFileId(Double.parseDouble(data.getFileId()));
            } catch (NumberFormatException e) {
                return null;
            }
        }

        if (!StringUtils.isBlank(data.getMigrationUniqueName())) {
            unit.setMigrationUniqueValue(data.getMigrationUniqueName());
        }

        if (!StringUtils.isBlank(data.getMigrationRowId())) {
            unit.setMigrationRowId(data.getMigrationRowId());
        }

        if (!StringUtils.isBlank(data.getMigrationMultiRowId())) {
            unit.setMigrationMultRowId(data.getMigrationMultiRowId());
        }

        if (!StringUtils.isBlank(data.getMigrationFieldValue())) {
            unit.setMigrationFieldValue(data.getMigrationFieldValue());
        }

        if (!StringUtils.isBlank(data.getMigrationFieldName())) {
            unit.setMigrationFieldName(data.getMigrationFieldName());
        }

        if (!StringUtils.isBlank(data.getMigrationMultiFieldName())) {
            unit.setMigrationMultFieldName(data.getMigrationMultiFieldName());
        }

        unit.setDetailedErrorText(data.getDetailedErrorText());
        unit.setProcessedDtm(data.getProcessedDTM());

        return unit;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * MigrationErrorVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return fully populated MigrationErrorVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected MigrationErrorVo toValueObject(EplMigrationErrorDo data) {
        MigrationErrorVo unit = new MigrationErrorVo();

        unit.setFileId(data.getFileId().toString());
        unit.setMigrationUniqueName(data.getMigrationUniqueValue());
        unit.setMigrationRowId(data.getMigrationRowId());
        unit.setMigrationMultiRowId(data.getMigrationMultRowId());
        unit.setMigrationFieldValue(data.getMigrationFieldValue());
        unit.setMigrationFieldName(data.getMigrationFieldName());
        unit.setMigrationMultiFieldName(data.getMigrationMultFieldName());
        unit.setDetailedErrorText(data.getDetailedErrorText());
        unit.setProcessedDTM(data.getProcessedDtm());

        return unit;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a
     * MigrationErrorVo.
     * <p>
     * The returned {@link MigrationErrorVo} likely only has enough data for the
     * {@link ValueObject#toShortString()} and {@link ValueObject#getUniqueId()}
     * methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the
     * {@link MigrationErrorVo} in a drop-down or multi-select list where a simple
     * text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated MigrationErrorVo
     */
    @Override
    protected MigrationErrorVo toMinimalValueObject(EplMigrationErrorDo data) {
        MigrationErrorVo unit = new MigrationErrorVo();

        unit.setFileId(data.getFileId().toString());
        unit.setDetailedErrorText(data.getDetailedErrorText());

        return unit;
    }
}
