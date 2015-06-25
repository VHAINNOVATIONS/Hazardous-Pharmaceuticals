/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.bean;


import org.apache.commons.lang.StringUtils;


/**
 * ReferrerBean's brief summary
 * 
 * Details of ReferrerBean's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public class ReferrerBean {

    private String originatingRedirectReferrer;
    private String previousWizardPageRedirectReferrer;

    /**
     * getOriginatingRedirectReferrer
     * @return the originatingRedirectReferrer
     */
    public String getOriginatingRedirectReferrer() {
        return StringUtils.isEmpty(originatingRedirectReferrer) ? "redirect:/searchItems.go" : originatingRedirectReferrer;
    }

    /**
     * setOriginatingRedirectReferrer
     * @param originatingRedirectReferrer the originatingRedirectReferrer to set
     */
    public void setOriginatingRedirectReferrer(String originatingRedirectReferrer) {
        this.originatingRedirectReferrer = originatingRedirectReferrer;
    }

    /**
     * getPreviousWizardPageRedirectReferrer
     * @return the previousWizardPageRedirectReferrer
     */
    public String getPreviousWizardPageRedirectReferrer() {

        return StringUtils.isEmpty(previousWizardPageRedirectReferrer) ? "redirect:/searchItems.go"
                                                                      : previousWizardPageRedirectReferrer;
    }

    /**
     * setPreviousWizardPageRedirectReferrer
     * @param previousWizardPageRedirectReferrer the previousWizardPageRedirectReferrer to set
     */
    public void setPreviousWizardPageRedirectReferrer(String previousWizardPageRedirectReferrer) {
        this.previousWizardPageRedirectReferrer = previousWizardPageRedirectReferrer;
    }
}
