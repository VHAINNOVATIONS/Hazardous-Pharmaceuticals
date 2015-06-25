/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PharmacySystemPmisLanguage;
import gov.va.med.pharmacy.peps.common.vo.PharmacySystemVo;
import gov.va.med.pharmacy.peps.common.vo.PharmacySystemWarningLabelSource;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplPharmacySystemDo;


/**
 * Convert to/from {@link PharmacySystemVo} and {@link EplPharmacySystemDo}.
 */
public class PharmacySystemConverter extends
        Converter<PharmacySystemVo, EplPharmacySystemDo> {

    /**
     * Fully copies data from the given PharmacySystemVo into a
     * {@link DataObject}.
     * 
     * @param data PharmacySystemVo to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplPharmacySystemDo toDataObject(PharmacySystemVo data) {
        EplPharmacySystemDo dataObject = new EplPharmacySystemDo();
        dataObject.setSiteNumber(data.getPsSiteNumber());

        dataObject.setPsAnd(data.getPsAnd());

        if (data.getPsCmopWarningLabelSource() != null) {
            dataObject.setPsCmopWarningLabelSource(data
                    .getPsCmopWarningLabelSource().toString());
        }

        dataObject.setPsDays(data.getPsDays());
        dataObject.setPsExcept(data.getPsExcept());
        dataObject.setPsEight(data.getPsEight());
        dataObject.setPsFive(data.getPsFive());
        dataObject.setPsFor(data.getPsFor());
        dataObject.setPsFour(data.getPsFour());
        dataObject.setPsHours(data.getPsHours());
        dataObject.setPsMinutes(data.getPsMinutes());
        dataObject.setPsMonths(data.getPsMonths());
        dataObject.setPsNine(data.getPsNine());
        dataObject.setPsOne(data.getPsOne());
        dataObject.setPsOneFourth(data.getPsOneFourth());
        dataObject.setPsOneHalf(data.getPsOneHalf());
        dataObject.setPsOneThird(data.getPsOneThird());
        dataObject.setCreatedBy(data.getCreatedBy());
        dataObject.setCreatedDtm(data.getCreatedDate());
        dataObject.setLastModifiedBy(data.getModifiedBy());
        dataObject.setLastModifiedDtm(data.getModifiedDate());

        if (data.getPsOpaiWarningLabelSource() != null) {
            dataObject.setPsOpaiWarningLabelSource(data
                    .getPsOpaiWarningLabelSource().toString());
        }

        if (data.getPsPmisLanguage() != null) {
            dataObject.setPsPmisLanguage(data.getPsPmisLanguage().toString());
        }

        dataObject.setPsPmisPrinter(data.getPsPmisPrinter());
        dataObject.setPsPmisSectionDelete(data.getPsPmisSectionDelete());
        dataObject.setPsSeconds(data.getPsSeconds());
        dataObject.setPsSeven(data.getPsSeven());
        dataObject.setPsSiteName(data.getValue());
        dataObject.setPsSix(data.getPsSix());
        dataObject.setPsTen(data.getPsTen());
        dataObject.setPsThen(data.getPsThen());
        dataObject.setPsThree(data.getPsThree());
        dataObject.setPsThreeFourths(data.getPsThreeFourths());
        dataObject.setPsTwo(data.getPsTwo());
        dataObject.setPsTwoThirds(data.getPsTwoThirds());

        if (data.getPsWarningLabelSource() != null) {
            dataObject.setPsWarningLabelSource(data.getPsWarningLabelSource()
                    .toString());
        }

        dataObject.setPsWeeks(data.getPsWeeks());
        dataObject.setInactivationDate(data.getInactivationDate());

        if (data.getItemStatus().equals(0)) {
            dataObject.setItemStatus(PPSConstants.ACTIVE);
        } else if (data.getItemStatus().equals(1)) {
            dataObject.setItemStatus(PPSConstants.INACTIVE);
        } else if (data.getItemStatus().equals(2)) {
            dataObject.setItemStatus(PPSConstants.ARCHIVED);
        }

        dataObject.setRevisionNumber(data.getRevisionNumber());

        return dataObject;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a
     * PharmacySystemVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any,
     * are not populated at all. Any remaining aggregated {@link ManagedItemVo}
     * are minimally populated. If any of these item types are intended to be
     * fully populated, the appropriate {@link Converter#convert(DataObject)}
     * should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated PharmacySystemVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected PharmacySystemVo toValueObject(EplPharmacySystemDo data) {
        PharmacySystemVo valueObject = new PharmacySystemVo();

        valueObject.setPsSiteNumber(data.getSiteNumber());
        valueObject.setPsAnd(data.getPsAnd());
        valueObject.setId(data.getSiteNumber().toString());

        if (data.getPsCmopWarningLabelSource() != null) {
            valueObject
                    .setPsCmopWarningLabelSource(PharmacySystemWarningLabelSource
                            .valueOf(data.getPsCmopWarningLabelSource()));
        }

        valueObject.setCreatedBy(data.getCreatedBy());
        valueObject.setCreatedDate(data.getCreatedDtm());
        valueObject.setModifiedBy(data.getLastModifiedBy());
        valueObject.setModifiedDate(data.getLastModifiedDtm());
        valueObject.setPsDays(data.getPsDays());
        valueObject.setPsExcept(data.getPsExcept());
        valueObject.setPsEight(data.getPsEight());
        valueObject.setPsFive(data.getPsFive());
        valueObject.setPsFor(data.getPsFor());
        valueObject.setPsFour(data.getPsFour());
        valueObject.setPsHours(data.getPsHours());
        valueObject.setPsMinutes(data.getPsMinutes());
        valueObject.setPsMonths(data.getPsMonths());
        valueObject.setPsNine(data.getPsNine());
        valueObject.setPsOne(data.getPsOne());
        valueObject.setPsOneFourth(data.getPsOneFourth());
        valueObject.setPsOneHalf(data.getPsOneHalf());
        valueObject.setPsOneThird(data.getPsOneThird());
        valueObject.setPsSiteName(data.getPsSiteName());

        if (data.getPsOpaiWarningLabelSource() != null) {
            valueObject
                    .setPsOpaiWarningLabelSource(PharmacySystemWarningLabelSource
                            .valueOf(data.getPsOpaiWarningLabelSource()));
        }

        if (data.getPsPmisLanguage() != null
                && data.getPsPmisLanguage().length() > 0) {
            valueObject.setPsPmisLanguage(PharmacySystemPmisLanguage
                    .valueOf(data.getPsPmisLanguage()));
        }

        valueObject.setPsPmisPrinter(data.getPsPmisPrinter());
        valueObject.setPsPmisSectionDelete(data.getPsPmisSectionDelete());
        valueObject.setPsSeconds(data.getPsSeconds());
        valueObject.setPsSeven(data.getPsSeven());
        valueObject.setPharmacySystem(data.getPsSiteName());
        valueObject.setPsSix(data.getPsSix());
        valueObject.setPsTen(data.getPsTen());
        valueObject.setPsThen(data.getPsThen());
        valueObject.setPsThree(data.getPsThree());
        valueObject.setPsThreeFourths(data.getPsThreeFourths());
        valueObject.setPsTwo(data.getPsTwo());
        valueObject.setPsTwoThirds(data.getPsTwoThirds());

        if (data.getPsWarningLabelSource() != null) {
            valueObject
                    .setPsWarningLabelSource(PharmacySystemWarningLabelSource
                            .valueOf(data.getPsWarningLabelSource()));
        }

        valueObject.setPsWeeks(data.getPsWeeks());
        valueObject.setRevisionNumber(data.getRevisionNumber());
        valueObject.setInactivationDate(data.getInactivationDate());

        if (data.getItemStatus().equals("ACTIVE")) {
            valueObject.setItemStatus(ItemStatus.ACTIVE);
        } else if (data.getItemStatus().equals("INACTIVE")) {
            valueObject.setItemStatus(ItemStatus.INACTIVE);
        } else if (data.getItemStatus().equals("ARCHIVED")) {
            valueObject.setItemStatus(ItemStatus.ARCHIVED);
        }

        return valueObject;
    }

}
