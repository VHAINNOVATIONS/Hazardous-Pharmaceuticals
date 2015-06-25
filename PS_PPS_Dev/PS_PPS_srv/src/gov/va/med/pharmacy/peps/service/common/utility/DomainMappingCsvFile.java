/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.exception.MigrationException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainType;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainVo;


/**
 * DomainMappingCsvFile
 */
public class DomainMappingCsvFile extends CsvFile {

    private static final Logger LOG = Logger.getLogger(DomainMappingCsvFile.class);
    private static final String SUFT8 = "UTF-8";
    private static final String PATH = "./tmp/";
    private static final String FILENAME = "DomainMapping.csv";

    //Column Headers
    private static final String DOMAIN = "DOMAIN";
    private static final String FDB_TERM = "FDB TERM";
    private static final String ENTRY_DATE = "ENTRY DATE";
    private static final String ASSOCIATED_TERM = "ASSOCIATED PPS-N TERM";

    private static final String[] DOMAIN_MAPPING_FIELDS = {
        DOMAIN, FDB_TERM, ENTRY_DATE, ASSOCIATED_TERM };

    private static final int I_DOMAIN = ArrayUtils.indexOf(DOMAIN_MAPPING_FIELDS, DOMAIN);
    private static final int I_FDB_TERM = ArrayUtils.indexOf(DOMAIN_MAPPING_FIELDS, FDB_TERM);
    private static final int I_ENTRY_DATE = ArrayUtils.indexOf(DOMAIN_MAPPING_FIELDS, ENTRY_DATE);
    private static final int I_ASSOCIATED_TERM = ArrayUtils.indexOf(DOMAIN_MAPPING_FIELDS, ASSOCIATED_TERM);

    /**
     * DomainMappingCsvFile
     */
    public DomainMappingCsvFile() {
        super();

    }

    /**
     * getNextDomain
     *
     * @param domainVo FdbDomainVo
     * @return FdbDomainVo
     * @throws MigrationException 
     * @throws ParseException 
     * @throws UnsupportedEncodingException 
     */
    public FdbDomainVo getNextDomain(FdbDomainVo domainVo) throws MigrationException, 
        ParseException, UnsupportedEncodingException {

        String[] domainFields = this.getNextRow(DOMAIN_MAPPING_FIELDS.length);

        if (domainFields == null) {
            return null;
        }

        domainVo.setDomainType(FdbDomainType.valueOf(domainFields[I_DOMAIN]));
        domainVo.setName(domainFields[I_FDB_TERM]);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        domainVo.setDate(dateFormat.parse(domainFields[I_ENTRY_DATE]));

        domainVo.setFdbDomainId(URLEncoder.encode(domainFields[I_FDB_TERM], SUFT8));
        domainVo.setEplTerm(domainFields[I_ASSOCIATED_TERM]);

        return domainVo;
    }

    /**
     * openForImport
     *
     * @param strFileName String
     * @throws ValidationException ValidationException
     * @throws MigrationException  MigrationException
     */
    public void openForImport(String strFileName) throws ValidationException, MigrationException {

        // open file and create string array with column headings
        String[] domainArray = this.openAndReadHeader(PATH + strFileName);

        // compare with a domain mapping valid header
        if (!(Arrays.equals(domainArray, DOMAIN_MAPPING_FIELDS))) {
            throw new ValidationException(ValidationException.INVALID_HEADER);
        }

    }

    /**
     * Creates Domain Mapping export file and adds header
     */
    public void createFile() {

        // open file and create string array with column headings
        try {
            this.openAndWriteHeader(PATH + FILENAME, DOMAIN_MAPPING_FIELDS);
        } catch (Exception e) {
            LOG.error("Error writing Domain Mapping CSV file: " + e);
        }
    }

    /**
     * Prints data to the Domain Mapping CSV file
     * @param list FdbDomainVo
     * @param domain Domain
     * @param eplTerms Map of epl terms per domain
     */
    @SuppressWarnings("deprecation")
    public void printDomainMapping(List<FdbDomainVo> list, FdbDomainType domain, Map<Long, String> eplTerms) {
        String[] domainMappingArray = new String[DOMAIN_MAPPING_FIELDS.length];
        LOG.debug("----- " + domain + "(" + list.size() + ") -----");

        for (FdbDomainVo vo : list) {
            Arrays.fill(domainMappingArray, "");

            // DOMAIN
            domainMappingArray[I_DOMAIN] = domain.toString();

            // FDB TERM
            if (vo.getName() != null) {
                try {
                    domainMappingArray[I_FDB_TERM] = URLDecoder.decode(vo.getFdbDomainId(), SUFT8);
                } catch (UnsupportedEncodingException e) {
                    LOG.error("Error with URLDecoder: " + e);
                }
            }

            // ENTRY DATE
            if (vo.getDate() != null) {
                domainMappingArray[I_ENTRY_DATE] = vo.getDate().toLocaleString();
            }

            // ASSOCIATED PPS-N TERM
            if (vo.getEplDomainId() != null) {
                domainMappingArray[I_ASSOCIATED_TERM] = eplTerms.get(vo.getEplDomainId());
            }

            try {
                putNextRow(domainMappingArray);
            } catch (Exception e) {
                LOG.error("Error occured while writing to Domain Mapping file for: " + vo.getName() + "-" + e);
            }
        }
    }

}
