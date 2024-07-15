package org.eclipse.jakarta.hello;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main extends HttpServlet {

    private static List<Producto> productos = new ArrayList<>();
    private static int nextId = 1;

    public void init() throws ServletException {
        // Inicializar con algunos productos por defecto
        productos.add(new Producto(nextId++, "Laptop", 1200.00));
        productos.add(new Producto(nextId++, "Mouse", 25.00));
        productos.add(new Producto(nextId++, "Teclado", 50.00));
        productos.add(new Producto(nextId++, "Monitor", 300.00));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
PrintWriter out = resp.getWriter();
    cabecera(req,resp,out);

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // No hay sesión, redirigir al login
            resp.sendRedirect("login");
            return;
        }


        // Mostrar formulario para añadir un nuevo producto
        out.println("<h1>Gestión de productos</h1>");
        out.println("<a href='logout'>Cerrar sesión</a>");

        out.println("<h3>Añadir un nuevo producto</h3>");
        out.println("<form method='post' action='main?action=add' autocomplete='off'>");
        out.println("<label for='name'>Nombre:</label>");
        out.println("<input type='text' id='name' name='name' required><br><br>");
        out.println("<label for='price'>Precio:</label>");
        out.println("<input type='number' id='price' name='price' step='0.01' required><br><br>");
        out.println("<input type='submit' value='Añadir producto'>");
        out.println("</form>");

        // Mostrar la lista de productos en una tabla
        out.println("<h3>Mis productos</h3>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Nombre</th><th>Precio</th><th>Acciones</th></tr>");
        for (Producto producto : productos) {
            out.println("<tr>");
            out.println("<td>" + producto.getId() + "</td>");
            out.println("<td>" + producto.getNombre() + "</td>");
            out.println("<td>" + producto.getPrecio() + "</td>");
            out.println("<td>");

            // Formulario para editar el producto
            out.println("<form method='get' action='main' style='display:inline;'>");
            out.println("<input type='hidden' name='action' value='editForm'>");
            out.println("<input type='hidden' name='id' value='" + producto.getId() + "'>");
            out.println("<input type='submit' value='Edit'>");
            out.println("</form> ");

            // Formulario para eliminar el producto
            out.println("<form method='post' action='main' style='display:inline;'>");
            out.println("<input type='hidden' name='action' value='delete'>");
            out.println("<input type='hidden' name='id' value='" + producto.getId() + "'>");
            out.println("<input type='submit' value='Delete'>");
            out.println("</form>");

            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");


        // Mostrar formulario de edición si se solicita
        String action = req.getParameter("action");
        if ("editForm".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Producto producto = productos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
            if (producto != null) {
                out.println("<h3>Editar Producto</h3>");
                out.println("<form method='post' action='main' autocomplete='off' >");
                out.println("<input type='hidden' name='action' value='edit'>");
                out.println("<input type='hidden' name='id' value='" + producto.getId() + "'>");
                out.println("<label for='name'>Nombre:</label>");
                out.println("<input type='text' id='name' name='name' value='" + producto.getNombre() + "' required><br><br>");
                out.println("<label for='price'>Precio:</label>");
                out.println("<input type='number' id='price' name='price' step='0.01' value='" + producto.getPrecio() + "' required><br><br>");
                out.println("<input type='submit' value='Actualizar producto'>");
                out.println("</form>");
            }
        }

    footer(req,resp,out);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String name = req.getParameter("name");
            double price = Double.parseDouble(req.getParameter("price"));

            Producto producto = new Producto(nextId++, name, price);
            productos.add(producto);
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            double price = Double.parseDouble(req.getParameter("price"));

            for (Producto producto : productos) {
                if (producto.getId() == id) {
                    producto.setNombre(name);
                    producto.setPrecio(price);
                    break;
                }
            }
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            productos.removeIf(producto -> producto.getId() == id);
        }

        resp.sendRedirect("main");
    }






    private void cabecera(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) throws IOException {
        //  PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello World</title>");
        out.println("</head>");
        out.println("<body>");

    }


    private void footer(HttpServletRequest req,HttpServletResponse resp,PrintWriter out) throws IOException {
        //* PrintWriter out = resp.getWriter();

        out.println("</body>");
        out.println("</html>");
    }

}
