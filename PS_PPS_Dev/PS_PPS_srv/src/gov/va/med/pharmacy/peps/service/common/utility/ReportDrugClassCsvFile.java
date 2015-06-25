/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.vo.ReportDrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.ReportType;
import gov.va.med.pharmacy.peps.service.common.session.ReportsService;


/**
 * Class to creates and formats Drug Class CSV File for report functionality.
 *
 */
public class ReportDrugClassCsvFile extends CsvFile {

    private static final Logger LOG = Logger.getLogger(ReportNdfCsvFile.class);
    private static final String PATH = "./tmp/";
    private static final String FILENAME = ReportType.VA_DRUG_CLASSIFICATION_REPORT_PRINT_TEMPLATE.getView() + ".csv";
    private ReportsService reportsService;

    /**
     * Constructor
     *
     */
    public ReportDrugClassCsvFile() {
        super();
    }

    /**
     * Constructor
     *
     * @param reportsService ReportsService
     */
    public ReportDrugClassCsvFile(ReportsService reportsService) {
        super();

        this.reportsService = reportsService;
    }

    /**
     * createFile
     *
     * @param description Boolean
     */
    public void createFile(Boolean description) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + FILENAME));

            //Write Column Headers
            writer.append("DRUG CLASS CODE" + ROW_FIELD_SEPARATOR);
            writer.append("CLASSIFICATION");
            
            if (description == Boolean.TRUE) {
                writer.append(ROW_FIELD_SEPARATOR + "DESCRIPTION");
            }

            writer.newLine();
            writer.flush();
            writer.close();

        } catch (IOException e) {
            LOG.info("Create File IOException: " + e);
        }
    }

    /**
     * printDrugClassReport
     *
     * @param description Boolean
     */
    public void printDrugClassReport(Boolean description) {
        List<ReportDrugClassVo> list = reportsService.getReportDomainCapability().getDrugClassData();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + FILENAME, true));

            for (ReportDrugClassVo vo : list) {
                LOG.info("Drug Class: " + vo.getCode());

                writer.append(vo.getCode() + ROW_FIELD_SEPARATOR);
                writer.append(vo.getClassification());

                if (description == Boolean.TRUE) {
                    writer.append(ROW_FIELD_SEPARATOR + vo.getDescription());
                }

                writer.newLine();

                List<ReportDrugClassVo> secondary = vo.getSecondaryDrugClasses();

                for (ReportDrugClassVo sec : secondary) {
                    writer.append(sec.getCode() + ROW_FIELD_SEPARATOR);
                    writer.append(sec.getClassification());
                    writer.newLine();

                    List<ReportDrugClassVo> tertiary = sec.getTertiaryDrugClasses();

                    for (ReportDrugClassVo third : tertiary) {
                        writer.append(third.getCode() + ROW_FIELD_SEPARATOR);
                        writer.append(third.getClassification());
                        writer.newLine();
                    }
                }

            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            LOG.info("Print Drug Class IOException: " + e);
        }

    }

}
