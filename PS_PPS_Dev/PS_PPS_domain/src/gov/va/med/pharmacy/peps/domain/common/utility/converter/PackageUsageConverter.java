/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PackageUsageVo;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplPackageUsageDo;


/**
 * Convert to/from {@link PackageUsageVo} and {@link EplPackageUsageDo}.
 */
public class PackageUsageConverter extends Converter<PackageUsageVo, EplPackageUsageDo> {

    /**
     * Fully copies data from the given PackageUsageVo into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toDataObject(
     *      gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    @Override
    protected EplPackageUsageDo toDataObject(PackageUsageVo data) {
        EplPackageUsageDo packageUseage = new EplPackageUsageDo();
        
        packageUseage.setId(Long.valueOf(data.getId()));
        packageUseage.setDescription(data.getDescription());
        packageUseage.setPackageUseCode(data.getValue());

        return packageUseage;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a PackageUsageVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated PackageUsageVo
     * 
     * @see gov.va.med.pharmacy.peps.domain.common.utility.converter.Converter#toValueObject(
     *      gov.va.med.pharmacy.peps.domain.common.model.DataObject)
     */
    @Override
    protected PackageUsageVo toValueObject(EplPackageUsageDo data) {
        PackageUsageVo packageUseage = new PackageUsageVo();
        
        packageUseage.setId(String.valueOf(data.getId()));
        packageUseage.setDescription(data.getDescription());
        packageUseage.setValue(data.getPackageUseCode());

        return packageUseage;
    }
}
