/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.validator;


import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.TemplateType;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * Validates the SearchTemplateVo
 */
public class SearchTemplateValidator extends AbstractValidator<SearchTemplateVo> {

    /**
     * Validate the given ValueObject. Validates data values and user permissions.
     * 
     * @param value the object that is to be validated (can be <code>null</code>)
     * @param user current UserVo for the user executing the code
     * @param errors contextual state about the validation process (never <code>null</code>)
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.UserVo, gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void validate(SearchTemplateVo value, UserVo user, Errors errors) {
        this.checkUserRoles(value, user, errors);
        
        this.validate(value, errors);
        
        
    }

    /**
     * Description
     * 
     * @param value the object that is to be validated (can be <code>null</code>)
     * @param errors contextual state about the validation process (never <code>null</code>)
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    @Override
    public void validate(SearchTemplateVo value, Errors errors) {
        if (value == null) {
            rejectIfNull(value, errors, FieldKey.SEARCH_TEMPLATE);

            return;
        }

        rejectIfNullOrEmpty(value.getTemplateName(), errors, FieldKey.SEARCH_TEMPLATE_NAME);
    }

    /**
     * Ensure this ValueObject instance contains valid data values. Throw a ValuObjectValidationException if any field values
     * are invalid. Validates data values and user permissions.
     * 
     * @param value Value to validate
     * @param user current UserVo executing code
     * @param environment is the local/national environment
     * 
     * @throws ValueObjectValidationException if this ValueObject is invalid
     */
    public void validateDefault(SearchTemplateVo value, UserVo user, Environment environment)
        throws ValueObjectValidationException {

        Errors errors = new Errors();

        if (value == null) {
            rejectIfNull(value, errors, FieldKey.SEARCH_TEMPLATE);

            return;
        }

        rejectIfUnsavedDefault(value, errors);

        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }
    }

    /**
     * Description
     * 
     * @param value the object that is to be validated (can be <code>null</code>)
     * @param errors contextual state about the validation process (never <code>null</code>)
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator#validate(java.lang.Object,
     *      gov.va.med.pharmacy.peps.common.vo.validator.Errors)
     */
    public void rejectIfUnsavedDefault(SearchTemplateVo value, Errors errors) {
        rejectIfTrue(isNullOrEmpty(value.getId()), ErrorKey.UNSAVED_DEFAULT_SEARCH, errors);
    }
    

    /**
     * Does user role validation versus the template type of the search Template.
     * 
     *  Should be a redundant check
     * 
     * @param value The Search Template value object
     * @param user The user value object
     * @param errors A list of errors.
     */
    private void checkUserRoles(SearchTemplateVo value, UserVo user, Errors errors) {
        
        if (value.getTemplateType().equals(TemplateType.NATIONAL)) {
            
            if (!user.hasRole(Role.PSS_PPSN_SUPERVISOR /*NATIONAL_ADMINISTRATIVE_MANAGER*/)) {
                errors.addError(FieldKey.USER, ErrorKey.USER_ROLE_ERROR);
            }
        }
        
        if (value.getTemplateType().equals(TemplateType.LOCAL)) {
            
            if (!user.hasRole(Role.LOCAL_ADMINISTRATIVE_MANAGER)) {
                errors.addError(FieldKey.USER, ErrorKey.USER_ROLE_ERROR);
            }
        }
        
    }

}
