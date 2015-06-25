/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


/**
 * ComplexIENStoree holds multiple data for the IEN hashmap
 *
 */
public class ComplexIENStore {
    private String eplId;
    private String ndfIen;

    /**
     * default constructor
     * @param id EPL_ID
     * @param ien The IEN of the item.
     */
    public ComplexIENStore(String id, String ien) {
        eplId = id;
        ndfIen = ien;
    }
    
    /**
     * getEplId for ComplexIENStore.
     * @return eplId
     */
    public String getEplId() {
        return eplId;
    }
    
    /**
     * getNdfIen for ComplexIENStore.
     * @return ndfIen
     */
    public String getNdfIen() {
        return ndfIen;
    }
}
