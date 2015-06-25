/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.Selectable;
import gov.va.med.pharmacy.peps.presentation.common.utility.DomainUtility;


/**
 * AjaxController's brief summary
 * 
 * Details of AjaxController's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Controller
public class AjaxController extends PepsController {

    @Autowired
    private DomainUtility domainUtility;

    /**
     * Retrieves the domains for a given fieldKey
     *
     * @param fieldKey - of domain to lookup
     * @return String - JSON String of domains
     * 
     */
    @RequestMapping(value = "retrieveDomains.go", method = RequestMethod.GET)
    public @ResponseBody
    String retrieveDomains(@RequestParam(value = "fieldKey", required = true) String fieldKey) {

        StringBuilder jsonString = new StringBuilder("[");

        List<Selectable> domains = domainUtility.getDomain(FieldKey.getKey(fieldKey));

        int i = 1;

        for (Selectable domain : domains) {
            jsonString.append("{\"");
            jsonString.append(i - 1);
            jsonString.append("\":[{");
            jsonString.append("\"value\":\"");
            jsonString.append(domain.getId());
            jsonString.append("\",\"display\":\"");
            jsonString.append(domain.getValue());
            jsonString.append("\"}]}");
            i++;

            if (i <= domains.size()) {
                jsonString.append(",");
            }
        }

        jsonString.append("]");

        return StringEscapeUtils.unescapeJava(jsonString.toString());
    }

    /**
     * Retrieves the domains for a given fieldKey
     *
     * @param fieldKey - of domain to lookup
     * @return String - JSON String of domains
     * 
     */
    @RequestMapping(value = "updateSpecialHandling.go", method = RequestMethod.POST)
    public @ResponseBody
    String drugClassSupplyRules() {

        request.getParameterMap();

        return StringEscapeUtils.unescapeJava("Posted successfully");
    }

    /**
     * setDomainUtility
     * @param domainUtility the domainUtility to set
     */
    public void setDomainUtility(DomainUtility domainUtility) {
        this.domainUtility = domainUtility;
    }

    /**
     * getDomainUtility
     * @return the domainUtility
     */
    public DomainUtility getDomainUtility() {
        return domainUtility;
    }

}
