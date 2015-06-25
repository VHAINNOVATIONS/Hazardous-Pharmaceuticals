/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.callback;


import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;


/**
 * Exposes selected product domain callback methods to the interface project.
 */
public interface ProductDomainCapabilityCallback {

    /**
     * Retrieve the ProductVo with the given vuId.
     * 
     * @param vuid vuid
     * @return ProductVo
     * @throws ItemNotFoundException ItemNotFoundException
     */
    ProductVo retrieveByVuId(String vuid) throws ItemNotFoundException;
}

