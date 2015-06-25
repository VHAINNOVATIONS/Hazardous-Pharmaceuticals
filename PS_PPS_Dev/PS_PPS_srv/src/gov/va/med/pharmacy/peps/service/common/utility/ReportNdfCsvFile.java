/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import gov.va.med.pharmacy.peps.common.vo.ReportCaptureNdfVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;


/**
 * Class to creates and formats NDF CSV File for report functionality.
 *
 */
public class ReportNdfCsvFile extends CsvFile {

//    private static final Logger LOG = Logger.getLogger(ReportNdfCsvFile.class);
    private static final String PATH = "./tmp/";
    private static final String FILENAME = ReportType.NDC_LIST_PRINT_TEMPLATE.getView() + ".csv";
    private static final String NON_DIGIT = "\\D";

    private static final String NDC_1 = "NDC_1";
    private static final String NDC_2 = "NDC_2";
    private static final String NDC_3 = "NDC_3";
    private static final String NDF_NDC = "NDF_NDC";
    private static final String UPN = "UPN";
    private static final String I_DATE_NDC = "I_DATE_NDC";
    private static final String TRADE = "TRADE";
    private static final String VA_PRODUCT = "VA_PRODUCT";
    private static final String I_DATE_VAP = "I_DATE_VAP";
    private static final String PRODUCT_NU = "PRODUCT_NU";
    private static final String FEEDER = "FEEDER";
    private static final String GENERIC = "GENERIC";
    private static final String PKG_SZ = "PKG_SZ";
    private static final String PKG_TYPE = "PKG_TYPE";
    private static final String VA_CLASS = "VA_CLASS";
    private static final String MANUFAC = "MANUFAC";
    private static final String STANDARD_MED_ROUTE = "STANDARD_MED_ROUTE";
    private static final String STRENGTH = "STRENGTH";
    private static final String UNITS = "UNITS";
    private static final String DOSE_FORM = "DOSE_FORM";
    private static final String NF_NAME = "NF_NAME";
    private static final String CSFS = "CSFS";
    private static final String RX_OTC = "RX_OTC";
    private static final String NF_INDICAT = "NF_INDICAT";
    private static final String VA_PRN = "VA_PRN";
    private static final String DISP_UNT = "DISP_UNT";
    private static final String ID = "ID";
    private static final String MARK = "MARK";

    private static final String[] NDF_FIELD_NAMES = {
        NDC_1, NDC_2, NDC_3, NDF_NDC, UPN, I_DATE_NDC, TRADE, VA_PRODUCT, I_DATE_VAP, PRODUCT_NU,
        FEEDER, GENERIC, PKG_SZ, PKG_TYPE, VA_CLASS, MANUFAC, STANDARD_MED_ROUTE, STRENGTH, UNITS,
        DOSE_FORM, NF_NAME, CSFS, RX_OTC, NF_INDICAT, VA_PRN, DISP_UNT, ID, MARK };

    private static final int I_NDC_1 = ArrayUtils.indexOf(NDF_FIELD_NAMES, NDC_1);
    private static final int I_NDC_2 = ArrayUtils.indexOf(NDF_FIELD_NAMES, NDC_2);
    private static final int I_NDC_3 = ArrayUtils.indexOf(NDF_FIELD_NAMES, NDC_3);
    private static final int I_NDF_NDC = ArrayUtils.indexOf(NDF_FIELD_NAMES, NDF_NDC);
    private static final int I_UPN = ArrayUtils.indexOf(NDF_FIELD_NAMES, UPN);
    private static final int I_I_DATE_NDC = ArrayUtils.indexOf(NDF_FIELD_NAMES, I_DATE_NDC);
    private static final int I_TRADE = ArrayUtils.indexOf(NDF_FIELD_NAMES, TRADE);
    private static final int I_VA_PRODUCT = ArrayUtils.indexOf(NDF_FIELD_NAMES, VA_PRODUCT);
    private static final int I_I_DATE_VAP = ArrayUtils.indexOf(NDF_FIELD_NAMES, I_DATE_VAP);
    private static final int I_PRODUCT_NU = ArrayUtils.indexOf(NDF_FIELD_NAMES, PRODUCT_NU);
    private static final int I_FEEDER = ArrayUtils.indexOf(NDF_FIELD_NAMES, FEEDER);
    private static final int I_GENERIC = ArrayUtils.indexOf(NDF_FIELD_NAMES, GENERIC);
    private static final int I_PKG_SZ = ArrayUtils.indexOf(NDF_FIELD_NAMES, PKG_SZ);
    private static final int I_PKG_TYPE = ArrayUtils.indexOf(NDF_FIELD_NAMES, PKG_TYPE);
    private static final int I_VA_CLASS = ArrayUtils.indexOf(NDF_FIELD_NAMES, VA_CLASS);
    private static final int I_MANUFAC = ArrayUtils.indexOf(NDF_FIELD_NAMES, MANUFAC);
    private static final int I_STANDARD_MED_ROUTE = ArrayUtils.indexOf(NDF_FIELD_NAMES, STANDARD_MED_ROUTE);
    private static final int I_STRENGTH = ArrayUtils.indexOf(NDF_FIELD_NAMES, STRENGTH);
    private static final int I_UNITS = ArrayUtils.indexOf(NDF_FIELD_NAMES, UNITS);
    private static final int I_DOSE_FORM = ArrayUtils.indexOf(NDF_FIELD_NAMES, DOSE_FORM);
    private static final int I_NF_NAME = ArrayUtils.indexOf(NDF_FIELD_NAMES, NF_NAME);
    private static final int I_CSFS = ArrayUtils.indexOf(NDF_FIELD_NAMES, CSFS);
    private static final int I_RX_OTC = ArrayUtils.indexOf(NDF_FIELD_NAMES, RX_OTC);
    private static final int I_NF_INDICAT = ArrayUtils.indexOf(NDF_FIELD_NAMES, NF_INDICAT);
    private static final int I_VA_PRN = ArrayUtils.indexOf(NDF_FIELD_NAMES, VA_PRN);
    private static final int I_DISP_UNT = ArrayUtils.indexOf(NDF_FIELD_NAMES, DISP_UNT);
    private static final int I_ID = ArrayUtils.indexOf(NDF_FIELD_NAMES, ID);
    private static final int I_MARK = ArrayUtils.indexOf(NDF_FIELD_NAMES, MARK);

    /**
     * Super
     */
    public ReportNdfCsvFile() {
        super();
    }

    /**
     * Creates file and adds field headers
     * @throws Exception Exception
     */
    public void createFile() throws Exception {

        // open file and create string array with column headings
        this.openAndWriteHeader(PATH + FILENAME, NDF_FIELD_NAMES);
    }

    /**
     * Adds new rows of data to file
     * @param rtList List of items to process
     * @throws Exception Exception
     */
    public void printNDC(List<ReportCaptureNdfVo> rtList) throws Exception {
        String[] ndfFieldArray = new String[NDF_FIELD_NAMES.length];
        Arrays.fill(ndfFieldArray, "");

        for (ReportCaptureNdfVo vo : rtList) {
            Arrays.fill(ndfFieldArray, "");

            if (vo.getNdc2to6() != null) {
                ndfFieldArray[I_NDC_1] = vo.getNdc2to6();
            }

            if (vo.getNdc7to10() != null) {
                ndfFieldArray[I_NDC_2] = vo.getNdc7to10();
            }

            if (vo.getNdc11to12() != null) {
                ndfFieldArray[I_NDC_3] = vo.getNdc11to12();
            }

            // NDF_NDC
            if (vo.getNdc() != null) {
                ndfFieldArray[I_NDF_NDC] = vo.getNdc().replaceAll(NON_DIGIT, "");
            }

            // UPN
            if (vo.getUpn() != null) {
                ndfFieldArray[I_UPN] = vo.getUpn();
            }

            // I_DATE_NDC
            if (vo.getNdcInactivationDate() != null) {
                ndfFieldArray[I_I_DATE_NDC] = vo.getNdcInactivationDate().toString();
            }

            // TRADE_NAME
            if (vo.getTradeName() != null) {
                ndfFieldArray[I_TRADE] = vo.getTradeName();
            }

            // VA_PRODUCT
            if (vo.getVaProductName() != null) {
                ndfFieldArray[I_VA_PRODUCT] = vo.getVaProductName();
            }

            // I_DATE_VAP
            if (vo.getProductInactivationDate() != null) {
                ndfFieldArray[I_I_DATE_VAP] = vo.getProductInactivationDate().toString();
            }

            // PRODUCT_NU
            if (vo.getProductNumber() != null) {
                ndfFieldArray[I_PRODUCT_NU] = vo.getProductNumber();
            }

            // FEEDER (<DSS_FEEDER_KEY>0<NDC>)
            if (vo.getDssFeederKey() != null && vo.getNdc() != null) {
                ndfFieldArray[I_FEEDER] = vo.getDssFeederKey() + "0" + ndfFieldArray[I_NDF_NDC];

//                ndfFieldArray[I_FEEDER] = ndfFieldArray[I_FEEDER].concat("0");
//                ndfFieldArray[I_FEEDER] = ndfFieldArray[I_FEEDER].concat(vo.getNdc().replaceAll(NON_DIGIT, ""));
            }

            // GENERIC
            if (vo.getVaGenericName() != null) {
                ndfFieldArray[I_GENERIC] = vo.getVaGenericName();
            }

            // PKG_SZ
            if (vo.getPackageSize() != null) {
                ndfFieldArray[I_PKG_SZ] = vo.getPackageSize().toString();
            }

            // PKG_TYPE
            if (vo.getPackageType() != null) {
                ndfFieldArray[I_PKG_TYPE] = vo.getPackageType();
            }

            // VA_CLASS
            if (vo.getVaDrugClassClassification() != null) {
                ndfFieldArray[I_VA_CLASS] = vo.getVaDrugClassClassification();
            }

            // MANUFAC
            if (vo.getManufacturer() != null) {
                ndfFieldArray[I_MANUFAC] = vo.getManufacturer();
            }

            // STANDARD_MED_ROUTE
            if (vo.getRouteOfAdministration() != null) {
                ndfFieldArray[I_STANDARD_MED_ROUTE] = vo.getRouteOfAdministration();
            }

            // STRENGTH
            if (vo.getStrength() != null) {
                ndfFieldArray[I_STRENGTH] = vo.getStrength();
            }

            // UNIT
            if (vo.getUnit() != null) {
                ndfFieldArray[I_UNITS] = vo.getUnit();
            }

            // DOSE_FORM
            if (vo.getDosageForm() != null) {
                ndfFieldArray[I_DOSE_FORM] = vo.getDosageForm();
            }

            // NF_NAME
            if (vo.getNationalFormularyName() != null) {
                ndfFieldArray[I_NF_NAME] = vo.getNationalFormularyName();
            }

            // CSFS
            if (vo.getCsFederalSchedule() != null) {
                ndfFieldArray[I_CSFS] = vo.getCsFederalSchedule();
            }

            // RX_OTC
            if (vo.getRxOtcIndicator() != null) {
                ndfFieldArray[I_RX_OTC] = vo.getRxOtcIndicator();
            }

            // NF_INDICAT
            if (vo.getNationalFormularyIndicator() != null) {
                ndfFieldArray[I_NF_INDICAT] = vo.getNationalFormularyIndicator();
            }

            // VA_PRN
            if (vo.getVaPrintName() != null) {
                ndfFieldArray[I_VA_PRN] = vo.getVaPrintName();
            }

            // DISP_UNT
            if (vo.getVaDispenseUnit() != null) {
                ndfFieldArray[I_DISP_UNT] = vo.getVaDispenseUnit();
            }

            // ID
            if (vo.getCmopId() != null) {
                ndfFieldArray[I_ID] = vo.getCmopId();
            }

            // MARK_CMOP
            if (vo.getMarkForCmop() != null) {
                ndfFieldArray[I_MARK] = vo.getMarkForCmop();
            }

            putNextRow(ndfFieldArray);
        }
    }
}
