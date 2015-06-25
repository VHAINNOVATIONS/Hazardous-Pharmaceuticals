/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.service.common.session.ReportsService;


/**
 * ReportExclusionsCsvFile's brief summary
 * 
 * Details of ReportExclusionsCsvFile's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class ReportExclusionsCsvFile extends CsvFile {

    private static final Logger LOG = Logger.getLogger(ReportExclusionsCsvFile.class);
    private static final String PATH = "./tmp/";
    private static final String FILENAME = ReportType.PRODUCTS_WITH_EXCLUSIONS_DRG_DRG_REPORT_PRINT_TEMPLATE.getView() + ".csv";
    private ReportsService reportsService;

    /**
     * Constructor
     *
     */
    public ReportExclusionsCsvFile() {
        super();
    }

    /**
     * Constructor
     *
     * @param reportsService ReportsService
     */
    public ReportExclusionsCsvFile(ReportsService reportsService) {
        super();

        this.reportsService = reportsService;
    }

    /**
     * createFile for the ReportExclusionsCsv File.
     *
     * @param startDate Date
     * @param stopDate Date
     */
    public void createFile(Date startDate, Date stopDate) {
        List<ReportProductVo> list = reportsService.getReportDomainCapability().getProductExclusionData(startDate, stopDate);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + FILENAME));

            //Write Column Headers
            writer.append("VA PRODUCT NAME" + ROW_FIELD_SEPARATOR);
            writer.append("EXCLUDED");
            writer.newLine();

            for (ReportProductVo vo : list) {
                writer.append(vo.getVaProductName() + ROW_FIELD_SEPARATOR);
                writer.append(vo.getExcluded());
                writer.newLine();
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            LOG.info("ReportExclusionsCsvFile.createFile IOException: " + e);
        }
    }

}
