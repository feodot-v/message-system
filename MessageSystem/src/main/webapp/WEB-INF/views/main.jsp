<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="main">
    <sec:authentication property="principal.username" var="userName"/>
    <c:url value="message/inbox/${userName}" var="inboxURL"/>
    <c:url value="message/sent/${userName}" var="sentURL"/>
     
    <h2><a href="<c:url value="user/list"/>">Список участников в системе</a></h2>
    <h2><a href="<c:url value="message/create"/>">Отправить сообщение</a></h2>
    <h2><a href="${inboxURL}">Полученные сообщения</a></h2>
    <h2><a href="${sentURL}">Отправленные сообщения</a></h2>
</div>
   