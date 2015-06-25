/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.messagingservice.test.stub;


import java.io.Serializable;
import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;


/**
 * Stub of javax.jms.ObjectMessage that allows us to test outside of the container.
 * 
 * NOTE: This ObjectMessage instance cannot be sent successfully to a Queue or Topic. 
 * It only implements the get/setObject methods!
 */
public class ObjectMessageStub implements ObjectMessage {
    private Serializable object;

    /**
     * Empty constructor
     */
    public ObjectMessageStub() {
        super();
    }

    /**
     *   setObject
     *    
     * @param object Object
     * @throws JMSException JMSException
     * 
     * @see javax.jms.ObjectMessage#setObject(java.io.Serializable)
     */
    public void setObject(Serializable object) throws JMSException {
        this.object = object;
    }

    /**
     * getObject
     * @return Serializable
     * @throws JMSException JMSException
     * 
     * @see javax.jms.ObjectMessage#getObject()
     */
    public Serializable getObject() throws JMSException {
        return object;
    }

    /**
     * getJMSMessageID
     * @return String
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getJMSMessageID()
     */
    public String getJMSMessageID() throws JMSException {
        return null;
    }

    /**
     * setJMSMessageID
     * @param arg0 ID
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setJMSMessageID(java.lang.String)
     */
    public void setJMSMessageID(String arg0) throws JMSException {
    }

    /**
     * getJMSTimestamp
     * @return long
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getJMSTimestamp()
     */
    public long getJMSTimestamp() throws JMSException {
        return 0;
    }

    /**
     * setJMSTimestamp
     * @param arg0 timestamp
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setJMSTimestamp(long)
     */
    public void setJMSTimestamp(long arg0) throws JMSException {
    }

    /**
     *  getJMSCorrelationIDAsBytes
     * @return byte array
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getJMSCorrelationIDAsBytes()
     */
    public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
        return null;
    }

    /**
     * setJMSCorrelationIDAsBytes
     * @param arg0 byte array
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setJMSCorrelationIDAsBytes(byte[])
     */
    public void setJMSCorrelationIDAsBytes(byte[] arg0) throws JMSException {
    }

    /**
     * setJMSCorrelationID
     * @param arg0 string
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setJMSCorrelationID(java.lang.String)
     */
    public void setJMSCorrelationID(String arg0) throws JMSException {
    }

    /**
     * getJMSCorrelationID
     * @return string
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getJMSCorrelationID()
     */
    public String getJMSCorrelationID() throws JMSException {
        return null;
    }

    /**
     * getJMSReplyTo
     * @return destination
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getJMSReplyTo()
     */
    public Destination getJMSReplyTo() throws JMSException {
        return null;
    }

    /**
     * setJMSReplyTo
     * @param arg0 destination
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setJMSReplyTo(javax.jms.Destination)
     */
    public void setJMSReplyTo(Destination arg0) throws JMSException {
    }

    /**
     * getJMSDestination
     * @return destination
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getJMSDestination()
     */
    public Destination getJMSDestination() throws JMSException {
        return null;
    }

    /**
     * setJMSDestination
     * @param arg0 destination
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setJMSDestination(javax.jms.Destination)
     */
    public void setJMSDestination(Destination arg0) throws JMSException {
    }

    /**
     * getJMSDeliveryMode
     * @return int
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getJMSDeliveryMode()
     */
    public int getJMSDeliveryMode() throws JMSException {
        return 0;
    }

    /**
     * setJMSDeliveryMode
     * @param arg0 int
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setJMSDeliveryMode(int)
     */
    public void setJMSDeliveryMode(int arg0) throws JMSException {
    }

    /**
     * getJMSRedelivered
     * @return boolean
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getJMSRedelivered()
     */
    public boolean getJMSRedelivered() throws JMSException {
        return false;
    }

    /**
     * setJMSRedelivered
     * @param arg0 boolean
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setJMSRedelivered(boolean)
     */
    public void setJMSRedelivered(boolean arg0) throws JMSException {
    }

    /**
     * getJMSType
     * @return string
     * @throws JMSException JMSExceptionJMSException
     * 
     * @see javax.jms.Message#getJMSType()
     */
    public String getJMSType() throws JMSException {
        return null;
    }

    /**
     * setJMSType
     * @param arg0 string
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setJMSType(java.lang.String)
     */
    public void setJMSType(String arg0) throws JMSException {
    }

    /**
     * getJMSExpiration
     * @return long
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getJMSExpiration()
     */
    public long getJMSExpiration() throws JMSException {
        return 0;
    }

    /**
     * setJMSExpiration
     * @param arg0 long
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setJMSExpiration(long)
     */
    public void setJMSExpiration(long arg0) throws JMSException {
    }

    /**
     * getJMSPriority
     * @return int
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getJMSPriority()
     */
    public int getJMSPriority() throws JMSException {
        return 0;
    }

    /**
     * setJMSPriority
     * @param arg0 int
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setJMSPriority(int)
     */
    public void setJMSPriority(int arg0) throws JMSException {
    }

    /**
     * clearProperties
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#clearProperties()
     */
    public void clearProperties() throws JMSException {
    }

    /**
     * propertyExists
     * @param arg0 string
     * @return boolean
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#propertyExists(java.lang.String)
     */
    public boolean propertyExists(String arg0) throws JMSException {
        return false;
    }

    /**
     * getBooleanProperty
     * @param arg0 string
     * @return boolean
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getBooleanProperty(java.lang.String)
     */
    public boolean getBooleanProperty(String arg0) throws JMSException {
        return false;
    }

    /**
     * getByteProperty
     * @param arg0 string
     * @return byte
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getByteProperty(java.lang.String)
     */
    public byte getByteProperty(String arg0) throws JMSException {
        return 0;
    }

    /**
     * getShortProperty
     * @param arg0 string
     * @return short
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getShortProperty(java.lang.String)
     */
    public short getShortProperty(String arg0) throws JMSException {
        return 0;
    }

    /**
     * getIntProperty
     * @param arg0 string
     * @return int
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getIntProperty(java.lang.String)
     */
    public int getIntProperty(String arg0) throws JMSException {
        return 0;
    }

    /**
     * getLongProperty
     * @param arg0 string
     * @return long
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getLongProperty(java.lang.String)
     */
    public long getLongProperty(String arg0) throws JMSException {
        return 0;
    }

    /**
     * getFloatProperty
     * @param arg0 string
     * @return float
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getFloatProperty(java.lang.String)
     */
    public float getFloatProperty(String arg0) throws JMSException {
        return 0;
    }

    /**
     * getDoubleProperty
     * @param arg0 string
     * @return double
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getDoubleProperty(java.lang.String)
     */
    public double getDoubleProperty(String arg0) throws JMSException {
        return 0;
    }

    /**
     * getStringProperty
     * @param arg0 string
     * @return string
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getStringProperty(java.lang.String)
     */
    public String getStringProperty(String arg0) throws JMSException {
        return null;
    }

    /**
     * getObjectProperty
     * @param arg0 string
     * @return object
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getObjectProperty(java.lang.String)
     */
    public Object getObjectProperty(String arg0) throws JMSException {
        return null;
    }

    /**
     * getPropertyNames
     * @return enumeration
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#getPropertyNames()
     */
    public Enumeration getPropertyNames() throws JMSException {
        return null;
    }

    /**
     * setBooleanProperty
     * @param arg0 string
     * @param arg1 boolean
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setBooleanProperty(java.lang.String, boolean)
     */
    public void setBooleanProperty(String arg0, boolean arg1) throws JMSException {
    }

    /**
     * setByteProperty
     * @param arg0 string
     * @param arg1 byte
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setByteProperty(java.lang.String, byte)
     */
    public void setByteProperty(String arg0, byte arg1) throws JMSException {
    }

    /**
     * setShortProperty
     * @param arg0 string
     * @param arg1 short
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setShortProperty(java.lang.String, short)
     */
    public void setShortProperty(String arg0, short arg1) throws JMSException {
    }

    /**
     * setIntProperty
     * @param arg0 string
     * @param arg1 int
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setIntProperty(java.lang.String, int)
     */
    public void setIntProperty(String arg0, int arg1) throws JMSException {
    }

    /**
     * setLongProperty
     * @param arg0 string
     * @param arg1 long
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setLongProperty(java.lang.String, long)
     */
    public void setLongProperty(String arg0, long arg1) throws JMSException {
    }

    /**
     * setFloatProperty
     * @param arg0 string
     * @param arg1 float
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setFloatProperty(java.lang.String, float)
     */
    public void setFloatProperty(String arg0, float arg1) throws JMSException {
    }

    /**
     * setDoubleProperty
     * @param arg0 string
     * @param arg1 double
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setDoubleProperty(java.lang.String, double)
     */
    public void setDoubleProperty(String arg0, double arg1) throws JMSException {
    }

    /**
     * setStringProperty
     * @param arg0 string
     * @param arg1 string
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setStringProperty(java.lang.String, java.lang.String)
     */
    public void setStringProperty(String arg0, String arg1) throws JMSException {
    }

    /**
     * setObjectProperty
     * @param arg0 string
     * @param arg1 object
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#setObjectProperty(java.lang.String, java.lang.Object)
     */
    public void setObjectProperty(String arg0, Object arg1) throws JMSException {
    }

    /**
     * acknowledge
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#acknowledge()
     */
    public void acknowledge() throws JMSException {
    }

    /**
     * clearBody
     * @throws JMSException JMSException
     * 
     * @see javax.jms.Message#clearBody()
     */
    public void clearBody() throws JMSException {
    }

}
