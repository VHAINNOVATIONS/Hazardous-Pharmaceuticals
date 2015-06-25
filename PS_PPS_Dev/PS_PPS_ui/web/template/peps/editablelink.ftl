<#if (environment)?? && environment.isLocal() && (parameters.name)?? && (parameters.id)?? && (fieldAuthorization)?? && fieldAuthorization.isEnabled(parameters.id) && fieldAuthorization.isNonEditable(parameters.id)>
<span class="requestToEdit"><#t/>
<@s.hidden
    id="${parameters.id}.requestToEdit"
    name="${parameters.name}.requestToEdit"
    value="%{${parameters.name} == null ? false : ${parameters.name}.isRequestToEdit()}"
    theme="simple" /><#t/>
<a href="javascript: toggle('${parameters.id}')">Edit</a><#t/>
</span><#t/>
</#if>