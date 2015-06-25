<#if parameters.labelposition?default("left") == 'none'>
<span<#t/>
<#else>
<div<#t/>
</#if>
 id="${parameters.id}.controlGroup"<#rt/>
<#if parameters.id?exists && fieldErrors[parameters.id]?exists>
 class="controlGroup errorMessage2"><#rt/>
<#elseif parameters.name?exists && fieldErrors[parameters.name]?exists>
 class="controlGroup errorMessage2"><#rt/>
<#else>
 class="controlGroup"><#rt/>
</#if>
<#include "/${parameters.templateDir}/peps/singlefielderror.ftl" /><#t />
<#if parameters.labelposition?default("left") != 'none'>
<#include "/${parameters.templateDir}/peps/nationalEditLink.ftl" /><#t />
</#if>
<#if parameters.label?exists && (parameters.labelposition?default("left") == 'left' || parameters.labelposition?default("left") == 'top')>
<#if parameters.labelposition?default("left") == 'top'>
<div<#t/>
<#else>
<span<#t/>
</#if>
 class="controlLabel"><#rt/>
<#include "/${parameters.templateDir}/peps/controllabel.ftl" /><#t />
<#if parameters.labelposition?default("left") == 'top'>
</div><#t />
<#else>
</span><#t />
</#if>
</#if>
<span class="control"><#t />