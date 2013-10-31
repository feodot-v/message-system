<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Message system</title>
        <link rel="stylesheet" type="text/css" href="<s:url value="/css"/>/messagesystem.css">
    </head>

    <body>
        <div id="header">
            <span style="display: table-cell;vertical-align: inherit;">Система обмена сообщениями</span> 
        </div>
        <div id="breadcrumbs">
            <t:insertAttribute name="crumbs"/>
        </div>
        <a style="float: right" href="<s:url value="/j_spring_security_logout"/>">Logout</a>    

        <div id="content">
            <t:insertAttribute name="content"/>
        </div>
