/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.reports;


/**
 * ReportSubject
 */
public interface ReportSubject {

    /**
     * registerObserver
     * @param pObserver pObserver
     */
    void registerObserver(ReportObserver pObserver);

    /**
     * removeObserver
     * @param pObserver pObserver
     */
    void removeObserver(ReportObserver pObserver);

    /**
     * notifyObservers
     */
    void notifyObservers();
}
