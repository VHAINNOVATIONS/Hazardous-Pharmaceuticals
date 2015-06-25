/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.IngredientVo;


/**
 * This class is used to generate an NngredientVo
 */
public class IngredientGenerator extends VoGenerator<IngredientVo> {

    private static final int I_20 = 20;
    private static final int I_64 = 64;

    /**
     * generate
     * 
     * @return A list of IngredientVos
     * 
     * @see gov.va.med.pharmacy.peps.common.utility.test.generator.VoGenerator#generate()
     */
    protected List<IngredientVo> generate() {

        List<IngredientVo> vos = new ArrayList<IngredientVo>();

        for (int i = 0; i < I_5; i++) {
            vos.add(generateVo());
        }

        return vos;

    }

    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
    
    /**
     * Generate an IngredientVo
     * 
     * @return IngredientVo
     */
    private IngredientVo generateVo() {

        IngredientVo vo = new IngredientVo();

        vo.setValue(RandomGenerator.nextAlphabetic(I_64));
        vo.setVuid(RandomGenerator.nextAlphabetic(I_20));

        return vo;

    }
}
