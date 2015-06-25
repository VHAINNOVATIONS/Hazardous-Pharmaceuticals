/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.capability.impl;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.va.med.pharmacy.peps.common.exception.DomainException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataField;
import gov.va.med.pharmacy.peps.common.vo.datafield.DataFields;
import gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability;
import gov.va.med.pharmacy.peps.domain.common.dao.EplMultiTextDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVaDfDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVadfAssocValueDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVadfEditablePropertyDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVadfLovDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVadfNonlistValueDao;
import gov.va.med.pharmacy.peps.domain.common.dao.EplVadfOwnerDao;
import gov.va.med.pharmacy.peps.domain.common.model.EplDosageFormDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplMultiTextDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVaDfDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfAssocValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfEditablePropertyDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfLovDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfLovDoKey;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfNonlistValueDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplVadfOwnerDo;
import gov.va.med.pharmacy.peps.domain.common.utility.DataFieldPersistenceHelper;
import gov.va.med.pharmacy.peps.domain.common.utility.converter.DataFieldsConverter;


/**
 * Perform CRUD operations on or with DataFields
 */
public class DataFieldsDomainCapabilityImpl implements DataFieldsDomainCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DataFieldsDomainCapabilityImpl.class);

    private EplVaDfDao eplVaDfDao;
    private EplVadfOwnerDao eplVadfOwnerDao;
    private EplVadfEditablePropertyDao eplVadfEditablePropertyDao;
    private EplVadfNonlistValueDao eplVadfNonlistValueDao;
    private EplVadfAssocValueDao eplVadfAssocValueDao;
    private EplVadfLovDao eplVadfLovDao;
    private EplMultiTextDao eplMultiTextDao;

    private DataFieldsConverter dataFieldsConverter;

    /**
     * Retrieve all possible data fields
     * 
     * @return List of DataFields
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability#retrieveDomain()
     */
    @Override
    public DataFields retrieveDomain() {
        DataFields fields = new DataFields();

        // Add any synthesized data fields.
        DataFieldPersistenceHelper.addAllFieldsNotYetPersisted(fields);

        // Add all data fields persisted in the database.
        for (EplVaDfDo eplVaDfDo : eplVaDfDao.retrieve()) {
            if (eplVaDfDo.getVadfName() == null) {
                LOG.error("LOG DataFieldDomainCapability: The eplVaDfName is null;");
                System.out.println("DFDC obtained NULL data field name ");
            }else{
                System.out.println("DFDC obtained data field name "+eplVaDfDo.getVadfName());
            }
            
            fields.setDataField(dataFieldsConverter.convert(eplVaDfDo, fields));
        }

        return fields;
    }

    /**
     * Retrieve the possible values for a given data field.
     * 
     * @param <T>
     *            Type of DataField
     * @param fieldKey
     *            FieldKey to retrieve
     * @return DataField with possible values populated
     */
    @Override
    public <T extends DataField> T retrieveDomain(FieldKey<T> fieldKey) {
        Criteria criteria = eplVaDfDao.getCriteria();
        criteria.add(Restrictions.eq(EplVaDfDo.NAME, fieldKey.getKey()));

        // FieldKeys are unique, so there will only be one, but in case it isn't
        // persisted in the database return null.
        EplVaDfDo vadf = (EplVaDfDo) criteria.uniqueResult();
        T dataField = null;

        if (vadf != null) {
            dataField = (T) dataFieldsConverter.convert(vadf, new DataFields());
        }

        return dataField;
    }

    /**
     * given the owner id and owner type retrieves all the EPL VADFs
     * 
     * @param itemOwnerId
     *            Long
     * @param itemOwnerType
     *            String
     * @return List of DataFields if they exists otherwise return empty has list
     * 
     */
    @Override
    public Set<EplVadfOwnerDo> retrieveEplVaDfOwners(Long itemOwnerId, String itemOwnerType) {
        Criteria criteria = eplVadfOwnerDao.getCriteria();
        criteria.add(Restrictions.eq(EplVadfOwnerDo.ITEM_OWNER_ID, itemOwnerId));
        criteria.add(Restrictions.eq(EplVadfOwnerDo.ITEM_OWNER_TYPE, itemOwnerType));
        List<EplVadfOwnerDo> ownerList = criteria.list();

        Set<EplVadfOwnerDo> owners = new HashSet<EplVadfOwnerDo>(ownerList.size());

        if (ownerList.size() > 0) {
            EplVadfOwnerDo owner = ownerList.get(0);
            eplVadfOwnerDao.refresh(owner);
            owner.setEplVadfEditableProperties(retrieveEditableProperties(owner));
            owner.setEplVadfNonlistValues(retrieveNonListValues(owner));
            owner.setEplVadfAssocValues(retrieveProdAssocs(owner));
            owner.setEplMultiTexts(retrieveMulitTextValues(owner));
            owners.add(owner);
        }

        return owners;
    }

    /**
     * Retrieve {@link DataFields} for the given item with ID and entityType.
     * 
     * @param itemId
     *            ID of item with VA data fields
     * @param entityType
     *            {@link EntityType} of item with VA data fields
     * @return VA {@link DataFields}
     */
    @Override
    public DataFields<DataField> retrieve(Long itemId, EntityType entityType) {
        Set<EplVadfOwnerDo> data = retrieveEplVaDfOwners(itemId, entityType.name());

        return dataFieldsConverter.convertFirst(data, entityType);
    }

    /**
     * Retrieve owner
     * 
     * @param columnName
     *            String
     * @param id
     *            Object
     * @return List of DataFields
     * 
     */
    @Override
    public EplVadfOwnerDo retrieveOwnerTest(String columnName, Object id) {

        java.util.List<EplVadfOwnerDo> vadfOwnerDo = eplVadfOwnerDao.retrieve(columnName, id);

        if (vadfOwnerDo == null || vadfOwnerDo.size() <= 0) {
            return null;
        }

        return vadfOwnerDo.get(0);

    }

    /**
     * Retrieve editable properties for the specified owner
     * 
     * @param owner
     *            EplVadfOwnerDO
     * @return List of DataFields
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability#retrieveDomain()
     */
    private Set<EplVadfEditablePropertyDo> retrieveEditableProperties(EplVadfOwnerDo owner) {

        Set<EplVadfEditablePropertyDo> allProps = new HashSet<EplVadfEditablePropertyDo>();
        java.util.List<EplVadfEditablePropertyDo> properties =
                eplVadfEditablePropertyDao.retrieve(EplVadfOwnerDo.EPL_VADF_OWNER, owner);

        for (EplVadfEditablePropertyDo data : properties) {
            EplVaDfDo def = eplVaDfDao.retrieve(data.getKey().getVadfIdFk());
            eplVaDfDao.refresh(def);
            data.setEplVaDf(def);
            allProps.add(data);
        }

        return allProps;

    }

    /**
     * Retrieve non list values for the specified owner
     * 
     * @param owner
     *            EplVadfOwnerDo
     * @return List of DataFields
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability#retrieveDomain()
     */
    private Set<EplVadfNonlistValueDo> retrieveNonListValues(EplVadfOwnerDo owner) {

        Set<EplVadfNonlistValueDo> allProps = new HashSet<EplVadfNonlistValueDo>();
        java.util.List<EplVadfNonlistValueDo> properties =
                eplVadfNonlistValueDao.retrieve(EplVadfOwnerDo.EPL_VADF_OWNER, owner);

        for (EplVadfNonlistValueDo data : properties) {
            EplVaDfDo def = eplVaDfDao.retrieve(data.getKey().getVadfIdFk());
            eplVaDfDao.refresh(def);
            data.setEplVaDf(def);
            allProps.add(data);
        }

        return allProps;

    }

    /**
     * retrieve multi text values for the specified owner
     * 
     * @param owner
     *            EplVadfOwnerDo
     * @return List of multi text Do
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability#retrieveDomain()
     */
    private Set<EplMultiTextDo> retrieveMulitTextValues(EplVadfOwnerDo owner) {

        Set<EplMultiTextDo> allProps = new HashSet<EplMultiTextDo>();
        java.util.List<EplMultiTextDo> properties = eplMultiTextDao.retrieve(EplVadfOwnerDo.EPL_VADF_OWNER, owner);

        for (EplMultiTextDo data : properties) {
            EplVaDfDo def = eplVaDfDao.retrieve(data.getKey().getVadfIdFk());
            eplVaDfDao.refresh(def);
            data.setEplVaDf(def);
            allProps.add(data);
        }

        return allProps;

    }

    /**
     * Retrieve all VadfLov Dos
     * 
     * @param columnName
     *            String
     * @param id
     *            Object
     * @return List of DataFields
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability#retrieveDomain()
     */
    @Override
    public List<EplVadfLovDo> retrieveLovTest(String columnName, Object id) {
        java.util.List<EplVadfLovDo> properties = eplVadfLovDao.retrieve(columnName, id);

        return properties;
    }

    /**
     * retrieve the specified VA data field DO
     * 
     * @param id
     *            long
     * @return EplVaDfDo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability#retrieveVaDf(java.lang.Long)
     */
    @Override
    public EplVaDfDo retrieveVaDf(Long id) {

        return (eplVaDfDao.retrieve(id));
    }

    /**
     * Retrieve all vadf associations for the specified owner
     * 
     * @param owner
     *            EplVadfOwnerDo
     * @return vadf associations
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability#retrieveDomain()
     */
    private Set<EplVadfAssocValueDo> retrieveProdAssocs(EplVadfOwnerDo owner) {

        Set<EplVadfAssocValueDo> allProps = new HashSet<EplVadfAssocValueDo>();
        java.util.List<EplVadfAssocValueDo> properties = eplVadfAssocValueDao.retrieve(EplVadfOwnerDo.EPL_VADF_OWNER, owner);

        for (EplVadfAssocValueDo data : properties) {
            EplVadfLovDo def = new EplVadfLovDo();
            EplVadfLovDoKey key = new EplVadfLovDoKey();
            key.setListValue(data.getKey().getListValue());
            key.setVadfIdFk(data.getKey().getVadfIdFk());
            def.setKey(key);

            EplVaDfDo vaDef = eplVaDfDao.retrieve(data.getKey().getVadfIdFk());
            eplVaDfDao.refresh(vaDef);
            def.setEplVaDf(vaDef);

            if (data.getEplVadfLov() != null) {
                def.setDefaultValue(data.getEplVadfLov().getDefaultValue());
            }

            data.setEplVadfLov(def);
            allProps.add(data);
        }

        return allProps;
    }

    /**
     * Retrieve all the vadf associations for the object
     * 
     * @param columnName
     *            String
     * @param id
     *            Object
     * @return List of vadf associations
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability#retrieveDomain()
     */
    @Override
    public List<EplVadfAssocValueDo> retrieveProdAssocsTest(String columnName, Object id) {
        java.util.List<EplVadfAssocValueDo> properties = eplVadfAssocValueDao.retrieve(columnName, id);

        return properties;
    }

    /**
     * set the VA data field DAO
     * 
     * @param eplVaDfDao
     *            eplVaDfDao property
     */
    public void setEplVaDfDao(EplVaDfDao eplVaDfDao) {
        this.eplVaDfDao = eplVaDfDao;
    }

    /**
     * Inserts Data objects corresponding to va df owners
     * 
     * @param ndc
     *            EplNdcDo
     * @param product
     *            EplProductDo item that needs to be referenced
     * @param orderableItem
     *            EplOrderableItemDo
     * @param dosageForm
     *            EplDosageFormDo
     * @param set
     *            a set
     * @param user
     *            {@link UserVo} performing the action
     * @return Set<EplVadfOwnerDo>
     * 
     */
    @Override
    public Set<EplVadfOwnerDo> insertEplVaDfOwners(EplNdcDo ndc, EplProductDo product, EplOrderableItemDo orderableItem,
                                                   EplDosageFormDo dosageForm, Set set, UserVo user) {
        if (set != null) {
            Iterator iterator = set.iterator();
            Set<EplVadfOwnerDo> insertedVaDfOwnerSet = new HashSet<EplVadfOwnerDo>();

            while (iterator.hasNext()) {
                EplVadfOwnerDo value = (EplVadfOwnerDo) iterator.next();

                if (dosageForm == null) {
                    if (ndc == null && orderableItem == null) {
                        value.setEplProduct(product);
                        value.setVadfOwnerId(product.getEplId());
                        value.setVadfOwnerType(EntityType.PRODUCT.name());

                    } else if (product == null && orderableItem == null) {
                        value.setEplNdc(ndc);
                        value.setVadfOwnerId(ndc.getEplId());
                        value.setVadfOwnerType(EntityType.NDC.name());
                    } else {
                        value.setEplOrderableItem(orderableItem);
                        value.setVadfOwnerId(orderableItem.getEplId());
                        value.setVadfOwnerType(EntityType.ORDERABLE_ITEM.name());
                    }
                } else {
                    value.setVadfOwnerId(dosageForm.getEplId());
                    value.setVadfOwnerType(EntityType.DOSAGE_FORM.name());
                }

                EplVadfOwnerDo addedDo = create(value, user);
                insertedVaDfOwnerSet.add(addedDo);
            }

            return insertedVaDfOwnerSet;
        }

        return null;
    }

    /**
     * Insert the given vadf owner.
     * 
     * @param vaDf
     *            EplVadfOwnerDo
     * @param user
     *            {@link UserVo} performing the action
     * @return EplVadfOwnerDo inserted EplVadfOwnerDo with new ID
     */
    private EplVadfOwnerDo create(EplVadfOwnerDo vaDf, UserVo user) {

        // calls set of editable, list and non list values
        EplVadfOwnerDo newEplVadfOwnerDo = eplVadfOwnerDao.insert(vaDf, user);

        // insert all the EplVadfOwnerDo child collections
        Set<EplVadfEditablePropertyDo> vaDfEditableProperties =
                insertVaDfEditableProperties(newEplVadfOwnerDo.getEplVadfEditableProperties(), user);
        Set<EplVadfNonlistValueDo> vaNonListValues = insertVaDfNonListValues(newEplVadfOwnerDo.getEplVadfNonlistValues(), user);
        Set<EplVadfAssocValueDo> vaListValues = insertVaDfProdNdcAssocValues(newEplVadfOwnerDo.getEplVadfAssocValues(), user);
        Set<EplMultiTextDo> vaTextValues = insertMulitTextValues(newEplVadfOwnerDo.getEplMultiTexts(), user);

        // set inserted EplVadfOwnerDo child collections
        newEplVadfOwnerDo.setEplVadfEditableProperties(vaDfEditableProperties);
        newEplVadfOwnerDo.setEplVadfNonlistValues(vaNonListValues);
        newEplVadfOwnerDo.setEplVadfAssocValues(vaListValues);
        newEplVadfOwnerDo.setEplMultiTexts(vaTextValues);

        return newEplVadfOwnerDo;
    }

    /**
     * Delete the given {@link DataFields} and the values contained within.
     * 
     * @param dataFields
     *            {@link DataFields} to delete
     */
    @Override
    public void delete(DataFields dataFields) {
        if (dataFields != null && NumberUtils.isNumber(dataFields.getVaDfOwnerId())) {
            Long id = Long.valueOf(dataFields.getVaDfOwnerId());
            eplMultiTextDao.delete(EplMultiTextDo.EPL_VA_DF_OWNER_ID, id);
            eplVadfNonlistValueDao.delete(EplVadfNonlistValueDo.EPL_VA_DF_OWNER_ID, id);
            eplVadfAssocValueDao.delete(EplVadfAssocValueDo.EPL_VA_DF_OWNER_ID, id);
            eplVadfEditablePropertyDao.delete(EplVadfEditablePropertyDo.EPL_VA_DF_OWNER_ID, id);
            eplVadfOwnerDao.delete(EplVadfOwnerDo.ID, id);
        }
    }

    /**
     * Inserts Data objects corresponding to va df owners for insertVaDfEditableProperties
     * 
     * @param set Set to insert
     * @param user The user performing the action
     * @return Set that was inserted
     * 
     */
    private Set<EplVadfEditablePropertyDo> insertVaDfEditableProperties(Set<EplVadfEditablePropertyDo> set, UserVo user) {
        Set<EplVadfEditablePropertyDo> insertedSet = new HashSet<EplVadfEditablePropertyDo>();

        if (set != null) {
            for (EplVadfEditablePropertyDo value : set) {
                insertedSet.add(eplVadfEditablePropertyDao.insert(value, user));
            }
        }

        return insertedSet;
    }

    /**
     * Inserts Data objects corresponding to va df owners for insertVaDfProdNdcAssocValues
     * 
     * @param set Set to insert
     * @param user The user performing the action
     * @return Set that was inserted
     * 
     * 
     */
    private Set<EplVadfAssocValueDo> insertVaDfProdNdcAssocValues(Set<EplVadfAssocValueDo> set, UserVo user) {
        Set<EplVadfAssocValueDo> insertedSet = new HashSet<EplVadfAssocValueDo>();

        if (set != null) {
            for (EplVadfAssocValueDo value : set) {
                insertedSet.add(eplVadfAssocValueDao.insert(value, user));
            }
        }

        return set;
    }

    /**
     * Inserts Data objects corresponding to va df owners into insertVaDfNonListValues
     * 
     * @param set
     *            Set
     * @param user
     *            {@link UserVo} performing the action
     * @return Set
     * 
     */
    private Set<EplVadfNonlistValueDo> insertVaDfNonListValues(Set<EplVadfNonlistValueDo> set, UserVo user) {
        Set<EplVadfNonlistValueDo> insertedSet = new HashSet<EplVadfNonlistValueDo>();

        if (set != null) {
            for (EplVadfNonlistValueDo value : set) {
                if (value.getKey().getVadfIdFk() == null) {
                    LOG.error("NULL FK: Could not insert non list values for " + value.getKey().toString());
                    throw new DomainException(DomainException.DATA_NOT_FOUND, "VadfIdFk");
                } else {
                    insertedSet.add(eplVadfNonlistValueDao.insert(value, user));
                }
            }
        }

        return set;
    }

    /**
     * Inserts Data objects corresponding to va df owners into insertMulitTextValues
     * 
     * @param set
     *            Set
     * @param user
     *            {@link UserVo} performing the action
     * @return Set
     * 
     */
    private Set<EplMultiTextDo> insertMulitTextValues(Set<EplMultiTextDo> set, UserVo user) {
        Set<EplMultiTextDo> insertedSet = new HashSet<EplMultiTextDo>();

        if (set != null) {
            for (EplMultiTextDo value : set) {
                LOG.debug("val: " + value.getKey().getVadfIdFk() + ":" + value.getKey().getVadfOwnerIdFk() + ":"
                                   + value.getKey().getText());
                insertedSet.add(eplMultiTextDao.insert(value, user));
            }
        }

        return set;
    }

    /**
     * updates the given ProductVo.
     * 
     * @param vaDfs
     *            set of EplVaDfOwnerDo from productDo
     * @param user
     *            {@link UserVo} performing the action
     */
    @Override
    public void update(Set<EplVadfOwnerDo> vaDfs, UserVo user) {
        for (EplVadfOwnerDo vadf : vaDfs) {

            eplVadfEditablePropertyDao.update(vadf.getEplVadfEditableProperties(), user);
            String key = "key.vadfOwnerIdFk";
            
            // delete all
            eplMultiTextDao.delete(key, vadf.getId());
            eplVadfNonlistValueDao.delete(key, vadf.getId());
            eplVadfAssocValueDao.delete(key, vadf.getId());

            // insert all
            // eplVadfAssocValueDao.delete("eplVadfOwner", vadf);
            eplMultiTextDao.insert(vadf.getEplMultiTexts(), user);
            eplVadfNonlistValueDao.insert(vadf.getEplVadfNonlistValues(), user);
            eplVadfAssocValueDao.insert(vadf.getEplVadfAssocValues(), user);
        }
    }

    /**
     * retrieve product associations
     * 
     * @param columnName
     *            String
     * @param value
     *            String
     * @return List of DataFields
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.capability.DataFieldsDomainCapability#retrieveDomain()
     */
    public List<EplVadfAssocValueDo> retrieveProdAssocsTest(String columnName, String value) {
        return (eplVadfAssocValueDao.retrieve(columnName, value));
    }

    /**
     * set the VA Data filed owner Dao
     * 
     * @param eplVadfOwnerDao
     *            eplVadfOwnerDao property
     */
    public void setEplVadfOwnerDao(EplVadfOwnerDao eplVadfOwnerDao) {
        this.eplVadfOwnerDao = eplVadfOwnerDao;
    }

    /**
     * set the VA Data filed editable property DAO
     * 
     * @param eplVadfEditablePropertyDao
     *            EplVadfEditablePropertyDao
     */
    public void setEplVadfEditablePropertyDao(EplVadfEditablePropertyDao eplVadfEditablePropertyDao) {
        this.eplVadfEditablePropertyDao = eplVadfEditablePropertyDao;
    }

    /**
     * set the VA data field non list value DAO
     * 
     * @param eplVadfNonlistValueDao
     *            EplVadfNonListValueDao
     */
    public void setEplVadfNonlistValueDao(EplVadfNonlistValueDao eplVadfNonlistValueDao) {
        this.eplVadfNonlistValueDao = eplVadfNonlistValueDao;
    }

    /**
     * set the VA data field association value DAO
     * 
     * @param eplVadfAssocValueDao
     *            EplVadfAssocValueDao
     */
    public void setEplVadfAssocValueDao(EplVadfAssocValueDao eplVadfAssocValueDao) {
        this.eplVadfAssocValueDao = eplVadfAssocValueDao;
    }

    /**
     * set the VA data field Lov DAO
     * 
     * @param eplVadfLovDao
     *            eplVadfLovDao property
     */
    public void setEplVadfLovDao(EplVadfLovDao eplVadfLovDao) {
        this.eplVadfLovDao = eplVadfLovDao;
    }

    /**
     * set the multi text DAO
     * 
     * @param eplMultiTextDao
     *            eplMultiTextDao property
     */
    public void setEplMultiTextDao(EplMultiTextDao eplMultiTextDao) {
        this.eplMultiTextDao = eplMultiTextDao;
    }

    /**
     * setDataFieldsConverter
     * @param dataFieldsConverter
     *            dataFieldsConverter property
     */
    public void setDataFieldsConverter(DataFieldsConverter dataFieldsConverter) {
        this.dataFieldsConverter = dataFieldsConverter;
    }
}
