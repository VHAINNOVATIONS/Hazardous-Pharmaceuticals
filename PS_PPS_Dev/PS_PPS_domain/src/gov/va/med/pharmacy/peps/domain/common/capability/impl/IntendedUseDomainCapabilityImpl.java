/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import org.hibernate.ObjectNotFoundException;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.domain.common.capability.IntendedUseDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplIntendedUseDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplIntendedUseDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.IntendedUseConverter;


/**
 * Perform CRUD operations on Intended Use VOs
 */
public class IntendedUseDomainCapabilityImpl implements
        IntendedUseDomainCapability {
    private EplIntendedUseDao eplIntendedUseDao;
    private IntendedUseConverter intendedUseConverter;

    /**
     * Retrieve all possible IntendedUse
     * 
     * @return List of IntendedUse
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.IntendedUseDomainCapability#retrieveDomain()
     */
    @Override
    public List<IntendedUseVo> retrieveDomain() {
        return intendedUseConverter.convert(eplIntendedUseDao.retrieve());
    }

    /**
     * Retrieve specified IntendedUseVo
     * 
     * @param id
     *            of intendedUseVo to retrieve
     * @return IntendedUseVo
     * @throws ItemNotFoundException  ItemNotFoundException
     * 
     */
    public IntendedUseVo retrieve(String id) throws ItemNotFoundException {
        EplIntendedUseDo intendedUseDo = null;

        try {
            intendedUseDo = eplIntendedUseDao.retrieve(new Long(id));

            return intendedUseConverter.convert(intendedUseDo);
        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(
                    ItemNotFoundException.ITEM_NOT_FOUND, new Object[] { id });

        }
    }

    /**
     * set the IntendedUse DAO
     * 
     * @param eplIntendedUseDao
     *            eplIntendedUseDao property
     */
    public void setEplIntendedUseDao(EplIntendedUseDao eplIntendedUseDao) {
        this.eplIntendedUseDao = eplIntendedUseDao;
    }

    /**
     * setIntendedUseConverter
     * @param intendedUseConverter
     *            intendedUseConverter property
     */
    public void setIntendedUseConverter(
            IntendedUseConverter intendedUseConverter) {
        this.intendedUseConverter = intendedUseConverter;
    }
}
