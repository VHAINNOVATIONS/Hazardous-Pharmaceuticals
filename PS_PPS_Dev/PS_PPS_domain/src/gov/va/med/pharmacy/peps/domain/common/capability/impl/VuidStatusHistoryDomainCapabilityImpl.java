/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import org.hibernate.Criteria;


import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.domain.common.capability.VuidStatusHistoryDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVuidStatusHistoryDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplVuidStatusHistoryDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.VuidStatusHistoryConverter;


/**
 * Perform CRUD operations with local consoles (System Information)
 */
public class VuidStatusHistoryDomainCapabilityImpl extends ManagedDataDomainCapabilityImpl<VuidStatusHistoryVo, 
    EplVuidStatusHistoryDo> implements VuidStatusHistoryDomainCapability {
    
    private EplVuidStatusHistoryDao eplVuidStatusHistoryDao;
    private VuidStatusHistoryConverter vuidStatusHistoryConverter;

    /**
     * Retrieve all VuidStatusHistoryVo.
     * 
     * @return VuidStatusHistoryVo
     */
    public VuidStatusHistoryVo retrieveVuidStatusHistoryInfo() {
        
        EplVuidStatusHistoryDo eplDo;
        VuidStatusHistoryVo vuidStatusHistoryVo = new VuidStatusHistoryVo();

        List<EplVuidStatusHistoryDo> eplDos = eplVuidStatusHistoryDao.retrieve();

        if (eplDos.size() > 0) {
            eplDo = eplDos.get(0);
            vuidStatusHistoryVo = vuidStatusHistoryConverter.convert(eplDo);
        }
        
        return vuidStatusHistoryVo;
    }

    /**
     * Insert the given VuidStatusHistoryVo.
     * 
     * @param vuidStatusHistoryVo vuidStatusHistoryVo
     * @param user {@link UserVo} performing the action
     * @return vuidStatusHistoryVo inserted Vo with new ID
     */
    public VuidStatusHistoryVo create(VuidStatusHistoryVo vuidStatusHistoryVo,
            UserVo user) {
        EplVuidStatusHistoryDo vuidStatusHistoryDo = 
            vuidStatusHistoryConverter.convert(vuidStatusHistoryVo);
        EplVuidStatusHistoryDo returnedVuidStatusHistoryDo = 
            eplVuidStatusHistoryDao.insert(vuidStatusHistoryDo, user);

        return vuidStatusHistoryConverter.convert(returnedVuidStatusHistoryDo);
    }

    /**
     * Update the given VuidStatusHistoryVo.
     * 
     * @param vuidStatusHistoryVo vuidStatusHistoryVo
     * @param user {@link UserVo} performing the action
     * @return VuidStatusHistoryVo upddated Vo
     */
    public VuidStatusHistoryVo update(VuidStatusHistoryVo vuidStatusHistoryVo,
            UserVo user) {
        
        EplVuidStatusHistoryDo vuidStatusHistoryDo = vuidStatusHistoryConverter.convert(vuidStatusHistoryVo);
        eplVuidStatusHistoryDao.update(vuidStatusHistoryDo, user);

        return vuidStatusHistoryVo;
    }

    /**
     * Deletes VuidStatusHistoryVo.
     */
    public void deleteAll() {
        eplVuidStatusHistoryDao
                .executeQuery("DELETE FROM EplVuidStatusHistoryDo");
    }


    /**
     * setVuidStatusHistoryConverter
     * @param vuidStatusHistoryConverter vuidStatusHistoryConverter property
     */
    public void setVuidStatusHistoryConverter(
            VuidStatusHistoryConverter vuidStatusHistoryConverter) {
        this.vuidStatusHistoryConverter = vuidStatusHistoryConverter;
    }

    /**
     * setEplVuidStatusHistoryDao
     * @param eplVuidStatusHistoryDao eplVuidStatusHistoryDao property
     */
    public void setEplVuidStatusHistoryDao(
            EplVuidStatusHistoryDao eplVuidStatusHistoryDao) {
        this.eplVuidStatusHistoryDao = eplVuidStatusHistoryDao;
    }

    /**
     * checkForActiveDependencies
     * @param managedItem managedItem
     * @param user user
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(VuidStatusHistoryVo managedItem, UserVo user) throws ValidationException {

        // not dependency checks needed for vuid status history
    }

    /**
     * getConverter
     * @return vuidStatusHistoryConverter vuidStatusHistoryConverter
     */
    @Override
    public Converter<VuidStatusHistoryVo, EplVuidStatusHistoryDo> getConverter() {
        return vuidStatusHistoryConverter;
    }

    /**
     * getDataAccessObject
     * @return eplVuidStatusHistoryDao
     */
    @Override
    public DataAccessObject getDataAccessObject() {
        return this.eplVuidStatusHistoryDao;
    }

    /**
     * createUniquenessCriteria is not used in this class.
     * @param item item
     * @return null
     */
    @Override
    protected Criteria createUniquenessCriteria(VuidStatusHistoryVo item) {
        return null;
    }
}
