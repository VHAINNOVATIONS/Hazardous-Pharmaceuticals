<%@ tag
    language="java"
    body-content="empty"
    description="Hidden div containing localized messages and other values for JavaScript alert/confirm messages."%>
<%@ taglib  
	prefix="spring"
	uri="http://www.springframework.org/tags"%>
<%@ tag import="java.util.Date" %>
<%@ tag import="org.apache.log4j.Logger" %>

<div class="hidden">
    <div id="session.timeout.message">
        <div><p><spring:message code="session.timeout" /></p></div>
        <div>
            <input type="button" value=" OK " onclick="skipLogout(); return false;"/>
        </div>
    </div>
</div>

<span class="hidden">
    <span id="page.changed"><spring:message code="page.changed" /></span>
    <span id="multi.changed"><spring:message code="multi.changed" /></span>
    <span id="search.type.changed"><spring:message code="search.type.changed" /></span>
    <span id="page.partialsave.changed"><spring:message code="page.partialsave.changed" /></span>
    <span id="popup.already.open"><spring:message code="popup.already.open" /></span>
    <span id="confirm.close.popups"><spring:message code="confirm.close.popups" /></span>
    <span id="session.ended"><spring:message code="session.ended" /></span>
    <span id="session.last.accessed.time"><%=request.getSession().getLastAccessedTime()%></span>
    <span id="session.max.inactive.interval"><%=request.getSession().getMaxInactiveInterval()%></span>
    <%
    //Logger LOG = Logger.getLogger("javascriptLocalization.tag");
    long timeout = request.getSession().getLastAccessedTime() + (request.getSession().getMaxInactiveInterval() * 1000);
    Date to = new Date();
    to.setTime(request.getSession().getLastAccessedTime());
    //LOG.debug("last accessed: . " + to.toLocaleString());
    to.setTime(timeout);
    //LOG.debug("session timeout: " + to.toLocaleString());
    %>
    <span id="session.timeout"><%=timeout %></span>
    <span id="user.logged.in"><%=request.getSession().getAttribute("user") != null %></span>
    <span id="timeout_text"></span>
    <span id="select.all"><spring:message code="select.all" /></span>
    <span id="unselect.all"><spring:message code="unselect.all" /></span>
    <span id="click.to.lock"><spring:message code="click.to.lock" /></span>
    <span id="click.to.unlock"><spring:message code="click.to.unlock" /></span>
</span>