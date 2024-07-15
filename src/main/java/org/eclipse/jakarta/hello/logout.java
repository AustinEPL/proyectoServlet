package org.eclipse.jakarta.hello;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        //cerrando sesion
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title></title>");

        out.println("<script type='text/javascript'>");
        out.println("function redirectToLogin() {");
        out.println("    window.location.href = 'login';");
        out.println("}");
        out.println("setTimeout(redirectToLogin, 3000);");
        out.println("</script>");

        out.println("</head>");
        out.println("<body>");

        out.println("<h3>Finalziando la sesión</h3>");
    out.println("</body>");
    out.println("</html>");

    }



    private void cabecera(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) throws IOException {
        //  PrintWriter out = resp.getWriter();

        out.println("<body>");

    }


    private void footer(HttpServletRequest req,HttpServletResponse resp,PrintWriter out) throws IOException {
        //* PrintWriter out = resp.getWriter();

        out.println("</body>");
        out.println("</html>");
    }

}





