<%--
  Created by IntelliJ IDEA.
  User: plotnikov
  Date: 16.08.2018
  Time: 10:06
  To change this template use File | Settings | File Templates.
--%>
<%--<jsp:useBean id="Contacts" type="com.urise.webapp.model.Contacts"/>--%>
<%@ page import="com.urise.webapp.model.Contacts" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>AllResume</title>
</head>
<body>
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <td width="25%">
            Имя
        </td>
        <td width="25%">
            EMail
        </td>
        <td width="25%">
        </td>
        <td width="25%">
        </td>
    </tr>
    <c:forEach var="element" items="${resumes}">
        <jsp:useBean id="element" type="com.urise.webapp.model.Resume"/>
        <tr>
            <td width="25%">
                <a href=resume?uuid=${element.uuid}&action=view>
                    ${element.fullname}
                </a>
            </td>
            <td width="25%">
                ${element.getContact(Contacts.MAIL)}
            </td>
            <td width="25%">
                <a href=resume?uuid=${element.uuid}&action=edit>
                    edit
                </a>
            </td>
            <td width="25%">
                <a href=resume?uuid=${element.uuid}&action=delete>
                    delete
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
<br><br>
<a href=resume?&action=add>
    Добавить
</a>
</body>
</html>
