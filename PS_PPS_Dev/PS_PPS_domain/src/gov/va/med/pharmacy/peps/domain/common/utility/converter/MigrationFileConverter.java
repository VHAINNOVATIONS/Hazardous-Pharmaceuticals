/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.MigrationFileVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplMigrationControlDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplMigrationFileDo;


/**
 * Convert to/from {@link MigrationFileVo} and {@link EplMigrationControlDo}.
 */
public class MigrationFileConverter extends
        Converter<MigrationFileVo, EplMigrationFileDo> {

    /**
     * Fully copies data from the given {@link MigrationFileVo} into a
     * {@link DataObject}.
     * 
     * @param data
     *            {@link MigrationFileVo} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplMigrationFileDo toDataObject(MigrationFileVo data) {
        EplMigrationFileDo unit = new EplMigrationFileDo();

        if (!StringUtils.isBlank(data.getFileId())) {
            unit.setFileId(new Double(data.getFileId()));
        }

        if (!StringUtils.isBlank(data.getMigrationFileName())) {
            unit.setMigrationFileName(data.getMigrationFileName());
        }

        if (data.getRowsProcessedQty() != null) {
            unit.setRowsProcessedQty(new Integer(data.getRowsProcessedQty()));
        }

        if (data.getRowsMigratedQty() != null) {
            unit.setRowsMigratedQty(new Integer(data.getRowsMigratedQty()));
        }

        if (data.getRowsNotMigratedQty() != null) {
            unit.setRowsNotMigratedQty(new Integer(data.getRowsNotMigratedQty()));
        }

        if (data.getErrorQty() != null) {
            unit.setErrorQty(new Integer(data.getErrorQty()));
        }
        
        return unit;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * {@link MigrationFileVo}.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return fully populated {@link MigrationFileVo}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected MigrationFileVo toValueObject(EplMigrationFileDo data) {
        MigrationFileVo unit = new MigrationFileVo();

        unit.setFileId(Double.toString(data.getFileId().doubleValue()));
        unit.setMigrationFileName(data.getMigrationFileName());
        unit.setRowsProcessedQty(data.getRowsProcessedQty());
        unit.setRowsMigratedQty(data.getRowsMigratedQty());
        unit.setRowsNotMigratedQty(data.getRowsNotMigratedQty());
        unit.setErrorQty(data.getErrorQty());

        return unit;
    }

    /**
     * Minimally copies data from the given {@link DataObject} into a
     * {@link MigrationFileVo}.
     * <p>
     * The returned {@link MigrationFileVo} likely only has enough data for the
     * {@link ValueObject#toShortString()} and {@link ValueObject#getUniqueId()}
     * methods to be called without getting nulls.
     * <p>
     * This method is only intended to be called for displaying the
     * {@link MigrationFileVo} in a drop-down or multi-select list where a simple
     * text value is displayed and the ID is sent back to the server.
     * <p>
     * Default implementation calls {@link #toValueObject(DataObject)}.
     * 
     * @param data
     *            {@link DataObject} to convert
     * @return minimally populated {@link MigrationFileVo}
     */
    @Override
    protected MigrationFileVo toMinimalValueObject(EplMigrationFileDo data) {
        MigrationFileVo unit = new MigrationFileVo();

        unit.setFileId(String.valueOf(data.getFileId()));
        unit.setMigrationFileName(data.getMigrationFileName());

        return unit;
    }
}
