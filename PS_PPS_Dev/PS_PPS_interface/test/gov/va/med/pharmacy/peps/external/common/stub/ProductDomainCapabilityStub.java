/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.stub;


import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.utility.test.generator.ProductGenerator;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.external.common.callback.ProductDomainCapabilityCallback;


/**
 * Product domain callback stub.
 */
public class ProductDomainCapabilityStub implements ProductDomainCapabilityCallback {

    /** FOUND */
    public static boolean FOUND = true;

    /**
     * Generate random product.
     * 
     * @param vuid vuid
     * @return ProductVo
     * @throws ItemNotFoundException exception
     */
    public ProductVo retrieveByVuId(String vuid) throws ItemNotFoundException {
        if (!FOUND) {
            throw new ItemNotFoundException(ItemNotFoundException.ITEM_NOT_FOUND);
        }

        ProductVo product = new ProductGenerator().getRandom();
        product.setVuid(vuid);
        product.setGcnSequenceNumber("006561");

        return product;
    }

}
