<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.taller.modelo.Usuario" %>
<%
    Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");
    if (admin == null || !admin.getRol().equals("Administrador")) {
        response.sendRedirect("InventarioServlet");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Crear Usuario - Taller Mecánico</title>
    <style>
        body { font-family: sans-serif; background-color: #f4f4f9; margin: 0; padding: 40px; display: flex; justify-content: center; }
        .form-container { background: white; padding: 35px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 400px; border-top: 5px solid #2196F3; }
        h2 { color: #333; text-align: center; margin-bottom: 25px; font-size: 22px; }
        label { font-weight: bold; color: #555; display: block; margin-bottom: 5px; }
        input, select { width: 100%; padding: 10px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .rules { font-size: 12px; color: #555; background: #f9f9f9; padding: 10px; border-radius: 4px; border-left: 3px solid #2196F3; margin-bottom: 15px; }
        .btn-save { background-color: #2196F3; color: white; padding: 12px; border: none; border-radius: 4px; width: 100%; cursor: pointer; font-weight: bold; margin-bottom: 10px; font-size: 15px; }
        .btn-save:hover { background-color: #1976D2; }
        .btn-cancel { background-color: #9e9e9e; color: white; padding: 12px; border: none; border-radius: 4px; width: 100%; cursor: pointer; font-weight: bold; display: block; text-align: center; text-decoration: none; box-sizing: border-box; font-size: 15px; }
        .btn-cancel:hover { background-color: #757575; }
        .error { color: #d32f2f; background: #ffebee; padding: 10px; border-radius: 4px; margin-bottom: 15px; font-size: 13px; font-weight: bold; text-align: center; }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Crear Nuevo Perfil<br><small style="color: #1976d2; font-size: 16px;">(Administrador)</small></h2>
    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form action="RegistroUsuarioServlet" method="POST">
        <label for="username">Nombre de Usuario:</label>
        <input type="text" id="username" name="username" value="<%= request.getAttribute("usernamePrevio") != null ? request.getAttribute("usernamePrevio") : "" %>" placeholder="Ej. Carlos" required>
        <label for="password">Contraseña temporal:</label>
        <input type="password" id="password" name="password" placeholder="Contraseña segura" required>
        <div class="rules">
            Requisitos: Mínimo 5 caracteres, una mayúscula, un número, un carácter especial y no coincidir con el usuario.
        </div>
        <label for="rol">Rol en el sistema:</label>
        <select id="rol" name="rol">
            <option value="Mecánico">Mecánico</option>
            <option value="Operador">Operador</option>
            <option value="Administrador">Administrador</option>
        </select>
        <button type="submit" class="btn-save">Guardar Perfil</button>
        <a href="InventarioServlet" class="btn-cancel">Cancelar</a>
    </form>
</div>
</body>
</html>