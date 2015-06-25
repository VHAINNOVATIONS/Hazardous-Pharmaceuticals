/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.utility.test.generator;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Supply default behavior for a VO generator
 * 
 * @param <T> Type of VO this generator creates
 */
public abstract class VoGenerator<T extends ValueObject> {

    /** I_3 */
    protected static final int I_3 = 3;

    /** I_5 */
    protected static final int I_5 = 5;

    /** I_6 */
    protected static final int I_6 = 6;

    /** I_9 */
    protected static final int I_9 = 9;

    /** I_10 */
    protected static final int I_10 = 10;

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private List<T> list = new ArrayList<T>();

    /**
     * Add the list of generated ValueObjects.
     */
    public VoGenerator() {
        doInitialization();
        
        list.addAll(generate());
    }
    
    /**
     * Allows derived classes to specify initialization operations prior to the call to generate().
     */
    protected abstract void doInitialization();

    /**
     * Generate the list of ValueObjects. This method is called once by the constructor.
     * 
     * @return List of generated ValueObjects
     */
    protected abstract List<T> generate();

    /**
     * Get the first ValueObject in the list (i.e., this method will always return the same VO).
     * 
     * @return ValueObject first in the list
     */
    public T getFirst() {
        return list.get(0);
    }

    /**
     * Get a random ValueObject in the list.
     * 
     * @return random ValueObject
     */
    public T getRandom() {
        return list.get(Math.abs(RANDOM.nextInt(list.size())));
    }

    /**
     * Get the full list of generated ValueObjects.
     * 
     * @return List of ValueObjects
     */
    public List<T> getList() {
        return list;
    }
}
