/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;


/**
 * Act as a "Map" of {@link ManagedItemVo} to its Collection of {@link ItemModDifferenceVo}.
 * <p>
 * Used while performing a multi-edit of a List of {@link ManagedItemVo}. Struts/OGNL would have a difficult (though not
 * impossible) time of using the {@link ManagedItemVo} as a key, so to simplify things there we'll use this object in a
 * Collection.
 */
public class ItemModDifferenceVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Collection<ModDifferenceVo> differences;
    private ManagedItemVo item;

    /**
     * Default empty constructor.
     */
    public ItemModDifferenceVo() {
        super();
    }

    /**
     * Set the {@link ManagedItemVo} and Collection of {@link ItemModDifferenceVo}.
     * 
     * @param item {@link ManagedItemVo}
     * @param differences Collection of {@link ModDifferenceVo}
     */
    public ItemModDifferenceVo(ManagedItemVo item, Collection<ModDifferenceVo> differences) {
        this.item = item;
        this.differences = differences;
    }

    /**
     * setItem
     * 
     * @param item ValueObject
     */
    public void setItem(ManagedItemVo item) {
        this.item = item;
    }

    /**
     * getItem
     * 
     * @return ValueObject
     */
    public ManagedItemVo getItem() {
        return this.item;
    }

    /**
     * getDifferences
     * 
     * @return Collection of ModDifferenceVos
     */
    public Collection<ModDifferenceVo> getDifferences() {
        return differences;
    }

    /**
     * setDifferences
     * 
     * @param differences differences property
     */
    public void setDifferences(Collection<ModDifferenceVo> differences) {
        if (this.differences == null) {
            this.differences = new ArrayList<ModDifferenceVo>();
        }

        if (differences != null && !differences.isEmpty()) {
            this.differences.addAll(differences);
        }
    }
}
