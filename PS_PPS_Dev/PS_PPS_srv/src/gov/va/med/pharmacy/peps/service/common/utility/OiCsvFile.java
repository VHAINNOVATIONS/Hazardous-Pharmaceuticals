/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.Category;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugTextVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.MultitextDataField;


/**
 * The OiCsvFile class extends the CsvFile class that contains common csv file
 * code. This class contains code specific to the import and export of OI files.
 * Methods defined in this subclass and used by the Migration service are:
 *   1. openForImport - open file and validate column header line
 *   2. getNextOi - get next OI from file, returns OrderableItemVo object
 *   3. openForExport - open export and write header line
 *   4. putNextOi - put next OI to file, input OrderableItemVo object
 */
public class OiCsvFile extends CsvFile {

    /** OI_NAME */
    public static final String OI_NAME = "OiName";

    /** VISTA_OI_NAME */
    public static final String VISTA_OI_NAME = "VistaOiName";

    /** CATEGORY */
    public static final String CATEGORY = "ProductType";

    /** DOSAGE_FORM_NAME */
    public static final String DOSAGE_FORM_NAME = "DosageFormName";

    /** INACTIVATION_DATE */
    public static final String INACTIVATION_DATE = "InactivationDate";

    /** PROP_INACT_DATE */
    public static final String PROP_INACT_DATE = "PropInactivationDate";

    /** LIFETIME_DOSAGE */
    public static final String LIFETIME_DOSAGE = "LifetimeCumulDosage";

    /** NAT_FORM_IND */
    public static final String NAT_FORM_IND = "NationalFormularyInd";

    /** NON_VA_MED */
    public static final String NON_VA_MED = "NonVaMed";

    /** OI_IV_FLAG */
    public static final String OI_IV_FLAG = "OiIvFlag";

    /** DAYS_LIMIT */
    public static final String DAYS_LIMIT = "DaysOrDoseLimit";

    /** OI_SYNONYM */
    public static final String OI_SYNONYM = "OiSynonym";

    /** NAT_DRUG_TEXT */
    public static final String NAT_DRUG_TEXT = "NatDrugText";

    /** REQ_REJ_REASON */
    public static final String REQ_REJ_REASON = "RequestRejectReason";

    /** REQ_REJ_TEXT */
    public static final String REQ_REJ_TEXT = "RejectReasonText";

    /** PAT_INSTR */
    public static final String PAT_INSTR = "PatientInstructions";

    /** OTHER_INSTR */
    public static final String OTHER_INSTR = "OtherLanguageInstructions";

    /** STD_MED_RT */
    public static final String STD_MED_RT = "StandardMedRoute";

    // column headings stored in first row of csv file
    // if a name is changed, index constant must be updated

    /** OI_FIELD_NAMES */
    public static final String[] OI_FIELD_NAMES = {
        OI_NAME, VISTA_OI_NAME, CATEGORY, DOSAGE_FORM_NAME, INACTIVATION_DATE, PROP_INACT_DATE, LIFETIME_DOSAGE, NAT_FORM_IND,
        NON_VA_MED, OI_IV_FLAG, DAYS_LIMIT, OI_SYNONYM, NAT_DRUG_TEXT, REQ_REJ_REASON, REQ_REJ_TEXT, PAT_INSTR,
        OTHER_INSTR, STD_MED_RT };

    // use these constants to index field arrays

    /** I_OI_NAME */
    public static final int I_OI_NAME = ArrayUtils.indexOf(OI_FIELD_NAMES, OI_NAME);

    /** I_VISTA_OI_NAME */
    public static final int I_VISTA_OI_NAME = ArrayUtils.indexOf(OI_FIELD_NAMES, VISTA_OI_NAME);

    /** I_PROD_TYPE */
    public static final int I_PROD_TYPE = ArrayUtils.indexOf(OI_FIELD_NAMES, CATEGORY);

    /** I_DOSE_FORM */
    public static final int I_DOSE_FORM = ArrayUtils.indexOf(OI_FIELD_NAMES, DOSAGE_FORM_NAME);

    /** I_INACT_DATE */
    public static final int I_INACT_DATE = ArrayUtils.indexOf(OI_FIELD_NAMES, INACTIVATION_DATE);

    /** I_PROP_INACT_DATE */
    public static final int I_PROP_INACT_DATE = ArrayUtils.indexOf(OI_FIELD_NAMES, PROP_INACT_DATE);

    /** I_LIFE_DOSE */
    public static final int I_LIFE_DOSE = ArrayUtils.indexOf(OI_FIELD_NAMES, LIFETIME_DOSAGE);

    /** I_NAT_FORM_IND */
    public static final int I_NAT_FORM_IND = ArrayUtils.indexOf(OI_FIELD_NAMES, NAT_FORM_IND);

    /** I_NON_VA_MED */
    public static final int I_NON_VA_MED = ArrayUtils.indexOf(OI_FIELD_NAMES, NON_VA_MED);

    /** I_OI_IV_FLAG */
    public static final int I_OI_IV_FLAG = ArrayUtils.indexOf(OI_FIELD_NAMES, OI_IV_FLAG);

    /** I_DOD_LIMIT */
    public static final int I_DOD_LIMIT = ArrayUtils.indexOf(OI_FIELD_NAMES, DAYS_LIMIT);

    /** I_SYNONYM */
    public static final int I_SYNONYM = ArrayUtils.indexOf(OI_FIELD_NAMES, OI_SYNONYM);

    /** I_DRUG_TEXT */
    public static final int I_DRUG_TEXT = ArrayUtils.indexOf(OI_FIELD_NAMES, NAT_DRUG_TEXT);

    /** I_REQ_REQ_REASON */
    public static final int I_REQ_REQ_REASON = ArrayUtils.indexOf(OI_FIELD_NAMES, REQ_REJ_REASON);

    /** I_REQ_REQ_TEXT */
    public static final int I_REQ_REQ_TEXT = ArrayUtils.indexOf(OI_FIELD_NAMES, REQ_REJ_TEXT);

    /** I_PAT_INSTR */
    public static final int I_PAT_INSTR = ArrayUtils.indexOf(OI_FIELD_NAMES, PAT_INSTR);

    /** I_OTHER_INSTR */
    public static final int I_OTHER_INSTR = ArrayUtils.indexOf(OI_FIELD_NAMES, OTHER_INSTR);

    /** I_STD_MED_ROUTE */
    public static final int I_STD_MED_ROUTE = ArrayUtils.indexOf(OI_FIELD_NAMES, STD_MED_RT);

    private static final Logger LOG = Logger.getLogger(OiCsvFile.class);
    private static final String FOR_OI = "For OI ";

    /**
     * subclass method to open OI file for import and validate the header row
     * 
     * @param filename filename
     * @throws MigrationException 
     */
    public void openForImport(String filename) throws MigrationException {

        // open file and create string array with column headings
        String[] headerArray = this.openAndReadHeader(filename);

        try {

            //  compare with a OI valid header
            for (int i = 0; i < headerArray.length; i++) {
                if (OI_FIELD_NAMES[i].equals(headerArray[i])) {
                    LOG.debug("size +  " + i + " is good!.");
                } else {
                    LOG.warn("size  " + i + " is bad.  " + OI_FIELD_NAMES[i] + ":" + headerArray[i]);
                }
            }
        } catch (Exception e) {
            throw new MigrationException("Column header on line 1 is not valid. Exception is " + e.toString());
        }

        if (!(Arrays.equals(headerArray, OI_FIELD_NAMES))) {
            throw new MigrationException("Column header on line 1 is not valid.");
        }
    }

    /**
     * Overloaded Method to open OI file for import and validate the header row.
     * @param pInputStream - passes a file ptr.
     * @throws MigrationException 
     */
    public void openForImport(InputStream pInputStream) throws MigrationException {
        String[] headerArray = this.openAndReadHeader(pInputStream);

        // compare with a OI valid header
        for (int i = 0; i < headerArray.length; i++) {
            if (OI_FIELD_NAMES[i].equals(headerArray[i])) {
                LOG.debug("size + " + i + " is good.");
            } else {
                LOG.warn("size " + i + " is bad. " + OI_FIELD_NAMES[i] + ":" + headerArray[i]);
            }
        }

        if (!(Arrays.equals(headerArray, OI_FIELD_NAMES))) {
            throw new MigrationException("Column header on line 1 isn't valid.");
        }
    }

    /**
     * getNextOi
     * subclass method to get next OI from file, return OrderableItemVo or null if EOF
     * input parameter oiVo should contain a blank template with default data fields
     * @param oiVo OrderableItemVo
     * @return OrderableItemVo
     * @throws MigrationException 
     */
    public OrderableItemVo getNextOi(OrderableItemVo oiVo) throws MigrationException {

        //----------------------------------------
        // call superclass member to read next row
        //----------------------------------------
        String[] oiFields = this.getNextRow(OI_FIELD_NAMES.length);

        if (oiFields == null) {
            return null;
        }

        //verify row has correct number of data fields
        if (oiFields.length < OI_FIELD_NAMES.length) {
            throw new MigrationException(ROW_ERROR_MESSAGE, Integer.toString(fileRowNumber), "", "", "");
        }

        // -------------------------------------------------
        // put file fields into oiVo object and data fields
        // convert fields if required but do not validate
        // service will call validators on returned oiVo
        // --------------------------------------------------

        //get data fields from blank Vo
        DataFields<DataField> vadfs = oiVo.getVaDataFields();

        //initialize Vo data fields (not contained in file)
        oiVo.setNational();
        oiVo.setRequestItemStatus(RequestItemStatus.APPROVED);

        //OiName field (required)
        //store string in oiVo variable
        if (oiFields[I_OI_NAME].length() > 0) {
            oiVo.setOiName(oiFields[I_OI_NAME]);
        } else {
            throw new MigrationException("PPS OI Name field is empty.", Integer.toString(fileRowNumber), oiFields[I_OI_NAME],
                                         OI_FIELD_NAMES[I_OI_NAME], oiFields[I_OI_NAME]);
        }

        //OiVistaName field (required)
        //store string in oiVo variable
        String vistaNameTemp = oiFields[I_VISTA_OI_NAME];

        if (StringUtils.isEmpty(vistaNameTemp)) {
            throw new MigrationException("PPS OI Vista Name field is empty.", Integer.toString(fileRowNumber),
                                         oiFields[I_OI_NAME], OI_FIELD_NAMES[I_VISTA_OI_NAME], oiFields[I_VISTA_OI_NAME]);
        } else {
            if (vistaNameTemp.length() > PPSConstants.I40) {
                oiVo.setVistaOiName(vistaNameTemp.substring(0, PPSConstants.I39));
            } else {
                oiVo.setVistaOiName(vistaNameTemp);
            }
        }

        //ProductType multiple field (required)
        //store string in data field, may contain multiples
        List<Category> productCatList = new ArrayList<Category>();

        String[] products = oiFields[I_PROD_TYPE].split(FIELD_MULTIPLE_SEPARATOR_SPLIT);

        for (String product : products) {

            if (product.equals(Category.SUPPLY.toString())) {
                productCatList.add(Category.SUPPLY);
            }

            if (product.equals(Category.MEDICATION.toString())) {
                productCatList.add(Category.MEDICATION);
            }

            if (product.equals(Category.COMPOUND.toString())) {
                productCatList.add(Category.COMPOUND);
            }

            if (product.equals(Category.INVESTIGATIONAL.toString())) {
                productCatList.add(Category.INVESTIGATIONAL);
            }
        }

        oiVo.setCategories(productCatList);

        //DosageFormName field (required)
        if (oiFields[I_DOSE_FORM] == null) {
            throw new MigrationException(FOR_OI + oiVo.getOiName() + ", the dosage Form name field is blank",
                                         Integer.toString(fileRowNumber), oiFields[I_OI_NAME], OI_FIELD_NAMES[I_DOSE_FORM],
                                         oiFields[I_DOSE_FORM]);
        } else {

            //store string in DosageFormVo variable, name only
            DosageFormVo dosageForm = new DosageFormVo();
            dosageForm.setDosageFormName(oiFields[I_DOSE_FORM]);
            oiVo.setDosageForm(dosageForm);
        }

        //InactivationDate field (optional)
        //store date in oiVo variable and set oiVo status variable
        //if empty, oi is active
        if (oiFields[I_INACT_DATE].equals("")) {
            oiVo.setInactivationDate(null);
            oiVo.setItemStatus(ItemStatus.ACTIVE);
        } else {
            try {
                Date inactiveDate = df.parse(oiFields[I_INACT_DATE]);
                oiVo.setItemStatus(ItemStatus.INACTIVE);
                oiVo.setInactivationDate(inactiveDate);
            } catch (ParseException ex) {
                throw new MigrationException(FOR_OI + oiVo.getOiName() + ", " + DATE_ERROR_MESSAGE,
                                             Integer.toString(fileRowNumber), oiFields[I_OI_NAME],
                                             OI_FIELD_NAMES[I_INACT_DATE], oiFields[I_INACT_DATE]);
            }
        }

        return getNextOi2(oiVo, oiFields, vadfs);
    }

    /**
     * getNextOi2
     * subclass method to get next OI from file, return OrderableItemVo or null if EOF
     * input parameter oiVo should contain a blank template with default data fields
     * @param oiVo OrderableItemVo
     * @param oiFields oiFields
     * @param vadfs VA Data fields
     * @return OrderableItemVo
     * @throws MigrationException 
     */
    private OrderableItemVo getNextOi2(OrderableItemVo oiVo, String[] oiFields, DataFields<DataField> vadfs)
        throws MigrationException {

        //PropInactivationDate field (optional)
        //store date in oiVo variable
        DataField<Date> proposedInactivationDate = vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);

        if (oiFields[I_PROP_INACT_DATE].equals("")) {
            proposedInactivationDate.selectValue(null);
            oiVo.getVaDataFields().setDataField(proposedInactivationDate);
        } else {
            try {
                Date inactiveDate = df.parse(oiFields[I_PROP_INACT_DATE]);
                proposedInactivationDate.selectValue(inactiveDate);
                oiVo.getVaDataFields().setDataField(proposedInactivationDate);
            } catch (ParseException ex) {
                throw new MigrationException(FOR_OI + oiVo.getOiName() + ", " + DATE_ERROR_MESSAGE,
                                             Integer.toString(fileRowNumber), oiFields[I_OI_NAME],
                                             OI_FIELD_NAMES[I_PROP_INACT_DATE], oiFields[I_PROP_INACT_DATE]);
            }
        }

        //LifetimeCumulDosage field (defaults to N)
        //use Y/N value to set boolean data field
        DataField<Boolean> lifetime = vadfs.getDataField(FieldKey.LIFETIME_CUMULATIVE_DOSAGE);

        if (oiFields[I_LIFE_DOSE].equals("Y")) {
            lifetime.selectValue(true);
            oiVo.getVaDataFields().setDataField(lifetime);
        } else if (oiFields[I_LIFE_DOSE].equals("N")) {
            lifetime.selectValue(false);
            oiVo.getVaDataFields().setDataField(lifetime);
        } else {
            throw new MigrationException(FOR_OI + oiVo.getOiName() + ", " + BOOLEAN_ERROR_MESSAGE,
                                         Integer.toString(fileRowNumber), oiFields[I_OI_NAME], OI_FIELD_NAMES[I_LIFE_DOSE],
                                         oiFields[I_LIFE_DOSE]);
        }

        //NationalFormularyIndicator field (defaults to N)
        //use Y/N value to set oiVo boolean variable
        if (oiFields[I_NAT_FORM_IND].equals("Y")) {
            oiVo.setNationalFormularyIndicator(true);
        } else if (oiFields[I_NAT_FORM_IND].equals("N")) {
            oiVo.setNationalFormularyIndicator(false);
        } else {
            throw new MigrationException(FOR_OI + oiVo.getOiName() + ", " + BOOLEAN_ERROR_MESSAGE,
                                         Integer.toString(fileRowNumber), oiFields[I_OI_NAME], OI_FIELD_NAMES[I_NAT_FORM_IND],
                                         oiFields[I_NAT_FORM_IND]);
        }

        //NonVaMed field (defaults to Y)
        //use Y/N value to set oiVo boolean variable
        if (oiFields[I_NON_VA_MED].equals("Y")) {
            oiVo.setNonVaMed(true);
        } else if (oiFields[I_NON_VA_MED].equals("N")) {
            oiVo.setNonVaMed(false);
        } else {
            throw new MigrationException(FOR_OI + oiVo.getOiName() + ", " + BOOLEAN_ERROR_MESSAGE,
                                         Integer.toString(fileRowNumber), oiFields[I_OI_NAME], OI_FIELD_NAMES[I_NON_VA_MED],
                                         oiFields[I_NON_VA_MED]);
        }

        //OiIvFlag field (defaults to N)
        //use Y/N value to set boolean data field
        DataField<Boolean> ivFlag = vadfs.getDataField(FieldKey.OI_IV_FLAG);

        if (oiFields[I_OI_IV_FLAG].equals("Y")) {
            ivFlag.selectValue(true);
            oiVo.getVaDataFields().setDataField(ivFlag);
        } else if (oiFields[I_OI_IV_FLAG].equals("N")) {
            ivFlag.selectValue(false);
            oiVo.getVaDataFields().setDataField(ivFlag);
        } else {
            throw new MigrationException(FOR_OI + oiVo.getOiName() + ", " + BOOLEAN_ERROR_MESSAGE,
                                         Integer.toString(fileRowNumber), oiFields[I_OI_NAME], OI_FIELD_NAMES[I_OI_IV_FLAG],
                                         oiFields[I_OI_IV_FLAG]);
        }

        //DaysOrDoseLimit field (optional)
        //store string in data field, single selection list
        if (oiFields[I_DOD_LIMIT].length() > 0) {
            DataField<String> dLimit = vadfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);
            dLimit.selectValue(oiFields[I_DOD_LIMIT]);
            oiVo.getVaDataFields().setDataField(dLimit);
        }

        return getNextOi3(oiVo, oiFields, vadfs);
    }

    /**
     * getNextOi3
     * subclass method to get next OI from file, return OrderableItemVo or null if EOF
     * input parameter oiVo should contain a blank template with default data fields
     * @param oiVo OrderableItemVo
     * @param oiFields oiFields
     * @param vadfs vadfs
     * @return OrderableItemVo
     * @throws MigrationException 
     */
    private OrderableItemVo getNextOi3(OrderableItemVo oiVo, String[] oiFields, DataFields<DataField> vadfs)
        throws MigrationException {

        //Synonym field (optional)
        //use string list to set multitext data field
        if (oiFields[I_SYNONYM].length() > 0) {
            MultitextDataField<String> synonym = vadfs.getDataField(FieldKey.ORDERABLE_ITEM_SYNONYM);
            String[] synonymArray = oiFields[I_SYNONYM].split(FIELD_MULTIPLE_SEPARATOR_SPLIT);

            for (String selection : synonymArray) {
                synonym.addStringSelection(selection);
            }

            oiVo.getVaDataFields().setDataField(synonym);
        }

        //Drug Text field (optional)
        //use string list to create a DrugTextVo array
        if (StringUtils.isNotBlank(oiFields[I_DRUG_TEXT])) {
            String[] dTextArray = oiFields[I_DRUG_TEXT].split(FIELD_MULTIPLE_SEPARATOR_SPLIT);
            List<DrugTextVo> drugTexts = new ArrayList<DrugTextVo>();

            for (String selection : dTextArray) {
                DrugTextVo drugText = new DrugTextVo();
                drugText.setValue(selection);
                drugTexts.add(drugText);
            }

            oiVo.setNationalDrugTexts(drugTexts);
        }

        //RequestRejectReason field (optional)
        //compare string to enum values; if found set oiVo enum variable
        if (oiFields[I_REQ_REQ_REASON].length() > 0) {
            Boolean found = false;

            for (RequestRejectionReason reason : RequestRejectionReason.values()) {
                if (oiFields[I_REQ_REQ_REASON].equals(reason.toString())) {
                    oiVo.setRequestRejectionReason(reason);
                    found = true;
                }
            }

            if (!found) {
                throw new MigrationException(FOR_OI + oiVo.getOiName() + ", " + LIST_ERROR_MESSAGE,
                                             Integer.toString(fileRowNumber), oiFields[I_OI_NAME],
                                             OI_FIELD_NAMES[I_REQ_REQ_REASON], oiFields[I_REQ_REQ_REASON]);
            }
        }

        //RejectReasonText field (optional)
        //use string to set oiVo string variable

        if (oiFields[I_REQ_REQ_TEXT].length() > 0) {
            oiVo.setRejectionReasonText(oiFields[I_REQ_REQ_TEXT]);
        }

        //PatientInstructions field (optional)
        //use string to set string data field
        if (oiFields[I_PAT_INSTR].length() > 0) {
            DataField<String> patientInstructions = vadfs.getDataField(FieldKey.PATIENT_INSTRUCTIONS);
            patientInstructions.selectValue(oiFields[I_PAT_INSTR]);
            oiVo.getVaDataFields().setDataField(patientInstructions);
        }

        //OtherLanguageInstructions field (optional)
        //use string to set string data field
        if (oiFields[I_OTHER_INSTR].length() > 0) {
            DataField<String> otherInstructions = vadfs.getDataField(FieldKey.OTHER_LANGUAGE_INSTRUCTIONS);
            otherInstructions.selectValue(oiFields[I_OTHER_INSTR]);
            oiVo.getVaDataFields().setDataField(otherInstructions);
        }

        //StandardMedRoute field (optional)
        //use string to set string variable
        if (oiFields[I_STD_MED_ROUTE].length() > 0) {
            StandardMedRouteVo stdVo = new StandardMedRouteVo();
            stdVo.setValue(oiFields[I_STD_MED_ROUTE]);
            oiVo.setStandardMedRoute(stdVo);
        }

        return oiVo;

    } //end getNextOi

    /**
     * openForExport
     * @param filename filename
     * @throws MigrationException 
     */
    public void openForExport(String filename) throws MigrationException {

        this.openAndWriteHeader(filename, OI_FIELD_NAMES);

    }

    /**
     * subclass method to add next OI row to file from data stored in ndcVo
     * @param oiVo OrderableItemVo
     * @throws MigrationException MigrationException
     */
    public void putNextOi(OrderableItemVo oiVo) throws MigrationException {

        String[] oiFieldArray = new String[OI_FIELD_NAMES.length];
        Arrays.fill(oiFieldArray, "");
        DataFields<DataField> vadfs = oiVo.getVaDataFields();
        oiFieldArray[I_OI_NAME] = oiVo.getOiName();

        //OiVistaName field (required)
        oiFieldArray[I_VISTA_OI_NAME] = oiVo.getVistaOiName();

        for (Category category : oiVo.getCategories()) {
            if (oiFieldArray[I_PROD_TYPE].length() > 0) {
                oiFieldArray[I_PROD_TYPE] = oiFieldArray[I_PROD_TYPE].concat(FIELD_MULTIPLE_SEPARATOR);
            }

            oiFieldArray[I_PROD_TYPE] = oiFieldArray[I_PROD_TYPE].concat(category.toString());
        }

        //DosageFormName field (required)
        //only uses name
        oiFieldArray[I_DOSE_FORM] = oiVo.getDosageForm().getDosageFormName();

        //InactivationDate field (optional)
        if (oiVo.getInactivationDate() != null) {
            oiFieldArray[I_INACT_DATE] = df.format(oiVo.getInactivationDate());
        }

        //ProposedInactivationDate field (optional)
        DataField<Date> proposedInactivationDate = vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);

        if (proposedInactivationDate.getValue() != null) {
            oiFieldArray[I_PROP_INACT_DATE] = df.format(proposedInactivationDate.getValue());
        }

        //LifetimeCumulDosage field (defaults to N)
        DataField<Boolean> lifetime = vadfs.getDataField(FieldKey.LIFETIME_CUMULATIVE_DOSAGE);

        if (lifetime.getValue()) {
            oiFieldArray[I_LIFE_DOSE] = "Y";
        } else {
            oiFieldArray[I_LIFE_DOSE] = "N";
        }

        //NationalFormularyIndicator field (defaults to N)
        if (oiVo.getNationalFormularyIndicator()) {
            oiFieldArray[I_NAT_FORM_IND] = "Y";
        } else {
            oiFieldArray[I_NAT_FORM_IND] = "N";
        }

        //NonVaMed field (defaults to Y)
        if (oiVo.getNonVaMed()) {
            oiFieldArray[I_NON_VA_MED] = "Y";
        } else {
            oiFieldArray[I_NON_VA_MED] = "N";
        }

        //OiIvFlag field (defaults to N)
        DataField<Boolean> ivFlag = vadfs.getDataField(FieldKey.OI_IV_FLAG);

        if (ivFlag.getValue()) {
            oiFieldArray[I_OI_IV_FLAG] = "Y";
        } else {
            oiFieldArray[I_OI_IV_FLAG] = "N";
        }

        //DaysOrDoseLimit field (optional)
        DataField<String> dLimit = vadfs.getDataField(FieldKey.DAY_ND_OR_DOSE_NL_LIMIT);

        if (dLimit.getValue() != null) {
            oiFieldArray[I_DOD_LIMIT] = dLimit.getValue();
        }

        putNextOi2(oiVo, oiFieldArray, vadfs);

    }

    /**
     * subclass method to add next OI row to file from data stored in ndcVo
     * @param oiVo OrderableItemVo
     * @param oiFieldArray oiFieldArray
     * @param vadfs VA datafields
     * @throws MigrationException MigrationException
     */
    private void putNextOi2(OrderableItemVo oiVo, String[] oiFieldArray, DataFields<DataField> vadfs)
        throws MigrationException {

        //Synonym multiple field (optional)
        MultitextDataField<String> synonym = vadfs.getDataField(FieldKey.ORDERABLE_ITEM_SYNONYM);

        if (synonym.getValue() != null && synonym.getValue().size() > 0) {
            List<String> synonymArray = synonym.getValue();

            for (String selection : synonymArray) {
                if (oiFieldArray[I_SYNONYM].length() > 0) {
                    oiFieldArray[I_SYNONYM] = oiFieldArray[I_SYNONYM].concat(FIELD_MULTIPLE_SEPARATOR);
                }

                oiFieldArray[I_SYNONYM] = oiFieldArray[I_SYNONYM].concat(selection);
            }
        }

        //Drug Text multiple field (optional)
        if (oiVo.getNationalDrugTexts() != null && oiVo.getNationalDrugTexts().size() > 0) {
            oiFieldArray[I_DRUG_TEXT] = "";
            List<DrugTextVo> dTextArray = oiVo.getNationalDrugTexts();

            for (DrugTextVo drugText : dTextArray) {
                if (oiFieldArray[I_DRUG_TEXT].length() > 0) {
                    oiFieldArray[I_DRUG_TEXT] = oiFieldArray[I_DRUG_TEXT].concat(FIELD_MULTIPLE_SEPARATOR);
                }

                oiFieldArray[I_DRUG_TEXT] = oiFieldArray[I_DRUG_TEXT].concat(drugText.getValue());
            }
        }

        //RequestRejectReason field (optional)
        if (oiVo.getRequestRejectionReason() != null && oiVo.getRequestRejectionReason().isItemExists()) {
            oiFieldArray[I_REQ_REQ_REASON] = oiVo.getRequestRejectionReason().toString();
        }

        //RejectReasonText field (optional)
        if (oiVo.getRejectionReasonText() != null && oiVo.getRejectionReasonText().length() > 0) {
            oiFieldArray[I_REQ_REQ_TEXT] = oiVo.getRejectionReasonText();
        }

        //PatientInstructions field (optional) 
        //use string to set string data field
        DataField<String> patInst = vadfs.getDataField(FieldKey.PATIENT_INSTRUCTIONS);

        if (patInst.getValue() != null && patInst.getValue().length() > 0) {
            oiFieldArray[I_PAT_INSTR] = patInst.getValue();
        }

        //OtherLanguageInstructions field 
        //use string to set string data field
        DataField<String> othInst = vadfs.getDataField(FieldKey.OTHER_LANGUAGE_INSTRUCTIONS);

        if (othInst.getValue() != null && othInst.getValue().length() > 0) {
            oiFieldArray[I_OTHER_INSTR] = othInst.getValue();
        }

        //StandardMedRoute field (optional)
        if (oiVo.getStandardMedRoute() != null) {
            oiFieldArray[I_STD_MED_ROUTE] = oiVo.getStandardMedRoute().getValue();
        }

        // write row to oi file
        this.putNextRow(oiFieldArray);

    } //end putNextOi

}
