/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.utility;


/**
 * ComplexGenericStore holds multiple data for the Generic hashmap
 *
 */
public class ComplexGenericStore {
    private String eplId;
    private String ndfIen;

    /**
     * default constructor
     * @param id EPL_ID
     * @param ien The IEN of the item.
     */
    public ComplexGenericStore(String id, String ien) {
        eplId = id;
        ndfIen = ien;
    }
    
    /**
     * getEplId
     * @return eplId
     */
    public String getEplId() {
        return eplId;
    }
    
    /**
     * getNdfIen
     * @return ndfIen
     */
    public String getNdfIen() {
        return ndfIen;
    }
}
