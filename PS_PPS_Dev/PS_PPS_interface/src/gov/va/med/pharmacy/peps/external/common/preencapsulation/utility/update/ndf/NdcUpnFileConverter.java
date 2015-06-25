/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.ndf;


import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.utility.update.DateFormatter;
import gov.va.med.pharmacy.peps.external.common.utility.AbstractConverter;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.common.ItemAction;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.ndcupn.NdcUpnFile;
import gov.va.med.pharmacy.peps.external.common.vo.outbound.ndf.ndcupn.ObjectFactory;


/**
 * Converts NDC VO to NDC/UPN File document.
 */
public class NdcUpnFileConverter extends AbstractConverter {

    /** FIELDS */
    public static final Set<FieldKey> FIELDS = Collections.unmodifiableSet(new LinkedHashSet<FieldKey>(Arrays
        .asList(new FieldKey[] {
            FieldKey.NDC, FieldKey.UPC_UPN, FieldKey.MANUFACTURER, FieldKey.TRADE_NAME, FieldKey.VA_PRODUCT_NAME,
            FieldKey.INACTIVATION_DATE, FieldKey.PACKAGE_SIZE, FieldKey.PACKAGE_TYPE, FieldKey.OTC_RX_INDICATOR })));

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Hidden constructor.
     */
    private NdcUpnFileConverter() {
    }

    /**
     * Convert NDC VO to NDC/UPN File document.
     * 
     * @param ndcVo NDC VO
     * @param differences old/new value differences
     * @param itemAction add/modify/inactivate
     * @return NDC File
     */
    public static NdcUpnFile toNdcUpnFile(NdcVo ndcVo, Map<FieldKey, Difference> differences, ItemAction itemAction) {
        NdcUpnFile ndcUpnFile = FACTORY.createNdcUpnFile();
        ndcUpnFile.setCandidateKey(FACTORY.createNdcUpnFileCandidateKey());
        ndcUpnFile.setNumber(new Float("50.67"));

        // NDC M/M - Candidate Key
        ndcUpnFile.getCandidateKey().setNdc(FACTORY.createNdcKey());
        ndcUpnFile.getCandidateKey().getNdc().setValue(
            "0" + ((String) toCandidateKeyValue(FieldKey.NDC, differences, ndcVo.getNdc())).replaceAll("-", ""));
        ndcUpnFile.getCandidateKey().getNdc().setNumber(1f);

        // NDC O/O
        if (ItemAction.ADD.equals(itemAction) || hasOldValue(FieldKey.NDC, differences)) {
            ndcUpnFile.setNdc(FACTORY.createNdcKey());
            ndcUpnFile.getNdc().setValue("0" + ndcVo.getNdc().replaceAll("-", ""));
            ndcUpnFile.getNdc().setNumber(1f);
        }

        // UPN O/O
        if (isValid(ndcVo.getUpcUpn()) || isUnset(FieldKey.UPC_UPN, differences)) {
            NdcUpnFile.Upn field = FACTORY.createNdcUpnFileUpn();
            field.setNumber(2f);

            JAXBElement<NdcUpnFile.Upn> element = FACTORY.createNdcUpnFileUpn(field);
            ndcUpnFile.setUpn(element);

            if (isUnset(FieldKey.UPC_UPN, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(ndcVo.getUpcUpn());
            }
        }

        // MANUFACTURER M/O
        ndcUpnFile.setManufacturer(FACTORY.createNdcUpnFileManufacturer());
        ndcUpnFile.getManufacturer().setValue(ndcVo.getManufacturer().getValue());
        ndcUpnFile.getManufacturer().setNumber(new Float("3"));

        // TRADE NAME O/O
        if (isValid(ndcVo.getTradeName()) || isUnset(FieldKey.TRADE_NAME, differences)) {
            NdcUpnFile.TradeName field = FACTORY.createNdcUpnFileTradeName();
            field.setNumber(new Float("4"));

            JAXBElement<NdcUpnFile.TradeName> element = FACTORY.createNdcUpnFileTradeName(field);
            ndcUpnFile.setTradeName(element);

            if (isUnset(FieldKey.TRADE_NAME, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(ndcVo.getTradeName());
            }
        }

        // VA PRODUCT NAME M/O
        ndcUpnFile.setVaProductName(FACTORY.createNdcUpnFileVaProductName());
        ndcUpnFile.getVaProductName().setValue(ndcVo.getProduct().getVaProductName());
        ndcUpnFile.getVaProductName().setNumber(new Float("5"));

        // INACTIVATION DATE O/M
        Date inactivationDate = ndcVo.getInactivationDate();

        if (ItemAction.INACTIVATE.equals(itemAction) || isValid(inactivationDate)
            || isUnset(FieldKey.INACTIVATION_DATE, differences)) {
            NdcUpnFile.InactivationDate field = FACTORY.createNdcUpnFileInactivationDate();
            field.setNumber(new Float("7"));

            JAXBElement<NdcUpnFile.InactivationDate> element = FACTORY.createNdcUpnFileInactivationDate(field);
            ndcUpnFile.setInactivationDate(element);

            if (isUnset(FieldKey.INACTIVATION_DATE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(DateFormatter.toDateString(inactivationDate));
            }
        }

        // PACKAGE SIZE O/O
        if (isValid(ndcVo.getPackageSize()) || isUnset(FieldKey.PACKAGE_SIZE, differences)) {
            NdcUpnFile.PackageSize field = FACTORY.createNdcUpnFilePackageSize();
            field.setNumber(new Float("8"));

            JAXBElement<NdcUpnFile.PackageSize> element = FACTORY.createNdcUpnFilePackageSize(field);
            ndcUpnFile.setPackageSize(element);

            if (isUnset(FieldKey.PACKAGE_SIZE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(String.valueOf(ndcVo.getPackageSize()));
            }
        }

        // PACKAGE TYPE O/O
        if (isValid(ndcVo.getPackageType()) || isUnset(FieldKey.PACKAGE_TYPE, differences)) {
            NdcUpnFile.PackageType field = FACTORY.createNdcUpnFilePackageType();
            field.setNumber(new Float("9"));

            JAXBElement<NdcUpnFile.PackageType> element = FACTORY.createNdcUpnFilePackageType(field);
            ndcUpnFile.setPackageType(element);

            if (isUnset(FieldKey.PACKAGE_TYPE, differences)) { // unset
                element.setNil(true);
            } else { // set
                field.setValue(ndcVo.getPackageType().getValue());
            }
        }

        // OTX/RX INDICATOR O/O
        if (isValid(ndcVo.getOtcRxIndicator()) || isUnset(FieldKey.OTC_RX_INDICATOR, differences)) {
            NdcUpnFile.OtxRxIndicator field = FACTORY.createNdcUpnFileOtxRxIndicator();
            field.setNumber(new Float("10"));

            JAXBElement<NdcUpnFile.OtxRxIndicator> element = FACTORY.createNdcUpnFileOtxRxIndicator(field);
            ndcUpnFile.setOtxRxIndicator(element);

            if (isUnset(FieldKey.OTC_RX_INDICATOR, differences)) { // unset
                element.setNil(true);
            } else { // set
                if (PPSConstants.OVER_THE_COUNTER.equalsIgnoreCase(ndcVo.getOtcRxIndicator().getValue())) { // Over the Counter
                    field.setValue("O");
                } else if (PPSConstants.PRESCRIPTION.equalsIgnoreCase(ndcVo.getOtcRxIndicator().getValue())) { // Prescription
                    field.setValue("R");
                }
            }
        }

        return ndcUpnFile;
    }


}
