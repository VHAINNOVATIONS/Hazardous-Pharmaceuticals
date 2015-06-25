/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.IntendedUseVo;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.SynonymVo;


/**
 * Generate a list of synonyms
 */
public class SynonymGenerator extends VoGenerator<SynonymVo> {
    private static final String I9991 = "9991";
    
    /**
     * Generate a list of synonyms
     * 
     * @return List of synonyms
     */
    protected List<SynonymVo> generate() {
        List<SynonymVo> synonyms = new ArrayList<SynonymVo>();

        IntendedUseVo inUse = new IntendedUseVo();
        inUse.setId(I9991);
        OrderUnitVo order = new OrderUnitVo();
        order.setId(I9991);
        SynonymVo syn = new SynonymVo();
        syn.setSynonymName("tradeName");
        syn.setNdcCode("11111-2222-33");
        syn.setSynonymIntendedUse(inUse);
        syn.setSynonymOrderUnit(order);

        synonyms.add(syn);
        
        return synonyms;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }
    
    /**
     * Generates a synonym with the specified synonym name.
     * 
     * @param synonymName name
     * @return synonym
     */
    public SynonymVo generate(String synonymName) {
        IntendedUseVo inUse = new IntendedUseVo();
        inUse.setId(I9991);
        OrderUnitVo order = new OrderUnitVo();
        order.setId(I9991);
        SynonymVo syn = new SynonymVo();
        syn.setSynonymName(synonymName);
        syn.setNdcCode("ndcCode");
        syn.setSynonymIntendedUse(inUse);
        syn.setSynonymOrderUnit(order);

        return syn;
        
    }
}
