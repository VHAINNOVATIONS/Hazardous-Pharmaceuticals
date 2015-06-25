<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<peps:warnings />
<br />
<peps:model ognlPrefix="item"> 
<h3>
<div class="columnpadded">&nbsp;&nbsp;Summary for&nbsp;</div>
    <div class="columnpadded">
    <s:if test="%{item.entityType.product}">
        <peps:label key="product"/>: <peps:text
            key="product.long.name"
            label="false" />
    </s:if>
    <s:elseif test="%{item.entityType.Ndc}">
        <peps:label key="ndcvo" />: <peps:text
            key="ndc"
            label="false" />
    </s:elseif>
    <s:elseif test="%{item.entityType.orderableItem}">
        <peps:label key="orderable.item" />: <peps:text
            key="oi.name"
            label="false" />
    </s:elseif>
    <s:else>
        <peps:text
            key="value"
            label="true"/>
    </s:else>
    </div>
</h3>
</peps:model>
<peps:form id="requestSummary">
    <div class="panel">
    <s:if test="%{mainRequest.requestType.addition}">
        <div class="twoColumn" ><peps:dataField
            key="note"
            ognl="mainRequest.note"
            disabled="true" 
            rows = "5"
            cols = "10" /></div>
            <div class="twoColumn"><peps:dataField
            key="new.note"
            ognl="mainRequest.newNote" 
            rows = "5"
            cols = "10"/></div>
    </s:if>
    <s:if test="%{mainRequest.requestState.isPendingFirstReview()}">
        <div class="twoColumn"><peps:dataField key="marked.for.peps.second.review"  labelPosition="right" ognl="mainRequest.markedForPepsSecondReview" /></div>
    </s:if>
       <div class="twoColumn"><peps:dataField
            key="psr.name"
            ognl="mainRequest.psrName" /></div>
    </div>
    <div class="panel">
    <s:if test="%{mainRequest != null 
              && mainRequest.requestState.isPendingFirstReview()
              && mainRequest.requestType.isProblemReport()}">
        <fieldset><legend>Change Requests</legend>
            <s:textarea 
                    name="mainRequest.note"  
                    id="mainRequest.note" 
                    rows="10" 
                    cols="70"/>
        </fieldset>
        <br>
    </s:if>
    <div class="topspacer"><s:text name="has.conflicts" /></div>
    
    <s:if test="%{!mainRequest.requestDetails.isEmpty()}">
    
    <fieldset><legend>Request Summary</legend>
    <table
        id="modTable"
        frame="box"
        rules="cols"
        class="pepsTable">
        <thead>
            <tr>
                <th><peps:label key="column.action" abbreviation="true" /></th>
                <th><peps:label key="field.name" abbreviation="true" /></th>
                <th><peps:label key="current.value" abbreviation="true" /></th>
                <th><peps:label key="modified.value" abbreviation="true" /></th>
                <th><peps:label key="modification.reason" abbreviation="true" /></th>
                <th><peps:label key="request.to.modify.value" abbreviation="true" /></th>
                <th><peps:label key="request.to.make.editable" abbreviation="true" /></th>
                <th><peps:label key="requested.by" abbreviation="true" /></th>
                <th><peps:label key="requires.second.approval" abbreviation="true" /></th>
                <th><peps:label key="reviewed.by" abbreviation="true" /></th>
                <th><peps:label key="request.comments" abbreviation="true" /></th>
            </tr>
        </thead>
        <tbody>
            <s:iterator
                value="mainRequest.requestDetails"
                status="details">
                <s:set
                    scope="page"
                    name="index"
                    value="#details.index" />
                <s:if test="mainRequest.requestDetails[#attr.index].hasConflict()">
                    <tr class="hasconflict">
                </s:if>
                <s:elseif test="#attr.index % 2 == 0">
                    <tr class="odd">
                </s:elseif>
                <s:else>
                    <tr class="even">
                </s:else>
                <td align="center"><peps:dataField
                    key="mod.request.item.status"
                    id="mod.request.item.status_${index}"
                    ognl="mainRequest.requestDetails[${index}].requestItemStatus"
                    labelPosition="none" />
                    </td>
                <td><s:if test="mainRequest.requestDetails[#attr.index].hasConflict()"><s:text name="conflict.symbol" />&nbsp;</s:if><peps:text
                    key="field.name"
                    ognl="mainRequest.requestDetails[${index}].difference.fieldKey.getLocalizedName(locale)" /></td>
                <td><peps:text
                    key="current.value"
                    ognl="mainRequest.requestDetails[${index}].difference.oldValueShortString" /></td>
                <td><peps:text
                    key="modified.value"
                    ognl="mainRequest.requestDetails[${index}].difference.newValueShortString" /></td>
                <td><peps:text
                    key="modification.reason"
                    ognl="mainRequest.requestDetails[${index}].modificationReason" /></td>
                <td align="center"><peps:dataField
                    key="request.to.modify.value"
                    id="request.to.modify.value_${index}"
                    ognl="mainRequest.requestDetails[${index}].requestToModifyValue"
                    labelPosition="none" 
                    disabled="true" 
                    cssClass="checkbox2 center" /></td>
                <td align="center"><peps:dataField
                    key="request.to.make.editable"
                    id="request.to.make.editable_${index}"
                    ognl="mainRequest.requestDetails[${index}].requestToMakeEditable"
                    labelPosition="none" 
                    disabled="true" 
                    cssClass="checkbox2 center" /></td>
                <td align="center"><peps:text
                    key="requested.by"
                    ognl="mainRequest.requestDetails[${index}].requestor.toDisplayName()"/></td>
                <td align="center"><peps:dataField
                    key="requires.second.approval"
                    id="requires.second.approval_${index}"
                    ognl="mainRequest.requestDetails[${index}].difference.fieldKey.isRequiresSecondApproval()"
                    labelPosition="none"
                    cssClass="center"
                    disabled="true" /></td>
                <td align="center"><peps:text
                    key="reviewed.by"
                    ognl="mainRequest.requestDetails[${index}].reviewer.toDisplayName()"/></td>
                <td align="center"><peps:dataField
                    key="note"
                    id="note_${index}"
                    ognl="mainRequest.requestDetails[${index}].comments"
                    labelPosition="none" rows="2" cols="10" /></td>
                </tr>
            </s:iterator>
        </tbody>
    </table>
    </fieldset>
    </s:if>
    <s:else>
        <p> There are no pending modification requests to this item. <p>
    </s:else>
    <br>
        
    <s:if test="%{@gov.va.med.pharmacy.peps.presentation.common.utility.DifferenceUtility@hasEditableDifference(modDifferences)}" >
        <fieldset><legend>Modification Summary for VA Data Fields</legend>
        <table
            id="modTable"
            frame="box"
            rules="cols"
            class="pepsTable">
            <thead>
                <tr>
                    <th><peps:label key="field.name" abbreviation="true" /></th>
                    <th><peps:label key="current.value" abbreviation="true" /></th>
                    <th><peps:label key="modified.value" abbreviation="true" /></th>
                    <th><peps:label key="modification.reason" abbreviation="true" /></th>
                    <th><peps:label key="accept.change" abbreviation="true" /></th>
                    <th><peps:label key="requires.second.approval" abbreviation="true" /></th>
                </tr>
            </thead>
            <tbody>
                <s:iterator
                    value="modDifferences"
                    status="modStatus">
                    <s:set
                        scope="page"
                        name="index"
                        value="#modStatus.index" />
                    <s:if test="modDifferences[#attr.index].hasConflict()">
                        <tr class="hasconflict">
                    </s:if>
                    <s:elseif test="#attr.index % 2 == 0">
                        <tr class="odd">
                    </s:elseif>
                    <s:else>
                        <tr class="even">
                    </s:else>
                    <td>
                        <s:if test="modDifferences[#attr.index].hasConflict()"><s:text name="conflict.symbol" />&nbsp;</s:if>
                        <peps:text
                            key="field.name"
                            ognl="modDifferences[${index}].difference.fieldKey.getLocalizedName(locale)" />
                    </td>
                    <td>
                        <peps:text
                            key="current.value"
                            ognl="modDifferences[${index}].difference.oldValueShortString" />
                    </td>
                    <td><peps:text
                        key="modified.value"
                        ognl="modDifferences[${index}].difference.newValueShortString" /></td>
                    <td><peps:dataField
                        key="modification.reason"
                        ognl="modDifferences[${index}].modificationReason"
                        labelPosition="none" rows="2" cols="10" /></td>
                    <td align="center"><peps:dataField
                        key="accept.change"
                        ognl="modDifferences[${index}].acceptChange"
                        labelPosition="none" cssClass="checkbox2 center" /></td>
                    <td align="center"><peps:dataField
                        key="requires.second.approval"
                        ognl="modDifferences[${index}].difference.fieldKey.isRequiresSecondApproval()"
                        labelPosition="none"
                        cssClass="checkbox2 center"
                        disabled="true" /></td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>
        </fieldset>
    </s:if>
    <p><b><s:property value="requestStateStatusMessage" /></b></p>
    <div>
    <div class="fourColumn"><peps:submit
        key="button.cancel"
        event="cancel" /></div>
    <div class="threeColumn">
        <s:if test="%{changeItemCount()>1}">
            <peps:submit
                key="button.accept.changeAll"
                event="reviewAll" />
        </s:if>
    </div>
    <div class="fourColumn"><peps:submit
        key="button.accept.changes"
        event="confirm" /></div>
    </div>
    </div>
</peps:form>