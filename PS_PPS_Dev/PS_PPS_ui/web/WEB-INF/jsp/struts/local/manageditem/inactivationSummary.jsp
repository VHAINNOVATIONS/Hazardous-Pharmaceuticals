<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<h3>Are you sure that you want to inactivate this item?</h3>
<peps:form id="confirmInactivate">
    <peps:dataField
        key="modification.reason"
        ognl="modDifferences[0].modificationReason"
        required="true" rows="2" cols="10" />
    <div>
    <div class="twoColumn"><peps:submit
        key="button.cancel"
        event="cancel" /></div>
    <div class="twoColumn"><peps:submit
        key="button.ok"
        event="commitInactivation" /></div>
    </div>
    </div>
</peps:form>