/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility;


import java.util.Comparator;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;


/**
 * Sorts FieldKeys alphabetically by localized name
 */
public class FieldKeyComparator implements Comparator<FieldKey> {
    private Locale locale;
    private EntityType entityType;

    /**
     * Set the {@link #locale} to the given {@link Locale}. Use this {@link Locale} to localize the {@link FieldKey} names
     * for sorting. Localize fields based upon {@link EntityType}.
     * 
     * @param locale {@link Locale} to localize
     * @param entityType {@link EntityType} for which to localize
     */
    public FieldKeyComparator(Locale locale, EntityType entityType) {
        this.locale = locale;
        this.entityType = entityType;
    }

    /**
     * Performs the comparison, returning if {@link FieldKey} one is greater than, less than, or equal to {@link FieldKey}
     * two based upon localized name, using the {@link Locale#getDefault()}.
     * 
     * @param one - FieldKey 1
     * @param two - FieldKey 2
     * @return integer that indicates results of case insensitive order compare of the localized name of the FieldKeys
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(FieldKey one, FieldKey two) {
        return String.CASE_INSENSITIVE_ORDER.compare(one.getLocalizedName(locale, entityType), two.getLocalizedName(locale,
            entityType));
    }

    /**
     * getLocale.
     * @return locale property
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * setLocale.
     * @param locale locale property
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * getEntityType.
     * @return entityType property
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * setEntityType.
     * @param entityType entityType property
     */
    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }
}
