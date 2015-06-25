<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<table width="625" border="0">
      <tr>
        <td width="620"><peps:dataField key="inpatient.pharm.locations" cssClass="pharmacylocation" /></td>
      </tr>
      <tr>
        <td width="620"><peps:dataField key="ar.ws.amis.category"  /></td>
      </tr>
      <tr>
        <td width="620"><peps:dataField key="ar.ws.amis.conversion.number" /></td>
      </tr>
</table>