package com.taller.controlador;

import com.taller.dao.BitacoraDAO;
import com.taller.dao.InventarioDAO;
import com.taller.modelo.Item;
import com.taller.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/EditarItemServlet")
public class EditarItemServlet extends HttpServlet {

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
            Item item = dao.obtenerItemPorId(id);

            request.setAttribute("itemEdicion", item);
            request.getRequestDispatcher("editar_item.jsp").forward(request, response);
        } else {
            response.sendRedirect("InventarioServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Item itemActualizado = new Item();
        itemActualizado.setIdItem(Integer.parseInt(request.getParameter("idItem").trim()));
        itemActualizado.setTipoItem(request.getParameter("tipoItem"));
        itemActualizado.setDescripcion(request.getParameter("descripcion"));
        itemActualizado.setCantidadStock(Integer.parseInt(request.getParameter("cantidadStock").trim()));
        itemActualizado.setPrecio(Double.parseDouble(request.getParameter("precio").trim()));

        InventarioDAO dao = new InventarioDAO();
        boolean actualizado = dao.actualizarItem(itemActualizado);

        if (actualizado) {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            BitacoraDAO bDao = new BitacoraDAO();
            bDao.registrarMovimiento(usuario.getUsername(), "EDITAR", "Modificó el ID: " + itemActualizado.getIdItem());
        }

        response.sendRedirect("InventarioServlet");
    }
}