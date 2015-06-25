/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.PartialSaveMgtDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplPartialSaveMgtDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplPartialSaveMgtDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.PartialSaveConverter;


/**
 * Perform CRUD operations on PartialSaveMgmtVo
 */
public class PartialSaveMgtDomainCapabilityImpl implements PartialSaveMgtDomainCapability {

    private EplPartialSaveMgtDao eplPartialSaveMgtDao;

    private PartialSaveConverter partialSaveConverter;

    /**
     * Insert the given PartialSaveMgmtVo.
     * 
     * @param partialSaveVo partialSaveVo
     * @param user {@link UserVo} performing the action
     * @return PartialSaveMgmtVo inserted PartialSaveMgmtVo with new ID
     */
    public PartialSaveVo create(PartialSaveVo partialSaveVo, UserVo user) {

        EplPartialSaveMgtDo partialSaveDo = partialSaveConverter.convert(partialSaveVo);
        EplPartialSaveMgtDo saved = eplPartialSaveMgtDao.insert(partialSaveDo, user);
        PartialSaveVo returnedVo = partialSaveConverter.convert(saved);

        return returnedVo;

    }

    /**
     * Create the list of values of the PartialSaveItems
     * 
     * @return PartialSaveMgmtVo inserted PartialSaveMgmtVo with new ID
     */
    public List<PartialSaveVo> retrieveAll() {
        Criteria criteria = eplPartialSaveMgtDao.getCriteria();
        criteria.addOrder(Order.desc(EplPartialSaveMgtDo.CREATED_DATE_TIME));
        List<EplPartialSaveMgtDo> values = criteria.list();
        List<PartialSaveVo> returnedValues = partialSaveConverter.convert(values);

        return returnedValues;
    }

    /**
     * Retrieve a partially saved item.
     * 
     * @param id String ID of the PartialSaveVo to retrieve
     * @return PartialSaveVo for the given ID
     * @throws ItemNotFoundException if cannot find the PartialSaveVo for the given ID
     */
    public PartialSaveVo retrieve(String id) throws ItemNotFoundException {
        EplPartialSaveMgtDo eplPartialSaveMgtDo;

        try {
            eplPartialSaveMgtDo = eplPartialSaveMgtDao.retrieve(Long.parseLong(id));
        } catch (ObjectNotFoundException e) {
            throw new ItemNotFoundException(ItemNotFoundException.ITEM_NOT_FOUND, new Object[] {id});
        }

        return partialSaveConverter.convert(eplPartialSaveMgtDo);
    }

    /**
     * Deletes row from database
     * 
     * @param id String
     */
    public void delete(String id) {
        EplPartialSaveMgtDo data = eplPartialSaveMgtDao.retrieve(Long.parseLong(id));
        eplPartialSaveMgtDao.delete(data);
    }

    /**
     * Deletes all partial saves for the given {@link ManagedItemVo}
     * 
     * @param item {@link ManagedItemVo} for which to delete partial saves
     */
    public void delete(ManagedItemVo item) {
        String property = null;

        if (item instanceof OrderableItemVo) {
            property = EplPartialSaveMgtDo.ORDERABLE_ITEM_EPL_ID;
        } else if (item instanceof ProductVo) {
            property = EplPartialSaveMgtDo.PRODUCT_EPL_ID;
        } else {
            property = EplPartialSaveMgtDo.NDC_EPL_ID;
        }

        eplPartialSaveMgtDao.delete(property, Long.valueOf(item.getId()));
    }

    /**
     * setEplPartialSaveMgtDao
     * @param eplPartialSaveMgtDao eplPartialSaveMgtDao property
     */
    public void setEplPartialSaveMgtDao(EplPartialSaveMgtDao eplPartialSaveMgtDao) {
        this.eplPartialSaveMgtDao = eplPartialSaveMgtDao;
    }

    /**
     * Retrieve partial saves for a user.
     * 
     * @param user UserVo for which to retrieve partial saves
     * @return List<PartialSaveVo>
     */
    public List<PartialSaveVo> retrieveAll(UserVo user) {
        Criteria criteria = eplPartialSaveMgtDao.getCriteria();
        criteria.add(Restrictions.ilike(EplPartialSaveMgtDo.LAST_MODIFIED_BY, user.getUsername()));
        criteria.addOrder(Order.desc(EplPartialSaveMgtDo.CREATED_DATE_TIME));
        List<EplPartialSaveMgtDo> values = criteria.list();
        List<PartialSaveVo> returnedValues = partialSaveConverter.convert(values);

        return returnedValues;

//        List<PartialSaveVo> all = retrieveAll();
//        List<PartialSaveVo> userSaves = new ArrayList<PartialSaveVo>();
//
//        for (PartialSaveVo partialSaveVo : all) {
//            if (partialSaveVo.getModifiedBy() != null && partialSaveVo.getModifiedBy().equals(user.getUsername())) {
//                userSaves.add(partialSaveVo);
//            }
//        }
//
//        return userSaves;
    }

    /**
     * setPartialSaveConverter
     * @param partialSaveConverter partialSaveConverter property
     */
    public void setPartialSaveConverter(PartialSaveConverter partialSaveConverter) {
        this.partialSaveConverter = partialSaveConverter;
    }
}

