package com.taller.controlador;
import com.taller.dao.UsuarioDAO;
import com.taller.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/RegistroUsuarioServlet")
public class RegistroUsuarioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");
        if (!admin.getRol().equals("Administrador")) {
            response.sendRedirect("InventarioServlet");
            return;
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rol = request.getParameter("rol");
        String errorPassword = UsuarioDAO.validarPoliticaPassword(username, password);
        if (errorPassword != null) {
            request.setAttribute("error", errorPassword);
            request.setAttribute("usernamePrevio", username);
            request.getRequestDispatcher("nuevo_usuario.jsp").forward(request, response);
            return;
        }
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(username.trim());
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setRol(rol);
        UsuarioDAO dao = new UsuarioDAO();
        if (dao.registrarUsuario(nuevoUsuario, true)) {
            response.sendRedirect("InventarioServlet");
        } else {
            request.setAttribute("error", "Error al guardar el usuario. Verifica que no esté duplicado.");
            request.getRequestDispatcher("nuevo_usuario.jsp").forward(request, response);
        }
    }
}