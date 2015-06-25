/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.bean;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;


/**
 * A bean to hold state while editing a ManagedItem
 */
public class EditManagedItemBean {

    /** item */
    private ManagedItemVo item;

    /** originalItem */
    private ManagedItemVo originalItem;

    /** differences */
    private Collection<ModDifferenceVo> differences;

    /** partialSave */
    private PartialSaveVo partialSave;

    /** mainRequest */
    private RequestVo mainRequest;

    /**
     * 
     * Description here
     *
     */
    public EditManagedItemBean() {
        super();
    }

    /**
     * 
     * getItem
     *
     * @return the modified item
     */
    public ManagedItemVo getItem() {
        return item;
    }

    /**
     * 
     * setItem
     *
     * @param item the modified ifem
     */
    public void setItem(ManagedItemVo item) {
        this.item = item;
    }

    /**
     * 
     * getOriginalItem
     *
     * @return originalIem
     */
    public ManagedItemVo getOriginalItem() {
        return originalItem;
    }

    /**
     * 
     * setOriginalItem
     *
     * @param originalItem the original item
     */
    public void setOriginalItem(ManagedItemVo originalItem) {
        this.originalItem = originalItem;
    }

    /**
     * 
     * getDifferences
     *
     * @return collection of ModDifferenceVos
     */
    public Collection<ModDifferenceVo> getDifferences() {
        return differences;
    }

    /**
     * 
     * setDifferences
     *
     * @param differences collection of ModDifferenceVos
     */
    public void setDifferences(Collection<ModDifferenceVo> differences) {
        this.differences = differences;
    }

    /**
     * 
     * getPartialSave
     *
     * @return the partial save
     */
    public PartialSaveVo getPartialSave() {
        return partialSave;
    }

    /**
     * 
     * setPartialSave
     *
     * @param partialSave the partial save
     */
    public void setPartialSave(PartialSaveVo partialSave) {
        this.partialSave = partialSave;
    }

    /**
     * getMainRequest
     * @return the mainRequest
     */
    public RequestVo getMainRequest() {
        return mainRequest;
    }

    /**
     * setMainRequest
     * @param mainRequest the mainRequest to set
     */
    public void setMainRequest(RequestVo mainRequest) {
        this.mainRequest = mainRequest;
    }

}
