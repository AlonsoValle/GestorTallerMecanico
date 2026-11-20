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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.validarUsuario(user, pass);

        if (usuario != null) {
            HttpSession session = request.getSession();
            if (usuario.isRequiereCambio()) {
                session.setAttribute("tempUsuario", usuario);
                response.sendRedirect("cambiar_password.jsp");
            } else {
                session.setAttribute("usuarioLogueado", usuario);
                response.sendRedirect("InventarioServlet");
            }
        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}