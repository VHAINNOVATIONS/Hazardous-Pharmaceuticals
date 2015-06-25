<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>

Please enter a message to be sent to the National Manager.
<peps:form id="problemReportTextForm">
    <textArea name="comment" id="comment" cols="60" rows="7" ></textArea>
    <div>
        &nbsp;
    </div>
    <peps:submit
        key="button.submit" />
    <peps:submit
        key="button.cancel" />
</peps:form>