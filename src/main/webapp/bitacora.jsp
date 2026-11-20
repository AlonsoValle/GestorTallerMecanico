<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.taller.modelo.Bitacora" %>
<%@ page import="com.taller.modelo.Usuario" %>
<%
    Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");
    if (admin == null || !admin.getRol().equals("Administrador")) {
        response.sendRedirect("InventarioServlet");
        return;
    }
    List<Bitacora> lista = (List<Bitacora>) request.getAttribute("listaBitacora");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Bitácora de Movimientos - Taller Mecánico</title>
    <style>
        body { font-family: sans-serif; margin: 20px; background-color: #f4f4f9; }
        table { width: 95%; border-collapse: collapse; margin-top: 20px; background: white; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
        th, td { border: 1px solid #dddddd; padding: 12px; text-align: left; }
        th { background-color: #2196F3; color: white; }
        .btn-back { display: inline-block; padding: 10px 15px; background-color: #607D8B; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .btn-back:hover { background-color: #455A64; }
    </style>
</head>
<body>
<h2>Historial de Movimientos del Sistema (Auditoría)</h2>
<a href="InventarioServlet" class="btn-back">← Volver al Inventario</a>

<table>
    <thead>
    <tr>
        <th>ID Movimiento</th>
        <th>Usuario Responsable</th>
        <th>Tipo de Acción</th>
        <th>Detalle del Movimiento</th>
        <th>Fecha y Hora</th>
    </tr>
    </thead>
    <tbody>
    <%
        if(lista != null && !lista.isEmpty()) {
            for(Bitacora b : lista) {
    %>
    <tr>
        <td><%= b.getIdMovimiento() %></td>
        <td><strong><%= b.getUsuario() %></strong></td>
        <td style="color: <%= b.getAccion().equals("ELIMINAR") ? "red" : (b.getAccion().equals("CREAR") ? "green" : "#FF9800") %>; font-weight: bold;">
            <%= b.getAccion() %>
        </td>
        <td><%= b.getDetalle() %></td>
        <td><%= b.getFecha() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5" style="text-align: center;">Aún no hay movimientos registrados en el sistema.</td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>