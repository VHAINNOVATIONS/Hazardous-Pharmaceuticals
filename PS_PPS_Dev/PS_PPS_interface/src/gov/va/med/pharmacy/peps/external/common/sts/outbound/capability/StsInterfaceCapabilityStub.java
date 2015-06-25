/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.sts.outbound.capability;


import java.util.ArrayList;
import java.util.List;

import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;


/**
 * StsCapabilityStub
 *
 */
public class StsInterfaceCapabilityStub implements StsInterfaceCapability {
   
    
    /**
     * StsCapabilityStub
     */
    public StsInterfaceCapabilityStub() {
        super();
    }
    
   
    /**
     * Returns a vuid for a ManagedItemVo of type GenericNameVo, IngredientVo, DrugClassVo, ProductVo, or
     * StandardMedicationRouteVo.
     * 
     * @param stdDomainItem GenericNameVo, IngredientVo, DrugClassVo, ProductVo, or StandardMedicationRouteVo
     * @return vuid The VUID that was generated
     */
    public String assignVuid(ManagedItemVo stdDomainItem) {
        return "1232";
    }

    /**
     * If an item has been previously assigned a vuid by a local, it can be updated to a national item after it has been
     * approved. If it is already a national item, no change will be made and normal response will be returned.
     * 
     * @param vuid vuid of managed item to set to national
     */
    public void setNational(String vuid) {
        
    }

    /**
     * Update a ManagedItemVo of type ProductVo.
     * 
     * @param stdDomainItem ManagedItemVo to update
     */
    public void update(ManagedItemVo stdDomainItem) {
        
    }

    /**
     * Activate a managed item.
     * 
     * @param item managed item
     */
    public void activate(ManagedItemVo item) {
        
    }

    /**
     * Inactivate an managed domain item.
     * 
     * @param item managed item
     */
    public void inactivate(ManagedItemVo item) {
        
    }

    /**
     * Update drug class parent for the drug class vuid.
     * 
     * @param vuid vuid of drug class
     * @param parentClassVuid vuid of parent of drug class
     */
    public void updateDrugClassParent(String vuid, String parentClassVuid) {
        
    }

    /**
     * Removes association of drug class to a parent.
     * 
     * @param vuid drug class vuid
     */
    public void removeDrugClassParent(String vuid) {
        
    }

    /**
     * getStsData.
     * @return List of StandardMedRouteVos
     */
    @Override
    public List<StandardMedRouteVo> getStsData() {
        List<StandardMedRouteVo> list = new ArrayList<StandardMedRouteVo>();
        
        String[] names = {"INTRAUTERINE", "INTRASYNOVIAL", "INTRABURSAL", "INTRA-ARTICULAR", "INTRAOCULAR",
            "BUCCAL", "OTIC", "SUBCUTANEOUS", "VAGINAL", "NASAL", 
            "IRRIGATION", "OPHTHALMIC", "URETHRAL", "EPIDURAL", "NEBULIZATION", 
            "INTRAVESICAL", "INTRAMUSCULAR", "INTRAPLEURAL", "TOPICAL", "INTRATHECAL", 
            "TRANSDERMAL", "INTRACAVERNOSAL", "NOT APPLICABLE", "DENTAL", "INHALATION",  
            "INTRALESIONAL", "INTRADERMAL", "INTRATRACHEAL", "INTRAVENOUS", "INTRAPERITONEAL", 
            "INTRA-ARTERIAL", "INTRACARDIAC", "SUBLINGUAL", "RECTAL", "INTRASPINAL", 
            "RETROBULBAR", "ORAL", "INFILTRATION", "INTRADUCTAL", "INTRATYMPANIC", 
            "INTRA-AMNIOTIC", "ENTERAL", "IONTOPHORESIS", "SUBCONJUNCTIVAL", "INTRAVITREAL", 
            "INTRACAVITARY", "INTRACAUDAL"};

        String[] vuids = {"4706350", "4706340", "4706339", "4706236", "4706242",
            "4706230", "4706256", "4706258", "4706263", "4706252", 
            "4706250", "4706254", "4706262", "4706232", "4706253", 
            "4706249", "4706241", "4706244", "4706260", "4706245", 
            "4706261", "4706238", "4706337", "4706231", "4706234", 
            "4706240", "4706239", "4706246", "4706248", "4706243", 
            "4706235", "4706237", "4706259", "4688679", "4706349", 
            "4706351", "4500642", "4706346", "4712291", "4712294", 
            "4712295", "4712338", "4712354", "4712290", "4706338", 
            "4706348", "4706347"};

        for (int i = 0; i < names.length; i++) {
            StandardMedRouteVo vo = new StandardMedRouteVo();
            vo.setValue(names[i]);
            vo.setVuid(vuids[i]);
            vo.setItemStatus(ItemStatus.ACTIVE);
            list.add(vo);
        }
        
        return list;
    }
    
}
