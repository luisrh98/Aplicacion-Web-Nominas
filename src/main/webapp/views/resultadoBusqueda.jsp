<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.aprendec.model.Empleado" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Resultados de la Búsqueda</title>
</head>
<body>
    <h2>Resultados de la Búsqueda</h2>

    <c:choose>
        <c:when test="${not empty empleados}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>DNI</th>
                        <th>Sexo</th>
                        <th>Categoria</th>
                        <th>Años experiencia</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterar sobre los empleados encontrados -->
                    <c:forEach var="empleado" items="${empleados}">
                        <tr>
                            <td>${empleado.nombre}</td>
                            <td>${empleado.dni}</td>
                            <td>${empleado.sexo}</td>
                            <td>${empleado.categoria}</td>
                            <td>${empleado.anyos}</td>
                            <td>
                                <!-- Botón para editar, pasa el ID del empleado como parámetro -->
                                <form action="editarEmpleado.jsp" method="get">
                                    <input type="hidden" name="dni" value="${empleado.dni}">
                                    <button type="submit">Editar</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No se encontraron empleados con los criterios de búsqueda especificados.</p>
        </c:otherwise>
    </c:choose>

</body>
</html>