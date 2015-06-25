/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;


/**
 * Data representing a RequestMessageVo. This RequestMessageVo wraps around the ProductVo, NdcVo, and Collection of
 * ModDifferenceVo
 * 
 * @param <T> for item type
 */
public class RequestMessageVo<T> extends ValueObject {
    private static final long serialVersionUID = 1L;

    private String id; // this is an enterprise id

    private RequestVo request;
    private T newItem;

    /**
     * empty constructor
     */
    public RequestMessageVo() {

    }

    /**
     * Constructor
     * 
     * @param newItem of type T
     * @param request of type RequestVo
     * @param user UserVo sending request
     */
    public RequestMessageVo(T newItem, RequestVo request, UserVo user) {
        this.newItem = newItem;
        this.request = request;

        setCreatedBy(user.getUsername());
        setCreatedDate(new Date());
    }

    /**
     * getId
     * 
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * setId
     * 
     * @param id of String type
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getRequest
     * 
     * @return RequestVo the request object
     */
    public RequestVo getRequest() {
        return request;
    }

    /**
     * setRequest
     * 
     * @param request of type RequestVo
     */
    public void setRequest(RequestVo request) {
        this.request = request;
    }

    /**
     * getNewItem
     * 
     * @return T for the item type
     */
    public T getNewItem() {
        return newItem;
    }

    /**
     * setNewItem
     * 
     * @param newItem of type T
     */
    public void setNewItem(T newItem) {
        this.newItem = newItem;
    }

}
