/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.pagetrail;


import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * FlowScope's brief summary
 * 
 * Details of FlowScope's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Component
public class FlowScope extends BeanScope {

    /** session */
    @Autowired
    HttpSession session;

    @Override
    Map<String, Object> getBackingMap() {
        return (Map<String, Object>) session.getAttribute(FlowScope.class.getName());
    }

    @Override
    void setBackingMap(Map<String, Object> backingMap) {
        session.setAttribute(FlowScope.class.getName(), backingMap);
    }
}
