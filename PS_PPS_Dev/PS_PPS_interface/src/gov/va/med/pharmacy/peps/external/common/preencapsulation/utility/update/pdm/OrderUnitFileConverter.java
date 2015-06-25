/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.pdm;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.OrderUnitVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.orderunit.ObjectFactory;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.pdm.orderunit.OrderUnitFile;


/**
 * Converts an Order Unit VO to an Order Unit File.
 */
public class OrderUnitFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] { FieldKey.VALUE, FieldKey.EXPANSION })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     *  Hidden constructor.
     */
    private OrderUnitFileConverter() {
    }


    /**
     * Convert an Order Unit VO to an Order Unit File.
     * 
     * @param orderUnitVo Order Unit VO
     * @param differences Differences
     * @param itemAction Add/Modify/Inactivate
     * @return Order Unit
     */
    public static OrderUnitFile toOrderUnitFile(OrderUnitVo orderUnitVo, Map<FieldKey, Difference> differences,
                                                ItemAction itemAction) {

        OrderUnitFile orderUnitFile = FACTORY.createOrderUnitFile();
        orderUnitFile.setCandidateKey(FACTORY.createOrderUnitFileCandidateKey());
        orderUnitFile.setNumber(new Float("51.5"));

        // ABBREVIATION M - Candidate Key
        orderUnitFile.getCandidateKey().setAbbreviation(FACTORY.createAbbreviationKey());
        orderUnitFile.getCandidateKey().getAbbreviation().setValue(
            (String) toCandidateKeyValue(FieldKey.VALUE, differences, orderUnitVo.getValue()));
        orderUnitFile.getCandidateKey().getAbbreviation().setNumber(PPSConstants.F0POINT01);

        // ABBREVIATION M
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.VALUE, differences)) {
            orderUnitFile.setAbbreviation(FACTORY.createAbbreviationKey());
            orderUnitFile.getAbbreviation().setValue(orderUnitVo.getValue());
            orderUnitFile.getAbbreviation().setNumber(PPSConstants.F0POINT01);
        }

        // EXPANSION O
        if (isValid(orderUnitVo.getExpansion()) || isUnset(FieldKey.EXPANSION, differences)) {
            OrderUnitFile.Expansion field = FACTORY.createOrderUnitFileExpansion();
            field.setNumber(PPSConstants.F0POINT02);

            JAXBElement<OrderUnitFile.Expansion> element = FACTORY.createOrderUnitFileExpansion(field);

            orderUnitFile.setExpansion(element);

            if (isUnset(FieldKey.EXPANSION, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(orderUnitVo.getExpansion());
            }
        }

        return orderUnitFile;

    }
}
