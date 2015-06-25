<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<peps:form id="managePrintTemplates">
<s:optiontransferselect
    id="searchCriteriaPrintFields"
    label="Change Search Results Template Fields"
     
    leftTitle="Available Fields"
    name="availableFields"
    list="searchTemplate.searchCriteria.advancedSearchFields"
    listKey="key"
    listValue="getLocalizedName(locale)" 
    allowAddAllToLeft="false"
    allowSelectAll="false"
    allowAddAllToRight="false"
    rightTitle="Selected Fields"
    doubleId="printTemplateFields"
    doubleName="templateFields"
    doubleList="templateFields"
    doubleListKey="key" 
    doubleListValue="fieldKey.getLocalizedName(locale)" 
    allowUpDownOnLeft="false" />   

    <peps:submit
        key="button.submit"
        event="submit"
        onclick="selectAllTheOptions('printTemplateFields');" />
</peps:form>