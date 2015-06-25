/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.ReportProductVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.service.common.session.ReportsService;


/**
 * ReportNoActiveNdcCsvFile's brief summary
 * 
 * Details of ReportNoActiveNdcCsvFile's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class ReportNoActiveNdcCsvFile extends CsvFile {

    private static final Logger LOG = Logger.getLogger(ReportNoActiveNdcCsvFile.class);
    private static final String PATH = "./tmp/";
    private static final String FILENAME = ReportType.ACTIVE_PRDUCTS_NO_ACTIVE_NDCS_REPORT_PRINT_TEMPLATE.getView() + ".csv";
    private ReportsService reportsService;

    /**
     * Constructor
     *
     */
    public ReportNoActiveNdcCsvFile() {
        super();
    }

    /**
     * Constructor
     *
     * @param reportsService ReportsService
     */
    public ReportNoActiveNdcCsvFile(ReportsService reportsService) {
        super();

        this.reportsService = reportsService;
    }

    /**
     * createFile
     *
     */
    public void createFile() {
        List<ReportProductVo> list = reportsService.getReportDomainCapability().getProductNoActiveNdcData();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + FILENAME));

            //Write Column Headers
            writer.append("PRODUCT NAME");
            writer.newLine();

            for (ReportProductVo vo : list) {
                writer.append(vo.getVaProductName());
                writer.newLine();
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            LOG.info("Create ReportNoActiveNdcCsvFile IOException: " + e);
        }
    }

}
