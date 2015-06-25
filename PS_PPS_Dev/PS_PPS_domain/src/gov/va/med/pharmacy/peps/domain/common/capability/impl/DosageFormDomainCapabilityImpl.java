/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.DosageFormDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplDfMedRtDfAssocDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplDfNounDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplDfUnitDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplDispenseUnitsPerDoseDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplDosageFormDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplOrderableItemDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplProductDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDosageFormDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplDrugUnitDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DataFieldsConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DosageFormConverter;
import gov.va.med.pharmacy.peps.domain.common.utility.querybuilder.HqlBuilder;


/**
 * Perform CRUD operations on dosage forms
 */
public class DosageFormDomainCapabilityImpl extends
        ManagedDataDomainCapabilityImpl<DosageFormVo, EplDosageFormDo>
        implements DosageFormDomainCapability {

    //private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
    //        .getLogger(DosageFormDomainCapabilityImpl.class);

    private static String DOSAGE_FORM_EPL_ID = "eplDosageForm.eplId";

    private EplDosageFormDao eplDosageFormDao;
    private EplDfNounDao eplDfNounDao;
    private EplDfUnitDao eplDfUnitDao;
    private EplProductDao eplProductDao;
    private EplOrderableItemDao eplOrderableItemDao;
    private EplDfMedRtDfAssocDao eplDfMedRtDfAssocDao;
    private EplDispenseUnitsPerDoseDao eplDispenseUnitsPerDoseDao;

    private DataFieldsConverter dataFieldsConverter;
    private DosageFormConverter dosageFormConverter;

    /**
     * deleteItem
     * @param dosageFormVo dosageFormVo
     * @throws ValidationException ValidationException
     */
    @Override
    public void deleteItem(DosageFormVo dosageFormVo) throws ValidationException {
        List<EplDosageFormDo> dosageFormDo = getDataAccessObject().retrieve(EplDrugUnitDo.EPL_ID,
            Long.valueOf(dosageFormVo.getId()));

        if (dosageFormDo.size() == 1) {
            if (dosageFormDo.get(0).getDosageformIen() == null) {
                try {
                    checkForActiveDependencies(dosageFormVo, null);
                } catch (Exception e) {
                    throw new ValidationException(ValidationException.CANNOT_DELETE,
                        dosageFormVo.getDosageFormName(), " because another item is using it.");
                }

                getDataAccessObject().delete(dosageFormDo.get(0));
            } else {
                throw new ValidationException(ValidationException.CANNOT_DELETE,
                    dosageFormVo.getDosageFormName(), " because it has already synched with NDF.");
            }
        }
    }

    /**
     * dosageFormVo cannot be in-activated if is has any active products.
     * 
     * @param dosageFormVo dosageFormVo
     * @param user {@link UserVo} performing the action
     * @throws ValidationException ValidationException
     */
    @Override
    public void checkForActiveDependencies(DosageFormVo dosageFormVo, UserVo user) throws ValidationException {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT  item FROM ").append(EplOrderableItemDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = " WHERE DOSAGE_FORM_ID_FK = " + dosageFormVo.getId() + " AND ITEM_STATUS LIKE 'ACTIVE'";
        query.append(whereClause);

        List<EplOrderableItemDo> ois = eplOrderableItemDao.executeHqlQuery(query.toString());

        if (ois == null || ois.size() == 0) {
            return;
        }

        throw new ValidationException(ValidationException.INACTIVATE_DOSAGEFORM,
                                      ois.size(), ois.get(0).getOiName());

    }

    /**
     * Insert the given DosageFormVo.
     * 
     * @param dosageForm
     *            DosageFormVo
     * @param user
     *            {@link UserVo} performing the action
     * @return DosageFormVo with new ID
     */
    @Override
    public DosageFormVo createWithoutDuplicateCheck(DosageFormVo dosageForm,
            UserVo user) {

        if (dosageForm.getId() == null) {
            dosageForm.setId(getSeqNumDomainCapability().generateId(
                    dosageForm.getEntityType(), user));

            if ((dosageForm.getVaDataFields() != null)
                    && (!(dosageForm.getVaDataFields().isEmpty()))) {
                dosageForm.getVaDataFields().setVaDfOwnerId(
                        getSeqNumDomainCapability().generatedOwnerId(user));
            }
        }

        EplDosageFormDo dosageFormDo = dosageFormConverter.convert(dosageForm);

        eplDosageFormDao.insert(dosageFormDo, user);

        if (dosageFormDo.getEplDfNouns() != null
                && dosageFormDo.getEplDfNouns().size() > 0) {
            eplDfNounDao.insert(dosageFormDo.getEplDfNouns(), user);
        }

        if (dosageFormDo.getEplDfUnits() != null
                && dosageFormDo.getEplDfUnits().size() > 0) {
            eplDfUnitDao.insert(dosageFormDo.getEplDfUnits(), user);
        }

        if (dosageFormDo.getEplDispenseUnitsPerDoses() != null
                && dosageFormDo.getEplDispenseUnitsPerDoses().size() > 0) {
            eplDispenseUnitsPerDoseDao.insert(
                    dosageFormDo.getEplDispenseUnitsPerDoses(), user);
        }

        if (dosageFormDo.getEplDfMedRtDfAssocs() != null
                && dosageFormDo.getEplDfMedRtDfAssocs().size() > 0) {
            eplDfMedRtDfAssocDao.insert(dosageFormDo.getEplDfMedRtDfAssocs(),
                    user);
        }

        if ((dosageForm.getVaDataFields() != null)
                && (!(dosageForm.getVaDataFields().isEmpty()))) {

            // convert dosage form vo's data fields to a set of EplVaDfOwnerDo

            EplVadfOwnerDo owners = dataFieldsConverter.convert(
                    dosageForm.getVaDataFields(), dosageFormDo);

            Set<EplVadfOwnerDo> ownerDOs = new HashSet<EplVadfOwnerDo>();
            ownerDOs.add(owners);

            getDataFieldsDomainCapability().insertEplVaDfOwners(null, null,
                    null, dosageFormDo, ownerDOs, user);
        }

        return dosageForm;
    }

    /**
     * update the given DosageFormVo.
     * 
     * @param dosageForm
     *            DosageFormVo
     * @param user
     *            {@link UserVo} performing the action
     * @return DosageFormVo
     */
    @Override
    public DosageFormVo update(DosageFormVo dosageForm, UserVo user) {
        EplDosageFormDo dosageFormDo = dosageFormConverter.convert(dosageForm);

        // delete all existing nouns and re-insert
        eplDfNounDao.delete(DOSAGE_FORM_EPL_ID, dosageFormDo.getEplId());
        eplDfNounDao.insert(dosageFormDo.getEplDfNouns(), user);

        // delete all existing dispense units and re-insert
        eplDispenseUnitsPerDoseDao.delete(DOSAGE_FORM_EPL_ID,
                dosageFormDo.getEplId());
        eplDispenseUnitsPerDoseDao.insert(
                dosageFormDo.getEplDispenseUnitsPerDoses(), user);

        // deletes all the existing dosage form units and re-insert
        eplDfUnitDao.delete(DOSAGE_FORM_EPL_ID, dosageFormDo.getEplId());
        eplDfUnitDao.insert(dosageFormDo.getEplDfUnits(), user);

        // deletes all the existing local med routes and re-insert
        eplDfMedRtDfAssocDao
                .delete(DOSAGE_FORM_EPL_ID, dosageFormDo.getEplId());
        eplDfMedRtDfAssocDao.insert(dosageFormDo.getEplDfMedRtDfAssocs(), user);

        // convert dosage form vo's data fields to a set of EplVaDfOwnerDo
        if (!(dosageForm.getVaDataFields().isEmpty())) {
            EplVadfOwnerDo owners = dataFieldsConverter.convert(
                    dosageForm.getVaDataFields(), dosageFormDo);
            Set<EplVadfOwnerDo> ownerDOs = new HashSet<EplVadfOwnerDo>();
            ownerDOs.add(owners);
            getDataFieldsDomainCapability().update(ownerDOs, user);
        }

        eplDosageFormDao.update(dosageFormDo, user);

        return dosageForm;

    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its
     * uniqueness fields for the DosageFormDomainCapabilityImpl.
     * 
     * @param item
     *            {@link ManagedItemVo} for which to create uniqueness
     *            {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(DosageFormVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplDosageFormDo.DF_NAME,
                item.getDosageFormName()));

        return criteria;
    }

    /**
     * setEplDosageFormDao
     * @param eplDosageFormDao
     *            eplDosageFormDao property
     */
    public void setEplDosageFormDao(EplDosageFormDao eplDosageFormDao) {
        this.eplDosageFormDao = eplDosageFormDao;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    @Override
    public DataAccessObject getDataAccessObject() {
        return eplDosageFormDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    @Override
    public DosageFormConverter getConverter() {
        return dosageFormConverter;
    }

    /**
     * setEplDfNounDao
     * @param eplDfNounDao
     *            eplDfNounDao property
     */
    public void setEplDfNounDao(EplDfNounDao eplDfNounDao) {
        this.eplDfNounDao = eplDfNounDao;
    }

    /**
     * setEplDfUnitDao
     * @param eplDfUnitDao
     *            eplDfUnitDao property
     */
    public void setEplDfUnitDao(EplDfUnitDao eplDfUnitDao) {
        this.eplDfUnitDao = eplDfUnitDao;
    }

    /**
     * setEplDispenseUnitsPerDoseDao
     * @param eplDispenseUnitsPerDoseDao
     *            eplDispenseUnitsPerDoseDao property
     */
    public void setEplDispenseUnitsPerDoseDao(
            EplDispenseUnitsPerDoseDao eplDispenseUnitsPerDoseDao) {
        this.eplDispenseUnitsPerDoseDao = eplDispenseUnitsPerDoseDao;
    }

    /**
     * setEplDfMedRtDfAssocDao
     * @param eplDfMedRtDfAssocDao
     *            eplDfMedRtDfAssocDao property
     */
    public void setEplDfMedRtDfAssocDao(
            EplDfMedRtDfAssocDao eplDfMedRtDfAssocDao) {
        this.eplDfMedRtDfAssocDao = eplDfMedRtDfAssocDao;
    }

    /**
     * setDataFieldsConverter for DosageFormDomainCapabilityImpl.
     * @param dataFieldsConverter
     *            dataFieldsConverter property
     */
    public void setDataFieldsConverter(DataFieldsConverter dataFieldsConverter) {
        this.dataFieldsConverter = dataFieldsConverter;
    }

    /**
     * setDosageFormConverter
     * @param dosageFormConverter
     *            dosageFormConverter property
     */
    public void setDosageFormConverter(DosageFormConverter dosageFormConverter) {
        this.dosageFormConverter = dosageFormConverter;
    }

    /**
     * setEplOrderableItemDao
     * @param eplOrderableItemDao eplOrderableItemDao property
     */
    public void setEplOrderableItemDao(EplOrderableItemDao eplOrderableItemDao) {
        this.eplOrderableItemDao = eplOrderableItemDao;
    }

    /**
     * Delete associated domain from data base if IEN is not assigned and other objects are not dependent on this object.
     * 
     * @param dosageFormNameVo to delete
     * @param user requesting the delete
     * 
     * @throws ValidationException to validate the IEN and dependencies
     * 
     */
    @Override
    public void deletePending(DosageFormVo dosageFormNameVo, UserVo user) throws ValidationException {

        StringBuffer query = new StringBuffer();
        query.append(HqlBuilder.create("SELECT  item FROM ").append(EplProductDo.class)
                .append(PPSConstants.ITEM));
        String whereClause = " WHERE DOSE_UNIT_ID_FK = " + dosageFormNameVo.getId();
        query.append(whereClause);

        List<EplProductDo> products = eplProductDao.executeHqlQuery(query.toString());

        if (products == null || products.size() == 0) {
            return;
        }

        throw new ValidationException(ValidationException.INACTIVATE_DOSAGEFORM,
                                      products.size(), products.get(0).getVaProductName());
    }

}
