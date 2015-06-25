<%@ taglib
    prefix="s"
    uri="/struts-tags"%>
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<table width="625" height="80" border="0">
   <tr><td width="620">
        <s:select
            name="item.atcChoice"
            id="atc.choice"
            list="@gov.va.med.pharmacy.peps.common.vo.AtcChoice@values()"
            listValue="getText('AtcChoice.' + name())"
            labelposition="left"
            label="%{getLocalizedFieldDescription('atc.choice')}"
            title="%{getLocalizedFieldDescription('atc.choice')}"
            cssClass="comboBox"
            onchange="submitButton = 'atcChoice'; this.form.submit(); setCookie('atcChoice', 2); "
            emptyOption="true"
            disabled="false" />
   </td></tr>
   
   <s:if test="%{item.atcChoice != null}">
     <tr><td width="620"><peps:dataField key="atc.mnemonic" required="true"/></td></tr>
   </s:if>  
      
   <s:if test="%{item.atcChoiceOne}">
     <tr><td width="620"><peps:dataField key="one.atc.canister" /></td></tr>
   </s:if>  


   <s:if test="%{item.atcChoiceMultiple}">     
     <tr><td width="620"><peps:dataField key="atc.canisters" disabled="false"/></td></tr>
   </s:if>
   <s:elseif test="%{item.atcChoiceOne && !item.newInstance}">     
     <tr><td width="620"><peps:dataField key="atc.canisters" disabled="true"/></td></tr>
   </s:elseif>
   
   <s:if test="%{item.atcChoice != null && !item.newInstance}">
     <tr><td width="620"><peps:dataField key="local.print.name" /></td></tr>
   </s:if>  

   <s:if test="%{item.atcChoiceMultiple && !item.newInstance}">
     <tr><td width="620"><peps:dataField key="message"/></td></tr>
     <tr><td width="620"><peps:dataField key="local.non.formulary"/></td></tr>
     <tr><td width="620"><peps:dataField key="formulary.alternative"/></td></tr>
     <tr><td width="620"><peps:dataField key="product.price.per.dispense.unit"/></td></tr>
     <tr><td width="620"><peps:dataField key="corresponding.outpatient.drug" cssClass="outpatientdrug" /></td></tr>
   </s:if>
</table>

 