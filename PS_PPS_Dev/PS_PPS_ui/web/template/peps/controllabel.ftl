<label<#rt/>
<#assign hasFieldErrors = fieldErrors?exists && ((parameters.name?exists && fieldErrors[parameters.name]?exists) || (parameters.id?exists && fieldErrors[parameters.id]?exists))>
<#if parameters.labelposition?default("left") == 'left'>
<#if hasFieldErrors && parameters.cssClass?exists>
 class="errorLabel left ${parameters.cssClass?html}"<#rt/>
<#elseif hasFieldErrors>
 class="errorLabel left"<#rt/>
<#elseif parameters.cssClass?exists>
 class="label left ${parameters.cssClass?html}"<#rt/>
<#else>
 class="label left"<#rt/>
</#if>
<#else>
<#if hasFieldErrors>
 class="errorLabel"<#rt/>
<#else>
 class="label"<#rt/>
</#if>
</#if>
<#if parameters.id?exists>
 id="_label_${parameters.id?html}"<#rt/>
</#if>
<#if parameters.title?exists>
 title="${parameters.title?html}"<#rt/>
</#if>
<#if parameters.for?exists>
 for="${parameters.for?html}"<#rt/>
<#elseif !parameters.for?exists && parameters.id?exists>
 for="${parameters.id?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
><#t/>
${parameters.label?html}<#t/>
<#include "/${parameters.templateDir}/peps/tooltip.ftl" /><#t/>
<#include "/${parameters.templateDir}/peps/required.ftl" /><#t/>
<#if (parameters.labelposition?default("left") == 'left' || parameters.labelposition?default("left") == 'top')>
:<#t/>
</#if>
</label>