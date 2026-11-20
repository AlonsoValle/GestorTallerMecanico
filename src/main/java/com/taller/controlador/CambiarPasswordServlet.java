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
@WebServlet("/CambiarPasswordServlet")
public class CambiarPasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("tempUsuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        String nuevaPassword = request.getParameter("nuevaPassword");
        Usuario temp = (Usuario) session.getAttribute("tempUsuario");
        String errorPassword = UsuarioDAO.validarPoliticaPassword(temp.getUsername(), nuevaPassword);
        if (errorPassword != null) {
            request.setAttribute("error", errorPassword);
            request.getRequestDispatcher("cambiar_password.jsp").forward(request, response);
            return;
        }
        UsuarioDAO dao = new UsuarioDAO();
        dao.actualizarPassword(temp.getIdUsuario(), nuevaPassword);
        temp.setRequiereCambio(false);
        session.removeAttribute("tempUsuario");
        session.setAttribute("usuarioLogueado", temp);
        response.sendRedirect("InventarioServlet");
    }
}