<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
    
<peps:model ognlPrefix="item">
    <h3>
    <div class="columnpadded">
    <s:if test="%{item.entityType.product}">
        PRODUCT: <peps:text
            key="product.long.name"
            label="false" />
    </s:if><s:elseif test="%{item.entityType.Ndc}">
        NDC: <peps:text
            key="ndc"
            label="false" />
    </s:elseif><s:elseif test="%{item.entityType.orderableItem}">
        OI: <peps:text
            key="oi.name"
            label="false" />
    </s:elseif><s:else>
        <peps:text
            key="value"
            label="true"/>
    </s:else>
    </div>
    <div class="columnpadded">&nbsp; has been modified by another user since you retrieved it</div>
    </h3>
</peps:model>

<peps:form id="mergeSummary">
    <div class="panel">
    <fieldset><legend>Merge Summary for VA Data Fields</legend>
    <table
        id="mergeTable"
        frame="box"
        rules="cols"
        class="pepsTable">
        <thead>
            <tr>
                <th><peps:label key="field.name" abbreviation="true" /></th>
                <th><peps:label key="current.value" abbreviation="true" /></th>
                <th><peps:label key="modified.value" abbreviation="true" /></th>
                <th><peps:label key="modified.by" abbreviation="true" /></th>
                <th><peps:label key="modified.date" abbreviation="true" /></th>
                <th><peps:label key="accept.change" abbreviation="true" /></th>
            </tr>
        </thead>
        <tbody>
            <s:set
                scope="page"
                name="row"
                value="0" />
            <s:iterator
                value="mergeItem.mergeRequestDetails"
                status="modStatus">
                <s:set
                    scope="page"
                    name="index"
                    value="#modStatus.index" />
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
                    <td> <peps:text
                            key="field.name"
                            ognl="mergeItem.mergeRequestDetails[${index}].difference.fieldKey.getLocalizedName(locale, item.entityType)" />
                    </td>    
                </s:if>
                <s:else>
                    <td> <peps:text
                            key="field.name"
                            ognl="mergeItem.mergeRequestDetails[${index}].difference.fieldKey.getLocalizedName(locale)" />
                    </td>
                </s:else>
                <td><peps:text
                    key="current.value"
                    ognl="mergeItem.mergeRequestDetails[${index}].difference.oldValueShortString" /></td>
                <td><peps:text
                    key="modified.value"
                    ognl="mergeItem.mergeRequestDetails[${index}].difference.newValueShortString" /></td>
                <td><peps:text
                    key="modified.by"
                    ognl="mergeItem.mergeRequestDetails[${index}].modifiedBy" /></td>
                <td><peps:text
                    key="modified.date"
                    ognl="mergeItem.mergeRequestDetails[${index}].modifiedDate" /></td>
                <td align="center"><peps:dataField
                    key="accept.change"
                    ognl="mergeItem.mergeRequestDetails[${index}].acceptChange"
                    labelPosition="none" cssClass="center" /></td>
                </tr>
            </s:iterator>
        </tbody>
    </table>
    </fieldset>
    <div>
    <div class="twoColumn"><peps:submit
        key="button.cancel"
        event="cancel" /></div>
          
    <div class="twoColumn"><peps:submit
        key="button.accept.changes"
        event="commitMergeModifications" /></div>
    </div>
    </div>
</peps:form>