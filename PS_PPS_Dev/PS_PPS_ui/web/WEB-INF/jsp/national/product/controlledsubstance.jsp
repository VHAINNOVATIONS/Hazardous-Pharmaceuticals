<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>    
    
<table width="655">
     <tr>
        <td><peps:dataField key="dea.schedule" cssClass="outpatientdrug" item="${item}" /></td>
     </tr>
     <tr>
        <td><peps:dataField key="special.handlings" item="${item}" /></td>
     </tr>
</table>