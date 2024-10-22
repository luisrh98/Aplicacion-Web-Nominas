package com.aprendec.dao;

import com.aprendec.conexion.Conexion;  // Asegúrate de importar tu clase de conexión
import com.aprendec.excepciones.DatosNoCorrectosException;
import com.aprendec.model.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadosDAO {

    // Obtener todos los empleados
    public List<Empleado> obtenerTodosLosEmpleados() throws DatosNoCorrectosException {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT nombre, dni, sexo, categoria, anyos FROM empleados";
        
        try (Connection connection = Conexion.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String sexo = rs.getString("sexo");
                int categoria = rs.getInt("categoria");
                int anyos = rs.getInt("anyos");
                Empleado emp = new Empleado(nombre,dni,sexo,categoria,anyos);
                empleados.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    // Obtener salario por DNI
    public double obtenerSalarioPorDni(String dni) {
        double salario = 0;
        String sql = "SELECT sueldo FROM nomina WHERE dni = ?";
        
        try (Connection connection = Conexion.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
             
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                salario = rs.getDouble("sueldo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salario;
    }

    // Modificar datos del empleado
    public boolean modificarEmpleado(Empleado empleado) {
        String sql = "UPDATE empleados SET categoria = ?, anyos = ? WHERE dni = ?";
        
        try (Connection connection = Conexion.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
             
            ps.setInt(1, empleado.getCategoria());
            ps.setInt(2, empleado.getAnyos());
            ps.setString(3, empleado.getDni());
            
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Buscar empleado por dni
    
    public Empleado obtenerEmpleadoPorDni(String dni) {
        String sql = "SELECT nombre, dni, sexo, categoria, anyos FROM empleados WHERE dni = ?";
        try (Connection connection = Conexion.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Crear el empleado usando el constructor completo
                return new Empleado(
                    rs.getString("nombre"),
                    rs.getString("dni"),
                    rs.getString("sexo"),
                    rs.getInt("categoria"),
                    rs.getInt("anyos")
                );
            }
        } catch (SQLException | DatosNoCorrectosException e) {
            e.printStackTrace();
        }
        return null; // Si no se encontró el empleado
    }
    
   
    public static List<Empleado> buscarEmpleados(String nombre, String dni, String sexo, Integer categoria, Integer anyos) throws DatosNoCorrectosException, SQLException {
        List<Empleado> empleados = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM empleados WHERE 1=1");
        
        // Condiciones dinámicas: los campos que no están vacíos/nulos se añaden a la consulta
        if (nombre != null && !nombre.isEmpty()) sql.append(" AND nombre LIKE ?");
        if (dni != null && !dni.isEmpty()) sql.append(" AND dni = ?");
        if (sexo != null && !sexo.isEmpty()) sql.append(" AND sexo = ?");
        if (categoria != null) sql.append(" AND categoria = ?");
        if (anyos != null) sql.append(" AND anyos = ?");

        try (Connection conn = Conexion.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Asignar parámetros solo a los campos que tienen valores
            int index = 1;
            if (nombre != null && !nombre.isEmpty()) stmt.setString(index++, "%" + nombre + "%");
            if (dni != null && !dni.isEmpty()) stmt.setString(index++, dni );
            if (sexo != null && !sexo.isEmpty()) stmt.setString(index++, sexo );
            if (categoria != null) stmt.setInt(index++, categoria);
            if (anyos != null) stmt.setInt(index++, anyos);

            // Ejecutar la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    empleados.add(new Empleado(
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("sexo"),
                        rs.getInt("categoria"),
                        rs.getInt("anyos")
                    ));
                }
            }
        }

        // Imprimir consulta y parámetros usados para depuración
        StringBuilder consultaFinal = new StringBuilder("Consulta SQL: " + sql.toString());
        consultaFinal.append("\nParámetros utilizados:");
        consultaFinal.append("\nNombre: " + (nombre != null && !nombre.isEmpty() ? "%" + nombre + "%" : "No especificado"));
        consultaFinal.append("\nDNI: " + (dni != null && !dni.isEmpty() ? "%" + dni + "%" : "No especificado"));
        consultaFinal.append("\nSexo: " + (sexo != null && !sexo.isEmpty() ? "%" + sexo + "%" : "No especificado"));
        consultaFinal.append("\nCategoría: " + (categoria != null ? categoria : "No especificado"));
        consultaFinal.append("\nAños de experiencia: " + (anyos != null ? anyos : "No especificado"));

        System.out.println(consultaFinal.toString());

        return empleados;
    }
    
    //Actualizar empleado
    
    public static boolean actualizarEmpleado(Empleado empleado) throws SQLException {
        String sql = "UPDATE empleados SET nombre = ?, sexo = ?, categoria = ?, anyos = ? WHERE dni = ?";

        try (Connection conn = Conexion.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getSexo());
            stmt.setInt(3, empleado.getCategoria());
            stmt.setInt(4, empleado.getAnyos());
            stmt.setString(5, empleado.getDni());

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0; // Retorna true si se actualizaron filas
        }
    }
     
    
}