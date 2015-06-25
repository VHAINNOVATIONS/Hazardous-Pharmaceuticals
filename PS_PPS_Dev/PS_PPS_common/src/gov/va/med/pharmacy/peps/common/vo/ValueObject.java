/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.ReflectionUtility;
import gov.va.med.pharmacy.peps.common.vo.diff.Diff;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.common.vo.diff.IgnoreDifference;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Implement common ValueObject functionality
 */
public class ValueObject implements Serializable, Comparable<ValueObject>, Cloneable {
    private static final long serialVersionUID = 1L;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ValueObject.class);
    private static final String NO_VALIDATION = "Validation will not occur because there was an error getting the validator"
        + " instance for value object ";

    @IgnoreEquals
    @IgnoreDifference
    private String createdBy;

    @IgnoreEquals
    @IgnoreDifference
    private Date createdDate;

    @IgnoreEquals
    @IgnoreDifference
    private String modifiedBy;

    @IgnoreEquals
    @IgnoreDifference
    private Date modifiedDate;

    @IgnoreEquals
    @IgnoreDifference
    private boolean readOnlyInstance = false;

    @IgnoreEquals
    @IgnoreDifference
    private boolean minimallyPopulated = false;

    /**
     * Protected constructor such that only subclasses can instantiate
     * ValueObject.
     */
    protected ValueObject() {

        super();
    }

    /**
     * Map the given ValueObject attribute name to its {@link FieldKey}.
     * 
     * The default logic to create the {@link String} value of the key is to
     * take the camelCase attribute name and separate
     * it at each capital letter with a period. The entire String is then
     * converted to lower case. This generated {@link String} value for the key
     * is passed into {@link FieldKey#getKey(String)}.
     * 
     * For example, the attribute name "genericName" is converted to the key
     * "generic.name", the attribute name
     * "gcnSequenceNumber" is converted to the key "gcn.sequence.number", and
     * the attribute "ndc" is simply returned as the
     * key "ndc".
     * 
     * @param <T>
     *            Type of FieldKey
     * @param attribute
     *            String name of the ValueObject attribute
     * @return FieldKey for the given attribute
     */
    public static <T> FieldKey<T> mapAttributeToFieldKey(String attribute) {
        Matcher matcher = Pattern.compile("\\p{Upper}").matcher(attribute);

        StringBuffer stringKey = new StringBuffer();
        int begin = 0;
        int end = 0;

        // while a match can be found.
        while (matcher.find()) {
            end = matcher.start();
            stringKey.append(attribute.substring(begin, end));
            stringKey.append(".");
            begin = end;
        }

        stringKey.append(attribute.substring(begin));

        return FieldKey.getKey(stringKey.toString().toLowerCase());
    }

    /**
     * Check this instance of ValueObject (considered the "old" value) with the
     * given "new" value of ValueObject.
     * 
     * The given ValueObject must be of the same Class type as this ValueObject,
     * otherwise an IllegalArgumentException is
     * thrown.
     * 
     * @param newValue
     *            "new" value to diff against
     * @return Collection<Difference> differences between the old and new
     *         ValueObject attribute values
     */
    public Collection<Difference> diff(ValueObject newValue) {
        return Diff.checkForDifferences(this, newValue);
    }

    /**
     * Call {@link #diff(ValueObject)} to check this instance of ValueObject
     * (considered the "old" value) with the given
     * "new" value of ValueObject.
     * 
     * Then create a Collection of {@link ModDifferenceVo} from the Collection
     * of {@link Difference} returned by {@link #diff(ValueObject)}.
     * 
     * @param newValue
     *            "new" value to diff against
     * @return Collection<ModDifferenceVo> differences between the old and new
     *         ValueObject attribute values
     */
    public Collection<ModDifferenceVo> compareDifferences(ValueObject newValue) {
        Collection<ModDifferenceVo> modDifferences = new ArrayList<ModDifferenceVo>();

        Collection<Difference> differences = diff(newValue);

        for (Difference difference : differences) {
            ModDifferenceVo modDifference = new ModDifferenceVo();
            modDifference.setDifference(difference);
            modDifferences.add(modDifference);
        }

        return modDifferences;
    }

    /**
     * Compare this ValueObject to another
     * 
     * @param vo
     *            ValueObject to compare to
     * @return a negative integer, zero, or a positive integer if this
     *         ValueObject is less than, equal to, or greater than
     *         the given Object
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(ValueObject vo) {
        return CompareToBuilder.reflectionCompare(this, vo);
    }

    /**
     * Equals returned by Jakarta Commons EqualsBuilder
     * 
     * @param obj
     *            Object to check equals against
     * @return true if equal
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, getIgnoreEqualsFields());
    }

    /**
     * HashCode built by Jakarta Commons HasCodeBuilder
     * 
     * @return int hash
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, getIgnoreEqualsFields());
    }

    /**
     * Return a Set of field names that should be ignored by the
     * {@link #equals(Object)} and {@link #hashCode()} methods.
     * <p>
     * Default implementation returns a Set of all Fields annotated with
     * {@link IgnoreEquals}.
     * 
     * @return Set of field names to be ignored by {@link #equals(Object)} and
     *         {@link #hashCode()}
     */
    protected Set<String> getIgnoreEqualsFields() {
        return ReflectionUtility.getAnnotatedFieldNames(getClass(), IgnoreEquals.class);
    }

    /**
     * Deep copy clone this ValueObject through a serialize-deserialize process
     * using Apache Common's {@link SerializationUtils} class.
     * 
     * Serialization could be slow, but with the complex structure in the
     * Orderable Items, Products, and NDCs, this is a much
     * simpler and more maintainable solution.
     * 
     * @return deep copy clone of this ValueObject
     * @throws CloneNotSupportedException
     *             if this object does not support cloning. Since ValueObjects
     *             support cloning, this
     *             should never get thrown.
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    public ValueObject clone() throws CloneNotSupportedException {
        return (ValueObject) SerializationUtils.clone(this);
    }

    /**
     * Create a copy of this ValueObject. This method wraps the clone() call in
     * try/catch block to turn the checked
     * exception, CloneNotSupportedException, into an unchecked exception,
     * CommonException.
     * 
     * @param <T>
     *            Type of ValueObject to return (must be the same type as this
     *            ValueObject)
     * @return A copy of this instance.
     */
    public <T extends ValueObject> T copy() {
        try {
            return (T) clone();
        } catch (CloneNotSupportedException e) {
            throw new CommonException(e, CommonException.UNEXPECTED_EXCEPTION_SEEN);
        }
    }

    /**
     * toString returned by Jakarta Commons ToStringBuilder
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * toShortString returns toString unless overridden in VO
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    public String toShortString() {
        return toString();
    }

    /**
     * getShortString returns toString unless overridden in VO, used for EL
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    public String getShortString() {

        return toString();
    }

    /**
     * Trims the given String. If the given String is null, returns null
     * 
     * @param value
     *            String value to trim
     * @return trimmed or null String
     * 
     * @see StringUtils#trimToEmpty(String)
     */
    protected String trimToEmpty(String value) {
        if (value != null) {
            return StringUtils.trimToEmpty(value);
        }

        return null;
    }

    /**
     * Ensure this ValueObject instance contains valid data values. Throw a
     * ValuObjectValidationException if any field values
     * are invalid.
     * 
     * @throws ValueObjectValidationException
     *             if this ValueObject is invalid
     */
    public final void validate() throws ValueObjectValidationException {
        Errors errors = checkForErrors();

        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }
    }

    /**
     * Ensure this ValueObject instance contains valid data values. Throws a ValuObjectValidationException 
     * if any field values are invalid. Validates data values and user permissions.
     * 
     * @param user This is the current UserVo executing code
     * @throws ValueObjectValidationException
     *             if this ValueObject is invalid
     */
    public final void validate(UserVo user) throws ValueObjectValidationException {
        Errors errors = checkForErrors(user);

        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }
    }

    /**
     * Ensure this Value Object instance contains valid data values. Throw a
     * ValuObjectValidationException if any field values
     * are invalid. Validates data values and user permissions.
     * 
     * @param user
     *            current UserVo executing code
     * @param environment
     *            is the local/national environment
     * @throws ValueObjectValidationException
     *             if this ValueObject is invalid
     */
    public final void validate(UserVo user, Environment environment) throws ValueObjectValidationException {
        Errors errors = checkForErrors(user, environment);

        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }
    }

    /**
     * Ensure this ValueObject instance contains valid data values. Throw a
     * ValuObjectValidationException if any field values
     * are invalid. Validates data values and user permissions.
     * 
     * @param user
     *            current UserVo executing code
     * @param environment
     *            is the local/national environment
     * @param pageNum
     *            is the page number for in add product wizard
     * @throws ValueObjectValidationException
     *             if this ValueObject is invalid
     */
    public final void validate(UserVo user, Environment environment, int pageNum) throws ValueObjectValidationException {
        Errors errors = checkForErrors(user, environment, pageNum);

        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }
    }

    /**
     * Ensure this ValudObject instance contains valid data values.
     * 
     * @return Errors containing messages concerning invalid values, if any
     */
    public final Errors checkForErrors() {
        Errors errors = new Errors();

        try {
            AbstractValidator validator = getValidatorInstance();
            validator.validate(this, errors);

        } catch (CommonException e) {
            LOG.warn(NO_VALIDATION + getClass().getName());
        }

        // return the ValueObject's errors
        return errors;
    }

    /**
     * Ensure this ValueObject instance does contain valid data values. Validates
     * data values and user permissions.
     * 
     * @param user
     *            current UserVo executing code
     * @return Errors containing messages concerning invalid values, if any
     */
    public final Errors checkForErrors(UserVo user) {
        Errors errors = new Errors();

        try {
            AbstractValidator validator = getValidatorInstance();
            validator.validate(this, user, errors);
        } catch (CommonException e) {
            LOG.warn(NO_VALIDATION + getClass().getName());
        }

        return errors;
    }

    /**
     * Ensure this ValudObject instance contains valid data values. Validates
     * data values and user permissions.
     * 
     * @param user
     *            current UserVo executing code
     * @param environment
     *            the local/national environment
     * @return Errors containing messages concerning invalid values, if any
     */
    public final Errors checkForErrors(UserVo user, Environment environment) {
        Errors errors = new Errors();

        try {
            AbstractValidator validator = getValidatorInstance();
            validator.validate(this, user, environment, errors);
        } catch (CommonException e) {
            LOG.warn(NO_VALIDATION + getClass().getName());
        }

        return errors;
    }

    /**
     * Ensure this ValueObject instance contains valid data values. Validates
     * data values and user permissions.
     * 
     * @param user
     *            current UserVo executing code
     * @param environment
     *            the local/national environment
     * @param pageNum
     *            is the page number in the Add product wizard
     * @return Errors containing messages concerning invalid values, if any
     */
    public final Errors checkForErrors(UserVo user, Environment environment, int pageNum) {
        Errors errors = new Errors();

        try {
            AbstractValidator validator = getValidatorInstance();
            validator.validate(this, user, environment, pageNum, errors);
        } catch (CommonException e) {
            LOG.warn(NO_VALIDATION + getClass().getName());
        }

        return errors;
    }

    /**
     * Return a String with the fields required for this {@link ValueObject} to
     * be unique concatenated together.
     * 
     * @return String concatenated fields making this ValueObject unique
     */
    public String getUniqueId() {
        return toShortString();
    }

    /**
     * Return a new Validator instance used to validate this ValueObject. The
     * Validator class is retrieved from this
     * ValueObject's FieldKey.
     * 
     * @return new AbstractValidator subclass instance for this ValueObject
     * 
     * @see #validate()
     */
    protected AbstractValidator<? extends ValueObject> getValidatorInstance() {
        return FieldKey.newValidatorInstance(getClass());
    }

    /**
     * Returns if this ValueObject instance should be non-modifiable.
     * 
     * @return boolean True if this instance is should be non-modifiable.
     */
    public boolean isReadOnlyInstance() {
        return readOnlyInstance;
    }

    /**
     * Description
     * 
     * @param readOnlyInstance
     *            boolean True if this instance should be non-modifiable.
     */
    public void setReadOnlyInstance(boolean readOnlyInstance) {
        this.readOnlyInstance = readOnlyInstance;
    }

    /**
     * Returns if this ValueObject instance is 'new' in the database persistence
     * sense. <BR>
     * Typically this means that the instance has a null unique ID.
     * 
     * @return boolean True if this instance is new.
     */
    public boolean isNewInstance() {
        return false;
    }

    /**
     * List all fields for this ValueObject, discovered via reflection.
     * <p>
     * Note: Sub-classes may need to add to the returned list fields that are
     * contained within structures such as Maps.
     * 
     * @return Set<FieldKey> All fields for this object.
     */
    public Set<FieldKey> listAllFields() {
        return ReflectionUtility.deriveFields(this);
    }

    /**
     * List all disabled fields for this ValueObject.
     * 
     * @param environment
     *            the current {@link Environment}
     * @param roles
     *            Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    public final Set<FieldKey> listDisabledFields(Environment environment, Collection<Role> roles) {
        if (isNewInstance()) {
            return Collections.emptySet();
        }

        if (isReadOnlyInstance()) {
            return listAllFields();
        }

        // if not a manager second approver or supervisor then disable all fields. 
        if (!((roles.contains(Role.PSS_PPSN_MANAGER) || roles.contains(Role.PSS_PPSN_SECOND_APPROVER) 
            || roles.contains(Role.PSS_PPSN_SUPERVISOR)))) {
            return listAllFields();
        }

        return handleListDisabledFields(environment, roles);
    }

    /**
     * List all disabled fields for this ValueObject, with the pre-condition
     * that the current instance is not a new one, nor
     * a read-only one.
     * <p>
     * Default implementation instantiates an empty {@link HashSet}.
     * 
     * @param environment
     *            the current {@link Environment}
     * @param roles
     *            Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All disabled fields for this object.
     */
    protected Set<FieldKey> handleListDisabledFields(Environment environment, Collection<Role> roles) {
        return new HashSet<FieldKey>();
    }

    /**
     * List all non-editable fields for this ValueObject. <BR>
     * Note: Though the VO's isEditable() could be used directly, using the
     * authorization framework (via the returned list)
     * eases the implementation of the custom JSP tag files that render the
     * fields.
     * 
     * @param environment
     *            the current {@link Environment}
     * @param roles
     *            Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All non-editable fields for this object.
     */
    public Set<FieldKey> listNonEditableFields(Environment environment, Collection<Role> roles) {
        return new HashSet<FieldKey>(0);
    }

    /**
     * List all Multiple Data Fields (<GroupListDataField>) whose Add
     * functionality should be disabled for the given {@link Role} in the given
     * {@link Environment}.
     * 
     * @param environment
     *            the current {@link Environment}
     * @param roles
     *            Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All Multiple Data Fields whose Add functionality
     *         should be disabled
     */
    public Set<FieldKey> listDisabledAddMultipleDataFields(Environment environment, Collection<Role> roles) {
        return new HashSet<FieldKey>(0);
    }

    /**
     * List all Multiple Data Fields (<GroupListDataField>) whose Modify
     * functionality should be disabled for the
     * given {@link Role} in the given {@link Environment}.
     * 
     * @param environment
     *            the current {@link Environment}
     * @param roles
     *            Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All Multiple Data Fields whose Modify functionality
     *         should be disabled
     */
    public Set<FieldKey> listDisabledModifyMultipleDataFields(Environment environment, Collection<Role> roles) {
        return new HashSet<FieldKey>(0);
    }

    /**
     * List all Multiple Data Fields (<GroupListDataField>) whose Remove
     * functionality should be disabled for the
     * given {@link Role} in the given {@link Environment}.
     * 
     * @param environment
     *            the current {@link Environment}
     * @param roles
     *            Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All Multiple Data Fields whose Remove functionality
     *         should be disabled
     */
    public Set<FieldKey> listDisabledRemoveMultipleDataFields(Environment environment, Collection<Role> roles) {
        return new HashSet<FieldKey>(0);
    }

    /**
     * List all required fields for this ValueObject.
     * 
     * @param environment
     *            the current {@link Environment}
     * @param roles
     *            Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    public final Set<FieldKey> listRequiredFields(Environment environment, Collection<Role> roles) {

        // If read-only, we don't need to note which fields are required ones.
        if (isReadOnlyInstance()) {
            return Collections.emptySet();
        }

        return handleListRequiredFields(environment, roles);
    }

    /**
     * List all required fields for this ValueObject, with the pre-condition
     * that the current instance is not a read-only
     * one.
     * <p>
     * Default implementation instantiates an empty {@link HashSet}.
     * 
     * @param environment
     *            the current {@link Environment}
     * @param roles
     *            Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {
        return new HashSet<FieldKey>(0);
    }

    /**
     * List all second review fields for this ValueObject
     * <p>
     * Default implementation instantiates an empty {@link HashSet}.
     * 
     * @param environment
     *            the current {@link Environment}
     * @param roles
     *            Collection of {@link Role} for the current user
     * @return Set<FieldKey> all second review fields for this object.
     */
    protected Set<FieldKey> handleListSecondReviewFields(Environment environment, Collection<Role> roles) {
        return new HashSet<FieldKey>(0);
    }

    /**
     * List all second review fields for the ValueObject valueObject
     * 
     * @param environment the current Environment
     * @param roles Collection of {@link Role} for the current user
     * @return Set<FieldKey> all second review fields for this object.
     */
    public final Set<FieldKey> listSecondReviewFields(Environment environment, Collection<Role> roles) {

        // If read-only, we don't need to note which fields are second review ones.
        if (isReadOnlyInstance()) {
            return Collections.emptySet();
        }

        return handleListSecondReviewFields(environment, roles);
    }

    /**
     * List all fields which are mandatory during second review fields for this ValueObject
     * <p>
     * Default implementation instantiates an empty {@link HashSet}.
     * 
     * @param environment
     *            the current {@link Environment}
     * @param roles
     *            Collection of {@link Role} for the current user
     * @return Set<FieldKey> all fields which are mandatory during second review for this object.
     */
    protected Set<FieldKey> handleMandatoryDuringSecondReviewFields(Environment environment, Collection<Role> roles) {

        return new HashSet<FieldKey>(0);
    }

    /**
     * List all second review fields for this ValueObject
     * 
     * @param environment
     *            the current {@link Environment}
     * @param roles
     *            Collection of {@link Role} for the current user
     * @return Set<FieldKey> all second review fields for this object.
     */
    public final Set<FieldKey> listMandatoryDuringSecondReviewFields(Environment environment, Collection<Role> roles) {

        // If read-only, we don't need to note which fields are second review ones.
        if (isReadOnlyInstance()) {
            return Collections.emptySet();
        }

        return handleMandatoryDuringSecondReviewFields(environment, roles);
    }

    /**
     * Description
     * 
     * @return createdBy property
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Description
     * 
     * @param createdBy createdBy property
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Description
     * 
     * @return createdDate property
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Description
     * 
     * @param createdDate createdDate property
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Description
     * 
     * @return modifiedBy property
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Description
     * 
     * @param modifiedBy modifiedBy property
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * Description
     * 
     * @return modifiedDate property
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Description
     * 
     * @param modifiedDate modifiedDate property
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     * Return true if this {@link ValueObject} is minimally populated.
     * <p>
     * A minimally populated {@link ValueObject} has only the
     * {@link #getUniqueId()} set and the values required to display to the
     * user. All other values are likely to be null.
     * 
     * @return minimallyPopulated property
     */
    public boolean isMinimallyPopulated() {
        return minimallyPopulated;
    }

    /**
     * Return true if this {@link ValueObject} is not minimally populated.
     * 
     * @return boolean
     */
    public boolean isFullyPopulated() {
        return !isMinimallyPopulated();
    }

    /**
     * Description
     * 
     * @param minimallyPopulated
     *            minimallyPopulated property
     */
    public void setMinimallyPopulated(boolean minimallyPopulated) {
        this.minimallyPopulated = minimallyPopulated;
    }
}
