/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.pagetrail;


import org.apache.commons.lang.StringUtils;


/**
 * A simple bean to represent a page in the PageTrail.
 */
public class Page {

    private final String id;
    private final String label;
    private final String url;

    /**
     * The constructor for this Page bean.
     *
     * @param id the id of this Page.
     * @param label the label for this Page.
     * @param url the URL for this Page.
     */
    public Page(String id, String label, String url) {
        super();

        this.id = id;
        this.label = label;
        this.url = url;
    }

    /**
     * Returns the id of this Page.
     *
     * @return the id of this Page.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the label for this Page.
     *
     * @return the label for this Page.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the URL for this Page.
     *
     * @return the URL for this Page.
     */
    public String getUrl() {
        return url;
    }

    /**
     * hashCode
     * 
     * @see java.lang.Object#hashCode()
     * 
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());

        return result;
    }

    /**
     * equals
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Page other = (Page) obj;

        if (id == null) {

            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }

        return true;
    }

    /**
     * Returns the a RoboHelp label for this Page 
     * with all of the spaces replaced with underscores.
     *
     * @return the RoboHelp label for this Page.
     */
    public String getRoboHelpLabel() {
        return StringUtils.replace(label, " ", "_");
    }
}
