package com.aprendec.controller;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.aprendec.dao.EmpleadosDAO;
import com.aprendec.excepciones.DatosNoCorrectosException;
import com.aprendec.model.Empleado;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/empleados")
public class EmpleadoController extends HttpServlet {

    private EmpleadosDAO empleadosDAO;

    @Override
    public void init() {
        empleadosDAO = new EmpleadosDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "listar":
			try {
				listarEmpleados(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatosNoCorrectosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
            case "buscarSalario":
                mostrarFormularioBuscarSalario(request, response);
                break;
            case "modificar":
			try {
				mostrarFormularioModificar(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
            case "buscar":
			try {
				buscarEmpleadoServlet(request, response);
			} catch (ServletException | IOException | DatosNoCorrectosException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
            case "editar":
                mostrarFormularioEditarEmpleado(request, response);  // Nueva acción
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }

    // Lista todos los empleados
    private void listarEmpleados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DatosNoCorrectosException {
        List<Empleado> listaEmpleados = empleadosDAO.obtenerTodosLosEmpleados();
        request.setAttribute("listaEmpleados", listaEmpleados);
        request.getRequestDispatcher("/views/listar.jsp").forward(request, response);
    }

    // Muestra el formulario para buscar salario por DNI
    private void mostrarFormularioBuscarSalario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/buscarSalario.jsp").forward(request, response);
    }

    // Muestra el formulario para modificar datos del empleado
    private void mostrarFormularioModificar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/buscarEmpleado.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "buscarSalario":
                buscarSalarioEmpleado(request, response);
                break;
            case "modificar":
			try {
				mostrarFormularioModificar(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
            case "buscar":
			try {
				buscarEmpleadoServlet(request, response);
				
			} catch (ServletException | IOException | DatosNoCorrectosException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
            case "actualizar":
			try {
				actualizarEmpleado(request, response);
			} catch (ServletException | IOException | DatosNoCorrectosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  // Nueva acción para actualizar el empleado
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }

    // Busca y muestra el salario del empleado por DNI
    private void buscarSalarioEmpleado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dni = request.getParameter("dni");
        double salario = empleadosDAO.obtenerSalarioPorDni(dni);
        request.setAttribute("salario", salario);
        request.setAttribute("dni", dni);
        request.getRequestDispatcher("/views/mostrarSalario.jsp").forward(request, response);
    }
    
    // Modifica los datos de un empleado
    
    
    protected void buscarEmpleadoServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DatosNoCorrectosException, SQLException {
        // Recoger los parámetros de búsqueda
        String nombre = request.getParameter("nombre");
        String dni = request.getParameter("dni");
        String sexo = request.getParameter("sexo");
        String categoriaStr = request.getParameter("categoria");
        String anyosStr = request.getParameter("anyos");
        
        System.out.println("Nombre: " + nombre);
        System.out.println("DNI: " + dni);
        System.out.println("Sexo: " + sexo);
        System.out.println("Categoría (String): " + categoriaStr);
        System.out.println("Años (String): " + anyosStr);
        
        
        Integer categoria = null;
        Integer anyos = null;
        
     // Convertir a Integer de manera segura
        if (categoriaStr != null && !categoriaStr.isEmpty()) {
            try {
                categoria = Integer.parseInt(categoriaStr);
            } catch (NumberFormatException e) {
                // Manejar el error de conversión si es necesario
                e.printStackTrace(); // O loguea el error
            }
        }

        if (anyosStr != null && !anyosStr.isEmpty()) {
            try {
                anyos = Integer.parseInt(anyosStr);
            } catch (NumberFormatException e) {
                // Manejar el error de conversión si es necesario
                e.printStackTrace(); // O loguea el error
            }
        }

        // Lógica para buscar empleados en la base de datos (simulada aquí)
        List<Empleado> empleados = EmpleadosDAO.buscarEmpleados(nombre, dni, sexo, categoria, anyos);
        
        for(Empleado empleado : empleados) {
        	empleado.imprime();
        }

        // Enviar la lista de empleados al JSP
        request.setAttribute("empleados", empleados);
        request.getRequestDispatcher("/views/resultadoBusqueda.jsp").forward(request, response);
        
    }
    
    //Mostrar form
    private void mostrarFormularioEditarEmpleado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dni = request.getParameter("dni");
        if (dni != null && !dni.isEmpty()) {
            Empleado empleado = empleadosDAO.obtenerEmpleadoPorDni(dni); // Método que obtiene el empleado
			if (empleado != null) {
			    request.setAttribute("empleado", empleado);
			    request.getRequestDispatcher("/views/editarEmpleado.jsp").forward(request, response);
			} else {
			    response.sendRedirect("listar.jsp");
			}
        } else {
            response.sendRedirect("listar.jsp");
        }
    }
    
 // Método para actualizar los datos del empleado
    private void actualizarEmpleado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DatosNoCorrectosException {
        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String sexo = request.getParameter("sexo");
        Integer categoria = Integer.parseInt(request.getParameter("categoria"));
        Integer anyos = Integer.parseInt(request.getParameter("anyos"));

        try {
            Empleado empleado = new Empleado(nombre, dni, sexo, categoria, anyos);
            boolean actualizado = empleadosDAO.actualizarEmpleado(empleado); // Actualizar en la BD
            if (actualizado) {
                response.sendRedirect("empleados?action=listar");  // Redirigir a la lista de empleados
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
    
}