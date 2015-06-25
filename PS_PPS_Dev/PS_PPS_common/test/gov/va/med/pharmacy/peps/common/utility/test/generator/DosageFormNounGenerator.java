/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.DosageFormNounVo;


/**
 * generates dosage form noun
 */
public class DosageFormNounGenerator extends VoGenerator<DosageFormNounVo> {

    /**
     * Generate a list of DosageFormUnitVo
     * 
     * @return List of DosageFormNounVo
     * 
     */
    @Override
    protected List<DosageFormNounVo> generate() {
        List<DosageFormNounVo> dosageFormNouns = new ArrayList<DosageFormNounVo>();

        DosageFormNounVo dosageFormNoun1 = new DosageFormNounVo();
        dosageFormNoun1.setNoun("CAPSULE(S)");
        dosageFormNoun1.setOtherLanguageNoun("Capsulee");
        dosageFormNoun1.setPackages(new PossibleDosagesPackageGenerator().getList());
        
        DosageFormNounVo dosageFormNoun2 = new DosageFormNounVo();
        dosageFormNoun2.setNoun("LOZENGE(S)");
        dosageFormNoun2.setOtherLanguageNoun("TBD");
        dosageFormNoun2.setPackages(new PossibleDosagesPackageGenerator().getList());
        
        

        dosageFormNouns.add(dosageFormNoun1);
        dosageFormNouns.add(dosageFormNoun2);

        return dosageFormNouns;
    }
    
    /**
     * doInitialization
     */
    @Override
    protected void doInitialization() {
        
    }

}
