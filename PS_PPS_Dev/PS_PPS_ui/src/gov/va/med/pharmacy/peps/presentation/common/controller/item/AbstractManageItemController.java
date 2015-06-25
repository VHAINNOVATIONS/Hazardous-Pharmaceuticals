/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.ui.Model;

import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.DateFormatUtility;
import gov.va.med.pharmacy.peps.common.utility.ReflectionUtility;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Environment;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.FieldKey.FieldSubType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey.FieldType;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.LanguageChoice;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.session.DrugReferenceService;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.PepsController;
import gov.va.med.pharmacy.peps.presentation.common.utility.DomainUtility;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.session.RequestService;


/** 
 * AbstractManageItemController
 */
public class AbstractManageItemController extends PepsController {

    /** SHOW_SPANISH_WARNING_LABELS */
    protected static final String SHOW_SPANISH_WARNING_LABELS = "showSpanishWarningLabels";

    /** LOG */
    private static final Logger LOG = Logger.getLogger(AbstractManageItemController.class);

    /** REMOVED_ROWS */
    private static final String REMOVED_ROWS = "_removedRows";

    /** EDITABLE */
    private static final String EDITABLE = ".editable";

    /** NUMERIC_FIELD_KEYS */
    private static final Collection<FieldKey> NUMERIC_FIELD_KEYS = FieldKey.getDataFields(Number.class);

    private static final String PRODUCT_NDCS = "productNdcs";

    @Autowired
    private ManagedItemService managedItemService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private DomainUtility domainUtility;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private DrugReferenceService drugReferenceService;

    @Autowired
    private Errors errors;

    /**
     * AbstractManageItemController
     */
    public AbstractManageItemController() {
        super();
    }

    /**
     * Binds the form fields to the item passed.
     *
     * @param <T> the type of the item
     * @param item the item
     * @throws ValueObjectValidationException an exception
     */
    protected <T extends ManagedItemVo> void bindParameters(T item) throws ValueObjectValidationException {

        Collection<Role> roles = getUser().getRoles();

        @SuppressWarnings("unchecked")
        Map<String, Object> parameterMap = request.getParameterMap();

        Set<FieldKey> validFieldKeys = getValidFieldKeysForRoles(item, roles);

        DataFields<DataField> vaDataFields = item.getVaDataFields();

        for (FieldKey validFieldKey : validFieldKeys) {

            FieldType fieldType = validFieldKey.getFieldType();
            FieldSubType fieldSubType = validFieldKey.getFieldSubType();
            String formElementName = validFieldKey.fromDotsToCamelCase();

            if (fieldSubType == FieldSubType.MULTI_SELECT_LIST_DATA_FIELD
                || fieldSubType == FieldSubType.MULTI_SELECT_PRIMARY_KEY_DATA_FIELD
                || fieldSubType == FieldSubType.MULTITEXT_DATA_FIELD) {

                bindMultiSelectParameters(item, parameterMap, vaDataFields, validFieldKey, fieldType, formElementName);

            } else if (fieldSubType == FieldSubType.MULTI_SELECT) {

                bindMultiSelectParameters(item, parameterMap, vaDataFields, validFieldKey, fieldType, formElementName + "."
                    + formElementName);

            } else if (fieldSubType == FieldSubType.LIST_CHECKBOX_DATA_FIELD) {

                bindListCheckboxParameters(item, parameterMap, vaDataFields, validFieldKey, fieldType, formElementName);

            } else if (fieldSubType == FieldSubType.GROUP_LIST_DATA_FIELD) {

                bindGroupedListParameters(item, parameterMap, vaDataFields, validFieldKey, formElementName);

            } else if (fieldSubType == FieldSubType.GROUP_DATA_FIELD) {

                bindGroupField(item, parameterMap, vaDataFields, validFieldKey, formElementName);

            } else if (fieldSubType == FieldSubType.PRIMITIVE || fieldSubType == FieldSubType.SINGLE_SELECT
                || fieldSubType == FieldSubType.SIMPLE_DATA_FIELD
                || fieldSubType == FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD) {

                bindSimpleField(item, parameterMap, vaDataFields, validFieldKey, formElementName);

            } else if (parameterMap.containsKey(formElementName)) {

                throw new NotImplementedException("Form handling for the FieldSubType " + fieldSubType.toString() + " in "
                    + ManageItemController.class.getName() + ".bindParameters() not implemented.");
            }

        }

        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }

    }

    /**
     * Binds the list grouped list html form values to the item's field indicated by the field key
     * 
     * @param <T> the type of item
     * @param item the item to bind to
     * @param parameterMap the request parameter map
     * @param vaDataFields 
     * @param validFieldKey the field key that indicates what to bind to
     * @param formElementName the html form element name
     */
    private <T> void bindGroupedListParameters(T item, Map<String, Object> parameterMap, DataFields<DataField> vaDataFields,
        FieldKey validFieldKey, String formElementName) {
        List<FieldKey> fieldKeys = validFieldKey.getGroupedFields();

        List<ValueObject> originalValueObjects = ReflectionUtility.getValue(item, validFieldKey);

        removeRows(parameterMap, formElementName, originalValueObjects);

        String paramName = formElementName + "." + fieldKeys.get(0).fromDotsToCamelCase() + "_";

        int rowCount = countRows(parameterMap, paramName);

        // was there a parameter map entry for at least one row
        if (rowCount > 0) {
            List values = new ArrayList(rowCount);

            for (int index = 0; index < rowCount; index++) {

                ValueObject vo = null;

                if (index >= originalValueObjects.size()) {
                    try {
                        vo = (ValueObject) validFieldKey.getFieldClass().newInstance();
                    } catch (InstantiationException e) {
                        LOG.error("An InstantiationException was caught creating a new instance of "
                            + validFieldKey.getFieldClass().getName(), e);
                    } catch (IllegalAccessException e) {
                        LOG.error("An IllegalAccessException was caught creating a new instance of "
                            + validFieldKey.getFieldClass().getName(), e);
                    }
                } else {
                    vo = originalValueObjects.get(index).copy();
                }

                for (FieldKey nestedValidFieldKey : fieldKeys) {

                    paramName = formElementName + "." + nestedValidFieldKey.fromDotsToCamelCase() + "_" + index;
                    bindSimpleField(vo, parameterMap, vaDataFields, nestedValidFieldKey, paramName);
                }

                values.add(vo);
            }

            ReflectionUtility.setValue((ValueObject) item, validFieldKey, values);
        }
    }

    /**
     * 
     * Binds the list checkbox html form values to the item's field indicated by the field key
     *
     * @param <T> the type of item
     * @param item the item to bind to
     * @param parameterMap the request parameter map
     * @param vaDataFields the vadatafields
     * @param validFieldKey the field key that indicates what to bind to
     * @param fieldType the type of field
     * @param formElementName the html form element name
     */
    private <T> void bindListCheckboxParameters(T item, Map<String, Object> parameterMap,
        DataFields<DataField> vaDataFields, FieldKey validFieldKey, FieldType fieldType, String formElementName) {
        if (parameterMap.get(formElementName) != null) {
            List possibleValues = domainUtility.getDomain(validFieldKey);
            List newValues = new ArrayList();

            for (Object option : possibleValues) {
                String[] values = (String[]) parameterMap.get(formElementName + "_" + option);

                if (values != null && values[0] != null) {
                    newValues.add(option);
                }
            }

            if (fieldType == FieldType.VA_DATA_FIELD) {
                ListDataField dataField = (ListDataField) vaDataFields.getDataField(validFieldKey);
                dataField =
                    (ListDataField) bindVaDfEditable(parameterMap, vaDataFields, validFieldKey, formElementName, dataField);
                dataField.unselect();
                dataField.addSelections(newValues);
            } else {
                ReflectionUtility.setValue((ValueObject) item, validFieldKey, newValues);
            }
        }
    }

    /**
     * Binds the multiselect html form values to the item's field indicated by the field key
     *
     * @param <T> the type of item
     * @param item the item to bind to
     * @param parameterMap the request parameter map
     * @param vaDataFields the vadatafields
     * @param validFieldKey the field key that indicates what to bind to
     * @param fieldType the type of field
     * @param formElementName the html form element name
     */
    private <T> void bindMultiSelectParameters(T item, Map<String, Object> parameterMap, DataFields<DataField> vaDataFields,
        FieldKey validFieldKey, FieldType fieldType, String formElementName) {

        List<Object> originalValueObjects = null;

        if (fieldType == FieldType.VA_DATA_FIELD) {
            ListDataField dataField = (ListDataField) vaDataFields.getDataField(validFieldKey);
            originalValueObjects = dataField.getValue();
        } else {
            originalValueObjects = ReflectionUtility.getValue(item, validFieldKey);
        }

        removeRows(parameterMap, formElementName, originalValueObjects);

        int rowCount = countRows(parameterMap, formElementName + "_");

        if (rowCount > 0) {
            List values = new ArrayList(rowCount);

            for (int index = 0; index < rowCount; index++) {
                String value = StringUtils.trimToNull(((String[]) parameterMap.get(formElementName + "_" + index))[0]);

                if (validate(validFieldKey, value) && value != null) {
                    values.add(conversionService.convert(value, validFieldKey.getFieldClass()));
                }
            }

            if (fieldType == FieldType.VA_DATA_FIELD) {
                ListDataField dataField = (ListDataField) vaDataFields.getDataField(validFieldKey);
                dataField =
                    (ListDataField) bindVaDfEditable(parameterMap, vaDataFields, validFieldKey, formElementName, dataField);
                dataField.unselect();
                dataField.addSelections(values);
            } else {
                ReflectionUtility.setValue((ValueObject) item, validFieldKey, values);
            }
        }
    }

    /**
     * 
     * Binds the groupfield html form values to the item's field indicated by the field key
     *
     * @param item the item to bind to
     * @param parameterMap the request parameter map
     * @param vaDataFields the items vaDfs
     * @param validFieldKey the valid field key
     * @param formElementName of the value to bind
     */
    private void bindGroupField(ValueObject item, Map<String, Object> parameterMap, DataFields<DataField> vaDataFields,
        FieldKey validFieldKey, String formElementName) {

        DataField vaDataField = null;

        vaDataField = bindVaDfEditable(parameterMap, vaDataFields, validFieldKey, formElementName, vaDataField);

        List<FieldKey> groupedFields = validFieldKey.getGroupedFields();

        for (FieldKey key : groupedFields) {
            if (vaDataField == null) {
                bindSimpleField(item, parameterMap, vaDataFields, key, key.fromDotsToCamelCase());
            } else {
                DataField groupedVaDf = (DataField) ((GroupDataField) vaDataField).getGroupedField(key);
                bindPrimitiveFields(item, parameterMap, key, key.fromDotsToCamelCase(), groupedVaDf);
            }
        }
    }

    /**
     * Binds the simple value from the parameter map to the item's field indicated by the fieldkey.
     *
     * @param item the item to bind to
     * @param parameterMap the request parameter map
     * @param vaDataFields 
     * @param validFieldKey the field key that indicates what to bind to
     * @param formElementName the html form element name
     */
    private void bindSimpleField(ValueObject item, Map parameterMap, DataFields<DataField> vaDataFields,
        FieldKey validFieldKey, String formElementName) {

        DataField vaDataField = null;

        vaDataField = bindVaDfEditable(parameterMap, vaDataFields, validFieldKey, formElementName, vaDataField);

        if (validFieldKey.getFieldSubType() == FieldSubType.MULTI_SELECT) {

            String[] values = (String[]) parameterMap.get(formElementName);

            List origValues = ReflectionUtility.getValue(item, validFieldKey);
            List newValues = new ArrayList();

            boolean eachEqual = true;
            boolean sizeEqual =
                (origValues == null && values == null)
                    || (origValues != null && values != null && values.length == origValues.size());
            int valueIndex = 0;

            if (values != null) {

                for (String value : values) {
                    Object objValue = conversionService.convert(value, validFieldKey.getFieldClass());
                    newValues.add(objValue);

                    if (valueIndex < origValues.size()) {

                        Object origValue = origValues.get(valueIndex);

                        if (origValue != null && objValue != null) {
                            if (objValue == null || origValue == null) {
                                eachEqual = false;
                            } else if (objValue instanceof ManagedItemVo) {
                                if (!((ManagedItemVo) objValue).getId().equals(((ManagedItemVo) origValue).getId())) {
                                    eachEqual = false;
                                }
                            } else if (!objValue.equals(origValue)) {
                                eachEqual = false;
                            }
                        }
                    }
                }
            }

            if (!eachEqual || !sizeEqual) {
                ReflectionUtility.setValue(item, validFieldKey, newValues);
            }

        } else if (validFieldKey.getFieldSubType() == FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD) {
            if (validFieldKey.getFieldType() == FieldType.VA_DATA_FIELD) {
                if (parameterMap.containsKey(formElementName)) {

                    vaDataField.unselect();

                    String value = ((String[]) parameterMap.get(formElementName))[0];

                    if (StringUtils.isNotEmpty(value)) {
                        ((ListDataField<String>) vaDataField).addSelection(conversionService.convert(
                            parameterMap.get(formElementName), String.class));
                    }
                }
            }
        } else {
            bindPrimitiveFields(item, parameterMap, validFieldKey, formElementName, vaDataField);
        }
    }

    /**
     * 
     * Binds the editable value for vaDf fields
     *
     * @param parameterMap request parameter map
     * @param vaDataFields for the item
     * @param validFieldKey field key to look up item value
     * @param formElementName of html form
     * @param vaDataField to edit 
     * @return the vaDataField
     */
    private DataField bindVaDfEditable(Map parameterMap, DataFields<DataField> vaDataFields, FieldKey validFieldKey,
        String formElementName, DataField vaDataField) {
        
        DataField vaDataField2 = vaDataField;
        
        if (validFieldKey.getFieldType() == FieldType.VA_DATA_FIELD) {
            vaDataField2 = vaDataFields.getDataField(validFieldKey);

            // Check to see if the user has changed the editable state of the field
            if (parameterMap.containsKey(formElementName + EDITABLE)) {
                String editable = ((String[]) parameterMap.get(formElementName + EDITABLE))[0];
                vaDataField2.setEditable(conversionService.convert(editable, Boolean.class));
            }
        }

        return vaDataField2;
    }

    /**
     * 
     * Binds html values for primitive types 
     *
     * @param item to bind to
     * @param parameterMap request parameters
     * @param validFieldKey to use to bind
     * @param formElementName html input
     * @param vaDataField the vaDf to bind to if the field is a vaDataField
     */
    private void bindPrimitiveFields(ValueObject item, Map parameterMap, FieldKey validFieldKey, String formElementName,
        DataField vaDataField) {
        if (parameterMap.containsKey(formElementName)) {

            String value = StringUtils.trimToNull(((String[]) parameterMap.get(formElementName))[0]);

            if (validate(validFieldKey, value)) {

                Object objValue = conversionService.convert(value, validFieldKey.getFieldClass());

                //Total hack, but I can't figure out where to fix this in the tags - sgw 05/30/12
                if (validFieldKey.equals(FieldKey.PRIMARY) && objValue == null) {
                    objValue = false;
                }

                Object origValue = ReflectionUtility.getValue(item, validFieldKey);
                boolean equal = false;

                if (origValue == null && objValue == null) {
                    equal = true;
                } else if (objValue == null || origValue == null) {
                    equal = false;
                } else if (objValue instanceof ManagedItemVo) {
                    equal = ((ManagedItemVo) objValue).getId().equals(((ManagedItemVo) origValue).getId());
                } else {
                    equal = objValue.equals(origValue);
                }

                if (!equal) {
                    if (validFieldKey.getFieldType() == FieldType.VA_DATA_FIELD) {
                        vaDataField.selectValue(objValue);
                    } else {
                        ReflectionUtility.setValue(item, validFieldKey, objValue);
                    }
                }
            }
        }
    }

    /**
     * 
     * Returns the number items with the given parameter name
     *
     * @param parameterMap request parameter map
     * @param paramName request parameter name
     * @return row count
     */
    protected int countRows(Map parameterMap, String paramName) {

        // figure out how many rows are being submitted (not including the hidden extra row)
        int rowCount = 0;

        while (parameterMap.containsKey(paramName + rowCount)) {
            rowCount++;
        }

        return rowCount;
    }

    /**
     * 
     * Removes data for the rows from the multiple select box fields on the GUI
     *
     * @param parameterMap request parameter map
     * @param formElementName name of element on backing form
     * @param originalValueObjects List of original VOs
     */
    protected void removeRows(Map parameterMap, String formElementName, List<? extends Object> originalValueObjects) {
        if (parameterMap.containsKey(formElementName + REMOVED_ROWS)) {
            
            String[] removeRowStringValues = ((String[]) parameterMap.get(formElementName + REMOVED_ROWS))[0].split(",");

            List<Integer> indexes = new ArrayList<Integer>();

            for (String removeRowStringValue : removeRowStringValues) {
                String stringIndex = removeRowStringValue.substring(removeRowStringValue.lastIndexOf('_') + 1);

                if (StringUtils.isNotEmpty(stringIndex)) {
                    int index = Integer.valueOf(stringIndex);

                    if (!indexes.contains(index)) {
                        indexes.add(index);
                    }
                }

            }
            
            Integer plusButtonIndex = (Integer) flowScope.get(formElementName 
                + "_" + ControllerConstants.PLUS_BUTTON_INDEX);            
            
            Collections.sort(indexes, Collections.reverseOrder());

            for (int index : indexes) {
                if (originalValueObjects.size() > index) {
                    originalValueObjects.remove(index);
                }
                
                if (plusButtonIndex != null && plusButtonIndex > index) {
                    plusButtonIndex -= 1;
                    flowScope.put(formElementName 
                        + "_" + ControllerConstants.PLUS_BUTTON_INDEX, plusButtonIndex);
                }
            }
        }
    }

    /**
     * Calls a field key's validator
     * 
     * @param validFieldKey a valid field key
     * @param values list of values to validate
     * @return true if value valid
     */
    @SuppressWarnings("unused")
    private boolean validate(FieldKey validFieldKey, String[] values) {
        boolean valid = true;

        for (String value : values) {
            if (!validate(validFieldKey, value)) {
                valid = false;
            }
        }

        return valid;
    }

    /**
     * 
     * Calls a field key's validator
     *
     * @param validFieldKey a valid field key
     * @param value a string value to validate
     * @return true if valid
     */
    private boolean validate(FieldKey validFieldKey, String value) {
        if (Double.class.isAssignableFrom(validFieldKey.getFieldClass()) && StringUtils.isNotEmpty(value)) {
            try {
                Double.valueOf(value);
            } catch (NumberFormatException e) {
                errors.addError(new ValidationError(validFieldKey, ErrorKey.COMMON_NOT_NUMERIC, validFieldKey
                    .getLocalizedName(getLocale())));

                return false;
            }
        } else if (Float.class.isAssignableFrom(validFieldKey.getFieldClass()) && StringUtils.isNotEmpty(value)) {
            try {
                Float.valueOf(value);
            } catch (NumberFormatException e) {
                errors.addError(new ValidationError(validFieldKey, ErrorKey.COMMON_NOT_NUMERIC, validFieldKey
                    .getLocalizedName(getLocale())));

                return false;
            }
        } else if (Long.class.isAssignableFrom(validFieldKey.getFieldClass()) && StringUtils.isNotEmpty(value)) {
            try {
                Long.valueOf(value);
            } catch (NumberFormatException e) {
                errors.addError(new ValidationError(validFieldKey, ErrorKey.COMMON_NOT_INTEGER, validFieldKey
                    .getLocalizedName(getLocale())));

                return false;
            }
        } else if (Integer.class.isAssignableFrom(validFieldKey.getFieldClass()) && StringUtils.isNotEmpty(value)) {
            try {
                Integer.valueOf(value);
            } catch (NumberFormatException e) {
                errors.addError(new ValidationError(validFieldKey, ErrorKey.COMMON_NOT_INTEGER, validFieldKey
                    .getLocalizedName(getLocale())));

                return false;
            }
        } else if (Date.class.isAssignableFrom(validFieldKey.getFieldClass()) && StringUtils.isNotEmpty(value)) {
            try {
                DateFormatUtility.convertToDateStrictly(value);

            } catch (ParseException e) {

                errors.addError(new ValidationError(validFieldKey, ErrorKey.COMMON_IMPROPER_DATE_FORMAT, new Object[] {
                    validFieldKey.getLocalizedName(getLocale()), value }));

                return false;
            } catch (IllegalArgumentException e1) {

                errors.addError(new ValidationError(validFieldKey, ErrorKey.COMMON_IMPROPER_DATE_FORMAT, new Object[] {
                    validFieldKey.getLocalizedName(getLocale()), value }));

                return false;
            }
        }

        return true;
    }

    /**
     * Check to see if numeric fields in FieldKey match any request parameters.
     * For those that match, validates that these
     * request parameters are indeed numeric.
     * 
     * @param parameterMap request parameter map
     */
    protected void validateNumericFields(Map parameterMap) {
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();

        for (Map.Entry<String, String[]> entry : entries) {
            for (FieldKey fieldKey : NUMERIC_FIELD_KEYS) {
                String partialOgnl;

                if (fieldKey.isVaDataField()) {
                    partialOgnl = fieldKey.toAttributeName() + "." + fieldKey.fromDotsToCamelCase();
                } else {
                    partialOgnl = fieldKey.toAttributeName();
                }

                if (entry.getKey().endsWith(partialOgnl)) {
                    try {
                        for (String value : entry.getValue()) {
                            if ((value != null) && (value.trim().length() > 0)) {
                                Double.valueOf(value);
                            }
                        }
                    } catch (NumberFormatException e) {
                        errors.addError(new ValidationError(fieldKey, ErrorKey.COMMON_NOT_NUMERIC, fieldKey
                            .getLocalizedName(getLocale())));
                    }
                }
            }
        }
    }

    /**
     * Get the set of valid field keys for the collection of roles
     *
     * @param vo the value object 
     * @param roles the collection of roles 
     * @return the set of valid field keys for the collection of roles
     */
    @SuppressWarnings("rawtypes")
    private Set<FieldKey> getValidFieldKeysForRoles(ValueObject vo, Collection<Role> roles) {

        Set<FieldKey> validFields = vo.listAllFields();

        if (environmentUtility.isLocal()) {
            validFields.removeAll(vo.listNonEditableFields(Environment.LOCAL, roles));
        }

        validFields.removeAll(vo.listDisabledFields(Environment.NATIONAL, roles));

        return validFields;
    }


    /**
     * Puts the warning labels on the model if this is a product and the options tab is selected.
     * 
     * @param entityType the entity type
     * @param model the model
     * @param tabKey the tab
     * @param item the item
     */
    protected void handleWarningLabels(EntityType entityType, Model model, String tabKey, ManagedItemVo item) {

        if (EntityType.PRODUCT == entityType && "options_tab".equals(tabKey)) {
            ReportProductVo reportProductVo = new ReportProductVo();
            reportProductVo.setSpanish(LanguageChoice.SPANISH == flowScope
                .get(WarningLabelController.WARNING_LABEL_LANGUAGE_CHOICE));
            reportProductVo.setGcnSeqNo(item.getGcnSequenceNumber());
            reportProductVo = drugReferenceService.retrieveWarningLabels(reportProductVo);
            model.addAttribute("warningLabels", reportProductVo.getWarningLabels());
        }
    }

    /**
     * Put language choices on the model if necessary
     *
     * @param entityType the entity type
     * @param model the model
     * @param tabKey the tab key
     */
    protected void handleLanguageChoices(EntityType entityType, Model model, String tabKey) {
        if (entityType == EntityType.PRODUCT && tabKey.equals(ControllerConstants.OPTIONS_TAB)) {
            List<String> choices = new ArrayList<String>();

            for (LanguageChoice languageChoice : LanguageChoice.values()) {
                choices.add(languageChoice.toString());
            }

            model.addAttribute("languageChoices", choices);
        }
    }

    /**
     * Sets the list of NDCs for a Product if the EntityType is Product.
     *
     * @param entityType the EntityType
     * @param model the Model
     * @param item the ManagedItem
     */
    protected void handleProductNdcs(EntityType entityType, Model model, ManagedItemVo item) {
        if (entityType == EntityType.PRODUCT) {

            Boolean showInactiveNDCs = (Boolean) flowScope.get(ManageNDCController.SHOW_INACTIVE_NDCS);

            if (showInactiveNDCs == null) {
                showInactiveNDCs = false;
            }

            ProductVo product = (ProductVo) item;
            List<NdcVo> ndcs = product.getNdcs();

            if (showInactiveNDCs) {
                model.addAttribute(PRODUCT_NDCS, ndcs);
            } else {
                List<NdcVo> activeNDCs = new ArrayList<NdcVo>();

                for (NdcVo ndc : ndcs) {
                    if (ndc.getItemStatus() == ItemStatus.ACTIVE) {
                        activeNDCs.add(ndc);
                    }
                }

                model.addAttribute(PRODUCT_NDCS, activeNDCs);
            }
        }
    }

    /**
     * Binds the new parameters to the item and checks to see if the special handling
     * needs to be updated
     * @param item the updated item
     * @throws ValueObjectValidationException ValueObjectValidationException
     */
    protected void bindAndUpdateSpecialHandling(ManagedItemVo item)
        throws ValueObjectValidationException {
        if (item.isProductItem()) {
            ProductVo previousVersionItem = new ProductVo();
            previousVersionItem.setDrugClasses(((ProductVo) item).getDrugClasses());

            bindParameters(item);

            getManagedItemService().updateSpecialHandlingBasedOnDrugClassChange((ProductVo) item,
                previousVersionItem, getUser());
        } else {
            bindParameters(item);
        }
    }

    /**
     * The domain Utility
     *
     * @return the domain utility
     */
    protected DomainUtility getDomainUtility() {
        return domainUtility;
    }

    /**
     * The managed item service
     * 
     * @return the managed item service
     */
    protected ManagedItemService getManagedItemService() {
        return managedItemService;
    }
}
