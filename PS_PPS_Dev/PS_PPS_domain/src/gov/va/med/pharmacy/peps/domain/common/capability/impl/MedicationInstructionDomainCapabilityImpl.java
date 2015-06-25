/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.vo.MedicationInstructionVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.domain.common.capability.LocalMedicationRouteDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.capability.MedicationInstructionDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.DataAccessObject;
import gov.va.med.pharmacy.peps.domain.common.dao.EplMedInstructWardDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplMedicationInstructionDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplMedicationInstructionDo;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.MedicationInstructionConverter;


/**
 * Perform CRUD operations on Medication Instructions
 */
public class MedicationInstructionDomainCapabilityImpl extends
    ManagedDataDomainCapabilityImpl<MedicationInstructionVo, EplMedicationInstructionDo> implements
    MedicationInstructionDomainCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(MedicationInstructionDomainCapabilityImpl.class);

    private EplMedicationInstructionDao eplMedicationInstructionDao;
    private EplMedInstructWardDao eplMedInstructWardDao;
    private LocalMedicationRouteDomainCapability localMedicationRouteDomainCapability;
    private MedicationInstructionConverter medicationInstructionConverter;

    /**
     * setMedicationInstructionConverter
     * 
     * @param medicationInstructionConverter medicationInstructionConverter property
     */
    public void setMedicationInstructionConverter(MedicationInstructionConverter medicationInstructionConverter) {
        this.medicationInstructionConverter = medicationInstructionConverter;
    }

    /**
     * Insert the given MedicationInstructionVo.
     * 
     * @param medicationInstruction MedicationInstructionVo
     * @param user {@link UserVo} performing the action
     * @return MedicationInstructionVo with new ID
     */
    @Override
    public MedicationInstructionVo createWithoutDuplicateCheck(MedicationInstructionVo medicationInstruction, UserVo user) {
        if (medicationInstruction.getId() == null) {
            medicationInstruction.setId(getSeqNumDomainCapability().generateId(medicationInstruction.getEntityType(), user));
        }

        EplMedicationInstructionDo medInstructionDo = medicationInstructionConverter.convert(medicationInstruction);

        eplMedicationInstructionDao.insert(medInstructionDo, user);

        if (medInstructionDo.getEplMedInstructWards() != null && medInstructionDo.getEplMedInstructWards().size() > 0) {
            eplMedInstructWardDao.insert(medInstructionDo.getEplMedInstructWards(), user);
        }

        return medicationInstruction;
    }

    /**
     * update the given MedicationInstructionVo.
     * 
     * @param medicationInstruction MedicationInstructionVo
     * @param user {@link UserVo} performing the action
     * @return MedicationInstructionVo
     * @throws DuplicateItemException 
     */
    @Override
    public MedicationInstructionVo update(MedicationInstructionVo medicationInstruction, UserVo user)
        throws DuplicateItemException {

        EplMedicationInstructionDo medInstructionDo = medicationInstructionConverter.convert(medicationInstruction);

        // delete all existing assocs and add the ones from the new Vo
        eplMedInstructWardDao.delete("eplMedicationInstruction.eplId", medInstructionDo.getEplId());
        eplMedInstructWardDao.insert(medInstructionDo.getEplMedInstructWards(), user);

        LOG.debug("medINstexp" + medInstructionDo.getMedInstructionExpansion());

        if (medicationInstruction.getLocalMedicationRoute() != null) {
            localMedicationRouteDomainCapability.update(medicationInstruction.getLocalMedicationRoute(), user);
        }

        eplMedicationInstructionDao.update(medInstructionDo, user);

        return medicationInstruction;
    }

    /**
     * Create Hibernate {@link Criteria} that will find an item by its uniqueness fields.
     * 
     * @param item {@link MedicationInstructionVo} for which to create uniqueness {@link Criteria}
     * @return {@link Criteria}
     */
    @Override
    protected Criteria createUniquenessCriteria(MedicationInstructionVo item) {
        Criteria criteria = getDataAccessObject().getCriteria();
        criteria.add(Restrictions.ilike(EplMedicationInstructionDo.NAME, item.getValue()));

        return criteria;
    }

    /**
     * Get the {@link DataAccessObject} that this capability uses.
     * 
     * @return {@link DataAccessObject}
     */
    public DataAccessObject getDataAccessObject() {
        return eplMedicationInstructionDao;
    }

    /**
     * Return the {@link Converter} instance for this capability.
     * 
     * @return {@link Converter}
     */
    public MedicationInstructionConverter getConverter() {
        return medicationInstructionConverter;
    }

    /**
     * setEplMedicationInstructionDao
     * @param eplMedicationInstructionDao eplMedicationInstructionDao property
     */
    public void setEplMedicationInstructionDao(EplMedicationInstructionDao eplMedicationInstructionDao) {
        this.eplMedicationInstructionDao = eplMedicationInstructionDao;
    }

    /**
     * setEplMedInstructWardDao
     * @param eplMedInstructWardDao eplMedInstructWardDao property
     */
    public void setEplMedInstructWardDao(EplMedInstructWardDao eplMedInstructWardDao) {
        this.eplMedInstructWardDao = eplMedInstructWardDao;
    }

    /**
     * setLocalMedicationRouteDomainCapability
     * @param localMedicationRouteDomainCapability localMedicationRouteDomainCapability property
     */
    public void setLocalMedicationRouteDomainCapability(
        LocalMedicationRouteDomainCapability localMedicationRouteDomainCapability) {
        this.localMedicationRouteDomainCapability = localMedicationRouteDomainCapability;
    }
}
