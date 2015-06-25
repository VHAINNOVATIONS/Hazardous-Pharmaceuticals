<%@ tag
    language="java"
    body-content="empty"%>
<%@ attribute
    name="key"
    required="true"
    type="java.lang.String"
    description="FieldKey for which to get localized name."%>
<%@ attribute
    name="entityType"
    required="false"
    type="gov.va.med.pharmacy.peps.common.vo.EntityType"
    description="Optional EntityType for which to localize"%>
<%=gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedAbbreviation(key, request.getLocale(), entityType)%>