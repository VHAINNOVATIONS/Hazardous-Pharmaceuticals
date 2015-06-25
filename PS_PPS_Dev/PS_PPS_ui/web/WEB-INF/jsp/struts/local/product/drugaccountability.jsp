<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<CENTER><b><peps:text key="product.long.name" label="false" /> Synonym Multiple &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;IFCAP Item Number <peps:text key="ifcap.item.number" label="false" /></b></CENTER>

<table width="655">
     <tr>
        <td><peps:dataField key="synonyms" /></td>
     </tr>
</table>