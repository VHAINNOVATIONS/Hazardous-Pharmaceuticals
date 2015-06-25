</span><#t />
<#if parameters.label?exists && (parameters.labelposition?default("left") == 'right' || parameters.labelposition?default("left") == 'bottom')>
<#if parameters.labelposition?default("left") == 'bottom'>
<div<#t/>
<#else>
<span<#t/>
</#if>
 class="controlLabel"><#rt/>
<#include "/${parameters.templateDir}/peps/controllabel.ftl" /><#t />
<#if parameters.labelposition?default("left") == 'bottom'>
</div><#t />
<#else>
</span><#t />
</#if>
</#if>
<#include "/${parameters.templateDir}/peps/editablelink.ftl" /><#t />
<#if parameters.labelposition?default("left") == 'none'>
</span><#t />
<#else>
</div><#t />
</#if>