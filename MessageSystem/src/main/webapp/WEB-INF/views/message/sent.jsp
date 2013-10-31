<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<div id="crumb">Отправленные сообщения</div>
<sec:authorize access="hasRole('ROLE_ADMIN') or principal.username=='${userName}'">
<div id="sent">
    <div id="title">Отправленные сообщения участника ${userName}</div>
    <table class="bordered" border="1">
        <tr>
            <th>Получатель</th>
            <th>Тема</th>
            <th>Сообщение</th>
            <th>Действия</th>
        </tr>
        <c:forEach var="message" items="${messages}">
            <c:url value="delete" var="deleteURL">
                <c:param name="messageID" value="${message.id}"/>
                <c:param name="messageType" value="sent"/>
            </c:url>
            <tr>
                <td>${message.toUser}</td>
                <td>${message.title}</td>
                <td>${message.messageText}</td>
                <td><a href="${deleteURL}">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
</sec:authorize>