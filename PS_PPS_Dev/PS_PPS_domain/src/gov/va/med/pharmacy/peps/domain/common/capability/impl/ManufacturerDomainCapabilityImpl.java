/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.ManufacturerDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplManufacturerDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplNdcDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplManufacturerDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.ManufacturerConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * Perform CRUD operations on manufacturers
 */
public class ManufacturerDomainCapabilityImpl extends ManagedDataDomainCapabilityImpl<ManufacturerVo, EplManufacturerDo>
    implements ManufacturerDomainCapability {

    private EplManufacturerDao eplManufacturerDao;
    private EplNdcDao eplNdcDao;
    private ManufacturerConverter manufacturerConverter;

    
    /**
     * The NDC Check does't have dependencies
     * 
     * @param manufacturerVo manufacturerVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(ManufacturerVo manufacturerVo, UserVo user) throws ValidationException {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT  item FROM ").append(EplNdcDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = " WHERE MANUFACTURER_ID_FK = " + manufacturerVo.getId() + " AND ITEM_STATUS LIKE 'ACTIVE'";
        query.append(whereClause);
        
        List<EplNdcDo> ndcs = eplNdcDao.executeHqlQuery(query.toString());

        if (ndcs == null || ndcs.size() == 0) {
            return;
        }

        throw new ValidationException(ValidationException.INACTIVATE_MANUFACTURER,
                                      ndcs.size(), ndcs.get(0).getNdcNumber());


    }
    
    
    /**
     * retrieveByName
     * @param name name
     * @return ManufacturerVo
     */
    public Collection<ManufacturerVo> retrieveByName(String name) {
        
        String name2 = name;
     
        // replace ' with double tick.
        if (name2.contains("'")) {
            name2 = name2.replace("'", "''");
        }
        
        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT item FROM").append(EplManufacturerDo.class)
                .append("item"));
        String whereClause = " where name LIKE '" + name2 + "'";
        query.append(whereClause);

        List<EplManufacturerDo> returnedDos = eplManufacturerDao.executeHqlQuery(query.toString());

        if (returnedDos == null || returnedDos.size() < 1) {
            return null;
        }
  
        return manufacturerConverter.convert(returnedDos);
             
//        return manufacturerVo;
        
    }
    
    
    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link ManagedItemVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(ManufacturerVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplManufacturerDo.NAME, item.getValue()));

        return criteria;
    }

    /**
     * setEplManufacturerDao
     * @param eplManufacturerDao eplManufacturerDao property
     */
    public void setEplManufacturerDao(EplManufacturerDao eplManufacturerDao) {
        this.eplManufacturerDao = eplManufacturerDao;
    }

    /**
     * setEplNdcDao
     * @param eplNdcDao eplNdcDao property
     */
    public void setEplNdcDao(EplNdcDao eplNdcDao) {
        this.eplNdcDao = eplNdcDao;
    }

    
    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplManufacturerDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public ManufacturerConverter getConverter() {
        return manufacturerConverter;
    }

    /**
     * setManufacturerConverter.
     * @param manufacturerConverter manufacturerConverter property
     */
    public void setManufacturerConverter(ManufacturerConverter manufacturerConverter) {
        this.manufacturerConverter = manufacturerConverter;
    }
}
