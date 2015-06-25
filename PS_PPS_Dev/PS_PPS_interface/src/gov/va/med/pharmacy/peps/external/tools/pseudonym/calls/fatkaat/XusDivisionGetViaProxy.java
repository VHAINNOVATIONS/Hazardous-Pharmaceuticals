/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.calls.fatkaat;


import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymException;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.calls.AbstractCall;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.calls.fatkaat.usermanagement.User;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.calls.fatkaat.usermanagement.UserLogon;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Request;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Response;


/**
 * Loaded by pseodonyM server if needed to generate response.  Generates a response object using a Velocity Template File. 
 * Specifically this class is called during the authentication process. 
 */
public class XusDivisionGetViaProxy extends AbstractCall {

    /**
     * default no-arg constructor 
     */
    public XusDivisionGetViaProxy() {
        super();
    }

    /**
     * This method generates a Response object and set's the attribute values of the Response object. 
     * In addition, a template file is used to simplify maintainability.  Upon positive identification
     * the user will also be set as the user for this connection and all future communications on this
     * connection will use the currently verified user.
     * 
     * @param call client request
     * @return Response object
     * @throws PseudonymException If an exception occurred
     * 
     * @see gov.va.med.pharmacy.external.pseudonym.calls.Call#getResponse
     * (gov.va.med.pharmacy.external.pseudonym.vistalink.Request)
     */
    public Response getResponse(Request call) throws PseudonymException {
        if (call.getParameters().size() <= 0) {
            throw new PseudonymException("Parameters size was not greater than 0");
        }

        UserLogon users = UserLogon.instance();
        String decryptedString = ((Request.StringParameter) call.getParameters().get(0)).getDecryptedValue();

        if (users.isValid(decryptedString)) {
            User currentUser = users.verifyLogon(decryptedString);
            users.setAuthenticatedUser(currentUser);

            return new Response(call, processTemplate(this, VALID));
        } else {
            return new Response(call, processTemplate(this, INVALID));
        }
    }

}
