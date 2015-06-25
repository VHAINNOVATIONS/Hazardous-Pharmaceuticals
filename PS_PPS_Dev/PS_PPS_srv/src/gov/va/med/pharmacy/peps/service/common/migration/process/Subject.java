/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration.process;


/**
 * public interface Subject
 *
 */
public interface Subject {
    
    /**
     * registerObserver
     * @param pObserver pObserver
     */
    void registerObserver(Observer pObserver);
    
    /**
     * removeObserver
     * @param pObserver pObserver
     */
    void removeObserver(Observer pObserver);
    
    /**
     * notifyObservers
     */
    void notifyObservers();
}
