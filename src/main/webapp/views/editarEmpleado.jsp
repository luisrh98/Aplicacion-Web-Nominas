<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Empleado</title>
</head>
<body>
    <h2>Editar Empleado</h2>
    <form action="empleados?action=actualizar" method="post">
        <input type="hidden" name="dni" value="${empleado.dni}" />

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" value="${empleado.nombre}" required /><br/>

        <label for="sexo">Sexo:</label>
        <input type="text" id="sexo" name="sexo" value="${empleado.sexo}" required /><br/>

        <label for="categoria">Categoría:</label>
        <input type="number" id="categoria" name="categoria" value="${empleado.categoria}" required /><br/>

        <label for="anyos">Años de experiencia:</label>
        <input type="number" id="anyos" name="anyos" value="${empleado.anyos}" required /><br/>

        <button type="submit">Actualizar</button>
    </form>
</body>
</html>