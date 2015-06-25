/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import gov.va.med.pharmacy.peps.common.exception.CommonException;
import gov.va.med.pharmacy.peps.common.utility.ResourceBundleUtility;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.GroupListDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.MultitextDataField;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ActiveIngredientValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.AdminSchedulesValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.AdministrationScheduleValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.AtcCanistersValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.CsFedScheduleValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DispenseUnitPerDoseValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DispenseUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DosageFormNounValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DosageFormUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DosageFormValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DoseUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DrugClassValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DrugClassificationValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DrugTextValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.DrugUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.EffectiveDateTimeTypeValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.GenericNameValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.HospitalLocationValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.IngredientValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.LocalMedicationRouteValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.LocalPossibleDosagesValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ManufacturerValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.MedInstructionWardValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.MedicationInstructionValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.NdcByOutpatientSiteNdcValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.NdcValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.OiRouteValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.OrderUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.OrderableItemValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.PackageSizeValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.PackageTypeValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.PartialSaveValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.PharmacySystemValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.PossibleDosagesValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.ProductValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.RxConsultValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.SearchCriteriaValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.SearchRequestCriteriaValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.SearchTemplateValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.SingleMultiSourceProductValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.SpecialHandlingForProductValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.SpecialHandlingValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.StandardMedicationRouteValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.SynonymValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.UserValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.WardValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.group.DispenseQuantityLimitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.list.ApplicationPackageUseValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.list.DeaScheduleValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.list.FormularyValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.list.NcpdpDispenseUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.list.OrderableItemSynonymValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.list.ProductTypeValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.ArWsAmisConversionNumberValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.CurrentInventoryValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.DispenseDaysSupplyLimitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.MonitorMaxDaysValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.NdcPricePerDispenseUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.NdcPricePerOrderUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.NormalAmountToOrderValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.NumberOfDosesValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.ProductDispenseUnitPerOrderUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.ProductPricePerDispenseUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.ProductPricePerOrderUnitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.ReorderLevelValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.numeric.UnitPriceValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.CommonStringLength0To40Validator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.CommonStringLength1To120Validator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.CommonStringLength3To8Validator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.DayNdOrDoseNlLimitValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.FsnValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.InpatientPharmacyLocationValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.LocalSpecialHandlingValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.MessageValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.NcpdpQuantityMultiplierValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.PreviousNdcValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.ProductNumberValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.QuantityDispenseMessageValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.UnitDoseScheduleValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.UpcUpnValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.datafield.string.VAPrintNameValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.CmopIdValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.FormularyAlternativeValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.FrequencyValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.GCNSequenceNumberValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.LocalPrintNameValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.MaximumDosePerDayValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.MigratedDosageFormNameValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.NationalFormularyNameValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.NdcFieldValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.ProductStrengthValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.StrengthValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.TallManLetteringValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.VAProductNameValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.VendorStockNumberValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.VendorValidator;
import gov.va.med.pharmacy.peps.common.vo.validator.fields.VuidValidator;


/**
 * Key used in map of DataFields. Also used as portion of the key to localize the DataField name.
 * 
 * @param <T> Type of field this FieldKey represents.
 */
public class FieldKey<T> implements Serializable {

    /** LOG */
    public static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(FieldKey.class);

    //The keys listed below are not in alphabetical order.  They are all members of migration
    //EPL_MIGRATION_CONTROL TABLE

    /** USER_NAME */
    public static final FieldKey<String> USER_NAME = new FieldKey<String>("user.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** START_DTM */
    public static final FieldKey<Date> START_DTM = new FieldKey<Date>("start.date", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** STOP_DTM */
    public static final FieldKey<Date> STOP_DTM = new FieldKey<Date>("stop.date", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** START_DTM */
    public static final FieldKey<Date> FILTER_DTM = new FieldKey<Date>("filter.date", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** THREAD_ID */
    public static final FieldKey<Integer> THREAD_ID = new FieldKey<Integer>("thread.id", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Integer.class, false, true);

    /** MIGRATION_STATUS */
    public static final FieldKey<String> MIGRATION_STATUS = new FieldKey<String>("migration.status", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** USER_NDC_FILE */
    public static final FieldKey<String> USER_NDC_FILE = new FieldKey<String>("user.ndc.file", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** USER_OI_FILE */
    public static final FieldKey<String> USER_OI_FILE = new FieldKey<String>("user.OI.file", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** USER_PRODUCT_FILE */
    public static final FieldKey<String> USER_PRODUCT_FILE = new FieldKey<String>("user.product.file", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    //EPL_MIGRATION_FILE TABLE

    /** MIGRATION_FILE_NAME */
    public static final FieldKey<String> MIGRATION_FILE_NAME = new FieldKey<String>("migration.file.name",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** ROWS_PROCESSED_QTY */
    public static final FieldKey<Integer> ROWS_PROCESSED_QTY = new FieldKey<Integer>("rows.processed.qty",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Integer.class, false, true);

    /** ROWS_MIGRATED_QTY */
    public static final FieldKey<Integer> ROWS_MIGRATED_QTY = new FieldKey<Integer>("rows.migrated.qty",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Integer.class, false, true);

    /** ROWS_NOT_MIGRATED_QTY */
    public static final FieldKey<Integer> ROWS_NOT_MIGRATED_QTY = new FieldKey<Integer>("rows.not.migrated.qty",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Integer.class, false, true);

    //EPL_MIGRATION_ERROR TABLE

    /** MIGRATION_ROW_ID */
    public static final FieldKey<String> MIGRATION_ROW_ID = new FieldKey<String>("migration.row.id", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** MIGRATION_MULT_ROW_ID */
    public static final FieldKey<String> MIGRATION_MULT_ROW_ID = new FieldKey<String>("migration.mult.row.id",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** MIGRATION_FIELD_NAME */
    public static final FieldKey<String> MIGRATION_FIELD_NAME = new FieldKey<String>("migration.field.name",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** MIGRATION_MULT_FIELD_NAME */
    public static final FieldKey<String> MIGRATION_MULT_FIELD_NAME = new FieldKey<String>("migration.mult.field.name",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** DETAILED_ERROR_TEXT */
    public static final FieldKey<String> DETAILED_ERROR_TEXT = new FieldKey<String>("detailed.error.text",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** PROCESSED_DTM */
    public static final FieldKey<Date> PROCESSED_DTM = new FieldKey<Date>("processed.date", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    // Please keep keys in alphabetical order to ease future maintenance

    /** ABBREVIATION */
    public static final FieldKey<String> ABBREVIATION = new FieldKey<String>("abbreviation", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.LOCAL_MEDICATION_ROUTE);

//    public static final FieldKey<String> ADDRESS = new FieldKey<String>("address", FieldType.DATA_FIELD,
//                                                                        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH,
//                                                                        String.class, false, true,
//                                                                        EntityType.MANUFACTURER);

    /** ACCEPT_CHANGE */
    public static final FieldKey<Boolean> ACCEPT_CHANGE = new FieldKey<Boolean>("accept.change", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** ACTION_PROFILE_MESSAGE */
    public static final FieldKey<DataField<String>> ACTION_PROFILE_MESSAGE = new FieldKey<DataField<String>>(
        "action.profile.message", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL,
        String.class, false, true, CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** ADMIN_SCHEDULE_TYPE */
    public static final FieldKey<AdministrationScheduleTypeVo> ADMIN_SCHEDULE_TYPE =
        new FieldKey<AdministrationScheduleTypeVo>("admin.schedule.type", FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT,
            FieldEnvironment.LOCAL, AdministrationScheduleTypeVo.class, false, true, EntityType.ADMINISTRATION_SCHEDULE);

    /** ADVANCED_AND_SEARCH */
    public static final FieldKey<Boolean> ADVANCED_AND_SEARCH = new FieldKey<Boolean>("advanced.and.search",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** FSN */
    public static final FieldKey<DataField<String>> FSN = new FieldKey<DataField<String>>("fsn", FieldType.VA_DATA_FIELD,
        FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true, FsnValidator.class,
        EntityType.PRODUCT);

    /** SOURCE */
    public static final FieldKey<NdcSourceType> SOURCE = new FieldKey<NdcSourceType>("source", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, NdcSourceType.class, false, true, EntityType.NDC);

    /** LIFETIME_CUMULATIVE_DOSAGE */
    public static final FieldKey<DataField<Boolean>> LIFETIME_CUMULATIVE_DOSAGE = new FieldKey<DataField<Boolean>>(
        "lifetime.cumulative.dosage", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Boolean.class, false, true, EntityType.ORDERABLE_ITEM);

    /** ADDITIONAL_INSTRUCTION */
    public static final FieldKey<String> ADDITIONAL_INSTRUCTION = new FieldKey<String>("additional.instruction",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.MEDICATION_INSTRUCTION);

    /** ADMINISTRATION_TIMES */
    public static final FieldKey<String> ADMINISTRATION_TIMES = new FieldKey<String>("administration.times",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true);

    /** ALL */
    public static final FieldKey<Boolean> ALL = new FieldKey<Boolean>("all", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.BOTH, Boolean.class, false, true);

    /** ALL_REQUESTS */
    public static final FieldKey<Boolean> ALL_REQUESTS = new FieldKey<Boolean>("all.requests", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** APPLICATION_PACKAGE_USE */
    public static final FieldKey<ListDataField<String>> APPLICATION_PACKAGE_USE = new FieldKey<ListDataField<String>>(
        "application.package.use", FieldType.VA_DATA_FIELD, FieldSubType.MULTI_SELECT_LIST_DATA_FIELD,
        FieldEnvironment.LOCAL, String.class, false, true, ApplicationPackageUseValidator.class, EntityType.ORDERABLE_ITEM,
        EntityType.PRODUCT, EntityType.NDC);

    // To support advanced searches on the 'APPLICATION_PACKAGE_USE' field.

    /** APPLICATION_PACKAGE_USE_SEARCHABLE */
    public static final FieldKey<ListDataField<String>> APPLICATION_PACKAGE_USE_SEARCHABLE =
        new FieldKey<ListDataField<String>>("application.package.use.searchable", FieldType.VA_DATA_FIELD,
            FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD, FieldEnvironment.LOCAL, String.class, false, true);

    /** IFCAP_ITEM_NUMBER */
    public static final FieldKey<Collection<IfcapItemNumberVo>> IFCAP_ITEM_NUMBER =
        new FieldKey<Collection<IfcapItemNumberVo>>("ifcap.item.number", FieldType.VISTA_LINKED_DATA_FIELD,
            FieldSubType.MULTI_SELECT, FieldEnvironment.LOCAL, IfcapItemNumberVo.class, false, true, EntityType.PRODUCT);

    /** APPROVED_FOR_SPLITTING */
    public static final FieldKey<ListDataField<String>> APPROVED_FOR_SPLITTING = new FieldKey<ListDataField<String>>(
        "approved.for.splitting", FieldType.VA_DATA_FIELD, FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, EntityType.PRODUCT);

    /** AR_WS_AMIS_CATEGORY */
    public static final FieldKey<ListDataField<String>> AR_WS_AMIS_CATEGORY = new FieldKey<ListDataField<String>>(
        "ar.ws.amis.category", FieldType.VA_DATA_FIELD, FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, EntityType.PRODUCT);

    /** AR_WS_CONVERSION_NUMBER */
    public static final FieldKey<DataField<Long>> AR_WS_CONVERSION_NUMBER = new FieldKey<DataField<Long>>(
        "ar.ws.amis.conversion.number", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Long.class, false, true, ArWsAmisConversionNumberValidator.class, EntityType.PRODUCT);

    /** BUILD_BASELINE */
    public static final FieldKey<String> BUILD_BASELINE = new FieldKey<String>("build.baseline", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** BUILD_DATE */
    public static final FieldKey<String> BUILD_DATE = new FieldKey<String>("build.date", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** BUILD_VERSION */
    public static final FieldKey<String> BUILD_VERSION = new FieldKey<String>("build.version", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** CHECKBOX_INPUT */
    public static final FieldKey<Boolean> CHECKBOX_INPUT = new FieldKey<Boolean>("checkbox.input", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** CHEMOTHERAPY_MEDICATION */
    public static final FieldKey<DataField<Boolean>> CHEMOTHERAPY_MEDICATION = new FieldKey<DataField<Boolean>>(
        "chemotherapy.medication", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Boolean.class, false, true);

    /** CLASSIFICATION */
    public static final FieldKey<String> CLASSIFICATION = new FieldKey<String>("classification", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, false, EntityType.DRUG_CLASS);

    /** CLASSIFICATION_TYPE */
    public static final FieldKey<DrugClassificationTypeVo> CLASSIFICATION_TYPE = new FieldKey<DrugClassificationTypeVo>(
        "classification.type", FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.NATIONAL,
        DrugClassificationTypeVo.class, false, true, EntityType.DRUG_CLASS);

    /** CODE */
    public static final FieldKey<String> CODE = new FieldKey<String>("code", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.BOTH, String.class, false, false, EntityType.DRUG_CLASS);

    /** DOSAGE_FORM_NAME */
    public static final FieldKey<String> DOSAGE_FORM_NAME = new FieldKey<String>("dosage.form.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, true, false, EntityType.DOSAGE_FORM);

    /** DOSE_UNIT_NAME */
    public static final FieldKey<String> DOSE_UNIT_NAME = new FieldKey<String>("dose.unit.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, true, true, EntityType.DOSE_UNIT);

    /** DOSE_INDICATOR */
    public static final FieldKey<Boolean> DOSE_INDICATOR = new FieldKey<Boolean>("dose.indicator", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true, EntityType.DOSE_UNIT);

    /** CORRESPONDING_INPATIENT_DRUG */
    public static final FieldKey<DataField<String>> CORRESPONDING_INPATIENT_DRUG =
        new FieldKey<DataField<String>>("corresponding.inpatient.drug", FieldType.VA_DATA_FIELD,
            FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL, String.class, false, true,
            EntityType.PRODUCT);

    /** CORRESPONDING_OUTPATIENT_DRUG */
    public static final FieldKey<DataField<String>> CORRESPONDING_OUTPATIENT_DRUG =
        new FieldKey<DataField<String>>("corresponding.outpatient.drug", FieldType.VA_DATA_FIELD,
            FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL, String.class, false, true,
            EntityType.PRODUCT);

    /** CMOP_DISPENSE */
    public static final FieldKey<Boolean> CMOP_DISPENSE = new FieldKey<Boolean>("cmop.dispense", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, true, true, EntityType.PRODUCT);

    /** CMOP_ID */
    public static final FieldKey<String> CMOP_ID = new FieldKey<String>("cmop.id", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, false, CmopIdValidator.class, EntityType.PRODUCT);

    /** COLOR */
    public static final FieldKey<ColorVo> COLOR = new FieldKey<ColorVo>("color", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, ColorVo.class, false, true, EntityType.NDC);

    /** COMMENT */
    public static final FieldKey<String> COMMENT = new FieldKey<String>("comment", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** COMMENTS */
    public static final FieldKey<String> COMMENTS = new FieldKey<String>("comments", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** COMPOUNDED_MEDICATION */
    public static final FieldKey<DataField<Boolean>> COMPOUNDED_MEDICATION = new FieldKey<DataField<Boolean>>(
        "compounded.medication", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Boolean.class, false, true);

    /** CONSULT_TEXT */
    public static final FieldKey<String> CONSULT_TEXT = new FieldKey<String>("consult.text", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.RX_CONSULT);

    /** COTS_UPDATE */
    public static final FieldKey<String> COTS_UPDATE = new FieldKey<String>("cots.update", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** CS_FED_SCHEDULE */
    public static final FieldKey<CsFedScheduleVo> CS_FED_SCHEDULE = new FieldKey<CsFedScheduleVo>("cs.fed.schedule",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, CsFedScheduleVo.class, false, true,
        CsFedScheduleValidator.class, EntityType.PRODUCT);

    /** CURRENT_INVENTORY */
    public static final FieldKey<DataField<Long>> CURRENT_INVENTORY = new FieldKey<DataField<Long>>("current.inventory",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL, Long.class, false, true,
        CurrentInventoryValidator.class, EntityType.PRODUCT);

    /** CURRENT_VALUE */
    public static final FieldKey<String> CURRENT_VALUE = new FieldKey<String>("current.value", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** DATE_FORMAT */
    public static final FieldKey<DateFormat> DATE_FORMAT = new FieldKey<DateFormat>("date.format", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, DateFormat.class, false, true);

    /** DAW_CODE */
    public static final FieldKey<ListDataField<String>> DAW_CODE = new FieldKey<ListDataField<String>>("daw.code",
        FieldType.VA_DATA_FIELD, FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD, FieldEnvironment.BOTH, String.class, false,
        true, EntityType.PRODUCT);

    /** ADMINISTRATION_SCHEDULE */
    public static final FieldKey<AdministrationScheduleVo> ADMINISTRATION_SCHEDULE = new FieldKey<AdministrationScheduleVo>(
        "administration.schedule", FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.LOCAL,
        AdministrationScheduleVo.class, false, true, AdministrationScheduleValidator.class);

    /** DESCRIPTION */
    public static final FieldKey<String> DESCRIPTION = new FieldKey<String>("description", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, true, EntityType.DRUG_CLASS,
        EntityType.SPECIAL_HANDLING);

    /** DAY_ND_OR_DOSE_NL_LIMIT */
    public static final FieldKey<DataField<String>> DAY_ND_OR_DOSE_NL_LIMIT = new FieldKey<DataField<String>>(
        "day.nd.or.dose.nl.limit", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, DayNdOrDoseNlLimitValidator.class, EntityType.PRODUCT, EntityType.ORDERABLE_ITEM);

    /** DEA_SCHEDULE */
    public static final FieldKey<ListDataField<String>> DEA_SCHEDULE = new FieldKey<ListDataField<String>>("dea.schedule",
        FieldType.VA_DATA_FIELD, FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD, FieldEnvironment.BOTH, String.class, false,
        true, DeaScheduleValidator.class, EntityType.PRODUCT);

    /** DEFAULT_ADMIN_TIMES */
    public static final FieldKey<String> DEFAULT_ADMIN_TIMES = new FieldKey<String>("default.admin.times",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.MEDICATION_INSTRUCTION);

    /** DEFAULT_MAIL_SERVICE */
    public static final FieldKey<DataField<String>> DEFAULT_MAIL_SERVICE = new FieldKey<DataField<String>>(
        "default.mail.service", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class,
        false, true, CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** DETAIL_CATEGORY */
    public static final FieldKey<EventCategory> DETAIL_CATEGORY = new FieldKey<EventCategory>("detail.category",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, EventCategory.class, false, true);

    // Dosage form fields

    /** DISPENSE_UNIT_PER_DOSE */
    public static final FieldKey<DispenseUnitPerDoseVo> DISPENSE_UNIT_PER_DOSE = new FieldKey<DispenseUnitPerDoseVo>(
        "dispense.unit.per.dose", FieldType.DATA_FIELD, FieldSubType.VALUE_OBJECT, FieldEnvironment.BOTH,
        DispenseUnitPerDoseVo.class, false, true, DispenseUnitPerDoseValidator.class);

    /** STR_DISPENSE_UNIT_PER_DOSE */
    public static final FieldKey<String> STR_DISPENSE_UNIT_PER_DOSE = new FieldKey<String>("str.dispense.unit.per.dose",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** PACKAGE_CODE */
    public static final FieldKey<PackageCode> PACKAGE_CODE = new FieldKey<PackageCode>("package.code", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, PackageCode.class, false, true);

    /** DOSAGE_FORM_NOUN */
    public static final FieldKey<DosageFormNounVo> DOSAGE_FORM_NOUN = new FieldKey<DosageFormNounVo>("dosage.form.noun",
        FieldType.DATA_FIELD, FieldSubType.VALUE_OBJECT, FieldEnvironment.BOTH, DosageFormNounVo.class, false, true,
        DosageFormNounValidator.class);

    /** NOUN */
    public static final FieldKey<String> NOUN = new FieldKey<String>("noun", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.LOCAL, String.class, false, true);

    /** OTHER_LANGUAGE_NOUN */
    public static final FieldKey<String> OTHER_LANGUAGE_NOUN = new FieldKey<String>("other.language.noun",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true);

    /** DOSAGE_FORM_UNIT */
    public static final FieldKey<DosageFormUnitVo> DOSAGE_FORM_UNIT = new FieldKey<DosageFormUnitVo>("dosage.form.unit",
        FieldType.DATA_FIELD, FieldSubType.VALUE_OBJECT, FieldEnvironment.BOTH, DosageFormUnitVo.class, false, true,
        DosageFormUnitValidator.class);

    /** DISPENSE_DAYS_SUPPLY_LIMIT */
    public static final FieldKey<DataField<Long>> DISPENSE_DAYS_SUPPLY_LIMIT = new FieldKey<DataField<Long>>(
        "dispense.days.supply.limit", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Long.class, false, true, DispenseDaysSupplyLimitValidator.class, EntityType.PRODUCT);

    /** DISPENSE_LIMIT_FOR_ORDER */
    public static final FieldKey<DataField<String>> DISPENSE_LIMIT_FOR_ORDER = new FieldKey<DataField<String>>(
        "dispense.limit.for.order", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** DISPENSE_LIMIT_FOR_SCHEDULE */
    public static final FieldKey<DataField<String>> DISPENSE_LIMIT_FOR_SCHEDULE = new FieldKey<DataField<String>>(
        "dispense.limit.for.schedule", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** DISPENSE_OVERRIDE */
    public static final FieldKey<DataField<String>> DISPENSE_OVERRIDE = new FieldKey<DataField<String>>("dispense.override",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true,
        CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** DISPENSE_OVERRIDE_REASON */
    public static final FieldKey<DataField<String>> DISPENSE_OVERRIDE_REASON = new FieldKey<DataField<String>>(
        "dispense.override.reason", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** DISPENSE_QUANTITY_LIMIT_DOSE */
    public static final FieldKey<DataField<Long>> DISPENSE_QUANTITY_LIMIT_DOSE = new FieldKey<DataField<Long>>(
        "dispense.quantity.limit.dose", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Long.class, false, true);

    /** DISPENSE_QUANTITY_LIMIT_TIME */
    public static final FieldKey<DataField<Long>> DISPENSE_QUANTITY_LIMIT_TIME = new FieldKey<DataField<Long>>(
        "dispense.quantity.limit.time", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Long.class, false, true);

    /** DISPENSE_QUANTITY_LIMIT */
    public static final FieldKey<GroupDataField> DISPENSE_QUANTITY_LIMIT = new FieldKey<GroupDataField>(
        "dispense.quantity.limit", FieldType.VA_DATA_FIELD, FieldSubType.GROUP_DATA_FIELD, FieldEnvironment.BOTH,
        GroupDataField.class, false, true, DispenseQuantityLimitValidator.class, new FieldKey[] {
            DISPENSE_QUANTITY_LIMIT_DOSE, DISPENSE_QUANTITY_LIMIT_TIME }, EntityType.PRODUCT);

    /** DISPENSE_QUANTITY_OVERRIDE */
    public static final FieldKey<DataField<String>> DISPENSE_QUANTITY_OVERRIDE = new FieldKey<DataField<String>>(
        "dispense.quantity.override", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** DISPENSE_QUANTITY_OVERRIDE_REASON */
    public static final FieldKey<DataField<String>> DISPENSE_QUANTITY_OVERRIDE_REASON = new FieldKey<DataField<String>>(
        "dispense.quantity.override.reason", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** DISPENSE_UNIT */
    public static final FieldKey<DispenseUnitVo> DISPENSE_UNIT = new FieldKey<DispenseUnitVo>("dispense.unit",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, DispenseUnitVo.class, true, true,
        DispenseUnitValidator.class, EntityType.PRODUCT);

    /** DISPLAY_ON_IVP */
    public static final FieldKey<Boolean> DISPLAY_ON_IVP = new FieldKey<Boolean>("display.on.ivp", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, Boolean.class, false, true, EntityType.LOCAL_MEDICATION_ROUTE);

    /** DISPLAYABLE_INGREDIENT_STRENGTH */
    public static final FieldKey<String> DISPLAYABLE_INGREDIENT_STRENGTH = new FieldKey<String>(
        "displayable.ingredient.strength", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class,
        false, true, EntityType.PRODUCT);

    /** DISPLAYABLE_INGREDIENT_UNIT */
    public static final FieldKey<DrugUnitVo> DISPLAYABLE_INGREDIENT_UNIT = new FieldKey<DrugUnitVo>(
        "displayable.ingredient.unit", FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH,
        DrugUnitVo.class, false, true, EntityType.PRODUCT);

//    public static final FieldKey<String> DISPLAYABLE_INGREDIENT_UNIT = new FieldKey<String>("displayable.ingredient.unit",
//        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.PRODUCT);

    /** DISPLAYABLE_INGREDIENT_NAME */
    public static final FieldKey<IngredientVo> DISPLAYABLE_INGREDIENT_NAME = new FieldKey<IngredientVo>(
        "displayable.ingredient.name", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, IngredientVo.class, true, true, IngredientValidator.class);

//  public static final FieldKey<String> DISPLAYABLE_INGREDIENT_NAME = new FieldKey<String>("displayable.ingredient.name",
//  FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.PRODUCT);

    /** DISPLAYABLE_SYNONYM_NAME */
    public static final FieldKey<String> DISPLAYABLE_SYNONYM_NAME = new FieldKey<String>("displayable.synonym.name",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.PRODUCT);

    /** DO_NOT_HANDLE_IF_PREGNANT */
    public static final FieldKey<DataField<Boolean>> DO_NOT_HANDLE_IF_PREGNANT = new FieldKey<DataField<Boolean>>(
        "do.not.handle.if.pregnant", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Boolean.class, false, true, EntityType.PRODUCT);

    /** DO_NOT_MAIL */
    public static final FieldKey<DataField<Boolean>> DO_NOT_MAIL = new FieldKey<DataField<Boolean>>("do.not.mail",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Boolean.class, false, true,
        EntityType.PRODUCT);

    /** DOMAIN_NAME */
    public static final FieldKey<String> DOMAIN_NAME = new FieldKey<String>("domain.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** DOSAGE_FORM */
    public static final FieldKey<DosageFormVo> DOSAGE_FORM = new FieldKey<DosageFormVo>("dosage.form", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, DosageFormVo.class, false, false, DosageFormValidator.class,
        EntityType.ORDERABLE_ITEM);

    /** EXCLUDE_FROM_DOSAGE_CHKS */
    public static final FieldKey<ExcludeDosageCheck> EXCLUDE_FROM_DOSAGE_CHKS = new FieldKey<ExcludeDosageCheck>(
        "exclude.from.dosage.chks", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL,
        ExcludeDosageCheck.class, false, true, EntityType.DOSAGE_FORM);

    /** EXCLUDE_DRG_DRG_INTERACTION_CHECK */
    public static final FieldKey<Boolean> EXCLUDE_DRG_DRG_INTERACTION_CHECK = new FieldKey<Boolean>(
        "exclude.drg.drg.interaction.check", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL,
        Boolean.class, false, true, EntityType.PRODUCT);

    /** EXCLUDED */
    public static final FieldKey<String> EXCLUDED = new FieldKey<String>("excluded", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, true);

    /** DOSE_UNIT */
    public static final FieldKey<DoseUnitVo> DOSE_UNIT = new FieldKey<DoseUnitVo>("dose.unit", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, DoseUnitVo.class, true, false, DoseUnitValidator.class);

    /** DOSE_UNIT_SYNONYM_NAME */
    public static final FieldKey<String> DOSE_UNIT_SYNONYM_NAME = new FieldKey<String>("dose.unit.synonym.name",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** DOSE_UNIT_SYNONYMS */
    public static final FieldKey<Collection<DoseUnitSynonymVo>> DOSE_UNIT_SYNONYMS =
        new FieldKey<Collection<DoseUnitSynonymVo>>("dose.unit.synonyms", FieldType.DATA_FIELD,
            FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.NATIONAL, DoseUnitSynonymVo.class, false, true,
            new FieldKey[] { DOSE_UNIT_SYNONYM_NAME }, EntityType.DOSE_UNIT);

    /** DRUG_CLASS */
    public static final FieldKey<DrugClassVo> DRUG_CLASS = new FieldKey<DrugClassVo>("drug.class", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, DrugClassVo.class, false, true, DrugClassificationValidator.class);

    /** DRUG_CLASS_SORT_PREFERENCE */
    public static final FieldKey<DrugClassSortPreference> DRUG_CLASS_SORT_PREFERENCE = new FieldKey<DrugClassSortPreference>(
        "drug.class.sort.preference", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH,
        DrugClassSortPreference.class, false, true);

    /** PRIMARY */
    public static final FieldKey<Boolean> PRIMARY = new FieldKey<Boolean>("primary", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** DRUG_CLASSES */
    public static final FieldKey<Collection<DrugClassGroupVo>> DRUG_CLASSES = new FieldKey<Collection<DrugClassGroupVo>>(
        "drug.classes", FieldType.DATA_FIELD, FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.BOTH,
        DrugClassGroupVo.class, true, true, DrugClassValidator.class, new FieldKey[] { DRUG_CLASS, PRIMARY },
        EntityType.PRODUCT);

    /** TEXT_LOCAL */
    public static final FieldKey<String> TEXT_LOCAL = new FieldKey<String>("text.local", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.DRUG_TEXT);

    /** TEXT_NATIONAL */
    public static final FieldKey<String> TEXT_NATIONAL = new FieldKey<String>("text.national", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, true, EntityType.DRUG_TEXT);

    /** DT_MODIFIED */
    public static final FieldKey<Date> DT_MODIFIED = new FieldKey<Date>("dt.modified", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** ENTITY_TYPE */
    public static final FieldKey<EntityType> ENTITY_TYPE = new FieldKey<EntityType>("entity.type", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, EntityType.class, false, true);

    /** EVENT_CATEGORY */
    public static final FieldKey<EventCategory> EVENT_CATEGORY = new FieldKey<EventCategory>("event.category",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, EventCategory.class, false, true);

    /** DETAIL_EVENT_CATEGORY */
    public static final FieldKey<EventCategory> DETAIL_EVENT_CATEGORY = new FieldKey<EventCategory>("detail.event.category",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, EventCategory.class, false, true);

    /** EXPANSION */
    public static final FieldKey<String> EXPANSION = new FieldKey<String>("expansion", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.ORDER_UNIT);

    /** EXTERNAL_DATA_FIELDS */
    public static final FieldKey<Object> EXTERNAL_DATA_FIELDS = new FieldKey<Object>("external.data.fields",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Object.class, false, true);

    /** FDA_MED_GUIDE */
    public static final FieldKey<String> FDA_MED_GUIDE = new FieldKey<String>("fda.med.guide", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.PRODUCT);

    /** FDB_DEVICES */
    public static final FieldKey<Boolean> FDB_DEVICES = new FieldKey<Boolean>("fdb.devices", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true);

    /** FDB_DOSE_UNIT */
    public static final FieldKey<String> FDB_DOSE_UNIT = new FieldKey<String>("fdb.dose.unit", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, false, EntityType.DOSE_UNIT);

    /** FDB_ID */
    public static final FieldKey<String> FDB_ID = new FieldKey<String>("fdb.id", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** FDB_MED_ROUTE */
    public static final FieldKey<String> FDB_MED_ROUTE = new FieldKey<String>("first.databank.med.route",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, false,
        EntityType.STANDARD_MED_ROUTE);

    /** FDB_NAME_TYPE */
    public static final FieldKey<FdbNameType> FDB_NAME_TYPE = new FieldKey<FdbNameType>("fdb.name.type",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, FdbNameType.class, false,
        true);

    /** FDB_OBSOLETE_DRUGS */
    public static final FieldKey<Boolean> FDB_OBSOLETE_DRUGS = new FieldKey<Boolean>("fdb.obsolete.drugs",
        FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true);

    /** FDB_PACKAGED_DRUG */
    public static final FieldKey<FdbPackagedDrug> FDB_PACKAGED_DRUG = new FieldKey<FdbPackagedDrug>("fdb.packaged.drug",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, FdbPackagedDrug.class, false,
        true);

    /** FDB_PRIVATE_LABELERS */
    public static final FieldKey<Boolean> FDB_PRIVATE_LABELERS = new FieldKey<Boolean>("fdb.private.labelers",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true);

    /** FDB_LABEL **/
    public static final FieldKey<String> LABEL_NAME = new FieldKey<String>("label.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** FDB_PRODUCT_NAME **/
    public static final FieldKey<String> FDB_PRODUCT_NAME = new FieldKey<String>("fdb.product.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** ADDITIONAL INSTRUCTION **/
    public static final FieldKey<String> ADDITIONAL_DESC = new FieldKey<String>("add.desc", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** PRODUCT_FK */
    public static final FieldKey<Long> PRODUCT_FK = new FieldKey<Long>("product.fk", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Long.class, false, true);

    /** NDC_FK */
    public static final FieldKey<Long> NDC_FK = new FieldKey<Long>("ndc.id.fk", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Long.class, false, true);

    /** FDB_CREATION_DATE */
    public static final FieldKey<Date> FDB_CREATION_DATE = new FieldKey<Date>("fdb.creation.date", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** CREATED_DTM */
    public static final FieldKey<Date> CREATED_DTM = new FieldKey<Date>("created.dtm", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** OBSOLETE_DATE */
    public static final FieldKey<Date> OBSOLETE_DATE = new FieldKey<Date>("obsolete.date", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** GCN_SEQNO */
 //   public static final FieldKey<Long> GCN_SEQNO = new FieldKey<Long>("gcn.seqno",
 //       FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, Long.class, false, true);

    /** FDB_REPACKAGERS */
    public static final FieldKey<Boolean> FDB_REPACKAGERS = new FieldKey<Boolean>("fdb.repackagers", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true);

    /** FDB_RX_OTC */
    public static final FieldKey<FdbRxOtc> FDB_RX_OTC = new FieldKey<FdbRxOtc>("fdb.rx.otc",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, FdbRxOtc.class, false,
        true);

    /** FDB_PHONETIC_SEARCH */
    public static final FieldKey<FdbPhoneticSearch> FDB_PHONETIC_SEARCH = new FieldKey<FdbPhoneticSearch>(
        "fdb.phonetic.search",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, FdbPhoneticSearch.class, false,
        true);

    /** FDB_SEARCH_METHODS */
    public static final FieldKey<FdbSearchMethods> FDB_SEARCH_METHODS = new FieldKey<FdbSearchMethods>("fdb.search.methods",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, FdbSearchMethods.class, false,
        true);

    /** FDB_SEARCH_OPTION_TYPE */
    public static final FieldKey<FDBSearchOptionType> FDB_SEARCH_OPTION_TYPE = new FieldKey<FDBSearchOptionType>(
        "fdb.search.option.type", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH,
        FDBSearchOptionType.class, false, true);

    /** FDB_SINGLE_INGREDIENT */
    public static final FieldKey<Boolean> FDB_SINGLE_INGREDIENT = new FieldKey<Boolean>("fdb.single.ingredient",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true);

    /** FDB_STATUS_CODE_ACTIVE */
    public static final FieldKey<Boolean> FDB_STATUS_CODE_ACTIVE = new FieldKey<Boolean>("fdb.status.code.active",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true);

    /** FDB_STATUS_CODE_REPLACED */
    public static final FieldKey<Boolean> FDB_STATUS_CODE_REPLACED = new FieldKey<Boolean>("fdb.status.code.replaced",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true);

    /** FDB_STATUS_CODE_RETIRED */
    public static final FieldKey<Boolean> FDB_STATUS_CODE_RETIRED = new FieldKey<Boolean>("fdb.status.code.retired",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true);

    /** FDB_STATUS_CODE_INACTIVE */
    public static final FieldKey<Boolean> FDB_STATUS_CODE_INACTIVE = new FieldKey<Boolean>("fdb.status.code.inactive",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true);

    /** FDB_STATUS_CODE_UNASSOCIATED */
    public static final FieldKey<Boolean> FDB_STATUS_CODE_UNASSOCIATED = new FieldKey<Boolean>(
        "fdb.status.code.unassociated",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true);

    /** POSSIBLE_DOSAGES_AUTO_CREATE */
    public static final FieldKey<PossibleDosagesAutoCreate> POSSIBLE_DOSAGES_AUTO_CREATE =
        new FieldKey<PossibleDosagesAutoCreate>("possible.dosages.auto.create", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
            FieldEnvironment.NATIONAL, PossibleDosagesAutoCreate.class, false, true, EntityType.PRODUCT);

    // product packages

    /** PRODUCT_PACKAGE */
    public static final FieldKey<ProductPackage> PRODUCT_PACKAGE = new FieldKey<ProductPackage>("product.package",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, ProductPackage.class, false, true,
        EntityType.PRODUCT);

    /** FDB_SEARCH_STRING */
    public static final FieldKey<String> FDB_SEARCH_STRING = new FieldKey<String>("fdb.search.string", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** FIELD_NAME */
    public static final FieldKey<String> FIELD_NAME = new FieldKey<String>("field.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** FIRST_NAME */
    public static final FieldKey<String> FIRST_NAME = new FieldKey<String>("first.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** FOLLOW_UP_TIME */
    public static final FieldKey<DataField<Boolean>> FOLLOW_UP_TIME = new FieldKey<DataField<Boolean>>("follow.up.time",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Boolean.class, false, true,
        EntityType.PRODUCT);

    /** FORMULARY */
    public static final FieldKey<ListDataField<String>> FORMULARY = new FieldKey<ListDataField<String>>("formulary",
        FieldType.VA_DATA_FIELD, FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD, FieldEnvironment.LOCAL, String.class, false,
        true, FormularyValidator.class, EntityType.PRODUCT);

    /** FORMULARY_ALTERNATIVE */
    public static final FieldKey<DataField<String>> FORMULARY_ALTERNATIVE = new FieldKey<DataField<String>>(
        "formulary.alternative", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD,
        FieldEnvironment.BOTH, String.class, false, true, FormularyAlternativeValidator.class, EntityType.PRODUCT);

    /*   *//** FORMULARY_STATUS */

    /*
    public static final FieldKey<FormularyStatus> FORMULARY_STATUS = new FieldKey<FormularyStatus>("formulary.status",
    FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, FormularyStatus.class, false, true,
    EntityType.ORDERABLE_ITEM);*/

    /** FREQUENCY */
    public static final FieldKey<Long> FREQUENCY = new FieldKey<Long>("frequency", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, Long.class, false, true, FrequencyValidator.class,
        EntityType.ADMINISTRATION_SCHEDULE, EntityType.MEDICATION_INSTRUCTION);

    /** FSS_I */
    public static final FieldKey<String> FSS_I = new FieldKey<String>("fss.i", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.NATIONAL, String.class, false, true, EntityType.NDC);

    /** FSS_PV */
    public static final FieldKey<Boolean> FSS_PV = new FieldKey<Boolean>("fss.pv", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true, EntityType.NDC);

    /** FSS_CNT_NO */
    public static final FieldKey<String> FSS_CNT_NO = new FieldKey<String>("fss.cnt.no", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, true, EntityType.NDC);

    /** FSS_FSS_END */
    public static final FieldKey<Date> FSS_FSS_END = new FieldKey<Date>("fss.fss.end", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Date.class, false, true, EntityType.NDC);

    /** FSS_FSS_PRICE */
    public static final FieldKey<Double> FSS_FSS_PRICE = new FieldKey<Double>("fss.fss.price", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Double.class, true, true, EntityType.NDC);

    /** FSS_VA_PRICE */
    public static final FieldKey<Double> FSS_VA_PRICE = new FieldKey<Double>("fss.va.price", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Double.class, true, true, EntityType.NDC);

    /** FSS_BIG4_PRICE */
    public static final FieldKey<Double> FSS_BIG4_PRICE = new FieldKey<Double>("fss.big4.price", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Double.class, true, true, EntityType.NDC);

    /** FSS_BPA_PRICE */
    public static final FieldKey<Double> FSS_BPA_PRICE = new FieldKey<Double>("fss.bpa.price", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Double.class, true, true, EntityType.NDC);

    /** FSS_BPA_AVAIL */
    public static final FieldKey<Boolean> FSS_BPA_AVAIL = new FieldKey<Boolean>("fss.bpa.avail", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true, EntityType.NDC);

    /** FSS_NC_PRICE */
    public static final FieldKey<Double> FSS_NC_PRICE = new FieldKey<Double>("fss.nc.price", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Double.class, true, true, EntityType.NDC);

    /** GCN_SEQUENCE_NUMBER */
    public static final FieldKey<String> GCN_SEQUENCE_NUMBER = new FieldKey<String>("gcn.sequence.number",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true,
        GCNSequenceNumberValidator.class, EntityType.PRODUCT);

    /** IEN */
    public static final FieldKey<Long> IEN = new FieldKey<Long>("ien", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.LOCAL, Long.class, true, false, EntityType.PRODUCT);

    /** GENERIC_NAME */
    public static final FieldKey<GenericNameVo> GENERIC_NAME = new FieldKey<GenericNameVo>("generic.name",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, GenericNameVo.class, true, true,
        GenericNameValidator.class, EntityType.PRODUCT);

    /** HAZARDOUS_TO_DISPOSE */
    public static final FieldKey<DataField<Boolean>> HAZARDOUS_TO_DISPOSE = new FieldKey<DataField<Boolean>>(
        "hazardous.to.dispose", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Boolean.class, false, true, EntityType.PRODUCT);

    /** HAZARDOUS_TO_HANDLE */
    public static final FieldKey<DataField<Boolean>> HAZARDOUS_TO_HANDLE = new FieldKey<DataField<Boolean>>(
        "hazardous.to.handle", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Boolean.class,
        false, true, EntityType.PRODUCT);

    /** HAZARDOUS_TO_PATIENT */
    public static final FieldKey<DataField<Boolean>> HAZARDOUS_TO_PATIENT = new FieldKey<DataField<Boolean>>(
        "hazardous.to.patient", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Boolean.class, false, true, EntityType.PRODUCT);
    
    /** PRIMARY_EPA_CODE */
    public static final FieldKey<DataField<String>> PRIMARY_EPA_CODE = new FieldKey<DataField<String>>(
        "primary.epa.code", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, EntityType.PRODUCT);
    
    /** DOT_SHIPPING_CODE */
    public static final FieldKey<DataField<String>> DOT_SHIPPING_NAME = new FieldKey<DataField<String>>(
        "dot.shipping.name", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, EntityType.PRODUCT);
    
    /** WASTE_SORT_CODE */
    public static final FieldKey<DataField<String>> WASTE_SORT_CODE = new FieldKey<DataField<String>>(
        "waste.sort.code", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, EntityType.PRODUCT);

    /** HIGH_ALERT */
    public static final FieldKey<DataField<String>> HIGH_ALERT = new FieldKey<DataField<String>>("high.alert",
        FieldType.DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true,
        CommonStringLength1To120Validator.class, EntityType.ORDERABLE_ITEM);

    /** HIGH_RISK */
    public static final FieldKey<DataField<Boolean>> HIGH_RISK = new FieldKey<DataField<Boolean>>("high.risk",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Boolean.class, false, true,
        EntityType.PRODUCT);

    /** HIGH_RISK_FOLLOWUP */
    public static final FieldKey<DataField<Boolean>> HIGH_RISK_FOLLOWUP = new FieldKey<DataField<Boolean>>(
        "high.risk.followup", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Boolean.class,
        false, true, EntityType.PRODUCT);

    /** HIGH_RISK_FOLLOWUP_TIME_PERIOD */
    public static final FieldKey<DataField<String>> HIGH_RISK_FOLLOWUP_TIME_PERIOD = new FieldKey<DataField<String>>(
        "high.risk.followup.time.period", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** NOTIFICATION_ID */
    public static final FieldKey<Long> NOTIFICATION_ID = new FieldKey<Long>("notification.id", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Long.class, false, true);

    /** NOTIFICATIONS */
    public static final FieldKey<String> NOTIFICATIONS = new FieldKey<String>("homepage.notifications", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** NOTIFICATION_STATUS */
    public static final FieldKey<String> NOTIFICATION_STATUS = new FieldKey<String>("notification.status",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** PARTIAL_SAVE */
    public static final FieldKey<PartialSaveVo> PARTIAL_SAVE = new FieldKey<PartialSaveVo>("partial.save",
        FieldType.DATA_FIELD, FieldSubType.VALUE_OBJECT, FieldEnvironment.BOTH, PartialSaveVo.class, false, true,
        PartialSaveValidator.class);

    /** PARTIAL_SAVES */
    public static final FieldKey<String> PARTIAL_SAVES = new FieldKey<String>("homepage.partialsaves", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** HOSPITAL_LOCATION_SELECTION */
    public static final FieldKey<HospitalLocationSelectionVo> HOSPITAL_LOCATION_SELECTION =
        new FieldKey<HospitalLocationSelectionVo>("hospital.location.selection", FieldType.VISTA_LINKED_DATA_FIELD,
            FieldSubType.SINGLE_SELECT, FieldEnvironment.LOCAL, HospitalLocationSelectionVo.class, false, true);

    /** ID */
    public static final FieldKey<String> ID = new FieldKey<String>("id", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.BOTH, String.class, false, true, EntityType.ORDERABLE_ITEM, EntityType.PRODUCT, EntityType.NDC);

    /** PARENT_ID */
    public static final FieldKey<String> PARENT_ID = new FieldKey<String>("parent.id", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** IMAGE */
    public static final FieldKey<String> IMAGE = new FieldKey<String>("image", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.BOTH, String.class, false, true, EntityType.NDC);

    /** IMPRINT */
    public static final FieldKey<String> IMPRINT = new FieldKey<String>("imprint", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.NDC);

    /** IMPRINT2 */
    public static final FieldKey<String> IMPRINT2 = new FieldKey<String>("imprint2", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.NDC);

    /** INPATIENT_MEDICATION_EXPIRED_ORDER_MAX_TIME */
    public static final FieldKey<DataField<String>> INPATIENT_MEDICATION_EXPIRED_ORDER_MAX_TIME =
        new FieldKey<DataField<String>>("inpatient.medication.expired.order.max.time", FieldType.VA_DATA_FIELD,
            FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true,
            CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** INPATIENT_MEDICATION_EXPIRED_ORDER_MIN_TIME */
    public static final FieldKey<DataField<String>> INPATIENT_MEDICATION_EXPIRED_ORDER_MIN_TIME =
        new FieldKey<DataField<String>>("inpatient.medication.expired.order.min.time", FieldType.VA_DATA_FIELD,
            FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true,
            CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** INPATIENT_PHARMACY_LOCATION */
    public static final FieldKey<DataField<String>> INPATIENT_PHARMACY_LOCATION = new FieldKey<DataField<String>>(
        "inpatient.pharm.locations", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL,
        String.class, false, true, InpatientPharmacyLocationValidator.class, EntityType.PRODUCT);

    /** INSTRUCTIONS */
    public static final FieldKey<String> INSTRUCTIONS = new FieldKey<String>("instructions", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.MEDICATION_INSTRUCTION);

    /** INTENDED_USE */
    public static final FieldKey<Collection<PossibleIntendedUseVo>> INTENDED_USE =
        new FieldKey<Collection<PossibleIntendedUseVo>>("intended.use", FieldType.DATA_FIELD, FieldSubType.MULTI_SELECT,
            FieldEnvironment.LOCAL, PossibleIntendedUseVo.class, false, true, EntityType.MEDICATION_INSTRUCTION);

    /** INVESTIGATIONAL_MEDICATION */
    public static final FieldKey<DataField<Boolean>> INVESTIGATIONAL_MEDICATION = new FieldKey<DataField<Boolean>>(
        "investigational.medication", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Boolean.class, false, true);

    /** ITEM_NAME */
    public static final FieldKey<String> ITEM_NAME = new FieldKey<String>("item.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** ITEM_STATUS */
    public static final FieldKey<ItemStatus> ITEM_STATUS = new FieldKey<ItemStatus>("item.status", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, ItemStatus.class, false, true, EntityType.ORDERABLE_ITEM,
        EntityType.PRODUCT, EntityType.NDC, EntityType.INGREDIENT, EntityType.DRUG_UNIT, EntityType.DRUG_CLASS,
        EntityType.PACKAGE_TYPE, EntityType.DOSAGE_FORM, EntityType.ADMINISTRATION_SCHEDULE, EntityType.GENERIC_NAME,
        EntityType.LOCAL_MEDICATION_ROUTE, EntityType.ORDER_UNIT, EntityType.DISPENSE_UNIT,
        EntityType.MEDICATION_INSTRUCTION, EntityType.MANUFACTURER, EntityType.STANDARD_MED_ROUTE, EntityType.DOSE_UNIT,
        EntityType.RX_CONSULT, EntityType.DRUG_TEXT, EntityType.SPECIAL_HANDLING);

    /** IV_FLAG */
    public static final FieldKey<Boolean> IV_FLAG = new FieldKey<Boolean>("iv.flag", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, Boolean.class, false, true, EntityType.LOCAL_MEDICATION_ROUTE);

    /** LAB_ASSOCIATED */
    public static final FieldKey<DataField<String>> LAB_ASSOCIATED = new FieldKey<DataField<String>>("lab.associated",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true);

    /** LAB_INSTANCE_TIME_RANGE */
    public static final FieldKey<DataField<String>> LAB_INSTANCE_TIME_RANGE = new FieldKey<DataField<String>>(
        "lab.instance.time.range", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true);

    /** LAB_TEST_MONITOR */
    public static final FieldKey<LabTestMonitorVo> LAB_TEST_MONITOR = new FieldKey<LabTestMonitorVo>("lab.test.monitor",
        FieldType.VISTA_LINKED_DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.LOCAL, LabTestMonitorVo.class, false,
        true, EntityType.PRODUCT);

    /** LAB_VALUE */
    public static final FieldKey<DataField<String>> LAB_VALUE = new FieldKey<DataField<String>>("lab.value",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.PRODUCT);

    /** LABS_ADMINISTRATION */
    public static final FieldKey<Collection<LabDisplayAdministrationVo>> LABS_ADMINISTRATION =
        new FieldKey<Collection<LabDisplayAdministrationVo>>("lab.display.administration", FieldType.VISTA_LINKED_DATA_FIELD,
            FieldSubType.MULTI_SELECT, FieldEnvironment.LOCAL, LabDisplayAdministrationVo.class, false, true,
            EntityType.ORDERABLE_ITEM, EntityType.PRODUCT);

    /** LABS_ORDER_ENTRY */
    public static final FieldKey<Collection<LabDisplayOrderEntryVo>> LABS_ORDER_ENTRY =
        new FieldKey<Collection<LabDisplayOrderEntryVo>>("lab.display.order.entry", FieldType.VISTA_LINKED_DATA_FIELD,
            FieldSubType.MULTI_SELECT, FieldEnvironment.LOCAL, LabDisplayOrderEntryVo.class, false, true,
            EntityType.ORDERABLE_ITEM, EntityType.PRODUCT);

    /** SCHEDULE */
    public static final FieldKey<AdministrationScheduleVo> SCHEDULE =
        new FieldKey<AdministrationScheduleVo>("schedule", FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT,
            FieldEnvironment.LOCAL, AdministrationScheduleVo.class, false, true);

    /** DEFAULT_SCHEDULE */
    public static final FieldKey<Boolean> DEFAULT_SCHEDULE = new FieldKey<Boolean>("default.schedule", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, Boolean.class, false, true);

    /** ADMIN_SCHEDULES */
    public static final FieldKey<Collection<AdministrationScheduleAssocVo>> ADMIN_SCHEDULES =
        new FieldKey<Collection<AdministrationScheduleAssocVo>>("admin.schedules", FieldType.DATA_FIELD,
            FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.LOCAL, AdministrationScheduleAssocVo.class, false, true,
            AdminSchedulesValidator.class, new FieldKey[] { ADMINISTRATION_SCHEDULE, DEFAULT_SCHEDULE },
            EntityType.ORDERABLE_ITEM);

    /** NUMBER_LAB_INSTANCE_DISPLAYED */
    public static final FieldKey<DataField<String>> NUMBER_LAB_INSTANCE_DISPLAYED = new FieldKey<DataField<String>>(
        "number.lab.instance.displayed", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL,
        String.class, false, true, EntityType.PRODUCT);

    /** NUMBER_LABS_ASSOCIATED */
    public static final FieldKey<DataField<String>> NUMBER_LABS_ASSOCIATED = new FieldKey<DataField<String>>(
        "number.labs.associated", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL,
        String.class, false, true, EntityType.PRODUCT);

    /** LABS_ORDER_FINISH */
    public static final FieldKey<Collection<LabDisplayFinishingAnOrderVo>> LABS_ORDER_FINISH =
        new FieldKey<Collection<LabDisplayFinishingAnOrderVo>>("lab.display.finishing.an.order",
            FieldType.VISTA_LINKED_DATA_FIELD, FieldSubType.MULTI_SELECT, FieldEnvironment.LOCAL,
            LabDisplayFinishingAnOrderVo.class, false, true, EntityType.ORDERABLE_ITEM, EntityType.PRODUCT);

    /** LAST_COMMENT */
    public static final FieldKey<String> LAST_COMMENT = new FieldKey<String>("last.comment", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** LAST_NAME */
    public static final FieldKey<String> LAST_NAME = new FieldKey<String>("last.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** LOCAL_MEDICATION_ROUTE */
    public static final FieldKey<LocalMedicationRouteVo> LOCAL_MEDICATION_ROUTE = new FieldKey<LocalMedicationRouteVo>(
        "local.medication.route", FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.LOCAL,
        LocalMedicationRouteVo.class, false, true, LocalMedicationRouteValidator.class, EntityType.MEDICATION_INSTRUCTION);

    /** LOCAL_MED_ROUTES */
    public static final FieldKey<Collection<LocalMedicationRouteVo>> LOCAL_MED_ROUTES =
        new FieldKey<Collection<LocalMedicationRouteVo>>("local.med.routes", FieldType.DATA_FIELD, FieldSubType.MULTI_SELECT,
            FieldEnvironment.LOCAL, LocalMedicationRouteVo.class, false, true, EntityType.DOSAGE_FORM);

    /** LOCAL_NON_FORMULARY */
    public static final FieldKey<DataField<Boolean>> LOCAL_NON_FORMULARY = new FieldKey<DataField<Boolean>>(
        "local.non.formulary", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL,
        Boolean.class, false, true, EntityType.PRODUCT);

    /** LOCAL_SPECIAL_HANDLING */
    public static final FieldKey<DataField<String>> LOCAL_SPECIAL_HANDLING = new FieldKey<DataField<String>>(
        "local.special.handling", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL,
        String.class, false, true, LocalSpecialHandlingValidator.class, EntityType.PRODUCT);

    /** LOCAL_USE */
    public static final FieldKey<Boolean> LOCAL_USE = new FieldKey<Boolean>("local.use", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, Boolean.class, false, true, EntityType.PRODUCT,
        EntityType.ORDERABLE_ITEM);

    /** CREATE_POSSIBLE_DOSAGE */
    public static final FieldKey<Boolean> CREATE_POSSIBLE_DOSAGE = new FieldKey<Boolean>("create.possible.dosage",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, false, true,
        EntityType.PRODUCT);

    /** PREVIOUSLY_MARKED_FOR_LOCAL_USE */
    public static final FieldKey<Boolean> PREVIOUSLY_MARKED_FOR_LOCAL_USE = new FieldKey<Boolean>(
        "previously.marked.for.local.use", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL,
        Boolean.class, false, true, EntityType.PRODUCT, EntityType.ORDERABLE_ITEM);

    /** LOCAL_DISPENSE */
    public static final FieldKey<Boolean> LOCAL_DISPENSE = new FieldKey<Boolean>("local.dispense", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, Boolean.class, true, true, EntityType.NDC);

    /** LOCAL_PRINT_NAME */
    public static final FieldKey<String> LOCAL_PRINT_NAME = new FieldKey<String>("local.print.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, LocalPrintNameValidator.class,
        EntityType.PRODUCT);

    /** LOCATION */
    public static final FieldKey<String> LOCATION = new FieldKey<String>("location", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** LONG_TERM_OUT_OF_STOCK */
    public static final FieldKey<DataField<Boolean>> LONG_TERM_OUT_OF_STOCK = new FieldKey<DataField<Boolean>>(
        "long.term.out.of.stock", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Boolean.class, false, true, EntityType.PRODUCT);

    /** LOW_SAFETY_MARGIN */
    public static final FieldKey<DataField<Boolean>> LOW_SAFETY_MARGIN = new FieldKey<DataField<Boolean>>(
        "low.safety.margin", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Boolean.class,
        false, true, EntityType.PRODUCT);

    /** MANUFACTURER */
    public static final FieldKey<ManufacturerVo> MANUFACTURER = new FieldKey<ManufacturerVo>("manufacturer",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, ManufacturerVo.class, false, true,
        ManufacturerValidator.class, EntityType.NDC);

    /** MASTER_ENTRY_FOR_VUID */
    public static final FieldKey<Boolean> MASTER_ENTRY_FOR_VUID = new FieldKey<Boolean>("master.entry.for.vuid",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Boolean.class, true, false,
        EntityType.PRODUCT, EntityType.DRUG_CLASS, EntityType.GENERIC_NAME, EntityType.INGREDIENT,
        EntityType.STANDARD_MED_ROUTE);

    /** MAX_DISPENSE_LIMIT */
    public static final FieldKey<DataField<String>> MAX_DISPENSE_LIMIT = new FieldKey<DataField<String>>(
        "max.dispense.limit", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class,
        false, true, CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** MAX_DOSE_PER_DAY */
    public static final FieldKey<Long> MAX_DOSE_PER_DAY = new FieldKey<Long>("max.dose.per.day", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Long.class, false, true, MaximumDosePerDayValidator.class,
        EntityType.PRODUCT);

    /** MED_INSTRUCTION_EXPANSION */
    public static final FieldKey<String> MED_INSTRUCTION_EXPANSION = new FieldKey<String>("med.instruction.expansion",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.MEDICATION_INSTRUCTION);

    /** MED_INSTRUCTION_SCHEDULE */
    public static final FieldKey<String> MED_INSTRUCTION_SCHEDULE = new FieldKey<String>("med.instruction.schedule",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true,
        EntityType.MEDICATION_INSTRUCTION);

    /** MED_INSTRUCTION_SYNONYM */
    public static final FieldKey<String> MED_INSTRUCTION_SYNONYM = new FieldKey<String>("med.instruction.synonym",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true,
        EntityType.MEDICATION_INSTRUCTION);

    /** MED_ROUTE */
    public static final FieldKey<String> MED_ROUTE = new FieldKey<String>("med.route", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** MEDICATION_INSTRUCTION */
    public static final FieldKey<MedicationInstructionVo> MEDICATION_INSTRUCTION = new FieldKey<MedicationInstructionVo>(
        "medication.instruction", FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH,
        MedicationInstructionVo.class, true, true, MedicationInstructionValidator.class);

    /** MESSAGE */
    public static final FieldKey<DataField<String>> MESSAGE = new FieldKey<DataField<String>>("message",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true,
        MessageValidator.class, EntityType.PRODUCT);

    /** MIGRATED_DOSAGE_FORM_NAME */
    public static final FieldKey<String> MIGRATED_DOSAGE_FORM_NAME = new FieldKey<String>("migrated.dosage.form.name",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, true, true,
        MigratedDosageFormNameValidator.class, EntityType.PRODUCT);

    /** MOD_REQUEST_ITEM_STATUS */
    public static final FieldKey<RequestItemStatus> MOD_REQUEST_ITEM_STATUS = new FieldKey<RequestItemStatus>(
        "mod.request.item.status", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH,
        RequestItemStatus.class, false, true);

    /** MODIFICATION_REASON */
    public static final FieldKey<String> MODIFICATION_REASON = new FieldKey<String>("modification.reason",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** CREATED_DATE */
    public static final FieldKey<Date> CREATED_DATE = new FieldKey<Date>("created.date", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** CREATED_BY */
    public static final FieldKey<String> CREATED_BY = new FieldKey<String>("created.by", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** MODIFIED_DATE */
    public static final FieldKey<Date> MODIFIED_DATE = new FieldKey<Date>("modified.date", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** MODIFIED_BY */
    public static final FieldKey<String> MODIFIED_BY = new FieldKey<String>("modified.by", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** MODIFIED_VALUE */
    public static final FieldKey<String> MODIFIED_VALUE = new FieldKey<String>("modified.value", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** MONITOR_MAX_DAYS */
    public static final FieldKey<DataField<Long>> MONITOR_MAX_DAYS = new FieldKey<DataField<Long>>("monitor.max.days",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Long.class, false, true,
        MonitorMaxDaysValidator.class, EntityType.PRODUCT);

    /** NAME */
    public static final FieldKey<String> NAME = new FieldKey<String>("name", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.BOTH, String.class, false, true);

    /** NATIONAL_FORMULARY_INDICATOR */
    public static final FieldKey<Boolean> NATIONAL_FORMULARY_INDICATOR = new FieldKey<Boolean>(
        "national.formulary.indicator", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL,
        Boolean.class, false, true, EntityType.PRODUCT, EntityType.ORDERABLE_ITEM);

    /** NATIONAL_FORMULARY_NAME */
    public static final FieldKey<String> NATIONAL_FORMULARY_NAME = new FieldKey<String>("national.formulary.name",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, false,
        NationalFormularyNameValidator.class, EntityType.PRODUCT);

    /** OVERRIDE_DF_DOSE_CHK_EXCLUSN */
    public static final FieldKey<Boolean> OVERRIDE_DF_DOSE_CHK_EXCLUSN = new FieldKey<Boolean>(
        "override.df.dose.chk.exclusn", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL,
        Boolean.class, false, true, EntityType.PRODUCT);

    /** NATIONAL_REJECT_REASON */
    public static final FieldKey<String> NATIONAL_REJECT_REASON = new FieldKey<String>("national.reject.reason",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** NATIONAL_REJECT_REASON_ADDITIONAL_TEXT */
    public static final FieldKey<String> NATIONAL_REJECT_REASON_ADDITIONAL_TEXT = new FieldKey<String>(
        "national.reject.reason.additional.text", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH,
        String.class, false, true);

    /** NATIONAL_STATUS_DATE */
    public static final FieldKey<Date> NATIONAL_STATUS_DATE = new FieldKey<Date>("national.status.date",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** NDCVO */
    public static final FieldKey<NdcVo> NDCVO = new FieldKey<NdcVo>("ndcvo", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, NdcVo.class, false, true, NdcValidator.class);

    /** NDF_PRODUCT_IEN */
    public static final FieldKey<Long> NDF_PRODUCT_IEN = new FieldKey<Long>("ndf.product.ien", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, Long.class, false, false, EntityType.PRODUCT);

    /** MANUFACTURER_IEN */
    public static final FieldKey<String> MANUFACTURER_IEN = new FieldKey<String>("manufacturer.ien", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, false, EntityType.MANUFACTURER);

    /** NDF_SYNCH_QUEUE */
    public static final FieldKey<NdfSynchQueueVo> NDF_SYNCH_QUEUE = new FieldKey<NdfSynchQueueVo>("ndf.synch.queue",
        FieldType.DATA_FIELD, FieldSubType.VALUE_OBJECT, FieldEnvironment.NATIONAL, NdfSynchQueueVo.class, false, true);

    /** PACKAGETYPE_IEN */
    public static final FieldKey<String> PACKAGETYPE_IEN = new FieldKey<String>("packagetype.ien", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, false, EntityType.PACKAGE_TYPE);

    /** NDC_IEN */
    public static final FieldKey<String> NDC_IEN = new FieldKey<String>("ndc.ien", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, false, EntityType.NDC);

    /** GENERIC_IEN */
    public static final FieldKey<String> GENERIC_IEN = new FieldKey<String>("generic.ien", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, false, EntityType.GENERIC_NAME);

    /** INGREDIENT_IEN */
    public static final FieldKey<String> INGREDIENT_IEN = new FieldKey<String>("ingredient.ien", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, false, EntityType.INGREDIENT);

    /** DRUGCLASS_IEN */
    public static final FieldKey<String> DRUGCLASS_IEN = new FieldKey<String>("drugClass.ien", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, false, EntityType.DRUG_CLASS);

    /** DOSAGE_FORM_IEN */
    public static final FieldKey<String> DOSAGE_FORM_IEN = new FieldKey<String>("dosageForm.ien", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, false, EntityType.DOSAGE_FORM);

    /** DRUG_UNIT_IEN */
    public static final FieldKey<String> DRUG_UNIT_IEN = new FieldKey<String>("drugUnit.ien", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, false, EntityType.DRUG_UNIT);

    /** DISPENSE_UNIT_IEN */
    public static final FieldKey<String> DISPENSE_UNIT_IEN = new FieldKey<String>("dispenseUnit.ien", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, false, EntityType.DISPENSE_UNIT);

    /** NDC */
    public static final FieldKey<String> NDC = new FieldKey<String>("ndc", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.BOTH, String.class, false, true, NdcFieldValidator.class, EntityType.NDC);

    /** NDC_COUNT */
    public static final FieldKey<Integer> NDC_COUNT = new FieldKey<Integer>("ndc.count", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Integer.class, false, true, EntityType.PRODUCT);

    /** NDC_LIST */
    public static final FieldKey<List<NdcVo>> NDC_LIST = new FieldKey<List<NdcVo>>("ndcs", FieldType.DATA_FIELD,
        FieldSubType.MULTI_SELECT, FieldEnvironment.BOTH, NdcVo.class, false, true, EntityType.PRODUCT);

    /** NDC_LONG_NAME */
    public static final FieldKey<String> NDC_LONG_NAME = new FieldKey<String>("ndc.long.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** NDC_NUMBER */
    public static final FieldKey<String> NDC_NUMBER = new FieldKey<String>("ndc.number", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** NDC_DISP_UNITS_PER_ORD_UNIT */
    public static final FieldKey<Double> NDC_DISP_UNITS_PER_ORD_UNIT = new FieldKey<Double>("ndc.disp.units.per.ord.unit",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Double.class, false, true, EntityType.NDC);

    /** NDC_PRICE_PER_DISPENSE_UNIT */
    public static final FieldKey<DataField<Double>> NDC_PRICE_PER_DISPENSE_UNIT = new FieldKey<DataField<Double>>(
        "ndc.price.per.dispense.unit", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Double.class, false, true, NdcPricePerDispenseUnitValidator.class, EntityType.NDC);

    /** CONJUNCTION */
    public static final FieldKey<DataField<String>> CONJUNCTION = new FieldKey<DataField<String>>("conjunction",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.DOSAGE_FORM);

    /** OTHER_LANGUAGE_VERB */
    public static final FieldKey<DataField<String>> OTHER_LANGUAGE_VERB = new FieldKey<DataField<String>>(
        "other.language.verb", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL, String.class,
        false, true, EntityType.DOSAGE_FORM);

    /** OTHER_LANGUAGE_PREPOSITION */
    public static final FieldKey<DataField<String>> OTHER_LANGUAGE_PREPOSITION = new FieldKey<DataField<String>>(
        "other.language.preposition", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL,
        String.class, false, true, EntityType.DOSAGE_FORM);

    /** PREPOSITION */
    public static final FieldKey<DataField<String>> PREPOSITION = new FieldKey<DataField<String>>("preposition",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.DOSAGE_FORM);

    /** VERB */
    public static final FieldKey<DataField<String>> VERB = new FieldKey<DataField<String>>("verb", FieldType.VA_DATA_FIELD,
        FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL, String.class, false, true, EntityType.DOSAGE_FORM);

    /** NDC_PRICE_PER_ORDER_UNIT */
    public static final FieldKey<DataField<Double>> NDC_PRICE_PER_ORDER_UNIT = new FieldKey<DataField<Double>>(
        "ndc.price.per.order.unit", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL,
        Double.class, false, true, NdcPricePerOrderUnitValidator.class, EntityType.NDC);

    /** NEW_VALUE */
    public static final FieldKey<String> NEW_VALUE = new FieldKey<String>("new.value", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** NON_RENEWABLE */
    public static final FieldKey<DataField<Boolean>> NON_RENEWABLE = new FieldKey<DataField<Boolean>>("non.renewable",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Boolean.class, false, true,
        EntityType.PRODUCT);

    /** NON_VA_MED */
    public static final FieldKey<Boolean> NON_VA_MED = new FieldKey<Boolean>("non.va.med", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, false, EntityType.ORDERABLE_ITEM);

    /** NORMAL_AMOUNT_TO_ORDER */
    public static final FieldKey<DataField<Long>> NORMAL_AMOUNT_TO_ORDER = new FieldKey<DataField<Long>>(
        "normal.amount.to.order", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL,
        Long.class, false, true, NormalAmountToOrderValidator.class, EntityType.PRODUCT);

    /** SERVICE_CODE */
    public static final FieldKey<String> SERVICE_CODE = new FieldKey<String>("service.code",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, false,
        EntityType.PRODUCT);

    /** NDCDP_QUANTITY_MULTIPLIER */
    public static final FieldKey<DataField<String>> NDCDP_QUANTITY_MULTIPLIER = new FieldKey<DataField<String>>(
        "ncpdp.quantity.multiplier", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, NcpdpQuantityMultiplierValidator.class, EntityType.PRODUCT);

    /** NOTE */
    public static final FieldKey<String> NOTE = new FieldKey<String>("note", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.NATIONAL, String.class, false, true);

    /** NEW_NOTE */
    public static final FieldKey<String> NEW_NOTE = new FieldKey<String>("new.note", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL, String.class, false, true);

    /** NOTIFICATION_DATE */
    public static final FieldKey<Date> NOTIFICATION_DATE = new FieldKey<Date>("notification.date", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** NOTIFICATION_HIDE */
    public static final FieldKey<Boolean> NOTIFICATION_HIDE = new FieldKey<Boolean>("notification.hidden",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** NOTIFICATION_UNREAD */
    public static final FieldKey<Boolean> NOTIFICATION_UNREAD = new FieldKey<Boolean>("notification.is.unread",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** NOTIFICATION_VIEWED */
    public static final FieldKey<Boolean> NOTIFICATION_VIEWED = new FieldKey<Boolean>("notification.is.viewed",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** NOTIFICATION_HIDDEN */
    public static final FieldKey<Boolean> NOTIFICATION_HIDDEN = new FieldKey<Boolean>("notification.is.hidden",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** NOTIFICATION_DATE_USE */
    public static final FieldKey<Boolean> NOTIFICATION_DATE_USE = new FieldKey<Boolean>("notification.date.use",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** NOTIFICATION_ONLY_LOCAL */
    public static final FieldKey<Boolean> NOTIFICATION_ONLY_LOCAL = new FieldKey<Boolean>("notification.only.local",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** NOTIFICATION_TYPE */
    public static final FieldKey<NotificationType> NOTIFICATION_TYPE = new FieldKey<NotificationType>("notification.type",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, NotificationType.class, false, true);

    /** NUMBER_OF_DOSES */
    public static final FieldKey<DataField<Long>> NUMBER_OF_DOSES = new FieldKey<DataField<Long>>("number.of.doses",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Long.class, false, true,
        NumberOfDosesValidator.class, EntityType.PRODUCT);

    /** NUMBER_VITALS_INSTANCE_DISPLAYED */
    public static final FieldKey<DataField<String>> NUMBER_VITALS_INSTANCE_DISPLAYED = new FieldKey<DataField<String>>(
        "number.vital.instance.displayed", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true);

    /** NUMERIC_DOSE */
    public static final FieldKey<Double> NUMERIC_DOSE = new FieldKey<Double>("numeric.dose", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Double.class, false, true);

    // public static final FieldKey<ListDataField<String>> OI_DRUG_TEXT_ENTRY = new FieldKey<ListDataField<String>>(
    // "oi.drug.text.entry", FieldType.VA_DATA_FIELD, FieldSubType.LIST_DATA_FIELD, FieldEnvironment.BOTH, String.class,
    // false, EntityType.ORDERABLE_ITEM);

    /** VISTA_OI_NAME */
    public static final FieldKey<String> VISTA_OI_NAME = new FieldKey<String>("vista.oi.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, false, EntityType.ORDERABLE_ITEM);

    /** OI_NAME */
    public static final FieldKey<String> OI_NAME = new FieldKey<String>("oi.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, false, EntityType.ORDERABLE_ITEM);

    /** OP_EXTERNAL_DISPENSE */
    public static final FieldKey<DataField<Boolean>> OP_EXTERNAL_DISPENSE = new FieldKey<DataField<Boolean>>(
        "op.external.dispense", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL,
        Boolean.class, false, true, EntityType.PRODUCT);

    /** OI_IV_FLAG */
    public static final FieldKey<DataField<Boolean>> OI_IV_FLAG = new FieldKey<DataField<Boolean>>("oi.iv.flag",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Boolean.class, false, true,
        EntityType.ORDERABLE_ITEM);

    /** ORDERABLE_ITEM */
    public static final FieldKey<OrderableItemVo> ORDERABLE_ITEM = new FieldKey<OrderableItemVo>("orderable.item",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, OrderableItemVo.class, false, true,
        OrderableItemValidator.class, EntityType.PRODUCT);

    /** ORDERABLE_ITEM_PARENT */
    public static final FieldKey<OrderableItemVo> ORDERABLE_ITEM_PARENT = new FieldKey<OrderableItemVo>(
        "orderable.item.parent", FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH,
        OrderableItemVo.class, false, true, EntityType.ORDERABLE_ITEM);

    /** PREVIOUS_ORDERABLE_ITEM_PARENT */
    public static final FieldKey<OrderableItemVo> PREVIOUS_ORDERABLE_ITEM_PARENT = new FieldKey<OrderableItemVo>(
        "previous.orderable.item.parent", FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH,
        OrderableItemVo.class, false, true, EntityType.ORDERABLE_ITEM);

    /** ORDERABLE_ITEM_SYNONYM */
    public static final FieldKey<MultitextDataField<String>> ORDERABLE_ITEM_SYNONYM =
        new FieldKey<MultitextDataField<String>>("orderable.item.synonym", FieldType.VA_DATA_FIELD,
            FieldSubType.MULTITEXT_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true,
            OrderableItemSynonymValidator.class, EntityType.ORDERABLE_ITEM);

    /** ORDERABLE_ITEM_TYPE */
    public static final FieldKey<OrderableItemType> ORDERABLE_ITEM_TYPE = new FieldKey<OrderableItemType>(
        "orderable.item.type", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.NATIONAL,
        OrderableItemType.class, false, false, EntityType.ORDERABLE_ITEM);

    /** ORIGINATING_LOCATION */
    public static final FieldKey<String> ORIGINATING_LOCATION = new FieldKey<String>("originating.location",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** ORIGINATOR */
    public static final FieldKey<String> ORIGINATOR = new FieldKey<String>("originator", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** OTC_RX_INDICATOR */
    public static final FieldKey<OtcRxVo> OTC_RX_INDICATOR = new FieldKey<OtcRxVo>("otc.rx.indicator", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, OtcRxVo.class, false, true, EntityType.NDC);

    /** OTHER_LANGUAGE_EXPANSION */
    public static final FieldKey<String> OTHER_LANGUAGE_EXPANSION = new FieldKey<String>("other.language.expansion",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.ADMINISTRATION_SCHEDULE, EntityType.MEDICATION_INSTRUCTION, EntityType.LOCAL_MEDICATION_ROUTE);

    /** OTHER_LANGUAGE_INSTRUCTIONS */
    public static final FieldKey<DataField<String>> OTHER_LANGUAGE_INSTRUCTIONS = new FieldKey<DataField<String>>(
        "other.language.instructions", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, EntityType.ORDERABLE_ITEM);

    /** OUTPATIENT_EXPANSION */
    public static final FieldKey<String> OUTPATIENT_EXPANSION = new FieldKey<String>("outpatient.expansion",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.LOCAL_MEDICATION_ROUTE);

    /** OVERRIDE_REASON_ENTERER */
    public static final FieldKey<DataField<String>> OVERRIDE_REASON_ENTERER = new FieldKey<DataField<String>>(
        "override.reason.enterer", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** PACKAGE_PREFIX */
    public static final FieldKey<String> PACKAGE_PREFIX = new FieldKey<String>("package.prefix", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.ADMINISTRATION_SCHEDULE);

    /** PACKAGE_SIZE */
    public static final FieldKey<Double> PACKAGE_SIZE = new FieldKey<Double>("package.size", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Double.class, false, true, PackageSizeValidator.class, EntityType.NDC);

    /** PACKAGE_TYPE */
    public static final FieldKey<PackageTypeVo> PACKAGE_TYPE = new FieldKey<PackageTypeVo>("package.type",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, PackageTypeVo.class, false, true,
        PackageTypeValidator.class, EntityType.NDC);

    /** PACKAGE_USAGES */
    public static final FieldKey<Collection<PackageUsageVo>> PACKAGE_USAGES = new FieldKey<Collection<PackageUsageVo>>(
        "package.usages", FieldType.DATA_FIELD, FieldSubType.MULTI_SELECT, FieldEnvironment.BOTH, PackageUsageVo.class,
        false, true, EntityType.LOCAL_MEDICATION_ROUTE);

    /** PARENT_DRUG_CLASS */
    public static final FieldKey<DrugClassVo> PARENT_DRUG_CLASS = new FieldKey<DrugClassVo>("parent.drug.class",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.NATIONAL, DrugClassVo.class, false, true,
        EntityType.DRUG_CLASS);

    /** PARENT_NAME */
    public static final FieldKey<String> PARENT_NAME = new FieldKey<String>("parent.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** PASSWORD */
    public static final FieldKey<String> PASSWORD = new FieldKey<String>("password", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** PATIENT_INSTRUCTIONS */
    public static final FieldKey<DataField<String>> PATIENT_INSTRUCTIONS = new FieldKey<DataField<String>>(
        "patient.instructions", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class,
        false, true, EntityType.ORDERABLE_ITEM);

    /** PATIENT_SPECIFIC_LABEL */
    public static final FieldKey<DataField<Boolean>> PATIENT_SPECIFIC_LABEL = new FieldKey<DataField<Boolean>>(
        "patient.specific.label", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Boolean.class, false, true, EntityType.PRODUCT);

    /** PENDING_ADDITION */
    public static final FieldKey<Boolean> PENDING_ADDITION = new FieldKey<Boolean>("pending.addition", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** PENDING_MODIFICATION */
    public static final FieldKey<Boolean> PENDING_MODIFICATION = new FieldKey<Boolean>("pending.modification",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** PENDING_REQUESTS */
    public static final FieldKey<String> PENDING_REQUESTS = new FieldKey<String>("pending.requests", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** PENDING_SECOND_APPROVAL_ADDITION */
    public static final FieldKey<Boolean> PENDING_SECOND_APPROVAL_ADDITION = new FieldKey<Boolean>(
        "pending.second.approval.addition", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH,
        Boolean.class, false, true);

    /** PENDING_SECOND_APPROVAL_MODIFICATION */
    public static final FieldKey<Boolean> PENDING_SECOND_APPROVAL_MODIFICATION = new FieldKey<Boolean>(
        "pending.second.approval.modification", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH,
        Boolean.class, false, true);

    // Pharmacy System simple domain fields

    /** PHARMACY_SYSTEM_AND */
    public static final FieldKey<String> PHARMACY_SYSTEM_AND = new FieldKey<String>("ps.and", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_DAYS */
    public static final FieldKey<String> PHARMACY_SYSTEM_DAYS = new FieldKey<String>("ps.days", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_EIGHT */
    public static final FieldKey<String> PHARMACY_SYSTEM_EIGHT = new FieldKey<String>("ps.eight", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_EXCEPT */
    public static final FieldKey<String> PHARMACY_SYSTEM_EXCEPT = new FieldKey<String>("ps.except", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_FIVE */
    public static final FieldKey<String> PHARMACY_SYSTEM_FIVE = new FieldKey<String>("ps.five", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_FOR */
    public static final FieldKey<String> PHARMACY_SYSTEM_FOR = new FieldKey<String>("ps.for", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_FOUR */
    public static final FieldKey<String> PHARMACY_SYSTEM_FOUR = new FieldKey<String>("ps.four", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_HOURS */
    public static final FieldKey<String> PHARMACY_SYSTEM_HOURS = new FieldKey<String>("ps.hours", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_MINUTES */
    public static final FieldKey<String> PHARMACY_SYSTEM_MINUTES = new FieldKey<String>("ps.minutes", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_MONTHS */
    public static final FieldKey<String> PHARMACY_SYSTEM_MONTHS = new FieldKey<String>("ps.months", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_NINE */
    public static final FieldKey<String> PHARMACY_SYSTEM_NINE = new FieldKey<String>("ps.nine", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_ONE */
    public static final FieldKey<String> PHARMACY_SYSTEM_ONE = new FieldKey<String>("ps.one", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_ONE_FOURTH */
    public static final FieldKey<String> PHARMACY_SYSTEM_ONE_FOURTH = new FieldKey<String>("ps.one.fourth",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_ONE_HALF */
    public static final FieldKey<String> PHARMACY_SYSTEM_ONE_HALF = new FieldKey<String>("ps.one.half", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_ONE_THIRD */
    public static final FieldKey<String> PHARMACY_SYSTEM_ONE_THIRD = new FieldKey<String>("ps.one.third",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_PMIS_PRINTER */
    public static final FieldKey<String> PHARMACY_SYSTEM_PMIS_PRINTER = new FieldKey<String>("ps.pmis.printer",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_PMIS_SECTION_DELETE */
    public static final FieldKey<String> PHARMACY_SYSTEM_PMIS_SECTION_DELETE = new FieldKey<String>("ps.pmis.section.delete",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_SECONDS */
    public static final FieldKey<String> PHARMACY_SYSTEM_SECONDS = new FieldKey<String>("ps.seconds", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_SEVEN */
    public static final FieldKey<String> PHARMACY_SYSTEM_SEVEN = new FieldKey<String>("ps.seven", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_SIX */
    public static final FieldKey<String> PHARMACY_SYSTEM_SIX = new FieldKey<String>("ps.six", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_TEN */
    public static final FieldKey<String> PHARMACY_SYSTEM_TEN = new FieldKey<String>("ps.ten", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_THEN */
    public static final FieldKey<String> PHARMACY_SYSTEM_THEN = new FieldKey<String>("ps.then", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_THREE */
    public static final FieldKey<String> PHARMACY_SYSTEM_THREE = new FieldKey<String>("ps.three", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_THREE_FOURTHS */
    public static final FieldKey<String> PHARMACY_SYSTEM_THREE_FOURTHS = new FieldKey<String>("ps.three.fourths",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_TWO */
    public static final FieldKey<String> PHARMACY_SYSTEM_TWO = new FieldKey<String>("ps.two", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_TWO_THIRDS */
    public static final FieldKey<String> PHARMACY_SYSTEM_TWO_THIRDS = new FieldKey<String>("ps.two.thirds",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_WEEKS */
    public static final FieldKey<String> PHARMACY_SYSTEM_WEEKS = new FieldKey<String>("ps.weeks", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_SITE_NUMBER */
    public static final FieldKey<Integer> PHARMACY_SYSTEM_SITE_NUMBER = new FieldKey<Integer>("ps.site.number",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, Integer.class, false, true,
        EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_SITE_NAME */
    public static final FieldKey<String> PHARMACY_SYSTEM_SITE_NAME = new FieldKey<String>("ps.site.name",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true,
        EntityType.PHARMACY_SYSTEM);

    // Pharmacy System enum list domain fields

    /** PHARMACY_SYSTEM_CMOP_WARNING_LABEL_SOURCE */
    public static final FieldKey<PharmacySystemWarningLabelSource> PHARMACY_SYSTEM_CMOP_WARNING_LABEL_SOURCE =
        new FieldKey<PharmacySystemWarningLabelSource>("ps.cmop.warning.label.source", FieldType.DATA_FIELD,
            FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, PharmacySystemWarningLabelSource.class, false, true,
            EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_OPAI_WARNING_LABEL_SOURCE */
    public static final FieldKey<PharmacySystemWarningLabelSource> PHARMACY_SYSTEM_OPAI_WARNING_LABEL_SOURCE =
        new FieldKey<PharmacySystemWarningLabelSource>("ps.opai.warning.label.source", FieldType.DATA_FIELD,
            FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, PharmacySystemWarningLabelSource.class, false, true,
            EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_WARNING_LABEL_SOURCE */
    public static final FieldKey<PharmacySystemWarningLabelSource> PHARMACY_SYSTEM_WARNING_LABEL_SOURCE =
        new FieldKey<PharmacySystemWarningLabelSource>("ps.warning.label.source", FieldType.DATA_FIELD,
            FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, PharmacySystemWarningLabelSource.class, false, true,
            EntityType.PHARMACY_SYSTEM);

    /** PHARMACY_SYSTEM_PMIS_LANGUAGE */
    public static final FieldKey<PharmacySystemPmisLanguage> PHARMACY_SYSTEM_PMIS_LANGUAGE =
        new FieldKey<PharmacySystemPmisLanguage>("ps.pmis.language", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
            FieldEnvironment.LOCAL, PharmacySystemPmisLanguage.class, false, true, EntityType.PHARMACY_SYSTEM);

//    public static final FieldKey<String> PHONE = new FieldKey<String>("phone", FieldType.DATA_FIELD,
//                                                                      FieldSubType.PRIMITIVE, FieldEnvironment.BOTH,
//                                                                      String.class, false, true,
//                                                                      EntityType.MANUFACTURER);

    /** PLURAL */
    public static final FieldKey<String> PLURAL = new FieldKey<String>("plural", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.MEDICATION_INSTRUCTION);

    /** PREVIOUS_NDC */
    public static final FieldKey<DataField<String>> PREVIOUS_NDC = new FieldKey<DataField<String>>("previous.ndc",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true,
        PreviousNdcValidator.class, EntityType.NDC);

    /** PREVIOUS_UPC_UPN */
    public static final FieldKey<DataField<String>> PREVIOUS_UPC_UPN = new FieldKey<DataField<String>>("previous.upc.upn",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true,
        CommonStringLength0To40Validator.class, EntityType.NDC);

    /** PREFERENCES */
    public static final FieldKey<Map<String, Object>> PREFERENCES = new FieldKey<Map<String, Object>>("preferences",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Map.class, false, true);

    /** PREFERENCE_TYPE */
    public static final FieldKey<PreferenceType> PREFERENCE_TYPE = new FieldKey<PreferenceType>("preference.type",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, PreferenceType.class, false, true);

    /** APPROVED */
    public static final FieldKey<Boolean> APPROVED = new FieldKey<Boolean>("approved", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** REJECTED */
    public static final FieldKey<Boolean> REJECTED = new FieldKey<Boolean>("rejected", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** MODIFIED */
    public static final FieldKey<Boolean> MODIFIED = new FieldKey<Boolean>("modified", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** INACTIVATED */
    public static final FieldKey<Boolean> INACTIVATED = new FieldKey<Boolean>("inactivated", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** PREFERENCE_GROUP */
    public static final FieldKey<Collection<PreferenceGroupVo>> PREFERENCE_GROUP =
        new FieldKey<Collection<PreferenceGroupVo>>("preference.group", FieldType.DATA_FIELD,
            FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.BOTH, PreferenceGroupVo.class, false, true, new FieldKey[] {
                PREFERENCE_TYPE, APPROVED, REJECTED, MODIFIED, INACTIVATED });

    /** PRIMARY_DRUG_CLASS */
    public static final FieldKey<DrugClassVo> PRIMARY_DRUG_CLASS = new FieldKey<DrugClassVo>("primary.drug.class",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, DrugClassVo.class, true, true,
        EntityType.PRODUCT);

    /** PRIMARY_DRUG_CLASS2 */
    public static final FieldKey<String> PRIMARY_DRUG_CLASS2 = new FieldKey<String>("primary.drug.class2",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, true, true, EntityType.PRODUCT);

    /** SECONDARY_DRUG_CLASS */
    public static final FieldKey<String> SECONDARY_DRUG_CLASS = new FieldKey<String>("secondary.drug.class",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, true, true, EntityType.PRODUCT);

    /** PRODUCT */
    public static final FieldKey<ProductVo> PRODUCT = new FieldKey<ProductVo>("product", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, ProductVo.class, false, true, ProductValidator.class,
        EntityType.NDC);

    /** PRODUCT_COUNT */
    public static final FieldKey<Integer> PRODUCT_COUNT = new FieldKey<Integer>("product.count", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Integer.class, false, true, EntityType.ORDERABLE_ITEM);

    /** PROBLEM_REPORTS */
    public static final FieldKey<Boolean> PROBLEM_REPORTS = new FieldKey<Boolean>("problem.reports", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT */
    public static final FieldKey<DataField<Double>> PRODUCT_DISPENSE_UNIT_PER_ORDER_UNIT = new FieldKey<DataField<Double>>(
        "product.dispense.unit.per.ord", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Double.class, false, true, ProductDispenseUnitPerOrderUnitValidator.class, EntityType.PRODUCT);

    /** PRODUCT_PRICE_PER_ORDER_UNIT */
    public static final FieldKey<DataField<Double>> PRODUCT_PRICE_PER_ORDER_UNIT = new FieldKey<DataField<Double>>(
        "product.price.per.order.unit", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL,
        Double.class, false, true, ProductPricePerOrderUnitValidator.class, EntityType.PRODUCT);

    /** PRODUCT_PRICE_PER_DISPENSE_UNIT */
    public static final FieldKey<DataField<Double>> PRODUCT_PRICE_PER_DISPENSE_UNIT = new FieldKey<DataField<Double>>(
        "product.price.per.dispense.unit", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL,
        Double.class, false, true, ProductPricePerDispenseUnitValidator.class, EntityType.PRODUCT);

    /** PRODUCT_STRENGTH */
    public static final FieldKey<String> PRODUCT_STRENGTH = new FieldKey<String>("product.strength", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, ProductStrengthValidator.class,
        EntityType.PRODUCT);

    /** PRODUCT_UNIT */
    public static final FieldKey<DrugUnitVo> PRODUCT_UNIT = new FieldKey<DrugUnitVo>("product.unit", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, DrugUnitVo.class, false, true, EntityType.PRODUCT);

    /** PRODUCT_LIST */
    public static final FieldKey<Collection<ProductVo>> PRODUCT_LIST = new FieldKey<Collection<ProductVo>>("products",
        FieldType.DATA_FIELD, FieldSubType.MULTI_SELECT, FieldEnvironment.BOTH, ProductVo.class, false, true,
        EntityType.ORDERABLE_ITEM);

    /** PRODUCT_LONG_NAME */
    public static final FieldKey<String> PRODUCT_LONG_NAME = new FieldKey<String>("product.long.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** PRODUCT_NUMBER */
    public static final FieldKey<DataField<String>> PRODUCT_NUMBER = new FieldKey<DataField<String>>("product.number",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true,
        ProductNumberValidator.class, EntityType.NDC);

    /** CATEGORIES **/
    public static final FieldKey<ListDataField<Category>> CATEGORIES = new FieldKey<ListDataField<Category>>("categories",
        FieldType.DATA_FIELD, FieldSubType.LIST_CHECKBOX_DATA_FIELD, FieldEnvironment.BOTH, Category.class, false, true,
        ProductTypeValidator.class, EntityType.PRODUCT, EntityType.NDC, EntityType.ORDERABLE_ITEM);

    /** SUB_CATEGORIES */
    public static final FieldKey<ListDataField<SubCategory>> SUB_CATEGORIES = new FieldKey<ListDataField<SubCategory>>(
        "sub.categories", FieldType.DATA_FIELD, FieldSubType.LIST_CHECKBOX_DATA_FIELD, FieldEnvironment.BOTH,
        SubCategory.class, false, true, EntityType.PRODUCT, EntityType.NDC, EntityType.ORDERABLE_ITEM);

    /** MEDICATION */
    public static final FieldKey<Boolean> MEDICATION = new FieldKey<Boolean>("medication", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true, EntityType.PRODUCT,
        EntityType.NDC, EntityType.ORDERABLE_ITEM);

    /** INVESTIGATIONAL */
    public static final FieldKey<Boolean> INVESTIGATIONAL = new FieldKey<Boolean>("investigational", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true, EntityType.PRODUCT,
        EntityType.NDC, EntityType.ORDERABLE_ITEM);

    /** COMPOUND */
    public static final FieldKey<Boolean> COMPOUND = new FieldKey<Boolean>("compound", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true, EntityType.PRODUCT,
        EntityType.NDC, EntityType.ORDERABLE_ITEM);

    /** SUPPLY */
    public static final FieldKey<Boolean> SUPPLY = new FieldKey<Boolean>("supply", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true, EntityType.PRODUCT,
        EntityType.NDC, EntityType.ORDERABLE_ITEM);

    /** HERBAL */
    public static final FieldKey<Boolean> HERBAL = new FieldKey<Boolean>("herbal", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true, EntityType.PRODUCT,
        EntityType.NDC, EntityType.ORDERABLE_ITEM);

    /** CHEMOTHERAPY */
    public static final FieldKey<Boolean> CHEMOTHERAPY = new FieldKey<Boolean>("chemotherapy", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true, EntityType.PRODUCT,
        EntityType.NDC, EntityType.ORDERABLE_ITEM);

    /** OTC */
    public static final FieldKey<Boolean> OTC = new FieldKey<Boolean>("otc", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true, EntityType.PRODUCT,
        EntityType.NDC, EntityType.ORDERABLE_ITEM);

    /** VETERINARY */
    public static final FieldKey<Boolean> VETERINARY = new FieldKey<Boolean>("veterinary", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true, EntityType.PRODUCT,
        EntityType.NDC, EntityType.ORDERABLE_ITEM);

    /** PROMPT_FOR_INJECTION_SITE */
    public static final FieldKey<Boolean> PROMPT_FOR_INJECTION_SITE = new FieldKey<Boolean>("prompt.for.injection.site",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, Boolean.class, false, true,
        EntityType.LOCAL_MEDICATION_ROUTE);

    /** REQUEST_PRODUCT_TYPE */
    public static final FieldKey<ListDataField<String>> REQUEST_PRODUCT_TYPE = new FieldKey<ListDataField<String>>(
        "request.product.type", FieldType.VA_DATA_FIELD, FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true);

    /** PROPOSED_INACTIVATION_DATE */
    public static final FieldKey<DataField<Date>> PROPOSED_INACTIVATION_DATE = new FieldKey<DataField<Date>>(
        "proposed.inactivation.date", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Date.class, false, true, EntityType.ORDERABLE_ITEM, EntityType.PRODUCT, EntityType.NDC);

    /** PROTECT_FROM_LIGHT */
    public static final FieldKey<DataField<Boolean>> PROTECT_FROM_LIGHT = new FieldKey<DataField<Boolean>>(
        "protect.from.light", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Boolean.class,
        false, true, EntityType.PRODUCT, EntityType.NDC);

    /** MONITOR_ROUTINE */
    public static final FieldKey<DataField<String>> MONITOR_ROUTINE = new FieldKey<DataField<String>>("monitor.routine",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true,
        CommonStringLength3To8Validator.class, EntityType.PRODUCT);

    /** LAB_MONITOR_MARK */
    public static final FieldKey<DataField<Boolean>> LAB_MONITOR_MARK = new FieldKey<DataField<Boolean>>("lab.monitor.mark",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Boolean.class, false, true,
        EntityType.PRODUCT);

    /** QUANTITY_DISPENSE_MESSAGE */
    public static final FieldKey<DataField<String>> QUANTITY_DISPENSE_MESSAGE = new FieldKey<DataField<String>>(
        "quantity.dispense.message", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, QuantityDispenseMessageValidator.class, EntityType.PRODUCT);

    /** RECALL_LEVEL */
    public static final FieldKey<ListDataField<String>> RECALL_LEVEL = new FieldKey<ListDataField<String>>("recall.level",
        FieldType.VA_DATA_FIELD, FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD, FieldEnvironment.BOTH, String.class, false,
        true, EntityType.PRODUCT);

    /** REDUCED_COPAY_START_DATE */
    public static final FieldKey<Date> REDUCED_COPAY_START_DATE = new FieldKey<Date>("reduced.copay.start.date",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** REDUCED_COPAY_STOP_DATE */
    public static final FieldKey<Date> REDUCED_COPAY_STOP_DATE = new FieldKey<Date>("reduced.copay.stop.date",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** REDUCED_COPAY */
    public static final FieldKey<Collection<ReducedCopayVo>> REDUCED_COPAY = new FieldKey<Collection<ReducedCopayVo>>(
        "reduced.copay", FieldType.DATA_FIELD, FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.BOTH,
        ReducedCopayVo.class, false, true, new FieldKey[] { REDUCED_COPAY_START_DATE, REDUCED_COPAY_STOP_DATE },
        EntityType.PRODUCT);

    /** REFRIGERATION */
    public static final FieldKey<ListDataField<String>> REFRIGERATION = new FieldKey<ListDataField<String>>("refrigeration",
        FieldType.VA_DATA_FIELD, FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD, FieldEnvironment.BOTH, String.class, false,
        true, EntityType.PRODUCT, EntityType.NDC);

    /** REORDER_LEVEL */
    public static final FieldKey<DataField<Long>> REORDER_LEVEL = new FieldKey<DataField<Long>>("reorder.level",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL, Long.class, false, true,
        ReorderLevelValidator.class, EntityType.PRODUCT);

    /** REQUEST_DATE_USE */
    public static final FieldKey<Boolean> REQUEST_DATE_USE = new FieldKey<Boolean>("request.date.use", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** REQUEST_ID */
    public static final FieldKey<String> REQUEST_ID = new FieldKey<String>("request.id", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** REQUEST_ITEM_ID */
    public static final FieldKey<String> REQUEST_ITEM_ID = new FieldKey<String>("request.item.id", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** REQUEST_ITEM_STATUS */
    public static final FieldKey<RequestItemStatus> REQUEST_ITEM_STATUS = new FieldKey<RequestItemStatus>(
        "request.item.status", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, RequestItemStatus.class,
        false, true, EntityType.ORDERABLE_ITEM, EntityType.PRODUCT, EntityType.NDC);

    /** REQUEST_REJECTION_REASON */
    public static final FieldKey<RequestRejectionReason> REQUEST_REJECTION_REASON = new FieldKey<RequestRejectionReason>(
        "request.rejection.reason", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH,
        RequestRejectionReason.class, false, false, EntityType.ORDERABLE_ITEM, EntityType.PRODUCT, EntityType.NDC);

    /** REJECTION_REASON_TEXT */
    public static final FieldKey<String> REJECTION_REASON_TEXT = new FieldKey<String>("rejection.reason.text",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true,
        EntityType.ORDERABLE_ITEM, EntityType.PRODUCT, EntityType.NDC);

    /** PSR_NAME */
    public static final FieldKey<String> PSR_NAME = new FieldKey<String>("psr.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** REPLACEMENT_DOSE_UNIT */
    public static final FieldKey<DoseUnitVo> REPLACEMENT_DOSE_UNIT = new FieldKey<DoseUnitVo>("replacement.dose.unit",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.NATIONAL, DoseUnitVo.class, false, true,
        EntityType.DOSE_UNIT);

    /** REPLACEMENT_STANDARD_MED_ROUTE */
    public static final FieldKey<StandardMedRouteVo> REPLACEMENT_STANDARD_MED_ROUTE = new FieldKey<StandardMedRouteVo>(
        "replacement.standard.med.route", FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.NATIONAL,
        StandardMedRouteVo.class, false, true, EntityType.STANDARD_MED_ROUTE);

    /** REQUEST_STATE */
    public static final FieldKey<RequestState> REQUEST_STATE = new FieldKey<RequestState>("request.state",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, RequestState.class, false, true);

    /** REQUEST_TO_MAKE_EDITABLE */
    public static final FieldKey<Boolean> REQUEST_TO_MAKE_EDITABLE = new FieldKey<Boolean>("request.to.make.editable",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** REQUEST_TO_MODIFY_VALUE */
    public static final FieldKey<Boolean> REQUEST_TO_MODIFY_VALUE = new FieldKey<Boolean>("request.to.modify.value",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** REQUEST_TYPE */
    public static final FieldKey<RequestType> REQUEST_TYPE = new FieldKey<RequestType>("request.type", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, RequestType.class, false, true);

    /** REQUESTED_BY */
    public static final FieldKey<String> REQUESTED_BY = new FieldKey<String>("requested.by", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** REVIEWED_BY */
    public static final FieldKey<String> REVIEWED_BY = new FieldKey<String>("reviewed.by", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** ROLE_LIST */
    public static final FieldKey<List<Role>> ROLE_LIST = new FieldKey<List<Role>>("roles", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Role.class, false, true);

    /** RX_CONSULT */
    public static final FieldKey<RxConsultVo> RX_CONSULT = new FieldKey<RxConsultVo>("rx.consult", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, RxConsultVo.class, false, true, RxConsultValidator.class);

    /** RX_CONSULT_TYPE */
    public static final FieldKey<RxConsultType> RX_CONSULT_TYPE = new FieldKey<RxConsultType>("rx.consult.type",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, RxConsultType.class, false, true,
        EntityType.RX_CONSULT);

    /** DEFAULT_ROUTE */
    public static final FieldKey<Boolean> DEFAULT_ROUTE = new FieldKey<Boolean>("default.route", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** REQUIRES_SECOND_APPROVAL */
    public static final FieldKey<Boolean> REQUIRES_SECOND_APPROVAL = new FieldKey<Boolean>("requires.second.approval",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** STANDARD_MED_ROUTE */
    public static final FieldKey<StandardMedRouteVo> STANDARD_MED_ROUTE = new FieldKey<StandardMedRouteVo>(
        "standard.med.route", FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.NATIONAL,
        StandardMedRouteVo.class, false, true, StandardMedicationRouteValidator.class, EntityType.ORDERABLE_ITEM);

    /** OI_ROUTE */
    public static final FieldKey<Collection<OiRouteVo>> OI_ROUTE = new FieldKey<Collection<OiRouteVo>>("oi.route",
        FieldType.DATA_FIELD, FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.LOCAL, OiRouteVo.class, false, true,
        OiRouteValidator.class, new FieldKey[] { LOCAL_MEDICATION_ROUTE, DEFAULT_ROUTE }, EntityType.ORDERABLE_ITEM);

    /** OLD_VALUE */
    public static final FieldKey<String> OLD_VALUE = new FieldKey<String>("old.value", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** PHARMACY_SYSTEM */
    public static final FieldKey<PharmacySystemVo> PHARMACY_SYSTEM = new FieldKey<PharmacySystemVo>("pharmacy.system",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, PharmacySystemVo.class, false, true,
        PharmacySystemValidator.class);

    /** REASON */
    public static final FieldKey<String> REASON = new FieldKey<String>("reason", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SCHEDULE_OUTPATIENT_EXPANSION */
    public static final FieldKey<String> SCHEDULE_OUTPATIENT_EXPANSION = new FieldKey<String>(
        "schedule.outpatient.expansion", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class,
        false, true, EntityType.ADMINISTRATION_SCHEDULE);

    /** OI_SCHEDULE_TYPE */
    public static final FieldKey<OiScheduleTypeVo> OI_SCHEDULE_TYPE = new FieldKey<OiScheduleTypeVo>("oi.schedule.type",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.LOCAL, OiScheduleTypeVo.class, false, true,
        EntityType.ORDERABLE_ITEM);

    /** SCORED */
    public static final FieldKey<ListDataField<String>> SCORED = new FieldKey<ListDataField<String>>("scored",
        FieldType.VA_DATA_FIELD, FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD, FieldEnvironment.BOTH, String.class, false,
        true, EntityType.NDC);

    /** SEARCH_ALL_FIELDS */
    public static final FieldKey<String> SEARCH_ALL_FIELDS = new FieldKey<String>("search.all.fields", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SEARCH_ALL_DESIGNATED_FIELDS */
    public static final FieldKey<String> SEARCH_ALL_DESIGNATED_FIELDS = new FieldKey<String>("search.all.designated.fields",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SEARCH_ALL_REQUEST_FIELDS */
    public static final FieldKey<Boolean> SEARCH_ALL_REQUEST_FIELDS = new FieldKey<Boolean>("search.all.request.fields",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** SEARCH_CRITERIA */
    public static final FieldKey<SearchCriteriaVo> SEARCH_CRITERIA = new FieldKey<SearchCriteriaVo>("search.criteria",
        FieldType.DATA_FIELD, FieldSubType.VALUE_OBJECT, FieldEnvironment.BOTH, SearchCriteriaVo.class, false, true,
        SearchCriteriaValidator.class);

    /** SEARCH_TEMPLATE */
    public static final FieldKey<SearchTemplateVo> SEARCH_TEMPLATE = new FieldKey<SearchTemplateVo>("search.template",
        FieldType.DATA_FIELD, FieldSubType.VALUE_OBJECT, FieldEnvironment.BOTH, SearchTemplateVo.class, false, true,
        SearchTemplateValidator.class);

    /** SEARCH_FIELDS */
    public static final FieldKey<List<FieldKey>> SEARCH_FIELDS = new FieldKey<List<FieldKey>>("search.fields",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, FieldKey.class, false, true);

    /** SEARCH_FOR */
    public static final FieldKey<String> SEARCH_FOR = new FieldKey<String>("search.for", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SEARCH_NO_FIELDS */
    public static final FieldKey<String> SEARCH_NO_FIELDS = new FieldKey<String>("search.no.fields", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SEARCH_NOTIFICATION_END_DATE */
    public static final FieldKey<Date> SEARCH_NOTIFICATION_END_DATE = new FieldKey<Date>("search.notification.end.date",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** SEARCH_NOTIFICATION_START_DATE */
    public static final FieldKey<Date> SEARCH_NOTIFICATION_START_DATE = new FieldKey<Date>("search.notification.start.date",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** SEARCH_IAH_END_DATE */
    public static final FieldKey<Date> SEARCH_IAH_END_DATE = new FieldKey<Date>("search.iah.end.date", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** SEARCH_REQUEST_CRITERIA */
    public static final FieldKey<SearchRequestCriteriaVo> SEARCH_REQUEST_CRITERIA = new FieldKey<SearchRequestCriteriaVo>(
        "search.request.criteria", FieldType.DATA_FIELD, FieldSubType.VALUE_OBJECT, FieldEnvironment.BOTH,
        SearchRequestCriteriaVo.class, false, true, SearchRequestCriteriaValidator.class);

    /** SEARCH_IAH_START_DATE */
    public static final FieldKey<Date> SEARCH_IAH_START_DATE = new FieldKey<Date>("search.iah.start.date",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** SEARCH_REQUEST_END_DATE */
    public static final FieldKey<Date> SEARCH_REQUEST_END_DATE = new FieldKey<Date>("search.request.end.date",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** SEARCH_REQUEST_START_DATE */
    public static final FieldKey<Date> SEARCH_REQUEST_START_DATE = new FieldKey<Date>("search.request.start.date",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** SEARCH_FOR_SETUP_ATCS */
    public static final FieldKey<String> SEARCH_FOR_SETUP_ATCS = new FieldKey<String>("search.for.setup.atcs",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** LOCAL_REQUESTS */
    public static final FieldKey<Boolean> LOCAL_REQUESTS = new FieldKey<Boolean>("local.requests", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** SELECT */
    public static final FieldKey<Boolean> SELECT = new FieldKey<Boolean>("select", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** CREATE_NEW */
    public static final FieldKey<String> CREATE_NEW = new FieldKey<String>("create.new", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SHAPE */
    public static final FieldKey<ShapeVo> SHAPE = new FieldKey<ShapeVo>("shape", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, ShapeVo.class, false, true, EntityType.NDC);

    /** SHIFTS */
    public static final FieldKey<String> SHIFTS = new FieldKey<String>("shifts", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true);

    /** HOSPITAL_LOCATION_MULTIPLE */
    public static final FieldKey<Collection<HospitalLocationVo>> HOSPITAL_LOCATION_MULTIPLE =
        new FieldKey<Collection<HospitalLocationVo>>("hospital.location.multiple", FieldType.DATA_FIELD,
            FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.LOCAL, HospitalLocationVo.class, false, true,
            HospitalLocationValidator.class, new FieldKey[] { HOSPITAL_LOCATION_SELECTION, SHIFTS, ADMINISTRATION_TIMES },
            EntityType.ADMINISTRATION_SCHEDULE);

    /** SINGLE_MULTISOURCE_PRODUCT */
    public static final FieldKey<SingleMultiSourceProductVo> SINGLE_MULTISOURCE_PRODUCT =
        new FieldKey<SingleMultiSourceProductVo>("single.multi.source.product", FieldType.DATA_FIELD,
            FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, SingleMultiSourceProductVo.class, false, true,
            SingleMultiSourceProductValidator.class, EntityType.PRODUCT);

    /** SINGLE_MULTISOURCE_NDC */
    public static final FieldKey<SingleMultiSourceProductVo> SINGLE_MULTISOURCE_NDC =
        new FieldKey<SingleMultiSourceProductVo>("single.multi.source.ndc", FieldType.DATA_FIELD,
            FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, SingleMultiSourceProductVo.class, false, true,
            SingleMultiSourceProductValidator.class, EntityType.NDC);

    /** SITE */
    public static final FieldKey<String> SITE = new FieldKey<String>("site", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.BOTH, String.class, false, true);

    /** SITE_COTS_DB_VERSION */
    public static final FieldKey<String> SITE_COTS_DB_VERSION = new FieldKey<String>("site.cots.db.version",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SITE_NAME */
    public static final FieldKey<String> SITE_NAME = new FieldKey<String>("site.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SITE_NUMBER */
    public static final FieldKey<String> SITE_NUMBER = new FieldKey<String>("site.number", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SITE_PEPS_DB_VERSION */
    public static final FieldKey<String> SITE_PEPS_DB_VERSION = new FieldKey<String>("site.peps.db.version",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SITE_SERVER_NAME */
    public static final FieldKey<String> SITE_SERVER_NAME = new FieldKey<String>("site.server.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SITE_VERSION_INFO_DATE */
    public static final FieldKey<String> SITE_VERSION_INFO_DATE = new FieldKey<String>("site.version.info.date",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SITE_VISTA_VERSION */
    public static final FieldKey<String> SITE_VISTA_VERSION = new FieldKey<String>("site.vista.version",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SITE_VISTA_MESSAGE_SYNCHRONIZATION */
    public static final FieldKey<String> SITE_VISTA_MESSAGE_SYNCHRONIZATION = new FieldKey<String>(
        "site.vista.message.synchronization", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH,
        String.class, false, true);

    /** SPANISH_TRANSLATION */
    public static final FieldKey<String> SPANISH_TRANSLATION =
        new FieldKey<String>("spanish.translation", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH,
            String.class, false, true, EntityType.RX_CONSULT);

    /** SPANISH_TEXT */
    public static final FieldKey<LanguageChoice> SPANISH_TEXT = new FieldKey<LanguageChoice>("spanish.text",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, LanguageChoice.class, false, true);

    /** SPECIAL_INSTRUCTIONS */
    public static final FieldKey<DataField<String>> SPECIAL_INSTRUCTIONS = new FieldKey<DataField<String>>(
        "special.instructions", FieldType.DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class,
        false, true, CommonStringLength1To120Validator.class, EntityType.ORDERABLE_ITEM);

    /** SPECIMEN_TYPE */
    public static final FieldKey<SpecimenTypeVo> SPECIMEN_TYPE = new FieldKey<SpecimenTypeVo>("specimen.type",
        FieldType.VISTA_LINKED_DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.LOCAL, SpecimenTypeVo.class, false,
        true, EntityType.PRODUCT);

    /** SCHEDULED_INTERVAL */
    public static final FieldKey<String> SCHEDULED_INTERVAL = new FieldKey<String>("scheduled.interval",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SCHEDULED_START_DTM */
    public static final FieldKey<Date> SCHEDULED_START_DTM = new FieldKey<Date>("scheduled.start.dtm", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true);

    /** UPLOAD */
    public static final FieldKey<File> UPLOAD = new FieldKey<File>("upload", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.BOTH, File.class, false, true);

    /** STANDARD_ADMINISTRATION_TIMES */
    public static final FieldKey<String> STANDARD_ADMINISTRATION_TIMES = new FieldKey<String>(
        "standard.administration.times", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class,
        false, true, EntityType.ADMINISTRATION_SCHEDULE);

    /** STANDARD_SHIFTS */
    public static final FieldKey<String> STANDARD_SHIFTS = new FieldKey<String>("standard.shifts", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.ADMINISTRATION_SCHEDULE);

    /** STRENGTH */
    public static final FieldKey<String> STRENGTH = new FieldKey<String>("strength", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, StrengthValidator.class);

    /** SUPPLY_ITEM */
    public static final FieldKey<DataField<Boolean>> SUPPLY_ITEM = new FieldKey<DataField<Boolean>>("supply.item",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** SYNONYM */
    public static final FieldKey<String> SYNONYM = new FieldKey<String>("synonym", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** LOCAL_DRUG_TEXTS */
    public static final FieldKey<List<DrugTextVo>> LOCAL_DRUG_TEXTS = new FieldKey<List<DrugTextVo>>("local.drug.texts",
        FieldType.DATA_FIELD, FieldSubType.MULTI_SELECT, FieldEnvironment.LOCAL, DrugTextVo.class, false, true,
        EntityType.ORDERABLE_ITEM, EntityType.PRODUCT);

    /** NATIONAL_DRUG_TEXTS */
    public static final FieldKey<List<DrugTextVo>> NATIONAL_DRUG_TEXTS = new FieldKey<List<DrugTextVo>>(
        "national.drug.texts", FieldType.DATA_FIELD, FieldSubType.MULTI_SELECT, FieldEnvironment.NATIONAL, DrugTextVo.class,
        false, true, EntityType.ORDERABLE_ITEM, EntityType.PRODUCT);

    /** LOCAL_DRUG_TEXT */
    public static final FieldKey<DrugTextVo> LOCAL_DRUG_TEXT = new FieldKey<DrugTextVo>("local.drug.text",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.LOCAL, DrugTextVo.class, false, true,
        DrugTextValidator.class);

    /** DRUG_TEXT_TYPE */
    public static final FieldKey<DrugTextType> DRUG_TEXT_TYPE = new FieldKey<DrugTextType>("drug.text.type",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, DrugTextType.class, false, true,
        EntityType.DRUG_TEXT);

    /** NATIONAL_DRUG_TEXT */
    public static final FieldKey<DrugTextVo> NATIONAL_DRUG_TEXT = new FieldKey<DrugTextVo>("national.drug.text",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.NATIONAL, DrugTextVo.class, false, true,
        DrugTextValidator.class);

    /** SYNONYM_NAME */
    public static final FieldKey<String> SYNONYM_NAME = new FieldKey<String>("synonym.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** TABLE_ROW_COUNT */
    public static final FieldKey<String> TABLE_ROW_COUNT = new FieldKey<String>("table.row.count", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** TALL_MAN_LETTERING */
    public static final FieldKey<String> TALL_MAN_LETTERING = new FieldKey<String>("tall.man.lettering",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true,
        TallManLetteringValidator.class, EntityType.PRODUCT);

    /** TEMPORARY_VUID */
    public static final FieldKey<String> TEMPORARY_VUID = new FieldKey<String>("temporary.vuid", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** TIME_FORMAT */
    public static final FieldKey<TimeFormat> TIME_FORMAT = new FieldKey<TimeFormat>("time.format", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, TimeFormat.class, false, true);

    /** TOTAL_DISPENSE_QUANTITY */
    public static final FieldKey<DataField<String>> TOTAL_DISPENSE_QUANTITY = new FieldKey<DataField<String>>(
        "total.dispense.quantity", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, CommonStringLength1To120Validator.class, EntityType.PRODUCT);

    /** TRADE_NAME */
    public static final FieldKey<String> TRADE_NAME = new FieldKey<String>("trade.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.NDC);

    /** TRANSMIT_TO_CMOP */
    public static final FieldKey<DataField<Boolean>> TRANSMIT_TO_CMOP = new FieldKey<DataField<Boolean>>("transmit.to.cmop",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL, Boolean.class, false, true,
        EntityType.PRODUCT);

    /** UNDER_REVIEW */
    public static final FieldKey<Boolean> UNDER_REVIEW = new FieldKey<Boolean>("under.review", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** MARKED_FOR_PEPS_SECOND_REVIEW */
    public static final FieldKey<Boolean> MARKED_FOR_PEPS_SECOND_REVIEW = new FieldKey<Boolean>(
        "marked.for.peps.second.review", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class,
        false, true);

    /** NOT_LAST_REVIEWER */
    public static final FieldKey<Boolean> NOT_LAST_REVIEWER = new FieldKey<Boolean>("not.last.reviewer",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** DRUG_TEXT */
    public static final FieldKey<DrugTextVo> DRUG_TEXT = new FieldKey<DrugTextVo>("drug.text", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, DrugTextVo.class, false, true, DrugTextValidator.class);

    /** DRUG_UNIT */
    public static final FieldKey<DrugUnitVo> DRUG_UNIT = new FieldKey<DrugUnitVo>("drug.unit", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, DrugUnitVo.class, false, true, DrugUnitValidator.class);

    /** UNIT_DOSE_SCHEDULE */
    public static final FieldKey<DataField<String>> UNIT_DOSE_SCHEDULE = new FieldKey<DataField<String>>(
        "unit.dose.schedule", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class,
        false, true, UnitDoseScheduleValidator.class, EntityType.PRODUCT);

    /** UNIT_DOSE_SCHEDULE_TYPE */
    public static final FieldKey<ListDataField<String>> UNIT_DOSE_SCHEDULE_TYPE = new FieldKey<ListDataField<String>>(
        "unit.dose.schedule.type", FieldType.VA_DATA_FIELD, FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD,
        FieldEnvironment.BOTH, String.class, false, true, EntityType.PRODUCT);

    /** NCPDP_DISPENSE_UNIT */
    public static final FieldKey<ListDataField<String>> NCPDP_DISPENSE_UNIT = new FieldKey<ListDataField<String>>(
        "ncpdp.dispense.unit", FieldType.VA_DATA_FIELD, FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true, NcpdpDispenseUnitValidator.class, EntityType.PRODUCT);

    /** UNIT_PRICE */
    public static final FieldKey<DataField<Double>> UNIT_PRICE = new FieldKey<DataField<Double>>("unit.price",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.LOCAL, Double.class, false, true,
        UnitPriceValidator.class, EntityType.NDC);

    /** UPC_UPN */
    public static final FieldKey<String> UPC_UPN = new FieldKey<String>("upc.upn", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, UpcUpnValidator.class, EntityType.NDC);

    /** USER */
    public static final FieldKey<UserVo> USER = new FieldKey<UserVo>("user", FieldType.DATA_FIELD, FieldSubType.VALUE_OBJECT,
        FieldEnvironment.BOTH, UserVo.class, false, true, UserValidator.class);

    /** USERNAME */
    public static final FieldKey<String> USERNAME = new FieldKey<String>("username", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** FDB_AUTO_UPDATE */
    public static final FieldKey<String> FDB_AUTO_UPDATE = new FieldKey<String>("fdb.auto.update", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** FDB_UPDATE */
    public static final FieldKey<String> FDB_UPDATE = new FieldKey<String>("fdb.update", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** FDB_ADD */
    public static final FieldKey<String> FDB_ADD = new FieldKey<String>("fdb.add", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** FDB_AUTO_ADD */
    public static final FieldKey<String> FDB_AUTO_ADD = new FieldKey<String>("fdb.auto.add", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** VA_DATA_FIELDS */
    public static final FieldKey<DataFields<DataField>> VA_DATA_FIELDS = new FieldKey<DataFields<DataField>>(
        "va.data.fields", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, DataFields.class, false, true,
        EntityType.ORDERABLE_ITEM, EntityType.PRODUCT, EntityType.NDC, EntityType.DOSAGE_FORM);

    /** VA DRUG_CLASS */
    public static final FieldKey<DrugClassVo> VA_DRUG_CLASS = new FieldKey<DrugClassVo>("va.drug.class",
        FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, DrugClassVo.class, false, true, DrugClassificationValidator.class);

    /** VA_PRINT_NAME */
    public static final FieldKey<String> VA_PRINT_NAME = new FieldKey<String>("va.print.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, true, true, VAPrintNameValidator.class,
        EntityType.PRODUCT);

    /** VA_PRODUCT_NAME */
    public static final FieldKey<String> VA_PRODUCT_NAME = new FieldKey<String>("va.product.name", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, true, false, VAProductNameValidator.class,
        EntityType.PRODUCT);

    /** VENDOR */
    public static final FieldKey<String> VENDOR = new FieldKey<String>("vendor", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, VendorValidator.class, EntityType.NDC);

    /** VENDOR_STOCK_NUMBER */
    public static final FieldKey<String> VENDOR_STOCK_NUMBER = new FieldKey<String>("vendor.stock.number",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true,
        VendorStockNumberValidator.class, EntityType.NDC);

    /** TEN_DIGIT_NDC */
    public static final FieldKey<String> TEN_DIGIT_NDC = new FieldKey<String>("ten.digit.ndc", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.NDC);

    /** TEN_DIGIT_FORMAT_INDICATION */
    public static final FieldKey<String> TEN_DIGIT_FORMAT_INDICATION = new FieldKey<String>("ten.digit.format.indication",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, EntityType.NDC);

    /** VIEWED_BY */
    public static final FieldKey<String> VIEWED_BY = new FieldKey<String>("viewed.by", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** VIEWED_BY_COUNT */
    public static final FieldKey<String> VIEWED_BY_COUNT = new FieldKey<String>("viewed.by.count", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** VISTA_ID */
    public static final FieldKey<String> VISTA_ID = new FieldKey<String>("vista.id", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** VITAL_INSTANCE_TIME_RANGE */
    public static final FieldKey<DataField<String>> VITAL_INSTANCE_TIME_RANGE = new FieldKey<DataField<String>>(
        "vital.instance.time.range", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true);

    /** VITAL_TYPE_ASSOCIATED */
    public static final FieldKey<DataField<String>> VITAL_TYPE_ASSOCIATED = new FieldKey<DataField<String>>(
        "vital.type.associated", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        String.class, false, true);

    /** VITAL_VALUE */
    public static final FieldKey<DataField<String>> VITAL_VALUE = new FieldKey<DataField<String>>("vital.value",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true);

    /** VITALS_ADMINISTRATION */
    public static final FieldKey<Collection<VitalsDisplayAdministrationVo>> VITALS_ADMINISTRATION =
        new FieldKey<Collection<VitalsDisplayAdministrationVo>>("vitals.display.administration",
            FieldType.VISTA_LINKED_DATA_FIELD, FieldSubType.MULTI_SELECT, FieldEnvironment.LOCAL,
            VitalsDisplayAdministrationVo.class, false, true, EntityType.ORDERABLE_ITEM, EntityType.PRODUCT);

    /** VITALS_ASSOCIATED */
    public static final FieldKey<DataField<String>> VITALS_ASSOCIATED = new FieldKey<DataField<String>>("vitals.associated",
        FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH, String.class, false, true);

    /** VITALS_ORDER_ENTRY */
    public static final FieldKey<Collection<VitalsDisplayOrderEntryVo>> VITALS_ORDER_ENTRY =
        new FieldKey<Collection<VitalsDisplayOrderEntryVo>>("vitals.display.order.entry", FieldType.VISTA_LINKED_DATA_FIELD,
            FieldSubType.MULTI_SELECT, FieldEnvironment.LOCAL, VitalsDisplayOrderEntryVo.class, false, true,
            EntityType.ORDERABLE_ITEM, EntityType.PRODUCT);

    /** VITALS_ORDER_FINISH */
    public static final FieldKey<Collection<VitalsDisplayFinishAnOrderVo>> VITALS_ORDER_FINISH =
        new FieldKey<Collection<VitalsDisplayFinishAnOrderVo>>("vitals.display.finish.an.order",
            FieldType.VISTA_LINKED_DATA_FIELD, FieldSubType.MULTI_SELECT, FieldEnvironment.LOCAL,
            VitalsDisplayFinishAnOrderVo.class, false, true, EntityType.ORDERABLE_ITEM, EntityType.PRODUCT);

    /** VUID */
    public static final FieldKey<String> VUID = new FieldKey<String>("vuid", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.BOTH, String.class, false, false, VuidValidator.class, EntityType.PRODUCT, EntityType.DRUG_CLASS,
        EntityType.GENERIC_NAME, EntityType.INGREDIENT, EntityType.STANDARD_MED_ROUTE);

    /** WARD_SELECTION */
    public static final FieldKey<WardSelectionVo> WARD_SELECTION = new FieldKey<WardSelectionVo>("ward.selection",
        FieldType.VISTA_LINKED_DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.LOCAL, WardSelectionVo.class, false,
        true);

    /** WARD_ADMIN_TIMES */
    public static final FieldKey<String> WARD_ADMIN_TIMES = new FieldKey<String>("ward.admin.times", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true);

    /** WARD_MULTIPLE */
    public static final FieldKey<Collection<WardMultipleVo>> WARD_MULTIPLE = new FieldKey<Collection<WardMultipleVo>>(
        "ward.multiple", FieldType.DATA_FIELD, FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.BOTH,
        WardMultipleVo.class, false, true, WardValidator.class, new FieldKey[] { WARD_SELECTION, WARD_ADMIN_TIMES },
        EntityType.ADMINISTRATION_SCHEDULE);

    /** MED_INSTRUCTION_WARD_MULTIPLE */
    public static final FieldKey<Collection<WardMultipleVo>> MED_INSTRUCTION_WARD_MULTIPLE =
        new FieldKey<Collection<WardMultipleVo>>("med.instruction.ward.multiple", FieldType.DATA_FIELD,
            FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.BOTH, WardMultipleVo.class, false, true,
            MedInstructionWardValidator.class, new FieldKey[] { WARD_SELECTION, WARD_ADMIN_TIMES },
            EntityType.MEDICATION_INSTRUCTION);

    /** WARNING_MAPPING */
    public static final FieldKey<RxConsultVo> WARNING_MAPPING = new FieldKey<RxConsultVo>("warning.mapping",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, RxConsultVo.class, false, true,
        EntityType.RX_CONSULT);

    /** WITNESS_TO_ADMINISTRATION */
    public static final FieldKey<DataField<Boolean>> WITNESS_TO_ADMINISTRATION = new FieldKey<DataField<Boolean>>(
        "witness.to.administration", FieldType.VA_DATA_FIELD, FieldSubType.SIMPLE_DATA_FIELD, FieldEnvironment.BOTH,
        Boolean.class, false, true, EntityType.PRODUCT);

    /** INGREDIENT */
    public static final FieldKey<IngredientVo> INGREDIENT = new FieldKey<IngredientVo>("ingredient", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, IngredientVo.class, true, true, IngredientValidator.class);

    /** INGREDIENT NAME*/
    public static final FieldKey<String> INGREDIENT_NAME = new FieldKey<String>("ingredient.name",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** PRIMARY_INGREDIENT */
    public static final FieldKey<IngredientVo> PRIMARY_INGREDIENT = new FieldKey<IngredientVo>("primary.ingredient",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.NATIONAL, IngredientVo.class, true, true,
        EntityType.INGREDIENT);

    /** ACTIVE */
    public static final FieldKey<Boolean> ACTIVE = new FieldKey<Boolean>("active", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** ACTIVE_INGREDIENT */
    public static final FieldKey<Collection<ActiveIngredientVo>> ACTIVE_INGREDIENT =
        new FieldKey<Collection<ActiveIngredientVo>>("active.ingredients", FieldType.DATA_FIELD,
            FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.BOTH, ActiveIngredientVo.class, true, true,
            ActiveIngredientValidator.class, new FieldKey[] { INGREDIENT, STRENGTH, DRUG_UNIT }, EntityType.PRODUCT);

    /** PARTIAL_CATEGORY */
    public static final FieldKey<String> PARTIAL_CATEGORY = new FieldKey<String>("partial.category", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    // atc canister

    /** ATC_CANISTER */
    public static final FieldKey<Long> ATC_CANISTER = new FieldKey<Long>("atc.canister", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, Long.class, false, true);

    /** WARD_GROUP_FOR_ATC */
    public static final FieldKey<WardGroupForAtcVo> WARD_GROUP_FOR_ATC = new FieldKey<WardGroupForAtcVo>(
        "ward.group.for.atc", FieldType.VISTA_LINKED_DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.LOCAL,
        WardGroupForAtcVo.class, false, true);

    /** ATC_MNEMONIC */
    public static final FieldKey<String> ATC_MNEMONIC = new FieldKey<String>("atc.mnemonic", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true, EntityType.PRODUCT);

    /** ATC_CHOICE */
    public static final FieldKey<AtcChoice> ATC_CHOICE = new FieldKey<AtcChoice>("atc.choice", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, AtcChoice.class, false, true, EntityType.PRODUCT);

    /** ONE_ATC_CANISTER */
    public static final FieldKey<Long> ONE_ATC_CANISTER = new FieldKey<Long>("one.atc.canister", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, Long.class, false, true, EntityType.PRODUCT);

    /** ATC_CANISTERS */
    public static final FieldKey<Collection<AtcCanisterVo>> ATC_CANISTERS = new FieldKey<Collection<AtcCanisterVo>>(
        "atc.canisters", FieldType.DATA_FIELD, FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.LOCAL,
        AtcCanisterVo.class, false, true, AtcCanistersValidator.class, new FieldKey[] { ATC_CANISTER, WARD_GROUP_FOR_ATC },
        EntityType.PRODUCT);

    // possible dosage package

    /** POSSIBLE_DOSAGE_PACKAGE */
    public static final FieldKey<Collection<PossibleDosagesPackageVo>> POSSIBLE_DOSAGE_PACKAGE =
        new FieldKey<Collection<PossibleDosagesPackageVo>>("possible.dosage.package", FieldType.DATA_FIELD,
            FieldSubType.MULTI_SELECT, FieldEnvironment.LOCAL, PossibleDosagesPackageVo.class, false, true);

    // packages

    /** PACKAGES */
    public static final FieldKey<Collection<PossibleDosagesPackageVo>> PACKAGES =
        new FieldKey<Collection<PossibleDosagesPackageVo>>("packages", FieldType.DATA_FIELD, FieldSubType.MULTI_SELECT,
            FieldEnvironment.BOTH, PossibleDosagesPackageVo.class, false, true);

    /** BCMA_UNITS_PER_DOSE */
    public static final FieldKey<Double> BCMA_UNITS_PER_DOSE = new FieldKey<Double>("bcma.units.per.dose",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, Double.class, false, true);

    /** LOCAL_POSSIBLE_DOSAGE */
    public static final FieldKey<String> LOCAL_POSSIBLE_DOSAGE = new FieldKey<String>("local.possible.dosage",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** OTHER_LANGUAGE_DOSAGE_NAME */
    public static final FieldKey<String> OTHER_LANGUAGE_DOSAGE_NAME = new FieldKey<String>("other.language.dosage.name",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true);

    /** LOCAL_POSSIBLE_DOSAGES */
    public static final FieldKey<Collection<LocalPossibleDosagesVo>> LOCAL_POSSIBLE_DOSAGES =
        new FieldKey<Collection<LocalPossibleDosagesVo>>("local.possible.dosages", FieldType.DATA_FIELD,
            FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.LOCAL, LocalPossibleDosagesVo.class, false, true,
            LocalPossibleDosagesValidator.class, new FieldKey[] {
                BCMA_UNITS_PER_DOSE, LOCAL_POSSIBLE_DOSAGE, DOSE_UNIT, OTHER_LANGUAGE_DOSAGE_NAME, NUMERIC_DOSE,
                POSSIBLE_DOSAGE_PACKAGE }, EntityType.PRODUCT);

    /** DF_NOUNS */
    public static final FieldKey<Collection<DosageFormNounVo>> DF_NOUNS = new FieldKey<Collection<DosageFormNounVo>>(
        "df.nouns", FieldType.DATA_FIELD, FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.LOCAL, DosageFormNounVo.class,
        false, true, new FieldKey[] { NOUN, OTHER_LANGUAGE_NOUN, PACKAGES }, EntityType.DOSAGE_FORM);

    /** DF_DISPENSE_UNITS_PER_DOSE */
    public static final FieldKey<Collection<DispenseUnitPerDoseVo>> DF_DISPENSE_UNITS_PER_DOSE =
        new FieldKey<Collection<DispenseUnitPerDoseVo>>("df.dispense.units.per.dose", FieldType.DATA_FIELD,
            FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.NATIONAL, DispenseUnitPerDoseVo.class, false, true,
            new FieldKey[] { STR_DISPENSE_UNIT_PER_DOSE, PACKAGES }, EntityType.DOSAGE_FORM);

    // national possible dosages

    /** POSSIBLE_DOSAGES_DISPENSE_UNITS_PER_DOSE */
    public static final FieldKey<Double> POSSIBLE_DOSAGES_DISPENSE_UNITS_PER_DOSE = new FieldKey<Double>(
        "possible.dosages.dispense.units.per.dose", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL,
        Double.class, false, true);

    // dose changed to a double

    /** DOSE */
    public static final FieldKey<Double> DOSE = new FieldKey<Double>("dose", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.BOTH, Double.class, false, true);

    /** NATIONAL_POSSIBLE_DOSAGES */
    public static final FieldKey<Collection<NationalPossibleDosagesVo>> NATIONAL_POSSIBLE_DOSAGES =
        new FieldKey<Collection<NationalPossibleDosagesVo>>("national.possible.dosages", FieldType.DATA_FIELD,
            FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.LOCAL, NationalPossibleDosagesVo.class, false, true,
            PossibleDosagesValidator.class, new FieldKey[] {
                POSSIBLE_DOSAGES_DISPENSE_UNITS_PER_DOSE, BCMA_UNITS_PER_DOSE, DOSE, POSSIBLE_DOSAGE_PACKAGE },
            EntityType.PRODUCT);

    /** DF_UNITS */
    public static final FieldKey<Collection<DosageFormUnitVo>> DF_UNITS = new FieldKey<Collection<DosageFormUnitVo>>(
        "df.units", FieldType.DATA_FIELD, FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.NATIONAL,
        DosageFormUnitVo.class, false, true, new FieldKey[] { DRUG_UNIT, PACKAGES }, EntityType.DOSAGE_FORM);

    // NdcByOutpatientSiteNdc

    /** OUTPATIENT_SITE */
    public static final FieldKey<OutpatientSiteVo> OUTPATIENT_SITE = new FieldKey<OutpatientSiteVo>("outpatient.site",
        FieldType.VISTA_LINKED_DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.LOCAL, OutpatientSiteVo.class, false,
        true);

    /** LAST_LOCAL_NDC */
    public static final FieldKey<String> LAST_LOCAL_NDC = new FieldKey<String>("last.local.ndc", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true);

    /** LAST_CMOP_NDC */
    public static final FieldKey<String> LAST_CMOP_NDC = new FieldKey<String>("last.cmop.ndc", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL, String.class, false, true);

    /** NDC_BY_OUTPATIENT_SITE_NDC */
    public static final FieldKey<Collection<NdcByOutpatientSiteNdcVo>> NDC_BY_OUTPATIENT_SITE_NDC =
        new FieldKey<Collection<NdcByOutpatientSiteNdcVo>>("ndc.by.outpatient.site.ndc", FieldType.DATA_FIELD,
            FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.LOCAL, NdcByOutpatientSiteNdcVo.class, false, true,
            NdcByOutpatientSiteNdcValidator.class, new FieldKey[] { OUTPATIENT_SITE, LAST_LOCAL_NDC, LAST_CMOP_NDC },
            EntityType.PRODUCT, EntityType.NDC);

    /** EFFECTIVE_DTM */
    public static final FieldKey<Date> EFFECTIVE_DTM = new FieldKey<Date>("effective.date.time", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, false);

    /** EFFECTIVE_DTM_STATUS */
    public static final FieldKey<ItemStatus> EFFECTIVE_DTM_STATUS = new FieldKey<ItemStatus>("effective.dtm.status",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, ItemStatus.class, false, false);

    /** EFFECTIVE_DATES */
    public static final FieldKey<Collection<VuidStatusHistoryVo>> EFFECTIVE_DATES =
        new FieldKey<Collection<VuidStatusHistoryVo>>("effective.dates", FieldType.DATA_FIELD,
            FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.BOTH, VuidStatusHistoryVo.class, false, false,
            EffectiveDateTimeTypeValidator.class, new FieldKey[] { EFFECTIVE_DTM, EFFECTIVE_DTM_STATUS }, EntityType.PRODUCT,
            EntityType.DRUG_CLASS, EntityType.GENERIC_NAME, EntityType.INGREDIENT, EntityType.STANDARD_MED_ROUTE);

    // synonyms

    /** SYNONYM_DISPENSE_UNIT_PER_ORDER_UNIT */
    public static final FieldKey<Double> SYNONYM_DISPENSE_UNIT_PER_ORDER_UNIT = new FieldKey<Double>(
        "synonym.dispense.unit.per.order.unit", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH,
        Double.class, false, true);

    /** SYNONYM_INTENDED_USE */
    public static final FieldKey<IntendedUseVo> SYNONYM_INTENDED_USE = new FieldKey<IntendedUseVo>("synonym.intended.use",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, IntendedUseVo.class, false, true);

    /** ORDER_UNIT */
    public static final FieldKey<OrderUnitVo> ORDER_UNIT = new FieldKey<OrderUnitVo>("order.unit", FieldType.DATA_FIELD,
        FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, OrderUnitVo.class, false, true, OrderUnitValidator.class,
        EntityType.NDC, EntityType.PRODUCT);

    /** SPECIAL_HANDLING */
    public static final FieldKey<SpecialHandlingVo> SPECIAL_HANDLING = new FieldKey<SpecialHandlingVo>("special.handling",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, SpecialHandlingVo.class, false, true,
        SpecialHandlingValidator.class);

    /** SPECIAL_HANDLINGS */
    public static final FieldKey<ListDataField<SpecialHandlingVo>> SPECIAL_HANDLINGS =
        new FieldKey<ListDataField<SpecialHandlingVo>>("special.handlings", FieldType.VA_DATA_FIELD,
            FieldSubType.MULTI_SELECT_PRIMARY_KEY_DATA_FIELD, FieldEnvironment.BOTH, SpecialHandlingVo.class, false, true,
            SpecialHandlingForProductValidator.class, EntityType.PRODUCT);

    /** SYNONYM_PRICE_PER_ORDER_UNIT */
    public static final FieldKey<Double> SYNONYM_PRICE_PER_ORDER_UNIT = new FieldKey<Double>("synonym.price.per.order.unit",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Double.class, false, true);

    // Note: Needed to create this searchable version of the SYNONYM_PRICE_PER_ORDER_UNIT key because on the product advanced
    // search, 'product.' is prepended in front of 'price.per.order.unit' when searching FieldKey.properties, which
    // returns 'Product Price Per Order Unit' instead of 'Price Per Order Unit' as needed.

    /** PRICE_PER_ORDER_UNIT_SEARCHABLE */
    public static final FieldKey<String> PRICE_PER_ORDER_UNIT_SEARCHABLE = new FieldKey<String>(
        "price.per.order.unit.searchable", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL,
        String.class, false, true);

    /** SYNONYM_PRICE_PER_DISPENSE_UNIT */
    public static final FieldKey<Double> SYNONYM_PRICE_PER_DISPENSE_UNIT = new FieldKey<Double>(
        "synonym.price.per.dispense.unit", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Double.class,
        false, true);

    // Note: Needed to create this searchable version of the SYNONYM_PRICE_PER_DISPENSE_UNIT key because on the product advanced
    // search, 'product.' is prepended in front of 'price.per.dispense.unit' when searching FieldKey.properties, which
    // returns 'Product Price Per Dispense Unit' instead of 'Price Per Dispense Unit' as needed.

    /** PRICE_PER_DISPENSE_UNIT_SEARCHABLE */
    public static final FieldKey<String> PRICE_PER_DISPENSE_UNIT_SEARCHABLE = new FieldKey<String>(
        "price.per.dispense.unit.searchable", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.LOCAL,
        String.class, false, true);

    /** NDC_CODE */
    public static final FieldKey<String> NDC_CODE = new FieldKey<String>("ndc.code", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SYNONYM_VENDOR */
    public static final FieldKey<String> SYNONYM_VENDOR = new FieldKey<String>("synonym.vendor", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, VendorValidator.class);

    /** SYNONYM_VSN */
    public static final FieldKey<String> SYNONYM_VSN = new FieldKey<String>("synonym.vsn", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true, VendorStockNumberValidator.class);

    /** SYNONYM_ORDER_UNIT */
    public static final FieldKey<OrderUnitVo> SYNONYM_ORDER_UNIT = new FieldKey<OrderUnitVo>("synonym.order.unit",
        FieldType.DATA_FIELD, FieldSubType.SINGLE_SELECT, FieldEnvironment.BOTH, OrderUnitVo.class, false, true,
        OrderUnitValidator.class);

    /** SYNONYMS */
    public static final FieldKey<Collection<SynonymVo>> SYNONYMS = new FieldKey<Collection<SynonymVo>>("synonyms",
        FieldType.DATA_FIELD, FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.BOTH, SynonymVo.class, false, true,
        SynonymValidator.class, new FieldKey[] {
            SYNONYM_NAME, SYNONYM_INTENDED_USE, NDC_CODE, SYNONYM_VSN, SYNONYM_ORDER_UNIT, SYNONYM_PRICE_PER_ORDER_UNIT,
            SYNONYM_DISPENSE_UNIT_PER_ORDER_UNIT, SYNONYM_PRICE_PER_DISPENSE_UNIT, SYNONYM_VENDOR }, EntityType.PRODUCT);

    /** DRUG_TEXT_SYNONYM_NAME */
    public static final FieldKey<String> DRUG_TEXT_SYNONYM_NAME = new FieldKey<String>("drug.text.synonym.name",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** DRUG_TEXT_SYNONYMS */
    public static final FieldKey<Collection<DrugTextSynonymVo>> DRUG_TEXT_SYNONYMS =
        new FieldKey<Collection<DrugTextSynonymVo>>("drug.text.synonyms", FieldType.DATA_FIELD,
            FieldSubType.GROUP_LIST_DATA_FIELD, FieldEnvironment.BOTH, DrugTextSynonymVo.class, false, true,
            new FieldKey[] { DRUG_TEXT_SYNONYM_NAME }, EntityType.DRUG_TEXT);

    // search template fields

    /** SEARCH_TEMPLATE_NAME */
    public static final FieldKey<String> SEARCH_TEMPLATE_NAME = new FieldKey<String>("displayable.name",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SEARCH_TEMPLATE_NOTES */
    public static final FieldKey<String> SEARCH_TEMPLATE_NOTES = new FieldKey<String>("notes", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, String.class, false, true);

    /** SEARCH_TEMPLATE_TEMPLATE_TYPE */
    public static final FieldKey<TemplateType> SEARCH_TEMPLATE_TEMPLATE_TYPE = new FieldKey<TemplateType>("template.type",
        FieldType.DATA_FIELD, FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, TemplateType.class, false, true);

    /** SEARCH_TEMPLATE_SELECTED */
    public static final FieldKey<Boolean> SEARCH_TEMPLATE_SELECTED = new FieldKey<Boolean>("selected", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Boolean.class, false, true);

    /** VALUE */
    public static final FieldKey<String> VALUE = new FieldKey<String>("value", FieldType.DATA_FIELD, FieldSubType.PRIMITIVE,
        FieldEnvironment.BOTH, String.class, false, true, EntityType.INGREDIENT, EntityType.DRUG_UNIT, EntityType.DRUG_CLASS,
        EntityType.PACKAGE_TYPE, EntityType.ADMINISTRATION_SCHEDULE, EntityType.GENERIC_NAME, EntityType.STANDARD_MED_ROUTE,
        EntityType.LOCAL_MEDICATION_ROUTE, EntityType.DRUG_TEXT, EntityType.ORDER_UNIT, EntityType.DISPENSE_UNIT,
        EntityType.MEDICATION_INSTRUCTION, EntityType.MANUFACTURER, EntityType.RX_CONSULT, EntityType.SPECIAL_HANDLING);

    /** INACTIVATION_DATE */
    public static final FieldKey<Date> INACTIVATION_DATE = new FieldKey<Date>("inactivation.date", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Date.class, false, true, EntityType.ORDERABLE_ITEM,
        EntityType.PRODUCT, EntityType.NDC, EntityType.INGREDIENT, EntityType.DRUG_UNIT, EntityType.PACKAGE_TYPE,
        EntityType.DOSAGE_FORM, EntityType.GENERIC_NAME, EntityType.LOCAL_MEDICATION_ROUTE, EntityType.DRUG_TEXT,
        EntityType.DISPENSE_UNIT, EntityType.MANUFACTURER, EntityType.DOSE_UNIT);

    /** REVISION_NUMBER */
    public static final FieldKey<Long> REVISION_NUMBER = new FieldKey<Long>("revision.number", FieldType.DATA_FIELD,
        FieldSubType.PRIMITIVE, FieldEnvironment.BOTH, Long.class, false, true);

    private static final long serialVersionUID = 1L;

    private static final String MULTITEXT_HIDDEN_FIELD_KEY_SUFFIX = ".multitext";
    private static final String ABBREVIATION_SUFFIX = ".abbreviation";
    private static final String NAME_SUFFIX = ".name";
    private static final String DESCRIPTION_SUFFIX = ".description";
    private static final String WIDTH_SUFFIX = ".width";
    private static final String SEARCHABLE_SUFFIX = ".searchable";

    private static Map<Enum, Collection<FieldKey>> ENUM_TO_FIELD_KEYS;
    private static Map<List<Enum>, Collection<FieldKey>> ENUMS_TO_FIELD_KEYS;
    private static Map<String, FieldKey> NAME_TO_KEY_MAP;
    private static Map<Class, FieldKey> VALUE_OBJECT_TO_KEY_MAP;
    private static Map<FieldKey, FieldKey> SEARCH_KEY_TO_MAIN_KEY_MAP;

    private Class fieldClass;
    private FieldEnvironment fieldEnvironment;
    private List<EntityType> entityTypes = Collections.emptyList();
    private FieldSubType fieldSubType;
    private FieldType fieldType;
    private List<FieldKey> groupedFields = Collections.emptyList();
    private FieldKey groupKey = null;
    private String key;
    private boolean editableAfterCreation;
    private boolean requiresSecondApproval = false;

    private Class<? extends AbstractValidator<T>> validator;

    // Supports having field keys used for advanced search that map to other field keys that cannot be directly rendered
    // via the peps:datafield tag on the advanced search screen.
    static {
        SEARCH_KEY_TO_MAIN_KEY_MAP = new HashMap<FieldKey, FieldKey>();

        SEARCH_KEY_TO_MAIN_KEY_MAP.put(FieldKey.APPLICATION_PACKAGE_USE_SEARCHABLE, FieldKey.APPLICATION_PACKAGE_USE);
    }

    /**
     * The environment level at which a field can be edited. The field environment is not the same as the editable attribute
     * on the {@link DataField}. This environment works in conjunction with that attribute.
     * 
     * <ul>
     * <li>LOCAL - The field is only editable at local and is not sent to national for updates</li>
     * <li>NATIONAL - The field is only editable at national</li>
     * <li>BOTH - The field is editable at local and national</li>
     * </ul>
     */
    public enum FieldEnvironment {

        /** BOTH */
        BOTH,

        /** LOCAL */
        LOCAL,

        /** NATIONAL */
        NATIONAL;
    }

    /**
     * Sub-types of fields represented by FieldKey
     * 
     * <ul>
     * <li>PRIMITIVE - The field is represented by a "primitive" (e.g., String, int, long, boolean, etc.)</li>
     * <li>VALUE_OBJECT - The field is represented by a {@link ValueObject} that is also not an instance of
     * {@link Selectable}</li>
     * <li>SINGLE_SELECT - The field is an instance of single-select {@link Selectable}</li>
     * <li>MULTI_SELECT - The field is an instance of multi-select {@link Selectable}</li>
     * <li>SIMPLE_DATA_FIELD - The field is an instance of {@link DataField}</li>
     * <li>SINGLE_SELECT_LIST_DATA_FIELD - The field is an instance of single-select {@link ListDataField}</li>
     * <li>MULTI_SELECT_LIST_DATA_FIELD - The field is an instance of multi-select {@link ListDataField}</li>
     * <li>SINGLE_SELECT_PRIMARY_KEY_DATA_FIELD - The field is an instance of single-select primary key
     * {@link ListDataField}. This ListDataField stores instances of {@link ManagedItemVo}.</li>
     * <li>MULTI_SELECT_PRIMARY_KEY_DATA_FIELD - The field is an instance of multi-select primary key {@link ListDataField}.
     * This ListDataField stores instances of {@link ManagedItemVo}.</li>
     * <li>GROUP_DATA_FIELD - The field is an instance of {@link GroupDataField}</li> *
     * <li>GROUP_LIST_DATA_FIELD - The field is an instance of {@link GroupListDataField} </li>
     * <li>GROUPED_DATA_FIELD - The field is an instance of a DataField which is grouped by another {@link GroupDataField}.
     * This enumeration value should not be used by individual FieldKey, as it is automatically used by the constructor.</li>
     * <li>MULTITEXT_DATA_FIELD - The field is an instance of {@link MultitextDataField}</li>
     * </ul>
     */
    public enum FieldSubType {

        /** GROUP_DATA_FIELD */
        GROUP_DATA_FIELD,

        /** GROUP_LIST_DATA_FIELD */
        GROUP_LIST_DATA_FIELD,

        /** GROUPED_DATA_FIELD */
        GROUPED_DATA_FIELD,

        /** SINGLE_SELECT_LIST_DATA_FIELD */
        SINGLE_SELECT_LIST_DATA_FIELD,

        /** MULTI_SELECT_LIST_DATA_FIELD */
        MULTI_SELECT_LIST_DATA_FIELD,

        /** PRIMITIVE */
        PRIMITIVE,

        /** SINGLE_SELECT */
        SINGLE_SELECT,

        /** MULTI_SELECT */
        MULTI_SELECT,

        /** SIMPLE_DATA_FIELD */
        SIMPLE_DATA_FIELD,

        /** SINGLE_SELECT_PRIMARY_KEY_DATA_FIELD */
        SINGLE_SELECT_PRIMARY_KEY_DATA_FIELD,

        /** MULTI_SELECT_PRIMARY_KEY_DATA_FIELD */
        MULTI_SELECT_PRIMARY_KEY_DATA_FIELD,

        /** MULTITEXT_DATA_FIELD */
        MULTITEXT_DATA_FIELD,

        /** VALUE_OBJECT */
        VALUE_OBJECT,

        /** LIST_CHECKBOX_DATA_FIELD */
        LIST_CHECKBOX_DATA_FIELD;
    }

    /**
     * Types of fields represented by FieldKey
     * 
     * <ul>
     * <li>DATA_FIELD - Generic data field that is not any of the other types </li>
     * <li>FDB_DIRECT_LINKED_DATA_FIELD - The possible domain for the field is taken directly from FDB</li>
     * <li>FDB_MAPPED_LINKED_DATA_FIELD - The possible domain for the field is taken mapped to FDB</li>
     * <li>VISTA_LINKED_DATA_FIELD - The possible domain for the field is taken directly from VistA</li>
     * <li>VA_DATA_FIELD - VA specific information stored in PEPS</li>
     * </ul>
     */
    public enum FieldType {

        /** DATA_FIELD */
        DATA_FIELD,

        /** FDB_DIRECT_LINKED_DATA_FIELD */
        FDB_DIRECT_LINKED_DATA_FIELD,

        /** FDB_MAPPED_LINKED_DATA_FIELD */
        FDB_MAPPED_LINKED_DATA_FIELD,

        /** VA_DATA_FIELD */
        VA_DATA_FIELD,

        /** VISTA_LINKED_DATA_FIELD */
        VISTA_LINKED_DATA_FIELD;
    }

    /**
     * Create a new instance of a FieldKey, setting the key, fieldType, and optional validator. This constructor is used for
     * DataFields, not for ValueObjects and general fields.
     * 
     * @param key This is the key value for the field
     * @param fieldType FieldType this FieldKey represents
     * @param fieldSubType FieldSubType this FieldKey represents
     * @param fieldEnvironment FieldEnvironment this FieldKey represents
     * @param fieldClass Class type of the field represented by this fieldKey. If this FieldKey is actually for a Collection
     *            type or DataField type, this class must be the type of Collection or DataField not the Collection or
     *            DataField class. For example, new FieldKey<String>("my.key", ..., String.class, ...).
     * @param requiresSecondApproval true if this field requires second approval when changed
     * @param editableAfterCreation true if this field is editable after the item has been created
     * @param validator AbstractValidator for the field
     * @param groupedFields FieldKeys grouped by this GroupDataField
     * @param entityTypes EntityType(s) at which the field represented by this FieldKey is displayed
     */
    private FieldKey(String key, FieldType fieldType, FieldSubType fieldSubType, FieldEnvironment fieldEnvironment,
        Class fieldClass, boolean requiresSecondApproval, boolean editableAfterCreation,
        Class<? extends AbstractValidator<T>> validator, FieldKey[] groupedFields, EntityType... entityTypes) {

        this(key, fieldType, fieldSubType, fieldEnvironment, fieldClass, requiresSecondApproval, editableAfterCreation,
            
            groupedFields, entityTypes);

        this.validator = validator;
        populateValueObjectToKeyMap(fieldClass);
    }

    /**
     * Create a brand new instance of a FieldKey, setting the key, fieldType, and optional validator. 
     * This constructor is used for DataFields, not for ValueObjects and general fields.
     * 
     * @param key String key for the field
     * @param fieldType FieldType this FieldKey represents
     * @param fieldSubType FieldSubType this FieldKey represents
     * @param fieldEnvironment FieldEnvironment this FieldKey represents
     * @param fieldClass Class type of the field represented by this fieldKey. If this FieldKey is actually for a Collection
     *            type or DataField type, this class must be the type of Collection or DataField not the Collection or
     *            DataField class. For example, new FieldKey<String>("my.key", ..., String.class, ...).
     * @param requiresSecondApproval true if this field requires second approval when changed
     * @param editableAfterCreation true if this field is editable after the item has been created
     * @param validator AbstractValidator for the field
     * @param entityTypes EntityType(s) at which the field represented by this FieldKey is displayed
     */
    private FieldKey(String key, FieldType fieldType, FieldSubType fieldSubType, FieldEnvironment fieldEnvironment,
        Class fieldClass, boolean requiresSecondApproval, boolean editableAfterCreation,
        Class<? extends AbstractValidator<T>> validator, EntityType... entityTypes) {

        this(key, fieldType, fieldSubType, fieldEnvironment, fieldClass, requiresSecondApproval, editableAfterCreation,
            
            entityTypes);

        this.validator = validator;
        populateValueObjectToKeyMap(fieldClass);
    }

    /**
     * Create a new instance of a FieldKey, setting the key, fieldType, and optional validator. This constructor is used for
     * DataFields, not for ValueObjects and general fields.
     * 
     * @param key String key for the field
     * @param fieldType FieldType this FieldKey represents
     * @param fieldSubType FieldSubType this FieldKey represents
     * @param fieldEnvironment FieldEnvironment this FieldKey represents
     * @param fieldClass Class type of the field represented by this fieldKey. If this FieldKey is actually for a Collection
     *            type or DataField type, this class must be the type of Collection or DataField not the Collection or
     *            DataField class. For example, new FieldKey<String>("my.key", ..., String.class, ...).
     * @param requiresSecondApproval true if this field requires second approval when changed
     * @param editableAfterCreation true if this field is editable after the item has been created
     * @param groupedFields FieldKeys grouped by this GroupDataField
     * @param entityTypes EntityType(s) at which the field represented by this FieldKey is displayed
     */
    private FieldKey(String key, FieldType fieldType, FieldSubType fieldSubType, FieldEnvironment fieldEnvironment,
        Class fieldClass, boolean requiresSecondApproval, boolean editableAfterCreation, FieldKey[] groupedFields,
        EntityType... entityTypes) {

        this(key, fieldType, fieldSubType, fieldEnvironment, fieldClass, requiresSecondApproval, editableAfterCreation,
            
            entityTypes);

        this.groupedFields = Arrays.asList(groupedFields);

        for (FieldKey grouped : this.groupedFields) {
            grouped.groupKey = this;
        }

        Collection<FieldKey> fields = ENUM_TO_FIELD_KEYS.get(FieldSubType.GROUPED_DATA_FIELD);

        if (fields == null) {
            fields = new ArrayList<FieldKey>();
            ENUM_TO_FIELD_KEYS.put(FieldSubType.GROUPED_DATA_FIELD, fields);
        }

        fields.addAll(this.groupedFields);
    }

    /**
     * Create a new instance of a FieldKey, using the given key and fieldType. This constructor is used for DataFields, not
     * for ValueObjects and general fields.
     * 
     * @param key String key for the field
     * @param fieldType FieldType this FieldKey represents
     * @param fieldSubType FieldSubType this FieldKey represents
     * @param fieldEnvironment FieldEnvironment this FieldKey represents
     * @param fieldClass Class type of the field represented by this fieldKey. If this FieldKey is actually for a Collection
     *            type or DataField type, this class must be the type of Collection or DataField not the Collection or
     *            DataField class. For example, new FieldKey<String>("my.key", ..., String.class, ...).
     * @param requiresSecondApproval true if this field requires second approval when changed
     * @param editableAfterCreation true if this field is editable after the item has been created
     * @param entityTypes EntityType(s) at which the field represented by this FieldKey is displayed
     */
    private FieldKey(String key, FieldType fieldType, FieldSubType fieldSubType, FieldEnvironment fieldEnvironment,
        Class fieldClass, boolean requiresSecondApproval, boolean editableAfterCreation, EntityType... entityTypes) {

        this.key = key;
        this.fieldType = fieldType;
        this.fieldSubType = fieldSubType;
        this.fieldEnvironment = fieldEnvironment;
        this.fieldClass = fieldClass;
        this.requiresSecondApproval = requiresSecondApproval;
        this.editableAfterCreation = editableAfterCreation;

        if (entityTypes != null) {
            this.entityTypes = Arrays.asList(entityTypes);
        }

        if (!isMultitextDataFieldValue()) {
            populateNameToKeyMap(key);
            populateEnumToKeyMap(fieldType);
            populateEnumToKeyMap(fieldSubType);
            populateEnumToKeyMap(fieldEnvironment);

            for (EntityType entityType : entityTypes) {
                populateEnumToKeyMap(entityType);
            }

            if (isMultitextDataField()) {
                addMultitextValueFieldKey();
            }
        }
    }

    /**
     * Create a {@link Collection} of {@link FieldKey} whose implementation {@link Class}, {@link #fieldClass}, implement
     * the given {@link Class}.
     * 
     * @param fieldClass {@link Class} that must be equal to the {@link FieldKey#fieldClass}
     * @return Collection<FieldKey>
     */
    public static Collection<FieldKey> getDataFields(Class fieldClass) {

        Collection<FieldKey> fields = new ArrayList<FieldKey>();

        for (FieldKey fieldKey : NAME_TO_KEY_MAP.values()) {
            if (fieldClass.isAssignableFrom(fieldKey.getFieldClass())) {
                fields.add(fieldKey);
            }
        }

        return fields;
    }

    /**
     * Get all FieldKeys which represent the array of given FieldKey enumerations - {@link FieldEnvironment},
     * {@link FieldType}, {@link FieldSubType}, {@link EntityType}.
     * 
     * The resulting list is an intersection of all of the types. In other words, the result is an 'AND' type of retrieval.
     * 
     * @param fieldKeyEnumerations Array of FieldKey enumerations
     * @return List of FieldKeys which represent the array of given FieldKey enumerations.
     */
    public static Collection<FieldKey> getDataFields(Enum... fieldKeyEnumerations) {

        List<Enum> enums = new ArrayList<Enum>();

        for (Enum fieldKeyEnumeration : fieldKeyEnumerations) {
            enums.add(fieldKeyEnumeration);
        }

        Collection<FieldKey> fields = ENUMS_TO_FIELD_KEYS.get(enums);

        if (fields == null) {
            fields = Collections.emptyList();

            for (Enum fieldKeyEnumeration : fieldKeyEnumerations) {
                Collection<FieldKey> enumFields = ENUM_TO_FIELD_KEYS.get(fieldKeyEnumeration);

                if (enumFields == null) {
                    enumFields = Collections.emptyList();
                }

                if (fields.isEmpty()) {
                    fields = enumFields;
                } else {
                    fields = CollectionUtils.intersection(fields, enumFields);
                }

            }

            fields = Collections.unmodifiableCollection(fields);

            ENUMS_TO_FIELD_KEYS.put(enums, fields);
        }

        return fields;
    }

    /**
     * Return an unmodifiable List of FieldKeys representing all VA group data fields.
     * 
     * @return Collection<FieldKey> of all VA group data fields
     */
    public static Collection<FieldKey> getGroupDataFields() {

        return getDataFields(FieldSubType.GROUP_DATA_FIELD);

    }

    /**
     * Create a new instance of the AbstractValidator for the given {@link ValueObject}.
     * <p>
     * Note that there is NOT a {@link ValueObject} could be used for more than one {@link FieldKey}, in which case there
     * could conceivably be more than one {@link AbstractValidator} class per {@link ValueObject} class.
     * <p>
     * If there is more than one {@link AbstractValidator} class registered for a {@link ValueObject}, use the appropriate
     * {@link FieldKey} directly to retrieve the {@link AbstractValidator} via {@link FieldKey#newValidatorInstance()}.
     * <p>
     * If the {@link AbstractValidator} could not be instantiated, a CommonException (PharmacyRuntimeException) is thrown.
     * 
     * @param <VO> Type of {@link ValueObject}
     * @param valueObject {@link ValueObject} class for which to instantiate a new {@link AbstractValidator}
     * @return AbstractValidator instance for the {@link FieldKey} for the given {@link ValueObject} class
     */
    public static <VO extends ValueObject> AbstractValidator<VO> newValidatorInstance(Class<VO> valueObject) {

        return VALUE_OBJECT_TO_KEY_MAP.get(valueObject).newValidatorInstance();
    }

    /**
     * Find the FieldKey for the given field name.
     * @param <K> Type of FieldKey to return
     * @param key String key of the data field for which to find the FieldKey
     * @return FieldKey for the given field key, null if a FieldKey for the given String key cannot be found
     */
    public static <K> FieldKey<K> getKey(String key) {

        return NAME_TO_KEY_MAP.get(key);
    }

    /**
     * Concatenate the given {@link EntityType#getPrefix()} with {@link FieldKey#getKey()}.
     * 
     * @param fieldKey {@link FieldKey}
     * @param entityType {@link EntityType}
     * @return String {@link EntityType#getPrefix()} plus {@link FieldKey#getKey()}
     */
    public static String getEntityFieldKey(FieldKey fieldKey, EntityType entityType) {

        return getEntityFieldKey(fieldKey.getKey(), entityType);
    }

    /**
     * Concatenate the given {@link EntityType#getPrefix()} with {@link FieldKey#getKey()}.
     * 
     * @param key String {@link FieldKey#getKey()}
     * @param entityType {@link EntityType}
     * @return String {@link EntityType#getPrefix()} plus {@link FieldKey#getKey()}
     */
    public static String getEntityFieldKey(String key, EntityType entityType) {

        return entityType.getPrefix() + key;
    }

    /**
     * Find the localized abbreviation for the given key in the given {@link Locale}.
     * 
     * @param fieldKey String to use with {@link #ABBREVIATION_SUFFIX} to find localized name
     * @param locale Locale of localization
     * @return String localized name
     */
    public static String getLocalizedAbbreviation(FieldKey fieldKey, Locale locale) {

        return getLocalizedAbbreviation(fieldKey.getKey(), locale);
    }

    /**
     * Find the localized abbreviation for the given {@link FieldKey}, {@link Locale}, and {@link EntityType}. If a
     * localized value with the given {@link EntityType} cannot be found, the localized value without it is returned.
     * 
     * @param fieldKey String to use with {@link #ABBREVIATION_SUFFIX} to find localized name
     * @param locale Locale of localization
     * @param entityType {@link EntityType} for which to localize
     * @return String localized name
     */
    public static String getLocalizedAbbreviation(FieldKey fieldKey, Locale locale, EntityType entityType) {

        return getLocalizedAbbreviation(fieldKey.getKey(), locale, entityType);
    }

    /**
     * Find the localized abbreviation for the given {@link FieldKey}, {@link Locale}, and {@link EntityType}. If a
     * localized value with the given {@link EntityType} cannot be found, the localized value without it is returned.
     * 
     * @param key String to use with {@link #ABBREVIATION_SUFFIX} to find localized name
     * @param locale Locale of localization
     * @param entityType {@link EntityType} for which to localize
     * @return String localized name
     */
    public static String getLocalizedAbbreviation(String key, Locale locale, EntityType entityType) {

        try {
            return ResourceBundleUtility.getResourceBundle(FieldKey.class, locale).getString(
                getEntityFieldKey(key, entityType) + ABBREVIATION_SUFFIX);
        } catch (Exception e) {
            LOG.trace("Unable to localize abbreviation for FieldKey '" + key + "' with EntityType  '" + entityType
                + "' in Locale  '" + locale.getDisplayName() + "'.  Returning localization without the EntityType instead.",
                e);

            return getLocalizedAbbreviation(key, locale);
        }
    }

    /**
     * Find the localized abbreviation for the given key in the given {@link Locale}.
     * 
     * @param key String to use with {@link #ABBREVIATION_SUFFIX} to find localized name
     * @param locale Locale of localization 
     * 
     * @return String localized name 
     */
    public static String getLocalizedAbbreviation(String key, Locale locale) {

        return ResourceBundleUtility.getResourceBundleValue(FieldKey.class, key + ABBREVIATION_SUFFIX, locale);
    }

    /**
     * Find the localized description for the given {@link FieldKey} and {@link Locale}.
     * 
     * @param fieldKey String to use with {@link #DESCRIPTION_SUFFIX} to find localized description
     * @param locale Locale for localization
     * @return String of the localized description for this value object
     */
    public static String getLocalizedDescription(FieldKey fieldKey, Locale locale) {

        return getLocalizedDescription(fieldKey.getKey(), locale);
    }

    /**
     * Find the localized description for the given {@link FieldKey}, {@link Locale}, and {@link EntityType}. If a
     * localized value with the given {@link EntityType} cannot be found, the localized value without it is returned.
     * 
     * @param fieldKey String to use with {@link #DESCRIPTION_SUFFIX} to find localized description
     * @param locale Locale for localization
     * @param entityType {@link EntityType} for which to localize
     * 
     * @return String of the localized description for this value object 
     */
    public static String getLocalizedDescription(FieldKey fieldKey, Locale locale, EntityType entityType) {

        return getLocalizedDescription(fieldKey.getKey(), locale, entityType);
    }

    /**
     * Find the localized description for the given {@link FieldKey}, {@link Locale}, and {@link EntityType}. If a
     * localized value with the given {@link EntityType} cannot be found, the localized value without it is returned.
     * 
     * @param key String to use with {@link #DESCRIPTION_SUFFIX} to find localized description
     * @param locale Locale for localization
     * @param entityType {@link EntityType} for which to localize
     * @return String of the localized description for this value object
     */
    public static String getLocalizedDescription(String key, Locale locale, EntityType entityType) {

        try {
            return ResourceBundleUtility.getResourceBundle(FieldKey.class, locale).getString(
                getEntityFieldKey(key, entityType) + DESCRIPTION_SUFFIX);
        } catch (Exception e) {
            LOG.trace("Unable to localize description for FieldKey '" + key + "' with the EntityType '" + entityType
                + "' in the Locale '" + locale.getDisplayName()
                + "'. We are Returning localization without the EntityType instead.", e);

            return getLocalizedDescription(key, locale);
        }
    }

    /**
     * Find the localized description for the given key in the given {@link Locale}.
     * 
     * @param key String to use with {@link #DESCRIPTION_SUFFIX} to find localized description
     * @param locale Locale of localization
     * @return String of the localized description for this value object
     */
    public static String getLocalizedDescription(String key, Locale locale) {

        return ResourceBundleUtility.getResourceBundleValue(FieldKey.class, key, DESCRIPTION_SUFFIX, locale);
    }

    /**
     * Find the localized name for the given {@link FieldKey} and {@link Locale}.
     * 
     * @param fieldKey String to use with {@link #NAME_SUFFIX} to find localized name
     * @param locale Locale for localization
     * @return String localized name
     */
    public static String getLocalizedName(FieldKey fieldKey, Locale locale) {

        return getLocalizedName(fieldKey.getKey(), locale);
    }

    /**
     * Find the localized name for the given {@link FieldKey}, {@link Locale}, and {@link EntityType}. If a localized
     * value with the given {@link EntityType} cannot be found, the localized value without it is returned.
     * 
     * @param fieldKey String to use with {@link #NAME_SUFFIX} to find localized name
     * @param locale Locale for localization
     * @param entityType {@link EntityType} for which to localize
     * @return String localized name
     */
    public static String getLocalizedName(FieldKey fieldKey, Locale locale, EntityType entityType) {

        return getLocalizedName(fieldKey.getKey(), locale, entityType);
    }

    /**
     * Find the localized name for the given {@link FieldKey}, {@link Locale}, and {@link EntityType}. If a localized
     * value with the given {@link EntityType} cannot be found, the localized value without it is returned.
     * 
     * @param key String to use with {@link #NAME_SUFFIX} to find localized name
     * @param locale Locale for localization
     * @param entityType {@link EntityType} for which to localize
     * @return String localized name
     */
    public static String getLocalizedName(String key, Locale locale, EntityType entityType) {

        if (entityType == null) {
            return getLocalizedName(key, locale);
        }

        try {
            return ResourceBundleUtility.getResourceBundle(FieldKey.class, locale).getString(
                getEntityFieldKey(key, entityType) + NAME_SUFFIX);
        } catch (Exception e) {
            LOG.trace("Unable to localize name for FieldKey '" + key + "' with EntityType '" + entityType + "' in Locale '"
                + locale.getDisplayName() + "'. Returning localization without the EntityType instead.", e);

            return getLocalizedName(key, locale);
        }
    }

    /**
     * Find the localized name for the given key in the given {@link Locale}.
     * 
     * @param key String to use with {@link #NAME_SUFFIX} to find localized name
     * @param locale Locale of localization
     * @return String localized name
     */
    public static String getLocalizedName(String key, Locale locale) {

        return ResourceBundleUtility.getResourceBundleValue(FieldKey.class, key, NAME_SUFFIX, locale);
    }

    /**
     * Find the localized name for the given key in the given {@link Locale}.
     * 
     * @param key String to use with {@link #NAME_SUFFIX} to find localized name
     * @param locale Locale of localization
     * @return String localized name
     */
    public static String getLocalizedField(String key, Locale locale) {

        return ResourceBundleUtility.getResourceBundleValue(FieldKey.class, key, locale);
    }

    /**
     * Find the localized width for the given {@link FieldKey} and {@link Locale}.
     * 
     * @param fieldKey String to use with {@link #WIDTH_SUFFIX} to find localized name
     * @param locale Locale for localization
     * @return String localized width
     */
    public static String getLocalizedWidth(FieldKey<?> fieldKey, Locale locale) {

        return getLocalizedWidth(fieldKey.getKey(), locale);
    }

    /**
     * Find the localized width for the given key in the given {@link Locale}.
     * 
     * @param key String to use with {@link #WIDTH_SUFFIX} to find localized name
     * @param locale Locale of localization
     * @return String localized width
     */
    public static String getLocalizedWidth(String key, Locale locale) {

        return ResourceBundleUtility.getResourceBundleValue(FieldKey.class, key, WIDTH_SUFFIX, locale);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing fields requiring second approval for the given EntityType.
     * 
     * @param entityType {@link EntityType} for which to return second approval fields
     * @return Collection<FieldKey> of product level VA group data fields
     */
    public static Collection<FieldKey> getSecondApprovalDataFields(EntityType entityType) {

        Collection<FieldKey> productFields = getDataFields(entityType);
        Collection<FieldKey> fields = new ArrayList<FieldKey>();

        for (FieldKey fieldKey : productFields) {
            if (fieldKey.isRequiresSecondApproval()) {
                fields.add(fieldKey);
            }
        }

        return Collections.unmodifiableCollection(fields);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing fields which cannot be edited after creation for the given 
     * EntityType.
     * 
     * @param entityType {@link EntityType} for which to return non editable after creation fields
     * @return Collection<FieldKey> of product level VA group data fields
     */
    public static Collection<FieldKey> getDisabledAfterCreationFields(EntityType entityType) {

        Collection<FieldKey> allFields = getDataFields(entityType);
        Collection<FieldKey> fields = new ArrayList<FieldKey>();

        for (FieldKey fieldKey : allFields) {
            if (!fieldKey.isEditableAfterCreation()) {
                fields.add(fieldKey);
            }
        }

        return Collections.unmodifiableCollection(fields);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing NDC level data fields.
     * 
     * @return List<FieldKey> of NDC level data fields
     */
    public static Collection<FieldKey> getNdcDataFields() {

        return getDataFields(EntityType.NDC);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing NDC level VA group data fields.
     * 
     * @return Collection<FieldKey> of NDC level VA group data fields
     */
    public static Collection<FieldKey> getNdcGroupDataFields() {

        return getDataFields(EntityType.NDC, FieldSubType.GROUP_DATA_FIELD);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing NDC level linked data fields.
     * 
     * @return Collection<FieldKey> of NDC level external data fields
     */
    public static Collection<FieldKey> getNdcLinkedDataFields() {

        Collection<FieldKey> fields = new ArrayList<FieldKey>();
        fields.addAll(getDataFields(EntityType.NDC, FieldType.FDB_DIRECT_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.NDC, FieldType.FDB_MAPPED_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.NDC, FieldType.VISTA_LINKED_DATA_FIELD));

        return Collections.unmodifiableCollection(fields);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing NDC level external data fields.
     * 
     * @return Collection<FieldKey> of NDC level external data fields
     */
    public static Collection<FieldKey> getNdcNonVaDataFields() {

        Collection<FieldKey> fields = new ArrayList<FieldKey>();
        fields.addAll(getDataFields(EntityType.NDC, FieldType.DATA_FIELD));
        fields.addAll(getDataFields(EntityType.NDC, FieldType.FDB_DIRECT_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.NDC, FieldType.FDB_MAPPED_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.NDC, FieldType.VISTA_LINKED_DATA_FIELD));

        return Collections.unmodifiableCollection(fields);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing NDC level external data fields for national
     * 
     * Essentially the same as the non national call, but allows for Item Status
     * 
     * @return Collection<FieldKey> of NDC level external data fields
     */
    public static Collection<FieldKey> getNdcNonVaDataFieldsNational() {

        Collection<FieldKey> fields = new ArrayList<FieldKey>();
        fields.addAll(getDataFields(EntityType.NDC, FieldType.DATA_FIELD));
        fields.addAll(getDataFields(EntityType.NDC, FieldType.FDB_DIRECT_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.NDC, FieldType.FDB_MAPPED_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.NDC, FieldType.VISTA_LINKED_DATA_FIELD));

        fields.remove(FieldKey.ITEM_STATUS);

        return Collections.unmodifiableCollection(fields);

    }

    /**
     * return an unmodifiable list of field keys representing the product level local only fields
     * 
     * @return Collection<FieldKey> of product level local only fields
     */
    public static Collection<FieldKey> getNdcLocalOnlyDataFields() {

        return getDataFields(EntityType.NDC, FieldEnvironment.LOCAL);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing NDC level VA data fields.
     * 
     * @return Collection<FieldKey> of NDC level VA data fields
     */
    public static Collection<FieldKey> getNdcVaDataFields() {

        return getDataFields(EntityType.NDC, FieldType.VA_DATA_FIELD);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing external data fields.
     * 
     * @return List<FieldKey> of external data fields
     */
    public static Collection<FieldKey> getNonVaDataFields() {

        Collection<FieldKey> fields = new ArrayList<FieldKey>();
        fields.addAll(getDataFields(FieldType.DATA_FIELD));
        fields.addAll(getDataFields(FieldType.FDB_DIRECT_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(FieldType.FDB_MAPPED_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(FieldType.VISTA_LINKED_DATA_FIELD));

        return Collections.unmodifiableCollection(fields);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing orderable item level data fields.
     * 
     * @return Collection<FieldKey> of orderable item level data fields
     */
    public static Collection<FieldKey> getDosageFormVaDataFields() {

        return getDataFields(EntityType.DOSAGE_FORM, FieldType.VA_DATA_FIELD);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing orderable item level data fields.
     * 
     * @return Collection<FieldKey> of orderable item level data fields
     */
    public static Collection<FieldKey> getOrderableItemDataFields() {

        return getDataFields(EntityType.ORDERABLE_ITEM);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing orderable item level VA group data fields.
     * 
     * @return Collection<FieldKey> of orderable item level VA group data fields
     */
    public static Collection<FieldKey> getOrderableItemGroupDataFields() {

        return getDataFields(EntityType.ORDERABLE_ITEM, FieldSubType.GROUP_DATA_FIELD);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing orderable item level linked data fields.
     * 
     * @return Collection<FieldKey> of orderable item level external data fields
     */
    public static Collection<FieldKey> getOrderableItemLinkedDataFields() {

        Collection<FieldKey> fields = new ArrayList<FieldKey>();
        fields.addAll(getDataFields(EntityType.ORDERABLE_ITEM, FieldType.FDB_DIRECT_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.ORDERABLE_ITEM, FieldType.FDB_MAPPED_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.ORDERABLE_ITEM, FieldType.VISTA_LINKED_DATA_FIELD));

        return Collections.unmodifiableCollection(fields);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing orderable item level external data fields.
     * 
     * @return Collection<FieldKey> of orderable item level external data fields
     */
    public static Collection<FieldKey> getOrderableItemNonVaDataFields() {

        Collection<FieldKey> fields = new ArrayList<FieldKey>();
        fields.addAll(getDataFields(EntityType.ORDERABLE_ITEM, FieldType.DATA_FIELD));
        fields.addAll(getDataFields(EntityType.ORDERABLE_ITEM, FieldType.FDB_DIRECT_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.ORDERABLE_ITEM, FieldType.FDB_MAPPED_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.ORDERABLE_ITEM, FieldType.VISTA_LINKED_DATA_FIELD));

        return Collections.unmodifiableCollection(fields);
    }

    /**
     * return an unmodifiable list of field keys representing the product level local only fields
     * 
     * @return Collection<FieldKey> of product level local only fields
     */
    public static Collection<FieldKey> getOrderableItemLocalOnlyDataFields() {

        return getDataFields(EntityType.ORDERABLE_ITEM, FieldEnvironment.LOCAL);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing orderable item level VA data fields.
     * 
     * @return Collection<FieldKey> of orderable item level VA data fields
     */
    public static Collection<FieldKey> getOrderableItemVaDataFields() {

        return getDataFields(EntityType.ORDERABLE_ITEM, FieldType.VA_DATA_FIELD);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing product level data fields.
     * 
     * @return Collection<FieldKey> of product level data fields
     */
    public static Collection<FieldKey> getProductDataFields() {

        return getDataFields(EntityType.PRODUCT);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing product level VA group data fields.
     * 
     * @return Collection<FieldKey> of product level VA group data fields
     */
    public static Collection<FieldKey> getProductGroupDataFields() {

        return getDataFields(EntityType.PRODUCT, FieldSubType.GROUP_DATA_FIELD);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing product level linked data fields.
     * 
     * @return Collection<FieldKey> of product level external data fields
     */
    public static Collection<FieldKey> getProductLinkedDataFields() {

        Collection<FieldKey> fields = new ArrayList<FieldKey>();
        fields.addAll(getDataFields(EntityType.PRODUCT, FieldType.FDB_DIRECT_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.PRODUCT, FieldType.FDB_MAPPED_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.PRODUCT, FieldType.VISTA_LINKED_DATA_FIELD));

        return Collections.unmodifiableCollection(fields);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing product level external data fields.
     * 
     * @return Collection<FieldKey> of product level external data fields
     */
    public static Collection<FieldKey> getProductNonVaDataFields() {

        Collection<FieldKey> fields = new ArrayList<FieldKey>();
        fields.addAll(getDataFields(EntityType.PRODUCT, FieldType.DATA_FIELD));
        fields.addAll(getDataFields(EntityType.PRODUCT, FieldType.FDB_DIRECT_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.PRODUCT, FieldType.FDB_MAPPED_LINKED_DATA_FIELD));
        fields.addAll(getDataFields(EntityType.PRODUCT, FieldType.VISTA_LINKED_DATA_FIELD));

        return Collections.unmodifiableCollection(fields);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing product level VA data fields.
     * 
     * @return Collection<FieldKey> of product level VA data fields
     */
    public static Collection<FieldKey> getProductVaDataFields() {

        return getDataFields(EntityType.PRODUCT, FieldType.VA_DATA_FIELD);
    }

    /**
     * return an unmodifiable list of field keys representing the product level local only fields
     * 
     * @return Collection<FieldKey> of product level local only fields
     */
    public static Collection<FieldKey> getProductLocalOnlyDataFields() {

        return getDataFields(EntityType.PRODUCT, FieldEnvironment.LOCAL);
    }

    /**
     * return an unmodifiable list of field keys representing the ndc level national only fields
     * 
     * @return Collection<FieldKey> of NDC national only fields
     */
    public static Collection<FieldKey> getNdcNationalOnlyDataFields() {

        return getDataFields(EntityType.NDC, FieldEnvironment.NATIONAL);
    }

    /**
     * return an unmodifiable list of field keys representing the product level national only fields
     * 
     * @return Collection<FieldKey> of product level national only fields
     */
    public static Collection<FieldKey> getProductNationalOnlyDataFields() {

        return getDataFields(EntityType.PRODUCT, FieldEnvironment.NATIONAL);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing all VA data fields.
     * 
     * @return Collection<FieldKey> of all VA data fields
     */
    public static Collection<FieldKey> getVaDataFields() {

        return getDataFields(FieldType.VA_DATA_FIELD);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing all VA data fields on the given {@link EntityType}.
     * 
     * @param entityType {@link EntityType} for which to retrieve VA data fields
     * @return Collection<FieldKey> of all VA data fields
     */
    public static Collection<FieldKey> getVaDataFields(EntityType entityType) {

        return getDataFields(entityType, FieldType.VA_DATA_FIELD);
    }

    /**
     * Return an unmodifiable List of FieldKeys representing all local-only VA data fields on the given {@link EntityType}.
     * 
     * @param entityType {@link EntityType} for which to retrieve VA data fields
     * @return Collection<FieldKey> of all local-only VA data fields for the given {@link EntityType}
     */
    public static Collection<FieldKey> getLocalOnlyVaDataFields(EntityType entityType) {

        return getDataFields(entityType, FieldType.VA_DATA_FIELD, FieldEnvironment.LOCAL);
    }

    /**
     * Return an unmodifiable list of field keys representing the local-only fields for the given {@link EntityType}
     * 
     * @param entityType {@link EntityType} for which to retrieve VA data fields
     * @return Collection<FieldKey> of local only fields
     */
    public static Collection<FieldKey> getLocalOnlyFields(EntityType entityType) {

        return getDataFields(entityType, FieldEnvironment.LOCAL);
    }

    /**
     * Create an attribute name from the String key value. The String key is split by its "." and then put in
     * initialCamelCase. For instance, the String key "generic.name" becomes the attribute name "genericName", the String key
     * "gcn.sequence.number" becomes the attribute name "gcnSequenceNumber", and the String key "ndc" simply stays the
     * attribute name "ndc".
     * 
     * If the given FieldKey is for a DataField, then the String "dataFields" is returned, not the derived name from the key.
     * 
     * @param fieldKey FieldKey to convert
     * @return String attribute name
     */
    public static String toAttributeName(FieldKey fieldKey) {

        if (fieldKey.isVaDataField()) {
            return VA_DATA_FIELDS.toAttributeName();
        } else {

            return fromDotsToCamelCase(fieldKey.getKey());
        }
    }

    /**
     * Convert a {@link FieldKey} into camelCase.
     * 
     * @param fieldKey FieldKey to convert
     * @return key in camelCase
     */
    private static String fromDotsToCamelCase(String fieldKey) {

        String[] words = fieldKey.split("\\.");
        StringBuffer attribute = new StringBuffer();

        for (int i = 0; i < words.length; i++) {
            if (i == 0) {
                attribute.append(words[i]);
            } else {
                attribute.append(words[i].substring(0, 1).toUpperCase() + words[i].substring(1));
            }
        }

        return attribute.toString();
    }

    /**
     * Map the given ValueObject attribute name to its {@link FieldKey}.
     * 
     * The default logic to create the {@link String} value of the key is to take the camelCase attribute name and separate
     * it at each capital letter with a period. The entire String is then converted to lower case. This generated
     * {@link String} value for the key is passed into {@link FieldKey#getKey(String)}.
     * 
     * For example, the attribute name "genericName" is converted to the key "generic.name", the attribute name
     * "gcnSequenceNumber" is converted to the key "gcn.sequence.number", and the attribute "ndc" is simply returned as the
     * key "ndc".
     * 
     * @param <T> Type of FieldKey
     * @param attribute String name of the ValueObject attribute
     * @return FieldKey for the given attribute
     */
    public static <T> FieldKey<T> toFieldKey(String attribute) {

        Matcher matcher = Pattern.compile("\\p{Upper}").matcher(attribute);

        StringBuffer stringKey = new StringBuffer();
        int begin = 0;
        int end = 0;

        while (matcher.find()) {
            end = matcher.start();
            stringKey.append(attribute.substring(begin, end));
            stringKey.append(".");
            begin = end;
        }

        stringKey.append(attribute.substring(begin));

        String myKey = stringKey.toString().toLowerCase();

        return FieldKey.getKey(myKey);
    }

    /**
     * Return all {@link FieldKey}.
     * 
     * @return all {@link FieldKey}
     */
    public static Set<FieldKey> values() {

        return new HashSet<FieldKey>(NAME_TO_KEY_MAP.values());
    }

    /**
     * Locates the main key for the specified searchable one, or else the passed in key if a mapping isn't found.
     * 
     * @param searchableKey The key to attempt to locate.
     * @return FieldKey The main key located, or else the same key as specified if no main key was mapped.
     */
    public static FieldKey findMainKeyFor(FieldKey searchableKey) {

        FieldKey mainKey = SEARCH_KEY_TO_MAIN_KEY_MAP.get(searchableKey);

        return (mainKey == null ? searchableKey : mainKey);
    }

    /**
     * Returns an unmodifiable map of the searchable keys to their main keys.
     * 
     * @return Map<FieldKey, FieldKey> An unmodifiable map of the searchable keys to main keys.
     */
    public static Map<FieldKey, FieldKey> getMapSearchableToMainKey() {

        return Collections.unmodifiableMap(SEARCH_KEY_TO_MAIN_KEY_MAP);
    }

    /**
     * MultitextDataFields get a hidden FieldKey instance representing the value itself. This aides the presentation in
     * displaying the table.
     * 
     * The MultitextDataField's FieldKey is used to know when to display the dynamic table, and its value FieldKey is used to
     * know what type of input box to display per row.
     */
    private void addMultitextValueFieldKey() {

        FieldKey value =
            new FieldKey(getKey() + MULTITEXT_HIDDEN_FIELD_KEY_SUFFIX, getFieldType(), FieldSubType.SIMPLE_DATA_FIELD,
                getFieldEnvironment(), getFieldClass(), isRequiresSecondApproval(), isEditableAfterCreation(),
                (EntityType[]) getEntityTypes().toArray());
        NAME_TO_KEY_MAP.put(value.getKey(), value);
    }

    /**
     * equals implementation
     * 
     * @param obj to check
     * @return true of obj is equal to the key
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof FieldKey) {
            return ((FieldKey) obj).getKey().equals(key);
        }

        return false;
    }

    /**
     * Get the Class type of the value this FieldKey deals with
     * 
     * @return Class of generic type
     */
    public Class getFieldClass() {

        return fieldClass;
    }

    /**
     * get field environment
     * 
     * @return fieldEnvironment property
     */
    public FieldEnvironment getFieldEnvironment() {

        return fieldEnvironment;
    }

    /**
     * get entity types
     * 
     * @return EntityTypes property
     */
    public List<EntityType> getEntityTypes() {

        return entityTypes;
    }

    /**
     * get field subtype
     * 
     * @return fieldType property
     */
    public FieldSubType getFieldSubType() {

        return fieldSubType;
    }

    /**
     * get field type
     * 
     * @return fieldType property
     */
    public FieldType getFieldType() {

        return fieldType;
    }

    /**
     * FieldKeys grouped by the DataField represented by this FieldKey
     * 
     * @return groupedFields property
     */
    public List<FieldKey> getGroupedFields() {

        return groupedFields;
    }

    /**
     * FieldKey of DataField that groups this DataField. Only valid if {@link #isGroupedDataField()} returns true (i.e., if
     * this FieldKey represents a grouped data field). If this data field does not represent a grouped data field, the
     * {@link #groupKey} attribute will be null.
     * 
     * @return FieldKey for DataField that groups this FieldKey (if the data field represented by this FieldKey is a grouped
     *         data field)
     */
    public FieldKey<? extends GroupDataField> getGroupKey() {

        return groupKey;
    }

    /**
     * get key
     * 
     * @return key String key value for this FieldKey
     */
    public String getKey() {

        return key;
    }

    /**
     * Get the localized abbreviation for the DataField represented by this FieldKey for the given {@link Locale}.
     * 
     * @param locale Locale for localization
     * @return String localized abbreviation for this key
     */
    public String getLocalizedAbbreviation(Locale locale) {

        return getLocalizedAbbreviation(this, locale);
    }

    /**
     * Get the localized abbreviation for the DataField represented by this FieldKey for the given {@link Locale} and
     * {@link EntityType}. If a localized value for the given {@link EntityType} is not found, the localization without the
     * {@link EntityType} is returned by calling {@link #getLocalizedAbbreviation(Locale)}.
     * 
     * @param locale Locale for localization
     * @param entityType {@link EntityType} for which to localize
     * @return String localized abbreviation for this key
     * 
     * @see #getLocalizedAbbreviation(Locale)
     */
    public String getLocalizedAbbreviation(Locale locale, EntityType entityType) {

        return getLocalizedAbbreviation(this, locale, entityType);
    }

    /**
     * Get the localized description for the DataField represented by this FieldKey for the given {@link Locale}.
     * 
     * @param locale Locale for localization
     * @return String localized description for this key
     */
    public String getLocalizedDescription(Locale locale) {

        return getLocalizedDescription(this, locale);
    }

    /**
     * Get the localized description for the DataField represented by this FieldKey for the given {@link Locale} and
     * {@link EntityType}. If a localized value for the given {@link EntityType} is not found, the localization without the
     * {@link EntityType} is returned by calling {@link #getLocalizedDescription(Locale)}.
     * 
     * @param locale Locale for localization
     * @param entityType {@link EntityType} for which to localize
     * @return String localized description for this key
     * 
     * @see #getLocalizedDescription(Locale)
     */
    public String getLocalizedDescription(Locale locale, EntityType entityType) {

        return getLocalizedDescription(this, locale, entityType);
    }

    /**
     * Get the localized name for the DataField represented by this FieldKey for the given {@link Locale}.
     * 
     * @param locale Locale for localization
     * @return String localized name for this key
     */
    public String getLocalizedName(Locale locale) {

        return getLocalizedName(this, locale);
    }

    /**
     * Get the localized name for the DataField represented by this FieldKey for the given {@link Locale} and
     * {@link EntityType}. If a localized value for the given {@link EntityType} is not found, the localization without the
     * {@link EntityType} is returned by calling {@link #getLocalizedName(Locale)}.
     * 
     * @param locale Locale for localization
     * @param entityType {@link EntityType} for which to localize
     * @return String localized name for this key
     * 
     * @see #getLocalizedName(Locale)
     */
    public String getLocalizedName(Locale locale, EntityType entityType) {

        return getLocalizedName(this, locale, entityType);
    }

    /**
     * Get the localized width for the DataField represented by this FieldKey for the given {@link Locale}.
     * 
     * @param locale Locale for localization
     * @return String localized width for this key
     */
    public String getLocalizedWidth(Locale locale) {

        return getLocalizedWidth(this, locale);
    }

    /**
     * get hashcode
     * 
     * @return int hash
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return new HashCodeBuilder().append(key).toHashCode();
    }

    /**
     * Return true if the validator class has been set.
     * 
     * @return true if the validator class has been set (i.e., if it is not null)
     */
    public boolean hasValidator() {

        return validator != null;
    }

    /**
     * is the field a boolean field?
     * 
     * @return true if this is a DataField<Boolean>
     */
    public boolean isBooleanSimpleDataField() {

        return isSimpleDataField() && Boolean.class.isAssignableFrom(fieldClass);
    }

    /**
     * True if this FieldKey represents a data field which is editable at Local and National.
     * 
     * @return boolean true if {@link FieldEnvironment#BOTH} is equal to this FieldKey's field environment.
     */
    public boolean isBothLocalAndNationalDataField() {

        return FieldEnvironment.BOTH.equals(fieldEnvironment);
    }

    /**
     * True if this FieldKey represents a generic Data Field. This classification is a "catch all" for any FieldKey that is
     * not a VA Data Field or a Linked Data Field.
     * 
     * @return boolean true if {@link FieldType#DATA_FIELD} is equal to this FieldKey's field type.
     */
    public boolean isDataField() {

        return FieldType.DATA_FIELD.equals(fieldType);
    }

    /**
     * is field a date list field?
     * 
     * @return true if this is a ListDataField<Date>
     */
    public boolean isDateListDataField() {

        return isListDataField() && Date.class.isAssignableFrom(fieldClass);
    }

    /**
     * is date multitext data field
     * 
     * @return true if this is a MultitextDataField<Date>
     */
    public boolean isDateMultitextDataField() {

        return isMultitextDataField() && Date.class.isAssignableFrom(fieldClass);
    }

    /**
     * is data simple data field
     * 
     * @return true if this is a DataField<Date>
     */
    public boolean isDateSimpleDataField() {

        return isSimpleDataField() && Date.class.isAssignableFrom(fieldClass);
    }

    /**
     * True if this FieldKey is a primary key type filed
     * 
     * @return true if this is a primary key data field
     */
    public boolean isSingleSelectPrimaryKeyDataField() {

        return FieldSubType.SINGLE_SELECT_PRIMARY_KEY_DATA_FIELD.equals(fieldSubType);
    }

    /**
     * True if this FieldKey is a primary key type filed
     * 
     * @return true if this is a primary key data field
     */
    public boolean isMultiSelectPrimaryKeyDataField() {

        return FieldSubType.MULTI_SELECT_PRIMARY_KEY_DATA_FIELD.equals(fieldSubType);
    }

    /**
     * True if this FieldKey is a primary key type filed
     * 
     * @return true if this is a primary key data field
     */
    public boolean isPrimaryKeyDataField() {

        return isSingleSelectPrimaryKeyDataField() || isMultiSelectPrimaryKeyDataField();
    }

    /**
     * is double list data field
     *  
     * @return true if this is a ListDataField<Double>
     */
    public boolean isDoubleListDataField() {

        return isListDataField() && Double.class.isAssignableFrom(fieldClass);
    }

    /**
     * is double multitext datafield
     * 
     * @return true if this is a MultitextDataField<Double>
     */
    public boolean isDoubleMultitextDataField() {

        return isMultitextDataField() && Double.class.isAssignableFrom(fieldClass);
    }

    /**
     * is doub simple data field
     * 
     * @return true if this is a DataField<Double>
     */
    public boolean isDoubleSimpleDataField() {

        return isSimpleDataField() && Double.class.isAssignableFrom(fieldClass);
    }

    /**
     * True if this FieldKey represents a FDB Direct Linked DataField.
     * 
     * @return boolean true if {@link FieldType#FDB_DIRECT_LINKED_DATA_FIELD} is equal to this FieldKey's field type.
     */
    public boolean isFdbDirectLinkedDataField() {

        return FieldType.FDB_DIRECT_LINKED_DATA_FIELD.equals(fieldType);
    }

    /**
     * True if this FieldKey represents a FDB Mapped Linked DataField.
     * 
     * @return boolean true if {@link FieldType#FDB_MAPPED_LINKED_DATA_FIELD} is equal to this FieldKey's field type.
     */
    public boolean isFdbMappedLinkedDataField() {

        return FieldType.FDB_MAPPED_LINKED_DATA_FIELD.equals(fieldType);
    }

    /**
     * True if this FieldKey represents a GroupDataField.
     * 
     * @return true if this is a GroupDataField
     */
    public boolean isGroupDataField() {

        return FieldSubType.GROUP_DATA_FIELD.equals(fieldSubType);
    }

    /**
     * True if this FieldKey is grouped by a GroupDataField
     * 
     * @return true if this is a grouped data field
     */
    public boolean isGroupedDataField() {

        return getDataFields(FieldSubType.GROUPED_DATA_FIELD).contains(this);
    }

    /**
     * True if this FieldKey represents a GroupListDataField.
     * 
     * @return true if this is a GroupListDataField
     */
    public boolean isGroupListDataField() {

        return FieldSubType.GROUP_LIST_DATA_FIELD.equals(fieldSubType);
    }

    /**
     * True if this FieldKey represents a Linked DataField.
     * 
     * @return boolean true if {@link FieldType#FDB_DIRECT_LINKED_DATA_FIELD},{@link FieldType#FDB_MAPPED_LINKED_DATA_FIELD},
     *         or {@link FieldType#VISTA_LINKED_DATA_FIELD} is equal to this FieldKey's field type.
     */
    public boolean isLinkedDataField() {

        return isFdbDirectLinkedDataField() || isFdbMappedLinkedDataField() || isVistaLinkedDataField();
    }

    /**
     * True if this FieldKey represents a single-select ListDataField, but not a "simple" DataField or GroupDataField.
     * 
     * @see #isDataFieldType()
     * @see #isGroupDataField()
     * @return true if this is a {@link FieldSubType#SINGLE_SELECT_LIST_DATA_FIELD}
     */
    public boolean isSingleSelectListDataField() {

        return FieldSubType.SINGLE_SELECT_LIST_DATA_FIELD.equals(fieldSubType);
    }

    /**
     * True if this FieldKey represents a multi-select ListDataField, but not a "simple" DataField or GroupDataField.
     * 
     * @see #isDataFieldType()
     * @see #isGroupDataField()
     * @return true if this is a {@link FieldSubType#MULTI_SELECT_LIST_DATA_FIELD}
     */
    public boolean isMultiSelectListDataField() {

        return FieldSubType.MULTI_SELECT_LIST_DATA_FIELD.equals(fieldSubType)
            || FieldSubType.LIST_CHECKBOX_DATA_FIELD.equals(fieldSubType);
    }

    /**
     * True if this FieldKey represents a list checkbox data field (categories and sub-categories),
     * but not a "simple" DataField or GroupDataField.
     * 
     * Added so that categories and sub-categories could be sortable
     * 
     * @see #isDataFieldType()
     * @see #isGroupDataField()
     * @return true if this is a {@link FieldSubType#LIST_CHECKBOX_DATA_FIELD}
     */
    public boolean isListCheckBoxDataField() {

        return FieldSubType.LIST_CHECKBOX_DATA_FIELD.equals(fieldSubType);
    }

    /**
     * True if this FieldKey represents a ListDataField, but not a "simple" DataField or GroupDataField.
     * 
     * @see #isDataFieldType()
     * @see #isGroupDataField()
     * @return true if this {@link #isSingleSelectListDataField()} or {@link #isMultiSelectListDataField() return true     
     */
    public boolean isListDataField() {

        return isSingleSelectListDataField() || isMultiSelectListDataField();
    }

    /**
     * True if this FieldKey represents a Local Only data field.
     * 
     * @return boolean true if {@link FieldEnvironment#LOCAL} is equal to this FieldKey's field environment.
     */
    public boolean isLocalOnlyDataField() {

        return FieldEnvironment.LOCAL.equals(fieldEnvironment);
    }

    /**
     * isLongListDataField
     * 
     * @return true if this is a ListDataField<Long>
     */
    public boolean isLongListDataField() {

        return isListDataField() && Long.class.isAssignableFrom(fieldClass);
    }

    /**
     * isLongMultitextDataField
     * 
     * @return true if this is a MultitextDataField<Long>
     */
    public boolean isLongMultitextDataField() {

        return isMultitextDataField() && Long.class.isAssignableFrom(fieldClass);
    }

    /**
     * isLongSimpleDataField
     * 
     * @return true if this is a DataField<Long>
     */
    public boolean isLongSimpleDataField() {

        return isSimpleDataField() && Long.class.isAssignableFrom(fieldClass);
    }

    /**
     * True if this FieldKey represents a MultitextDataField.
     * 
     * @return true if this is a MultitextDataField
     */
    public boolean isMultitextDataField() {

        return FieldSubType.MULTITEXT_DATA_FIELD.equals(fieldSubType);
    }

    /**
     * True if this FieldKey represents the hidden FieldKey added when a new MultitextDataField FieldKey is created. Used by
     * the Presentation to decide if the national editable link/icon should be displayed.
     * 
     * @return boolean true if this FieldKey represents the hidden FieldKey
     */
    public boolean isMultitextDataFieldValue() {

        return getKey().contains(MULTITEXT_HIDDEN_FIELD_KEY_SUFFIX);
    }

    /**
     * True if this FieldKey represents a National data field.
     * 
     * @return boolean true if {@link FieldEnvironment#NATIONAL} is equal to this FieldKey's field environment.
     */
    public boolean isNationalDataField() {

        return FieldEnvironment.NATIONAL.equals(fieldEnvironment);
    }

    /**
     * Return true if this FieldKey is stored at the NDC level. This is not mutually exclusive with
     * {@link #isOrderableItemLevelField()} and {@link #isProductLevelField()}, as fields can be stored at multiple levels.
     * 
     * @return boolean
     */
    public boolean isNdcLevelField() {

        return entityTypes.contains(EntityType.NDC);
    }

    /**
     * isNumberListDataField
     * 
     * @return true if this is a ListDataField<Number>
     */
    public boolean isNumberListDataField() {

        return isListDataField() && Number.class.isAssignableFrom(fieldClass);
    }

    /**
     * isNumberMultitextDataField
     * 
     * @return true if this is a MultitextDataField<Number>
     */
    public boolean isNumberMultitextDataField() {

        return isMultitextDataField() && Number.class.isAssignableFrom(fieldClass);
    }

    /**
     * isNumberSimpleDataField
     * 
     * @return true if this is a DataField<Number>
     */
    public boolean isNumberSimpleDataField() {

        return isSimpleDataField() && Number.class.isAssignableFrom(fieldClass);
    }

    /**
     * Return true if this FieldKey is stored at the Orderable Item level. This is not mutually exclusive with
     * {@link #isProductLevelField()} and {@link #isNdcLevelField()}, as fields can be stored at multiple levels.
     * 
     * @return boolean
     */
    public boolean isOrderableItemLevelField() {

        return entityTypes.contains(EntityType.ORDERABLE_ITEM);
    }

    /**
     * True if this FieldKey represents a field (member variable) within a ValueObject (i.e., strength, generic name)
     * 
     * @return boolean true if {@link FieldSubType#PRIMITIVE} is equal to this FieldKey's field sub type
     */
    public boolean isPrimitiveField() {

        return FieldSubType.PRIMITIVE.equals(fieldSubType);
    }

    /**
     * True if this FieldKey represents a numeric field (member variable) within a ValueObject.
     * 
     * @return boolean true if {@link FieldSubType#PRIMITIVE} is equal to this FieldKey's field sub type and if the
     *         fieldClass extends {@link Number}
     */
    public boolean isNumericPrimitiveField() {

        return isPrimitiveField() && Number.class.isAssignableFrom(getFieldClass());
    }

    /**
     * Return true if this FieldKey is stored at the Product level. This is not mutually exclusive with
     * {@link #isOrderableItemLevelField()} and {@link #isNdcLevelField()}, as fields can be stored at multiple levels.
     * 
     * @return boolean
     */
    public boolean isProductLevelField() {

        return entityTypes.contains(EntityType.PRODUCT);
    }

    /**
     * Due to lazy loading Hibernate features, the results of sorting a multi-select field (e.g., multi-select list data
     * field) do not make much sense (all selected values are displayed for all results, making the sort nonsensical).
     * <p>
     * Therefore, this method returns true unless this FieldKey represents a multi-select field.
     * 
     * @return boolean true if this {@link FieldKey} is sortable
     */
    public boolean isSortable() {

        boolean rv = (isMultiSelectField() || isMultiSelectPrimaryKeyDataField());
        rv = (rv || isMultiSelectListDataField() || isMultitextDataField());

        //rv = ((rv || isMultiSelectListDataField() || isMultitextDataField()) && !isListCheckBoxDataField());

        return !(rv);
    }

    /**
     * isRequiresSecondApproval
     * 
     * @return requiresSecondApproval property
     */
    public boolean isRequiresSecondApproval() {

        return requiresSecondApproval;
    }

    /**
     * isEditableAfterCreation
     * 
     * @return editableAfterCreation property
     */
    public boolean isEditableAfterCreation() {

        return editableAfterCreation;
    }

    /**
     * True if this FieldKey represents a single-select {@link Selectable}.
     * 
     * @return true if this is a {@link FieldSubType#SINGLE_SELECT}
     */
    public boolean isSingleSelectField() {

        return FieldSubType.SINGLE_SELECT.equals(fieldSubType);
    }

    /**
     * True if this FieldKey represents a multi-select {@link Selectable}.
     * 
     * @return true if this is a {@link FieldSubType#MULTI_SELECT}
     */
    public boolean isMultiSelectField() {

        return FieldSubType.MULTI_SELECT.equals(fieldSubType);
    }

    /**
     * True if this FieldKey represents a single-select or multi-select {@link Selectable}.
     * 
     * @return true if {@link #isSingleSelectField()} or {@link #isMultiSelectField()} return true
     */
    public boolean isSelectableField() {

        return isSingleSelectField() || isMultiSelectField();
    }

    /**
     * True if this FieldKey represents a DataField, but not a ListDataField or GroupDataField.
     * 
     * @see #isListDataField()
     * @see #isGroupDataField()
     * @return boolean
     */
    public boolean isSimpleDataField() {

        return FieldSubType.SIMPLE_DATA_FIELD.equals(fieldSubType);
    }

    /**
     * isStringListDataField
     * 
     * @return true if this is a ListDataField<String>
     */
    public boolean isStringListDataField() {

        return isListDataField() && String.class.isAssignableFrom(fieldClass);
    }

    /**
     * isStringMultitextDataField
     * 
     * @return true if this is a MultitextDataField<String>
     */
    public boolean isStringMultitextDataField() {

        return isMultitextDataField() && String.class.isAssignableFrom(fieldClass);
    }

    /**
     * isStringSimpleDataField
     * 
     * @return true if this is a DataField<String>
     */
    public boolean isStringSimpleDataField() {

        return isSimpleDataField() && String.class.isAssignableFrom(fieldClass);
    }

    /**
     * True if this FieldKey represents a VA Data Field.
     * 
     * @return boolean true if {@link FieldType#VA_DATA_FIELD} is equal to this FieldKey's field type.
     */
    public boolean isVaDataField() {

        return FieldType.VA_DATA_FIELD.equals(fieldType);
    }

    /**
     * True if this FieldKey represents a ValueObject (i.e., Route, Unit, Product)
     * 
     * @return boolean true if {@link FieldSubType#VALUE_OBJECT} is equal to this FieldKey's field sub type
     */
    public boolean isValueObject() {

        return FieldSubType.VALUE_OBJECT.equals(fieldSubType);
    }

    /**
     * True if this FieldKey represents a VistA Linked DataField.
     * 
     * @return boolean true if {@link FieldType#VISTA_LINKED_DATA_FIELD} is equal to this FieldKey's field type.
     */
    public boolean isVistaLinkedDataField() {

        return FieldType.VISTA_LINKED_DATA_FIELD.equals(fieldType);
    }

    /**
     * A {@link FieldKey} is "searchable" if the key ends with {@link #SEARCHABLE_SUFFIX}.
     * 
     * @return boolean true if the key ends with {@value #SEARCHABLE_SUFFIX}
     */
    public boolean isSearchableField() {

        return getKey().endsWith(SEARCHABLE_SUFFIX);
    }

    /**
     * If this FieldKey {@link #isSearchableField()}, then this method will return the {@link FieldKey} for which the search
     * is actually for.
     * <p>
     * For example, {@link #APPLICATION_PACKAGE_USE_SEARCHABLE} will return {@link #APPLICATION_PACKAGE_USE}.
     * 
     * @return {@link FieldKey}
     */
    public FieldKey getSearchableFieldKey() {

        String searchableKey = getKey().substring(0, getKey().length() - SEARCHABLE_SUFFIX.length());

        return FieldKey.getKey(searchableKey);
    }

    /**
     * Create a new instance of the AbstractValidator for this field key. If the validator is not set or the validator cannot
     * be instantiated, a CommonException (PharmacyRuntimeException) is thrown.
     * 
     * @return AbstractValidator instance for the field represented by this FieldKey
     */
    public AbstractValidator<T> newValidatorInstance() {

        if (hasValidator()) {
            try {
                return validator.newInstance();
            } catch (InstantiationException e) {
                throw new CommonException(e, CommonException.VALIDATOR_ERROR, getClass().getName());
            } catch (IllegalAccessException e) {
                throw new CommonException(e, CommonException.VALIDATOR_ERROR, getClass().getName());
            }
        } else {
            throw new CommonException(CommonException.VALIDATOR_ERROR, getClass().getName());
        }
    }

    /**
     * Add this instance of FieldKey to the Map of FieldKey enumerations to FieldKeys.
     * 
     * @param fieldKeyEnumeration enumeration to add
     */
    private synchronized void populateEnumToKeyMap(Enum fieldKeyEnumeration) {

        if (ENUM_TO_FIELD_KEYS == null) {
            ENUM_TO_FIELD_KEYS = new HashMap<Enum, Collection<FieldKey>>();
        }

        if (ENUMS_TO_FIELD_KEYS == null) {
            ENUMS_TO_FIELD_KEYS = new HashMap<List<Enum>, Collection<FieldKey>>();
        }

        Collection<FieldKey> fields = ENUM_TO_FIELD_KEYS.get(fieldKeyEnumeration);

        if (fields == null) {
            fields = new ArrayList<FieldKey>();
            ENUM_TO_FIELD_KEYS.put(fieldKeyEnumeration, fields);
        }

        fields.add(this);
    }

    /**
     * Map the given field name to this instance of a FieldKey
     * 
     * @param tKey String name of the data field
     */
    private synchronized void populateNameToKeyMap(String tKey) {

        if (NAME_TO_KEY_MAP == null) {
            NAME_TO_KEY_MAP = new HashMap<String, FieldKey>();
        }

        if (NAME_TO_KEY_MAP.put(tKey, this) != null) {
            throw new IllegalArgumentException("Data field key already put for name " + tKey + ".");
        }
    }

    /**
     * Map the given {@link ValueObject} class to this instance of a FieldKey.
     * 
     * @param fldClass Class<ValueObject>
     */
    private synchronized void populateValueObjectToKeyMap(Class fldClass) {

        if (isValueObject() || isSelectableField()) {
            if (VALUE_OBJECT_TO_KEY_MAP == null) {
                VALUE_OBJECT_TO_KEY_MAP = new HashMap<Class, FieldKey>();
            }

            if (VALUE_OBJECT_TO_KEY_MAP.containsKey(fldClass)) {
                LOG.warn("ValueObject to FieldKey mapping already exists for class '" + fldClass.getName()
                    + "' and FieldKey '" + VALUE_OBJECT_TO_KEY_MAP.get(fldClass).getKey()
                    + "'. Any validator configured for any other FieldKey/ValueObject comibination will not be"
                    + " registered, unless newValidatorInstance() is invoked directly from the other FieldKey instances.");
            } else {
                VALUE_OBJECT_TO_KEY_MAP.put(fldClass, this);
            }
        }
    }

    /**
     * Convert this FieldKey into an attribute name.
     * 
     * @return String attribute name
     * @see FieldKey#toAttributeName(FieldKey)
     */
    public String toAttributeName() {

        return toAttributeName(this);
    }

    /**
     * Returns the field key as a camel case string.
     * 
     * 
     * @return key as camel case key
     */
    public String fromDotsToCamelCase() {

        return fromDotsToCamelCase(this.getKey());
    }

    /**
     * toString
     * 
     * @return String
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return new ToStringBuilder(this).append("key", key).toString();
    }
}
