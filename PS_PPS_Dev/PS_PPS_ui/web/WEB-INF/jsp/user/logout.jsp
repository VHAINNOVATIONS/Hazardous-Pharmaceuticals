<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>
<%@ taglib  
    prefix="c" 
    uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib 
    prefix="form" 
    uri="http://www.springframework.org/tags/form"%>
<%@taglib 
    prefix="spring" 
    uri="http://www.springframework.org/tags"%>    

<div class="panel" style="width: 1250px;">
    System has logged the user out.
</div>

<%--
    response.sendRedirect(response.encodeURL(request.getContextPath() + "/index.jsp"));
--%>
<script type="text/javascript">
    window.location.assign("/PRE/index.jsp");
</script>