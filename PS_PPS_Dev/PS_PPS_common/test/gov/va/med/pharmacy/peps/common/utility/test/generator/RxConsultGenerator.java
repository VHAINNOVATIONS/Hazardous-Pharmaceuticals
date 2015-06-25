/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RxConsultType;
import gov.va.med.pharmacy.peps.common.vo.RxConsultVo;


/**
 * Generate {@link RxConsultVo}
 */
public class RxConsultGenerator extends VoGenerator<RxConsultVo> {

    /**
     * Create a List of {@link RxConsultVo}
     * 
     * @return List of {@link RxConsultVo}
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    protected List<RxConsultVo> generate() {

        List<RxConsultVo> testData = new ArrayList<RxConsultVo>();
        RxConsultVo vo = new RxConsultVo();
        vo.setValue("NAME1");
        vo.setConsultText("TEXT1");
        testData.add(vo);

        vo = new RxConsultVo();
        vo.setValue("NAME2");
        vo.setConsultText("TEXT2");
        testData.add(vo);

        return testData;

    }

    /**
     * Create a Local Warning Label.
     * 
     * @return Local warning label
     */
    public RxConsultVo generateLocal() {
        RxConsultVo vo = new RxConsultVo();
        vo.setConsultText("Do not take with alcohol.");
        vo.setItemStatus(ItemStatus.ACTIVE);
        vo.setRxConsultType(RxConsultType.LOCAL);
        vo.setSpanishTranslation("No toman alcohole.");
        vo.setValue("Local Name");

        return vo;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }

    /**
     * Create a Local Warning Label.
     * 
     * @return Local warning label
     */
    public RxConsultVo generateNational() {
        RxConsultVo vo = new RxConsultVo();
        vo.setConsultText("Do not take with milk.");
        vo.setItemStatus(ItemStatus.ACTIVE);
        vo.setRxConsultType(RxConsultType.NATIONAL);
        vo.setSpanishTranslation("No toman leche.");
        vo.setValue("National Name");

        return vo;
    }
}
