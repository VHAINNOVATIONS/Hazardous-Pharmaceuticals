<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<peps:form id="manageDif">
    <peps:dataField
        key="scheduled.start.dtm"
        cssClass="iahdate" />
    <peps:dataField
        key="scheduled.interval" />
    <peps:submit
        key="button.submit"
        event="submit" />
        
        <s:if test="%{canUpdate}">
        <peps:submit
        key="button.manual"
        event="manualExecute"/>
        </s:if>
        
</peps:form>