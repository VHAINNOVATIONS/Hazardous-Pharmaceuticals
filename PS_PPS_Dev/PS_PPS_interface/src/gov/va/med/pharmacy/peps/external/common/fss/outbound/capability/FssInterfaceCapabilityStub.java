/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.fss.outbound.capability;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.NdcVo;



/**
 * StsCapabilityStub
 *
 */
public class FssInterfaceCapabilityStub implements FssInterfaceCapability {
   
    
    /**
     * FssInterfaceCapabilityStub
     */
    public FssInterfaceCapabilityStub() {
        super();
    }
    

    /**
     * getStsData.
     * @param lastRun Date of last run
     * @return List of StandardMedRouteVos
     */
    @Override
    public Map<String, String> getNdcsToUpdate(String lastRun) {

        Map<String, String> map = new HashMap<String, String>();
        
        map.put("00093-7155-98", "00093-7155-98");
        map.put("00310-0131-11", "00310-0131-11");
        map.put("00310-0131-10", "00310-0131-10");
        
        return map;
    }
    
    
    
    /**
     * getStsData.
     * @param ndcVo Ndc
     */
    @Override
    public void getFssData(NdcVo ndcVo) {

        if (ndcVo.getNdc().equals("00093-7155-98")) {
            ndcVo.setFssBig4Price(new Double("1.1"));
            ndcVo.setFssBpaAvail(true);
            ndcVo.setFssBpaPrice(new Double("2.2"));
            ndcVo.setFssCntNo("3");
            ndcVo.setFssFssEnd(new Date());
            ndcVo.setFssFssPrice(new Double("4.4"));
            ndcVo.setFssI("0");
            ndcVo.setFssNcPrice(new Double("6.6"));
            ndcVo.setFssPv(true);
            ndcVo.setFssVaPrice(new Double("7.7"));
        }

        if (ndcVo.getNdc().equals("00310-0131-11")) {
            ndcVo.setNdc("00310-0131-11");
            ndcVo.setFssBig4Price(new Double("11.1"));
            ndcVo.setFssBpaAvail(true);
            ndcVo.setFssBpaPrice(new Double("12.2"));
            ndcVo.setFssCntNo("13");
            ndcVo.setFssFssEnd(new Date());
            ndcVo.setFssFssPrice(new Double("14.4"));
            ndcVo.setFssI("1");
            ndcVo.setFssNcPrice(new Double("16.6"));
            ndcVo.setFssPv(true);
            ndcVo.setFssVaPrice(new Double("17.7"));
        }
        
        if (ndcVo.getNdc().equals("00310-0131-10")) {
            ndcVo.setNdc("00310-0131-10");
            ndcVo.setFssBig4Price(new Double("11.1"));
            ndcVo.setFssBpaAvail(true);
            ndcVo.setFssBpaPrice(new Double("12.2"));
            ndcVo.setFssCntNo("13");
            ndcVo.setFssFssEnd(new Date());
            ndcVo.setFssFssPrice(new Double("14.4"));
            ndcVo.setFssI("1");
            ndcVo.setFssNcPrice(new Double("16.6"));
            ndcVo.setFssPv(true);
            ndcVo.setFssVaPrice(new Double("17.7"));
        }
    }
    
}
