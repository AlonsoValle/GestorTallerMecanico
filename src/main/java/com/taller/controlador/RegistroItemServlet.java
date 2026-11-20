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

@WebServlet("/RegistroItemServlet")
public class RegistroItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Item nuevoItem = new Item();
        nuevoItem.setTipoItem(request.getParameter("tipoItem"));
        nuevoItem.setDescripcion(request.getParameter("descripcion"));
        nuevoItem.setCantidadStock(Integer.parseInt(request.getParameter("cantidadStock")));
        nuevoItem.setPrecio(Double.parseDouble(request.getParameter("precio")));

        InventarioDAO dao = new InventarioDAO();
        boolean guardado = dao.registrarItem(nuevoItem);

        if (guardado) {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            BitacoraDAO bDao = new BitacoraDAO();
            bDao.registrarMovimiento(usuario.getUsername(), "CREAR", "Registró nuevo item: " + nuevoItem.getDescripcion());
        }

        response.sendRedirect("InventarioServlet");
    }
}