<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Buscar Empleado</title>
</head>
<body>
    <h2>Buscar Empleado</h2>
    <form action="empleados" method="post">
    <input type="hidden" name="action" value="buscar">
        <!-- Campos para buscar por cualquier criterio -->
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre"><br><br>
        
        <label for="dni">DNI:</label>
        <input type="text" id="dni" name="dni"><br><br>
        
        <label for="sexo">Sexo:</label>
		<input type="text" id="sexo" name="sexo"><br><br>

        <label for="categoria">Categoría:</label>
        <input type="number" id="categoria" name="categoria"><br><br>

        <label for="anyos">Años de experiencia:</label>
        <input type="number" id="anyos" name="anyos"><br><br>

        <!-- Botón para buscar -->
        <button type="submit">Buscar</button>
    </form>
</body>
</html>