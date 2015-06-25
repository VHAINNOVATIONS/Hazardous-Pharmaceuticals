/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability;


import java.util.List;
import java.util.Set;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.domain.common.model.EplDosageFormDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDfDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfAssocValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfLovDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;


/**
 * Perform CRUD operations on data fields
 */
public interface DataFieldsDomainCapability {

    /**
     * Insert the given vaDf.
     * 
     * @param ndc EplNdcDo
     * @param product EplProductDO
     * @param orderableItem EplOrderableItemDo
     * @param dosageForm EplDosageFormDo
     * @param set set of data fields
     * @param user {@link UserVo} performing the action
     * @return ProductVo inserted ProductVo with new ID
     */
    Set insertEplVaDfOwners(EplNdcDo ndc, EplProductDo product, EplOrderableItemDo orderableItem,
                            EplDosageFormDo dosageForm, Set set, UserVo user);

    /**
     * Retrieve all possible data fields
     * 
     * @return List of DataFields
     */
    DataFields retrieveDomain();

    /**
     * Retrieve the possible values for a given data field.
     * 
     * @param <T> Type of DataField
     * @param fieldKey FieldKey to retrieve
     * @return DataField with possible values populated
     */
    <T extends DataField> T retrieveDomain(FieldKey<T> fieldKey);

    /**
     * Retrieve owner
     * 
     * @param columnName String
     * @param id Object
     * @return List of DataFields
     */
    EplVadfOwnerDo retrieveOwnerTest(String columnName, Object id);

    /**
     * Retrieve all VadfLov Dos
     * 
     * @param columnName String
     * @param id Object
     * @return vaDfownerdo for a given id
     */
    List<EplVadfLovDo> retrieveLovTest(String columnName, Object id);

    /**
     * given the owner id and owner type retrieves all the EPL VADFs
     * 
     * @param itemOwnerId Long
     * @param itemOwnerType String
     * @return vaDfownerdo for a given id
     */
    Set<EplVadfOwnerDo> retrieveEplVaDfOwners(Long itemOwnerId, String itemOwnerType);

    /**
     * Retrieve {@link DataFields} for the given item with ID and entityType.
     * 
     * @param itemId ID of item with VA data fields
     * @param entityType {@link EntityType} of item with VA data fields
     * @return VA {@link DataFields}
     */
    DataFields<DataField> retrieve(Long itemId, EntityType entityType);

    /**
     * Retrieve all the vadf associations for the object
     * 
     * @param columnName String
     * @param id Object
     * @return VA DF associations
     */
    List<EplVadfAssocValueDo> retrieveProdAssocsTest(String columnName, Object id);

    /**
     * Update the given vaDfs.
     * 
     * @param vaDfs Set of EplVadfOwnerDo
     * @param user {@link UserVo} performing the action
     */
    void update(Set<EplVadfOwnerDo> vaDfs, UserVo user);

    /**
     * retrieve the VA Data field DO
     * 
     * @param id Long
     * @return EplVaDfDo
     * 
     */
    EplVaDfDo retrieveVaDf(Long id);

    /**
     * Delete the given {@link DataFields} and the values contained within.
     * 
     * @param dataFields {@link DataFields} to delete
     */
    void delete(DataFields dataFields);
}
