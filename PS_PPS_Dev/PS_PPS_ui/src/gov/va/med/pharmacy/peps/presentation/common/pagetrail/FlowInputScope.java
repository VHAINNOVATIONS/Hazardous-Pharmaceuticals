/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.pagetrail;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * FlowInputScope's brief summary
 * 
 * Details of FlowInputScope's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Component
public class FlowInputScope extends BeanScope {

    /** flashScope */
    @Autowired
    FlashScope flashScope;

    @Override
    Map<String, Object> getBackingMap() {

        return (Map<String, Object>) flashScope.get(FlowInputScope.class.getName());
    }

    @Override
    void setBackingMap(Map<String, Object> backingMap) {
        flashScope.put(FlowInputScope.class.getName(), backingMap);
    }
}
