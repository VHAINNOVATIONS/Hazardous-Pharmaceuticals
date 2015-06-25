<%@ tag
    language="java"
    body-content="empty"%>
<%@ attribute
    name="key"
    required="true"
    type="java.lang.String"
    description="FieldKey for which to get localized width."%>
<%=gov.va.med.pharmacy.peps.common.vo.FieldKey.getLocalizedWidth(key, request.getLocale())%>