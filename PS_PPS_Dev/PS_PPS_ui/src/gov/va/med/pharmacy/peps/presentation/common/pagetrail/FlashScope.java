/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.pagetrail;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * FlashScope's brief summary
 * 
 * Details of FlashScope's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Component
public class FlashScope extends BeanScope {

    @Autowired
    private HttpServletRequest request;

    @Override
    Map<String, Object> getBackingMap() {

        return (Map<String, Object>) request.getAttribute(FlashScope.class.getName());
    }

    @Override
    void setBackingMap(Map<String, Object> backingMap) {

        // remove the entries from the old backing map from the request
        Map<String, Object> oldBackingMap = (Map<String, Object>) request.getAttribute(FlashScope.class.getName());

        if (oldBackingMap != null) {

            for (String key : oldBackingMap.keySet()) {
                request.removeAttribute(key);
            }
        }

        // copy the new values into request from the backing map
        for (String key : backingMap.keySet()) {
            request.setAttribute(key, backingMap.get(key));
        }

        // save the new backing map in the request for the intercepter to move to the next request
        request.setAttribute(FlashScope.class.getName(), backingMap);
    }

    @Override
    public Object get(Object key) {

        Object requestValue = request.getAttribute((String) key);

        return requestValue;
    }

    @Override
    public Object put(String key, Object value) {
        Object oldValue = super.put(key, value);
        request.setAttribute(key, value);

        return oldValue;
    }

    @Override
    public Object remove(Object key) {
        super.remove(key);

        Object value = get(key);
        request.removeAttribute((String) key);

        return value;
    }

}
