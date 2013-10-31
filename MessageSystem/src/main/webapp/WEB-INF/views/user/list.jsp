<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<div id="crumb">Список участников в системе</div>
<c:set var="role" value="hasRole('ROLE_ADMIN')"/>
<div id="title">
Список участников в системе
<sec:authorize access="${role}" >
    <form style="float: right" action="create" method="GET">
        <input type="submit" value="Создать нового участника"/> 
    </form>
</sec:authorize>
</div>
<table class="bordered">
    <tr>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>Никнейм</th>
        <sec:authorize access="${role}">
        <th>Пароль</th>
        <th style="width: 30%">Действия</th>
        </sec:authorize>
    </tr>
    <c:forEach items="${users}" var="user">
        <c:url value="update" var="updateURL">
            <c:param name="userID" value="${user.id}"/>
        </c:url>
        <c:url value="delete" var="deleteURL">
            <c:param name="userID" value="${user.id}"/>
        </c:url>
        <c:url value="/message/inbox/${user.userName}" var="inboxURL">
        </c:url>
        <c:url value="/message/sent/${user.userName}" var="sentURL">
        </c:url>
        <tr>
            <td><c:out value="${user.lastname}"/></td>
            <td><c:out value="${user.firstname}"/></td>
            <td><c:out value="${user.userName}"/></td>
            <sec:authorize access="${role}">
                <td><c:out value="${user.password}"/></td>
                <td style="width: 33%">
                    <a href="${updateURL}">Модифицировать</a>
                    <a href="${deleteURL}">Удалить</a>
                    <a href="${inboxURL}">Полученные</a>
                    <a href="${sentURL}">Отправленные</a>
                </td>
            </sec:authorize>
        </tr>
    </c:forEach>
</table>