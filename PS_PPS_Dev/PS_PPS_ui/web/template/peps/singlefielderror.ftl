<#--
<#assign hasFieldErrors = fieldErrors?exists && ((parameters.name?exists && fieldErrors[parameters.name]?exists) || (parameters.id?exists && fieldErrors[parameters.id]?exists))/>
<#if hasFieldErrors>
<ul class="errorMessage">
<#if parameters.name?exists && fieldErrors[parameters.name]?exists>
<#list fieldErrors[parameters.name] as error>
<li>${error?html}</li>
</#list>
</#if>
<#if parameters.id?exists && fieldErrors[parameters.id]?exists>
<#list fieldErrors[parameters.id] as error>
<li>${error?html}</li>
</#list>
</#if>
</ul>
</#if>
-->