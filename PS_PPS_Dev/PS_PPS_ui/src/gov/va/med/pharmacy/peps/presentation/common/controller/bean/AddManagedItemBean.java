/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.bean;


import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;


/**
 * AddManagedItemBean's brief summary
 * 
 * Details of AddManagedItemBean's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class AddManagedItemBean {

    private ManagedItemVo item;
    private FieldKey parentFieldKey;
    private FieldKey fieldKey;
    private int index;
    private boolean addingFromEntity = false;

    /**
     * Required default constructor
     *
     */
    public AddManagedItemBean() {

    }

    /**
     * getItem
     *
     * @return ManagedItemVo
     */
    public ManagedItemVo getItem() {
        return item;
    }

    /**
     * setItem
     *
     * @param item 
     */
    public void setItem(ManagedItemVo item) {
        this.item = item;
    }

    /**
     * getParentFieldKey
     * @return the parentFieldKey
     */
    public FieldKey getParentFieldKey() {
        return parentFieldKey;
    }

    /**
     * setParentFieldKey
     * @param parentFieldKey the parentFieldKey to set
     */
    public void setParentFieldKey(FieldKey parentFieldKey) {
        this.parentFieldKey = parentFieldKey;
    }

    /**
     * getFieldKey
     * @return the fieldKey
     */
    public FieldKey getFieldKey() {
        return fieldKey;
    }

    /**
     * setFieldKey
     * @param fieldKey the fieldKey to set
     */
    public void setFieldKey(FieldKey fieldKey) {
        this.fieldKey = fieldKey;
    }

    /**
     * getIndex
     * @return the index of group or multiselect datafield
     */
    public int getIndex() {
        return index;
    }

    /**
     * setIndex
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * setAddingFromEntity
     * @param addingFromEntity the addingFromEntity to set
     */
    public void setAddingFromEntity(boolean addingFromEntity) {
        this.addingFromEntity = addingFromEntity;
    }

    /**
     * isAddingFromEntity
     * @return the addingFromEntity
     */
    public boolean isAddingFromEntity() {
        return addingFromEntity;
    }

}
