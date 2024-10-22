<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Salario del Empleado</title>
</head>
<body>
    <h2>Salario del Empleado</h2>
    <c:choose>
        <c:when test="${not empty salario}">
            <p>El salario del empleado con DNI <strong>${dni}</strong> es: <strong>${salario}</strong></p>
        </c:when>
        <c:otherwise>
            <p>No se encontró un empleado con el DNI <strong>${dni}</strong>.</p>
        </c:otherwise>
    </c:choose>
    <a href="buscarSalario.jsp">Buscar otro salario</a> <!-- Opción para buscar otro -->
    <a href="index.jsp">Volver al inicio</a> <!-- Opción para volver -->
</body>
</html>