/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.LocalConsoleInfoVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ConsoleDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplLocalConsoleInfoDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplLocalConsoleInfoDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.LocalConsoleInfoConverter;


/**
 * Perform CRUD operations with local consoles (System Information)
 */
public class ConsoleDomainCapabilityImpl implements ConsoleDomainCapability {
    private EplLocalConsoleInfoDao localConsoleInfoDao;
    private LocalConsoleInfoConverter localConsoleInfoConverter;

    /**
     * Retrieve all LocalConsoleInfoVo.
     * 
     * @return List<LocalConsoleInfoVo>
     */
    public List<LocalConsoleInfoVo> retrieveLocalConsoleInfo() {
        List<EplLocalConsoleInfoDo> eplDos = localConsoleInfoDao.retrieveAscending(EplLocalConsoleInfoDo.KEY);
        List<LocalConsoleInfoVo> consoleVos = localConsoleInfoConverter.convert(eplDos);

        return consoleVos;
    }

    /**
     * set the Local console info
     * 
     * @param inLocalConsoleInfoDao EplLocalConsoleInfoDao
     */
    public void setEplLocalConsoleInfoDao(EplLocalConsoleInfoDao inLocalConsoleInfoDao) {
        this.localConsoleInfoDao = inLocalConsoleInfoDao;
    }

    /**
     * Insert the given LocalConsoleInfoVo.
     * 
     * @param localConsoleInfoVo LocalConsoleInfoVo
     * @param user {@link UserVo} performing the action
     * @return LocalConsoleInfoVo inserted Vo with new ID
     */
    public LocalConsoleInfoVo create(LocalConsoleInfoVo localConsoleInfoVo, UserVo user) {
        EplLocalConsoleInfoDo consoleDo = localConsoleInfoConverter.convert(localConsoleInfoVo);
        EplLocalConsoleInfoDo returnedConsoleDo = localConsoleInfoDao.insert(consoleDo, user);

        return localConsoleInfoConverter.convert(returnedConsoleDo);
    }

    /**
     * Update the given LocalConsoleInfoVo.
     * 
     * @param localConsoleInfoVo LocalConsoleInfoVo
     * @param user {@link UserVo} performing the action
     * @return LocalConsoleInfoVo upddated Vo
     */
    public LocalConsoleInfoVo update(LocalConsoleInfoVo localConsoleInfoVo, UserVo user) {
        EplLocalConsoleInfoDo consoleDo = localConsoleInfoConverter.convert(localConsoleInfoVo);
        localConsoleInfoDao.update(consoleDo, user);

        return localConsoleInfoVo;
    }

    /**
     * Deletes LocalConsoleInfoVo.
     */
    public void deleteAll() {
        localConsoleInfoDao.executeQuery("DELETE FROM EplLocalConsoleInfoDo");
    }

    /**
     * setLocalConsoleInfoConverter
     * @param localConsoleInfoConverter localConsoleInfoConverter property
     */
    public void setLocalConsoleInfoConverter(LocalConsoleInfoConverter localConsoleInfoConverter) {
        this.localConsoleInfoConverter = localConsoleInfoConverter;
    }
}
