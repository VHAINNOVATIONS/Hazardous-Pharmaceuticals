/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.util.Arrays;
import java.util.List;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.common.vo.ReportWarningLabelVo;


/**
 * Generates CSV File for Warning Label Report
 *
 */
public class ReportWarningLableCsvFile extends CsvFile {

    /** PRODUCT_NAME */
    public static final String PRODUCT_NAME = "VA PRODUCT NAME";

    /** WARNING_LABEL_CODE */
    public static final String WARNING_LABEL_CODE = "WARNING LABEL ";

    /** WARNING_LABEL_FIELDS */
    public static final String[] WARNING_LABEL_FIELDS = getColumnHeaders();

//    private static final Logger LOG = Logger.getLogger(ReportWarningLableCsvFile.class);
    private static final String PATH = "./tmp/";
    private static final String FILENAME = ReportType.PRINT_PRODUCTS_WARNING_LABELS.getView() + ".csv";

    /**
     * Super 
     */
    public ReportWarningLableCsvFile() {
        super();
    }

    /**
     * Gets column headers for report
     * @return Array of column headers
     */
    private static String[] getColumnHeaders() {
        String[] headers = new String[PPSConstants.I11];
        Arrays.fill(headers, "");

        headers[0] = PRODUCT_NAME;

        // Loops through the number of code columns
        // Add the number of non code columns
        for (int i = 0; i < PPSConstants.I10; i = i + 1) {
            headers[i + 1] = (WARNING_LABEL_CODE + (i + 1));
        }

        return headers;
    }

    /**
     * Creates file and writes column headers
     * @throws Exception 
     */
    public void createFile() throws Exception {

        // open file and create string array with column headings
        this.openAndWriteHeader(PATH + FILENAME, WARNING_LABEL_FIELDS);
    }

    /**
     * Send in a known invalid ProductVo (a completely empty Product) to verify validation occurs.
     * @param list List of productVo
     * @throws Exception Exception
     */
    public void printWarningLabelReport(List<ReportProductVo> list) throws Exception {

        String[] warnFieldArray = new String[WARNING_LABEL_FIELDS.length];

        for (ReportProductVo vo : list) {

            Arrays.fill(warnFieldArray, "");

            if (vo.getWarningLabels().size() != 0) {

                // VA PRODUCT NAME (GCNSEQNO)
                warnFieldArray[0] = vo.getVaProductName();
                warnFieldArray[0] = warnFieldArray[0].concat(" (" + vo.getGcnSeqNo() + ")");

                // Count is number of non warning label fields
                int count = 1;

                for (ReportWarningLabelVo warnVo : vo.getWarningLabels()) {
                    warnFieldArray[count] = warnVo.getCode();
                    count++;

                }

                this.putNextRow(warnFieldArray);
            }
        }
    }
}
