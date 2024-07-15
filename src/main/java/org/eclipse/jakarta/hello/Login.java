package org.eclipse.jakarta.hello;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/main")

public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        PrintWriter out = resp.getWriter();

       cabecera(req, resp, out);

       //mostrar el contenido
        out.println("<h2>Login</h2>");
        out.println("<form method='post' action='login'>");
        out.println("<label for='username'>Usuario:</label>");
        out.println("<input type='text' id='username' name='username'><br><br>");
        out.println("<label for='password'>Contraseña:</label>");
        out.println("<input type='password' id='password' name='password'><br><br>");
        out.println("<input type='hidden' name='action' value='login'>");
        out.println("<input type='submit' value='Login'>");

       footer(req, resp,out);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();


        cabecera(req, resp, out);

        String action = req.getParameter("action");
        if ("login".equals(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            // Aquí iría la lógica de autenticación, por ejemplo:
            if ("admin".equals(username) && "admin".equals(password)) {
                //creamos la sesion y redireccionamos a la pagina prinicpal

                HttpSession session = req.getSession();
                session.setAttribute("user", username);
                resp.sendRedirect("main");
            } else {
                resp.setContentType("text/html");


                out.println("<h2>Login Failed</h2>");
                out.println("<p>Invalid username or password.</p>");
                out.println("<a href='login'>Try again</a>");

            }
        }

        footer(req, resp,out);


    }


    private void cabecera(HttpServletRequest req,HttpServletResponse resp,PrintWriter out) throws IOException {
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
