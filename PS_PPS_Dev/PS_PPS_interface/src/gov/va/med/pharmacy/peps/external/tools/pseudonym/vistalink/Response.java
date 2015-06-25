/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink;


/**
 *  Class that is used to setup a response to a command.  This class abstracts away the generation of the VistALink response
 *  message.  It allows specific request handling methods to be concerned only about the response data and not the XML
 *  formatting of the response and other common features.
 */
public class Response {
    
    /**
     * DEFAULT_MESSAGE_TYPE
     */
    public static final String DEFAULT_MESSAGE_TYPE = "gov.va.med.foundations.rpc.response";
    
    /**
     * FAULT_MESSAGE_TYPE
     */
    public static final String FAULT_MESSAGE_TYPE = "gov.va.med.foundations.rpc.fault";
    
    private static final String XML_VERSION = "1.0";
    private static final String ENCODING = "utf-8";
    private static final String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String DEFAULT_NO_NAMESPACE_SCHEMA_LOCATION = "rpcResponse.xsd";

    private String response;
    private String messageType;
    private String noNamespaceSchemaLocation;
    private boolean generateXml;

    /**
     * Constructor to setup an empty response object.
     */
    public Response() {

    }

    /**
     * Set the response text.
     * 
     * @param response String response
     */
    public Response(String response) {
        this.response = response;
        this.generateXml = false;
    }

    /**
     * Constructor used to setup the response object with whatever data the method wants to be sent to VistALink.
     * 
     * @param called the request object that was used to make the call
     * @param data the correct response string
     */
    public Response(Request called, String data) {
        response = data;
        this.messageType = DEFAULT_MESSAGE_TYPE;
        this.noNamespaceSchemaLocation = DEFAULT_NO_NAMESPACE_SCHEMA_LOCATION;
        this.generateXml = true;
    }

    /**
     * Constructor used to setup the response object with whatever data the method wants to be sent to VistALink as well as
     * the type of message that will be returned if it does not match the default type.
     * 
     * @param called the request object that was used to make the call
     * @param data the correct response string
     * @param messageType the type of message for this response
     */
    public Response(Request called, String data, String messageType) {
        response = data;
        this.messageType = messageType;
        this.noNamespaceSchemaLocation = DEFAULT_NO_NAMESPACE_SCHEMA_LOCATION;
        this.generateXml = true;
    }

    /**
     * Constructor used to setup the response object with whatever data the method wants to be sent to VistALink as well as
     * the type of message that will be returned if it does not match the default type and also sets up the 
     * noNamespaceSchemaLocation if it does not match the default value.
     * 
     * @param called the request object that was used to make the call
     * @param data the correct response string
     * @param messageType the type of message for this response
     * @param noNamespaceSchemaLocation the schema location if not the default
     */
    public Response(Request called, String data, String messageType, String noNamespaceSchemaLocation) {
        response = data;
        this.messageType = messageType;
        this.noNamespaceSchemaLocation = noNamespaceSchemaLocation;
        this.generateXml = true;
    }

    /**
     * This method is used to build the VistALink response based on all the information that was specified while constructing
     * the object.
     * 
     * @return string representing the results of the call
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (generateXml) {
            StringBuffer compiled = new StringBuffer();

            compiled.append("<?xml version=\"").append(XML_VERSION).append("\" encoding=\"").append(ENCODING).
                append("\" ?><VistaLink messageType=\"").append(messageType).append("\" version=\"1.5\" xmlns:xsi=\"").
                append(XMLNS_XSI).append("\" xsi:noNamespaceSchemaLocation=\"").append(
                noNamespaceSchemaLocation).append("\">").append(response).append("</VistaLink>");

            return compiled.toString();
        } else {
            return response;
        }

    }
}
