<%@ taglib
    prefix="s"
    uri="/struts-tags"%>

<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<peps:form
    id="updateCots"
    mimetype="multipart/form-data">
    <s:file
        name="upload"
        label="DIF Update File" />
    <peps:submit
        key="button.submit"
        event="submit" />
</peps:form>
