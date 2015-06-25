/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Data representing site configuration
 */
public class InterfaceCounterVo extends ValueObject {

    private static final long serialVersionUID = 1L;

    private String interfaceName;
    private String counterName;
    private Long counterValue;

    /**
     * getInterfaceName.
     * @return interfaceName property
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * setInterfaceName.
     * @param interfaceName interfaceName property
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * getCounterName.
     * @return counterName property
     */
    public String getCounterName() {
        return counterName;
    }

    /**
     * setCounterName.
     * @param counterName counterName property
     */
    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    /**
     * getCounterValue.
     * @return counterValue property
     */
    public Long getCounterValue() {
        return counterValue;
    }

    /**
     * setCounterValue.
     * @param counterValue counterValue property
     */
    public void setCounterValue(Long counterValue) {
        this.counterValue = counterValue;
    }
}
