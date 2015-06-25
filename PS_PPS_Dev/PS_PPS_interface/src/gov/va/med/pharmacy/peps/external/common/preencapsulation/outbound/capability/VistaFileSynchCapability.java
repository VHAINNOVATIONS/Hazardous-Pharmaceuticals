/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability;


import java.util.Collection;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;


/**
 * Sends Updates to Vista Link
 */
public interface VistaFileSynchCapability {

    /**
     * Transforms a ManagedItem into an XML document and sends it to VistA.
     * 
     * @param item ManagedItem to send to VistA
     * @param differences Differences between the old and new value object
     * @param user current UserVo
     * @param action Approved, Pending, etc.
     * @param isLocal true if local environment
     * @param okToSend Boolean indicating communications with VistA are turned on 
     * @param catchingUp boolean
     * @throws ValidationException exception
     * @return Boolean
     */
    Boolean sendItemToVista(ManagedItemVo item, Collection<Difference> differences, UserVo user, ItemAction action,
                         boolean isLocal, boolean okToSend, boolean catchingUp) throws ValidationException ;

    /**
     * Transforms a new ManagedItem into an XML document and sends it to VistA.
     * 
     * @param item ManagedItem to send to VistA
     * @param user current UserVo
     * @param okToSend Boolean indicating communications with VistA are turned on 
     * @param catchingUp boolean
     * @throws ValidationException 
     * @return Boolean
     */
    Boolean sendNewItemToVista(ManagedItemVo item, UserVo user, boolean okToSend, boolean catchingUp) 
        throws ValidationException;

    /**
     * Transforms a modified ManagedItem into an XML document and sends it to VistA.
     * 
     * @param item ManagedItem to send to VistA
     * @param differences Differences between the old and new value object
     * @param user current UserVo
     * @param okToSend Boolean indicating communications with VistA are turned on 
     * @param catchingUp boolean
     * @return shouldHaveSent Boolean indicating that the send should be added to the Queue
     * @throws ValidationException 
     */
    Boolean sendModifiedItemToVista(ManagedItemVo item, Collection<Difference> differences, UserVo user, boolean okToSend, 
        boolean catchingUp) 
        throws ValidationException;

}
