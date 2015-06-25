/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.SessionPreferenceType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Validate the user preferences.
 */
public class UserValidator extends AbstractValidator<UserVo> {

    /**
     * Validate the user preferences.
     * 
     * @param user UserVo
     * @param errors Errors object to add validation errors to
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(UserVo user, Errors errors) {
        rejectIfNull(user.getId(), errors, FieldKey.ID);
        rejectIfNullOrEmpty(user.getLastName(), errors, FieldKey.LAST_NAME);
        rejectIfNullOrEmpty(user.getFirstName(), errors, FieldKey.FIRST_NAME);
        rejectIfNullOrEmpty(user.getUsername(), errors, FieldKey.USERNAME);

        String tableSizePreference = user.getSessionPreferences().get(SessionPreferenceType.TABLE_ROW_COUNT);

        if (!isNull(tableSizePreference)) {
            rejectIfNotNumeric(tableSizePreference, errors, FieldKey.TABLE_ROW_COUNT);
            rejectIfNotInteger(tableSizePreference, errors, FieldKey.TABLE_ROW_COUNT);

            if (isInteger(tableSizePreference)) {
                int tableSize = Integer.parseInt(tableSizePreference);
                rejectIfOutsideRangeInclusive(tableSize, NUM_10, NUM_100, errors, FieldKey.TABLE_ROW_COUNT);
            }
        }
    }
}
