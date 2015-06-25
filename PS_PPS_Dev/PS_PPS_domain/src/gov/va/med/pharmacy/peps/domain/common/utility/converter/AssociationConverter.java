/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;


/**
 * Convert association tables to/from {@link ValueObject} and {@link DataObject}.
 * <p>
 * This class extends {@link Converter}, adding methods specific to converting association tables where the parent item's
 * data is also required for conversion.
 * 
 * @param <VO> {@link ValueObject} representing the association
 * @param <DO> {@link DataObject} representing the association table
 * @param <PO> {@link DataObject} representing the parent item in the association
 */
public abstract class AssociationConverter<VO extends ValueObject, DO extends DataObject, PO extends DataObject> extends
    Converter<VO, DO> {

    /**
     * Fully copies data from the given Collection of {@link ValueObject} into a Set of {@link DataObject}.
     * <p>
     * The parent {@link DataObject} must have the minimal amount of data required already set prior to calling this method.
     * <p>
     * The default implementation should be sufficient for most scenarios, but sub classes can override this method if
     * necessary.
     * 
     * @param data Collection of {@link ValueObject} to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    public Set<DO> convert(Collection<VO> data, PO parent) {
        Set<DO> values = new HashSet<DO>();

        if (data != null) {
            int sequence = 0;
            Iterator<VO> iterator = data.iterator();

            while (iterator.hasNext()) {
                VO current = iterator.next();
                DO value = toDataObject(current, parent, sequence);

                if (value != null) {
                    value.setMinimallyPopulated(false);
                    values.add(value);
                    sequence++;
                }
            }
        }

        return values;
    }

    /**
     * Fully copies data from the given {@link ValueObject} into a {@link DataObject}.
     * <p>
     * The parent {@link DataObject} must have the minimal amount of data required already set prior to calling this method.
     * <p>
     * The default implementation should be sufficient for most scenarios, but sub classes can override this method if
     * necessary.
     * <p>
     * Since this method only converts one {@link ValueObject} into one {@link DataObject}, the
     * {@link #toDataObject(ValueObject, DataObject, int)} sequence number parameters is set to zero.
     * 
     * @param data Collection of {@link ValueObject} to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    public DO convert(VO data, PO parent) {
        DO value = null;

        if (data != null) {
            value = toDataObject(data, parent, 0);

            if (value != null) {
                value.setMinimallyPopulated(false);
            }
        }

        return value;
    }

    /**
     * Fully copies data from the given Vo into a Do.
     * <p>
     * Implementations should populate the data with a call to {@link #convert(ValueObject)} and then populate the parent
     * data into the association.
     * <p>
     * The parent {@link DataObject} must have the minimal amount of data required already set prior to calling this method.
     * 
     * @param data {@link ValueObject} to convert
     * @param parent {@link DataObject} representing the parent item in the association
     * @param sequence ordinal location of the current {@link ValueObject} to convert, used only if order matters in this
     *            association
     * @return fully populated {@link DataObject}
     */
    protected abstract DO toDataObject(VO data, PO parent, int sequence);
}
