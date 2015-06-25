<#if ((actionErrors?exists && actionErrors.size() > 0) || (fieldErrors?exists && fieldErrors.size() > 0))>
<br><div class="validation">
<#if (actionErrors?exists && actionErrors.size() > 0)>
<#if (actionErrors.size() > 1)>
<span class="errorMessage"><@s.text name="action.level.error1" />&nbsp;${actionErrors.size()}&nbsp;<@s.text name="action.level.error2" /></span>
<#elseif (actionErrors.size() == 1)>
<span class="errorMessage"><@s.text name="action.level.error3" /></span>
</#if><#t/>
<ul>
<#list actionErrors as error><#t/>
<li><span class="errorMessage3">${error}</span></li>
</#list><#t/>
</ul>
</#if>
<#if (fieldErrors?exists && fieldErrors.size() > 0)>
<#if (fieldErrors.size() > 1)>
<span class="errorMessage"><@s.text name="field.level.error1" />&nbsp;${fieldErrors.size()}&nbsp;<@s.text name="field.level.error2" /></span>
<#elseif (fieldErrors.size() == 1)>
<span class="errorMessage"><@s.text name="field.level.error3" /></span>
</#if><#t/>
<#assign eKeys = fieldErrors.keySet()><#t/>
<#assign eKeysSize = eKeys.size()><#t/>
<#if (eKeysSize > 0)><#t/>
<ul>
<#list eKeys as eKey><#t/>
<li><span class="errorMessage3">${fieldErrors[eKey]?replace("[","")?replace("]","")}</span></li>
</#list><#t/>
</ul>
</#if><#t/>
</#if> 
</div>
</#if>