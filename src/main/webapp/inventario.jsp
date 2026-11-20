<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.taller.modelo.Item" %>
<%@ page import="com.taller.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    List<Item> lista = (List<Item>) request.getAttribute("listaInventario");
    int totalItems = 0;
    double valorInventario = 0;
    int stockBajoCount = 0;
    if (lista != null) {
        totalItems = lista.size();
        for(Item i : lista) {
            valorInventario += (i.getCantidadStock() * i.getPrecio());
            if ("Refacción".equals(i.getTipoItem()) && i.getCantidadStock() <= 3) {
                stockBajoCount++;
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Inventario - Taller Mecánico</title>
    <style>
        body { font-family: sans-serif; margin: 20px; background-color: #f4f4f9; }
        table { width: 95%; border-collapse: collapse; margin-top: 15px; margin-bottom: 20px; background-color: white; box-shadow: 0 4px 10px rgba(0,0,0,0.1); border-radius: 5px; overflow: hidden; }
        th, td { border: 1px solid #dddddd; padding: 12px; text-align: left; }
        th { background-color: #2196F3; color: white; }
        .admin-panel { background-color: #e3f2fd; border-left: 4px solid #2196F3; padding: 15px; margin-bottom: 20px; width: 92%; box-shadow: 0 2px 5px rgba(0,0,0,0.05); }
        .stats { display: flex; gap: 20px; margin-top: 20px; flex-wrap: wrap; }
        .stat-box { background: #fff3e0; padding: 15px 25px; border-radius: 5px; border-left: 5px solid #FF9800; box-shadow: 0 2px 5px rgba(0,0,0,0.05); font-size: 16px; }
        .stat-alert { background: #ffebee; padding: 15px 25px; border-radius: 5px; border-left: 5px solid #f44336; box-shadow: 0 2px 5px rgba(0,0,0,0.05); font-size: 16px; }
        .btn-action { text-decoration: none; padding: 6px 12px; border-radius: 4px; color: white; font-weight: bold; font-size: 13px; margin-right: 5px; }
        .btn-edit { background-color: #FF9800; }
        .btn-delete { background-color: #f44336; }
        .btn-edit:hover { background-color: #e68a00; }
        .btn-delete:hover { background-color: #da190b; }
        .filter-container { background-color: white; padding: 15px; border-radius: 5px; width: 92%; box-shadow: 0 2px 5px rgba(0,0,0,0.05); margin-bottom: 20px; border: 1px solid #eee; }
        .badge-agotado { background-color: #d32f2f; color: white; padding: 3px 7px; border-radius: 3px; font-size: 11px; font-weight: bold; margin-left: 6px; }
        .badge-bajo { background-color: #f57c00; color: white; padding: 3px 7px; border-radius: 3px; font-size: 11px; font-weight: bold; margin-left: 6px; }
        .row-alert { background-color: #fff5f5; }
    </style>
</head>
<body>
<div style="display: flex; justify-content: space-between; width: 95%; align-items: center;">
    <h2 style="color: #333;">Bienvenido al sistema, <%= usuario.getUsername() %> (<%= usuario.getRol() %>)</h2>
    <a href="LogoutServlet" style="color: white; background-color: #f44336; padding: 8px 15px; border-radius: 4px; font-weight: bold; text-decoration: none; box-shadow: 0 2px 4px rgba(0,0,0,0.2);">Cerrar Sesión</a>
</div>
<hr style="width: 95%; margin-left: 0; border: 1px solid #ccc; margin-bottom: 20px;">
<% if (usuario.getRol().equals("Administrador")) { %>
<div class="admin-panel">
    <strong style="color: #1976d2; font-size: 16px;">⚙️ Panel de Control Administrativo:</strong>
    <a href="nuevo_usuario.jsp" style="margin-left: 20px; color: #1976d2; text-decoration: none; font-weight: bold; background: white; padding: 6px 12px; border-radius: 4px; border: 1px solid #1976d2;">+ Crear Nuevo Perfil</a>
    <a href="BitacoraServlet" style="margin-left: 15px; color: #E91E63; text-decoration: none; font-weight: bold; background: white; padding: 6px 12px; border-radius: 4px; border: 1px solid #E91E63;">👁 Ver Bitácora de Movimientos</a>
</div>
<% } %>
<h3 style="color: #555;">Gestión de Refacciones y Servicios</h3>
<a href="nuevo_item.jsp" style="padding: 10px 15px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">+ Agregar Nueva Refacción/Servicio</a>
<br><br>
<form action="InventarioServlet" method="GET" class="filter-container">
    <label for="busqueda" style="font-weight: bold; color: #333;">Buscar:</label>
    <input type="text" name="busqueda" id="busqueda" placeholder="Ej. Filtro de aceite..." value="<%= request.getAttribute("busquedaActual") != null ? request.getAttribute("busquedaActual") : "" %>" style="padding: 8px; width: 180px; border: 1px solid #ccc; border-radius: 4px;">
    <label for="tipoFiltro" style="margin-left: 15px; font-weight: bold; color: #333;">Categoría:</label>
    <select name="tipoFiltro" id="tipoFiltro" style="padding: 8px; border: 1px solid #ccc; border-radius: 4px;">
        <% String filtro = (String) request.getAttribute("filtroActual"); %>
        <option value="Todos" <%= "Todos".equals(filtro) ? "selected" : "" %>>Mostrar Todos</option>
        <option value="Refacción" <%= "Refacción".equals(filtro) ? "selected" : "" %>>Solo Refacciones</option>
        <option value="Servicio" <%= "Servicio".equals(filtro) ? "selected" : "" %>>Solo Servicios</option>
    </select>
    <label for="ordenId" style="margin-left: 15px; font-weight: bold; color: #333;">Orden ID:</label>
    <select name="ordenId" id="ordenId" style="padding: 8px; border: 1px solid #ccc; border-radius: 4px;">
        <% String orden = (String) request.getAttribute("ordenActual"); %>
        <option value="mayor" <%= "mayor".equals(orden) ? "selected" : "" %>>Mayor a Menor</option>
        <option value="menor" <%= "menor".equals(orden) ? "selected" : "" %>>Menor a Mayor</option>
    </select>
    <button type="submit" style="padding: 8px 15px; margin-left: 15px; background-color: #2196F3; color: white; border: none; border-radius: 4px; font-weight: bold; cursor: pointer;">Filtrar</button>
    <a href="InventarioServlet" style="margin-left: 10px; color: #f44336; text-decoration: none; font-weight: bold;">Limpiar Filtros</a>
</form>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Tipo</th>
        <th>Descripción</th>
        <th>Stock</th>
        <th>Precio Unitario</th>
        <th>Valor Total Stock</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <%
        if(lista != null && !lista.isEmpty()) {
            for(Item item : lista) {
                double valorTotalFila = item.getCantidadStock() * item.getPrecio();
                boolean esRefaccion = "Refacción".equals(item.getTipoItem());
                boolean agotado = esRefaccion && item.getCantidadStock() == 0;
                boolean stockBajo = esRefaccion && item.getCantidadStock() > 0 && item.getCantidadStock() <= 3;
    %>
    <tr class="<%= (agotado || stockBajo) ? "row-alert" : "" %>">
        <td><%= item.getIdItem() %></td>
        <td><%= item.getTipoItem() %></td>
        <td><%= item.getDescripcion() %></td>
        <td>
            <strong><%= item.getCantidadStock() %></strong>
            <% if (agotado) { %>
            <span class="badge-agotado">AGOTADO</span>
            <% } else if (stockBajo) { %>
            <span class="badge-bajo">STOCK BAJO</span>
            <% } %>
        </td>
        <td>$<%= item.getPrecio() %></td>
        <td style="font-weight: bold; color: #388E3C;">$<%= String.format("%.2f", valorTotalFila) %></td>
        <td>
            <a href="EditarItemServlet?id=<%= item.getIdItem() %>" class="btn-action btn-edit">Editar</a>
            <a href="EliminarItemServlet?id=<%= item.getIdItem() %>" class="btn-action btn-delete" onclick="return confirm('¿Estás seguro de que deseas eliminar este registro?');">Eliminar</a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="7" style="text-align: center; padding: 20px; color: #777;">No hay refacciones ni servicios registrados.</td>
    </tr>
    <% } %>
    </tbody>
</table>
<div class="stats">
    <div class="stat-box"><strong>Registros en vista:</strong> <span style="color: #2196F3;"><%= totalItems %></span></div>
    <div class="stat-box"><strong>Valor estimado del stock:</strong> <span style="color: #4CAF50;">$<%= String.format("%.2f", valorInventario) %></span></div>
    <div class="stat-alert"><strong>Refacciones por surtir:</strong> <span style="color: #d32f2f;"><%= stockBajoCount %></span></div>
</div>
</body>
</html>