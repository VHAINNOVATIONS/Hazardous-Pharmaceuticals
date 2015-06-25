/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.bean;


import gov.va.med.pharmacy.peps.common.vo.ProductVo;


/**
 * NewProductBean's brief summary
 * 
 * Details of NewProductBean's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class NewProductBean {

    private ProductVo product;

    /**
     * getProduct
     *
     * @return ProductVo
     */
    public ProductVo getProduct() {
        return product;
    }

    /**
     * setProduct
     *
     * @param product 
     */
    public void setProduct(ProductVo product) {
        this.product = product;
    }

    /**
     * clear
     *
     */
    public void clear() {
        product = null;
    }

}
