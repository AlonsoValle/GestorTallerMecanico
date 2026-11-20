package com.taller.controlador;
import com.taller.dao.UsuarioDAO;
import com.taller.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/SetupAdminServlet")
public class SetupAdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO dao = new UsuarioDAO();
        if (dao.existenUsuarios()) {
            response.sendRedirect("index.jsp");
            return;
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errorPassword = UsuarioDAO.validarPoliticaPassword(username, password);
        if (errorPassword != null) {
            request.setAttribute("error", errorPassword);
            request.setAttribute("usernamePrevio", username);
            request.getRequestDispatcher("setup_admin.jsp").forward(request, response);
            return;
        }
        Usuario admin = new Usuario();
        admin.setUsername(username.trim());
        admin.setPassword(password);
        admin.setRol("Administrador");
        if (dao.registrarUsuario(admin, false)) {
            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("error", "No se pudo crear el administrador inicial.");
            request.getRequestDispatcher("setup_admin.jsp").forward(request, response);
        }
    }
}