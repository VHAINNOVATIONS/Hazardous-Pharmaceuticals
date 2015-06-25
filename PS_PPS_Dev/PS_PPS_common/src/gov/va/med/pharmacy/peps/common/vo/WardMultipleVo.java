/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * Value object for "Ward - Multiple" used in medication instruction domain.
 */
public class WardMultipleVo extends ValueObject {

    private static final long serialVersionUID = 1L;

    private String wardAdminTimes;
    private WardSelectionVo wardSelection;

    /**
     * Description
     * 
     * @return ward property
     */
    public WardSelectionVo getWardSelection() {

        return wardSelection;
    }

    /**
     * Description
     * 
     * @param ward ward property
     */
    public void setWardSelection(WardSelectionVo ward) {

        this.wardSelection = ward;
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition that the current instance is not a new one, nor
     * a read-only one for WardMultipleVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    @Override
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {

        // set the fields for wardMultipleVo.
        Collection<FieldKey> extFields = FieldKey.getVaDataFields();
        Set<FieldKey> disabledFields = new HashSet<FieldKey>();

        if (environment.isNational()) {
            for (FieldKey field : extFields) {
                if (FieldKey.WARD_SELECTION.equals(field)) {
                    disabledFields.add(field);
                } else if (FieldKey.WARD_ADMIN_TIMES.equals(field)) {
                    disabledFields.add(field);
                }
            }
        }

        return disabledFields;
    }

    /**
     * getWardAdminTimes
     * 
     * @return wardAdminTimes property
     */
    public String getWardAdminTimes() {

        return wardAdminTimes;
    }

    /**
     * Description
     * 
     * @param wardAdminTimes wardAdminTimes property
     */
    public void setWardAdminTimes(String wardAdminTimes) {

        this.wardAdminTimes = trimToEmpty(wardAdminTimes);
    }

    /**
     * toShortString returns toString unless overridden in VO
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    public String toShortString() {

        String s1 = FieldKey.getLocalizedName(FieldKey.WARD_SELECTION, Locale.getDefault());
        String s2 = FieldKey.getLocalizedName(FieldKey.WARD_ADMIN_TIMES, Locale.getDefault());

        StringBuffer value =
                new StringBuffer(s1 + ": " 
                    + (wardSelection == null ?  PPSConstants.NOT_SPECIFIED : wardSelection.toShortString())
                    + PPSConstants.P_TAG + s2 + ": " 
                    + (wardAdminTimes == null ? PPSConstants.NOT_SPECIFIED : wardAdminTimes));

        value.append(PPSConstants.P_TAG);

        return value.toString();
    }
}
