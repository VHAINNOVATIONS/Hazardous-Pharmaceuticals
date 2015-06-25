/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import java.math.BigInteger;

import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * OrderCheckHeaderVo's brief summary
 * 
 * Details of OrderCheckHeaderVo's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class OrderCheckHeaderVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    private String time = "";
    private BigInteger jobNumber = new BigInteger("0");
    private BigInteger duz = new BigInteger("0");
    private String userName = "";
    private BigInteger stationNumber = new BigInteger("0");
    private String serverName = "";
    private String ip = "";
    private String uci = "";
    private String namespace = "";
    private boolean pingOnly;

    /**
     * Empty constructor
     */
    public OrderCheckHeaderVo() {
        super();
    }

    /**
     * description
     * @return duz property
     */
    public BigInteger getDuz() {
        return duz;
    }

    /**
     * description
     * @param duz duz property
     */
    public void setDuz(BigInteger duz) {
        this.duz = duz;
    }

    /**
     * description
     * @return ip property
     */
    public String getIp() {
        return ip;
    }

    /**
     * description
     * @param ip ip property
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * description
     * @return jobNumber property
     */
    public BigInteger getJobNumber() {
        return jobNumber;
    }

    /**
     * description
     * @param jobNumber jobNumber property
     */
    public void setJobNumber(BigInteger jobNumber) {
        this.jobNumber = jobNumber;
    }

    /**
     * description
     * @return namespace property
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * description
     * @param namespace namespace property
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * description
     * @return serverName property
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * description
     * @param serverName serverName property
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * description
     * @return stationNumber property
     */
    public BigInteger getStationNumber() {
        return stationNumber;
    }

    /**
     * description
     * @param stationNumber stationNumber property
     */
    public void setStationNumber(BigInteger stationNumber) {
        this.stationNumber = stationNumber;
    }

    /**
     * description
     * @return time property
     */
    public String getTime() {
        return time;
    }

    /**
     * description
     * @param time time property
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * description
     * @return uci property
     */
    public String getUci() {
        return uci;
    }

    /**
     * description
     * @param uci uci property
     */
    public void setUci(String uci) {
        this.uci = uci;
    }

    /**
     * description
     * @return userName property
     */
    public String getUserName() {
        return userName;
    }

    /**
     * description
     * @param userName userName property
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * description
     * @return pingOnly property
     */
    public boolean isPingOnly() {
        return pingOnly;
    }

    /**
     * description
     * @param pingOnly pingOnly property
     */
    public void setPingOnly(boolean pingOnly) {
        this.pingOnly = pingOnly;
    }
}
