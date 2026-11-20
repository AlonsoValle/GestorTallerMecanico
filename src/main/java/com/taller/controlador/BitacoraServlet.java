package com.taller.controlador;

import com.taller.dao.BitacoraDAO;
import com.taller.modelo.Bitacora;
import com.taller.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/BitacoraServlet")
public class BitacoraServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        BitacoraDAO dao = new BitacoraDAO();
        List<Bitacora> lista = dao.listarMovimientos();

        request.setAttribute("listaBitacora", lista);
        request.getRequestDispatcher("bitacora.jsp").forward(request, response);
    }
}