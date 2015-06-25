/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Extends {@link ArrayList} and adds start index, full list size, page size, and sorting attributes.
 * <p>
 * Used for paging large lists from the database for performance reasons, in particular lists of "child" items (e.g., NDCs on
 * a Product) and search results.
 * 
 * @param <T> Type of elements in the list
 */
public class PaginatedList<T> extends ArrayList<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int fullSize;
    private FieldKey sortedFieldKey;
    private SortOrder sortOrder;
    private int startIndex;
    private int pageSize;

    /**
     * Empty constructor.
     * <p>
     * Code using this constructor should ensure all attributes are set!
     * 
     * @see #setFullSize(int)
     * @see #setSortedFieldKey(FieldKey)
     * @see #setSortOrder(SortOrder)
     * @see #setStartIndex(int)
     * @see #setPageSize(int)
     */
    public PaginatedList() {
        super();
    }

    /**
     * Populate the list with the given {@link Collection}.
     * <p>
     * Code using this constructor should ensure all attributes are set!
     * 
     * @param collection to populate list with
     * 
     * @see #setFullSize(int)
     * @see #setSortedFieldKey(FieldKey)
     * @see #setSortOrder(SortOrder)
     * @see #setStartIndex(int)
     * @see #setPageSize(int)
     */
    public PaginatedList(Collection<? extends T> collection) {
        super(collection);
    }

    /**
     * Set the initial capacity.
     * <p>
     * Code using this constructor should ensure all attributes are set!
     * 
     * @param initialCapacity initial capacity to send to {@link ArrayList}
     * 
     * @see #setFullSize(int)
     * @see #setSortedFieldKey(FieldKey)
     * @see #setSortOrder(SortOrder)
     * @see #setStartIndex(int)
     * @see #setPageSize(int)
     */
    public PaginatedList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Set the full size, sorted {@link FieldKey}, {@link SortOrder}, start index, and page size attributes.
     * 
     * @param collection the Collection from which to populate this list
     * @param fullSize integer full size of list in database
     * @param sortedFieldKey FieldKey sorted
     * @param sortOrder {@link SortOrder} ascending or descending
     * @param startIndex integer index of full list at which this partial list starts
     * @param pageSize integer size of an individual page
     * 
     * @see #setFullSize(int)
     * @see #setSortedFieldKey(FieldKey)
     * @see #setSortOrder(SortOrder)
     * @see #setStartIndex(int)
     * @see #setPageSize(int)
     */
    public PaginatedList(Collection<? extends T> collection, int fullSize, FieldKey sortedFieldKey, SortOrder sortOrder,
                         int startIndex, int pageSize) {
        addAll(collection);
        
        this.fullSize = fullSize;
        this.sortedFieldKey = sortedFieldKey;
        this.sortOrder = sortOrder;
        this.startIndex = startIndex;
        this.pageSize = pageSize;
    }

    /**
     * A {@link PaginatedList} is the full list if the {@link #getFullSize()} is equal to the {@link #size()}.
     * 
     * @return boolean true if this {@link PaginatedList} actually represents the full list
     */
    public boolean isFullList() {
        return getFullSize() == size();
    }

    /**
     * A {@link PaginatedList} is a partial list of the {@link #getFullSize()} does not equal the {@link #size()}.
     * 
     * @return boolean true if this {@link PaginatedList} does not represent the full list
     */
    public boolean isPartialList() {
        return getFullSize() != size();
    }

    /**
     * A {@link PaginatedList} has a "next page" available if the {@link #getFullSize()} is greater than the sum of
     * {@link #getStartIndex()} and the {@link #getPageSize()}.
     * 
     * @return boolean true if this {@link PaginatedList} has a "next page"
     */
    public boolean hasNextPage() {
        return getFullSize() > getStartIndex() + getPageSize();
    }

    /**
     * A {@link PaginatedList} has a "previous page" available if the difference between the {@link #getStartIndex()} and the
     * {@link #getPageSize()} is greater than zero.
     * 
     * @return boolean true if this {@link PaginatedList} has a "previous page"
     */
    public boolean hasPreviousPage() {
        return getStartIndex() - getPageSize() > 0;
    }

    /**
     * Returns the current "page" of the full list this {@link PaginatedList} represents.
     * <p>
     * Calculated by dividing the {@link #getStartIndex()} by the {@link #getPageSize()} and adding 1.
     * <p>
     * Note that the first page has a number of 1 as opposed to Java arrays and collections whose first index is 0.
     * <p>
     * For example, if the current start index was 20 and the page size were 10, then 20 divided by 10 is 2, plus 1 is 3. In
     * this example, the {@link PaginatedList} represents the third page. The first page has a start index of 0, the second
     * 10, and the third 20.
     * 
     * @return integer representing the current "page" this {@link PaginatedList} represents of the full list
     */
    public int getPage() {
        return (getStartIndex() / getPageSize()) + 1;
    }

    /**
     * Full size of list of all rows in the database.
     * 
     * @return fullSize property
     */
    public int getFullSize() {
        return fullSize;
    }

    /**
     * Full size of list of all rows in the database.
     * 
     * @param fullSize fullSize property
     */
    public void setFullSize(int fullSize) {
        this.fullSize = fullSize;
    }

    /**
     * FieldKey representing the field on the stored element that is sorted.
     * 
     * @return sortedFieldKey property
     */
    public FieldKey getSortedFieldKey() {
        return sortedFieldKey;
    }

    /**
     * FieldKey representing the field on the stored element that is sorted.
     * 
     * @param sortedFieldKey sortedFieldKey property
     */
    public void setSortedFieldKey(FieldKey sortedFieldKey) {
        this.sortedFieldKey = sortedFieldKey;
    }

    /**
     * Order in which the list is sorted.
     * 
     * @return sortOrder property
     */
    public SortOrder getSortOrder() {
        return sortOrder;
    }

    /**
     * Order in which the list is sorted.
     * 
     * @param sortOrder sortOrder property
     */
    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Convenience method to retrieve if this list is in ascending order.
     * 
     * @return boolean true if in ascending order
     * 
     * @see SortOrder#isAscending()
     */
    public boolean isAscendingOrder() {
        return getSortOrder() == null || getSortOrder().isAscending();
    }

    /**
     * Convenience method to retrieve if this list is in descending order.
     * 
     * @return boolean true if in descending order
     * 
     * @see SortOrder#isDescending()
     */
    public boolean isDescendingOrder() {
        return getSortOrder() != null && getSortOrder().isDescending();
    }

    /**
     * Index within the full list at which this partial paged list begins.
     * <p>
     * For example, if the full list size where 100, the list were paged at 10 rows per page, and this was the second page,
     * then this value would be 10.
     * 
     * @return startIndex property
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * Index within the full list at which this partial paged list begins.
     * <p>
     * For example, if the full list size where 100, the list were paged at 10 rows per page, and this was the second page,
     * then this value would be 10.
     * 
     * @param startIndex startIndex property
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * The number of elements displayed per page of the full list.
     * <p>
     * Note that the page size (the number of rows returned per page) may not be equal to the current list size! An
     * inequality could happen if there were fewer total elements returned by the query than the page size (i.e., the "full
     * size" is smaller than the page size) or if the last page contains fewer results than the page size.
     * 
     * @return pageSize property
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * The number of elements displayed per page of the full list.
     * <p>
     * Note that the page size (the number of rows returned per page) may not be equal to the current list size! An
     * inequality could happen if there were fewer total elements returned by the query than the page size (i.e., the "full
     * size" is smaller than the page size) or if the last page contains fewer results than the page size.
     * 
     * @param pageSize pageSize property
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
