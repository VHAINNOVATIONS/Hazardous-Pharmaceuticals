/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.domain.common.utility.converter;


import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.SerializationUtils;
import org.hibernate.Hibernate;

import gov.va.med.pharmacy.peps.common.exception.DomainException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.diff.Difference;
import gov.va.med.pharmacy.peps.domain.common.model.DataObject;
import gov.va.med.pharmacy.peps.domain.common.model.EplNdcDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplOrderableItemDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplProductDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplRequestDetailDo;
import gov.va.med.pharmacy.peps.domain.common.model.EplRequestDo;


/**
 * Convert to/from {@link RequestVo} and {@link EplRequestDo}.
 */
public class RequestConverter extends Converter<RequestVo, EplRequestDo> {

    /**
     * Converts EplRequestDo to RequestVo
     * 
     * @param data EplRequestDo
     * 
     * @return RequestVo
     */
    private RequestVo createRequestTableData(EplRequestDo data) {
        RequestVo request = new RequestVo();
        request.setEntityType(EntityType.valueOf(data.getItemType()));

        switch (request.getEntityType()) {
            case ORDERABLE_ITEM:
                request.setItemId(data.getEplOrderableItem().getEplId().toString());
                break;
            case PRODUCT:
                request.setItemId(data.getEplProduct().getEplId().toString());
                break;
            case NDC:
                request.setItemId(data.getEplNdc().getEplId().toString());
                break;
            default:
                request.setItemId(data.getDomainId().toString());
                break;
        }

        request.setId(String.valueOf(data.getId()));
        request.setSiteName(data.getSiteName());
        request.setRequestType(RequestType.valueOf(data.getRequestType()));
        request.setRequestState(RequestState.valueOf(data.getRequestStatus()));
        request.setNewItemRequestStatus(RequestItemStatus.valueOf(data.getNewItemRequestStatus()));

        request.setUnderReview(toBoolean(data.getUnderReviewFlag()));
        request.setMarkedForPepsSecondReview(toBoolean(data.getMarkedForPsr()));
        request.setCreatedDate(data.getCreatedDtm());
        request.setCreatedBy(data.getCreatedBy());
        request.setModifiedBy(data.getLastModifiedBy());
        request.setModifiedDate(data.getLastModifiedDtm());
        request.setPsrName(data.getPsrName());

        if (data.getRequesterName() != null) {
            request.setNewItemRequestor(toUser(data.getRequesterName()));
        }

        request.setRejectionReasonText(data.getRejectReasonText());

        if (data.getRequestRejectReason() != null) {
            request.setRequestRejectionReason(RequestRejectionReason.valueOf(data.getRequestRejectReason()));
        }

        request.setNote(data.getNote());

        if (data.getLastReviewerName() != null) {
            request.setLastReviewer(toUser(data.getLastReviewerName()));
        }

        return request;
    }

    /**
     * Fully copies data from the given RequestVo into a {@link DataObject}.
     * 
     * @param data RequestVo to convert
     * @return fully populated {@link DataObject}
     * 
     */
    @Override
    protected EplRequestDo toDataObject(RequestVo data) {
        EplRequestDo request = new EplRequestDo();
        request.setCreatedBy(data.getCreatedBy());
        request.setCreatedDtm(data.getCreatedDate());
        request.setLastModifiedBy(data.getModifiedBy());
        request.setLastModifiedDtm(data.getModifiedDate());

        if (data.getId() != null) {
            request.setId(new Long(data.getId()));
        }

        if (data.getEntityType().isOrderableItem()) {
            EplOrderableItemDo orderableItem = new EplOrderableItemDo();
            orderableItem.setEplId(new Long(data.getItemId()));
            request.setEplOrderableItem(orderableItem);
        } else if (data.getEntityType().isNdc()) {
            EplNdcDo ndcDo = new EplNdcDo();
            ndcDo.setEplId(new Long(data.getItemId()));
            request.setEplNdc(ndcDo);
        } else if (data.getEntityType().isProduct()) {
            EplProductDo eplProduct = new EplProductDo();
            eplProduct.setEplId(new Long(data.getItemId()));
            request.setEplProduct(eplProduct);
        } else {

            // these are all the domains set the domain id
            request.setDomainId(new Long(data.getItemId()));
        }

        request.setRequestType(data.getRequestType().name());
        request.setSiteName(data.getSiteName());
        request.setItemType(data.getEntityType().name());
        request.setRequestStatus(data.getRequestState().name());
        request.setUnderReviewFlag(toYesOrNo(data.isUnderReview()));
        request.setMarkedForPsr(toYesOrNo(data.isMarkedForPepsSecondReview()));
        request.setPsrName(data.getPsrName());

        if (data.getNewItemRequestStatus() != null) {
            request.setNewItemRequestStatus(data.getNewItemRequestStatus().toString());
        }

        if (data.getNewItemRequestor() != null) {
            request.setRequesterName(fromUser(data.getNewItemRequestor()));
        }

        if (data.getRejectionReasonText() != null) {
            request.setRejectReasonText(data.getRejectionReasonText());
        }

        // request.setRequestRejectReason(data.getRequestRejectReason().toString());
        if (data.getRequestRejectionReason() != null) {
            request.setRequestRejectReason(data.getRequestRejectionReason().toString());
        }

        if (data.getNote() != null) {
            request.setNote(data.getNote());
        }

        if (data.getLastReviewer() != null) {
            request.setLastReviewerName(fromUser(data.getLastReviewer()));
        }

        Set<EplRequestDetailDo> reqDetailCollection = new HashSet<EplRequestDetailDo>();

        if (data.getRequestDetails() != null) {

            // iterate through collection
            for (ModDifferenceVo diff : data.getRequestDetails()) {

                // each mod difference is one requestDetail;
                EplRequestDetailDo reqDetail = toRequestDetail(diff, request);
                reqDetailCollection.add(reqDetail);

            } // end of for gone through all mod differences

            request.setEplRequestDetails(reqDetailCollection);
        }// end if

        return request;
    }

    /**
     * Convert the ModDifferenceVo to request deatil
     * @param request EplRequestDo
     * @param diff ModDifferenceVo
     * @return EplRequestDetailDo
     */
    private EplRequestDetailDo toRequestDetail(ModDifferenceVo diff, EplRequestDo request) {

        // each mod difference is one requestDetail;
        EplRequestDetailDo reqDetail = new EplRequestDetailDo();
        reqDetail.setCreatedBy(diff.getCreatedBy());
        reqDetail.setCreatedDtm(diff.getCreatedDate());
        reqDetail.setLastModifiedBy(diff.getModifiedBy());
        reqDetail.setLastModifiedDtm(diff.getModifiedDate());
        reqDetail.setNote(diff.getComments());

        if (diff.getId() != null) {
            reqDetail.setId(new Long(diff.getId()));
        }

        if (diff.getRequestRejectReason() != null) {
            reqDetail.setRequestRejectReason(diff.getRequestRejectReason());
        }

        reqDetail.setRejectReasonText(diff.getRejectReasonText());
        reqDetail.setEditAtLocalYn(toYesOrNo(diff.getRequestToMakeEditable()));
        reqDetail.setModificationReason(diff.getModificationReason());
        reqDetail.setSiteName(diff.getSiteName());
        reqDetail.setStatus(diff.getModRequestItemStatus().toString());
        reqDetail.setRejectReasonText(diff.getRejectReasonText());
        reqDetail.setRequestToModifyYn(toYesOrNo(diff.getRequestToModifyValue()));

        if (reqDetail.getRequesterName() == null && diff.getRequestor() != null) {
            reqDetail.setRequesterName(fromUser(diff.getRequestor()));

        }

        if (diff.getReviewer() != null) {
            reqDetail.setLastReviewerName(fromUser(diff.getReviewer()));
        }

        // convert Difference to a Blob

        byte[] bytes = SerializationUtils.serialize(diff.getDifference());
        reqDetail.setDifference(Hibernate.createBlob(bytes));

        reqDetail.setEplRequest(request);

        return reqDetail;
    }

    /**
     * Fully copies data from the given {@link DataObject} into a RequestVo.
     * <p>
     * Parent objects, if any, are minimally populated. Child objects, if any, are not populated at all. Any remaining
     * aggregated RequestVo are minimally populated. If any of these item types are intended to be fully
     * populated, the appropriate {@link Converter#convert(DataObject)} should be called.
     * 
     * @param data {@link DataObject} to convert
     * @return fully populated RequestVo
     */
    @Override
    protected RequestVo toValueObject(EplRequestDo data) {
        RequestVo request = createRequestTableData(data);

        if (data.getEplRequestDetails() != null && data.getEplRequestDetails().size() > 0) {

            Collection<ModDifferenceVo> requestDetailCollection = new ArrayList<ModDifferenceVo>();

            @SuppressWarnings("unused")
            Map<FieldKey, Difference> differences = new HashMap<FieldKey, Difference>();

            // Convert each EPL_REQUEST_DETAILS row into a Difference
            for (EplRequestDetailDo detail : data.getEplRequestDetails()) {

                ModDifferenceVo mod = new ModDifferenceVo();
                mod.setCreatedBy(detail.getCreatedBy());
                mod.setCreatedDate(detail.getCreatedDtm());
                mod.setModifiedBy(detail.getLastModifiedBy());
                mod.setModifiedDate(detail.getLastModifiedDtm());
                mod.setId(String.valueOf(detail.getId()));

                if (detail.getEditAtLocalYn() != null) {
                    mod.setRequestToMakeEditable(toBoolean(detail.getEditAtLocalYn()));
                }

                mod.setModificationReason(detail.getModificationReason());
                mod.setSiteName(detail.getSiteName());
                mod.setModRequestItemStatus(RequestItemStatus.valueOf(detail.getStatus()));

                mod.setComments(detail.getNote());
                mod.setRejectReasonText(data.getRejectReasonText());

                if (data.getRequestRejectReason() != null) {
                    mod.setRequestRejectReason(data.getRequestRejectReason());
                }

                mod.setReviewerNote(detail.getNote());

                if (detail.getLastReviewerName() != null) {
                    mod.setReviewer(toUser(detail.getLastReviewerName()));
                }

                if (detail.getRequesterName() != null) {
                    mod.setRequestor(toUser(detail.getRequesterName()));
                }

                if (detail.getRequestToModifyYn() != null) {
                    mod.setRequestToModifyValue(toBoolean(detail.getRequestToModifyYn()));
                }

                // deserialize back to difference object
                Blob b = detail.getDifference();

                Object o = null;

                try {
                    o = SerializationUtils.deserialize(b.getBinaryStream());
                } catch (SQLException e) {
                    throw new DomainException(e, DomainException.WRAPPED_EXCEPTION);
                }

                mod.setDifference((Difference) (o));

                requestDetailCollection.add(mod);

            } // end of for (EplRequestDetailDo detail : reqDetail)

            request.setRequestDetails(requestDetailCollection);

        } // end of getting the request details

        return request;
    }
}
