<#if (environment)?? && environment.isNational() && (parameters.name)?? && (parameters.id)?? && (fieldAuthorization)?? && fieldAuthorization.isEnabled(parameters.id) && (displayNationalEditLink)?? && (displayNationalEditLink) && (parameters.id != "searchTemplate_templateName") && (parameters.id != "searchTemplate_notes") && (parameters.id != "searchTemplate_templateType") >
<span class="editable"><#t/>
<@s.hidden
    id="${parameters.id}.editable"
    name="${parameters.name}.editable"
    value="%{${parameters.name} == null ? 'true' : ${parameters.name}.isEditable().toString()}"
    theme="simple" /><#t/>
<a href="#" onclick="swapImage('${parameters.id}'); return false;"><#t/>
<img id="${parameters.id}.editable.image" src="<#t/>
<#if (fieldAuthorization)?? && fieldAuthorization.isNonEditable(parameters.id)>        
images/nonEditable.gif" alt="locked" title="Click image to unlock the field" /><#t/>
<#else>
images/editable.gif" alt="unlocked" title="Click image to lock the field" /><#t/>
</#if>
</a></span><#t/>
</#if>
<#if (environment)?? && environment.isNational() && (parameters.name)?? && (parameters.id)?? &&  (displayNationalEditLink)?? && !(displayNationalEditLink)>
<span class="editable"><img alt="" src="images/blank.gif" height="16px;" width="16px;" /></span><#t/>
</#if>
<#if (environment)?? && environment.isNational() && (parameters.name)?? && (parameters.id)?? && (fieldAuthorization)?? && fieldAuthorization.isDisabled(parameters.id) && (displayNationalEditLink)?? && (displayNationalEditLink) >
<span class="editable"><img alt="" src="images/blank.gif" height="16px;" width="16px;" /></span><#t/>
</#if>