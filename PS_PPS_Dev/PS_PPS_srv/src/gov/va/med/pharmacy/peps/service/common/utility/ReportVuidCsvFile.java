/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.common.vo.ReportVuidApprovalVo;
import gov.va.med.pharmacy.peps.common.vo.ReportVuidVo;
import gov.va.med.pharmacy.peps.service.common.session.ReportsService;


/**
 * ReportVuidCsvFile
 */
public class ReportVuidCsvFile extends CsvFile {

    private static final Logger LOG = Logger.getLogger(ReportVuidCsvFile.class);
    private static final String PATH = "./tmp/";
    private static final String FILENAME = ReportType.ITEM_AUDIT_HISTORY_PRINT_TEMPLATE.getView() + ".csv";
    private static final String ERROR = "VUID Csv Report IOException: ";

    //Section Headers
    private static final String PRODUCTS = "NEW PRODUCTS";
    private static final String PRODUCTS_MODIFIED = "PRODUCTS INACTIVATED OR RE-ACTIVATED";
    private static final String INGREDIENTS = "NEW INGREDIENTS";
    private static final String INGREDIENTS_MODIFIED = "INGREDIENTS INACTIVATED OR RE-ACTIVATED";
    private static final String GENERIC_NAMES = "NEW GENERIC NAMES";
    private static final String GENERIC_NAMES_MODIFIED = "GENERIC NAMES INACTIVATED OR RE-ACTIVATED";
    private static final String DRUG_CLASS = "NEW VA DRUG CLASS";
    
    //Field Headers
    private static final String IEN = "IEN";
    private static final String VA_PRODUCT_NAME = "VA PRODUCT NAME";
    private static final String VUID = "VUID";
    private static final String INACTIVATION_DATE = "INACTIVATION DATE";
    private static final String VA_INGREDIENT_NAME = "VA INGREDIENT NAME";
    private static final String VA_GENERIC_NAME = "VA GENERIC NAME";
    
    private ReportsService reportsService;

    /**
     * ReportVuidCsvFile
     */
    public ReportVuidCsvFile() {
        super();

    }

    /**
     * ReportVuidCsvFile
     * @param reportsService reportsService
     */
    public ReportVuidCsvFile(ReportsService reportsService) {
        super();

        this.reportsService = reportsService;
    }

    /**
     * Creates the VUID Report CSV File
     * @param start Start Date
     */
    public void createFile(Date start) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + FILENAME));

            // VUID Products
            addHeader(writer, PRODUCTS);
            addProductData(writer, PRODUCTS, start);
            writer.newLine();
            addHeader(writer, PRODUCTS_MODIFIED);
            addProductData(writer, PRODUCTS_MODIFIED, start);
            writer.newLine();

            // VUID Ingredients
            addHeader(writer, INGREDIENTS);
            addIngredientData(writer, INGREDIENTS, start);
            writer.newLine();
            addHeader(writer, INGREDIENTS_MODIFIED);
            addIngredientData(writer, INGREDIENTS_MODIFIED, start);
            writer.newLine();

            // VUID Generic Names
            addHeader(writer, GENERIC_NAMES);
            addGenericData(writer, GENERIC_NAMES, start);
            writer.newLine();
            addHeader(writer, GENERIC_NAMES_MODIFIED);
            addGenericData(writer, GENERIC_NAMES_MODIFIED, start);
            writer.newLine();

            // VUID Drug Class
            addHeader(writer, DRUG_CLASS);
            addDrugClassData(writer, DRUG_CLASS, start);

            writer.flush();
            writer.close();

        } catch (IOException e) {
            LOG.info("Create File IOException: " + e);
        }
    }

    /**
     * Adds the header for each section of the report
     * @param writer BufferedWriter
     * @param section Section to write
     */
    private void addHeader(BufferedWriter writer, String section) {
        try {
            LOG.info("Writing header: " + section);
            writer.append(section);
            writer.newLine();

            if (section == PRODUCTS) {
                writer.append(IEN + ROW_FIELD_SEPARATOR);
                writer.append(VA_PRODUCT_NAME + ROW_FIELD_SEPARATOR);
                writer.append(VUID);
                writer.newLine();
            }

            if (section == PRODUCTS_MODIFIED) {
                writer.append(IEN + ROW_FIELD_SEPARATOR);
                writer.append(VA_PRODUCT_NAME + ROW_FIELD_SEPARATOR);
                writer.append(VUID + ROW_FIELD_SEPARATOR);
                writer.append(INACTIVATION_DATE + ROW_FIELD_SEPARATOR);
                writer.newLine();
            }

            if (section == INGREDIENTS) {
                writer.append(IEN + ROW_FIELD_SEPARATOR);
                writer.append(VA_INGREDIENT_NAME + ROW_FIELD_SEPARATOR);
                writer.append(VUID + ROW_FIELD_SEPARATOR);
                writer.newLine();
            }

            if (section == INGREDIENTS_MODIFIED) {
                writer.append(IEN + ROW_FIELD_SEPARATOR);
                writer.append(VA_INGREDIENT_NAME + ROW_FIELD_SEPARATOR);
                writer.append(VUID + ROW_FIELD_SEPARATOR);
                writer.append(INACTIVATION_DATE + ROW_FIELD_SEPARATOR);
                writer.newLine();
            }

            if (section == GENERIC_NAMES) {
                writer.append(IEN + ROW_FIELD_SEPARATOR);
                writer.append(VA_GENERIC_NAME + ROW_FIELD_SEPARATOR);
                writer.append(VUID + ROW_FIELD_SEPARATOR);
                writer.newLine();
            }

            if (section == GENERIC_NAMES_MODIFIED) {
                writer.append(IEN + ROW_FIELD_SEPARATOR);
                writer.append(VA_GENERIC_NAME + ROW_FIELD_SEPARATOR);
                writer.append(VUID + ROW_FIELD_SEPARATOR);
                writer.append(INACTIVATION_DATE + ROW_FIELD_SEPARATOR);
                writer.newLine();
            }

            if (section == DRUG_CLASS) {
                writer.append(IEN + ROW_FIELD_SEPARATOR);
                writer.append("VA DRUG CLASS NAME" + ROW_FIELD_SEPARATOR);
                writer.append(VUID + ROW_FIELD_SEPARATOR);
                writer.newLine();
            }

        } catch (IOException e) {
            LOG.info(ERROR + e);
        }

    }

    /**
     * Writes report data
     * @param writer BufferedWriter
     * @param section Section to write
     * @param pstartDate Start Date
     */
    private void addProductData(BufferedWriter writer, String section, Date pstartDate) {
        ReportVuidApprovalVo list = reportsService.getReportDomainCapability().getVuidApprovalReportProducts(pstartDate);

        try {
            LOG.info("Writing Product Data: " + section);

            if (section == PRODUCTS && list != null) {
                for (ReportVuidVo vo : list.getNewProductList()) {
                    writer.append(vo.getIen() + ROW_FIELD_SEPARATOR);
                    writer.append(vo.getVaProductName() + ROW_FIELD_SEPARATOR);
                    writer.append(vo.getVuid());
                    writer.newLine();
                }
            }

            ReportVuidApprovalVo modList = reportsService.getReportDomainCapability().getVuidModifiedReportProducts(pstartDate);

            if (section == PRODUCTS_MODIFIED) {
                for (ReportVuidVo vo : modList.getModifedProductList()) {
                    writer.append(vo.getIen() + ROW_FIELD_SEPARATOR);
                    writer.append(vo.getName() + ROW_FIELD_SEPARATOR);
                    writer.append(vo.getVuid() + ROW_FIELD_SEPARATOR);
                    writer.append(checkEmpty(vo.getInactivationDate()));
                    writer.newLine();
                }
            }
            
            // log any exceptions
        } catch (IOException e) {
            LOG.info(ERROR + e);
        }
    }

    /**
     * @param writer Buffered Writer
     * @param section Section to write
     * @param pstartDate Start Date
     */
    private void addIngredientData(BufferedWriter writer, String section, Date pstartDate) {
        ReportVuidApprovalVo list = reportsService.getReportDomainCapability().getVuidApprovalReportIngredient(pstartDate);

        try {
            LOG.info("Writing Ingredient Data: " + section);

            if (section == INGREDIENTS) {
                for (ReportVuidVo vo : list.getNewIngredientList()) {
                    writer.append(vo.getIen() + ROW_FIELD_SEPARATOR);
                    writer.append(vo.getName() + ROW_FIELD_SEPARATOR);
                    writer.append(vo.getVuid() + ROW_FIELD_SEPARATOR);
                    writer.newLine();
                }
            }

            ReportVuidApprovalVo modList =
                reportsService.getReportDomainCapability().getVuidModifiedReportIngredient(pstartDate);

            if (section == INGREDIENTS_MODIFIED) {
                for (ReportVuidVo vo : modList.getModifiedIngredientList()) {
                    writer.append(vo.getIen() + ROW_FIELD_SEPARATOR);
                    writer.append(vo.getName() + ROW_FIELD_SEPARATOR);
                    writer.append(vo.getVuid() + ROW_FIELD_SEPARATOR);
                    writer.append(checkEmpty(vo.getInactivationDate()));
                    writer.newLine();
                }
            }

            // Log the exception at the info level.
        } catch (IOException e) {
            LOG.info(ERROR + e);
        }
    }

    /**
     * @param writer Buffered Writer
     * @param section Section to write
     * @param pstartDate Start Date
     */
    private void addGenericData(BufferedWriter writer, String section, Date pstartDate) {
        ReportVuidApprovalVo list = reportsService.getReportDomainCapability().getVuidApprovalReportGeneric(pstartDate);

        try {
            LOG.info("Writing Generic Data: " + section);

            if (section == GENERIC_NAMES) {
                for (ReportVuidVo vo : list.getNewGenericList()) {
                    writer.append(vo.getIen() + ROW_FIELD_SEPARATOR);
                    writer.append(vo.getName() + ROW_FIELD_SEPARATOR);
                    writer.append(vo.getVuid() + ROW_FIELD_SEPARATOR);
                    writer.newLine();
                }
            }

            if (section == GENERIC_NAMES_MODIFIED) {

                ReportVuidApprovalVo modList =
                    reportsService.getReportDomainCapability().getVuidModifiedReportGeneric(pstartDate);

                for (ReportVuidVo vo : modList.getModifiedGenericList()) {
                    writer.append(vo.getIen() + ROW_FIELD_SEPARATOR);
                    writer.append(vo.getName() + ROW_FIELD_SEPARATOR);
                    writer.append(vo.getVuid() + ROW_FIELD_SEPARATOR);
                    writer.append(checkEmpty(vo.getInactivationDate()));
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            LOG.info(ERROR + e);
        }
    }

    /**
     * @param writer Buffered Writer
     * @param section Section to write
     * @param pstartDate Start Date
     */
    private void addDrugClassData(BufferedWriter writer, String section, Date pstartDate) {
        ReportVuidApprovalVo list = reportsService.getReportDomainCapability().getVuidApprovalReportDrugClasses(pstartDate);

        try {
            LOG.info("Writing Drug Class Data: " + section);

            for (ReportVuidVo vo : list.getNewDrugClassList()) {
                writer.append(vo.getIen() + ROW_FIELD_SEPARATOR);
                writer.append(vo.getName() + ROW_FIELD_SEPARATOR);
                writer.append(vo.getVuid() + ROW_FIELD_SEPARATOR);
                writer.newLine();
            }
        } catch (IOException e) {
            LOG.info(ERROR + e);
        }

    }

    /**
     * @param in Date passed in
     * @return If null returns Field separator, else returns date + field separator.
     */
    private String checkEmpty(Date in) {

        // Get the default MEDIUM/SHORT DateFormat
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

        String out = null;

        if (in == null) {
            out = ROW_FIELD_SEPARATOR;
        } else {
            out = format.format(in) + ROW_FIELD_SEPARATOR;
        }

        return out;
    }
}
