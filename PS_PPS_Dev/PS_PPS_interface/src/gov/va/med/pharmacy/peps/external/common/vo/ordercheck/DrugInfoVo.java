/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Data representing a request and response for drug information.
 */
public class DrugInfoVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private String gcnSeqNo = "";
    private String strengthUnit = "";
    private Collection<DoseRouteVo> doseRoutes = new ArrayList<DoseRouteVo>();
    private Collection<DoseTypeVo> doseTypes = new ArrayList<DoseTypeVo>();

    /**
     * description
     * @return gcnSeqNo property
     */
    public String getGcnSeqNo() {
        return gcnSeqNo;
    }

    /**
     * description
     * @param gcnSeqNo gcnSeqNo property
     */
    public void setGcnSeqNo(String gcnSeqNo) {
        this.gcnSeqNo = gcnSeqNo;
    }

    /**
     * description
     * @return doseRoutes property
     */
    public Collection<DoseRouteVo> getDoseRoutes() {
        return doseRoutes;
    }

    /**
     * description
     * @param doseRoutes doseRoutes property
     */
    public void setDoseRoutes(Collection<DoseRouteVo> doseRoutes) {
        this.doseRoutes = doseRoutes;
    }

    /**
     * description
     * @return doseTypes property
     */
    public Collection<DoseTypeVo> getDoseTypes() {
        return doseTypes;
    }

    /**
     * description
     * @param doseTypes doseTypes property
     */
    public void setDoseTypes(Collection<DoseTypeVo> doseTypes) {
        this.doseTypes = doseTypes;
    }

    /**
     * description
     * @return strengthUnit property
     */
    public String getStrengthUnit() {
        return strengthUnit;
    }

    /**
     * description
     * @param strengthUnit strengthUnit property
     */
    public void setStrengthUnit(String strengthUnit) {
        this.strengthUnit = strengthUnit;
    }
}
