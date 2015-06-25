/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo;


import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Super class for generated XML classes to implement {@link #equals(Object)} and {@link #hashCode()}.
 * <p>
 * Marked as {@link XmlTransient} to resolve "@XmlValue is not allowed on a class that derives another class" error.
 * 
 * @see http://sites.google.com/site/codingkb/java-2/jaxb/jaxb-4
 */
@XmlTransient
public class XmlValueObject {

    /**
     * Equals returned by Jakarta Commons EqualsBuilder
     * 
     * @param obj Object to check equals against
     * @return true if equal
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * HashCode built by Jakarta Commons HasCodeBuilder
     * 
     * @return int hash
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
