/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Indicates originating source of Product/NDC data.
 */
public enum NdcSourceType {
    
    /**
     * VA
     */
    VA,
    
    /**
     * COTS
     */
    COTS,
    
    /**
     * FDA
     */
    FDA;
    
    /**
     * Test to see if this instance of {@link NdcSourceType} is equal to the given {@link NdcSourceType}.
     * 
     * @param productDataSourceType {@link NdcSourceType} to test equality with
     * @return true on match
     */
    public boolean is(NdcSourceType productDataSourceType) {
        if (productDataSourceType == null) {
            return false;
        }

        if (this.equals(productDataSourceType)) {
            return true;
        }

        return false;
    }
}
