package com.taller.controlador;

import com.taller.dao.BitacoraDAO;
import com.taller.dao.InventarioDAO;
import com.taller.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/EliminarItemServlet")
public class EliminarItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.trim().isEmpty()) {
            int id = Integer.parseInt(idParam.trim());

            InventarioDAO dao = new InventarioDAO();
            boolean eliminado = dao.eliminarItem(id);

            if (eliminado) {
                Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
                BitacoraDAO bDao = new BitacoraDAO();
                bDao.registrarMovimiento(usuario.getUsername(), "ELIMINAR", "Eliminó el ID: " + id);
            }
        }

        response.sendRedirect("InventarioServlet");
    }
}