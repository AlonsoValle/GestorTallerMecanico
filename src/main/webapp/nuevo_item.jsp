<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("usuarioLogueado") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Nuevo Registro - Taller Mecánico</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f4f9; margin: 0; padding: 40px; display: flex; justify-content: center; }
        .form-container { background: white; padding: 35px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 400px; border-top: 5px solid #4CAF50; }
        h2 { color: #333; text-align: center; margin-bottom: 25px; }
        label { font-weight: bold; color: #555; display: block; margin-bottom: 5px; }
        input, select { width: 100%; padding: 10px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .btn-save { background-color: #4CAF50; color: white; padding: 12px; border: none; border-radius: 4px; width: 100%; cursor: pointer; font-weight: bold; margin-bottom: 10px; font-size: 15px; }
        .btn-save:hover { background-color: #45a049; }
        .btn-cancel { background-color: #f44336; color: white; padding: 12px; border: none; border-radius: 4px; width: 100%; cursor: pointer; font-weight: bold; display: block; text-align: center; text-decoration: none; box-sizing: border-box; font-size: 15px; }
        .btn-cancel:hover { background-color: #da190b; }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Agregar Nueva Refacción / Servicio</h2>

    <form action="RegistroItemServlet" method="POST">
        <label for="tipoItem">Tipo:</label>
        <select id="tipoItem" name="tipoItem" required>
            <option value="Refacción">Refacción</option>
            <option value="Servicio">Servicio</option>
        </select>

        <label for="descripcion">Descripción:</label>
        <input type="text" id="descripcion" name="descripcion" placeholder="Ej. Balatas delanteras..." required>

        <label for="cantidadStock">Cantidad en Stock (0 si es Servicio):</label>
        <input type="number" id="cantidadStock" name="cantidadStock" value="0" min="0" required>

        <label for="precio">Precio Unitario ($):</label>
        <input type="number" step="0.01" id="precio" name="precio" placeholder="0.00" min="0" required>

        <button type="submit" class="btn-save">Guardar Registro</button>
        <a href="InventarioServlet" class="btn-cancel">Cancelar y Volver</a>
    </form>
</div>
</body>
</html>