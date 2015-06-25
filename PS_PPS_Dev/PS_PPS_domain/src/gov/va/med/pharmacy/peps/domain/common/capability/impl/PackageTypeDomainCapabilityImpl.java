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
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.PackageTypeDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplNdcDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplPackageTypeDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplPackageTypeDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.PackageTypeConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * Perform CRUD operations on package types
 */
public class PackageTypeDomainCapabilityImpl extends ManagedDataDomainCapabilityImpl<PackageTypeVo, EplPackageTypeDo>
    implements PackageTypeDomainCapability {

    private EplPackageTypeDao eplPackageTypeDao;
    private EplNdcDao eplNdcDao;
    private PackageTypeConverter packageTypeConverter;

    
    /**
     * NDCs depend upon the packagetype
     * 
     * @param packageTypeVo packageTypeVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(PackageTypeVo packageTypeVo, UserVo user) throws ValidationException {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT  item FROM ").append(EplNdcDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = " WHERE PACKAGE_TYPE_ID_FK = " + packageTypeVo.getId() + " AND ITEM_STATUS LIKE 'ACTIVE'";
        query.append(whereClause);
        
        List<EplNdcDo> ndcs = eplNdcDao.executeHqlQuery(query.toString());

        if (ndcs == null || ndcs.size() == 0) {
            return;
        }

        throw new ValidationException(ValidationException.INACTIVATE_PACKAGETYPE,
                                      ndcs.size(), ndcs.get(0).getNdcNumber());


    }

    /**
     * retrieveByName
     * @param name name
     * @return Collection<PackageTypeVo>
     */
    public Collection<PackageTypeVo> retrieveByName(String name) {
        
        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT item FROM").append(EplPackageTypeDo.class)
                .append("item"));
        String whereClause = " where PACKAGE_TYPE_NAME = '" + name + "'";
        query.append(whereClause);

        List<EplPackageTypeDo> returnedDos = eplPackageTypeDao.executeHqlQuery(query.toString());

        if (returnedDos == null || returnedDos.size() < 1) {
            return null;
        }
  
        return packageTypeConverter.convert(returnedDos);
             
//        return manufacturerVo;
        
    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link PackageTypeVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(PackageTypeVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplPackageTypeDo.NAME, item.getValue()));

        return criteria;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplPackageTypeDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public PackageTypeConverter getConverter() {
        return packageTypeConverter;
    }

    /**
     * setEplNdcDao
     * @param eplNdcDao eplNdcDao property
     */
    public void setEplNdcDao(EplNdcDao eplNdcDao) {
        this.eplNdcDao = eplNdcDao;
    }
    
    /**
     * setEplPackageTypeDao
     * @param eplPackageTypeDao EplPackageTypeDao property
     */
    public void setEplPackageTypeDao(EplPackageTypeDao eplPackageTypeDao) {
        this.eplPackageTypeDao = eplPackageTypeDao;
    }

    /**
     * setPackageTypeConverter
     * @param packageTypeConverter packageTypeConverter property
     */
    public void setPackageTypeConverter(PackageTypeConverter packageTypeConverter) {
        this.packageTypeConverter = packageTypeConverter;
    }
}
