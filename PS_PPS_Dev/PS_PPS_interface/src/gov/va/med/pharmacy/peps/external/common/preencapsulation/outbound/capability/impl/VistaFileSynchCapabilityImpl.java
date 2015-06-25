/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.impl;


import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.utility.PropertyUtility;
import gov.va.med.pharmacy.peps.common.vo.DispenseUnitVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugClassVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedDataVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ManufacturerVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.callback.InterfaceCounterDomainCapabilityCallback;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.document.SyncResponseDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.utility.VistALinkResponseInfo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.VistaFileSynchCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.DosageFormSyncRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.DrugClassSyncRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.DrugIngredientSyncRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.DrugUnitSyncRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.ManufacturerSyncRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.NdcSyncRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.NdfDomainDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.OrderableItemDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.PackageTypeSyncRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.PdmDomainDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.ProductItemDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.VaDispenseUnitSyncRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.VaGenericNameSyncRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.document.VaProductSyncRequestDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.utility.VistaLinkConnectionUtility;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.domain.NdfDomainConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.domain.PdmDomainConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.DosageFormConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.DrugClassConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.DrugIngredientConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.DrugUnitConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.ManufacturerConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.NdcConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.OrderableItemConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.PackageTypeConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.ProductItemConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.VaDispenseUnitConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.VaGenericNameConverter;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.item.VaProductConverter;
import gov.va.med.pharmacy.peps.external.common.vo.DomainItem;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.sync.syncresponse.SyncResponse;
import gov.va.med.pharmacy.peps.external.common.vo.inbound.sync.syncresponse.SyncResponseStatusType;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemStatus;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.ndf.NdfDomain;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.domain.pdm.PdmDomain;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.orderableitem.OrderableItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.item.productitem.ProductItem;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.dosageformsyncrequest.DosageFormSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugclasssyncrequest.DrugClassSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugingredientsyncrequest.DrugIngredientSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.drugunitsyncrequest.DrugUnitSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.manufacturersyncrequest.ManufacturerSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.ndcsyncrequest.NdcSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.packagetypesyncrequest.PackageTypeSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vadispenseunitsyncrequest.VaDispenseUnitSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vagenericnamesyncrequest.VaGenericNameSyncRequest;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.sync.vaproductsyncrequest.VaProductSyncRequest;
import gov.va.med.vistalink.rpc.RpcResponse;


/**
 * Sends updates to VistaLink
 */
public class VistaFileSynchCapabilityImpl implements VistaFileSynchCapability {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
            .getLogger(VistaFileSynchCapabilityImpl.class);

    private String sendItemRpcContext;
    private String sendItemRpcName;
    private VistaLinkConnectionUtility vistaLinkConnectionUtility;
    private EnvironmentUtility environmentUtility;
    private InterfaceCounterDomainCapabilityCallback interfaceCounter;

    private Boolean sendToVistAFlag = true;

    /**
     * Constructor
     */
    public VistaFileSynchCapabilityImpl() {
        try {
            Properties properties = PropertyUtility.loadProperties(VistaFileSynchCapabilityImpl.class);
            sendItemRpcContext = properties.getProperty("sendItemRpcContext");
            sendItemRpcName = properties.getProperty("sendItemRpc");
        } catch (IOException e) {
            LOG.error("unable to load properties file", e);
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        }
    }

    /**
     * Transforms a ManagedItem into an XML document and sends it to VistA.
     * 
     * @param item
     *            ManagedItem to send to VistA
     * @param differences
     *            Differences between the old and new value object
     * @param user
     *            current UserVo
     * @param action
     *            Approved, Pending, etc.
     * @param isLocal
     *            true if local environment
     * @param okToSend
     *            true if VistA Communications turned on
     * @param catchingUp
     *            ?
     * @throws ValidationException exception
     * @return Boolean
     */
    @Override
    public Boolean sendItemToVista(ManagedItemVo item, Collection<Difference> differences, UserVo user, ItemAction action,
                                boolean isLocal, boolean okToSend, boolean catchingUp) throws ValidationException {

        Map<FieldKey, Difference> setDifference = toSetDifference(differences);

        Boolean shouldHaveSent = false;

        if (item instanceof NdcVo && sendToVistAFlag) { // NDC Item
            NdcVo ndc = (NdcVo) item;
            shouldHaveSent = sendNdcItem(ndc, setDifference, toStatus(item.getRequestItemStatus()), action, user, okToSend,
                catchingUp);

        } else if (item instanceof ManufacturerVo && sendToVistAFlag) { // N
            ManufacturerVo manuf = (ManufacturerVo) item;
            shouldHaveSent = sendManufacturerItem(manuf, setDifference, toStatus(item.getRequestItemStatus()),
                action, user, isLocal, okToSend, catchingUp);

        } else if (item instanceof PackageTypeVo && sendToVistAFlag) { // N
            PackageTypeVo ptVo = (PackageTypeVo) item;
            shouldHaveSent = sendPackageTypeItem(ptVo, setDifference, toStatus(item.getRequestItemStatus()),
                action, user, isLocal, okToSend, catchingUp);

        } else if (item instanceof OrderableItemVo && sendToVistAFlag) { // Orderable Item
            OrderableItemVo oi = (OrderableItemVo) item;
            shouldHaveSent = sendOrderableItem(oi, setDifference, toStatus(item.getRequestItemStatus()), action, user, isLocal);

        } else if (item instanceof ProductVo && sendToVistAFlag) { // Product Item
            ProductVo product = (ProductVo) item;
            shouldHaveSent = sendVaProductItem(product, setDifference, toStatus(item.getRequestItemStatus()),
                action, user, isLocal, okToSend, catchingUp);

        } else if (item instanceof ManagedDataVo && sendToVistAFlag) { // Domain
            ManagedDataVo data = (ManagedDataVo) item;

            if (data instanceof DrugUnitVo) {

                DomainItem[] items = new DomainItem[1];
                items[0] = new DomainItem(data, action, setDifference);
                shouldHaveSent = sendDrugUnitItem(items, user, okToSend, catchingUp);

            } else if (data instanceof DispenseUnitVo) {

                DomainItem[] items = new DomainItem[1];
                items[0] = new DomainItem(data, action, setDifference);
                shouldHaveSent = sendVaDispenseUnitItem(items, user, okToSend, catchingUp);

            } else if (data instanceof GenericNameVo && sendToVistAFlag) {

                DomainItem[] items = new DomainItem[1];
                items[0] = new DomainItem(data, action, setDifference);
                shouldHaveSent = sendVaGenericNameItem(items, user, okToSend, catchingUp);

            } else if (data instanceof DosageFormVo && sendToVistAFlag) {

                DomainItem[] items = new DomainItem[1];
                items[0] = new DomainItem(data, action, setDifference);
                shouldHaveSent = sendDosageFormItem(items, user, okToSend, catchingUp);

            } else if (data instanceof DrugClassVo && sendToVistAFlag) {

                DomainItem[] items = new DomainItem[1];
                items[0] = new DomainItem(data, action, setDifference);
                shouldHaveSent = sendDrugClassItem(items, user, okToSend, catchingUp);

            } else if (data instanceof IngredientVo && sendToVistAFlag) {

                DomainItem[] items = new DomainItem[1];
                items[0] = new DomainItem(data, action, setDifference);
                shouldHaveSent = sendDrugIngredientItem(items, user, okToSend, catchingUp);

//  No default send in Version 1.0
//            } else {
//
//                DomainItem[] items = new DomainItem[1];
//                items[0] = new DomainItem(data, action, setDifference);
//
//                if (data.isNdf()) {
//                    sendNdfDomain(items, user);
//                } else if (isLocal) {
//                    sendPdmDomain(items, user);
//                }
            }
        }

        return shouldHaveSent;
    }

    /**
     * Transforms a new ManagedItem into an XML document and sends it to VistA.
     * 
     * @param item
     *            ManagedItem to send to VistA
     * @param user current UserVo
     * @param okToSend
     *            true if VistA Communications turned on
     * @param catchingUp
     *            ?
     * @throws ValidationException exception
     * @return Boolean
     */
    public Boolean sendNewItemToVista(ManagedItemVo item, UserVo user, boolean okToSend, boolean catchingUp)
        throws ValidationException {
        Boolean shouldHaveSent = sendItemToVista(item, null, user, ItemAction.ADD, environmentUtility.isLocal(), okToSend,
            catchingUp);

        return shouldHaveSent;
    }

    /**
     * Transforms a modified ManagedItem into an XML document and sends it to
     * VistA.
     * 
     * @param item
     *            ManagedItem to send to VistA
     * @param differences
     *            Differences between the old and new value object
     * @param user
     *            current UserVo
     * @param okToSend
     *            true if VistA Communications turned on    
     * @param catchingUp
     *            ?
     * @throws ValidationException exception
     * 
     * @return Boolean 
     */
    public Boolean sendModifiedItemToVista(ManagedItemVo item, Collection<Difference> differences, UserVo user,
        boolean okToSend, boolean catchingUp) throws ValidationException {
        Boolean shouldHaveSent = sendItemToVista(item, differences, user, ItemAction.MODIFY, environmentUtility.isLocal(),
            okToSend, catchingUp);

        return shouldHaveSent;

    }

    /**
     * Sets the VistaLinkConnectionUtility.
     * 
     * @param connection
     *            Class that will be used to communicate with VistA.
     */
    public void setVistaLinkConnectionUtility(VistaLinkConnectionUtility connection) {
        this.vistaLinkConnectionUtility = connection;
    }

    /**
     * sets the Environment Property
     * 
     * @param environmentUtility
     *            environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

    /**
     * Callback.
     * 
     * @param interfaceCounterIn
     *            counter
     */
    public void setInterfaceCounterDomainCapability(InterfaceCounterDomainCapabilityCallback interfaceCounterIn) {
        this.interfaceCounter = interfaceCounterIn;
    }

    /**
     * Convert to a hashed set of Differences.
     * 
     * @param differences
     *            old/new value differences
     * @return set difference
     */
    private Map<FieldKey, Difference> toSetDifference(Collection<Difference> differences) {
        Map<FieldKey, Difference> setDifference = new HashMap<FieldKey, Difference>();

        if (differences != null) { // null for 'add' operations
            for (Difference diff : differences) {
                setDifference.put(diff.getFieldKey(), diff);
            }
        }

        return setDifference;
    }

    /**
     * Sends an NDC Item to VistA.
     * 
     * @param ndcItem
     *            An NDC Item.
     * @param differences
     *            old/new value differences
     * @param status
     *            item status
     * @param action
     *            item action
     * @param user
     *            The user making this call.
     * @param okToSend
     *            Boolean indicating whether communications are turned on with VistA or not.
     * @param catchingUp
     *            Boolean indicating that theh queue is being sent to VistA
     * @return shouldHaveSent
     *            true if a message would have been sent, but communications were turned off         
     * @throws ValidationException ValidationException
     */
    private Boolean sendNdcItem(NdcVo ndcItem, Map<FieldKey, Difference> differences, ItemStatus status, ItemAction action,
                             UserVo user, boolean okToSend, boolean catchingUp) throws ValidationException {

        Boolean shouldHaveSent = false;
        LOG.debug("sending NDC Item: " + ndcItem.getNdc());
        
        if (StringUtils.isNotBlank(ndcItem.getNdc())) {
            LOG.debug("sending NDC Item: " + ndcItem.getNdc() + "\n");
          
            if (okToSend) {
           
                NdcSyncRequest convertedItem = NdcConverter.toNdcSyncRequest(ndcItem, differences, action);
  
                if (convertedItem == null || StringUtils.isBlank(convertedItem.getNdcName())) {
                    LOG.debug("Did not send NDC Item: " + ndcItem.getValue() + ", " + action.toString() + " - "
                        + "Since it is not complete or there were no changes to be synced with VistA\n");
                } else {
                        
    
                    String xml;
    
                    try {
                        xml = NdcSyncRequestDocument.instance().marshal(convertedItem);
                        LOG.debug("sending NDC Item: " + xml + "\n");
                    } catch (Exception e) {
                        String eText = "Error while attempting to marshal NDC "
                            + convertedItem.getNdcName();
                        LOG.debug(eText);
                        String msg = e.getMessage();
    
                        if (e.getCause() != null) {
                            if (e.getCause().getCause() != null) {
                                msg = msg + " : " + e.getCause().getCause().getLocalizedMessage();
                            }
                        }
    
                        throw new ValidationException(ValidationException.VISTA_XML_MARSHALLING,
                            "NDC", convertedItem.getNdcName(), msg);
                    }
    
                    List<String> params = new LinkedList<String>();
                    params.add(xml);
    
                    VistALinkResponseInfo inf = sendItem(params, user);
    
                    if (inf.isError()) {
                        LOG.debug(inf.getErrorResponseString());
                    } else {
                        LOG.debug("Sent NDC Item to VistA: " + convertedItem.getNdcName() + ", "
                                  + convertedItem.getInactivationDate()
                                  + ", " + convertedItem.getRequestType() + "\n");
    
                        // Set the IEN to the returned IEN
                        NdcVo ndcVo = ndcItem;
    
                        if (ItemAction.ADD.equals(action) && (ndcVo.getNdcIen() == null)) {
                            ndcVo.setNdcIen(inf.getIen());
                        }
    
                    }
                }
            } else {
                if (catchingUp) {
                    String eText = "Attempt to send sync message to VistA for NDC " + ndcItem.getNdc()
                        + " while sending queue to VistA";
                    LOG.debug(eText);
                    throw new ValidationException(ValidationException.VISTA_COMM_TURNED_OFF);
                } else {
                    shouldHaveSent = true;
                }
        
            }
        } else {
            LOG.debug("Did not send NDC Item: " + ndcItem.getValue() + ", " + action.toString() + " - "
                      + "Since it is not complete or there were no changes to be synced with VistA\n");
        }

  

        return shouldHaveSent;
    }

    /**
     * Sends an Manufacturer Item to VistA.
     * 
     * @param manufacturerItem A Manufacturer Item.
     * @param differences old/new value differences
     * @param status item status
     * @param action item action
     * @param user The user making this call.
     * @param environment Currently unused. Would be local or national
     * @param okToSend Boolean indicating whether communications are turned on with VistA or not.
     * @param catchingUp Boolean indicating that theh queue is being sent to VistA
     * @return shouldHaveSent true if a message would have been sent, but communications were turned off         
     * @throws ValidationException ValidationException
     */
    private Boolean sendManufacturerItem(ManufacturerVo manufacturerItem, Map<FieldKey, Difference> differences,
        ItemStatus status, ItemAction action, UserVo user, Boolean environment, boolean okToSend, boolean catchingUp)
        throws ValidationException {

        ManufacturerSyncRequest convertedItem =
                ManufacturerConverter.toManufacturerSyncRequest(manufacturerItem, differences, action);
        Boolean shouldHaveSent = false;

        if (StringUtils.isNotBlank(convertedItem.getManufacturerName())) {

            if (okToSend) {
                LOG.debug("sending Manufacturer Item: " + convertedItem.getManufacturerName() + "\n");

                String xml;

                try {
                    xml = ManufacturerSyncRequestDocument.instance().marshal(convertedItem);
                } catch (Exception e) {
                    String eText = "Error while attempting to marshal Manufacturer "
                        + convertedItem.getManufacturerName();
                    LOG.debug(eText);
                    String msg = e.getMessage();

                    if (e.getCause() != null) {
                        if (e.getCause().getCause() != null) {
                            msg = msg + " : " + e.getCause().getCause().getLocalizedMessage();
                        }
                    }

                    throw new ValidationException(ValidationException.VISTA_XML_MARSHALLING,
                        "Manufacturer", convertedItem.getManufacturerName(), msg);
                }

                List<String> params = new LinkedList<String>();
                params.add(xml);

                VistALinkResponseInfo inf = sendItem(params, user);

                if (inf.isError()) {
                    LOG.debug(inf.getErrorResponseString());
                } else {
                    LOG.debug("Sent Manufacturer Item to VistA: " + convertedItem.getManufacturerName() + ", "
                              + convertedItem.getInactivationDate() + ", " + convertedItem.getRequestType() + "\n");

                    // Set the IEN to the returned IEN
                    if (ItemAction.ADD.equals(action) && (manufacturerItem.getManufacturerIen() == null)) {
                        manufacturerItem.setManufacturerIen(inf.getIen());
                    }

                }
            } else {
                if (catchingUp) {
                    String eText = "Attempt to send sync message to VistA for Manufacturer "
                        + convertedItem.getManufacturerName()
                        + " while sending queued message to VistA";
                    LOG.debug(eText);
                    throw new ValidationException(ValidationException.VISTA_COMM_TURNED_OFF);
                } else {
                    shouldHaveSent = true;
                }
            }

        } else {
            LOG.debug("Did not send Manufacturer Item: " + manufacturerItem.getValue() + ", " + action.toString() + " - "
                      + "since it is not complete or syncing with VistA was not required.\n");
        }

        return shouldHaveSent;
    }

    /**
     * Sends an Package Type Item to VistA.
     * 
     * @param packageTypeItem
     *            A Package Type Item.
     * @param differences
     *            old/new value differences
     * @param status
     *            item status
     * @param action
     *            item action
     * @param user
     *            The user making this call.
     * @param environment
     *            Currently unused.
     * @param okToSend
     *            Boolean indicating whether communications are turned on with VistA or not.
     * @param catchingUp
     *            Boolean indicating that theh queue is being sent to VistA
     * @return shouldHaveSent
     *            true if a message would have been sent, but communications were turned off         
     * @throws ValidationException ValidationException
     */
    private Boolean sendPackageTypeItem(PackageTypeVo packageTypeItem, Map<FieldKey, Difference> differences,
        ItemStatus status, ItemAction action, UserVo user, Boolean environment, boolean okToSend, boolean catchingUp)
        throws ValidationException {

        PackageTypeSyncRequest convertedItem =
                PackageTypeConverter.toPackageTypeSyncRequest(packageTypeItem, differences, action);
        Boolean shouldHaveSent = false;

        if (StringUtils.isNotBlank(convertedItem.getPackageTypeName())) {

            if (okToSend) {
                LOG.debug("sending Package Type Item: " + convertedItem.getPackageTypeName() + "\n");

                String xml;

                try {
                    xml = PackageTypeSyncRequestDocument.instance().marshal(convertedItem);
                } catch (Exception e) {
                    String eText = "Error while attempting to marshal Package Type "
                        + convertedItem.getPackageTypeName();
                    LOG.debug(eText);
                    String msg = e.getMessage();

                    if (e.getCause() != null) {
                        if (e.getCause().getCause() != null) {
                            msg = msg + " : " + e.getCause().getCause().getLocalizedMessage();
                        }
                    }

                    throw new ValidationException(ValidationException.VISTA_XML_MARSHALLING,
                        "Package Type", convertedItem.getPackageTypeName(), msg);
                }

                List<String> params = new LinkedList<String>();
                params.add(xml);

                VistALinkResponseInfo inf = sendItem(params, user);

                if (inf.isError()) {
                    LOG.debug(inf.getErrorResponseString());
                } else {
                    LOG.debug("Sent Package Type Item to VistA: " + convertedItem.getPackageTypeName() + ", "
                              + convertedItem.getInactivationDate() + ", " + convertedItem.getRequestType() + "\n");

                    // Set the IEN to the returned IEN
                    if (ItemAction.ADD.equals(action) && (packageTypeItem.getPackagetypeIen() == null)) {
                        packageTypeItem.setPackagetypeIen(inf.getIen());
                    }

                }
            } else {
                if (catchingUp) {
                    String eText = "Attempt to send sync message to VistA for Package Type "
                        + convertedItem.getPackageTypeName()
                        + " while sending queued messages to VistA";
                    LOG.debug(eText);
                    throw new ValidationException(ValidationException.VISTA_COMM_TURNED_OFF);
                } else {
                    shouldHaveSent = true;
                }
            }

        } else {
            LOG.debug("Did not send Package Type Item: " + packageTypeItem.getValue() + ", " + action.toString() + " - "
                      + "since it is not complete or syncing with VistA is not required.\n");
        }

        return shouldHaveSent;
    }

    /**
     * Sends a Drug Unit Item to VistA.
     * 
     * @param drugUnitItem The list of Drug unit Items containing a single Drug Unit Change Request
     * @param user The user for in whose name the change will be done (null is default)
     * @param okToSend Boolean value indicating whether communications are turned on with VistA or not.
     * @param catchingUp Boolean indicating that the queue is currently being sent to VistA
     * @return shouldHaveSent true if a message would have been sent, but communications were turned off         
     * @throws ValidationException ValidationException
     */
    private Boolean sendDrugUnitItem(DomainItem[] drugUnitItem, UserVo user, boolean okToSend, boolean catchingUp)
        throws ValidationException {
        DrugUnitSyncRequest convertedItem =
                DrugUnitConverter.toDrugUnitSyncRequest((DrugUnitVo) drugUnitItem[0].getItem(),
                                                        drugUnitItem[0].getDifference(), drugUnitItem[0].getAction());
        Boolean shouldHaveSent = false;

        if (StringUtils.isNotBlank(convertedItem.getDrugUnitName())) {

            if (okToSend) {
                LOG.debug("sending Drug Unit Item: " + convertedItem.getDrugUnitName() + "\n");

                String xml;

                try {
                    xml = DrugUnitSyncRequestDocument.instance().marshal(convertedItem);
                } catch (Exception e) {
                    String eText = "Error while attempting to marshal Drug Unit "
                        + convertedItem.getDrugUnitName();
                    LOG.debug(eText);
                    String msg = e.getMessage();

                    if (e.getCause() != null) {
                        if (e.getCause().getCause() != null) {
                            msg = msg + " : " + e.getCause().getCause().getLocalizedMessage();
                        }
                    }

                    throw new ValidationException(ValidationException.VISTA_XML_MARSHALLING,
                        "Drug Unit", convertedItem.getDrugUnitName(), msg);
                }

                List<String> params = new LinkedList<String>();
                params.add(xml);

                VistALinkResponseInfo inf = sendItem(params, user);

                if (inf.isError()) {
                    LOG.debug(inf.getErrorResponseString());
                } else {
                    LOG.debug("Sent Drug Unit Item to VistA: " + convertedItem.getDrugUnitName() + ", "
                              + convertedItem.getInactivationDate() + ", " + convertedItem.getRequestType() + "\n");

                    // Set the IEN to the returned IEN
                    DrugUnitVo duVo = (DrugUnitVo) drugUnitItem[0].getItem();

                    if ((drugUnitItem[0].getAction() == ItemAction.ADD) && (duVo.getDrugUnitIen() == null)) {
                        duVo.setDrugUnitIen(inf.getIen());
                    }

                }
            } else {
                if (catchingUp) {
                    String eText = "Attempt to send sync message to VistA for Drug Unit " + convertedItem.getDrugUnitName()
                        + " while queued messages are being sent to VistA.";
                    LOG.debug(eText);
                    throw new ValidationException(ValidationException.VISTA_COMM_TURNED_OFF);
                } else {
                    shouldHaveSent = true;
                }
            }

        } else {
            LOG.debug("Did not send Drug Unit Item: " + drugUnitItem[0].getItem().getValue() + ", "
                      + drugUnitItem[0].getAction().toString() + " - "
                      + "since it is not complete or syncing with VistA is not required.\n");
        }

        return shouldHaveSent;
    }

    /**
     * Sends a VA Dispense Unit Item to VistA.
     * 
     * @param vaDispenseUnitItem The list of Domain Items containing a single Dispense Unit Change
     *            Request
     * @param user The user for in whose name the change will be done (null is default)
     * @param okToSend
     *            Boolean indicating whether communications are turned on with VistA or not.
     * @param catchingUp
     *            Boolean indicating that theh queue is being sent to VistA
     * @return shouldHaveSent
     *            true if a message would have been sent, but communications were turned off         
     * @throws ValidationException ValidationException
     */
    private Boolean sendVaDispenseUnitItem(DomainItem[] vaDispenseUnitItem, UserVo user, boolean okToSend, boolean catchingUp)
        throws ValidationException {
        VaDispenseUnitSyncRequest convertedItem =
                VaDispenseUnitConverter.toVaDispenseUnitSyncRequest((DispenseUnitVo) vaDispenseUnitItem[0].getItem(),
                                                                    vaDispenseUnitItem[0].getDifference(),
                                                                    vaDispenseUnitItem[0].getAction());
        Boolean shouldHaveSent = false;

        if (StringUtils.isNotBlank(convertedItem.getVaDispenseUnitName())) {

            if (okToSend) {
                LOG.debug("sending VA Dispense Unit Item: " + convertedItem.getVaDispenseUnitName() + "\n");

                String xml;

                try {
                    xml = VaDispenseUnitSyncRequestDocument.instance().marshal(convertedItem);
                } catch (Exception e) {
                    String eText = "Error while attempting to marshal Dispense Unit "
                        + convertedItem.getVaDispenseUnitName();
                    LOG.debug(eText);
                    String msg = e.getMessage();

                    if (e.getCause() != null) {
                        if (e.getCause().getCause() != null) {
                            msg = msg + " : " + e.getCause().getCause().getLocalizedMessage();
                        }
                    }

                    throw new ValidationException(ValidationException.VISTA_XML_MARSHALLING,
                        "Dispense Unit", convertedItem.getVaDispenseUnitName(), msg);
                }

                List<String> params = new LinkedList<String>();
                params.add(xml);

                VistALinkResponseInfo inf = sendItem(params, user);

                if (inf.isError()) {
                    LOG.debug(inf.getErrorResponseString());
                } else {
                    LOG.debug("Sent VA Dispense Unit Item to VistA: " + convertedItem.getVaDispenseUnitName() + ", "
                              + convertedItem.getInactivationDate() + ", " + convertedItem.getRequestType() + "\n");

                    // Set the IEN to the returned IEN
                    DispenseUnitVo duVo = (DispenseUnitVo) vaDispenseUnitItem[0].getItem();

                    if ((vaDispenseUnitItem[0].getAction() == ItemAction.ADD) && (duVo.getDispenseUnitIen() == null)) {
                        duVo.setDispenseUnitIen(inf.getIen());
                    }
                }
            } else {
                if (catchingUp) {
                    String eText = "Attempt to send sync message to VistA for VA Dispense Unit "
                        + convertedItem.getVaDispenseUnitName()
                        + " while sending queued messages to VistA.";
                    LOG.debug(eText);
                    throw new ValidationException(ValidationException.VISTA_COMM_TURNED_OFF);
                } else {
                    shouldHaveSent = true;
                }
            }

        } else {
            LOG.debug("Did not send VA Dispense Unit Item: " + vaDispenseUnitItem[0].getItem().getValue() + ", "
                      + vaDispenseUnitItem[0].getAction().toString() + " - "
                      + "since it is not complete or syncing with VistA is not required.\n");
        }

        return shouldHaveSent;
    }

    /**
     * Sends a VA Generic Name Item to VistA.
     * 
     * @param vaGenericNameItem The list of Domain Items containing a single Drug Unit Change Request
     * @param user The user for in whose name the change will be done (null is default)
     * @param okToSend Boolean indicating whether communications are turned on with VistA or not.
     * @param catchingUp Boolean indicating that theh queue is being sent to VistA
     * @return shouldHaveSent true if a message would have been sent, but communications were turned off         
     * @throws ValidationException ValidationException
     */
    private Boolean sendVaGenericNameItem(DomainItem[] vaGenericNameItem, UserVo user, boolean okToSend, boolean catchingUp)
        throws ValidationException {
        VaGenericNameSyncRequest convertedItem =
                VaGenericNameConverter.toVaGenericNameSyncRequest((GenericNameVo) vaGenericNameItem[0].getItem(),
                                                                  vaGenericNameItem[0].getDifference(),
                                                                  vaGenericNameItem[0].getAction());
        Boolean shouldHaveSent = false;

        if (StringUtils.isNotBlank(convertedItem.getVaGenericNameName())) {

            if (okToSend) {
                LOG.debug("sending VA Generic Name Item: " + convertedItem.getVaGenericNameName() + "\n");

                String xml;

                try {
                    xml = VaGenericNameSyncRequestDocument.instance().marshal(convertedItem);
                } catch (Exception e) {
                    String eText = "Error while attempting to marshal VA Generic "
                        + convertedItem.getVaGenericNameName();
                    LOG.debug(eText);
                    String msg = e.getMessage();

                    if (e.getCause() != null) {
                        if (e.getCause().getCause() != null) {
                            msg = msg + " : " + e.getCause().getCause().getLocalizedMessage();
                        }
                    }

                    throw new ValidationException(ValidationException.VISTA_XML_MARSHALLING,
                        "VA Generic Name", convertedItem.getVaGenericNameName(), msg);
                }

                List<String> params = new LinkedList<String>();
                params.add(xml);

                VistALinkResponseInfo inf = sendItem(params, user);

                if (inf.isError()) {
                    LOG.debug(inf.getErrorResponseString());
                } else {
                    LOG.debug("Sent Generic Name Item to VistA: " + convertedItem.getVaGenericNameName() + ", "
                              + convertedItem.getInactivationDate() + ", " + convertedItem.getRequestType() + "\n");

                    // Set the IEN to the returned IEN
                    GenericNameVo gnVo = (GenericNameVo) vaGenericNameItem[0].getItem();

                    if ((vaGenericNameItem[0].getAction() == ItemAction.ADD) && (gnVo.getGenericIen() == null)) {
                        gnVo.setGenericIen(inf.getIen());
                    }
                }
            } else {
                if (catchingUp) {
                    String eText = "Attempt to send sync message to VistA for VA Generic "
                        + convertedItem.getVaGenericNameName()
                        + " while the queue is being sent to VistA.";
                    LOG.debug(eText);
                    throw new ValidationException(ValidationException.VISTA_COMM_TURNED_OFF);
                } else {
                    shouldHaveSent = true;
                }
            }

        } else {
            LOG.debug("Did not send VA Generic Name Item: " + vaGenericNameItem[0].getItem().getValue() + ", "
                      + vaGenericNameItem[0].getAction().toString() + " - "
                      + "Since it is not complete or syncing with VistA is not required.\n");
        }

        return shouldHaveSent;
    }

    /**
     * Sends a Dosage Form Item to VistA.
     * 
     * @param dosageFormItem The list of Domain Items containing a single Doseage Form Change Request
     * @param user The user for in whose name the change will be done (The default is null)
     * @param okToSend This is a Boolean indicating whether communications are turned on with VistA or not.
     * @param catchingUp Boolean indicating that the queue is being sent to VistA
     * @return shouldHaveSent true if a message would have been sent, but communications were turned off         
     * @throws ValidationException ValidationException
     */
    private Boolean sendDosageFormItem(DomainItem[] dosageFormItem, UserVo user, boolean okToSend, boolean catchingUp)
        throws ValidationException {
        DosageFormSyncRequest convertedItem =
                DosageFormConverter.toDosageFormSyncRequest((DosageFormVo) dosageFormItem[0].getItem(),
                                                            dosageFormItem[0].getDifference(), dosageFormItem[0].getAction());
        Boolean shouldHaveSent = false;

        if (StringUtils.isNotBlank(convertedItem.getDosageFormName())) {

            if (okToSend) {
                LOG.debug("Sending Dosage Form Item: " + convertedItem.getDosageFormName() + "\n");

                String xml;

                try {
                    xml = DosageFormSyncRequestDocument.instance().marshal(convertedItem);
                } catch (Exception e) {
                    String eText = "Error while attempting to marshal Dosage Form "
                        + convertedItem.getDosageFormName();
                    LOG.debug(eText);
                    String msg = e.getMessage();

                    if (e.getCause() != null) {
                        if (e.getCause().getCause() != null) {
                            msg = msg + " : " + e.getCause().getCause().getLocalizedMessage();
                        }
                    }

                    throw new ValidationException(ValidationException.VISTA_XML_MARSHALLING,
                        "Dosage Form", convertedItem.getDosageFormName(), msg);
                }

                List<String> params = new LinkedList<String>();
                params.add(xml);

                VistALinkResponseInfo inf = sendItem(params, user);

                if (inf.isError()) {
                    LOG.debug(inf.getErrorResponseString());
                } else {
                    LOG.debug("Sent Dosage Form Item to VistA: " + convertedItem.getDosageFormName() + ", "
                              + convertedItem.getInactivationDate() + ", " + convertedItem.getRequestType() + "\n");

                    // Set the IEN to the returned IEN
                    DosageFormVo dfVo = (DosageFormVo) dosageFormItem[0].getItem();

                    if ((dosageFormItem[0].getAction() == ItemAction.ADD) && (dfVo.getDosageFormIen() == null)) {
                        dfVo.setDosageFormIen(inf.getIen());
                    }
                }
            } else {
                if (catchingUp) {
                    String eText = "Attempt to send sync message to VistA for Dosage Form "
                        + convertedItem.getDosageFormName()
                        + " while sending queued messages to VistA.";
                    LOG.debug(eText);
                    throw new ValidationException(ValidationException.VISTA_COMM_TURNED_OFF);
                } else {
                    shouldHaveSent = true;
                }
            }

        } else {
            LOG.debug("Did not send Dosage Form Item: " + dosageFormItem[0].getItem().getValue() + ", "
                      + dosageFormItem[0].getAction().toString() + " - "
                      + "since it is not complete or no changes required syncing with VistA.\n");
        }

        return shouldHaveSent;
    }

    /**
     * Sends a Drug Class Item to VistA.
     * 
     * @param drugClassItem The list of Domain Items containing a single sendDrugClassItem Change Request
     * @param user The user for in whose name the change will be done (null is default)
     * @param okToSend Boolean indicating whether communications are turned on with VistA or not.
     * @param catchingUp Boolean indicating that the queue is being sent to VistA
     * @return shouldHaveSent This field is true if a message would have been sent, 
     *          but wasn't because communications were turned off         
     * @throws ValidationException ValidationException t5hat is thrown if the message could not be 
     *          converted and saved.
     */
    private Boolean sendDrugClassItem(DomainItem[] drugClassItem, UserVo user, boolean okToSend, boolean catchingUp)
        throws ValidationException {
        DrugClassSyncRequest convertedItem =
                DrugClassConverter.toDrugClassSyncRequest((DrugClassVo) drugClassItem[0].getItem(),
                                                          drugClassItem[0].getDifference(), drugClassItem[0].getAction());
        Boolean shouldHaveSent = false;

        if (StringUtils.isNotBlank(convertedItem.getDrugClassCode())) {

            if (okToSend) {
                if (StringUtils.isNotBlank(convertedItem.getDrugClassClassification())) {
                    LOG.debug("Sending Drug Class Item: " + convertedItem.getDrugClassCode() + ":"
                              + convertedItem.getDrugClassClassification() + "\n");
                } else {
                    LOG.debug("Sending Drug Class Item: " + ":" + convertedItem.getDrugClassClassification() + "\n");
                }

                String xml;

                try {
                    xml = DrugClassSyncRequestDocument.instance().marshal(convertedItem);
                } catch (Exception e) {
                    String eText = "Error while attempting to marshal Drug Class with Code "
                        + convertedItem.getDrugClassCode();
                    LOG.debug(eText);
                    String msg = e.getMessage();

                    if (e.getCause() != null) {
                        if (e.getCause().getCause() != null) {
                            msg = msg + " : " + e.getCause().getCause().getLocalizedMessage();
                        }
                    }

                    throw new ValidationException(ValidationException.VISTA_XML_MARSHALLING,
                        "Drug Class", convertedItem.getDrugClassCode(), msg);
                }

                List<String> params = new LinkedList<String>();
                params.add(xml);

                VistALinkResponseInfo inf = sendItem(params, user);

                if (inf.isError()) {
                    LOG.debug(inf.getErrorResponseString());
                } else {
                    LOG.debug("Sent Drug Class Item: " + convertedItem.getDrugClassCode() + ": "
                              + convertedItem.getDrugClassClassification() + ", " + ", "
                              + convertedItem.getRequestType() + "\n");

                    // Set the IEN to the returned IEN
                    DrugClassVo dcVo = (DrugClassVo) drugClassItem[0].getItem();

                    if ((drugClassItem[0].getAction() == ItemAction.ADD) && (dcVo.getDrugClassIen() == null)) {
                        dcVo.setDrugClassIen(inf.getIen());
                    }
                }
            } else {
                if (catchingUp) {
                    String eText = "Attempt to send sync message to VistA for Drug Class "
                        + convertedItem.getDrugClassCode()
                        + " while sending queued messages to VistA.";
                    LOG.debug(eText);
                    throw new ValidationException(ValidationException.VISTA_COMM_TURNED_OFF);
                } else {
                    shouldHaveSent = true;
                }
            }

        } else {
            DrugClassVo drugClassVo = (DrugClassVo) drugClassItem[0].getItem();
            LOG.debug("Did not send Drug Class Item: " + drugClassVo.getCode() + ": " + drugClassVo.getClassification() + ", "
                      + drugClassItem[0].getAction().toString() + " - "
                      + "Since it is not complete or no sync with VistA was required.\n");
        }

        return shouldHaveSent;
    }

    /**
     * Sends a Drug Ingredient Item to VistA.
     * 
     * @param drugIngredientItem
     *            The list of Domain Items containing a single Drug Unit Change
     *            Request
     * @param user
     *            The user for in whose name the change will be done (null is
     *            default)
     * @param okToSend
     *            Boolean indicating whether communications are turned on with VistA or not.
     * @param catchingUp
     *            Boolean indicating that theh queue is being sent to VistA
     * @return shouldHaveSent
     *            true if a message would have been sent, but communications were turned off         
     * @throws ValidationException ValidationException
     */
    private Boolean sendDrugIngredientItem(DomainItem[] drugIngredientItem, UserVo user, boolean okToSend, boolean catchingUp)
        throws ValidationException {
        DrugIngredientSyncRequest convertedItem =
                DrugIngredientConverter.toDrugIngredientSyncRequest((IngredientVo) drugIngredientItem[0].getItem(),
                                                                    drugIngredientItem[0].getDifference(),
                                                                    drugIngredientItem[0].getAction());
        Boolean shouldHaveSent = false;

        if (StringUtils.isNotBlank(convertedItem.getDrugIngredientName())) {

            if (okToSend) {
                LOG.debug("Sending Drug Ingredient Item: " + convertedItem.getDrugIngredientName() + ", "
                          + convertedItem.getInactivationDate() + "\n");

                String xml;

                try {
                    xml = DrugIngredientSyncRequestDocument.instance().marshal(convertedItem);
                } catch (Exception e) {
                    String eText = "Error while attempting to marshal Drug Ingredient "
                        + convertedItem.getDrugIngredientName();
                    LOG.debug(eText);
                    String msg = e.getMessage();

                    if (e.getCause() != null) {
                        if (e.getCause().getCause() != null) {
                            msg = msg + " : " + e.getCause().getCause().getLocalizedMessage();
                        }
                    }

                    throw new ValidationException(ValidationException.VISTA_XML_MARSHALLING,
                        "Drug Ingredient", convertedItem.getDrugIngredientName(), msg);
                }

                List<String> params = new LinkedList<String>();
                params.add(xml);

                VistALinkResponseInfo inf = sendItem(params, user);

                if (inf.isError()) {
                    LOG.debug(inf.getErrorResponseString());
                } else {
                    LOG.debug("Sent Drug Ingredient Item to VistA: " + convertedItem.getDrugIngredientName() + ", "
                              + convertedItem.getInactivationDate() + ", " + convertedItem.getRequestType() + "\n");

                    // Set the IEN to the returned IEN
                    IngredientVo diVo = (IngredientVo) drugIngredientItem[0].getItem();

                    if ((drugIngredientItem[0].getAction() == ItemAction.ADD) && (diVo.getNdfIngredientIen() == null)) {
                        diVo.setIngredientIen(inf.getIen());
                    }
                }
            } else {
                if (catchingUp) {
                    String eText = "Attempt to send sync message to VistA for Drug Ingredient "
                        + convertedItem.getDrugIngredientName()
                        + " while sending queued messages to VistA.";
                    LOG.debug(eText);
                    throw new ValidationException(ValidationException.VISTA_COMM_TURNED_OFF);
                } else {
                    shouldHaveSent = true;
                }
            }

        } else {

            LOG.debug("Did not send Drug Ingredient Item: " + convertedItem.getDrugIngredientName() + ", "
                      + convertedItem.getInactivationDate() + ", " + ", " + convertedItem.getRequestType()
                      + "since it was not complete or did not require syncing with VistA.\n");
        }

        return shouldHaveSent;
    }

    /**
     * Sends an Orderable Item to VistA.
     * 
     * @param orderableItem        orderable item
     * @param differences          difference set
     * @param status               item status
     * @param action               item action
     * @param user                 user making this call
     * @param isLocal              is local environment
     * @return shouldHaveSent      should never be true for Orderable Item
     * @throws ValidationException ValidationException
     */
    private Boolean sendOrderableItem(OrderableItemVo orderableItem, Map<FieldKey, Difference> differences, ItemStatus status,
                                   ItemAction action, UserVo user, boolean isLocal) throws ValidationException {
        OrderableItem convertedItem =
                OrderableItemConverter.toOrderableItem(orderableItem, differences, action, new DomainItem[] {}, isLocal);
        Boolean shouldHaveSent = false;

        if (convertedItem.isSetPharmacyOrderableItemFile()) {
            convertedItem.setStatus(status);

            convertedItem.setPepsIdNumber(BigInteger.valueOf(interfaceCounter
                    .incrementCounter(InterfaceCounterDomainCapabilityCallback.INTERFACE_VISTA_SYNCHRONIZATION,
                                      InterfaceCounterDomainCapabilityCallback.COUNTER_MESSAGE_ID, user)));

            LOG.debug("sending Orderable Item: " + status + ", " + action + ", " + orderableItem.isLocalUse() + ", "
                      + orderableItem.getOiName() + "\n" + differences);

            String xml = OrderableItemDocument.instance().marshal(convertedItem);
            List<String> params = new LinkedList<String>();
            params.add(xml);

            sendItem(params, user);
        } else {
            LOG.debug("did NOT send Orderable Item: " + status + ", " + action + ", " + orderableItem.getOiName() + ", "
                      + orderableItem.getOiName() + "\n" + differences);
        }

        return shouldHaveSent;
    }

    /**
     * Sends a Product Item to VistA.
     * 
     * @param productItem
     *            A product item.
     * @param differences
     *            set difference
     * @param status
     *            item status
     * @param action item action
     * @param user The user of the system that is making this call.
     * @param isLocalEnvironment true if local, for PPSN always false.
     * @throws ValidationException ValidationException
     */
    private void sendProductItem(ProductVo productItem, Map<FieldKey, Difference> differences, ItemStatus status,
                                 ItemAction action, UserVo user, boolean isLocalEnvironment) throws ValidationException {
        ProductItem convertedItem =
                ProductItemConverter.toProductItem(productItem, differences, action, new DomainItem[] {}, isLocalEnvironment);

        if (convertedItem.isSetDrugFile() || convertedItem.isSetVaProductFile()) {
            convertedItem.setStatus(status);

            convertedItem.setPepsIdNumber(BigInteger.valueOf(interfaceCounter
                    .incrementCounter(InterfaceCounterDomainCapabilityCallback.INTERFACE_VISTA_SYNCHRONIZATION,
                                      InterfaceCounterDomainCapabilityCallback.COUNTER_MESSAGE_ID, user)));

            LOG.debug("Sending Product Item: " + status + ", " + action + ", " + productItem.isLocalUse() + ", "
                      + productItem.getVuid() + "\n" + differences);

            String xml = ProductItemDocument.instance().marshal(convertedItem);
            List<String> params = new LinkedList<String>();
            params.add(xml);

            sendItem(params, user);
        } else {
            LOG.debug("Did NOT send Product Item: " + status + ", " + action + ", "
                      + productItem.isPreviouslyMarkedForLocalUse() + ", " + productItem.getVuid() + "\n" + differences);
        }
    }

    /**
     * Sends a VA Product Item to VistA.
     * 
     * @param productItem
     *            A product item.
     * @param differences
     *            set difference
     * @param status
     *            item status
     * @param action
     *            item action
     * @param user
     *            The user making this call.
     * @param isLocalEnvironment
     *            true if local
     * @param okToSend
     *            Boolean indicating whether communications are turned on with VistA or not.
     * @param catchingUp
     *            Boolean indicating that theh queue is being sent to VistA
     * @return shouldHaveSent
     *            true if a message would have been sent, but communications were turned off         
     * @throws ValidationException ValidationException
     */
    private Boolean sendVaProductItem(ProductVo productItem, Map<FieldKey, Difference> differences, ItemStatus status,
        ItemAction action, UserVo user, boolean isLocalEnvironment, boolean okToSend, boolean catchingUp)
        throws ValidationException {

        VaProductSyncRequest convertedItem = VaProductConverter.toVaProductSyncRequest(productItem, differences, action);
        Boolean shouldHaveSent = false;

        if (convertedItem.getVaProductName() != null) {

            if (okToSend) {
                LOG.debug("Sending VA Product Item: " + status + ", " + action + ", " + productItem.isLocalUse() + ", "
                          + productItem.getVuid() + "\n" + differences);

                String xml;

                try {
                    xml = VaProductSyncRequestDocument.instance().marshal(convertedItem);
                } catch (Exception e) {
                    String eText = "Error while attempting to marshal Product "
                        + convertedItem.getVaProductName();
                    LOG.debug(eText);
                    String msg = e.getMessage();

                    if (e.getCause() != null) {
                        if (e.getCause().getCause() != null) {
                            msg = msg + " : " + e.getCause().getCause().getLocalizedMessage();
                        }
                    }

                    throw new ValidationException(ValidationException.VISTA_XML_MARSHALLING,
                        "VA Product", convertedItem.getVaProductName(), msg);
                }

                LOG.debug("Sending Product Sync Message: " + xml);
                
                //TODO: Remove before deployment
                System.out.println("Sending Product Sync Message to Vista "+xml);
                
                List<String> params = new LinkedList<String>();
                params.add(xml);

                VistALinkResponseInfo inf = sendItem(params, user);

                if (inf.isError()) {
                    LOG.debug(inf.getErrorResponseString());
                } else {
                    LOG.debug("Sent VA Product Item to VistA: " + convertedItem.getVaProductName() + ", "
                              + convertedItem.getInactivationDate() + ", " + convertedItem.getRequestType() + "\n");

                    // Set the IEN to the returned IEN
                    if ((action == ItemAction.ADD) && (productItem.getIen() == null)) {
                        productItem.setNdfProductIen(Long.valueOf(inf.getIen()));
                        Long serviceCode = productItem.getNdfProductIen() + PPSConstants.L600000;
                        productItem.setServiceCode(serviceCode.toString());
                    }
                }
            } else {
                if (catchingUp) {
                    String eText = "Attempt to send sync message to VistA for Product "
                        + convertedItem.getVaProductName()
                        + " while sending the queue to VistA.";
                    LOG.debug(eText);
                    throw new ValidationException(ValidationException.VISTA_COMM_TURNED_OFF);
                } else {
                    shouldHaveSent = true;
                }
            }

        } else {
            LOG.debug("Did NOT send Product Item: " + status + ", " + action + ", "
                + productItem.isPreviouslyMarkedForLocalUse() + ", " + productItem.getVuid() + "\n"
                + differences + "\n since it was incomplete or no sync with VistA was required.\n");
        }

        return shouldHaveSent;
    }

    /**
     * Send one or more new/updated NDF Domain item(s) to VistA.
     * 
     * @param items
     *            domain items
     * @param user
     *            User making this call (null authenticates using a DUZ)
     * @throws ValidationException ValidationException
     */
    private void sendNdfDomain(DomainItem[] items, UserVo user) throws ValidationException {
        NdfDomain convertedItem = NdfDomainConverter.toNdfDomain(items);

        if (convertedItem.isSetDrugIngredientsFile() || convertedItem.isSetDrugManufacturerFile()
            || convertedItem.isSetDrugUnitsFile() || convertedItem.isSetVaDispenseUnitFile()
            || convertedItem.isSetVaDrugClassFile() || convertedItem.isSetVaGenericFile()) {

            convertedItem.setPepsIdNumber(BigInteger.valueOf(interfaceCounter
                    .incrementCounter(InterfaceCounterDomainCapabilityCallback.INTERFACE_VISTA_SYNCHRONIZATION,
                                      InterfaceCounterDomainCapabilityCallback.COUNTER_MESSAGE_ID, user)));

            LOG.debug("Sending NDF Domain(s): ");

            for (DomainItem item : items) {
                LOG.debug(item.getAction() + ", " + item.getItem().getValue() + "\n" + item.getDifference());
            }

            String xml = NdfDomainDocument.instance().marshal(convertedItem);

            List<String> params = new LinkedList<String>();
            params.add(xml);

            sendItem(params, user);
        } else {
            LOG.debug("Did NOT send NDF Domain(s): ");

            for (DomainItem item : items) {
                LOG.debug(item.getAction() + ", " + item.getItem().getValue() + "\n" + item.getDifference());
            }
        }
    }

    /**
     * Send one or more new/updated PDM Domain item(s) to VistA.
     * 
     * @param items
     *            domain items
     * @param user
     *            User making this call (null authenticates using a DUZ)
     * @throws ValidationException ValidationException
     */
    private void sendPdmDomain(DomainItem[] items, UserVo user) throws ValidationException {
        PdmDomain convertedItem = PdmDomainConverter.toPdmDomain(items);

        boolean test =
            convertedItem.isSetAdministrationScheduleFile() || convertedItem.isSetDosageFormFile()
                || convertedItem.isSetDoseUnitsFile() || convertedItem.isSetDrugTextFile();

        test =
            test || convertedItem.isSetMedicationInstructionFile() || convertedItem.isSetMedicationRoutesFile()
                || convertedItem.isSetOrderUnitFile() || convertedItem.isSetPharmacySystemFile();

        test = test || convertedItem.isSetRxConsultFile() || convertedItem.isSetStandardMedicationRoutesFile();

        if (test) {

            convertedItem.setPepsIdNumber(BigInteger.valueOf(interfaceCounter.incrementCounter(
                InterfaceCounterDomainCapabilityCallback.INTERFACE_VISTA_SYNCHRONIZATION,
                InterfaceCounterDomainCapabilityCallback.COUNTER_MESSAGE_ID, user)));

            LOG.debug("Sending PDM Domain(s): ");

            for (DomainItem item : items) {
                LOG.debug(item.getAction() + ", " + item.getItem().getValue() + "\n" + item.getDifference());
            }

            String xml = PdmDomainDocument.instance().marshal(convertedItem);
            List<String> params = new LinkedList<String>();
            params.add(xml);

            sendItem(params, user);
        } else {
            LOG.debug("Did NOT send PDM Domains(s): ");

            for (DomainItem item : items) {
                LOG.debug(item.getAction() + ", " + item.getItem().getValue() + "\n" + item.getDifference());
            }
        }
    }

    /**
     * Calls send item RPC using provided user and parameters.
     * 
     * @param params
     *            RPC parameters
     * @param user
     *            current user
     * @return VistALinkResponseInfo VistALinkResponseInfo
     * @throws ValidationException ValidationException
     */
    private VistALinkResponseInfo sendItem(List params, UserVo user) throws ValidationException {
        VistALinkResponseInfo inf = new VistALinkResponseInfo();
        RpcResponse rpcResponse;

        try {
            rpcResponse = vistaLinkConnectionUtility.sendRequest(sendItemRpcContext, sendItemRpcName, params, null);
        } catch (Exception e) {
            
            //TODO: Remove before deployment
            System.out.println("Exception while Calling RPC context :"+sendItemRpcContext+" name: "+sendItemRpcName+" error message is "+e.getMessage());
            
            throw new ValidationException(ValidationException.SEND_ITEM_TO_VISTA, sendItemRpcName,
                                          e.getMessage());
            
        }
        //TODO: Remove before deployment
        System.out.println("Called RPC context :"+sendItemRpcContext+" name: "+sendItemRpcName+" with results "+ rpcResponse.getResults());
        
        LOG.debug("Vista Link XML Response is: " + rpcResponse.getResults());

        SyncResponse response = SyncResponseDocument.instance().unmarshal(rpcResponse.getResults());

        if (SyncResponseStatusType.FAILURE.equals(response.getSyncResponseType().getStatus())) {
            inf.setErrorResponseString(response.getSyncResponseType().getMessage());
            
            //TODO: Remove before deployment
            System.out.println("Sync Failure while Calling RPC context :"+sendItemRpcContext+" name: "+sendItemRpcName+" failure message is "+response.getSyncResponseType().getMessage());
            
            throw new ValidationException(ValidationException.SEND_ITEM_TO_VISTA, sendItemRpcName,
                                         response.getSyncResponseType().getMessage());
        } else if (SyncResponseStatusType.SUCCESS.equals(response.getSyncResponseType().getStatus())) { // should happen

            inf.setIen(response.getIen());
        }

        return inf;

    }

    /**
     * Convert from RequestItemStatus to ItemStatus.
     * 
     * @param status
     *            status
     * @return status
     */
    private ItemStatus toStatus(RequestItemStatus status) {
        if (RequestItemStatus.APPROVED.equals(status)) {
            return ItemStatus.APPROVED;
        } else if (RequestItemStatus.PENDING.equals(status)) {
            return ItemStatus.PENDING;
        } else if (RequestItemStatus.REJECTED.equals(status)) {
            return ItemStatus.REJECTED;
        }

        throw new InterfaceException(InterfaceException.UNKNOWN_STATUS, new Object[] { status.toString() });
    }

}
