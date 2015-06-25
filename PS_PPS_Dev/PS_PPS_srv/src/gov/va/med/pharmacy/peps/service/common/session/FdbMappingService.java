/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.session;


import java.util.Date;
import java.util.List;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainType;
import gov.va.med.pharmacy.peps.common.vo.FdbDomainVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * FdbMappingService
 *
 */
public interface FdbMappingService {

    /**
     * getFdbDomains
     * 
     * @param domainType FdbDomainType
     * @param filterDate Date to filter results
     * @return List<FdbDomainVo>
     */
    List<FdbDomainVo> getFdbDomains(FdbDomainType domainType, Date filterDate);

    /**
     * getEplTerms
     *
     * @param domain FdbDomainType
     * @return Map<String, Long>
     */
    Map<Long, String> getEplTerms(FdbDomainType domain);

    /**
     * getEplIds
     *
     * @param domain FdbDomainType
     * @return Map<String, Long>
     */
    Map<String, Long> getEplIds(FdbDomainType domain);

    /**
     * assignFdbDomainAssociation
     * 
     * @param domainType FdbDomainType
     * @param domains List of FdbDomainVo
     * @param user UserVo
     * @throws ValidationException ValidationException
     */
    void assignFdbDomainAssociation(FdbDomainType domainType,
        List<FdbDomainVo> domains, UserVo user) throws ValidationException;

    /**
     * Imports Mapping File
     * @param filePath String of file name
     * @param user UserVo
     * @throws Exception exception
     */
    void importDomainMapping(String filePath, UserVo user) throws Exception;

    /**
     * getUser
     *
     * @return UserVo
     */
    UserVo getUser();

    /**
     * getFileName
     *
     * @return String
     */
    String getFileName();

    /**
     * setProcessRunning
     *
     * @param processRunning Boolean
     */
    void setProcessRunning(Boolean processRunning);

    /**
     * getProcessRunning
     *
     * @return Boolean
     */
    Boolean getProcessRunning();

}
