/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.sts.outbound.capability.impl;


import java.util.ArrayList;
import java.util.List;


import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.StandardMedRouteVo;
import gov.va.med.pharmacy.peps.external.common.sts.outbound.capability.StsInterfaceCapability;

import ct.webservice.sts.med.va.gov.CtService;
import ct.webservice.sts.med.va.gov.CtServiceLocator;
import ct.webservice.sts.med.va.gov.ValueSetContentsListTransfer;
import ct.webservice.sts.med.va.gov.ValueSetContentsTransfer;







/**
 * STS Web Service Capability to perform operations on standard managed domain items.
 */
public class StsInterfaceCapabilityImpl implements StsInterfaceCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StsInterfaceCapabilityImpl.class);
    private static final Long VUID_LIST_NUM = 4708487L; // this is the VUID for the standard med route list 

    
   // @WebServiceRef(wsdlLocation = "http://islvsswls2.fo-slc.med.va.gov:9205/sts.webservice/ctService?wsdl")
   // private CtService service;

    /**
     * The default constructor loads the properties for the location of the STS service and instantiates the sts client.
     */
    public StsInterfaceCapabilityImpl() {
        LOG.debug("StsInterfaceCapabilityImpl constructor");
    }

    /**
     * getStsData.
     * @return  List<StandardMedicationRouteVo> dataList
     */
    public List<StandardMedRouteVo> getStsData() {

        List<StandardMedRouteVo> dataList = new ArrayList<StandardMedRouteVo>();

        
        try {
            LOG.debug("Before service instantiation");
            CtService service = new CtServiceLocator();
            LOG.debug("Retrieving the port from the following service: " + service); 
          
            ct.webservice.sts.med.va.gov.Sts sts = service.getstsPort();
            Long l = new Long(VUID_LIST_NUM); 
    
            // sts.listValueSets(arg0, arg1, arg2, arg3)
            ValueSetContentsListTransfer response = sts.listValueSetContents(l, "current", null, null, null, null);
            LOG.debug("Total records is " + response.getTotalNumberOfRecords());
            ValueSetContentsTransfer[] contents = response.getValueSetContents();
            
            for (ValueSetContentsTransfer data : contents) {
                LOG.debug("Name is " + data.getName());
                LOG.debug("VUID is " + data.getVUID());
                LOG.debug("Status is " + data.getStatus());
                StandardMedRouteVo vo = new StandardMedRouteVo();

                vo.setVuid(String.valueOf(data.getVUID()));
                vo.setValue(data.getName());

                if (data.getStatus().equals("active")) {
                    vo.setItemStatus(ItemStatus.ACTIVE);
                } else {
                    vo.setItemStatus(ItemStatus.INACTIVE);
                }
                
                dataList.add(vo);
            }
            
        

        } catch (Exception e) {
            LOG.debug("STSInterface Exception " + e.getMessage());
        }

        
        
        
//        try {
//
//            LOG.debug(" Retrieving the port from the following service: "
//                + " http://islvsswls2.fo-slc.med.va.gov:9205/sts.webservice/ctService?wsdl");
//
//            if (service == null) {
//                service = new CtService();
//                LOG.error("After service");
//                LOG.error("service has been called." + service.toString());
//            } else {
//                LOG.error("service is already instantiated (by spring).");
//            }
//
//            Sts sts = service.getStsPort();
//            LOG.debug("getSTSPort called");
//            LOG.error("Sts is " + sts.toString());
//            ValueSetContentsListTransfer response = sts.listValueSetContents(VUID_LIST_NUM, "current", 
//        null, null, null, null);
//            LOG.debug("Total records is " + response.getTotalNumberOfRecords());
//            List<ValueSetContentsTransfer> contents = response.getValueSetContents();
//
//            for (ValueSetContentsTransfer data : contents) {
//                LOG.debug("Name is " + data.getName());
//                LOG.debug("VUID is " + data.getVUID());
//                LOG.debug("Status is " + data.getStatus());
//                StandardMedRouteVo vo = new StandardMedRouteVo();
//
//                vo.setVuid(String.valueOf(data.getVUID()));
//                vo.setValue(data.getName());
//
//                if (data.getStatus().equals("active")) {
//                    vo.setItemStatus(ItemStatus.ACTIVE);
//                } else {
//                    vo.setItemStatus(ItemStatus.INACTIVE);
//                }
//
//            }
//
//        } catch (Exception e) {
//            LOG.debug("Exception thrown was " + e.getMessage());
//        }

        return dataList;
    }
}
