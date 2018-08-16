<jsp:useBean id="resume" scope="request" type="com.urise.webapp.model.Resume"/>
<%@ page import="com.urise.webapp.model.Contacts" %>
<%--
  Created by IntelliJ IDEA.
  User: plotnikov
  Date: 16.08.2018
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>EditResume</title>
</head>
<body>
<section>
    <form method="post" action="resume">
        <h2>
            <dl>
                <dt><b>Имя</b></dt>
                <dd>
                    <p>
                        <a href="resume?uuid=${resume.uuid}&action=edit">
                            ${resume.fullname}
                        </a>
                    </p>
                </dd>
            </dl>
        </h2>
        <hr>
        <br><br>
        <section>
            <h3>Контакты</h3>
            <br>
            <c:forEach var="element" items="${Contacts.values()}">
                <dl>
                    <dt><b>${element.title}</b></dt>
                    <dd>${resume.getContact(element)}</dd>
                </dl>
            </c:forEach>
        </section>
        <input type="button" onclick="history.back();" value="Назад"/>
    </form>
</section>
</body>
</html>
