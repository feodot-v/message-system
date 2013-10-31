<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<div id="crumb">Создание участника</div>
<sec:authorize access="hasRole('ROLE_ADMIN') or principal.username=='${MUser.userName}'">
    <form:form id="userForm" modelAttribute="MUser" method="POST">
        <h2>Заполните необходимые значения и нажмите кнопку 
            <c:choose>
                <c:when test="${MUser.id==null}">
                    <input type="submit" formaction="create" value="Создать">
                </c:when>
                <c:otherwise>
                    <form:hidden path="id"/>
                    <input type="submit" formaction="update" value="Применить"/>
                </c:otherwise>
            </c:choose>
        </h2>
        <table>
            <tr>
                <td>Фамилия:</td>
                <td><form:input path="lastname"/></td>
                <td class="error"><form:errors path="lastname"/></td>
            </tr>
            <tr>
                <td>Имя:</td>
                <td><form:input path="firstname"/></td>
                <td class="error"><form:errors path="firstname"/></td>
            </tr>
            <tr>
                <td>Никнейм:</td>
                <td><form:input path="userName"/></td>
                <td class="error"><form:errors path="userName"/></td>
                <td class="error">${errMessage}</td>
            </tr>
            <tr>
                <td>Пароль:</td>
                <td><form:input path="password"/></td>
                <td class="error"><form:errors path="password"/></td>
            </tr>
        </table>
    </form:form>    
</sec:authorize>