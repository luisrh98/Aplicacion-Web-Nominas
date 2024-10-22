<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Buscar Salario</title>
</head>
<body>
    <h2>Buscar Salario de un Empleado</h2>
    <form action="empleados" method="post">
        <input type="hidden" name="action" value="buscarSalario">
        <label for="dni">DNI del Empleado:</label>
        <input type="text" name="dni" id="dni" required>
        <button type="submit">Buscar Salario</button>
    </form>
    <a href="index.jsp">Volver</a> <!-- Opción para volver -->
</body>
</html>