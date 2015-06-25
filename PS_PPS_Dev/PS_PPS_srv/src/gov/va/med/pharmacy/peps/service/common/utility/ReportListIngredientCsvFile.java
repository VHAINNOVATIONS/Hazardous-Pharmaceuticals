/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.vo.ReportIngredientsVo;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;


/**
 * Class to creates and formats NDF CSV File for report functionality.
 *
 */
public class ReportListIngredientCsvFile extends CsvFile {

    /** PRODUCT_NAME */
    public static final String PRODUCT_NAME = "PRODUCT NAME";

    /** INGREDIENT_NAME */
    public static final String INGREDIENT_NAME = "INGREDIENT NAME";

    /** STRENGTH */
    public static final String STRENGTH = "STRENGTH";

    /** STRENGTH_UNIT */
    public static final String STRENGTH_UNIT = "STRENGTH UNIT";

    /** DOSAGE_FORM */
    public static final String DOSAGE_FORM = "DOSAGE FORM";

    /** LIST_INGREDIENTS_FIELD_NAMES */
    public static final String[] LIST_INGREDIENTS_FIELD_NAMES = {
        PRODUCT_NAME, INGREDIENT_NAME, STRENGTH, STRENGTH_UNIT, DOSAGE_FORM };

    // use these constants to index field arrays

    /** I_PRODUCT_NAME */
    public static final int I_PRODUCT_NAME = ArrayUtils.indexOf(LIST_INGREDIENTS_FIELD_NAMES, PRODUCT_NAME);

    /** I_INGREDIENT_NAME */
    public static final int I_INGREDIENT_NAME = ArrayUtils.indexOf(LIST_INGREDIENTS_FIELD_NAMES, INGREDIENT_NAME);

    /** I_STRENGTH */
    public static final int I_STRENGTH = ArrayUtils.indexOf(LIST_INGREDIENTS_FIELD_NAMES, STRENGTH);

    /** I_STRENGTH_UNIT */
    public static final int I_STRENGTH_UNIT = ArrayUtils.indexOf(LIST_INGREDIENTS_FIELD_NAMES, STRENGTH_UNIT);

    /** I_DOSAGE_FORM */
    public static final int I_DOSAGE_FORM = ArrayUtils.indexOf(LIST_INGREDIENTS_FIELD_NAMES, DOSAGE_FORM);

    private static final Logger LOG = Logger.getLogger(ReportListIngredientCsvFile.class);
    private static final String PATH = "./tmp/";
    private static final String FILENAME = ReportType.LIST_INGREDIENTS.getView() + ".csv";

    /**
     * Super
     */
    public ReportListIngredientCsvFile() {
        super();
    }

    /**
     * Creates file and adds field headers
     * @throws IOException IOException
     * @throws MigrationException IOException
     */
    public void createFile() throws IOException, MigrationException {

        // open file and create string array with column headings
        this.openAndWriteHeader(PATH + FILENAME, LIST_INGREDIENTS_FIELD_NAMES);

    }

    /**
     * Print List Ingredient report from data
     * @param actionList actionList
     * @exception Exception Exception
     */
    public void printListIngredient(List<ReportProductVo> actionList) throws Exception {

        String[] ingrFieldArray = new String[LIST_INGREDIENTS_FIELD_NAMES.length];

        int count = 0;

        for (ReportProductVo vo : actionList) {
            Arrays.fill(ingrFieldArray, "");

//            LOG.debug("Product" + vo.getVaProductName());
            count = 0;

            // Product Name
            if (vo.getVaProductName() != null) {
                ingrFieldArray[I_PRODUCT_NAME] = vo.getVaProductName();

                for (ReportIngredientsVo ingVo : vo.getIngredients()) {

                    // Removes Product Name for additional ingredients
                    if (count > 0 && vo.getVaProductName() != null) {
                        ingrFieldArray[I_PRODUCT_NAME] = "";
                    }

                    if (vo.getIngredients() == null) {
                        LOG.info(" No Ingredients.");
                        ingrFieldArray[I_INGREDIENT_NAME] = "No Ingredients";

                        // write row
                        this.putNextRow(ingrFieldArray);

                    } else {

                        // Ingredient Name
                        if (ingVo.getIngredient() != null) {
                            ingrFieldArray[I_INGREDIENT_NAME] = ingVo.getIngredient();
                        }

                        // Strength
                        if (ingVo.getStrength() != null) {
                            ingrFieldArray[I_STRENGTH] = ingVo.getStrength();
                        }

                        // Strength Unit
                        if (ingVo.getUnit() != null) {
                            ingrFieldArray[I_STRENGTH_UNIT] = ingVo.getUnit();
                        }

                        // Dosage Form
                        if (ingVo.getDosageForm() != null) {
                            ingrFieldArray[I_DOSAGE_FORM] = ingVo.getDosageForm();
                        }

                        count++;

                        // write row to product file
                        this.putNextRow(ingrFieldArray);

                    }
                }
            }

        }
    }
}
