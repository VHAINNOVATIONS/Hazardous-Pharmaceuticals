/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * OI Route group data field
 */
public class OiRouteVo extends ValueObject {

    private static final long serialVersionUID = 1L;

    private LocalMedicationRouteVo localMedicationRoute;
    private boolean defaultRoute;

    /**
     * getLocalMedicationRoute
     * 
     * @return LocalMedicationRouteVo
     */
    public LocalMedicationRouteVo getLocalMedicationRoute() {

        return localMedicationRoute;
    }

    /**
     * setLocalMedicationRoute
     * 
     * @param route LocalMedicationRouteVo
     */
    public void setLocalMedicationRoute(LocalMedicationRouteVo route) {

        this.localMedicationRoute = route;
    }

    /**
     * isDefaultRoute
     * 
     * @return boolean isDefault
     */
    public boolean isDefaultRoute() {

        return defaultRoute;
    }

    /**
     * setDefaultRoute
     * 
     * @param defaultRoute boolean
     */
    public void setDefaultRoute(boolean defaultRoute) {

        this.defaultRoute = defaultRoute;
    }

    /**
     * This is the OI Route Vo short string method. 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {

        //String s1 = FieldKey.getLocalizedName(FieldKey.OI_ROUTE, Locale.getDefault());
        //String s2 = FieldKey.getLocalizedName(FieldKey.DEFAULT_ROUTE, Locale.getDefault());

        //added for text only display
        String s3 = localMedicationRoute.toShortString();

        //return (s1 + ": " 
        //           + (localMedicationRoute != null ? localMedicationRoute.toShortString() : "(Not specified)") 
        //           + "<p>" + 
        //        s2 + ": " + (defaultRoute ? "Is Default" : "Not Default") + "<p>");

        return s3;
    }
}
