<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<peps:form id="search">
<div class="panel">
    <div class="column"><s:textfield
        key="searchTemplate.searchCriteria.searchTerms[0].value"
        label="%{getLocalizedFieldName('search.for.setup.atcs')}"
        labelposition="top"
        required="true"
        title="%{getLocalizedFieldDescription('search.for.setup.atcs')}"
        size="15" /><s:radio
        name="searchTemplate.searchCriteria.searchTerms[0].searchType"
        id="searchTemplate.searchCriteria.searchTerms[0].searchType"
        list="@gov.va.med.pharmacy.peps.common.vo.SearchType@values()"
        listKey="name()"
        listValue="getText('SearchType.' + name())" /></div>

    <div class="column20">
    <s:if test="%{searchTemplate.entityType.product}">
        <s:textfield
            key="searchTemplate.searchCriteria.strength"
            label="%{getLocalizedFieldName('strength')}"
            labelposition="top"
            title="%{getLocalizedFieldDescription('strength')}"
            size="7" />&nbsp;(number only)
    </s:if>
    <p><peps:submit
            key="button.search"
            event="search" /></p>
    </div>

    <div class="column"><table><tr><td width="120px" height="50px"><s:checkboxlist
        key="searchTemplate.searchCriteria.productTypes"
        label="%{getLocalizedFieldName('categories')}"
        title="%{getLocalizedFieldDescription('categories')}"
        list="{domainUtility.getDomain('categories').value[5],
            domainUtility.getDomain('categories').value[3],
            domainUtility.getDomain('categories').value[1],
            domainUtility.getDomain('categories').value[7]}"
        labelposition="top" /></td></tr></table></div>

    <s:if test="%{environment.local}">
    <div class="column22">
        <s:if test="%{searchTemplate.entityType.product || searchTemplate.entityType.orderableItem}">
            <s:radio
                key="searchTemplate.searchCriteria.localUse"
                list="@gov.va.med.pharmacy.peps.common.vo.LocalUseSearchType@values()"
                listKey="name()"
                listValue="getText('LocalUseSearchType.' + name())"
                label="%{getLocalizedFieldName('local.use')}"
                labelposition="top"
                title="%{getLocalizedFieldDescription('local.use')}" />
        </s:if>
    </div>
    </s:if>

    <div class="column"><s:checkboxlist
        key="searchTemplate.searchCriteria.itemStatus"
        list="@gov.va.med.pharmacy.peps.common.vo.ItemStatus@getValues()"
        listValue="getText('ItemStatus.' + name())"
        label="%{getLocalizedFieldName('item.status')}"
        labelposition="top"
        title="%{getLocalizedFieldDescription('item.status')}"
        cssClass="checkbox" /></div>

    <div class="column"><s:checkboxlist
        key="searchTemplate.searchCriteria.requestStatus"
        list="@gov.va.med.pharmacy.peps.common.vo.RequestItemStatus@displayedValues()"
        listValue="getText('RequestItemStatus.' + name())"
        label="%{getLocalizedFieldName('request.item.status')}"
        labelposition="top"
        title="%{getLocalizedFieldDescription('request.item.status')}"
        cssClass="checkbox" /></div>
</div>
</peps:form>