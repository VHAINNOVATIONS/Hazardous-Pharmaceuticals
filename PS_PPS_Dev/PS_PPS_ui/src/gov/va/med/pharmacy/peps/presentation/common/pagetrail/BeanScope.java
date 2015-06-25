/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.pagetrail;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


/**
 * BeanScope's brief summary
 * 
 * Details of BeanScope's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public abstract class BeanScope implements Map<String, Object> {

    /** LOG */
    static final Logger LOG = Logger.getLogger(BeanScope.class);

    /**
     * Constructor
     *
     */
    public BeanScope() {
        super();
    }

    /**
     * getBackingMap
     *
     * @return Map
     */
    abstract Map<String, Object> getBackingMap();

    /**
     * setBackingMap
     *
     * @param backingMap Map
     */
    abstract void setBackingMap(Map<String, Object> backingMap);

    /**
     * getNonNullBackingMap
     *
     * @return Map
     */
    private Map<String, Object> getNonNullBackingMap() {
        Map<String, Object> backingMap = getBackingMap();

        if (backingMap == null) {
            backingMap = new HashMap<String, Object>();
            setBackingMap(backingMap);
        }

        return backingMap;
    }

//    public <T> T get(String key) {
//        return (T) get(key);
//    }

    /**
     * get
     *
     * @param <T> t
     * @param beanClass Class
     * @return <T>
     */
    @Deprecated
    public <T> T get(Class<? extends Object> beanClass) {
        return (T) get(beanClass, true);
    }

    /**
     * get
     *
     * @param <T> t
     * @param beanClass Class
     * @param autoCreate boolean
     * @return <T extends Object> extends Object
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    public <T extends Object> T get(Class<? extends Object> beanClass, boolean autoCreate) {
        T scopedBean = (T) get(beanClass.getName());

        if (scopedBean == null && autoCreate) {
            try {
                scopedBean = (T) beanClass.newInstance();
            } catch (InstantiationException e) {
                LOG.error("An InstantiationException was caught creating a new instance of " + beanClass.getName(), e);
            } catch (IllegalAccessException e) {
                LOG.error("An IllegalAccessException was caught creating a new instance of " + beanClass.getName(), e);
            }

            put(beanClass.getName(), scopedBean);
        }

        return scopedBean;

    }

    /**
     * put
     *
     * @param bean Object
     */
    @Deprecated
    public void put(Object bean) {
        put(bean.getClass().getName(), bean);
    }

    /**
     * remove
     *
     * @param beanClass Class
     */
    @Deprecated
    public void remove(Class<? extends Object> beanClass) {
        remove(beanClass.getName());
    }

    /**
     * getNew
     *
     * @param <T> Object
     * @param beanClass Object
     * @return Object
     */
    @Deprecated
    public <T extends Object> T getNew(Class<? extends Object> beanClass) {
        remove(beanClass);

        return (T) get(beanClass);
    }

    // Map Interface
    @Override
    public Object get(Object key) {
        Object value = null;
        Map<String, Object> backingMap = getBackingMap();

        if (backingMap != null) {
            value = backingMap.get(key);
        }

        return value;
    }

    @Override
    public Object put(String key, Object value) {
        return getNonNullBackingMap().put(key, value);
    }

    @Override
    public int size() {
        int size = 0;
        Map<String, Object> backingMap = getBackingMap();

        if (backingMap != null) {
            size = backingMap.size();
        }

        return size;
    }

    @Override
    public boolean isEmpty() {
        boolean empty = true;
        Map<String, Object> backingMap = getBackingMap();

        if (backingMap != null) {
            empty = backingMap.isEmpty();
        }

        return empty;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean containsKey = false;
        Map<String, Object> backingMap = getBackingMap();

        if (backingMap != null) {
            containsKey = backingMap.containsKey(key);
        }

        return containsKey;
    }

    @Override
    public boolean containsValue(Object value) {
        boolean containsValue = true;
        Map<String, Object> backingMap = getBackingMap();

        if (backingMap != null) {
            containsValue = backingMap.containsValue(value);
        }

        return containsValue;
    }

    @Override
    public Object remove(Object key) {
        Object removedValue = null;
        Map<String, Object> backingMap = getBackingMap();

        if (backingMap != null) {
            removedValue = backingMap.remove(key);
        }

        return removedValue;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> map) {
        getNonNullBackingMap().putAll(map);
    }

    @Override
    public void clear() {
        Map<String, Object> backingMap = getBackingMap();

        if (backingMap != null) {
            backingMap.clear();
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> keySet = null;
        Map<String, Object> backingMap = getBackingMap();

        if (backingMap == null) {
            keySet = new HashSet<String>();
        } else {
            keySet = backingMap.keySet();
        }

        return keySet;
    }

    @Override
    public Collection<Object> values() {
        Collection<Object> values = null;
        Map<String, Object> backingMap = getBackingMap();

        if (backingMap == null) {
            values = new ArrayList<Object>();
        } else {
            values = backingMap.values();
        }

        return values;
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {

        Set<Entry<String, Object>> entrySet = null;
        Map<String, Object> backingMap = getBackingMap();

        if (backingMap == null) {
            entrySet = new HashSet<Entry<String, Object>>();
        } else {
            entrySet = backingMap.entrySet();
        }

        return entrySet;
    }
}
