package com.taller.controlador;

import com.taller.dao.InventarioDAO;
import com.taller.modelo.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/InventarioServlet")
public class InventarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String busqueda = request.getParameter("busqueda");
        String tipoFiltro = request.getParameter("tipoFiltro");
        String ordenId = request.getParameter("ordenId"); // Nuevo parámetro

        InventarioDAO dao = new InventarioDAO();
        List<Item> listaItems = dao.listarInventario(busqueda, tipoFiltro, ordenId);

        request.setAttribute("listaInventario", listaItems);
        request.setAttribute("busquedaActual", busqueda != null ? busqueda : "");
        request.setAttribute("filtroActual", tipoFiltro != null ? tipoFiltro : "Todos");
        request.setAttribute("ordenActual", ordenId != null ? ordenId : "mayor");

        request.getRequestDispatcher("inventario.jsp").forward(request, response);
    }
}