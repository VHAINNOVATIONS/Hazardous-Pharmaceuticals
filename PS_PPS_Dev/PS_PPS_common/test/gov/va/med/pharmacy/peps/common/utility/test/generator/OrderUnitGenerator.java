/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;


/**
 * Generate a list of Manufacturers for test cases
 */
public class OrderUnitGenerator extends VoGenerator<OrderUnitVo> {

    /**
     * Generate a list of Manufacturers for test cases
     * 
     * @return List of Manufacturers
     */
    protected List<OrderUnitVo> generate() {
        List<OrderUnitVo> testData = new ArrayList<OrderUnitVo>();
        OrderUnitVo orderUnitVo = new OrderUnitVo();
        orderUnitVo.setId("9992");
        orderUnitVo.setValue("BT");
        testData.add(orderUnitVo);
        
        OrderUnitVo secondOrderUnitVo = new OrderUnitVo();
        secondOrderUnitVo.setId("9991");
        secondOrderUnitVo.setValue("AM");
        testData.add(secondOrderUnitVo);

        return testData;

    }
    
    /**
     * doInitialization for the OrderUnitGenerator
     */
    @Override
    protected void doInitialization() {
        
    }
}
