/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.PackageTypeVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.domain.common.model.EplPackageTypeDo;


/**
 * Convert to/from {@link PackageTypeVo} and {@link EplPackageTypeDo}.
 */
public class PackageTypeConverter extends Converter<PackageTypeVo, EplPackageTypeDo> {

    /**
     * Fully copies data from the given PackageTypeVo into a {@link DataObject}.
     * 
     * @param data {@link ValueObject} to convert
     * @return fully populated {@link DataObject}
     * 
     */
    @Override
    protected EplPackageTypeDo toDataObject(PackageTypeVo data) {
        EplPackageTypeDo packageType = new EplPackageTypeDo();

        packageType.setEplId(Long.valueOf(data.getId()));
        packageType.setPackageTypeName(data.getValue());

        if (data.getPackagetypeIen() != null) {
            packageType.setNdfPackagetypeIen(Long.valueOf(data.getPackagetypeIen()));
        }

        
        packageType.setRevisionNumber(data.getRevisionNumber());
        packageType.setCreatedBy(data.getCreatedBy());
        packageType.setCreatedDtm(data.getCreatedDate());
        packageType.setLastModifiedBy(data.getModifiedBy());
        packageType.setLastModifiedDtm(data.getModifiedDate());
        packageType.setInactivationDate(data.getInactivationDate());
        packageType.setRejectReasonText(data.getRejectionReasonText());

        if (data.getItemStatus() != null) {
            packageType.setItemStatus(data.getItemStatus().name());
        }

        if (data.getRequestItemStatus() != null) {
            packageType.setRequestStatus(data.getRequestItemStatus().name());
        }

        if (data.getRequestRejectionReason() != null) {
            packageType.setRequestRejectReason(data.getRequestRejectionReason().name());
        }

        return packageType;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a PackageTypeVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated {@link ManagedItemVo} are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated PackageTypeVo
     * 
     */
    @Override
    protected PackageTypeVo toValueObject(EplPackageTypeDo data) {
        PackageTypeVo packageType = new PackageTypeVo();

        packageType.setId(String.valueOf(data.getEplId()));

        if (data.getNdfPackagetypeIen() != null) {
            packageType.setPackagetypeIen(data.getNdfPackagetypeIen().toString());
        }

        packageType.setValue(data.getPackageTypeName());
        packageType.setItemStatus(ItemStatus.valueOf(data.getItemStatus()));
        packageType.setRequestItemStatus(RequestItemStatus.valueOf(data.getRequestStatus()));
        packageType.setRejectionReasonText(data.getRejectReasonText());
        packageType.setRevisionNumber(data.getRevisionNumber());
        packageType.setCreatedBy(data.getCreatedBy());
        packageType.setCreatedDate(data.getCreatedDtm());
        packageType.setModifiedBy(data.getLastModifiedBy());
        packageType.setModifiedDate(data.getLastModifiedDtm());
        packageType.setInactivationDate(data.getInactivationDate());

        if (data.getRequestRejectReason() != null) {
            packageType.setRequestRejectionReason(RequestRejectionReason.valueOf(data.getRequestRejectReason()));
        }

        return packageType;
    }

    /**
     * toMinimalValueObject
     * 
     * @param data EplPackageTypeDo
     * @return minimally populated {@link ValueObject}
     */
    @Override
    protected PackageTypeVo toMinimalValueObject(EplPackageTypeDo data) {
        PackageTypeVo packageType = new PackageTypeVo();
        packageType.setId(String.valueOf(data.getEplId()));
        packageType.setValue(data.getPackageTypeName());

        if (data.getNdfPackagetypeIen() != null) {
            packageType.setPackagetypeIen(data.getNdfPackagetypeIen().toString());
        }
     
        return packageType;
    }
}
