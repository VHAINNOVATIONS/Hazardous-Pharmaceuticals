/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.test;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import gov.va.med.pharmacy.peps.common.vo.ValueObject;



/**
 * Base class for domain generators.  Derived types must provide vo generator implementation.
 * The domain capabilities provided must support a create method for the value object type.
 * This could have been a bit more elegant, but there's not adequate interface support for 
 * the functionality needed.
 * 
 * To create a new generator class, just extend this class and provide a constructor that delegates to 
 * super and implement the generate method.  A VO generator's pseudoRandom() method should do the trick.
 * 
 * @param <VoType> type of ValueObject handled
 */
public abstract class DomainGenerator<VoType extends ValueObject> {
    private Object domainCapability;
    
    /**
     * Constructor
     * 
     * @param domainCapability domain capability for save operation
     */
    public DomainGenerator(Object domainCapability) { 
        this.domainCapability = domainCapability;
    }
    
    /**
     * implementing class must provide generated value object instance
     * 
     * @return value object instance
     */
    protected abstract VoType generate();
    

    /**
     * Creates and saves
     * @return valueObject
     * @throws NoSuchMethodException NoSuchMethodException
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     */
    public VoType createSaved()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        
        VoType valueObject = generate();
        
        valueObject = create(valueObject);
        
        return valueObject;
    }
    

    /**
     * invokes the domainCapability's create() method
     * 
     * @param valueObject value object to insert
     * @return inserted VoType
     * @throws NoSuchMethodException NoSuchMethodException
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     */
    private VoType create(VoType valueObject) 
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method createMethod = null;
        Class[] methodParamTypes = new Class[1];
        Object[] methodArgs = new Object[1];
        
        methodParamTypes[0] = valueObject.getClass();
        methodArgs[0] = valueObject;
         
        createMethod = domainCapability.getClass().getMethod("create", methodParamTypes);
        
        return (VoType) createMethod.invoke(this.domainCapability, methodArgs);
    }
}
