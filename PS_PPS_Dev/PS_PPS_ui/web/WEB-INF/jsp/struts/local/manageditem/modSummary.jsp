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
    <span class="columnpadded">&nbsp;&nbsp;Modification Summary for&nbsp;</span>
    <span class="columnpadded"><s:if test="%{item.entityType.product}">
        Product:<peps:text
            key="product.long.name"
            label="false" />
    </s:if> <s:elseif test="%{item.entityType.Ndc}">
        NDC:<peps:text
            key="ndc"
            label="false" />
    </s:elseif> <s:elseif test="%{item.entityType.orderableItem}">
        Orderable Item:<peps:text
            key="oi.name"
            label="false" />
    </s:elseif> <s:elseif test="%{item.entityType.isDomainType()}">
        Name: <peps:text 
            key="value"
            label="false" />
    </s:elseif> <s:else>
        <peps:text
            key="value"
            label="true" />
    </s:else></span>
    </h3>
</peps:model>

<peps:form id="modificationSummary">
    <div class="panel">
  
    <s:if test="%{@gov.va.med.pharmacy.peps.presentation.common.utility.DifferenceUtility@hasNonEditableDifference(modDifferences)}" >
    
    <fieldset><legend>Modification Summary for Non-Editable VA Data Fields</legend>
        <table
            id="noneditableModTable"
            frame="box"
            rules="cols"
            class="pepsTable">
            <thead>
                <tr>
                    <th><peps:label
                        key="field.name"
                        abbreviation="true" /></th>
                    <th><peps:label
                        key="current.value"
                        abbreviation="true" /></th>
                    <th><peps:label
                        key="modified.value"
                        abbreviation="true" /></th>
                    <th><peps:label
                        key="modification.reason"
                        abbreviation="true" /></th>
                    <th><peps:label
                        key="request.to.modify.value"
                        abbreviation="true" /></th>
                    <th><peps:label
                        key="request.to.make.editable"
                        abbreviation="true" /></th>
                    <th><peps:label
                        key="requires.second.approval"
                        abbreviation="true" /></th>
                </tr>
            </thead>
            <tbody>
                <s:set
                    scope="page"
                    name="row"
                    value="0" />
                <s:iterator
                    value="modDifferences"
                    status="modStatus">
                    <s:set
                        scope="page"
                        name="index"
                        value="#modStatus.index" />
                    <s:if test="modDifferences[#attr.index].difference.newValue.requestToEdit">
                        <s:if test="#attr.row % 2 == 0">
                            <tr class="odd">
                        </s:if>
                        <s:else>
                            <tr class="even">
                        </s:else>
                        <s:set
                            scope="page"
                            name="row"
                            value="#attr.row + 1" />
                        <s:if test="%{item.entityType.isDomainType()}">
                            <td><peps:text
                                key="field.name"
                                ognl="modDifferences[${index}].difference.fieldKey.getLocalizedName(locale, item.entityType)" /></td>
                        </s:if>
                        <s:else>
                            <td><peps:text
                                key="field.name"
                                ognl="modDifferences[${index}].difference.fieldKey.getLocalizedName(locale)" /></td>
                        </s:else>
                        <td><peps:text
                            key="current.value"
                            ognl="modDifferences[${index}].difference.oldValueShortString" /></td>
                        <td><peps:text
                            key="modified.value"
                            ognl="modDifferences[${index}].difference.newValueShortString" /></td>
                        <td><peps:dataField
                            key="modification.reason"
                            id="modification.reason_${index}"
                            ognl="modDifferences[${index}].modificationReason"
                            labelPosition="none" /></td>
                        <td align="center"><peps:dataField
                            key="request.to.modify.value"
                            id="request.to.modify.value_${index}"
                            ognl="modDifferences[${index}].requestToModifyValue"
                            labelPosition="none"
                            cssClass="center" /></td>
                        <td align="center"><peps:dataField
                            key="request.to.make.editable"
                            id="request.to.make.editable_${index}"
                            ognl="modDifferences[${index}].requestToMakeEditable"
                            labelPosition="none"
                            cssClass="center" /></td>
                        <td align="center"><peps:dataField
                            key="requires.second.approval"
                            id="requires.second.approval_${index}"
                            ognl="fieldAuthorization.isRequiresSecondApproval(modDifferences[${index}].difference.fieldKey)"
                            labelPosition="none"
                            cssClass="center"
                            disabled="true" /></td>
                        </tr>
                    </s:if>
                </s:iterator>
            </tbody>
        </table>
    </fieldset>
    <br />
    </s:if>
    <s:else>
        <p>No Non-editable VA Data Fields were modified.</p>
    </s:else>
    
    <s:if test="%{@gov.va.med.pharmacy.peps.presentation.common.utility.DifferenceUtility@hasEditableDifference(modDifferences)}" >
    
    <fieldset><legend>Modification Summary for Editable VA Data Fields</legend>

    <table
        id="editableModTable"
        frame="box"
        rules="cols"
        class="pepsTable">
        <thead>
            <tr>
                <th><peps:label
                    key="field.name"
                    abbreviation="true" /></th>
                <th><peps:label
                    key="current.value"
                    abbreviation="true" /></th>
                <th><peps:label
                    key="modified.value"
                    abbreviation="true" /></th>
                <th><peps:label
                    key="modification.reason"
                    abbreviation="true" /></th>
                <th><peps:label
                    key="accept.change"
                    abbreviation="true" /></th>
                <th><peps:label
                    key="requires.second.approval"
                    abbreviation="true" /></th>
            </tr>
        </thead>
        <tbody>
            <s:set
                scope="page"
                name="row"
                value="0" />
            <s:iterator
                value="modDifferences"
                status="modStatus">
                <s:set
                    scope="page"
                    name="index"
                    value="#modStatus.index" />
                <s:if test="!modDifferences[#attr.index].difference.oldValue.editable">
                </s:if>
                <s:else>
                    <s:if test="#attr.row % 2 == 0">
                        <tr class="odd">
                    </s:if>
                    <s:else>
                        <tr class="even">
                    </s:else>
                    <s:set
                        scope="page"
                        name="row"
                        value="#attr.row + 1" />
                    <s:if test="%{item.entityType.isDomainType()}">
                        <td><peps:text
                            key="field.name"
                            ognl="modDifferences[${index}].difference.fieldKey.getLocalizedName(locale, item.entityType)" />
                        </td>
                    </s:if>
                    <s:else>
                        <td><peps:text
                            key="field.name"
                            ognl="modDifferences[${index}].difference.fieldKey.getLocalizedName(locale)" /></td>
                    </s:else>
                    <td><peps:text
                        key="current.value"
                        ognl="modDifferences[${index}].difference.oldValueShortString" /></td>
                    <td><peps:text
                        key="modified.value"
                        ognl="modDifferences[${index}].difference.newValueShortString" /></td>
                    <td><peps:dataField
                        key="modification.reason"
                        id="modification.reason_${index}"
                        ognl="modDifferences[${index}].modificationReason"
                        labelPosition="none" rows="2" cols="10" /></td>
                    <td align="center"><peps:dataField
                        key="accept.change"
                        id="accept.change_${index}"
                        ognl="modDifferences[${index}].acceptChange"
                        labelPosition="none"
                        cssClass="center" /></td>
                    <td align="center"><peps:dataField
                        key="requires.second.approval"
                        id="requires.second.approval_${index}"
                        ognl="fieldAuthorization.isRequiresSecondApproval(modDifferences[${index}].difference.fieldKey)"
                        labelPosition="none"
                        cssClass="center"
                        disabled="true" /></td>
                    </tr>
                </s:else>
            </s:iterator>
        </tbody>
    </table>
    </fieldset>
    </s:if>
    <s:else>
        <p>No Editable VA Data Fields were modified.</p>
    </s:else>
    
    </div>
    <div>
    <div class="fourColumn"><peps:submit
        key="button.cancel"
        event="cancel" /></div>
    <div class="threeColumn"><s:if test="%{tableSelections != null && tableSelections.size() > 0}">
        <peps:submit
            key="button.accept.changeAll"
            event="reviewAll" />
    </s:if></div>
    
    <s:if test="%{@gov.va.med.pharmacy.peps.presentation.common.utility.DifferenceUtility@hasNonEditableDifference(modDifferences) || @gov.va.med.pharmacy.peps.presentation.common.utility.DifferenceUtility@hasEditableDifference(modDifferences) }" >
        <div class="fourColumn">
        <s:if test="%{tableSelections != null && tableSelections.size() > 0}">
            <peps:submit
                key="button.accept.changes2"
                event="commitModifications" />
        </s:if>
        <s:else>
        <peps:submit
            key="button.accept.changes"
            event="commitModifications" />
        </s:else>
        </div>
    </s:if>
    </div>
</peps:form>
