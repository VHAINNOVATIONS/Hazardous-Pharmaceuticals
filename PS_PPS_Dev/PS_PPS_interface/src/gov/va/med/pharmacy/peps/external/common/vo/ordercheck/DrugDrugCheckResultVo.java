/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Results from a drug-drug check
 */
public class DrugDrugCheckResultVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Collection<DrugCheckVo> drugs = new ArrayList<DrugCheckVo>();
    private String severity = "";
    private String interactionDescription = "";
    private String shortText = "";
    private ProfessionalMonographVo professionalMonograph;
    private ConsumerMonographVo consumerMonograph;

    /**
     * description
     */
    public DrugDrugCheckResultVo() {
        super();
    }

    /**
     * description
     * @return drugs property
     */
    public Collection<DrugCheckVo> getDrugs() {
        return drugs;
    }

    /**
     * description
     * @param drugs drugs property
     */
    public void setDrugs(Collection<DrugCheckVo> drugs) {
        this.drugs = drugs;
    }

    /**
     * description
     * @return interactionDescription property
     */
    public String getInteractionDescription() {
        return interactionDescription;
    }

    /**
     * description
     * @param interactionDescription interactionDescription property
     */
    public void setInteractionDescription(String interactionDescription) {
        this.interactionDescription = interactionDescription;
    }

    /**
     * description
     * @return severity property
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * description
     * @param severity severity property
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * description
     * @return shortText property
     */
    public String getShortText() {
        return shortText;
    }

    /**
     * description
     * @param shortText shortText property
     */
    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    /**
     * description
     * @return professionalMonograph property
     */
    public ProfessionalMonographVo getProfessionalMonograph() {
        return professionalMonograph;
    }

    /**
     * description
     * @param professionalMonograph professionalMonograph property
     */
    public void setProfessionalMonograph(ProfessionalMonographVo professionalMonograph) {
        this.professionalMonograph = professionalMonograph;
    }

    /**
     * description
     * @return consumerMonograph property
     */
    public ConsumerMonographVo getConsumerMonograph() {
        return consumerMonograph;
    }

    /**
     * description
     * @param consumerMonograph consumerMonograph property
     */
    public void setConsumerMonograph(ConsumerMonographVo consumerMonograph) {
        this.consumerMonograph = consumerMonograph;
    }
}
