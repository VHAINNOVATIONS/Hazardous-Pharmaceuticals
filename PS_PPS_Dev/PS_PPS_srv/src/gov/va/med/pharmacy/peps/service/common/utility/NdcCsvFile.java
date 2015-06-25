/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcSourceType;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.OtcRxVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;


/**
// The NdcCsvFile class extends the CsvFile class that contains common csv file
// code. This class contains code specific to the import and export of NDC files.
// Methods defined in this subclass and used by the Migration service are:
//   1. openForImport - open file and validate column header line
//   2. getNextNdc - get next NDC from file, returns NDC as a NdcVo object
//   3. openForExport - open export and write header line
//   4. putNextNdc - put next NDC to file, input is a NdcVo object
*/
public class NdcCsvFile extends CsvFile {

    /** NDC */
    public static final String NDC = "Ndc";

    /** VA_PRODUCT_NAME */
    public static final String VA_PRODUCT_NAME = "VaProductName";

    /** PRODUCT_VUID */
    public static final String PRODUCT_VUID = "ProductVuid";

    /** PRODUCT_GCN */
    public static final String PRODUCT_GCN = "ProductGcnSeqNo";

    /** INACTIVATION_DATE */
    public static final String INACTIVATION_DATE = "NdcItemInactivationDate";

    /** MANUFACTURER */
    public static final String MANUFACTURER = "Manufacturer";

    /** DUPOU */
    public static final String DUPOU = "NdcDispenseUnitPerOrderUnit";

    /** OTC_RX */
    public static final String OTC_RX = "OtcRxIndicator";

    /** PACAGKAGE_SIZE */
    public static final String PACAGKAGE_SIZE = "PackageSize";

    /** PACKAGE_TYPE */
    public static final String PACKAGE_TYPE = "PackageType";

    /** TRADENAME */
    public static final String TRADENAME = "TradeName";

    /** REFRIGERATION */
    public static final String REFRIGERATION = "Refrigeration";

    /** PROTECT_FROM_LIGHT */
    public static final String PROTECT_FROM_LIGHT = "ProtectFromLight";

    /** PROPOSED_INACTIVATION_DATE */
    public static final String PROPOSED_INACTIVATION_DATE = "ProposedInactivationDate";

    /** PREVIOUS_NDC */
    public static final String PREVIOUS_NDC = "PreviousNdc";

    /** PREVIOUS_UPC */
    public static final String PREVIOUS_UPC = "PreviousUpcUpn";

    /** ORDER_UNIT */
    public static final String ORDER_UNIT = "OrderUnit";

    /** SOURCE */
    public static final String SOURCE = "Source";

    /** PRODUCT_NUMBER */
    public static final String PRODUCT_NUMBER = "ProductNumber";

    /** INVALID_HEADER */
    public static final String INVALID_HEADER = "Column header on line 1 is invalid.";


    // column headings stored in first row of ndc csv file

    /** NDF_FIELD_NAMES */
    public static final String[] NDF_FIELD_NAMES = {
        NDC, VA_PRODUCT_NAME, PRODUCT_VUID, PRODUCT_GCN, INACTIVATION_DATE, MANUFACTURER, DUPOU, OTC_RX, PACAGKAGE_SIZE,
        PACKAGE_TYPE, TRADENAME, REFRIGERATION, PROTECT_FROM_LIGHT, PROPOSED_INACTIVATION_DATE, PREVIOUS_NDC, PREVIOUS_UPC,
        ORDER_UNIT, SOURCE, PRODUCT_NUMBER };

    // use these constants to index field arrays

    /** I_NDC_NUM */
    public static final int I_NDC_NUM = ArrayUtils.indexOf(NDF_FIELD_NAMES, NDC);

    /** I_PROD_NAME */
    public static final int I_PROD_NAME = ArrayUtils.indexOf(NDF_FIELD_NAMES, VA_PRODUCT_NAME);

    /** I_PROD_VUID */
    public static final int I_PROD_VUID = ArrayUtils.indexOf(NDF_FIELD_NAMES, PRODUCT_VUID);

    /** I_PROD_GCN */
    public static final int I_PROD_GCN = ArrayUtils.indexOf(NDF_FIELD_NAMES, PRODUCT_GCN);

    /** I_INACTIVATION_DATE */
    public static final int I_INACTIVATION_DATE = ArrayUtils.indexOf(NDF_FIELD_NAMES, INACTIVATION_DATE);

    /** I_MANUFACTURER */
    public static final int I_MANUFACTURER = ArrayUtils.indexOf(NDF_FIELD_NAMES, MANUFACTURER);

    /** I_ORDER_UNIT */
    public static final int I_ORDER_UNIT = ArrayUtils.indexOf(NDF_FIELD_NAMES, ORDER_UNIT);

    /** I_OTC_RX_IND */
    public static final int I_OTC_RX_IND = ArrayUtils.indexOf(NDF_FIELD_NAMES, OTC_RX);

    /** I_PACKAGE_SIZE */
    public static final int I_PACKAGE_SIZE = ArrayUtils.indexOf(NDF_FIELD_NAMES, PACAGKAGE_SIZE);

    /** I_PACKAGE_TYPE */
    public static final int I_PACKAGE_TYPE = ArrayUtils.indexOf(NDF_FIELD_NAMES, PACKAGE_TYPE);

    /** I_TRADE_NAME */
    public static final int I_TRADE_NAME = ArrayUtils.indexOf(NDF_FIELD_NAMES, TRADENAME);

    /** I_REFRIG */
    public static final int I_REFRIG = ArrayUtils.indexOf(NDF_FIELD_NAMES, REFRIGERATION);

    /** I_PROTECT */
    public static final int I_PROTECT = ArrayUtils.indexOf(NDF_FIELD_NAMES, PROTECT_FROM_LIGHT);

    /** I_PROP_INACT_DATE */
    public static final int I_PROP_INACT_DATE = ArrayUtils.indexOf(NDF_FIELD_NAMES, PROPOSED_INACTIVATION_DATE);

    /** I_PREV_NDC */
    public static final int I_PREV_NDC = ArrayUtils.indexOf(NDF_FIELD_NAMES, PREVIOUS_NDC);

    /** I_PREV_NDC_UPN */
    public static final int I_PREV_NDC_UPN = ArrayUtils.indexOf(NDF_FIELD_NAMES, PREVIOUS_UPC);

    /** I_DISP_UNIT */
    public static final int I_DISP_UNIT = ArrayUtils.indexOf(NDF_FIELD_NAMES, DUPOU);

    /** I_SOURCE */
    public static final int I_SOURCE = ArrayUtils.indexOf(NDF_FIELD_NAMES, SOURCE);

    /** I_PROD_NO */
    public static final int I_PROD_NO = ArrayUtils.indexOf(NDF_FIELD_NAMES, PRODUCT_NUMBER);

    /**
     * subclass method to open ndc file for import and validate the header row
     * @param filename filename
     * @throws MigrationException 
     */
    public void openForImport(String filename) throws MigrationException {

        // open file and create string array with column headings
        String[] ndcArray = this.openAndReadHeader(filename);

        // compare with a ndc valid header
        if (!(Arrays.equals(ndcArray, NDF_FIELD_NAMES))) {
            throw new MigrationException(INVALID_HEADER);
        }
    }

    /**
     * subclass method to open ndc file for import and validate the header row
     * @param pInputStream InputStream
     * @exception MigrationException 
     */
    public void openForImport(InputStream pInputStream) throws MigrationException {

        // open file and create string array with column headings
        String[] ndcArray = this.openAndReadHeader(pInputStream);

        // compare with a ndc valid header
        if (!(Arrays.equals(ndcArray, NDF_FIELD_NAMES))) {
            throw new MigrationException(INVALID_HEADER);
        }
    }

    /**
     * subclass method to get next ndc from file, return NdcVo or null if EOF
     * input parameter ndc should contain a blank template ndc with all data fields
     * 
     * @param ndc ndc   
     * @return NDCVO
     * @throws MigrationException 
     */
    public NdcVo getNextNdc(NdcVo ndc) throws MigrationException {

        //----------------------------------------
        // call superclass member to read next row
        //----------------------------------------
        String[] ndcFields = this.getNextRow(NDF_FIELD_NAMES.length);

        //if null, assume end of file

        if (ndcFields == null) {
            return null;
        }

        //verify row has correct number of data fields

        if (ndcFields.length < NDF_FIELD_NAMES.length) {
            throw new MigrationException(ROW_ERROR_MESSAGE, Integer.toString(fileRowNumber), "", "", "");
        }

        // -------------------------------------------------
        // put file fields into ndcVo object and data fields
        // convert fields if required but do not validate
        // service will call validators on returned NdcVo
        // --------------------------------------------------

        //initialize Vo data fields (not contained in file)
        ndc.setRequestItemStatus(RequestItemStatus.APPROVED);

        //Ndc field
        //store string in NdcVo variable.  Need to put in the dashes.  We may be getting some NDCS that are 11 digits
        // and some that are 12.  If we get 12 digits then remove the leading zero
        String strNDC = ndcFields[I_NDC_NUM];

        if (strNDC == null ||  strNDC.length() == 0 || strNDC.length() > PPSConstants.I12) {
            throw new MigrationException("Not a valid NDC length (12 digits)", Integer.toString(fileRowNumber),
                                         ndcFields[I_NDC_NUM], NDF_FIELD_NAMES[I_NDC_NUM], ndcFields[I_NDC_NUM]);
        }
        
        while (strNDC.length() < PPSConstants.I12) {
            strNDC = "0" + strNDC;
        }
        
        strNDC = strNDC.substring(1, PPSConstants.I6) + "-" 
            + strNDC.substring(PPSConstants.I6, PPSConstants.I10) + "-" 
            + strNDC.substring(PPSConstants.I10, PPSConstants.I12);


        ndc.setNdc(strNDC);

        //VaProductName, ProductVuid, ProductGcnSeqNo fields 
        //store strings in ProductVo fields, service will set link to product
        ProductVo product = new ProductVo();

        if (ndcFields[I_PROD_NAME].length() > 0) {
            product.setVaProductName(ndcFields[I_PROD_NAME]);
        }

        if (ndcFields[I_PROD_VUID].length() > 0) {
            product.setVuid(ndcFields[I_PROD_VUID]);
        }

        if (ndcFields[I_PROD_GCN].length() > 0) {
            product.setGcnSequenceNumber(ndcFields[I_PROD_GCN]);
        }

        ndc.setProduct(product);

        //NdcItemInactivationDate field
        //store date in NdcVo variable
        //also set ItemStatus variable: if date is empty, ndc is active
        //also set RequestItemStatus variable = Approved
        if (ndcFields[I_INACTIVATION_DATE].equals("") || ndcFields[I_INACTIVATION_DATE].equals(PPSConstants.NULL_DATE) 
                ||  ndcFields[I_INACTIVATION_DATE].equals("0")) {
            ndc.setInactivationDate(null);
            ndc.setItemStatus(ItemStatus.ACTIVE);
        } else {
            try {
                ndc.setItemStatus(ItemStatus.INACTIVE);
                Date inactiveDate = df.parse(ndcFields[I_INACTIVATION_DATE]);
                ndc.setInactivationDate(inactiveDate);
            } catch (ParseException ex) {
                throw new MigrationException(DATE_ERROR_MESSAGE, Integer.toString(fileRowNumber), ndcFields[I_NDC_NUM],
                    NDF_FIELD_NAMES[I_INACTIVATION_DATE], ndcFields[I_INACTIVATION_DATE]);
            }
        }

        //Manufacturer field
        //store string in NdcVo variable
        //only value is set, service will add manufacturer as domain entry
        ManufacturerVo manufacturer = new ManufacturerVo();

        if (ndcFields[I_MANUFACTURER].length() > 0) {
            manufacturer.setValue(ndcFields[I_MANUFACTURER]);
        } else {
            throw new MigrationException("Manufacturer has no values", Integer.toString(fileRowNumber), ndcFields[I_NDC_NUM],
                NDF_FIELD_NAMES[I_MANUFACTURER], ndcFields[I_MANUFACTURER]);
        }

        ndc.setManufacturer(manufacturer);

        // Order Unit is optional
        OrderUnitVo orderUnit = new OrderUnitVo();

        if (ndcFields[I_ORDER_UNIT].length() > 0) {

            // only abbrev is set, service completes vo as needed 
            orderUnit.setAbbrev(ndcFields[I_ORDER_UNIT]);
        }

        ndc.setOrderUnit(orderUnit);

        // OtcRxIndicator is optional
        OtcRxVo otcRx = new OtcRxVo();

        if (StringUtils.isEmpty(ndcFields[I_OTC_RX_IND])) {
            throw new MigrationException("OTC RX indicator must not be empty.", Integer.toString(fileRowNumber),
                ndcFields[I_OTC_RX_IND], NDF_FIELD_NAMES[I_OTC_RX_IND], ndcFields[I_OTC_RX_IND]);
        } else if (ndcFields[I_OTC_RX_IND].equals("R")) {
            otcRx.setValue(PPSConstants.PRESCRIPTION);
        } else if (ndcFields[I_OTC_RX_IND].equals("O")) {
            otcRx.setValue(PPSConstants.OVER_THE_COUNTER);
        } else {
            throw new MigrationException("OTC RX indicator is invalid. It must be O or R.", Integer.toString(fileRowNumber),
                ndcFields[I_OTC_RX_IND], NDF_FIELD_NAMES[I_OTC_RX_IND], ndcFields[I_OTC_RX_IND]);
        }

        ndc.setOtcRxIndicator(otcRx);

        return getNextNdc2(ndc, ndcFields);
    }

    /**
     * subclass method to get next ndc from file, return NdcVo or null if EOF
     * input parameter ndc should contain a blank template ndc with all data fields
     * 
     * @param ndc ndc
     * @param ndcFields ndcFields   
     * @return NDCVO
     * @throws MigrationException 
     */
    private NdcVo getNextNdc2(NdcVo ndc, String[] ndcFields) throws MigrationException {
        
        //PackageSize field - convert to double and store in NdcVo variable
        if (ndcFields[I_PACKAGE_SIZE].length() > 0) {
            try {
                Double packageSize = Double.parseDouble(ndcFields[I_PACKAGE_SIZE]);
                ndc.setPackageSize(packageSize);
            } catch (NumberFormatException ex) {
                throw new MigrationException("Package Size is not numeric.", Integer.toString(fileRowNumber),
                    ndcFields[I_NDC_NUM], NDF_FIELD_NAMES[I_PACKAGE_SIZE], ndcFields[I_PACKAGE_SIZE]);
            }
        }

        //PackageType field
        //store string in NdcVo.PackageTypeVo
        //only value is set, service will add package type as domain entry
        PackageTypeVo packageType = new PackageTypeVo();

        if (StringUtils.isBlank(ndcFields[I_PACKAGE_TYPE])) {
            throw new MigrationException("Package Type has no value.", Integer.toString(fileRowNumber), ndcFields[I_NDC_NUM],
                NDF_FIELD_NAMES[I_PACKAGE_TYPE], ndcFields[I_PACKAGE_TYPE]);
        } else {
            packageType.setValue(ndcFields[I_PACKAGE_TYPE]);
        }

        ndc.setPackageType(packageType);

        //TradeName field - store string in NdcVo field
        if (StringUtils.isNotBlank(ndcFields[I_TRADE_NAME])) {
            ndc.setTradeName(ndcFields[I_TRADE_NAME]);
        } else {
            throw new MigrationException("Trade Name has no value.", Integer.toString(fileRowNumber), ndcFields[I_NDC_NUM],
                NDF_FIELD_NAMES[I_TRADE_NAME], ndcFields[I_TRADE_NAME]);
        }

        // Set the VA DAta field values
        DataFields<DataField> ndcVadf = ndc.getVaDataFields();

        // Refrigeration field
        if (ndcFields[I_REFRIG].length() > 0) {
            if (ndcFields[I_REFRIG].equals("Y")) {
                ndcVadf.getDataField(FieldKey.REFRIGERATION).addStringSelection(PPSConstants.REFRIGERATE);
            } else if (ndcFields[I_REFRIG].equals("N")) {
                ndcVadf.getDataField(FieldKey.REFRIGERATION).addStringSelection(PPSConstants.DO_NOT_REFRIGERATE);
            }
        }

        //ProtectFromLight field (defaulted to N in blankNdcVo)
        if (ndcFields[I_PROTECT].length() > 0) {
            if (ndcFields[I_PROTECT].equals("Y")) {
                ndcVadf.getDataField(FieldKey.PROTECT_FROM_LIGHT).selectValue(true);
            } else if (ndcFields[I_PROTECT].equals("N")) {
                ndcVadf.getDataField(FieldKey.PROTECT_FROM_LIGHT).selectValue(false);
            }
        }

        //ProposedInactivationDate field
        if (!(ndcFields[I_PROP_INACT_DATE].equals("") || ndcFields[I_PROP_INACT_DATE].equals(PPSConstants.NULL_DATE))) {

            try {
                Date piDate = df.parse(ndcFields[I_PROP_INACT_DATE]);
                ndcVadf.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE).selectValue(piDate);
            } catch (ParseException ex) {
                throw new MigrationException(DATE_ERROR_MESSAGE, Integer.toString(fileRowNumber), ndcFields[I_NDC_NUM],
                                             NDF_FIELD_NAMES[I_PROP_INACT_DATE], ndcFields[I_PROP_INACT_DATE]);
            }
        }

        //PreviousNdc field is an optional field
        //CR will change this field to a single selection, assume no change to type
        if (ndcFields[I_PREV_NDC].length() > 0) {
            ndcVadf.getDataField(FieldKey.PREVIOUS_NDC).selectStringValue(ndcFields[I_PREV_NDC]);
        }

        //PreviousUpcUpn is an optional field
        //CR will change this field to a single selection, assume no change to type
        if (ndcFields[I_PREV_NDC_UPN].length() > 0) {
            ndcVadf.getDataField(FieldKey.PREVIOUS_UPC_UPN).selectStringValue(ndcFields[I_PREV_NDC_UPN]);
        }

        //ProductNumber field is optional
        if (ndcFields[I_PROD_NO].length() > 0) {
            ndcVadf.getDataField(FieldKey.PRODUCT_NUMBER).selectStringValue(ndcFields[I_PROD_NO]);
        }

        ndc.setVaDataFields(ndcVadf);

        //NdcDispenseUnitPerOrderUnit field is optional - convert to Double and store in NdcVo variable
        if (ndcFields[I_DISP_UNIT].length() > 0) {
            try {
                Double ndcDispUnitsPerOrdUnit = Double.parseDouble(ndcFields[I_DISP_UNIT]);

                if (ndcDispUnitsPerOrdUnit < 1.0) {
                    ndcDispUnitsPerOrdUnit = 1.0;
                }

                ndc.setNdcDispUnitsPerOrdUnit(ndcDispUnitsPerOrdUnit);
            } catch (NumberFormatException ex) {
                throw new MigrationException(NUMBER_ERROR_MESSAGE, Integer.toString(fileRowNumber), ndcFields[I_NDC_NUM],
                    NDF_FIELD_NAMES[I_DISP_UNIT], ndcFields[I_DISP_UNIT]);
            }
        }

        //Source field is optional  
        if (ndcFields[I_SOURCE].length() > 0) {
            if (ndcFields[I_SOURCE].equals("VA")) {
                ndc.setSource(NdcSourceType.VA);
            } else if (ndcFields[I_SOURCE].equals("COTS")) {
                ndc.setSource(NdcSourceType.COTS);
            } else if (ndcFields[I_SOURCE].equals("FDA")) {
                ndc.setSource(NdcSourceType.FDA);
            } else {
                throw new MigrationException(LIST_ERROR_MESSAGE, Integer.toString(fileRowNumber), ndcFields[I_NDC_NUM],
                    NDF_FIELD_NAMES[I_SOURCE], ndcFields[I_SOURCE]);
            }
        } else { // if null, then set it to VA if the product name exists, else COTS
            if (ndcFields[I_PROD_NAME].length() > 0) {
                ndc.setSource(NdcSourceType.VA);
            } else {
                ndc.setSource(NdcSourceType.COTS);
            }
        }

        return ndc;

    } //end getNextNdc method

    /**
     * subclass method to open a ndc file for export and write header row
     * @param filename fileName
     * @exception MigrationException MigrationException
     */
    public void openForExport(String filename) throws MigrationException {

        this.openAndWriteHeader(filename, NDF_FIELD_NAMES);

    }

    /**
     * subclass method to add next ndc row to file from data stored in ndcVo
     * @param ndc NdcVo
     * @exception MigrationException MigrationException
     */
    public void putNextNdc(NdcVo ndc) throws MigrationException {
        String[] ndcFieldArray = new String[NDF_FIELD_NAMES.length];
        Arrays.fill(ndcFieldArray, "");

        //get data fields
        DataFields<DataField> vadfs = ndc.getVaDataFields();

        //Ndc name field 
        if (ndc.getNdc() != null) {
            ndcFieldArray[I_NDC_NUM] = ndc.getNdc().replace("-", "");
        }

        //VaProductName, field 
        if (ndc.getProduct().getVaProductName() != null) {
            ndcFieldArray[I_PROD_NAME] = ndc.getProduct().getVaProductName();
        }

        //ProductVuid field 
        if (ndc.getProduct().getVuid() != null) {
            ndcFieldArray[I_PROD_VUID] = ndc.getProduct().getVuid();
        }

        //ProductGcnSeqNo field 
        if (ndc.getProduct().getGcnSequenceNumber() != null) {
            ndcFieldArray[I_PROD_GCN] = ndc.getProduct().getGcnSequenceNumber();
        }

        //NdcItemInactivationDate field
        if (ndc.getInactivationDate() != null) {
            ndcFieldArray[I_INACTIVATION_DATE] = df.format(ndc.getInactivationDate());
        }

        //Manufacturer field
        if (ndc.getManufacturer().getValue() != null) {
            ndcFieldArray[I_MANUFACTURER] = ndc.getManufacturer().getValue();
        }

        //OrderUnit field
        if (ndc.getOrderUnit().getAbbrev() != null) {
            ndcFieldArray[I_ORDER_UNIT] = ndc.getOrderUnit().getAbbrev();
        }

        //OtcRxIndicator field
        if (ndc.getOtcRxIndicator() != null && ndc.getOtcRxIndicator().getValue() != null) {
            if (ndc.getOtcRxIndicator().getValue().equals(PPSConstants.OVER_THE_COUNTER)) {
                ndcFieldArray[I_OTC_RX_IND] = "O";
            } else {
                ndcFieldArray[I_OTC_RX_IND] = "R";
            }
        }

        //PackageSize field
        if (ndc.getPackageSize().toString() != null) {
            ndcFieldArray[I_PACKAGE_SIZE] = ndc.getPackageSize().toString();
        }

        //PackageType field
        if (ndc.getPackageType().getValue() != null) {
            ndcFieldArray[I_PACKAGE_TYPE] = ndc.getPackageType().getValue();
        }

        //TradeName field
        if (ndc.getTradeName() != null) {
            ndcFieldArray[I_TRADE_NAME] = ndc.getTradeName();
        }

        //Refrigeration field
        ListDataField<String> refrigerate = vadfs.getDataField(FieldKey.REFRIGERATION);

        if (refrigerate.getValue() != null) {
            if (refrigerate.contains(PPSConstants.REFRIGERATE)) {
                ndcFieldArray[I_REFRIG] = "Y";
            }

            if (refrigerate.contains(PPSConstants.DO_NOT_REFRIGERATE)) {
                ndcFieldArray[I_REFRIG] = "N";
            }
        }

        //ProtectFromLight field
        DataField<Boolean> protectFromLight = vadfs.getDataField(FieldKey.PROTECT_FROM_LIGHT);

        if (protectFromLight.getValue() != null) {
            if (protectFromLight.getValue()) {
                ndcFieldArray[I_PROTECT] = "Y";
            } else {
                ndcFieldArray[I_PROTECT] = "N";
            }
        }

        //ProposedInactivationDate field
        DataField<Date> proposedInactivationDate = vadfs.getDataField(FieldKey.PROPOSED_INACTIVATION_DATE);

        if (proposedInactivationDate.getValue() != null) {
            ndcFieldArray[I_PROP_INACT_DATE] = df.format(proposedInactivationDate.getValue());
        }

        //PreviousNdc field, single selection
        DataField<String> previousNdc = vadfs.getDataField(FieldKey.PREVIOUS_NDC);

        if (previousNdc.getValue() != null) {

            ndcFieldArray[I_PREV_NDC] = previousNdc.getValue();

        }

        //PreviousUpcUpn field, single selection
        DataField<String> previousUpcUpn = vadfs.getDataField(FieldKey.PREVIOUS_UPC_UPN);

        if (previousUpcUpn.getValue() != null) {

            ndcFieldArray[I_PREV_NDC_UPN] = previousUpcUpn.getValue();

        }

        //NdcDispenseUnitPerOrderUnit field
        if (ndc.getNdcDispUnitsPerOrdUnit() != null) {
            ndcFieldArray[I_DISP_UNIT] = ndc.getNdcDispUnitsPerOrdUnit().toString();
        }

        //Source field
        if (ndc.getSource() != null) {
            ndcFieldArray[I_SOURCE] = ndc.getSource().toString();
        }

        //ProductNumber field
        DataField<String> productNumber = vadfs.getDataField(FieldKey.PRODUCT_NUMBER);

        if (productNumber.getValue() != null) {
            ndcFieldArray[I_PROD_NO] = productNumber.getValue();
        }

        // write row to ndc file
        this.putNextRow(ndcFieldArray);

    } //end putNextNdc method

} //end NdcCsvFile class
