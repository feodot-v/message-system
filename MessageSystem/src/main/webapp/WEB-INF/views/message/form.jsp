<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<div id="crumb">Отправка сообщения</div>
<datalist id="names" >
    <c:forEach items="${usersNames}" var="name">
        <option>${name}</option>
    </c:forEach>
</datalist>
<sec:authentication property="principal.username" var="username"/>
<sf:form id="messageForm" action="create" modelAttribute="message" method="POST" >
    <input type="hidden" name="fromUser" value="${username}"/>
    <table style="margin-top: 20px">
        <tr>
            <td class="alignRight">Получатель</td>
            <td><input style="width: 100%;font-size: large" type="text" name="toUser" required list="names"/>
            </td>
            <td><input type="submit" value="Отправить" /></td>
            <td class="error">${errMessage}</td>
        </tr>
        <tr>
            <td class="alignRight">Тема</td>
            <td colspan="2"><sf:input cssStyle="width: 100%; font-size: large" path="title"/></td>
            <td class="error"><sf:errors path="title"/></td>
        </tr>
        <tr>
            <td class="alignRight">Сообщение</td>
            <td colspan="2"><sf:textarea cols="80" rows="30" path="messageText"/></td>
        </tr>
    </table>
</sf:form>
